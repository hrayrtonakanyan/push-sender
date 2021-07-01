package org.pushme.pushsender.model.config;

import lombok.Builder;
import lombok.Getter;

/**
 * Created with IntelliJ IDEA.
 * User: Hrayr
 * Date: 6/26/21.
 * Time: 02:25.
 */
@Builder(toBuilder = true)
@Getter
public class HuaweiPushConfig {

    private final String appName;
}
