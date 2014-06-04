package com.softcoil;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.http.AndroidHttpClient;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

import java.net.URI;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is for building and using a public source of MMSC APN data for use when access to
 * the device APN data is not available. For instance when developing MMS apps for Android 4.2, 4.3.
 *
 * The class provides an in-app source for APN MMSC info for use as a fallback in
 * the event that the system APN DB is unavailable and the user has not provided
 * local MMSC configuration details of their own.
 *
 * It also provides a way for working APN configurations to report their parameters to a
 * central source so that the data can be integrated into this class and shared with the
 * public.
 *
 * Copyright 2014 SoftCoil Development, LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
public class ApnDefaults {

    private static final String PREF_KEY_LAST_APN_REPORT = "com.softcoil.apn_data";
    private static final String REPORT_URL = "http://apn.softcoil.com/apnReport";

    /**
     * Currently known APN parameters stored by MCCMNC and sim parameters.
     */
    private static final Map<String, ApnParameters> APN_PARAMETERS_MAP = new HashMap<String, ApnParameters>(){{

        //AT&T
        put("310410||310410|AT&T", new ApnParameters("http://mmsc.mobile.att.net", "proxy.mobile.att.net", null));

        //Boost Mobile
        put("310120|Sprint|310120|Boost Mobile", new ApnParameters("http://mm.myboostmobile.com", "68.28.31.7", 80));

        //Family Mobile
        put("310260||310260|Family Mobile", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", "null", null));

        //HOME
        put("310260||310260|HOME", new ApnParameters("http://mms.tracfone.com", "216.155.165.40", 8080));

        //Home
        put("310000|Default|310000|Home", new ApnParameters("http://pix.cspire.com", "null", null));

        //MetroPCS
        put("310260||310260|MetroPCS", new ApnParameters("http://metropcs.mmsmvno.com/mms/wapenc", "null", null));

        //Red Pocket
        put("310410|AT&T@|310410|Red Pocket", new ApnParameters("http://mmsc.cingular.com", "66.209.11.33", null));

        //Red Pocket
        put("310410||310410|Red Pocket", new ApnParameters("http://mmsc.cingular.com", "66.209.11.33", 80));

        //Simple Mobile
        put("310260||310260|Simple Mobile", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", "null", null));

        //SoftBank
        put("44020|SoftBank|44020|SoftBank", new ApnParameters("http://mms-s", "andmms.plusacs.ne.jp", 8080));

        //Sprint
        put("310120|Sprint|310120|Sprint", new ApnParameters("http://mms.sprintpcs.com", "68.28.31.7", 80));

        //T-Mobile USA
        put("310260||310260|T-Mobile", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //Verizon Wireless
        put("311480|Verizon Wireless|311480|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", "null", null));

        //Verizon Wireless
        put("311480|Verizon|311480|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", "null", null));

        //Verizon Wireless
        put("311480|Verizon Wireless|310000|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", "null", null));

        //Verizon Wireless
        put("311480|Verizon|31000|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", "null", null));

        //Verizon Wireless
        put("310004|Verizon|310000|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", "null", null));

        //vodafone UK
        put("23415||23415|vodafone UK", new ApnParameters("http://mms.vodafone.co.uk/servlets/mms", "212.183.137.12", 8799));

        // ******* Old data keyed only by mccmnc below!
        // ******* Sub-carriers will match the wrong entry here because they share MCCMNCs!

