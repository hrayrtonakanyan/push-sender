package org.pushme.pushsender.service;

import org.pushme.pushsender.model.Message;
import org.pushme.pushsender.model.config.HuaweiPushConfig;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Hrayr
 * Date: 6/25/21.
 * Time: 17:14.
 */
public class HuaweiPushService {

    private final Map<String/*appName*/, HuaweiPushConfig> configMap = Collections.synchronizedMap(new HashMap<>());

    public HuaweiPushService(HuaweiPushConfig ... configs) {
        for (HuaweiPushConfig config : configs) {
            this.configMap.put(config.getAppName(), config);
        }
    }

    public void addConfigs(HuaweiPushConfig... configs) {
        for (HuaweiPushConfig config : configs) {
            this.configMap.put(config.getAppName(), config);
        }
    }

    public void send(Message message) {

    }
}
