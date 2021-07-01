package org.pushme.pushsender.exception;

/**
 * Created with IntelliJ IDEA.
 * User: Hrayr
 * Date: 6/25/21.
 * Time: 19:41.
 */
public class ApnsClientInitializationException  extends PushSenderCoreException {

    public ApnsClientInitializationException(String message) {
        super(message);
    }

    public ApnsClientInitializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApnsClientInitializationException(Throwable cause) {
        super(cause);
    }
}
