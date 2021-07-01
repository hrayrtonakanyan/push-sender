package org.pushme.pushsender.model;

import lombok.*;
import org.pushme.pushsender.enumuration.DeviceType;

/**
 * Created with IntelliJ IDEA.
 * User: Hrayr
 * Date: 6/1/21.
 * Time: 13:14.
 */
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Message {

    @NonNull
    private DeviceType deviceType;
    @NonNull
    private String appName;
    private String userIdentifier;
    private String pushToken;
    private String voipPushToken;
    private boolean isVoip;
    private boolean isDev;
    private Long expireMs;
    @NonNull
    private Payload payload;
}
