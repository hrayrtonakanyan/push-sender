package org.pushme.pushsender.listener;

import lombok.extern.log4j.Log4j2;
import org.pushme.pushsender.model.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Hrayr
 * Date: 6/28/21.
 * Time: 15:04.
 */
@Log4j2
public class PushResponseManager {

    private static final PushResponseManager ourInstance = new PushResponseManager();
    private final List<PushResponseListener> pushResponseListenerList = new ArrayList<>();

    public static PushResponseManager getInstance() {
        return ourInstance;
    }

    private PushResponseManager() {
    }

    public void addListener(PushResponseListener pushResponseListener) {
        pushResponseListenerList.add(pushResponseListener);
    }

    public void fireEvent(Message message, boolean isTokenValid, String rejectionReason) {
        for (PushResponseListener pushResponseListener : pushResponseListenerList) {
            pushResponseListener.receiveDevice(message, isTokenValid, rejectionReason);
        }
    }
}
