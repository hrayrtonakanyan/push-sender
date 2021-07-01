package org.pushme.pushsender.service;

import com.eatthepath.pushy.apns.ApnsClient;
import com.eatthepath.pushy.apns.ApnsClientBuilder;
import com.eatthepath.pushy.apns.DeliveryPriority;
import com.eatthepath.pushy.apns.util.SimpleApnsPushNotification;
import lombok.extern.slf4j.Slf4j;
import org.pushme.pushsender.exception.ApnsClientInitializationException;
import org.pushme.pushsender.exception.PushServiceConfigurationException;
import org.pushme.pushsender.listener.PushResponseManager;
import org.pushme.pushsender.model.Message;
import org.pushme.pushsender.model.config.IosPushConfig;
import org.pushme.pushsender.model.thirdparty.ThirdPartyApnsPayloadBuilder;
import org.pushme.pushsender.utils.file.FileUtils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Hrayr
 * Date: 6/25/21.
 * Time: 17:13.
 */
@Slf4j
public class IosPushService {

    private final static Object lock = new Object();
    private final Map<String/*appName*/, IosPushConfig> configMap = Collections.synchronizedMap(new HashMap<>());
    private final Map<String/*appName*/, Map<Boolean/*dev*/, ApnsClient>> clientMap = Collections.synchronizedMap(new HashMap<>());

    public IosPushService(IosPushConfig... configs) {
        for (IosPushConfig config : configs) {
            this.configMap.put(config.getAppName(), config);
        }
    }

    public void addConfigs(IosPushConfig... configs) {
        for (IosPushConfig config : configs) {
            this.configMap.put(config.getAppName(), config);
        }
    }

    public void send(Message message) throws PushServiceConfigurationException, ApnsClientInitializationException {
        getClient(message)
                .sendNotification(createNotification(message))
                .whenComplete((response, cause) -> {
                    if (response == null) {
                        log.error("Response does not exist. " + cause.getMessage(), cause);
                        return;
                    }
                    boolean isValid = true;
                    if (response.isAccepted()) {
                        log.info("[ACCEPTED][{}]", message.getUserIdentifier());
                    } else {
                        log.error("[REJECTED - {}][{}] {}", response.getRejectionReason(), message.getUserIdentifier(), message.toString());
                        if ("Unregistered".equalsIgnoreCase(response.getRejectionReason())) {
                            isValid = false;
                        }
                        if (response.getTokenInvalidationTimestamp().isPresent()) {
                            log.error("[TOKEN_INVALIDATION_TIMESTAMP][{}] {}", message.getUserIdentifier(), response.getTokenInvalidationTimestamp());
                        }
                    }
                    PushResponseManager.getInstance().fireEvent(message, isValid);
                });
    }

    public void destroy() {
        for (Map<Boolean/*dev*/, ApnsClient> map : clientMap.values()) {
            for (ApnsClient client : map.values()) {
                client.close();
            }
        }
    }

    private ApnsClient getClient(Message message) throws PushServiceConfigurationException, ApnsClientInitializationException {
        ApnsClient client = null;
        Map<Boolean/*dev*/, ApnsClient> appClientMap = clientMap.get(message.getAppName());
        if (appClientMap == null) {
            synchronized (lock) {
                appClientMap = clientMap.get(message.getAppName());
                if (appClientMap == null) {
                    client = initClient(message);
                }
            }
        } else {
            client = appClientMap.get(message.isDev());
            if (client == null) {
                synchronized (lock) {
                    if (client == null) {
                        client = initClient(message, appClientMap);
                    }
                }
            }
        }
        return client;
    }

    private SimpleApnsPushNotification createNotification(Message message) throws PushServiceConfigurationException {
        return new SimpleApnsPushNotification(getToken(message),
                getPushTopic(message),
                new ThirdPartyApnsPayloadBuilder(message.getPayload())
                        .build(),
                calculateExpiry(message.getExpireMs()),
                DeliveryPriority.IMMEDIATE);
    }

    private String getPushTopic(Message message) throws PushServiceConfigurationException {
        IosPushConfig config = getConfig(message.getAppName());
        return message.isVoip() ? config.getVoipPushTopic() : config.getPushTopic();
    }

    private String getToken(Message message) {
        if (message.isVoip()) {
            return message.getVoipPushToken();
        } else {
            return message.getPushToken();
        }
    }

    private Instant calculateExpiry(Long expireMs) {
        if (expireMs == null) {
            expireMs = 24L * 60 * 60 * 1000;
        }
        long expireDateMs = System.currentTimeMillis() + expireMs;
        return Instant.ofEpochMilli(expireDateMs);
    }

    private ApnsClient initClient(Message message) throws PushServiceConfigurationException, ApnsClientInitializationException {
        return initClient(message, null);
    }

    private ApnsClient initClient(Message message, Map<Boolean/*dev*/, ApnsClient> appClientMap)
            throws PushServiceConfigurationException, ApnsClientInitializationException {
        IosPushConfig config = getConfig(message.getAppName());
        InputStream inputStream = null;
        try {
            String apnsHost = message.isDev() ?
                    ApnsClientBuilder.DEVELOPMENT_APNS_HOST :
                    ApnsClientBuilder.PRODUCTION_APNS_HOST;

            inputStream = FileUtils.getInstance().getFileInputStream(config.getCertPath(),
                    message.isDev() ? config.getDevCertName() : config.getCertName());
            ApnsClient client = new ApnsClientBuilder()
                    .setClientCredentials(inputStream, config.getCertPassword())
                    .setApnsServer(apnsHost)
                    .build();

            if (appClientMap == null) {
                appClientMap = new HashMap<>();
            }
            appClientMap.put(message.isDev(), client);
            clientMap.put(message.getAppName(), appClientMap);

            return client;
        } catch (Throwable throwable) {
            String errorMessage = String.format("Can not create APNs Client. appName: %s, certPath: %s, certName: %s, voipCertName: %s",
                    config.getAppName(), config.getCertPath(), config.getCertPath(), config.getDevCertName());
            log.error(errorMessage);
            throw new ApnsClientInitializationException(errorMessage, throwable);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Throwable throwable) {
                log.error("Can not close input stream. " + throwable.getMessage());
                log.info("Can not close input stream. " + throwable.getMessage(), throwable);
            }
        }
    }

    private IosPushConfig getConfig(String appName) throws PushServiceConfigurationException {
        IosPushConfig config = configMap.get(appName);
        if (config == null) {
            String errorMessage = String.format("App push configuration does not exist for appName: %s", appName);
            log.error(errorMessage);
            throw new PushServiceConfigurationException(errorMessage);
        }
        return config;
    }
}
