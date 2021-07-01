package org.pushme.pushsender.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Hrayr
 * Date: 6/1/21.
 * Time: 13:59.
 */
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Payload {

    private String alertBody;

    private String localizedAlertKey;
    private String[] localizedAlertArguments;
    private String alertTitle;
    private String localizedAlertTitleKey;
    private String[] localizedAlertTitleArguments;
    private String alertSubtitle;
    private String localizedAlertSubtitleKey;
    private String[] localizedAlertSubtitleArguments;

    private String launchImageFileName;
    private Integer badgeNumber;
    private String categoryName;

    private boolean contentAvailable;
    private boolean mutableContent;

    @Builder.Default
    private boolean showActionButton = true;
    private String actionButtonLabel;
    private String localizedActionButtonKey;

    private String threadId;
    private String targetContentId;

    private String summaryArgument;
    private Integer summaryArgumentCount;

    private String[] urlArguments;
    private String soundFileName;

    private Map<String, String> customProperties;
}
