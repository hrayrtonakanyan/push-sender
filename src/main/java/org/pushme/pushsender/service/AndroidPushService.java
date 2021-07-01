package org.pushme.pushsender.service;

import org.pushme.pushsender.model.Message;
import org.pushme.pushsender.model.config.AndroidPushConfig;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Hrayr
 * Date: 6/25/21.
 * Time: 17:14.
 */
public class AndroidPushService {

    private final Map<String/*appName*/, AndroidPushConfig> configMap = Collections.synchronizedMap(new HashMap<>());

    public AndroidPushService(AndroidPushConfig ... configs) {
        for (AndroidPushConfig config : configs) {
            this.configMap.put(config.getAppName(), config);
        }
    }

    public void addConfigs(AndroidPushConfig... configs) {
        for (AndroidPushConfig config : configs) {
            this.configMap.put(config.getAppName(), config);
        }
    }

    public void send(Message message) {

    }
}
