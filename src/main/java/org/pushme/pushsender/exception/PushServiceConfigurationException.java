package org.pushme.pushsender.exception;

/**
 * Created with IntelliJ IDEA.
 * User: Hrayr
 * Date: 6/26/21.
 * Time: 02:39.
 */
public class PushServiceConfigurationException extends PushSenderCoreException {

    public PushServiceConfigurationException(String message) {
        super(message);
    }

    public PushServiceConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    public PushServiceConfigurationException(Throwable cause) {
        super(cause);
    }
}
