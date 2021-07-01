package org.pushme.pushsender.model.thirdparty;

import com.eatthepath.json.JsonSerializer;
import com.eatthepath.pushy.apns.util.ApnsPayloadBuilder;
import org.pushme.pushsender.model.Payload;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Hrayr
 * Date: 6/25/21.
 * Time: 17:41.
 */
public class ThirdPartyApnsPayloadBuilder extends ApnsPayloadBuilder {

    public ThirdPartyApnsPayloadBuilder(Payload payload) {
        init(payload);
    }

    private void init(Payload payload) {

        setAlertBody(payload.getAlertBody());
        setLocalizedAlertMessage(payload.getLocalizedAlertKey(), payload.getLocalizedAlertArguments());
        setAlertTitle(payload.getAlertTitle());
        setLocalizedAlertTitle(payload.getLocalizedAlertTitleKey(), payload.getLocalizedAlertTitleArguments());
        setAlertSubtitle(payload.getAlertSubtitle());
        setLocalizedAlertSubtitle(payload.getLocalizedAlertSubtitleKey(), payload.getLocalizedAlertSubtitleArguments());

        setLaunchImageFileName(payload.getLaunchImageFileName());
        setBadgeNumber(payload.getBadgeNumber());
        setCategoryName(payload.getCategoryName());

        setContentAvailable(payload.isContentAvailable());
        setMutableContent(payload.isMutableContent());

        setShowActionButton(payload.isShowActionButton());
        setActionButtonLabel(payload.getActionButtonLabel());
        setLocalizedActionButtonKey(payload.getLocalizedActionButtonKey());

        setThreadId(payload.getThreadId());
        setTargetContentId(payload.getTargetContentId());

        setSummaryArgument(payload.getSummaryArgument());
        setSummaryArgumentCount(payload.getSummaryArgumentCount());

        setUrlArguments(payload.getUrlArguments());
        setSound(payload.getSoundFileName());

        if (payload.getCustomProperties() != null) {
            for (Map.Entry<String, String> entity : payload.getCustomProperties().entrySet()) {
                addCustomProperty(entity.getKey(), entity.getValue());
            }
        }
    }

    @Override
    public String build() {
        return JsonSerializer.writeJsonTextAsString(this.buildPayloadMap());
    }

    @Override
    public String buildMdmPayload(String pushMagicValue) {
        return JsonSerializer.writeJsonTextAsString(this.buildMdmPayloadMap(pushMagicValue));
    }
}
