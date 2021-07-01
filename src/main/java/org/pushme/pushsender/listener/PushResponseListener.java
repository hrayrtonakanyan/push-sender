package org.pushme.pushsender.listener;

import org.pushme.pushsender.model.Message;

/**
 * Created with IntelliJ IDEA.
 * User: Hrayr
 * Date: 6/28/21.
 * Time: 15:05.
 */
public interface PushResponseListener {
    void receiveDevice(Message message, boolean isValid);
}
