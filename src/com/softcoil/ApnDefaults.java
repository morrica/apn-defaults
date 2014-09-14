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

        /// (Report Count: 1, Last Report: 2014-09-12 19:33:20)
        put("310000||123456| ", new ApnParameters("http://mms.vzwreseller.com/servlets/mms", null, null));

        // (Report Count: 1, Last Report: 2014-09-07 03:57:29)
        put("310000||31000| ", new ApnParameters("http://mms.vzwreseller.com/servlets/mms", null, null));

        // (Report Count: 1, Last Report: 2014-09-07 02:09:35)
        put("31000||31000|", new ApnParameters("null", null, null));

        // (Report Count: 1, Last Report: 2014-09-08 18:24:09)
        put("310410||310410| ", new ApnParameters("http://mmscUrl.cingular.com/", "wireless.cingular.com", 80));

        // (Report Count: 1, Last Report: 2014-07-22 13:50:03)
        put("||| ", new ApnParameters("http://mms.celcom.net.my", "10.128.1.242", 8080));

        // DiGi (Report Count: 1, Last Report: 2014-08-17 04:50:21)
        put("50216|DiGi|50216| DiGi ", new ApnParameters("http://mms.digi.com.my/servlets/mms", "203.92.128.160", 80));

        // T-Mobile (Report Count: 1, Last Report: 2014-08-20 12:39:51)
        put("310260||310260| T-Mobile", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //1&1 (Report Count: 1, Last Report: 2014-09-05 16:14:27)
        put("26202|1&1|26202|1&1", new ApnParameters("http://139.7.24.1/servlets/mms", "139.7.29.17", 80));

        //3 (Report Count: 1, Last Report: 2014-09-12 14:53:42)
        put("23420||23420|3", new ApnParameters("http://mms.um.three.co.uk:10021/mmsc", "217.171.129.2", 8799));

        //3 (Report Count: 1, Last Report: 2014-09-11 13:53:09)
        put("24002|3|24002|3", new ApnParameters("http://mms.tre.se", "172.16.53.11", 8799));

        //3 (Report Count: 1, Last Report: 2014-07-24 10:38:38)
        put("27205||27205|3", new ApnParameters("http://mms.um.3ireland.ie:10021/mmsc", "mms.3ireland.ie", 8799));

        //3 (Report Count: 1, Last Report: 2014-09-13 09:53:21)
        put("51089|3|51089|3", new ApnParameters("http://mmsc.indosat.com", "10.19.19.19", 8080));

        //3 AT (Report Count: 1, Last Report: 2014-09-07 10:27:27)
        put("23205||23205|3 AT", new ApnParameters("http://mmsc", "213.94.78.133", 8799));

        //3 ITA (Report Count: 1, Last Report: 2014-08-04 12:59:37)
        put("22299||22299|3 ITA", new ApnParameters("http://10.216.59.240:10021/mmsc", "62.13.171.3", 8799));

        //310260 (Report Count: 1, Last Report: 2014-08-01 06:36:16)
        put("310260||310260|310260", new ApnParameters("http://metropcs.mmsmvno.com/mms/wapenc", null, null));

        //425 02 (Report Count: 1, Last Report: 2014-08-15 09:47:07)
        put("42502|Cellcom|42502|425 02", new ApnParameters("http://mms.cellcom.co.il", "vwapm2.ain.co.il", 8080));

        //A1 (Report Count: 1, Last Report: 2014-07-19 19:27:53)
        put("23201||23201|A1", new ApnParameters("http://mmsc.a1.net", "194.48.124.71", 8001));

        //airtel (Report Count: 1, Last Report: 2014-08-21 04:54:35)
        put("40402|airtel|40402|airtel", new ApnParameters("http://100.1.201.171:10021/mmsc", "100.1.201.172", 8799));

        //Airtel (Report Count: 1, Last Report: 2014-08-08 08:48:33)
        put("40410|airtel|40410|Airtel", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //airtel (Report Count: 1, Last Report: 2014-07-30 10:31:29)
        put("40445|Airtel|40445|airtel", new ApnParameters("http://100.1.201.171:10021/mmsc", "100.1.201.172", 8799));

        //Airtel (Report Count: 1, Last Report: 2014-07-17 22:35:12)
        put("40449|airtel|40449|Airtel", new ApnParameters("http://100.1.201.171:10021/mmsc", "100.1.201.172", 8799));

        //AirTel (Report Count: 1, Last Report: 2014-08-17 14:36:28)
        put("40493|airtel|40493|AirTel", new ApnParameters("http://100.1.201.171:10021/mmsc", "100.1.201.172", 8799));

        //airtel (Report Count: 1, Last Report: 2014-09-08 12:21:51)
        put("40494|airtel|40494|airtel", new ApnParameters("http://100.1.201.171:10021/mmsc", "100.1.201.172", 8799));

        //airtel (Report Count: 1, Last Report: 2014-08-23 08:25:28)
        put("40554|airtel|40554|airtel", new ApnParameters("http://100.1.201.171:10021/mmsc/", "100.1.201.172", 8799));

        //Airtel A.P. (Report Count: 1, Last Report: 2014-08-19 06:45:49)
        put("40449|airtel|40449|Airtel A.P.", new ApnParameters("http://100.1.201.171:10021/mmsc/", "100.1.201.172", 8799));

        //Airtel Mumbai (Report Count: 1, Last Report: 2014-08-16 03:56:54)
        put("40492|AirTel|40492|Airtel Mumbai", new ApnParameters("http://100.1.201.171:10021/mmsc/", "100.1.201.172", 8799));

        //Airtel NG (Report Count: 1, Last Report: 2014-09-09 15:46:58)
        put("62120|Airtel NG|62120|Airtel NG", new ApnParameters("http://10.199.212.8/servlets/mms", "10.199.212.2", 8080));

        //Airtel Rajasthan (Report Count: 1, Last Report: 2014-08-19 03:42:24)
        put("40470|airtel|40470|Airtel Rajasthan", new ApnParameters("http://100.1.201.171:10021/mmsc", "100.1.201.172", 8799));

        //Airtel T.N. (Report Count: 1, Last Report: 2014-09-13 11:13:49)
        put("40494|airtel|40494|Airtel T.N.", new ApnParameters("http://100.1.201.171:10021/mmsc", "100.1.201.172", 8799));

        //Airtel UP East (Report Count: 1, Last Report: 2014-06-23 17:48:42)
        put("40497|airtel|40554|Airtel UP East", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //AIRVOICE WIRELESS (Report Count: 1, Last Report: 2014-06-11 19:35:48)
        put("310410||310410|AIRVOICE WIRELESS", new ApnParameters("http://mmsc.cingular.com", "66.209.11.33", 80));

        //AIS (Report Count: 1, Last Report: 2014-08-02 17:06:44)
        put("52003|AIS|52001|AIS", new ApnParameters("http://mms.mobilelife.co.th", "203.170.229.34", 8080));

        //AIS (Report Count: 1, Last Report: 2014-09-07 02:07:22)
        put("52003|AIS|52003|AIS", new ApnParameters("http://mms.ais.co.th", "203.170.229.34", 8080));

        //amaysim (Report Count: 1, Last Report: 2014-09-13 02:16:27)
        put("50502|amaysim|50502|amaysim", new ApnParameters("http://mmsc.optus.com.au:8002/", "61.88.190.10", 8070));

        //ANTEL (Report Count: 1, Last Report: 2014-08-06 21:59:12)
        put("74801|Antel|74801|ANTEL", new ApnParameters("http://mmsc.mms.ancelutil.com.uy", "200.40.246.2", 3128));

        //AT T (Report Count: 1, Last Report: 2014-09-10 22:14:06)
        put("310410||310410|AT T", new ApnParameters("http://mms-tf.net", "mms3.tracfone.com", 80));

        //AT&T (Report Count: 1, Last Report: 2014-08-15 20:10:32)
        put("302370||310410|AT&T", new ApnParameters("http://mms.fido.ca", "mmsproxy.fido.ca", 80));

        //AT&T (Report Count: 1, Last Report: 2014-08-23 21:49:43)
        put("310150|Verizon|310410|AT&T", new ApnParameters("http://mmsc.aiowireless.net", "proxy.aiowireless.net", 80));

        //AT&T (Report Count: 1, Last Report: 2014-08-19 00:08:49)
        put("310260|T-Mobile|310260|AT&T", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //AT&T (Report Count: 1, Last Report: 2014-09-10 21:23:06)
        put("310260||310260|AT&T", new ApnParameters("http://metropcs.mmsmvno.com/mms/wapenc", null, null));

        //AT&T (Report Count: 1, Last Report: 2014-09-08 21:21:07)
        put("310410|AT&T|310410|AT&T", new ApnParameters("http://mms-tf.net", "mms3.tracfone.com", 80));

        //AT&T (Report Count: 1, Last Report: 2014-08-02 16:15:37)
        put("310410|Verizon|310410|AT&T", new ApnParameters("httt://mmsc.mobile.att.net", "proxy.mobile.att.net", 80));

        //AT&T (Report Count: 3, Last Report: 2014-09-14 01:34:02)
        put("310410||310410|AT&T", new ApnParameters("http://mmsc.mobile.att.net", "proxy.mobile.att.net", 80));

        //AT&T MicroCell (Report Count: 1, Last Report: 2014-09-11 18:23:24)
        put("310410||310410|AT&T MicroCell", new ApnParameters("http://mmsc.mobile.att.net", "proxy.mobile.att.net", 80));

        //Banglalink (Report Count: 1, Last Report: 2014-07-29 17:47:53)
        put("47003|Banglalink|47003|Banglalink", new ApnParameters("http://mmsc1:10021/mmsc/01", "10.10.55.34", 8799));

        //BASE (Report Count: 1, Last Report: 2014-08-22 07:58:59)
        put("20620|BASE|20620|BASE", new ApnParameters("http://mmsc.base.be", "217.72.235.1", 8080));

        //Beeline (Report Count: 1, Last Report: 2014-09-12 04:11:42)
        put("25099|Beeline|25099|Beeline", new ApnParameters("http://mms/", "192.168.94.23", 8080));

        //Bell (Report Count: 1, Last Report: 2014-09-12 19:47:02)
        put("302610||302610|Bell", new ApnParameters("http://mms.bell.ca/mms/wapenc", null, null));

        //BH Mobile (Report Count: 1, Last Report: 2014-06-19 08:06:51)
        put("21890|BH Mobile|21890|BH Mobile", new ApnParameters("http://mms.bhmobile.ba/cmmsc/post", "195.222.56.41", 8080));

        //Black Wireless (Report Count: 1, Last Report: 2014-08-20 21:30:14)
        put("310410||310410|Black Wireless", new ApnParameters("http://mmsc.cingular.com", "66.209.11.33", 80));

        //Boost Mobile (Report Count: 1, Last Report: 2014-05-28 19:27:08)
        put("310120|Sprint|310120|Boost Mobile", new ApnParameters("http://mm.myboostmobile.com", "68.28.31.7", 80));

        //Boost Mobile (Report Count: 1, Last Report: 2014-09-13 23:35:35)
        put("311870|Boost Mobile|311870|Boost Mobile", new ApnParameters("http://mm.myboostmobile.com", "68.28.31.7", 80));

        //Boost Mobile (Report Count: 1, Last Report: 2014-08-04 15:06:42)
        put("311870||31000|Boost Mobile", new ApnParameters("http://mm.myboostmobile.com", "68.28.31.7", 80));

        //Boost Mobile (Report Count: 1, Last Report: 2014-08-23 06:45:12)
        put("3118790|Boost Mobile|3118790|Boost Mobile", new ApnParameters("null", null, null));

        //Bouygtel (Report Count: 1, Last Report: 2014-07-25 22:34:08)
        put("20820|Bouygues Telecom|20820|Bouygtel", new ApnParameters("http://mms.bouyguestelecom.fr/mms/wapenc", "62.201.129.226", 8080));

        //Bouygues Telecom (Report Count: 1, Last Report: 2014-09-08 15:31:56)
        put("20820|Bouygues Telecom|20820|Bouygues Telecom", new ApnParameters("http://mms.bouyguestelecom.fr/mms/wapenc", "62.201.129.226", 8080));

        //BrightSpot (Report Count: 1, Last Report: 2014-08-05 08:20:02)
        put("310260||310260|BrightSpot", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //BYTEL (Report Count: 1, Last Report: 2014-09-08 18:05:53)
        put("20820|Bouygues Telecom|20820|BYTEL", new ApnParameters("http://mms.bouyguestelecom.fr/mms/wapenc", "62.201.129.226", 8080));

        //C Spire (Report Count: 1, Last Report: 2014-09-08 05:53:28)
        put("311230|C Spire |311230|C Spire", new ApnParameters("http://pix.cspire.com/servlets/mms", "66.175.144.91", 80));

        //C Spire (Report Count: 1, Last Report: 2014-09-10 00:58:55)
        put("311230|C-Spire|310120|C Spire", new ApnParameters("http://pix.cspire.com/servlets/mms", "66.175.144.91", 80));

        //CamGSM (Report Count: 1, Last Report: 2014-09-09 07:52:11)
        put("45601||45601|CamGSM ", new ApnParameters("http://mms.mobitel.com.kh/mmsc", "203.144.95.98", 3130));

        //CAN Rogers Wireless Inc. (Report Count: 1, Last Report: 2014-09-13 17:57:30)
        put("302720|ROGERS|302720|CAN Rogers Wireless Inc.", new ApnParameters("http://mms.gprs.rogers.com", "10.128.1.69", 80));

        //cell c (Report Count: 1, Last Report: 2014-09-11 10:33:53)
        put("65507| Cell C |65507|cell c", new ApnParameters("http://mms.cmobile.co.za", "196.31.116.250", 8080));

        //CellOne (Report Count: 1, Last Report: 2014-08-05 01:24:38)
        put("40458|CellOne|40458|CellOne", new ApnParameters("http://10.4.42.21:8002/", "10.4.42.15", 8080));

        //Cellular One (Report Count: 1, Last Report: 2014-07-09 18:28:36)
        put("310260||310260|Cellular One", new ApnParameters("http://metropcs.mmsmvno.com/mms/wapenc", null, null));

        //CELLULARONE (Report Count: 1, Last Report: 2014-09-06 16:52:46)
        put("311190||311190|CELLULARONE", new ApnParameters("http://mms.cellular1.net", "10.10.0.97", 9201));

        //CH (Report Count: 1, Last Report: 2014-08-03 05:04:33)
        put("42502|Cellcom|42502|CH", new ApnParameters("http://mms.cellcom.co.il", "vwapm2.ain.co.il", 8080));

        //Chameleon (Report Count: 1, Last Report: 2014-09-06 18:33:40)
        put("310000|Chameleon|310000|Chameleon", new ApnParameters("http://mms.sprintpcs.com", "68.28.31.7", 80));

        //China Mobile HK (Report Count: 1, Last Report: 2014-09-12 06:14:26)
        put("45412|PEOPLES|45412|China Mobile HK", new ApnParameters("http://mms.hk.chinamobile.com/mms", null, null));

        //CinBell USA (Report Count: 1, Last Report: 2014-09-08 21:40:55)
        put("310420||310260|CinBell USA", new ApnParameters("http://mms.gocbw.com:8088/mms", "216.68.79.202", 80));

        //Cincinnati Bell (Report Count: 1, Last Report: 2014-07-18 13:23:31)
        put("310420||310420|Cincinnati Bell", new ApnParameters("http://mms.gocbw.com:8088/mms", "216.68.79.202", 80));

        //Claro (Report Count: 1, Last Report: 2014-09-11 04:51:55)
        put("70401|Claro|70401|Claro", new ApnParameters("http://mms.ideasclaro.com:8002", "216.230.133.66", 8080));

        //Claro (Report Count: 1, Last Report: 2014-07-29 05:30:47)
        put("71610|Claro|71610|Claro", new ApnParameters("http://claro/servlets/mms", "192.168.231.30", 80));

        //Claro AR (Report Count: 1, Last Report: 2014-08-24 20:51:01)
        put("722310|Claro AR|722310|Claro AR", new ApnParameters("http://mms.ctimovil.com.ar", "170.51.255.240", 8080));

        //CMCC (Report Count: 1, Last Report: 2014-09-08 01:12:54)
        put("46000|CMCC|46000|CMCC", new ApnParameters("http://mmsc.monternet.com", "10.0.0.172", 80));

        //CMCC (Report Count: 1, Last Report: 2014-06-16 05:44:35)
        put("46002||46000|CMCC", new ApnParameters("http://mmsc.monternet.com", "10.0.0.172", 80));

        //CricKet (Report Count: 1, Last Report: 2014-09-06 04:07:55)
        put("310016|CricKet|123456|CricKet", new ApnParameters("http://mms.mycricket.com/servlets/mms", null, null));

        //CricKet (Report Count: 1, Last Report: 2014-09-12 18:43:55)
        put("310016|CricKet|31001|CricKet", new ApnParameters("http://mms.mycricket.com/servlets/mms", null, null));

        //CricKet (Report Count: 1, Last Report: 2014-09-12 00:45:26)
        put("310016|CricKet|310090|CricKet", new ApnParameters("http://mms.mycricket.com/servlets/mms", null, null));

        //Cricket (Report Count: 1, Last Report: 2014-09-13 02:25:52)
        put("310090|CricKet |31000|Cricket", new ApnParameters("http://mms.mycricket.com/servlets/mms", null, null));

        //Cricket (Report Count: 1, Last Report: 2014-06-17 02:30:43)
        put("310090|CricKet |31001|Cricket", new ApnParameters("http://mms.mycricket.com/servlets/mms", null, null));

        //Cricket (Report Count: 1, Last Report: 2014-09-13 16:34:06)
        put("310090|CricKet |310090|Cricket", new ApnParameters("http://mms.mycricket.com/servlets/mms", null, null));

        //cricket (Report Count: 1, Last Report: 2014-08-09 02:03:16)
        put("310150|Verizon|310410|cricket", new ApnParameters("http://mmsc.aiowireless.net", "proxy.aiowireless.net", 80));

        //cricket (Report Count: 1, Last Report: 2014-09-14 00:21:09)
        put("310150||310410|cricket", new ApnParameters("http://mmsc.aiowireless.net", "proxy.aiowireless.net", 80));

        //Cricket (Report Count: 1, Last Report: 2014-08-19 20:40:43)
        put("311230|C-Spire|311480|Cricket", new ApnParameters("http://mms.mycricket.com/servlets/mms", "wap.mycricket.com", 8080));

        //CricKet (Report Count: 1, Last Report: 2014-09-12 23:32:12)
        put("310090|CricKet |31000|CricKet ", new ApnParameters("http://mms.mycricket.com/servlets/mms", null, null));

        //Dialog (Report Count: 1, Last Report: 2014-07-01 08:59:15)
        put("41302|Dialog|41302|Dialog", new ApnParameters("http://mms.dialog.lk:3130/mmsc", "192.168.122.2", 8080));

        //Digital Roaming (Report Count: 1, Last Report: 2014-08-14 17:11:53)
        put("310120|Sprint|00000|Digital Roaming", new ApnParameters("http://mms.sprintpcs.com", "68.28.31.7", 80));

        //Digital Roaming (Report Count: 1, Last Report: 2014-08-10 00:34:12)
        put("310120|Sprint|31000|Digital Roaming", new ApnParameters("http://mms.sprintpcs.com", "68.28.31.7", 80));

        //Digital Roaming (Report Count: 1, Last Report: 2014-09-08 22:11:48)
        put("310120|Sprint|31070|Digital Roaming", new ApnParameters("http://mms.sprintpcs.com", "68.28.31.7", 80));

        //DTAC (Report Count: 1, Last Report: 2014-08-17 03:54:33)
        put("52005|DTAC|52005|DTAC", new ApnParameters("http://mms2.dtac.co.th:8002/", "10.10.10.10", 8080));

        //DTAC (Report Count: 1, Last Report: 2014-09-08 15:04:14)
        put("52005||52005|DTAC", new ApnParameters("http://mms2.dtac.co.th:8002", "10.10.10.10", 8080));

        //DTAC (Report Count: 1, Last Report: 2014-07-11 00:29:27)
        put("52005||52018|DTAC", new ApnParameters("http://mms.dtac.co.th:8002", "203.155.200.133", 8080));

        //dtac (Report Count: 1, Last Report: 2014-09-11 05:28:27)
        put("52005||52018|dtac", new ApnParameters("http://mms2.dtac.co.th:8002", "10.10.10.10", 8080));

        //DTAC (Report Count: 1, Last Report: 2014-07-05 09:49:18)
        put("52018||52018|DTAC", new ApnParameters("http://mms.dtac.co.th:8002", "203.155.200.133", 8080));

        //du (Report Count: 1, Last Report: 2014-07-31 12:48:16)
        put("42403|du|42403|du", new ApnParameters("http://mms.du.ae", "10.19.18.4", 8080));

        //EE (Report Count: 1, Last Report: 2014-09-07 08:58:20)
        put("23430||23430|EE", new ApnParameters("http://mms/", "149.254.201.135", 8080));

        //EE (Report Count: 1, Last Report: 2014-09-13 16:11:00)
        put("23430||23433|EE", new ApnParameters("http://mms/", "149.254.201.135", 8080));

        //EE (Report Count: 1, Last Report: 2014-09-12 18:17:23)
        put("23433|EE|23430|EE", new ApnParameters("http://mms/", "149.254.201.135", 8080));

        //EE (Report Count: 1, Last Report: 2014-08-10 10:31:29)
        put("23433|EE|23433|EE", new ApnParameters("http://mms/", "149.254.201.135", 8080));

        //ETISALAT (Report Count: 1, Last Report: 2014-07-20 08:22:16)
        put("62160|etisalat|62160|ETISALAT", new ApnParameters("http://10.71.170.30:38090/was", "10.71.170.5", 8080));

        //Extended Network (Report Count: 1, Last Report: 2014-07-02 20:49:51)
        put("311480|Verizon Wireless|31000|Extended Network", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Extended Network (Report Count: 1, Last Report: 2014-08-26 02:12:16)
        put("311480|Verizon Wireless|3107|Extended Network", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Extended Network (Report Count: 1, Last Report: 2014-08-13 00:28:26)
        put("311480|Verizon Wireless|31135|Extended Network", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Extended Network (Report Count: 1, Last Report: 2014-09-12 22:24:47)
        put("311480|Verizon Wireless|311480|Extended Network", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Extended Network (Report Count: 1, Last Report: 2014-09-13 22:11:03)
        put("311480|Verizon|311480|Extended Network", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //F SFR (Report Count: 1, Last Report: 2014-07-12 14:41:09)
        put("20810|LeclercMobile|20810|F SFR", new ApnParameters("http://mms66", "10.143.156.9", 8080));

        //F SFR (Report Count: 1, Last Report: 2014-09-12 12:55:06)
        put("20810||20810|F SFR", new ApnParameters("http://mms1", "10.151.0.1", 8080));

        //F-Bouygues Telecom (Report Count: 1, Last Report: 2014-08-22 19:21:29)
        put("20820|Bouygues Telecom|20820|F-Bouygues Telecom", new ApnParameters("http://mms.bouyguestelecom.fr/mms/wapenc", "62.201.129.226", 8080));

        //Family Mobile (Report Count: 1, Last Report: 2014-09-13 02:42:10)
        put("310260||310260|Family Mobile", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //Fido (Report Count: 1, Last Report: 2014-09-13 11:00:57)
        put("302370||302720|Fido", new ApnParameters("http://mms.fido.ca", "mmsproxy.fido.ca", 80));

        //Free (Report Count: 1, Last Report: 2014-09-12 15:19:50)
        put("20815|Free|20801|Free", new ApnParameters("http://mms.free.fr/", null, null));

        //Free (Report Count: 1, Last Report: 2014-09-11 12:25:43)
        put("20815|Free|20815|Free", new ApnParameters("http://mms.free.fr", null, null));

        //GCI (Report Count: 1, Last Report: 2014-06-22 16:50:34)
        put("311370|GCI|311370|GCI", new ApnParameters("http://mmsc.gci.csky.us:6672", "209.4.229.92", 9201));

        //GoSmart (Report Count: 1, Last Report: 2014-09-12 15:37:27)
        put("310260||310260|GoSmart", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //GrameenPhone (Report Count: 1, Last Report: 2014-09-08 01:56:52)
        put("47001||47001|GrameenPhone", new ApnParameters("http://mms.gpsurf.net/servlets/mms", "10.128.1.2", 8080));

        //H2O (Report Count: 1, Last Report: 2014-09-10 23:29:33)
        put("310410||310410|H2O", new ApnParameters("http://mmsc.cingular.com", "66.209.11.33", 80));

        //Home (Report Count: 1, Last Report: 2014-08-04 00:41:02)
        put("310000|Defalut|31000|Home", new ApnParameters("http://mms.mobipcs.com", null, null));

        //Home (Report Count: 1, Last Report: 2014-06-03 18:00:52)
        put("310000|Default|310000|Home", new ApnParameters("http://pix.cspire.com", null, null));

        //HOME (Report Count: 1, Last Report: 2014-09-12 15:34:00)
        put("310260||310260|HOME", new ApnParameters("http://mms.tracfone.com", null, null));

        //HOME (Report Count: 1, Last Report: 2014-08-19 18:35:03)
        put("310410|Verizon|310410|HOME", new ApnParameters("http://mms-tf.net", "mms3.tracfone.com", 80));

        //HOME (Report Count: 1, Last Report: 2014-09-13 17:34:03)
        put("310410||310410|HOME", new ApnParameters("http://mms-tf.net", "mms3.tracfone.com", 80));

        //Home (Report Count: 1, Last Report: 2014-09-05 03:58:48)
        put("311230|Default|311230|Home", new ApnParameters("http://pix.cspire.com/servlets/mms", null, null));

        //HTC (Report Count: 1, Last Report: 2014-09-13 19:58:54)
        put("000000|HTC|000000|HTC", new ApnParameters("null", null, null));

        //HTC (Report Count: 1, Last Report: 2014-09-12 04:00:04)
        put("000000|HTC|310120|HTC", new ApnParameters("null", null, null));

        //i wireless (Report Count: 1, Last Report: 2014-08-08 00:34:05)
        put("310770||310770|i wireless", new ApnParameters("http://mmsc.iwireless.dataonair.net:6672", "209.4.229.31", 9401));

        //IDEA (Report Count: 1, Last Report: 2014-07-18 07:26:27)
        put("40414|Spice|40414|IDEA", new ApnParameters("http://10.4.42.21:8002/", "10.4.42.15", 8080));

        //IDEA (Report Count: 1, Last Report: 2014-09-12 14:59:35)
        put("40422|!dea|40422|IDEA", new ApnParameters("http://10.4.42.21:8002/", "10.4.42.15", 8080));

        //IDEA (Report Count: 1, Last Report: 2014-08-10 09:42:13)
        put("40478|!dea|40478|IDEA", new ApnParameters("http://10.4.42.21:8002/", "10.4.42.15", 8080));

        //IDEA (Report Count: 1, Last Report: 2014-08-19 07:22:09)
        put("40487|!dea|40487|IDEA", new ApnParameters("http://100.1.201.171:10021/mmsc", "100.1.201.172", 8799));

        //Idea (Report Count: 1, Last Report: 2014-08-13 06:26:53)
        put("405799|Idea|40492|Idea", new ApnParameters("http://10.4.42.21:8002/", "10.4.42.15", 8080));

        //IND airtel (Report Count: 1, Last Report: 2014-08-18 07:08:52)
        put("40449|airtel|40449|IND airtel", new ApnParameters("http://100.1.201.171:10021/mmsc", "100.1.201.172", 8799));

        //IND XL (Report Count: 1, Last Report: 2014-09-05 08:18:22)
        put("51011|XL Axiata|51011|IND XL", new ApnParameters("http://mmc.xl.net.id/servlets/mms", "202.152.240.50", 8080));

        //IND-TELKOMSEL (Report Count: 1, Last Report: 2014-09-08 10:19:32)
        put("51010||51010|IND-TELKOMSEL", new ApnParameters("http://mms.telkomsel.com", "10.1.89.150", 8000));

        //INDOSAT (Report Count: 1, Last Report: 2014-06-08 01:35:13)
        put("51001|INDOSAT|51001| ", new ApnParameters("http://mmsc.indosat.com", "10.19.19.19", 8080));

        //INDOSAT (Report Count: 1, Last Report: 2014-09-10 15:47:10)
        put("51001|INDOSAT|51001|INDOSAT", new ApnParameters("http://mmsc.indosat.com", "10.19.19.19", 8080));

        //Iowa Wireless USA (Report Count: 1, Last Report: 2014-08-05 17:20:12)
        put("310260||310770|Iowa Wireless USA", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //iWireless (Report Count: 1, Last Report: 2014-08-06 23:11:47)
        put("310770||310770|iWireless", new ApnParameters("http://mmsc.iwireless.dataonair.net:6672", "209.4.229.31", 9201));

        //IWS (Report Count: 1, Last Report: 2014-07-12 16:46:33)
        put("310770||310770|IWS", new ApnParameters("http://mmsc.iwireless.dataonair.net:6672", "209.4.229.31", 9401));

        //Koodo (Report Count: 1, Last Report: 2014-09-13 21:32:27)
        put("302220||302220|Koodo", new ApnParameters("http://aliasredirect.net/proxy/koodo/mmsc", "74.49.0.18", 80));

        //KT, VIVA (Report Count: 1, Last Report: 2014-08-20 15:31:16)
        put("41904|VIVA|41904|KT, VIVA", new ApnParameters("http://172.16.128.80:38090/was", "172.16.128.228", 8080));

        //La Poste Mobile (Report Count: 1, Last Report: 2014-08-05 11:06:32)
        put("20810|La Poste Mobile|20810|La Poste Mobile", new ApnParameters("http://mmsdebitel", "10.143.156.3", 8080));

        //life:) (Report Count: 1, Last Report: 2014-06-24 20:08:45)
        put("25506|life:)|25506|life:)", new ApnParameters("http://mms.life.com.ua/cmmsc/post", "212.58.162.230", 8080));

        //LIME (Report Count: 1, Last Report: 2014-08-24 00:54:12)
        put("338180|LIME|338180|LIME", new ApnParameters("http://mmsc", "10.20.5.34", 8799));

        //Lycamobile (Report Count: 1, Last Report: 2014-08-23 15:03:33)
        put("310260|Lycamobile|310260|Lycamobile", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //MegaFon (Report Count: 1, Last Report: 2014-09-11 04:03:35)
        put("25002|MegaFon|25002|MegaFon", new ApnParameters("http://mmsc:8002", "10.10.10.10", 8080));

        //MetroPCS (Report Count: 1, Last Report: 2014-06-25 16:50:47)
        put("25001|MTS|310260|MetroPCS", new ApnParameters("http://metropcs.mmsmvno.com/mms/wapenc", null, null));

        //MetroPCS (Report Count: 1, Last Report: 2014-09-14 00:22:29)
        put("310260||310260|MetroPCS", new ApnParameters("http://metropcs.mmsmvno.com/mms/wapenc", null, null));

        //MetroPCS (Report Count: 1, Last Report: 2014-08-25 21:46:48)
        put("||310260|MetroPCS", new ApnParameters("http://metropcs.mmsmvno.com/mms/wapenc", null, null));

        //MOBILICITY (Report Count: 1, Last Report: 2014-09-12 02:33:46)
        put("302320||302320|MOBILICITY", new ApnParameters("hhtp://mms.mobilicity.net", "10.100.3.4", 8080));

        //Mobilicity (Report Count: 1, Last Report: 2014-08-07 05:20:11)
        put("302320||302320|Mobilicity", new ApnParameters("http://mms.mobilicity.net", "10.100.3.4", 8080));

        //Mobistar (Report Count: 1, Last Report: 2014-08-14 20:32:29)
        put("20610||20610|Mobistar", new ApnParameters("http://mmsc.mobistar.be", "212.65.63.143", 8080));

        //MOBITEL (Report Count: 1, Last Report: 2014-08-16 09:26:45)
        put("29341|Mobitel|29341|MOBITEL", new ApnParameters("http://mms.mobitel.si/servlets/mms", "213.229.249.40", 8080));

        //Mobitel (Report Count: 1, Last Report: 2014-08-25 16:21:44)
        put("41301|Mobitel|41301|Mobitel", new ApnParameters("http://mms.dialog.lk:3130/mmsc", "192.168.122.2", 8080));

        //Movistar (Report Count: 1, Last Report: 2014-09-08 18:46:48)
        put("26201|Telekom.de|21407|Movistar", new ApnParameters("http://mms.t-mobile.de/servlets/mms", "172.28.23.131", 8008));

        //movistar (Report Count: 1, Last Report: 2014-08-17 02:23:50)
        put("334030|Movistar|33403|movistar", new ApnParameters("http://mms.movistar.mx", "10.2.20.1", 80));

        //movistar (Report Count: 1, Last Report: 2014-08-14 14:38:17)
        put("71606|movistar|71606|movistar", new ApnParameters("http://mmsc.telefonicamovistar.com.pe:8088/mms/", "200.4.196.118", 8080));

        //Movistar (Report Count: 1, Last Report: 2014-07-03 18:01:58)
        put("72207|Movistar|72207|Movistar", new ApnParameters("http://mms.movistar.com.ar", "200.68.32.239", 8080));

        //movistar (Report Count: 1, Last Report: 2014-09-05 12:34:41)
        put("73404|movistar|73404|movistar", new ApnParameters("http://mms.movistar.com.ve:8088/mms", "200.35.64.73", 9001));

        //mt:s (Report Count: 1, Last Report: 2014-07-21 14:27:39)
        put("22003|mt:s|22003|mt:s", new ApnParameters("http://mms.mts064.telekom.rs/mms/wapenc", "172.17.85.131", 8080));

        //MTN NG (Report Count: 1, Last Report: 2014-09-09 13:36:13)
        put("62130||62130|MTN NG", new ApnParameters("http://10.199.212.8/servlets/mms", "10.199.212.2", 8080));

        //MTN-SA (Report Count: 1, Last Report: 2014-09-12 13:04:51)
        put("65510||65510|MTN-SA", new ApnParameters("http://mms.mtn.co.za/mms/wapenc", "196.11.240.241", 8080));

        //MTS (Report Count: 1, Last Report: 2014-07-06 04:49:13)
        put("302370|MTS|302720|MTS", new ApnParameters("http://mmsc2.mts.net/", "209.4.229.90", 9401));

        //MTS (Report Count: 1, Last Report: 2014-09-12 15:17:23)
        put("302660|MTS|302720|MTS", new ApnParameters("http://mmsc2.mts.net/", "wapgw1.mts.net", 9201));

        //MTS RUS (Report Count: 1, Last Report: 2014-09-11 11:19:46)
        put("25001|MTS RUS|25001|MTS RUS", new ApnParameters("http://mmsc", "192.168.192.192", 8080));

        //MTS-RUS (Report Count: 1, Last Report: 2014-08-02 16:23:34)
        put("25001|MTS RUS|25001|MTS-RUS", new ApnParameters("http://mmsc", "10.10.30.60", 8080));

        //MY CELCOM (Report Count: 1, Last Report: 2014-09-11 12:04:43)
        put("50219|MERCHANTRADE|50219|MY CELCOM", new ApnParameters("http://mms.celcom.net.my", "10.128.1.242", 8080));

        //MY CELCOM (Report Count: 1, Last Report: 2014-08-21 12:36:09)
        put("50219|TuneTalk|50219|MY CELCOM", new ApnParameters("http://mms.celcom.net.my", "10.128.1.242", 8080));

        //MY CELCOM (Report Count: 1, Last Report: 2014-09-11 15:39:44)
        put("50219||50219|MY CELCOM", new ApnParameters("http://mms.celcom.net.my", "10.128.1.242", 8080));

        //MY DiGi (Report Count: 1, Last Report: 2014-09-08 01:14:01)
        put("50216|DiGi|50216|MY DiGi", new ApnParameters("http://mms.digi.com.my/servlets/mms", "203.92.128.160", 80));

        //MY MAXIS (Report Count: 1, Last Report: 2014-09-07 00:56:06)
        put("50212||50212|MY MAXIS", new ApnParameters("http://172.16.74.100:10021/mmsc", "202.75.133.49", 80));

        //My Network (Report Count: 1, Last Report: 2014-09-11 00:15:54)
        put("310410||310410|My Network", new ApnParameters("http://mmsc.cingular.com", "proxy.mvno.ccmobileweb.com", 80));

        //NECCI (Report Count: 1, Last Report: 2014-08-02 01:37:50)
        put("310450||310450|NECCI", new ApnParameters("http://mms.viaero.com", "10.168.3.23", 9401));

        //NetCom (Report Count: 1, Last Report: 2014-08-19 17:16:29)
        put("24202|NetCom|24202|NetCom", new ApnParameters("http://mms/", "193.209.134.133", 8080));

        //Network Extender (Report Count: 1, Last Report: 2014-07-29 12:05:18)
        put("310002|Verizon Wireless|31000|Network Extender", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Network Extender (Report Count: 1, Last Report: 2014-08-16 22:15:24)
        put("311480|Verizon|311480|Network Extender", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Nextel (Report Count: 1, Last Report: 2014-09-09 22:51:57)
        put("72439|Nextel|72439|Nextel", new ApnParameters("http://3gmms.nextel3g.net.br", "129.192.129.104", 8080));

        //nTelos (Report Count: 1, Last Report: 2014-07-22 14:15:19)
        put("310470|nTelos|310000|nTelos", new ApnParameters("http://mms.ntelospcs.net", null, null));

        //o2 - de (Report Count: 1, Last Report: 2014-08-24 21:28:16)
        put("26207|o2 - de|26207|o2 - de", new ApnParameters("http://10.81.0.7:8002/", "82.113.100.5", 8080));

        //O2 - UK (Report Count: 1, Last Report: 2014-08-09 16:43:57)
        put("23410|TESCO|23410|O2 - UK", new ApnParameters("http://mmsc.mms.o2.co.uk:8002", "193.113.200.195", 8080));

        //O2 - UK (Report Count: 1, Last Report: 2014-09-07 08:58:55)
        put("23410|giffgaff|23410|O2 - UK", new ApnParameters("http://mmsc.mediamessaging.co.uk:8002", "193.113.200.195", 8080));

        //O2 - UK (Report Count: 1, Last Report: 2014-09-09 18:54:10)
        put("23410||23410|O2 - UK", new ApnParameters("http://mmsc.mms.o2.co.uk:8002", "82.132.254.1", 8080));

        //Oi (Report Count: 1, Last Report: 2014-07-15 13:51:44)
        put("72431|Oi|72431|Oi", new ApnParameters("http://200.222.42.204:8002", "192.168.10.50", 3128));

        //OJSC VimpelCom (Report Count: 1, Last Report: 2014-06-27 12:45:11)
        put("25099|Beeline|25099|OJSC VimpelCom", new ApnParameters("http://mms/", "192.168.94.23", 8080));

        //olleh (Report Count: 1, Last Report: 2014-08-25 03:48:25)
        put("45008||45008|olleh", new ApnParameters("http://mmsc.ktfwing.com:9082", null, null));

        //Ooredoo (Report Count: 1, Last Report: 2014-07-31 05:01:17)
        put("41903|KT WATANIYA|41903|Ooredoo", new ApnParameters("http://action.wataniya.com", "194.126.53.64", 8080));

        //Ooredoo (Report Count: 1, Last Report: 2014-09-07 20:49:42)
        put("41903|Ooredoo|41903|Ooredoo", new ApnParameters("http://action.wataniya.com", "194.126.53.64", 8080));

        //Optus (Report Count: 1, Last Report: 2014-09-13 22:02:30)
        put("50502|TPG|50502|Optus", new ApnParameters("http://mmsc.optus.com.au:8002/", "61.88.190.10", 8070));

        //Orange (Report Count: 1, Last Report: 2014-06-12 15:36:53)
        put("21403|Orange|21403|Orange", new ApnParameters("http://mms.orange.es", "172.22.188.25", 8080));

        //Orange (Report Count: 1, Last Report: 2014-08-24 16:56:43)
        put("26003|Orange|26003|Orange", new ApnParameters("http://mms.orange.pl", "192.168.6.104", 8080));

        //Orange (Report Count: 1, Last Report: 2014-08-24 21:56:41)
        put("26003|nju|26003|Orange", new ApnParameters("http://mms.orange.pl", "192.168.6.104", 8080));

        //ORANGE (Report Count: 1, Last Report: 2014-09-07 13:28:18)
        put("42501|orange|42501|ORANGE", new ApnParameters("http://192.168.220.15/servlets/mms", null, null));

        //Orange F (Report Count: 1, Last Report: 2014-09-09 17:13:24)
        put("20801|Orange F|20801|Orange F", new ApnParameters("http://mms.orange.fr", "192.168.10.200", 8080));

        //ORANGE IL (Report Count: 1, Last Report: 2014-07-21 18:06:28)
        put("42501|orange|42501|ORANGE IL", new ApnParameters("http://192.168.220.15/servlets/mms", null, null));

        //PC mobile (Report Count: 1, Last Report: 2014-08-23 20:20:04)
        put("302220||302220|PC mobile", new ApnParameters("http://aliasredirect.net/proxy/mb/mmsc", "74.49.0.18", 80));

        //Pelephone (Report Count: 1, Last Report: 2014-08-19 14:05:59)
        put("42503|Pelephone|42503|Pelephone", new ApnParameters("http://mmsu.pelephone.net.il", "10.170.9.54", 9093));

        //Plus (Report Count: 1, Last Report: 2014-08-24 16:47:06)
        put("26001||26001|Plus", new ApnParameters("http://mms.plusgsm.pl:8002", "212.2.96.16", 8080));

        //Preferred System (Report Count: 1, Last Report: 2014-07-19 12:33:00)
        put("310005|Verizon Wireless|311480|Preferred System", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //PrixTel (Report Count: 1, Last Report: 2014-09-09 14:42:45)
        put("20810|PrixTel|20810|PrixTel", new ApnParameters("http://mms1", "10.151.0.1", 8080));

        //PTEL MOBILE (Report Count: 1, Last Report: 2014-07-09 12:16:57)
        put("310260||310260|PTEL MOBILE", new ApnParameters("http://mms.tracfone.com", null, null));

        //Public Mobile (Report Count: 1, Last Report: 2014-09-11 12:15:26)
        put("302220|TELUS@|302220|Public Mobile ", new ApnParameters("http://aliasredirect.net/proxy/mmsc", "74.49.0.18", 80));

        //Public Mobile (Report Count: 1, Last Report: 2014-09-11 15:31:56)
        put("302220||302220|Public Mobile ", new ApnParameters("http://aliasredirect.net/proxy/mb/mmsc", "74.49.0.18", 80));

        //Red Pocket (Report Count: 1, Last Report: 2014-05-30 03:41:38)
        put("310410|AT&T@|310410|Red Pocket", new ApnParameters("http://mmsc.cingular.com", "66.209.11.33", 80));

        //Red Pocket (Report Count: 1, Last Report: 2014-08-24 01:17:52)
        put("310410|Red Pocket|310410|Red Pocket", new ApnParameters("http://mmsc.cingular.com", "66.209.11.33", 80));

        //Red Pocket (Report Count: 1, Last Report: 2014-09-06 17:42:37)
        put("310410||310410|Red Pocket", new ApnParameters("http://mmsc.cingular.com", "66.209.11.33", 80));

        //Republic (Report Count: 1, Last Report: 2014-09-07 18:01:33)
        put("310000|Motorola|310000|Republic", new ApnParameters("http://127.0.0.1:18181", null, null));

        //RO ORANGE (Report Count: 1, Last Report: 2014-09-09 10:09:19)
        put("22610|orange|22610|RO ORANGE", new ApnParameters("http://wap.mms.orange.ro:8002", "62.217.247.252", 8799));

        //Roaming (Report Count: 1, Last Report: 2014-07-27 02:42:13)
        put("310000|Verizon|311480|Roaming", new ApnParameters("http://mms.ekn.com", null, null));

        //Roaming (Report Count: 1, Last Report: 2014-09-06 14:34:33)
        put("311580|US Cellular|311580|Roaming", new ApnParameters("http://mmsc1.uscc.net/mmsc/MMS", null, null));

        //Roaming Indicator Off (Report Count: 1, Last Report: 2014-08-23 16:03:36)
        put("310120|Sprint|310120|Roaming Indicator Off", new ApnParameters("http://mms.sprintpcs.com", "68.28.31.7", 80));

        //Roaming Indicator Off (Report Count: 1, Last Report: 2014-09-13 13:59:32)
        put("311480|Verizon Wireless|31000|Roaming Indicator Off", new ApnParameters("null", null, null));

        //ROGERS (Report Count: 1, Last Report: 2014-09-13 02:06:44)
        put("302720|ROGERS|302720|ROGERS", new ApnParameters("http://mms.gprs.rogers.com", "mmsproxy.rogers.com", 80));

        //S COMVIQ (Report Count: 1, Last Report: 2014-09-12 07:20:44)
        put("24007|Comviq|24007|S COMVIQ", new ApnParameters("http://mmsc.tele2.se", "130.244.202.30", 8080));

        //Samsung (Report Count: 1, Last Report: 2014-09-09 19:23:41)
        put("310000|Samsung|310000|Samsung", new ApnParameters("http://mm.myboostmobile.com", "68.28.31.7", 80));

        //SaskTel (Report Count: 1, Last Report: 2014-09-12 03:07:28)
        put("302780||302780|SaskTel", new ApnParameters("http://mms.sasktel.com/", "mig.sasktel.com", 80));

        //SFR (Report Count: 1, Last Report: 2014-08-24 17:05:01)
        put("20810|PrixTel|20810|SFR", new ApnParameters("http://mms1", "10.151.0.1", 8080));

        //SFR (Report Count: 1, Last Report: 2014-07-28 20:10:03)
        put("20810||20810|SFR", new ApnParameters("http://mms1", "10.151.0.1", 8080));

        //SFR REUNION (Report Count: 1, Last Report: 2014-09-13 04:56:42)
        put("64710||64710|SFR REUNION", new ApnParameters("http://mms", "10.0.224.145", 8080));

        //Si.mobil (Report Count: 1, Last Report: 2014-07-08 00:08:15)
        put("29340|SIMOBIL|29340|Si.mobil", new ApnParameters("http://mmc/", "80.95.224.46", 9201));

        //Si.mobil (Report Count: 1, Last Report: 2014-07-29 17:41:27)
        put("29340||29340|Si.mobil", new ApnParameters("http://mmc", "80.95.224.46", 9201));

        //Simple Mobile (Report Count: 1, Last Report: 2014-09-12 02:54:35)
        put("310260|Simple Mobile|310260|Simple Mobile", new ApnParameters("http://smpl.mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //Simple Mobile (Report Count: 1, Last Report: 2014-09-12 18:31:55)
        put("310260|Verizon|310260|Simple Mobile", new ApnParameters("http://smpl.mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //Simple Mobile (Report Count: 1, Last Report: 2014-09-13 17:42:15)
        put("310260||310260|Simple Mobile", new ApnParameters("http://smpl.mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //SingTel (Report Count: 1, Last Report: 2014-09-06 13:14:22)
        put("52501|SingTel|52501|SingTel", new ApnParameters("http://mms.singtel.com:10021/mmsc", "165.21.42.84", 8080));

        //SKTelecom (Report Count: 1, Last Report: 2014-09-11 07:31:25)
        put("45005|SKTelecom|45005|SKTelecom", new ApnParameters("http://omms.nate.com:9082/oma_mms", "smart.nate.com", 9093));

        //SMART (Report Count: 1, Last Report: 2014-08-20 00:47:21)
        put("51503|SMART Buddy|51503|SMART", new ApnParameters("http://10.102.61.238:8002", "10.102.61.46", 8080));

        //SMART (Report Count: 1, Last Report: 2014-09-11 08:40:17)
        put("51503|SMART Prepaid|51503|SMART", new ApnParameters("http://10.102.61.238:8002", "10.102.61.46", 8080));

        //smartfren (Report Count: 1, Last Report: 2014-07-25 21:23:26)
        put("51009|smartfren|51009|smartfren", new ApnParameters("http://10.17.93.103:8080", "10.17.27.250", 8080));

        //SmarToneVodafone (Report Count: 1, Last Report: 2014-06-24 09:40:23)
        put("45406||45406|SmarToneVodafone", new ApnParameters("http://mms.smartone-vodafone.com/server", "10.9.9.9", 8080));

        //SoftBank (Report Count: 1, Last Report: 2014-09-11 10:46:28)
        put("44020|SoftBank|44020|SoftBank", new ApnParameters("http://mms-s", "andmms.plusacs.ne.jp", 8080));

        //Solavei (Report Count: 1, Last Report: 2014-09-10 20:07:49)
        put("310260||310260|Solavei", new ApnParameters("http://solavei.mmsmvno.com/mms/wapenc", null, null));

        //Spark NZ (Report Count: 1, Last Report: 2014-08-25 04:58:53)
        put("53005|Telecom NZ|53005|Spark NZ", new ApnParameters("http://lsmmsc.xtra.co.nz", "210.55.11.73", 80));

        //Spot Mobile (Report Count: 1, Last Report: 2014-07-07 03:28:35)
        put("310260||310260|Spot Mobile", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //Sprint (Report Count: 1, Last Report: 2014-08-25 15:09:23)
        put("310120|Sprint|00000|Sprint", new ApnParameters("http://mms.sprintpcs.com", "68.28.31.7", 80));

        //Sprint (Report Count: 1, Last Report: 2014-07-20 15:40:09)
        put("310120|Sprint|123456|Sprint", new ApnParameters("http://mms.sprintpcs.com", "68.28.31.7", 80));

        //Sprint (Report Count: 1, Last Report: 2014-09-13 23:44:15)
        put("310120|Sprint|31000| ", new ApnParameters("http://mms.plspictures.com", "68.28.31.7", -1));

        //Sprint (Report Count: 1, Last Report: 2014-09-13 09:59:44)
        put("310120|Sprint|31000|Sprint", new ApnParameters("http://mms.sprintpcs.com", "68.28.31.7", 80));

        //Sprint (Report Count: 1, Last Report: 2014-07-30 01:49:34)
        put("310120|Sprint|310120| ", new ApnParameters("http://mms.plspictures.com", "68.28.31.7", 80));

        //Sprint (Report Count: 1, Last Report: 2014-09-13 22:38:31)
        put("310120|Sprint|310120|Sprint", new ApnParameters("http://mms.sprintpcs.com", "68.28.31.7", 80));

        //Sprint (Report Count: 1, Last Report: 2014-09-07 01:23:21)
        put("310120|Sprint|31070|Sprint", new ApnParameters("http://mms.sprintpcs.com", "68.28.31.7", 80));

        //Sprint (Report Count: 1, Last Report: 2014-08-17 15:01:39)
        put("310120|Sprint|311480|Sprint", new ApnParameters("http://mms.sprintpcs.com", "68.28.31.7", 80));

        //Sprint (Report Count: 1, Last Report: 2014-09-12 18:08:16)
        put("310120|Sprint|311870|Sprint", new ApnParameters("http://mms.sprintpcs.com", "68.28.31.7", 80));

        //Sprint (Report Count: 1, Last Report: 2014-08-10 03:57:59)
        put("31012|Sprint|31000|Sprint", new ApnParameters("http://mms.sprintpcs.com", "68.28.31.7", 80));

        //Sprint (Report Count: 1, Last Report: 2014-09-11 04:20:16)
        put("31012|Sprint|310120|Sprint", new ApnParameters("http://mms.sprintpcs.com", "68.28.31.7", 80));

        //Sprint (Report Count: 1, Last Report: 2014-09-08 17:37:31)
        put("|Sprint|31000|Sprint", new ApnParameters("http://mms.sprintpcs.com", "68.28.31.7", 80));

        //Sprint (Report Count: 1, Last Report: 2014-08-09 02:31:59)
        put("|Sprint|310120|Sprint", new ApnParameters("http://mms.sprintpcs.com", "68.28.31.7", 80));

        //SRI DIALOG (Report Count: 1, Last Report: 2014-09-13 03:03:06)
        put("41302|Dialog|41302|SRI DIALOG", new ApnParameters("http://mms.dialog.lk:3130/mmsc", null, null));

        //Sweden 3G (Report Count: 1, Last Report: 2014-07-23 11:38:49)
        put("24205|One Call|24005|Sweden 3G", new ApnParameters("http://mms.nwn.no", "188.149.250.10", 80));

        //Swedfone (Report Count: 1, Last Report: 2014-09-08 04:04:01)
        put("24002|Swedfone|24002|Swedfone", new ApnParameters("http://mms.tre.se", "172.16.53.11", 8799));

        //Swisscom (Report Count: 1, Last Report: 2014-08-18 01:21:12)
        put("22801|Swisscom|22801|Swisscom", new ApnParameters("http://mms.natel.ch:8079", "192.168.210.2", 8080));

        //T - Mobile (Report Count: 1, Last Report: 2014-09-06 18:21:41)
        put("310260|T-Mobile|310260|T - Mobile", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //T - Mobile (Report Count: 1, Last Report: 2014-09-10 16:40:55)
        put("310260||310260|T - Mobile", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //T-Mobile (Report Count: 1, Last Report: 2014-08-25 10:46:56)
        put("23430||23430|T-Mobile", new ApnParameters("http://mms/", "149.254.201.135", 8080));

        //T-Mobile (Report Count: 1, Last Report: 2014-07-28 00:39:12)
        put("310260|Simple Mobile|310260|T-Mobile", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //T-Mobile (Report Count: 1, Last Report: 2014-08-05 21:14:38)
        put("310260|Sprint|310260|T-Mobile", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //T-Mobile (Report Count: 1, Last Report: 2014-08-15 13:10:26)
        put("310260|T-Mobile@|310260|T-Mobile", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", "216.165.155.50", 8080));

        //T-Mobile (Report Count: 1, Last Report: 2014-09-12 00:24:18)
        put("310260|T-Mobile|310260|T-Mobile", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //T-Mobile (Report Count: 1, Last Report: 2014-09-11 14:39:01)
        put("310260|Verizon|310260|T-Mobile", new ApnParameters("http://smpl.mms.msg.eng.t-mobile..com/mms/wapenc", null, null));

        //T-Mobile (Report Count: 6, Last Report: 2014-09-14 01:25:41)
        put("310260||310260|T-Mobile", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //T-Mobile (Report Count: 1, Last Report: 2014-07-18 21:14:01)
        put("310770||310260|T-Mobile", new ApnParameters("http://mmsc.iwireless.dataonair.net:6672", "209.4.229.31", 9201));

        //T-Mobile (Report Count: 1, Last Report: 2014-08-14 14:09:38)
        put("310260||310260|T-Mobile ", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", "216.155.165.50", 8080));

        //T-Mobile A (Report Count: 1, Last Report: 2014-08-05 18:05:33)
        put("23203||23203|T-Mobile A", new ApnParameters("http://mmsc.t-mobile.at/servlets/mms", "10.12.0.20", 80));

        //T-Mobile UK (Report Count: 1, Last Report: 2014-09-11 19:16:56)
        put("23430||23430|T-Mobile UK", new ApnParameters("http://mms/", "149.254.201.135", 8080));

        //T-Mobile UK (Report Count: 1, Last Report: 2014-08-18 08:29:23)
        put("23433|@@@@@@@@@@@@@@@@|23430|T-Mobile UK", new ApnParameters("http://mms/", "149.254.201.135", 8080));

        //TATA DOCOMO (Report Count: 1, Last Report: 2014-09-13 14:53:58)
        put("405031|TATA DOCOMO|405031|TATA DOCOMO", new ApnParameters("http://mmsc/", "10.124.26.94", 8799));

        //TATA DOCOMO (Report Count: 1, Last Report: 2014-08-15 16:56:40)
        put("405034|TATA DOCOMO|405034|TATA DOCOMO", new ApnParameters("http://mmsc/", "10.124.26.94", 8799));

        //TATA DOCOMO (Report Count: 1, Last Report: 2014-08-05 14:57:58)
        put("405042|TATA DOCOMO|405042|TATA DOCOMO", new ApnParameters("http://mmsc/", "10.124.26.94", 8799));

        //Tbaytel / Rogers (Report Count: 1, Last Report: 2014-08-14 13:36:38)
        put("302720|Tbaytel / Rogers|302720|Tbaytel / Rogers", new ApnParameters("http://mms.gprs.rogers.com", "mmsproxy.rogers.com", 80));

        //TELCEL (Report Count: 1, Last Report: 2014-09-10 17:10:24)
        put("334020|TELCEL|334020|TELCEL", new ApnParameters("http://mms.itelcel.com/servlets/mms", "148.233.151.240", 8080));

        //Telecom NZ (Report Count: 1, Last Report: 2014-08-06 07:26:19)
        put("53005|Telecom NZ|53005|Telecom NZ", new ApnParameters("http://lsmmsc.xtra.co.nz", "210.55.11.73", 80));

        //Telenor PK (Report Count: 1, Last Report: 2014-07-25 21:01:04)
        put("41006||41006|Telenor PK", new ApnParameters("http://mmstelenor", "172.18.19.11", 8080));

        //TELKOMSEL (Report Count: 1, Last Report: 2014-06-23 09:34:12)
        put("51010|TELKOMSEL|51010|TELKOMSEL", new ApnParameters("http://mms.telkomsel.com", "10.1.89.150", 8000));

        //TELKOMSEL (Report Count: 1, Last Report: 2014-09-13 05:07:07)
        put("51010||51010|TELKOMSEL", new ApnParameters("http://mms.telkomsel.com", "10.1.89.150", 8000));

        //Telstra (Report Count: 1, Last Report: 2014-09-06 08:51:37)
        put("50501|Telstra|50501|Telstra", new ApnParameters("http://mmsc.telstra.com:8002", "10.1.1.180", 80));

        //Telstra Mobile (Report Count: 1, Last Report: 2014-08-24 11:44:09)
        put("50501|BOOST|50501|Telstra Mobile", new ApnParameters("http://mmsc.telstra.com:8002/", "10.1.1.180", 80));

        //Telstra Mobile (Report Count: 1, Last Report: 2014-09-13 03:05:13)
        put("50501|Telstra|50501|Telstra Mobile", new ApnParameters("http://mmsc.telstra.com:8002/", "10.1.1.180", 80));

        //TELUS (Report Count: 1, Last Report: 2014-09-12 02:42:55)
        put("302220||302220|TELUS", new ApnParameters("http://aliasredirect.net/proxy/mmsc", "74.49.0.18", 80));

        //TELUS (Report Count: 1, Last Report: 2014-06-19 00:07:55)
        put("311480||302220|TELUS", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //TESCO (Report Count: 1, Last Report: 2014-08-14 16:01:56)
        put("23410|TESCO|23410|TESCO", new ApnParameters("http://mmsc.mms.o2.co.uk:8002", "193.113.200.195", 8080));

        //TH GSM (Report Count: 1, Last Report: 2014-06-23 12:01:11)
        put("52003|AIS|52001|TH GSM", new ApnParameters("http://mms.mobilelife.co.th", "203.170.229.34", 8080));

        //TIM (Report Count: 1, Last Report: 2014-09-13 16:35:08)
        put("72402|TIM|72402|TIM", new ApnParameters("http://mms.tim.br", "200.179.66.242", 8080));

        //TIM (Report Count: 1, Last Report: 2014-07-21 16:53:18)
        put("72404|TIM|72404|TIM", new ApnParameters("http://mms.tim.br", "200.179.66.242", 8080));

        //TPG (Report Count: 1, Last Report: 2014-07-16 06:43:24)
        put("50502|TPG|50502|TPG", new ApnParameters("http://mmsc.optus.com.au:8002/", "61.88.190.10", 8070));

        //TR TURKCELL (Report Count: 1, Last Report: 2014-09-05 19:30:56)
        put("28601||28601|TR TURKCELL", new ApnParameters("http://mms.turkcell.com.tr/servlets/mms", "212.252.169.217", 8080));

        //TracFone (Report Count: 1, Last Report: 2014-09-08 17:10:19)
        put("310000|TracFone|310000| ", new ApnParameters("http://mms.vzwreseller.com/servlets/mms", null, null));

        //TracFone (Report Count: 1, Last Report: 2014-07-29 12:59:19)
        put("310410||310410|TracFone", new ApnParameters("http://mms-tf.net", "mms3.tracfone.com", 80));

        //TRUE-H (Report Count: 1, Last Report: 2014-07-19 10:41:10)
        put("52000|TRUE-H|52000|TRUE-H", new ApnParameters("http://mms.trueh.com:8002", "10.4.7.39", 8080));

        //TSTT (Report Count: 1, Last Report: 2014-08-17 14:47:05)
        put("374129||37412|TSTT", new ApnParameters("http://192.168.210.104/mmrelay.app", "192.168.210.104", 8080));

        //Tuenti (Report Count: 1, Last Report: 2014-07-11 17:15:07)
        put("21405|Tuenti|21407|Tuenti", new ApnParameters("http://tuenti.com", "10.138.255.43", 8080));

        //TuneTalk (Report Count: 1, Last Report: 2014-07-15 07:21:08)
        put("50219|TuneTalk|50219|TuneTalk", new ApnParameters("http://mms.celcom.net.my", "10.128.1.242", 8080));

        //U Mobile (Report Count: 1, Last Report: 2014-07-09 14:44:26)
        put("50218||50212|U Mobile", new ApnParameters("http://10.30.3.11/servlets/mms", "10.30.5.11", 8080));

        //U Mobile (Report Count: 1, Last Report: 2014-09-11 05:41:48)
        put("50218||50218|U Mobile", new ApnParameters("http://10.30.3.11/servlets/mms", "10.30.5.11", 8080));

        //U.S. Cellular (Report Count: 1, Last Report: 2014-09-10 23:36:09)
        put("311580|U.S. Cellular|310000|U.S. Cellular", new ApnParameters("http://mmsc1.uscc.net/mmsc/MMS", null, null));

        //U.S. Cellular (Report Count: 1, Last Report: 2014-08-25 15:43:57)
        put("311580|U.S. Cellular|31000|U.S. Cellular", new ApnParameters("http://mmsc1.uscc.net/mmsc/MMS", null, null));

        //U.S. Cellular (Report Count: 1, Last Report: 2014-09-12 22:41:50)
        put("311580|U.S. Cellular|311580|U.S. Cellular", new ApnParameters("http://mmsc1.uscc.net/mmsc/MMS", null, null));

        //U.S. Cellular (Report Count: 1, Last Report: 2014-09-06 15:27:59)
        put("311580|US Cellular|31000|U.S. Cellular", new ApnParameters("http://mmsc1.uscc.net/mmsc/MMS", null, null));

        //U.S. Cellular (Report Count: 1, Last Report: 2014-07-26 18:05:30)
        put("311580|US Cellular|31099|U.S. Cellular", new ApnParameters("http://mmsc1.uscc.net/mmsc/MMS", null, null));

        //U.S. Cellular (Report Count: 1, Last Report: 2014-09-11 16:40:59)
        put("311580|US Cellular|311580|U.S. Cellular", new ApnParameters("http://mmsc1.uscc.net/mmsc/MMS", null, null));

        //Ufone (Report Count: 1, Last Report: 2014-08-22 15:07:34)
        put("41003|Ufone|41003|Ufone", new ApnParameters("http://10.81.6.11:8080", "10.81.6.33", 8000));

        //Ultra.me (Report Count: 1, Last Report: 2014-09-11 18:05:36)
        put("310260||310260|Ultra.me", new ApnParameters("http://wholesale.mmsmvno.com/mms/wapenc", null, null));

        //US (Report Count: 1, Last Report: 2014-08-05 19:57:43)
        put("310260|T-Mobile|310260|US", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //US (Report Count: 1, Last Report: 2014-09-13 01:46:33)
        put("310260||310260|US", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //US - Union Telephone (Report Count: 1, Last Report: 2014-08-15 13:43:11)
        put("310260||310020|US - Union Telephone", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //US Cellular (Report Count: 1, Last Report: 2014-09-07 14:14:20)
        put("311580|US Cellular|31000|US Cellular", new ApnParameters("http://mmsc1.uscc.net/mmsc/MMS", null, null));

        //US Cellular (Report Count: 1, Last Report: 2014-09-13 02:23:21)
        put("311580|US Cellular|311580|US Cellular", new ApnParameters("http://mmsc1.uscc.net/mmsc/MMS", null, null));

        //US PLATEAU (Report Count: 1, Last Report: 2014-07-14 18:57:27)
        put("310100||310100|US PLATEAU", new ApnParameters("http://mms", "172.23.253.206", 8080));

        //Verizon (Report Count: 1, Last Report: 2014-09-06 22:34:05)
        put("204043|Verizon|310004|", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon (Report Count: 1, Last Report: 2014-09-09 00:33:53)
        put("310000|Verizon|31000| ", new ApnParameters("null", null, null));

        //Verizon (Report Count: 1, Last Report: 2014-09-10 21:39:48)
        put("310000|Verizon|311480| ", new ApnParameters("null", null, null));

        //Verizon (Report Count: 1, Last Report: 2014-09-13 23:12:36)
        put("310090|Verizon|31001|", new ApnParameters("null", null, null));

        //Verizon (Report Count: 1, Last Report: 2014-09-09 20:11:43)
        put("310260|Verizon|00000|", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //Verizon (Report Count: 1, Last Report: 2014-07-26 22:28:02)
        put("311480|Verizon|00000| ", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon (Report Count: 1, Last Report: 2014-09-08 22:25:21)
        put("311480|Verizon|31000| ", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon (Report Count: 1, Last Report: 2014-09-13 16:40:06)
        put("311480|Verizon|311480| ", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon (Report Count: 1, Last Report: 2014-07-22 16:09:56)
        put("|Verizon|310004| ", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon (Report Count: 1, Last Report: 2014-09-10 00:33:58)
        put("|Verizon|31000| ", new ApnParameters("null", null, null));

        //Verizon Wireless (Report Count: 1, Last Report: 2014-09-07 00:02:40)
        put("310002|Verizon Wireless|311480|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 1, Last Report: 2014-09-05 04:48:04)
        put("310003|Verizon Wireless|311480|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 1, Last Report: 2014-08-23 23:12:30)
        put("310004|Verizon Wireless|311480|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 1, Last Report: 2014-09-10 19:47:18)
        put("310004|Verizon|310000|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 1, Last Report: 2014-09-08 20:17:34)
        put("310004|Verizon|31000|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 1, Last Report: 2014-08-19 01:17:14)
        put("310004|Verizon|310012|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 1, Last Report: 2014-08-05 21:47:17)
        put("310005|Verizon Wireless|000000|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 1, Last Report: 2014-07-31 17:18:18)
        put("310005|Verizon Wireless|311480|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 1, Last Report: 2014-06-24 17:30:39)
        put("310006|Verizon Wireless|311480|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 1, Last Report: 2014-08-26 03:03:01)
        put("310006|Verizon|311480|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 1, Last Report: 2014-08-14 22:43:43)
        put("310007|Verizon Wireless|311480|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 1, Last Report: 2014-06-07 02:23:03)
        put("310008|Verizon Wireless|311480|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 1, Last Report: 2014-08-23 17:41:41)
        put("31000|Verizon Wireless|311480|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 1, Last Report: 2014-07-25 16:04:19)
        put("311480|Verizon Wireless|001001|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 1, Last Report: 2014-09-11 00:47:37)
        put("311480|Verizon Wireless|123456|Verizon Wireless", new ApnParameters("null", null, null));

        //Verizon Wireless (Report Count: 1, Last Report: 2014-07-21 13:21:38)
        put("311480|Verizon Wireless|310000| ", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 1, Last Report: 2014-09-13 14:06:59)
        put("311480|Verizon Wireless|310000|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 1, Last Report: 2014-09-13 23:24:56)
        put("311480|Verizon Wireless|310004|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 1, Last Report: 2014-09-11 12:43:34)
        put("311480|Verizon Wireless|31000| ", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 1, Last Report: 2014-09-13 23:29:52)
        put("311480|Verizon Wireless|31000|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 1, Last Report: 2014-08-21 20:49:29)
        put("311480|Verizon Wireless|31007|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 1, Last Report: 2014-09-13 17:04:45)
        put("311480|Verizon Wireless|3107|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 1, Last Report: 2014-08-08 19:06:51)
        put("311480|Verizon Wireless|31099|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 1, Last Report: 2014-09-13 02:07:48)
        put("311480|Verizon Wireless|311480| ", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 3, Last Report: 2014-09-14 01:18:01)
        put("311480|Verizon Wireless|311480|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 1, Last Report: 2014-09-11 02:20:29)
        put("311480|Verizon Wireless|3167| ", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 1, Last Report: 2014-09-08 19:52:51)
        put("311480|Verizon Wireless|3167|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 1, Last Report: 2014-07-14 12:53:36)
        put("311480|Verizon|00000|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 1, Last Report: 2014-06-28 00:59:52)
        put("311480|Verizon|310000|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 1, Last Report: 2014-09-13 00:10:47)
        put("311480|Verizon|31000|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 1, Last Report: 2014-09-11 20:29:43)
        put("311480|Verizon|31072|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 1, Last Report: 2014-09-06 03:08:17)
        put("311480|Verizon|31099|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 1, Last Report: 2014-09-13 23:42:50)
        put("311480|Verizon|311480|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 1, Last Report: 2014-08-08 00:05:40)
        put("311480||3167|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 1, Last Report: 2014-09-08 15:39:58)
        put("31148|Verizon Wireless|310004|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Videotron (Report Count: 1, Last Report: 2014-08-11 23:20:08)
        put("302500|Videotron|302500|Videotron", new ApnParameters("http://media.videotron.com/", "10.208.89.17", 8080));

        //Videotron (Report Count: 1, Last Report: 2014-09-12 00:03:20)
        put("302500|Vidéotron|302500|Videotron", new ApnParameters("http://media.videotron.com", null, null));

        //Viettel Mobile (Report Count: 1, Last Report: 2014-09-09 15:36:31)
        put("45204|VIETTEL|45204|Viettel Mobile", new ApnParameters("http://mms.viettelmobile.com.vn/mms/wapenc", "192.168.233.10", 8080));

        //Virgin (Report Count: 1, Last Report: 2014-08-25 13:16:54)
        put("20823|Virgin|20810|Virgin", new ApnParameters("http://virginmms.fr", "10.6.10.1", 8080));

        //Virgin (Report Count: 1, Last Report: 2014-09-05 16:30:36)
        put("23430||23430|Virgin", new ApnParameters("http://mms.virginmobile.co.uk:8002", "193.30.166.2", 8080));

        //VIRGIN (Report Count: 1, Last Report: 2014-09-13 14:35:00)
        put("302610||302610|VIRGIN", new ApnParameters("http://mms.bell.ca/mms/wapenc", null, null));

        //Virgin (Report Count: 1, Last Report: 2014-08-18 21:37:34)
        put("50502|Virgin Mobile|50502|Virgin", new ApnParameters("http://mmsc.optus.com.au:8002/", "61.88.190.10", 8070));

        //Virgin Mobile (Report Count: 1, Last Report: 2014-07-02 08:28:22)
        put("310120|Sprint|310120|Virgin Mobile", new ApnParameters("http://mmsc.vmobl.com:8088/mms?type=null", "68.28.31.7", 80));

        //Virgin Mobile (Report Count: 1, Last Report: 2014-09-12 20:51:21)
        put("311490|Virgin Mobile|311490|Virgin Mobile", new ApnParameters("http://mmsc.vmobl.com:8088/mms?", null, null));

        //VIVO (Report Count: 1, Last Report: 2014-09-12 14:58:58)
        put("72406|VIVO|72406|VIVO", new ApnParameters("http://termnat.vivomms.com.br:8088/mms", "200.142.130.104", 80));

        //VIVO (Report Count: 1, Last Report: 2014-07-11 22:52:47)
        put("72410|VIVO|72410|VIVO", new ApnParameters("http://termnat.vivomms.com.br:8088/mms", "200.142.130.104", 80));

        //VIVO (Report Count: 1, Last Report: 2014-09-05 21:49:36)
        put("72411|VIVO|72411|VIVO", new ApnParameters("http://termnat.vivomms.com.br:8088/mms", "200.142.130.104", 80));

        //VIVO (Report Count: 1, Last Report: 2014-08-02 01:45:56)
        put("72411||72411|VIVO", new ApnParameters("http://termnat.vivomms.com.br:8088/mms", "200.142.130.104", 80));

        //VMS(Mobiphone) (Report Count: 1, Last Report: 2014-09-12 15:14:35)
        put("45201|MOBIFONE|45201|VMS(Mobiphone)", new ApnParameters("http://203.162.21.114/mmsc", "203.162.21.114", 8080));

        //VMS(Mobiphone) (Report Count: 1, Last Report: 2014-09-06 05:27:59)
        put("45201|Mobifone|45201|VMS(Mobiphone)", new ApnParameters("http://203.162.21.114/mmsc", "203.162.21.114", 3130));

        //VN MobiFone (Report Count: 1, Last Report: 2014-07-15 10:59:06)
        put("45201|MOBIFONE|45201|VN MobiFone", new ApnParameters("http://203.162.21.114/mmsc", "203.162.21.114", 8080));

        //VN MOBIFONE (Report Count: 1, Last Report: 2014-09-05 05:25:25)
        put("45201||45201|VN MOBIFONE", new ApnParameters("http://203.162.21.114/mmsc", "203.162.21.114", 3130));

        //VN VINAPHONE (Report Count: 1, Last Report: 2014-08-13 10:37:33)
        put("45202||45202|VN VINAPHONE", new ApnParameters("http://mms.vinaphone.com.vn", "10.1.10.46", 8000));

        //Vodafone (Report Count: 1, Last Report: 2014-08-17 14:24:29)
        put("40413|Vodafone IN|40407|Vodafone", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Vodafone (Report Count: 1, Last Report: 2014-08-07 05:20:37)
        put("40488|Vodafone IN|40488|Vodafone", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Vodafone AU (Report Count: 1, Last Report: 2014-09-13 04:57:00)
        put("50503||50503|Vodafone AU", new ApnParameters("http://pxt.vodafone.net.au/pxtsend", "10.202.2.60", 8080));

        //Vodafone IN (Report Count: 1, Last Report: 2014-07-24 18:19:01)
        put("40405|Hutch|40405|Vodafone IN", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Vodafone In (Report Count: 1, Last Report: 2014-09-07 11:38:41)
        put("40405|Vodafone IN|40405|Vodafone In", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Vodafone IN (Report Count: 1, Last Report: 2014-09-12 14:30:20)
        put("40411|Vodafone IN|40411|Vodafone IN", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Vodafone IN (Report Count: 1, Last Report: 2014-08-03 06:59:31)
        put("40413|Vodafone IN|40413|Vodafone IN", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Vodafone IN (Report Count: 1, Last Report: 2014-06-13 06:44:52)
        put("40413|Vodafone IN|40427|Vodafone IN", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Vodafone IN (Report Count: 1, Last Report: 2014-09-11 05:13:03)
        put("40420|Vodafone IN|40420|Vodafone IN", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Vodafone IN (Report Count: 1, Last Report: 2014-09-08 14:21:02)
        put("40427|BPL Mobile|40427|Vodafone IN", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Vodafone IN (Report Count: 1, Last Report: 2014-09-08 17:46:15)
        put("40427|Vodafone IN|40427|Vodafone IN", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Vodafone IN (Report Count: 1, Last Report: 2014-07-14 11:41:02)
        put("40430|Hutch|40430|Vodafone IN", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Vodafone IN (Report Count: 1, Last Report: 2014-09-09 15:46:02)
        put("40443|Vodafone IN|40484|Vodafone IN", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Vodafone IN (Report Count: 1, Last Report: 2014-07-03 07:18:46)
        put("40484||40484|Vodafone IN", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Vodafone IN (Report Count: 1, Last Report: 2014-06-17 10:11:49)
        put("40488|Vodafone IN|40488|Vodafone IN", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //vodafone IT (Report Count: 1, Last Report: 2014-08-23 17:25:30)
        put("22210||22210|vodafone IT", new ApnParameters("http://mms.vodafone.it/servlets/mms", "10.128.224.10", 80));

        //vodafone P (Report Count: 1, Last Report: 2014-09-12 20:26:44)
        put("26801||26801|vodafone P", new ApnParameters("http://mms.vodafone.pt/servlets/mms", "iproxy.vodafone.pt", 80));

        //vodafone UK (Report Count: 1, Last Report: 2014-08-12 13:42:53)
        put("23415|Talkmobile|23415|vodafone UK", new ApnParameters("http://mms.talkmobile.co.uk/servlets/mms", "212.183.137.12", 8799));

        //vodafone UK (Report Count: 1, Last Report: 2014-09-11 20:53:02)
        put("23415||23415|vodafone UK", new ApnParameters("http://mms.vodafone.co.uk/servlets/mms", "212.183.137.12", 8799));

        //Vodafone.de (Report Count: 1, Last Report: 2014-09-06 05:43:39)
        put("26202||26202|Vodafone.de", new ApnParameters("http://139.7.24.1/servlets/mms", "139.7.29.17", 80));

        //WIND (Report Count: 1, Last Report: 2014-07-24 20:35:59)
        put("302490||302490|WIND", new ApnParameters("http://mms.windmobile.ca", "74.115.197.70", 8080));

        //XL (Report Count: 1, Last Report: 2014-09-13 09:14:26)
        put("51011|XL Axiata|51011|XL", new ApnParameters("http://mmc.xl.net.id/servlets/mms", "202.152.240.50", 8080));

        //XL (Report Count: 1, Last Report: 2014-08-19 09:41:26)
        put("51011|XL|51011|XL", new ApnParameters("http://mmc.xl.net.id/servlets/mms", "202.152.240.50", 8080));

        //XL IND (Report Count: 1, Last Report: 2014-09-09 02:09:37)
        put("51011|XL Axiata|51011|XL IND", new ApnParameters("http://mmc.xl.net.id/servlets/mms", "202.152.240.50", 8080));

        //YES OPTUS (Report Count: 1, Last Report: 2014-09-11 22:17:23)
        put("50502|TPG|50502|YES OPTUS", new ApnParameters("http://mmsc.optus.com.au:8002/", "61.88.190.10", 8070));

        //YES OPTUS (Report Count: 1, Last Report: 2014-08-19 10:30:49)
        put("50502|YES OPTUS|50502|YES OPTUS", new ApnParameters("http://mmsc.optus.com.au:8002/", "61.88.190.10", 8070));

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