        //T-Mobile USA - Tested: Works
        put("310260", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //AT&T - Untested
        put("310410", new ApnParameters("http://mmscUrl.cingular.com/", "wireless.cingular.com", 80));

        //Verizon - Untested
        put("310004", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));
        put("310005", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));
        put("310012", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));
        put("311480", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Telenor Norway - Tested
        put("24201", new ApnParameters("http://mmscUrl", "10.10.10.11", 8080));

        // Rogers - Untested
        put("302720", new ApnParameters("http://mms.gprs.rogers.com", "10.128.1.69", 80));

        // Virgin Mobile US - Untested
        put("310053", new ApnParameters("http://mmscUrl.vmobl.com:8080/mms", "205.239.233.136", 81));
    }};

    /**
     * This is a purely static class.
     */
    private ApnDefaults() {}

    /**
     * Get the default ApnParameters for the current carrier from the APN_PARAMETERS_MAP. This uses
     * a combination of SIM MCCMNC, SIM operator name, network MCCMNC, and network name to try to
     * determine the correct parameters to use.
     *
     * If there is no match and fallback is true the method will attempt to return a match on the
     * SIM MCCMNC only. This will help in many cases but will return incorrect parameters in others.
     *
     * @param context The current context.
     * @param fallBack Should we attempt to fallback on matching just the SIM MCCMNC if we don't
     *                 find a match for the full key?
     * @return The ApnParameters or null.
     */
    public static ApnParameters getApnParameters(Context context, boolean fallBack) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        StringBuffer sb = new StringBuffer()
                .append(tm.getSimOperator()).append('|')
                .append(tm.getSimOperatorName()).append('|')
                .append(tm.getNetworkOperator()).append('|')
                .append(tm.getNetworkOperatorName());

        ApnParameters apnParameters = APN_PARAMETERS_MAP.get(sb.toString());

        //Fallback on old data if we don't have new full network keys yet.
        if(apnParameters == null && fallBack) {
            apnParameters = APN_PARAMETERS_MAP.get(tm.getSimOperator());
        }

        return apnParameters;
    }

    /**
     * Get the default ApnParameters for the current carrier from the APN_PARAMETERS_MAP. This uses
     * a combination of SIM MCCMNC, SIM operator name, network MCCMNC, and network name to try to
     * determine the correct parameters to use.
     *
     * Fall back on using only the SIM MCCMNC if we don't have a match for the full key.
     *
     * @param context The current context.
     * @return The ApnParameters or null.
     */
    public static ApnParameters getApnParameters(Context context) {
        return getApnParameters(context, true);
    }

    /**
     * This method provides a means for clients to report new, good APN connection parameters
     * to a central repository so that they can be integrated with this class and shared with
     * the public.
     *
     * It contains protections so that new ApnParameters are only reported to the server the first
     * time this method is called. In addition, it uses a short connection timeout so it can be
     * safely called from your current worker thread without worry that it will unnecessarily
     * delay your process.
     *
     * It should be called immediately after successfully sending a MMS message. Example:<br/>
     *  <pre>
     *  //Send your MMS using whatever method you currently use.
     *  byte[] response = myMmsHttpPost(mmscUrl, mmsProxy, mmsProxyPort, sendReqPdu);
     *
     *  //Parse the response.
     *  SendConf sendConf = (SendConf) new PduParser(response).parse();
     *
     *  //Check to see if the response was success.
     *  if(sendConf != null && sendConf.getResponseStatus() == PduHeaders.RESPONSE_STATUS_OK) {
     *      //Report the ApnParameters used for the post.
     *      ApnParameters parameters = new ApnParameters(mmscUrl, mmsProxy, mmsProxyPort);
     *      ApnDefaults.reportApnData(context, parameters);
     *  }
     *
     *  //Continue your process.
     *  ...
     *  </pre>
     *
     *  In order for this class to be useful we must collect data from as many working
     *  configurations as possible. Adding a call to this method after a successful MMSC operation
     *  in your app will help us all to provide a good MMS experience to our users.
     *
     * @param context The current context.
     * @param apnParameters The known good ApnParameters to report.
     */
    public static void reportApnData(Context context, ApnParameters apnParameters) {

        if(apnParameters == null) return;

        String apnData = apnParameters.getMmscUrl() + "|" + apnParameters.getProxyAddress() + "|" + apnParameters.getProxyPort();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String previousApnData = prefs.getString(PREF_KEY_LAST_APN_REPORT, null);

        if(!apnData.equals(previousApnData)) {

            //Save new apn data
            prefs.edit().putString(PREF_KEY_LAST_APN_REPORT, apnData).apply();

            //Report new apn data
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String networkOperator = tm.getNetworkOperator();
            String networkOperatorName = tm.getNetworkOperatorName();
            String simOperator = tm.getSimOperator();
            String simOperatorName = tm.getSimOperatorName();

            //Create HttpClient
            AndroidHttpClient client = AndroidHttpClient.newInstance("ApnDefaults/0.1");
            HttpParams params = client.getParams();
            HttpProtocolParams.setContentCharset(params, "UTF-8");
            HttpConnectionParams.setConnectionTimeout(params, 1 * 1000); //Set timeout to wait for a connection.
            HttpConnectionParams.setSoTimeout(params, 1 * 1000); //Set timeout to wait for a response.
            try {
                StringBuffer uriString = new StringBuffer(REPORT_URL)
                        .append("?")
                                //Report the MMSC connection used.
                        .append("apnData=").append(URLEncoder.encode(apnData, "UTF-8"))
                                //SIM and Network data are reported to enable determining which
                                //parameters work under which circumstances.
                        .append("&simOperator=").append(URLEncoder.encode(simOperator, "UTF-8"))
                        .append("&simOperatorName=").append(URLEncoder.encode(simOperatorName, "UTF-8"))
                        .append("&simCountry=").append(URLEncoder.encode(tm.getSimCountryIso(), "UTF-8"))
                        .append("&networkOperator=").append(URLEncoder.encode(networkOperator, "UTF-8"))
                        .append("&networkOperatorName=").append(URLEncoder.encode(networkOperatorName, "UTF-8"))
                        .append("&networkCountry=").append(URLEncoder.encode(tm.getNetworkCountryIso(), "UTF-8"));

                URI uri = new URI(uriString.toString());

                //Send request
                client.execute(new HttpGet(uri));
                client.close();
            } catch (Exception e) {
            }
        }
    }

    /**
     * Parameters necessary to establish a connection to a MMSC.
     */
    public static class ApnParameters {
        private final String mmscUrl;
        private final String proxyAddress;
        private final Integer proxyPort;

        public ApnParameters(String mmscUrl, String proxyAddress, Integer proxyPort) {
            this.mmscUrl = mmscUrl;
            this.proxyAddress = proxyAddress;
            this.proxyPort = proxyPort;
        }

        public boolean isProxySet() {
            return proxyAddress != null && proxyAddress.trim().length() != 0;
        }

        public String getMmscUrl() {
            return mmscUrl;
        }

        public String getProxyAddress() {
            if (!isProxySet()) {
                return null;
            }

            return proxyAddress;
        }

        public Integer getProxyPort() {
            if (isProxySet() && proxyPort == null) {
                return 80;
            }

            return proxyPort;
        }
    }
}
