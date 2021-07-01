package org.pushme.pushsender;

import org.pushme.pushsender.exception.ApnsClientInitializationException;
import org.pushme.pushsender.exception.PushServiceConfigurationException;
import org.pushme.pushsender.listener.PushResponseListener;
import org.pushme.pushsender.listener.PushResponseManager;
import org.pushme.pushsender.model.Message;
import org.pushme.pushsender.model.config.AndroidPushConfig;
import org.pushme.pushsender.model.config.HuaweiPushConfig;
import org.pushme.pushsender.model.config.IosPushConfig;
import org.pushme.pushsender.service.AndroidPushService;
import org.pushme.pushsender.service.HuaweiPushService;
import org.pushme.pushsender.service.IosPushService;

/**
 * Created with IntelliJ IDEA.
 * User: Hrayr
 * Date: 6/1/21.
 * Time: 02:43.
 */
public class Push {

    private final IosPushService iosPushService;
    private final AndroidPushService androidPushService;
    private final HuaweiPushService huaweiPushService;

    private Push(IosPushService iosPushService, AndroidPushService androidPushService, HuaweiPushService huaweiPushService) {
        this.iosPushService = iosPushService;
        this.androidPushService = androidPushService;
        this.huaweiPushService = huaweiPushService;
    }

    public static PushBuilder create() {
        return new PushBuilder();
    }

    public void send(Message message) throws PushServiceConfigurationException, ApnsClientInitializationException {
        switch (message.getDeviceType()) {
            case iOS:
                if (iosPushService == null) {
                    throw new PushServiceConfigurationException("iOS push service does not configured");
                }
                iosPushService.send(message);
                break;
            case ANDROID:
                if (androidPushService == null) {
                    throw new PushServiceConfigurationException("Android push service does not configured");
                }
                androidPushService.send(message);
                break;
            case HUAWEI:
                if (huaweiPushService == null) {
                    throw new PushServiceConfigurationException("Huawei push service does not configured");
                }
                huaweiPushService.send(message);
                break;
        }
    }

    public static class PushBuilder {

        private IosPushService iosPushService;
        private AndroidPushService androidPushService;
        private HuaweiPushService huaweiPushService;

        PushBuilder() {
        }

        public PushBuilder addConfigs(IosPushConfig... iosPushConfigs) {
            if (iosPushService == null) {
                iosPushService = new IosPushService(iosPushConfigs);
            } else {
                iosPushService.addConfigs(iosPushConfigs);
            }
            return this;
        }

        public PushBuilder addConfigs(AndroidPushConfig... androidPushConfigs) {
            if (androidPushService == null) {
                androidPushService = new AndroidPushService(androidPushConfigs);
            } else {
                androidPushService.addConfigs(androidPushConfigs);
            }
            return this;
        }

        public PushBuilder addConfigs(HuaweiPushConfig... huaweiPushConfigs) {
            if (huaweiPushService == null) {
                huaweiPushService = new HuaweiPushService(huaweiPushConfigs);
            } else {
                huaweiPushService.addConfigs(huaweiPushConfigs);
            }
            return this;
        }

        public PushBuilder addListener(PushResponseListener listener) {
            PushResponseManager.getInstance().addListener(listener);
            return this;
        }

        public Push build() {
            return new Push(iosPushService, androidPushService, huaweiPushService);
        }
    }
}
