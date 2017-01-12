package com.mes.sdk.gateway;

import com.mes.sdk.core.Settings;

/**
 * A object that holds generic configuration for the Payment Gateway.<br />
 * The {@link Gateway} class requires this object in it's constructor.
 *
 * @author brice
 */
public class GatewaySettings extends Settings<GatewaySettings> {

    /**
     * Used by MeS to test new release code before being pushed to production. Uses the transaction simulator.
     */
    public static final String URL_TEST = "https://test.merchante-solutions.com/mes-api/tridentApi";

    /**
     * Uses the same codebase as the live URL, but is pointed to the transaction simulator.
     */
    public static final String URL_CERT = "https://cert.merchante-solutions.com/mes-api/tridentApi";

    /**
     * Used to process live transaction requests.
     */
    public static final String URL_LIVE = "https://api.merchante-solutions.com/mes-api/tridentApi";

    private String profileId;
    private String profileKey;

    /**
     * Creates default gateway settings of method type POST, verbose false, a timeout of 20s, and validation of request objects true.<br />
     * The default URL is set to live.
     */
    public GatewaySettings() {
        super();
        hostUrl(URL_LIVE);
    }

    /**
     * Sets the profile ID and profile Key which are used to authenticate with the Payment Gateway
     *
     * @param id
     * @param key
     */
    public final GatewaySettings credentials(String id, String key) {
        this.profileId = id;
        this.profileKey = key;
        return this;
    }

    /**
     * Returns the profile ID
     *
     * @return
     */
    public final String getProfileId() {
        return profileId;
    }

    /**
     * Returns the profile Key
     *
     * @return
     */
    public final String getProfileKey() {
        return profileKey;
    }

}
