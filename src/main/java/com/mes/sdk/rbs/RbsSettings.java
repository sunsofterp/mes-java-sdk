package com.mes.sdk.rbs;

import com.mes.sdk.core.Settings;

/**
 * A object that holds generic configuration for the Recurring Billing System.<br />
 * The {@link Rbs} class requires this object in it's constructor.
 *
 * @author BRice
 */
public class RbsSettings extends Settings<RbsSettings> {

    private String urlPostfix;

    /**
     * Used by MeS to test new release code before being pushed to production.
     */
    public static final String URL_TEST = "https://test.merchante-solutions.com/srv/api/";

    /**
     * Used to process live requests.
     */
    public static final String URL_LIVE = "https://test.merchante-solutions.com/srv/api/";

    private String userName, userPass, profileId;

    /**
     * Creates default gateway settings of method type POST, verbose false, and a timeout of 20s.<br />
     * The default URL is set to live.
     */
    public RbsSettings() {
        super();
        hostUrl(URL_LIVE);
    }

    /**
     * Sets the User name and User Password which are used to authenticate with the Recurring Billing System.
     *
     * @param userName
     * @param userPass
     * @param profileId
     */
    public final RbsSettings credentials(String userName, String userPass, String profileId) {
        this.userName = userName;
        this.userPass = userPass;
        this.profileId = profileId;
        return this;
    }

    /**
     * Returns the current User Name
     *
     * @return
     */
    public final String getUserName() {
        return userName;
    }

    /**
     * Returns the user User Password
     *
     * @return
     */
    public final String getUserPass() {
        return userPass;
    }

    public final String getProfileId() {
        return profileId;
    }

    @Override
    public String getHostUrl() {
        return super.getHostUrl() + urlPostfix;
    }

    /**
     * The RBS URL changes depending on the request type. Store the postfix for the URL here.
     *
     * @param postfix
     */
    protected void setUrlPostfix(String postfix) {
        this.urlPostfix = postfix;
    }

}
