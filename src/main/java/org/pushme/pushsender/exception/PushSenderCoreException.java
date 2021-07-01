package org.pushme.pushsender.exception;

/**
 * Created with IntelliJ IDEA.
 * User: Hrayr
 * Date: 6/25/21.
 * Time: 18:53.
 */
public class PushSenderCoreException extends Exception {

    public PushSenderCoreException(String message) {
        super(message);
    }

    public PushSenderCoreException(String message, Throwable cause) {
        super(message, cause);
    }

    public PushSenderCoreException(Throwable cause) {
        super(cause);
    }
}
