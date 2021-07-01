package org.pushme.pushsender.model.config;

import lombok.Builder;
import lombok.Getter;

/**
 * Created with IntelliJ IDEA.
 * User: Hrayr
 * Date: 6/1/21.
 * Time: 14:10.
 */
@Builder(toBuilder = true)
@Getter
public class IosPushConfig {

    private final String appName;
    private final String pushTopic;
    private final String voipPushTopic;
    private final String certPath;
    private final String certName;
    private final String devCertName;
    private final String certPassword;
}
