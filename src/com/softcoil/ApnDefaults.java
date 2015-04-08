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

        // (Report Count: 1, Last Report: 2014-10-06 16:10:12)
        put("20404||31000|", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        // (Report Count: 433, Last Report: 2015-04-08 01:19:57)
        put("310000||123456| ", new ApnParameters("http://mms.vzwreseller.com/servlets/mms", null, null));

        // (Report Count: 35, Last Report: 2015-04-07 00:44:22)
        put("310000||31000|", new ApnParameters("null", null, null));

        // (Report Count: 113, Last Report: 2015-04-08 17:38:34)
        put("310000||31000| ", new ApnParameters("http://mms.vzwreseller.com/servlets/mms", null, null));

        // (Report Count: 1, Last Report: 2015-03-29 05:48:27)
        put("31000||123456| ", new ApnParameters("null", null, null));

        // (Report Count: 22, Last Report: 2015-04-05 06:54:11)
        put("31000||310000|", new ApnParameters("null", null, null));

        // (Report Count: 223, Last Report: 2015-04-06 14:01:34)
        put("31000||31000|", new ApnParameters("null", null, null));

        // (Report Count: 8, Last Report: 2015-03-28 04:05:25)
        put("310120||31000| ", new ApnParameters("null", null, null));

        // (Report Count: 2, Last Report: 2014-12-24 19:48:30)
        put("310150||310410|", new ApnParameters("http://mmsc.aiowireless.net", "proxy.aiowireless.net", 80));

        // (Report Count: 3, Last Report: 2015-03-26 20:53:10)
        put("310260||310260| ", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        // (Report Count: 2, Last Report: 2015-01-01 14:44:07)
        put("310260|||", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        // (Report Count: 99, Last Report: 2015-04-08 14:29:26)
        put("310410||310410| ", new ApnParameters("http://mmscUrl.cingular.com/", "wireless.cingular.com", 80));

        // (Report Count: 2, Last Report: 2015-02-27 12:58:22)
        put("310410|||", new ApnParameters("http://mmscUrl.cingular.com/", "wireless.cingular.com", 80));

        // (Report Count: 3, Last Report: 2015-03-07 22:56:08)
        put("311480||310004|", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        // (Report Count: 14, Last Report: 2015-03-14 18:56:17)
        put("311480||31000|", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        // (Report Count: 1, Last Report: 2014-11-26 23:20:54)
        put("311480||3107|", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        // (Report Count: 18, Last Report: 2015-03-20 02:39:41)
        put("311480||311480|", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        // (Report Count: 4, Last Report: 2015-03-21 20:12:10)
        put("311480||3167|", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        // (Report Count: 1, Last Report: 2015-02-12 13:26:10)
        put("311480|||", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        // (Report Count: 1, Last Report: 2014-09-20 20:26:50)
        put("311580||311580|", new ApnParameters("null", null, null));

        // (Report Count: 4, Last Report: 2015-03-24 03:00:48)
        put("51028|||", new ApnParameters("http://mmsc.xl.net.id/servlets/mms", "202.152.240.50", 8080));

        // (Report Count: 1, Last Report: 2015-01-28 08:44:11)
        put("52503|||", new ApnParameters("http://mms.starhubgee.com.sg:8002", "10.12.1.80", 80));

        // (Report Count: 1, Last Report: 2015-02-19 14:03:33)
        put("62130||62130|", new ApnParameters("http://10.199.212.8/servlets/mms", "10.199.212.2", 8080));

        // (Report Count: 1, Last Report: 2015-03-31 00:25:24)
        put("||310004|", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        // (Report Count: 4, Last Report: 2015-03-10 19:07:23)
        put("||31000|", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        // (Report Count: 5, Last Report: 2015-04-08 01:49:54)
        put("|||", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        // (Report Count: 1, Last Report: 2014-07-22 13:50:03)
        put("||| ", new ApnParameters("http://mms.celcom.net.my", "10.128.1.242", 8080));

        // Empty (Report Count: 1, Last Report: 2015-03-05 17:58:55)
        put("310005|Verizon Wireless|123456| Empty", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        // DiGi (Report Count: 50, Last Report: 2015-04-02 03:20:24)
        put("50216|DiGi|50216| DiGi ", new ApnParameters("http://mms.digi.com.my/servlets/mms", "203.92.128.160", 80));

        // DiGi (Report Count: 3, Last Report: 2015-02-18 15:38:34)
        put("50216||50216| DiGi ", new ApnParameters("http://mms.digi.com.my/servlets/mms", "203.92.128.160", 80));

        // Galaxy Nexus (Report Count: 1, Last Report: 2015-01-19 00:30:42)
        put("311480|Verizon Wireless|311480| Galaxy Nexus ", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        // BASE (Report Count: 1, Last Report: 2015-03-05 14:06:02)
        put("20620|BASE|20620| BASE", new ApnParameters("http://mmsc.base.be", "217.72.235.1", 8080));

        // Cell C (Report Count: 3, Last Report: 2015-03-04 11:12:42)
        put("65507| Cell C |65507| Cell C ", new ApnParameters("http://mms.cmobile.co.za", "196.31.116.250", 8080));

        // T-Mobile (Report Count: 2, Last Report: 2015-01-27 02:20:09)
        put("310260||310260| T-Mobile", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //!dea (Report Count: 1, Last Report: 2015-01-22 06:52:52)
        put("40407|!dea|40407|!dea", new ApnParameters("http://10.4.42.21:8002/", "10.4.42.15", 8080));

        //!dea (Report Count: 1, Last Report: 2015-02-14 07:42:33)
        put("40412|!dea|40412|!dea", new ApnParameters("http://100.1.201.171:10021/mmsc", "100.1.201.172", 8799));

        //!dea (Report Count: 1, Last Report: 2014-12-06 05:07:22)
        put("40414|!dea|40414|!dea", new ApnParameters("http://10.4.42.21:8002/", "10.4.42.15", 8080));

        //!dea (Report Count: 1, Last Report: 2015-03-07 10:08:30)
        put("40422|!dea|40422|!dea", new ApnParameters("http://10.4.42.21:8002/", "10.4.42.15", 8080));

        //!dea (Report Count: 1, Last Report: 2015-02-24 08:28:32)
        put("40422|Vodafone IN|40422|!dea", new ApnParameters("http://10.4.42.21:8002", "10.4.42.15", 8080));

        //!dea (Report Count: 1, Last Report: 2014-12-24 07:58:26)
        put("40424|!dea|40424|!dea", new ApnParameters("http://10.4.42.21:8002/", "10.4.42.15", 8080));

        //!dea (Report Count: 1, Last Report: 2015-02-13 23:06:32)
        put("40424|Vodafone IN|40424|!dea", new ApnParameters("http://10.4.42.21:8002", "10.4.42.15", 8080));

        //!dea (Report Count: 1, Last Report: 2014-12-06 02:27:22)
        put("40424||40424|!dea", new ApnParameters("http://10.4.42.21:8002", "10.4.42.15", 8080));

        //!dea (Report Count: 2, Last Report: 2015-02-25 09:18:24)
        put("40444|!dea|40445|!dea", new ApnParameters("http://10.4.42.21:8002", "10.4.42.15", 8080));

        //!dea (Report Count: 1, Last Report: 2015-02-22 12:52:26)
        put("40478|!dea|40478|!dea", new ApnParameters("http://10.4.42.21:8002", "10.4.42.15", 8080));

        //!dea (Report Count: 1, Last Report: 2015-01-01 01:43:49)
        put("40478|!dea||", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //!dea (Report Count: 1, Last Report: 2015-03-13 15:09:54)
        put("40487|!dea|40487|!dea", new ApnParameters("http://10.4.42.21:8002/", "10.4.42.15", 8080));

        //!dea (Report Count: 1, Last Report: 2015-04-01 19:37:40)
        put("405799|!dea|405799|", new ApnParameters("http://10.4.42.21:8002", "10.4.42.15", 8080));

        //012 Mobile (Report Count: 35, Last Report: 2015-04-01 19:49:44)
        put("42501|012 Mobile|42501|012 Mobile", new ApnParameters("http://192.168.220.15/servlets/mms", null, null));

        //1&1 (Report Count: 3, Last Report: 2015-03-13 19:24:12)
        put("26202|1&1|26202|1&1", new ApnParameters("http://139.7.24.1/servlets/mms", "139.7.29.17", 80));

        //1&1 (Report Count: 1, Last Report: 2015-03-13 19:02:24)
        put("26203|1&1|26203|1&1", new ApnParameters("http://mms/eplus/", "212.23.97.153", 5080));

        //2010122001 (Report Count: 1, Last Report: 2015-03-05 05:27:07)
        put("310120|Sprint|310120|2010122001", new ApnParameters("http://mms.sprintpcs.com", "68.28.31.7", 80));

        //208 11 (Report Count: 1, Last Report: 2015-01-24 11:31:42)
        put("20810||20811|208 11", new ApnParameters("http://mms1/", "10.151.0.1", 8080));

        //20815 (Report Count: 1, Last Report: 2015-04-06 08:19:52)
        put("20815|Free|20815|20815", new ApnParameters("http://mms.free.fr", null, null));

        //25003 (Report Count: 1, Last Report: 2015-02-12 18:11:28)
        put("25003|Rostelecom|25003|25003", new ApnParameters("http://10.0.3.50", "10.0.3.20", 8080));

        //2degrees (Report Count: 8, Last Report: 2015-04-02 02:27:08)
        put("53024|2degrees|53024|2degrees", new ApnParameters("http://mms.2degreesmobile.net.nz:48090", "118.148.1.118", 8080));

        //3 (Report Count: 16, Last Report: 2015-03-31 18:25:02)
        put("23420|3|23420|3", new ApnParameters("http://mms.um.three.co.uk:10021/mmsc", "mms.three.co.uk", 8799));

        //3 (Report Count: 198, Last Report: 2015-04-07 07:54:00)
        put("23420||23420|3", new ApnParameters("http://mms.um.three.co.uk:10021/mmsc", "mms.three.co.uk", 8799));

        //3 (Report Count: 1, Last Report: 2014-09-19 06:36:53)
        put("23806|3|23801|3", new ApnParameters("http://mms.3.dk", "172.16.53.12", 8799));

        //3 (Report Count: 2, Last Report: 2015-02-12 13:35:46)
        put("23806|3|23806|3", new ApnParameters("http://mms.3.dk", "mmsproxy.3.dk", 8799));

        //3 (Report Count: 13, Last Report: 2015-03-17 14:00:18)
        put("24002|3|24002|3", new ApnParameters("http://mms.tre.se", "mmsproxy.tre.se", 8799));

        //3 (Report Count: 4, Last Report: 2015-03-12 07:06:28)
        put("24002|3|24004|3", new ApnParameters("http://mms.tre.se", "mmsproxy.tre.se", 8799));

        //3 (Report Count: 1, Last Report: 2015-02-10 09:03:44)
        put("27205|3|27205|3", new ApnParameters("http://mms.um.3ireland.ie:10021/mmsc", "mms.3ireland.ie", 8799));

        //3 (Report Count: 17, Last Report: 2015-03-31 18:55:02)
        put("27205||27205|3", new ApnParameters("http://mms.um.3ireland.ie:10021/mmsc", "mms.3ireland.ie", 8799));

        //3 (Report Count: 3, Last Report: 2015-03-26 10:35:17)
        put("45403||45403|3", new ApnParameters("http://mms.um.three.com.hk:10021/mmsc", "mms.three.com.hk", 8799));

        //3 (Report Count: 2, Last Report: 2015-03-18 05:24:55)
        put("51089|3|51089|3", new ApnParameters("http://mmsc.indosat.com", "10.19.19.19", 8080));

        //3 (Report Count: 1, Last Report: 2015-02-07 07:20:28)
        put("51089||51089|3", new ApnParameters("http://10.17.93.103:8080", "10.17.27.250", 8080));

        //3 AT (Report Count: 1, Last Report: 2014-10-29 16:53:57)
        put("23205|3 AT@|23205|3 AT", new ApnParameters("http://mmsc.orange.at/mms/wapenc", "194.24.128.118", 8080));

        //3 AT (Report Count: 1, Last Report: 2015-03-23 08:55:28)
        put("23205|3 AT|23205|3 AT", new ApnParameters("http://mmsc", "213.94.78.133", 8799));

        //3 AT (Report Count: 23, Last Report: 2015-03-24 15:48:12)
        put("23205||23205|3 AT", new ApnParameters("http://mmsc", "213.94.78.133", 8799));

        //3 AT (Report Count: 9, Last Report: 2015-03-30 09:18:18)
        put("23210||23205|3 AT", new ApnParameters("http://mmsc", "213.94.78.133", 8799));

        //3 AT (Report Count: 23, Last Report: 2015-04-02 19:49:17)
        put("23210||23210|3 AT", new ApnParameters("http://mmsc", "213.94.78.133", 8799));

        //3 DK (Report Count: 1, Last Report: 2015-02-25 21:05:55)
        put("23806|3|23806|3 DK", new ApnParameters("http://mms.3.dk", "mmsproxy.3.dk", 8799));

        //3 DK (Report Count: 5, Last Report: 2015-02-13 20:04:02)
        put("23806|Oister|23806|3 DK", new ApnParameters("http://mms.3.dk/", "mmsproxy.3.dk", 8799));

        //3 HK (Report Count: 1, Last Report: 2014-10-29 02:34:29)
        put("45403||45403|3 HK", new ApnParameters("http://mms.um.three.com.hk:10021/mmsc", "mms.three.com.hk", 8799));

        //3 IRL (Report Count: 2, Last Report: 2014-10-22 15:43:39)
        put("27205|3|27205|3 IRL", new ApnParameters("http://mms.um.3ireland.ie:10021/mmsc", "mms.3ireland.ie", 8799));

        //3 IRL (Report Count: 2, Last Report: 2014-11-02 12:55:59)
        put("27205||27205|3 IRL", new ApnParameters("http://mms.um.3ireland.ie:10021/mmsc", "mms.3ireland.ie", 8799));

        //3 ITA (Report Count: 18, Last Report: 2015-04-01 09:44:52)
        put("22299||22299|3 ITA", new ApnParameters("http://10.216.59.240:10021/mmsc", "62.13.171.3", 8799));

        //3 UK (Report Count: 1, Last Report: 2015-03-05 13:08:15)
        put("23420|3 UK|23420|3 UK", new ApnParameters("http://mms.um.three.co.uk:10021/mmsc", "mms.three.co.uk", 8799));

        //3 UK (Report Count: 7, Last Report: 2015-04-08 14:05:20)
        put("23420|3|23420|3 UK", new ApnParameters("http://mms.um.three.co.uk:10021/mmsc", "mms.three.co.uk", 8799));

        //3 UK (Report Count: 7, Last Report: 2015-03-15 17:34:34)
        put("23420||23420|3 UK", new ApnParameters("http://mms.um.three.co.uk:10021/mmsc", "mms.three.co.uk", 8799));

        //3 UK (Report Count: 1, Last Report: 2014-10-04 11:05:57)
        put("23806|3|23420|3 UK", new ApnParameters("http://mms.3.dk/", "mmsproxy.3.dk", 8799));

        //3-AT (Report Count: 1, Last Report: 2014-12-02 15:44:39)
        put("23210||23203|3-AT", new ApnParameters("http://mmsc", "213.94.78.133", 8799));

        //31000 (Report Count: 2, Last Report: 2015-03-27 19:15:59)
        put("330120|Open Mobile|31000|31000", new ApnParameters("http://mms.openmobilepr.com:1981", null, null));

        //310260 (Report Count: 4, Last Report: 2015-03-09 23:29:00)
        put("310260|MetroPCS|310260|310260", new ApnParameters("http://metropcs.mmsmvno.com/mms/wapenc", null, null));

        //310260 (Report Count: 4, Last Report: 2014-12-17 18:29:36)
        put("310260||310260|310260", new ApnParameters("http://metropcs.mmsmvno.com/mms/wapenc", null, null));

        //311 870 (Report Count: 2, Last Report: 2015-03-19 22:09:20)
        put("311870|Boost Mobile|311870|311 870", new ApnParameters("null", null, null));

        //311370 (Report Count: 1, Last Report: 2015-03-19 03:10:42)
        put("311370|GCI|311370|311370", new ApnParameters("http://mmsc.gci.net", "24.237.158.34", 9201));

        //311480 (Report Count: 1, Last Report: 2015-01-01 18:58:07)
        put("311480|Verizon|311480|311480", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //348 770 (Report Count: 1, Last Report: 2014-10-04 19:19:01)
        put("338050|DIGICEL|348770|348 770", new ApnParameters("http://mms.digicelgroup.com", "172.16.7.12", 8080));

        //3Mob (Report Count: 1, Last Report: 2014-10-25 16:15:11)
        put("25507|OGO!|25507|3Mob", new ApnParameters("http://10.212.1.4/mms/wapenc", "10.212.3.148", 8080));

        //404-71 (Report Count: 1, Last Report: 2015-02-12 11:41:49)
        put("40471|BSNL MOBILE|40471|404-71", new ApnParameters("http://bsnlmmsc.in:8514", "10.210.10.11", 8080));

        //40492 (Report Count: 1, Last Report: 2015-03-17 05:08:57)
        put("40492|Airtel Mumbai|40492|40492", new ApnParameters("http://100.1.201.171:10021/mmsc", "100.1.201.172", 8799));

        //410-01 (Report Count: 1, Last Report: 2015-01-24 17:20:22)
        put("41001|Mobilink|41001|410-01", new ApnParameters("http://mms/", "172.25.20.12", 8080));

        //425 02 (Report Count: 3, Last Report: 2015-01-23 11:28:12)
        put("42502|Cellcom|42502|425 02", new ApnParameters("http://mms.cellcom.co.il", "vwapm2.ain.co.il", 8080));

        //42902 (Report Count: 1, Last Report: 2015-01-26 09:40:21)
        put("42902|Ncell|42902|42902", new ApnParameters("http://10.68.1.149", "10.68.1.149", 8080));

        //48 (Report Count: 1, Last Report: 2014-11-29 18:10:55)
        put("27211|48|27202|48", new ApnParameters("http://mmc1/servlets/mms", "10.1.11.19", 8080));

        //4G SingTel (Report Count: 10, Last Report: 2015-03-21 16:17:01)
        put("52501|SingTel|52501|4G SingTel", new ApnParameters("http://mms.singtel.com:10021/mmsc", "165.21.42.84", 8080));

        //52003 (Report Count: 1, Last Report: 2015-03-03 15:57:57)
        put("52003|AIS|52003|52003", new ApnParameters("http://mms.mobilelife.co.th", "203.170.229.34", 8080));

        //525 01 (Report Count: 2, Last Report: 2015-02-18 14:13:49)
        put("52501|SingTel|52501|525 01", new ApnParameters("http://mms.singtel.com:10021/mmsc", "165.21.42.84", 8080));

        //8.ta (Report Count: 2, Last Report: 2014-10-09 13:41:50)
        put("65502|TelkomSA|65502|8.ta", new ApnParameters("http://mms.8ta.com:38090/was", "41.151.254.162", 8080));

        //8ta (Report Count: 1, Last Report: 2014-12-03 16:10:38)
        put("65502|8.ta|65502|8ta", new ApnParameters("http://mms.8ta.com:38090/was", "41.151.254.162", 8080));

        //8ta (Report Count: 4, Last Report: 2015-02-26 08:02:32)
        put("65502|TelkomSA|65502|8ta", new ApnParameters("http://mms.8ta.com:38090/was", "41.151.254.162", 8080));

        //A1 (Report Count: 41, Last Report: 2015-04-05 05:58:55)
        put("23201||23201|A1", new ApnParameters("http://mmsc.a1.net", "194.48.124.71", 8001));

        //A1 (Report Count: 1, Last Report: 2014-11-09 14:47:05)
        put("23211||23201|A1", new ApnParameters("http://mmsc.bob.at", "194.48.124.7", 8001));

        //ACS (Report Count: 3, Last Report: 2015-02-02 22:22:26)
        put("311370||311370|ACS", new ApnParameters("http://mmsc.gci.csky.us:6672", "209.4.229.92", 9201));

        //Aero2 (Report Count: 1, Last Report: 2015-01-15 17:51:39)
        put("26017||26001|Aero2", new ApnParameters("http://mms.orange.pl", "192.168.6.104", 8080));

        //aio (Report Count: 1, Last Report: 2015-02-28 04:51:17)
        put("310150||310410|aio", new ApnParameters("http://mmsc.aiowireless.net", "proxy.aiowireless.net", 80));

        //AIRCEL (Report Count: 1, Last Report: 2015-03-07 05:43:54)
        put("40417|AIRCEL|40417|AIRCEL", new ApnParameters("http://10.50.1.166/servlets/mms", "172.17.83.69", 8080));

        //Aircel (Report Count: 1, Last Report: 2014-11-02 15:18:41)
        put("40435|AIRCEL|40435|Aircel", new ApnParameters("http://172.17.83.67/servlets/mms", "172.17.83.69", 8080));

        //AIRCEL (Report Count: 2, Last Report: 2015-02-05 03:09:58)
        put("40442|AIRCEL|40442|AIRCEL", new ApnParameters("http://10.4.42.21:8002/", "10.4.42.15", 8080));

        //Aircel (Report Count: 2, Last Report: 2015-02-18 07:58:56)
        put("40442|AIRCEL|40442|Aircel", new ApnParameters("http://100.1.201.171:10021/mmsc", "100.1.201.172", 8799));

        //Aircel (Report Count: 1, Last Report: 2015-03-02 15:34:10)
        put("40491|AIRCEL|40491|Aircel", new ApnParameters("http://172.17.83.67/servlets/mms", "172.17.83.69", 8080));

        //AIRCEL (Report Count: 1, Last Report: 2014-10-22 11:18:32)
        put("405800|AIRCEL|405800|AIRCEL", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Aircel (Report Count: 1, Last Report: 2015-01-11 04:37:50)
        put("405801|AIRCEL|405801|Aircel", new ApnParameters("http://mmsc/mmrelay.app", "192.168.35.196", 8081));

        //AIRCEL (Report Count: 1, Last Report: 2014-09-20 14:59:23)
        put("405803|AIRCEL|405803|AIRCEL", new ApnParameters("http://172.17.83.67/servlets/mms", "172.17.83.69", 8080));

        //Aircel (Report Count: 1, Last Report: 2014-12-09 18:48:04)
        put("405803|AIRCEL|405803|Aircel", new ApnParameters("http://mmsc/mmrelay.app", "192.168.35.196", 8081));

        //AIRCEL (Report Count: 4, Last Report: 2015-03-27 08:07:24)
        put("405806|AIRCEL|405043|AIRCEL", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //AIRCEL (Report Count: 1, Last Report: 2015-02-23 17:58:45)
        put("405806|AIRCEL|405806|AIRCEL", new ApnParameters("http://100.1.201.171:10021/mmsc", "100.1.201.172", 8799));

        //AIRCEL (Report Count: 1, Last Report: 2015-04-01 04:41:44)
        put("405810|AIRCEL|405810|AIRCEL", new ApnParameters("http://10.4.42.21:8002", "10.4.42.15", 8080));

        //Aircel Chennai (Report Count: 1, Last Report: 2014-11-07 12:14:13)
        put("40441|AIRCEL|40441|Aircel Chennai", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Aircel Kolkata (Report Count: 1, Last Report: 2015-02-08 06:18:07)
        put("40491|Aircel|40491|Aircel Kolkata", new ApnParameters("http://172.17.83.67/servlets/mms", "172.17.83.69", 8080));

        //Aircel Mumbai (Report Count: 1, Last Report: 2015-01-21 19:28:22)
        put("405805|AIRCEL|405805|Aircel Mumbai", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Aircel T.N. (Report Count: 1, Last Report: 2015-01-24 10:41:22)
        put("40442|AIRCEL|40442|Aircel T.N.", new ApnParameters("http://172.17.83.67/servlets/mms", "172.17.83.69", 8080));

        //airtel (Report Count: 1, Last Report: 2014-12-20 05:21:26)
        put("40402|Airtel|40402|airtel", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //airtel (Report Count: 9, Last Report: 2015-02-14 10:11:38)
        put("40402|airtel|40402|airtel", new ApnParameters("http://100.1.201.171:10021/mmsc", "100.1.201.172", 8799));

        //airtel (Report Count: 1, Last Report: 2015-03-05 06:50:12)
        put("40403|airtel|40403|airtel", new ApnParameters("http://100.1.201.171:10021/mmsc", "100.1.201.172", 8799));

        //Airtel (Report Count: 10, Last Report: 2015-03-04 14:02:44)
        put("40410|airtel|40410|Airtel", new ApnParameters("http://100.1.201.171:10021/mmsc", "100.1.201.172", 8799));

        //AIRTEL (Report Count: 1, Last Report: 2014-12-24 08:23:01)
        put("40410|airtel|40470|AIRTEL", new ApnParameters("http://100.1.201.171:10021/mmsc", "100.1.201.172", 8799));

        //Airtel (Report Count: 1, Last Report: 2015-03-06 06:20:39)
        put("40416|Airtel|40416|Airtel", new ApnParameters("http://10.50.1.166/servlets/mms", "172.17.83.69", 8080));

        //airtel (Report Count: 1, Last Report: 2015-03-06 07:26:46)
        put("40416|airtel|40416|airtel", new ApnParameters("http://100.1.201.171:10021/mmsc", "100.1.201.172", 8799));

        //Airtel (Report Count: 1, Last Report: 2015-03-10 23:07:30)
        put("40431|airtel|40431|Airtel", new ApnParameters("http://100.1.201.171:10021/mmsc", "100.1.201.172", 8799));

        //airtel (Report Count: 1, Last Report: 2015-01-12 15:06:43)
        put("40431|airtel|40431|airtel", new ApnParameters("http://100.1.201.171:10021/mmsc/", "100.1.201.172", 8799));

        //Airtel (Report Count: 5, Last Report: 2015-02-14 10:29:32)
        put("40440|Airtel|40440|Airtel", new ApnParameters("http://100.1.201.171:10021/mmsc", "100.1.201.172", 8799));

        //Airtel (Report Count: 1, Last Report: 2014-11-03 18:41:34)
        put("40440|airtel|40440|Airtel", new ApnParameters("http://10.4.42.21:8002/", "10.4.42.15", 8080));

        //airtel (Report Count: 23, Last Report: 2015-04-03 10:23:17)
        put("40445|Airtel|40445|airtel", new ApnParameters("http://100.1.201.171:10021/mmsc", "100.1.201.172", 8799));

        //Airtel (Report Count: 6, Last Report: 2015-03-18 13:38:31)
        put("40449|airtel|40449|Airtel", new ApnParameters("http://100.1.201.171:10021/mmsc", "100.1.201.172", 8799));

        //Airtel (Report Count: 4, Last Report: 2015-03-22 14:58:13)
        put("40470|Airtel|40470|Airtel", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //AirTel (Report Count: 1, Last Report: 2015-01-01 04:59:23)
        put("40470|CellOne|40470|AirTel", new ApnParameters("http://100.1.201.171:10021/mmsc", "100.1.201.172", 8799));

        //airtel (Report Count: 18, Last Report: 2015-03-22 09:53:24)
        put("40470|airtel|40470|airtel", new ApnParameters("http://100.1.201.171:10021/mmsc", "100.1.201.172", 8799));

        //AirTel (Report Count: 1, Last Report: 2014-12-23 11:51:05)
        put("40490|AirTel|40490|AirTel", new ApnParameters("http://100.1.201.171:10021/mmsc/", "100.1.201.172", 8799));

        //AirTel (Report Count: 3, Last Report: 2015-01-26 11:48:46)
        put("40490|airtel|40490|AirTel", new ApnParameters("http://100.1.201.171:10021/mmsc", "100.1.201.172", 8799));

        //airtel (Report Count: 2, Last Report: 2015-03-01 17:02:53)
        put("40490|airtel|40490|airtel", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //AirTel (Report Count: 1, Last Report: 2014-11-10 13:30:48)
        put("40491|AIRCEL|40491|AirTel", new ApnParameters("http://10.50.1.166/servlets/mms", "172.17.83.69", 8080));

        //airtel (Report Count: 13, Last Report: 2015-04-08 12:54:43)
        put("40492|airtel|40492|airtel", new ApnParameters("http://100.1.201.171:10021/mmsc", "100.1.201.172", 8799));

        //AirTel (Report Count: 1, Last Report: 2014-12-13 18:07:11)
        put("40492||40492|AirTel", new ApnParameters("http://100.1.201.171:10021/mmsc", "100.1.201.172", 8799));

        //Airtel (Report Count: 3, Last Report: 2015-03-09 12:50:09)
        put("40493|airtel|40478|Airtel", new ApnParameters("http://100.1.201.171:10021/mmsc", "100.1.201.172", 8799));

        //AirTel (Report Count: 2, Last Report: 2014-12-08 12:14:39)
        put("40493|airtel|40493|AirTel", new ApnParameters("http://100.1.201.171:10021/mmsc", "100.1.201.172", 8799));

        //airtel (Report Count: 11, Last Report: 2015-04-01 12:25:28)
        put("40494|airtel|40494|airtel", new ApnParameters("http://100.1.201.171:10021/mmsc", "100.1.201.172", 8799));

        //AirTel (Report Count: 1, Last Report: 2015-02-26 15:22:15)
        put("40495|Airtel|40495|AirTel", new ApnParameters("http://100.1.201.171:10021/mmsc", "100.1.201.172", 8799));

        //airtel (Report Count: 1, Last Report: 2015-01-26 06:41:57)
        put("40495|airtel|40495|airtel", new ApnParameters("http://100.1.201.171:10021/mmsc/", "100.1.201.172", 8799));

        //airtel (Report Count: 1, Last Report: 2014-12-09 18:16:19)
        put("40495||40495|airtel", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //airtel (Report Count: 1, Last Report: 2015-02-14 06:54:53)
        put("40496|airtel|40401|airtel", new ApnParameters("http://100.1.201.171:10021/mmsc", "100.1.201.172", 8799));

        //Airtel (Report Count: 3, Last Report: 2015-03-13 07:49:33)
        put("40496|airtel|40496|Airtel", new ApnParameters("http://100.1.201.171:10021/mmsc", "100.1.201.172", 8799));

        //AirTel (Report Count: 1, Last Report: 2015-03-11 07:58:00)
        put("40497|airtel|40470|AirTel", new ApnParameters("http://100.1.201.171:10021/mmsc", "100.1.201.172", 8799));

        //Airtel (Report Count: 3, Last Report: 2015-02-15 03:38:58)
        put("40497|airtel|40497|Airtel", new ApnParameters("http://100.1.201.171:10021/mmsc/", "100.1.201.172", 8799));

        //airtel (Report Count: 12, Last Report: 2015-03-20 09:35:27)
        put("40497|airtel|40497|airtel", new ApnParameters("http://100.1.201.171:10021/mmsc", "100.1.201.172", 8799));

        //airtel (Report Count: 2, Last Report: 2014-12-24 12:23:57)
        put("40498|airtel|40498|airtel", new ApnParameters("http://100.1.201.171:10021/mmsc", "100.1.201.172", 8799));

        //AirTel (Report Count: 3, Last Report: 2015-03-09 13:50:06)
        put("40551|airtel|40551|AirTel", new ApnParameters("http://100.1.201.171:10021/mmsc", "100.1.201.172", 8799));

        //Airtel (Report Count: 4, Last Report: 2015-03-19 13:15:04)
        put("40552|airtel|40552|Airtel", new ApnParameters("http://100.1.201.171:10021/mmsc", "100.1.201.172", 8799));

        //Airtel (Report Count: 1, Last Report: 2014-12-04 08:15:51)
        put("40554|airtel|40410|Airtel", new ApnParameters("http://10.4.42.21:8002/", "10.4.42.15", 8080));

        //airtel (Report Count: 1, Last Report: 2015-02-03 09:48:15)
        put("40554|airtel|40554|airtel", new ApnParameters("http://10.4.42.21:8002/", "10.4.42.15", 8080));

        //airtel (Report Count: 2, Last Report: 2015-03-10 23:55:12)
        put("40555|airtel|40555|airtel", new ApnParameters("http://100.1.201.171:10021/mmsc", "100.1.201.172", 8799));

        //Airtel (Report Count: 4, Last Report: 2014-12-24 10:57:24)
        put("40556|airtel|40556|Airtel", new ApnParameters("http://100.1.201.171:10021/mmsc", "100.1.201.172", 8799));

        //Airtel (Report Count: 1, Last Report: 2014-12-17 14:11:28)
        put("40566|Vodafone IN|40497|Airtel", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //airtel (Report Count: 5, Last Report: 2015-03-20 18:25:11)
        put("41305|Airtel|41305|airtel", new ApnParameters("http://mmsc/", "10.200.184.86", 8080));

        //airtel (Report Count: 2, Last Report: 2015-03-04 13:10:52)
        put("41305|airtel|41305|airtel", new ApnParameters("http://mms.airtel.lk", "10.200.184.86", 8080));

        //Airtel (Report Count: 1, Last Report: 2015-01-17 15:03:58)
        put("62120|Airtel NG|62120|Airtel", new ApnParameters("http://mms.gloworld.com/mmsc", "10.100.114.144", 3130));

        //AIRTEL (Report Count: 1, Last Report: 2015-01-22 14:57:46)
        put("63903|Airtel KE|63903|AIRTEL", new ApnParameters("http://www.safaricom.com", "172.22.2.38", 8080));

        //AIRTEL (Report Count: 2, Last Report: 2015-03-01 07:04:18)
        put("64005|Airtel TZ|64005|AIRTEL", new ApnParameters("http://mms.tz.airtel.com:8002", "10.87.248.136", 8080));

        //Airtel A.P. (Report Count: 5, Last Report: 2015-03-05 10:45:01)
        put("40449|airtel|40449|Airtel A.P.", new ApnParameters("http://100.1.201.171:10021/mmsc/", "100.1.201.172", 8799));

        //Airtel Bihar (Report Count: 1, Last Report: 2014-11-15 07:17:21)
        put("40552|airtel|40552|Airtel Bihar", new ApnParameters("http://100.1.201.171:10021/mmsc", "100.1.201.172", 8799));

        //Airtel Chennai (Report Count: 1, Last Report: 2015-04-04 07:06:36)
        put("40440|airtel|40440|Airtel Chennai", new ApnParameters("http://100.1.201.171:10021/mmsc/", "100.1.201.172", 8799));

        //Airtel Delhi (Report Count: 1, Last Report: 2014-12-25 15:43:12)
        put("40410|AirTel|40410|Airtel Delhi", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Airtel Delhi (Report Count: 2, Last Report: 2015-02-13 19:23:07)
        put("40410|airtel|40410|Airtel Delhi", new ApnParameters("http://100.1.201.171:10021/mmsc/", "100.1.201.172", 8799));

        //Airtel INA (Report Count: 1, Last Report: 2014-12-25 06:10:30)
        put("40470|Airtel|40470|Airtel INA", new ApnParameters("http://100.1.201.171:10021/mmsc", "100.1.201.172", 8799));

        //Airtel Kerala (Report Count: 1, Last Report: 2015-03-19 06:18:54)
        put("40494|airtel|40495|Airtel Kerala", new ApnParameters("http://100.1.201.171:10021/mmsc/", "100.1.201.172", 8799));

        //Airtel Kerala (Report Count: 2, Last Report: 2014-12-23 10:33:38)
        put("40495|Airtel|40495|Airtel Kerala", new ApnParameters("http://100.1.201.171:10021/mmsc", "100.1.201.172", 8799));

        //Airtel Kerala (Report Count: 1, Last Report: 2014-12-15 12:29:17)
        put("40495|airtel|40495|Airtel Kerala", new ApnParameters("http://100.1.201.171:10021/mmsc/", "100.1.201.172", 8799));

        //Airtel Kolkata (Report Count: 2, Last Report: 2015-02-14 04:52:11)
        put("40431|Airtel|40431|Airtel Kolkata", new ApnParameters("http://100.1.201.171:10021/mmsc/", "100.1.201.172", 8799));

        //Airtel Kolkata (Report Count: 1, Last Report: 2014-10-27 14:21:34)
        put("40431|airtel|40431|Airtel Kolkata", new ApnParameters("http://100.1.201.171:10021/mmsc", "100.1.201.172", 8799));

        //Airtel Mumbai (Report Count: 3, Last Report: 2015-04-03 09:48:39)
        put("40492|AirTel|40492|Airtel Mumbai", new ApnParameters("http://100.1.201.171:10021/mmsc/", "100.1.201.172", 8799));

        //Airtel Mumbai (Report Count: 1, Last Report: 2015-03-05 12:28:42)
        put("40492|Airtel Mumbai|40492|Airtel Mumbai", new ApnParameters("http://100.1.201.171:10021/mmsc", "100.1.201.172", 8799));

        //Airtel NG (Report Count: 2, Last Report: 2015-01-06 12:21:19)
        put("62120|Airtel NG|62120|Airtel NG", new ApnParameters("http://10.199.212.8/servlets/mms", "10.199.212.2", 8080));

        //Airtel Rajasthan (Report Count: 1, Last Report: 2014-08-19 03:42:24)
        put("40470|airtel|40470|Airtel Rajasthan", new ApnParameters("http://100.1.201.171:10021/mmsc", "100.1.201.172", 8799));

        //Airtel T.N. (Report Count: 2, Last Report: 2015-01-30 11:13:19)
        put("40440|Airtel|40494|Airtel T.N.", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Airtel T.N. (Report Count: 2, Last Report: 2015-02-16 13:33:06)
        put("40494|airtel|40494|Airtel T.N.", new ApnParameters("http://100.1.201.171:10021/mmsc", "100.1.201.172", 8799));

        //Airtel U.P.(W) (Report Count: 3, Last Report: 2015-03-26 06:01:50)
        put("40497|airtel|40497|Airtel U.P.(W)", new ApnParameters("http://100.1.201.171:10021/mmsc/", "100.1.201.172", 8799));

        //Airtel UP East (Report Count: 1, Last Report: 2014-06-23 17:48:42)
        put("40497|airtel|40554|Airtel UP East", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Airtel UP East (Report Count: 2, Last Report: 2015-02-19 16:56:43)
        put("40554|airtel|40554|Airtel UP East", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //AirTel,Etisalat (Report Count: 1, Last Report: 2014-12-24 16:25:25)
        put("41305|Airtel|41305|AirTel,Etisalat", new ApnParameters("http://mmsc/", "10.200.184.86", 8080));

        //AIRVOICE WIRELESS (Report Count: 9, Last Report: 2015-04-07 17:44:35)
        put("310410||310410|AIRVOICE WIRELESS", new ApnParameters("http://mmsc.cingular.com", "66.209.11.33", 80));

        //AIS (Report Count: 1, Last Report: 2014-12-19 08:46:31)
        put("52003|AIS 2100|52001|AIS", new ApnParameters("http://mms.mobilelife.co.th", "203.170.229.34", 8080));

        //AIS (Report Count: 8, Last Report: 2015-03-10 11:01:56)
        put("52003|AIS|52001|AIS", new ApnParameters("http://mms.mobilelife.co.th", "203.170.229.34", 8080));

        //AIS (Report Count: 115, Last Report: 2015-04-06 11:01:56)
        put("52003|AIS|52003|AIS", new ApnParameters("http://mms.ais.co.th", "203.170.229.34", 8080));

        //AIS (Report Count: 2, Last Report: 2015-03-22 12:23:59)
        put("52003||52003|AIS", new ApnParameters("http://mms.mobilelife.co.th", "203.170.229.34", 8080));

        //AIS 2100 (Report Count: 9, Last Report: 2015-03-29 06:42:02)
        put("52003|AIS|52003|AIS 2100", new ApnParameters("http://mms.mobilelife.co.th", "203.170.229.34", 8080));

        //AIS 2100 3G (Report Count: 2, Last Report: 2015-04-05 06:53:11)
        put("52003|AIS|52003|AIS 2100 3G", new ApnParameters("http://mms.mobilelife.co.th", "203.170.229.34", 8080));

        //AIS 3G (Report Count: 3, Last Report: 2015-03-20 06:48:11)
        put("52003|AIS|52003|AIS 3G", new ApnParameters("http://mms.ais.co.th", "203.170.229.34", 8080));

        //AIS2100 (Report Count: 1, Last Report: 2014-12-12 05:51:42)
        put("52003|AIS|52003|AIS2100", new ApnParameters("http://mms.ais.co.th", "203.170.229.34", 8080));

        //Al jawal (Report Count: 1, Last Report: 2015-02-25 12:05:43)
        put("42001||42001|Al jawal", new ApnParameters("http://mms.net.sa:8002/", "10.1.1.1", 8080));

        //alaskacomm (Report Count: 1, Last Report: 2015-03-02 23:04:03)
        put("310050|alaskacomm|310000|", new ApnParameters("http://mmsc1.acsalaska.net/servlets/mms", null, null));

        //alaskacomm (Report Count: 1, Last Report: 2015-03-18 08:41:10)
        put("310050|alaskacomm|31000|", new ApnParameters("http://mmsc1.acsalaska.net/servlets/mms", null, null));

        //ALDI mobile (Report Count: 1, Last Report: 2015-01-02 09:08:17)
        put("22802|ALDI mobile|22802|ALDI mobile", new ApnParameters("http://mmsc.sunrise.ch", "212.35.34.75", 8080));

        //ALDImobile (Report Count: 1, Last Report: 2014-10-07 02:28:10)
        put("50501|ALDImobile|50501|ALDImobile", new ApnParameters("http://mmsc.telstra.com:8002", "10.1.1.180", 80));

        //alfa (Report Count: 1, Last Report: 2015-03-29 16:21:24)
        put("41501||41501|alfa", new ApnParameters("http://mms.mic1.com.lb", "192.168.23.51", 8080));

        //alltel2 (Report Count: 3, Last Report: 2015-01-01 15:01:16)
        put("310330|alltel2|310000|", new ApnParameters("http://mms.alltel.com/servlets/mms", "mms.alltel.com", 8080));

        //ALTEL (Report Count: 2, Last Report: 2015-01-26 05:04:57)
        put("50219|ALTEL|50219|ALTEL", new ApnParameters("http://mms.celcom.net.my", "10.128.1.242", 8080));

        //amaysim (Report Count: 7, Last Report: 2015-02-03 14:17:07)
        put("50502|amaysim|50502|amaysim", new ApnParameters("http://mmsc.optus.com.au:8002/", "61.88.190.10", 8070));

        //ANTEL (Report Count: 2, Last Report: 2014-11-27 03:22:45)
        put("74801|Antel|74801|ANTEL", new ApnParameters("http://mmsc.mms.ancelutil.com.uy", "200.40.246.2", 3128));

        //Antel (Report Count: 1, Last Report: 2015-01-02 18:26:18)
        put("74801|Antel|74801|Antel", new ApnParameters("http://mmsc.mms.ancelutil.com.uy", null, null));

        //AT (Report Count: 1, Last Report: 2014-09-22 08:10:01)
        put("310260|Verizon|310260|AT", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //AT T (Report Count: 2, Last Report: 2014-11-22 16:17:55)
        put("310410||310410|AT T", new ApnParameters("http://mms-tf.net", "mms3.tracfone.com", 80));

        //AT&T (Report Count: 1, Last Report: 2014-10-08 20:50:03)
        put("25001||310410|AT&T", new ApnParameters("http://mmsc.cingular.com", "wireless.cingular.com", 80));

        //AT&T (Report Count: 2, Last Report: 2015-02-13 21:32:10)
        put("302220||310410|AT&T", new ApnParameters("http://aliasredirect.net/proxy/koodo/mmsc", "74.49.0.18", 80));

        //AT&T (Report Count: 1, Last Report: 2014-08-15 20:10:32)
        put("302370||310410|AT&T", new ApnParameters("http://mms.fido.ca", "mmsproxy.fido.ca", 80));

        //AT&T (Report Count: 1, Last Report: 2014-09-28 19:53:25)
        put("302500|Videotron|310410|AT&T", new ApnParameters("http://media.videotron.com", null, null));

        //AT&T (Report Count: 1, Last Report: 2014-11-18 19:51:12)
        put("302720|ROGERS AT&T|310410|AT&T", new ApnParameters("http://mms.gprs.rogers.com", "mmsproxy.rogers.com", 80));

        //AT&T (Report Count: 2, Last Report: 2015-03-03 19:23:12)
        put("310150|Sprint|310410|AT&T", new ApnParameters("http://mmsc.aiowireless.net", "proxy.aiowireless.net", 80));

        //AT&T (Report Count: 120, Last Report: 2015-04-08 02:11:38)
        put("310150|Verizon|310410|AT&T", new ApnParameters("http://mmsc.aiowireless.net", "proxy.aiowireless.net", 80));

        //AT&T (Report Count: 3, Last Report: 2015-01-13 04:54:45)
        put("310150|cricket|310410|AT&T", new ApnParameters("http://mmsc.aiowireless.net", "proxy.aiowireless.net", 80));

        //AT&T (Report Count: 32, Last Report: 2015-04-07 17:53:29)
        put("310150||310410|AT&T", new ApnParameters("http://mmsc.aiowireless.net", "proxy.aiowireless.net", 80));

        //AT&T (Report Count: 1, Last Report: 2014-08-19 00:08:49)
        put("310260|T-Mobile|310260|AT&T", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //AT&T (Report Count: 3, Last Report: 2015-02-16 18:19:37)
        put("310260|Verizon|310260|AT&T", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //AT&T (Report Count: 61, Last Report: 2015-04-08 02:28:30)
        put("310260||310260|AT&T", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //AT&T (Report Count: 9, Last Report: 2015-03-29 00:49:18)
        put("310260||310410|AT&T", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //AT&T (Report Count: 2, Last Report: 2015-01-01 01:40:18)
        put("310410|AIRVOICE WIRELESS|310410|AT&T", new ApnParameters("http://mmsc.cingular.com", "66.209.11.33", 80));

        //AT&T (Report Count: 3, Last Report: 2014-12-23 18:18:56)
        put("310410|AT&T@|310410|AT&T", new ApnParameters("http://mmsc.cingular.com", "wireless.cingular.com", 80));

        //AT&T (Report Count: 373, Last Report: 2015-04-05 19:58:04)
        put("310410|AT&T|310410|AT&T", new ApnParameters("http://mms-tf.net", "mms3.tracfone.com", 80));

        //AT&T (Report Count: 8, Last Report: 2015-03-20 18:54:36)
        put("310410|Home|310410|AT&T", new ApnParameters("http://mms-tf.net", "mms3.tracfone.com", 80));

        //AT&T (Report Count: 2, Last Report: 2014-11-01 17:25:13)
        put("310410|My Network|310410|AT&T", new ApnParameters("http://mmsc.cingular.com", "66.209.11.33", 80));

        //AT&T (Report Count: 1, Last Report: 2015-04-08 16:18:50)
        put("310410|Pure GSM|310410|AT&T", new ApnParameters("http://mmsc.cingular.com", "66.209.11.33", 80));

        //AT&T (Report Count: 3, Last Report: 2015-03-15 20:56:55)
        put("310410|Sprint|310410|AT&T", new ApnParameters("http://mms-tf.net", "mms3.tracfone.com", 80));

        //AT&T (Report Count: 36, Last Report: 2015-04-03 22:41:24)
        put("310410|Verizon|310410|AT&T", new ApnParameters("http://mms-tf.net", "mms3.tracfone.com", 80));

        //AT&T (Report Count: 1, Last Report: 2015-03-21 16:00:52)
        put("310410||310100|AT&T", new ApnParameters("http://mmsc.mobile.att.net", "proxy.mobile.att.net", 80));

        //AT&T (Report Count: 6279, Last Report: 2015-04-08 16:29:01)
        put("310410||310410|AT&T", new ApnParameters("http://mmsc.mobile.att.net", "proxy.mobile.att.net", 80));

        //AT&T (Report Count: 1, Last Report: 2015-01-20 12:24:38)
        put("310410||310570|AT&T", new ApnParameters("http://mmsc.cingular.com", "wireless.cingular.com", 80));

        //AT&T (Report Count: 1, Last Report: 2014-12-10 19:51:44)
        put("310410||311040|AT&T", new ApnParameters("http://mmsc.mobile.att.net", "proxy.mobile.att.net", 80));

        //AT&T (Report Count: 1, Last Report: 2015-04-05 11:53:53)
        put("338050|DIGICEL|310410|AT&T", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //AT&T (Report Count: 1, Last Report: 2014-11-22 17:49:27)
        put("722310|Claro AR|310410|AT&T", new ApnParameters("http://mms.claro.com.ar", null, null));

        //AT&T MicroCell (Report Count: 56, Last Report: 2015-03-29 23:41:53)
        put("310410||310410|AT&T MicroCell", new ApnParameters("http://mmsc.mobile.att.net", "proxy.mobile.att.net", 80));

        //ATT (Report Count: 1, Last Report: 2015-01-18 00:12:16)
        put("310150||310410|ATT", new ApnParameters("http://mmsc.aiowireless.net", "proxy.aiowireless.net", 80));

        //ATT (Report Count: 2, Last Report: 2014-10-09 00:27:19)
        put("310410||310410|ATT", new ApnParameters("http://mmsc.cingular.com", "wireless.cingular.com", 80));

        //AVEA (Report Count: 10, Last Report: 2015-02-18 20:11:31)
        put("28603|AVEA|28603|AVEA", new ApnParameters("http://mms.avea.com.tr/servlets/mms", "213.161.151.201", 8080));

        //AVEA (Report Count: 1, Last Report: 2014-12-29 08:03:34)
        put("28603|Pttcell|28603|AVEA", new ApnParameters("http://mms.avea.com.tr/servlets/mms", "213.161.151.201", 8080));

        //AVEA (Report Count: 2, Last Report: 2015-02-18 18:57:02)
        put("28603||28603|AVEA", new ApnParameters("http://mms.avea.com.tr/servlets/mms", "213.161.151.201", 8080));

        //axis (Report Count: 2, Last Report: 2014-09-19 15:48:47)
        put("51008|AXIS|51008|axis", new ApnParameters("http://wap.axisworld.co.id", null, null));

        //Axis (Report Count: 1, Last Report: 2014-12-28 10:36:23)
        put("51011|Axis|51011|Axis", new ApnParameters("http://mmc.xl.net.id/servlets/mms", "202.152.240.50", -1));

        //AXIS+ (Report Count: 1, Last Report: 2014-12-30 12:19:50)
        put("51008|AXIS+|51011|AXIS+", new ApnParameters("http://mms.telkomsel.com", "10.1.89.150", 8000));

        //AY YILDIZ (Report Count: 1, Last Report: 2015-01-06 19:57:46)
        put("26203|AY YILDIZ|26203|AY YILDIZ", new ApnParameters("http://mms/eplus/", "212.23.97.153", 5080));

        //AZE - AZERCELL GSM (Report Count: 1, Last Report: 2015-02-02 09:30:53)
        put("40001|AZE - AZERCELL GSM|40001|AZE - AZERCELL GSM", new ApnParameters("http://mms.azercell.com/cMMSC/post", "10.0.154.101", 8080));

        //AZE - AZERCELL GSM (Report Count: 1, Last Report: 2014-12-12 10:27:52)
        put("40001|Azercell|40001|AZE - AZERCELL GSM", new ApnParameters("http://mms.azercell.com/cMMSC/post", "10.0.154.101", 8080));

        //AZE - AZERCELL GSM (Report Count: 1, Last Report: 2015-01-22 18:35:49)
        put("40001|SimSim|40001|AZE - AZERCELL GSM", new ApnParameters("http://mms.azercell.com/cMMSC/post", "10.0.154.101", 8080));

        //B Mobistar (Report Count: 2, Last Report: 2014-12-31 19:30:33)
        put("20610||20610|B Mobistar", new ApnParameters("http://mmsc.mobistar.be", "212.65.63.143", 8080));

        //b2c.de (Report Count: 1, Last Report: 2015-03-26 20:49:58)
        put("26207|b2c.de|26207|b2c.de", new ApnParameters("http://10.81.0.7:8002/", "82.113.100.43", 8080));

        //b2c.de (Report Count: 1, Last Report: 2015-03-23 15:52:10)
        put("26207||26207|b2c.de", new ApnParameters("http://10.81.0.7:8002", "82.113.100.5", 8080));

        //BAKCELL (Report Count: 1, Last Report: 2015-03-04 17:08:43)
        put("40002|CIN Kart|40002|BAKCELL", new ApnParameters("http://mmsc.bakcell.com", null, null));

        //BAKCELL (Report Count: 3, Last Report: 2014-12-20 06:27:34)
        put("40002|Cin|40002|BAKCELL", new ApnParameters("http://mmsc.bakcell.com", null, null));

        //Banglalink (Report Count: 20, Last Report: 2015-03-31 13:45:55)
        put("47003|Banglalink|47003|Banglalink", new ApnParameters("http://mmsc1:10021/mmsc/01", "10.10.55.34", 8799));

        //Banglalink (Report Count: 1, Last Report: 2014-10-06 09:34:52)
        put("47003|Banglalink||", new ApnParameters("http://mms.gpsurf.net/servlets/mms", "10.128.1.2", 8080));

        //BASE (Report Count: 3, Last Report: 2015-03-27 17:34:18)
        put("20620|BASE|20620|BASE", new ApnParameters("http://mmsc.base.be", "217.72.235.1", 8080));

        //BASE DE (Report Count: 1, Last Report: 2015-01-22 05:39:51)
        put("26203|BASE DE|26203|BASE DE", new ApnParameters("http://mms/eplus", "212.23.97.153", 8080));

        //BASE DE (Report Count: 1, Last Report: 2015-03-20 15:10:25)
        put("26203||26203|BASE DE", new ApnParameters("http://mms/eplus/", "212.23.97.153", 5080));

        //Beeline (Report Count: 93, Last Report: 2015-04-06 07:38:27)
        put("25099|Beeline|25099|Beeline", new ApnParameters("http://mms/", "192.168.94.23", 8080));

        //Beeline (Report Count: 2, Last Report: 2015-02-07 19:32:58)
        put("25099||25099|Beeline", new ApnParameters("http://mms/", "192.168.94.23", 8080));

        //Beeline (Report Count: 1, Last Report: 2015-03-25 18:12:50)
        put("40101||25099|Beeline", new ApnParameters("http://mmsc", "192.168.192.192", 8080));

        //Beeline,Beeline (Report Count: 1, Last Report: 2015-03-08 07:54:17)
        put("25099|Beeline|25099|Beeline,Beeline", new ApnParameters("http://mms/", "192.168.94.23", 8080));

        //Beeline@ (Report Count: 1, Last Report: 2014-11-11 10:59:08)
        put("25099|Beeline|25099|Beeline@", new ApnParameters("http://mms/", "192.168.94.23", 8080));

        //Bell (Report Count: 1, Last Report: 2015-03-04 00:12:27)
        put("302610|Bell|302610|Bell", new ApnParameters("http://mms.bell.ca/mms/wapenc", "web.wireless.bell.ca", 80));

        //Bell (Report Count: 3, Last Report: 2015-01-29 13:35:43)
        put("302610|VIRGIN|302610|Bell", new ApnParameters("http://mms.bell.ca/mms/wapenc", "web.wireless.bell.ca", 80));

        //Bell (Report Count: 1, Last Report: 2015-02-27 13:46:39)
        put("302610|Verizon|302610|Bell", new ApnParameters("http://mms.bell.ca/mms/wapenc", null, null));

        //Bell (Report Count: 338, Last Report: 2015-04-08 15:33:37)
        put("302610||302610|Bell", new ApnParameters("http://mms.bell.ca/mms/wapenc", null, null));

        //Ben NL (Report Count: 1, Last Report: 2015-04-05 12:29:20)
        put("20416|Ben NL|20416|Ben NL", new ApnParameters("http://benmms", "10.10.10.11", 8080));

        //BG GLOBUL (Report Count: 2, Last Report: 2014-12-21 17:31:20)
        put("28405|Telenor BG|28405|BG GLOBUL", new ApnParameters("http://mmsc1.mms.globul.bg:8002", "192.168.87.11", 8004));

        //BGD ROBI AXIATA (Report Count: 1, Last Report: 2014-11-01 09:42:41)
        put("47002|Robi|47002|BGD ROBI AXIATA", new ApnParameters("http://10.16.18.40:38090/was", "10.16.18.77", 9028));

        //BH Mobile (Report Count: 2, Last Report: 2014-09-17 18:55:08)
        put("21890|BH Mobile|21890|BH Mobile", new ApnParameters("http://mms.bhmobile.ba/cmmsc/post", "195.222.56.41", 8080));

        //BHMOBILE (Report Count: 1, Last Report: 2015-03-20 10:51:55)
        put("21890|BH Mobile|21890|BHMOBILE", new ApnParameters("http://mms.bhmobile.ba/servlets/mms", "195.222.56.41", 8080));

        //BIMcell (Report Count: 1, Last Report: 2015-02-25 07:35:23)
        put("28603|BIMCell|28603|BIMcell", new ApnParameters("http://mms.avea.com.tr/servlets/mms", "213.161.151.201", 8080));

        //Black Wireless (Report Count: 3, Last Report: 2014-10-26 04:00:02)
        put("310410||310410|Black Wireless", new ApnParameters("http://mmsc.cingular.com", "66.209.11.33", 80));

        //blau (Report Count: 4, Last Report: 2015-04-06 09:25:05)
        put("26203|blau|26203|blau", new ApnParameters("http://mms/eplus/", "212.23.97.153", 5080));

        //blau.de (Report Count: 1, Last Report: 2014-12-25 20:16:19)
        put("26203||26203|blau.de", new ApnParameters("http://mms/eplus", "212.23.97.153", 5080));

        //BLESKmobil (Report Count: 1, Last Report: 2014-11-05 14:33:31)
        put("23002|BLESKmobil|23002|BLESKmobil", new ApnParameters("http://mms.o2active.cz:8002", "160.218.160.218", 8080));

        //bluegrass (Report Count: 1, Last Report: 2015-04-03 17:58:37)
        put("311440|bluegrass|311480|bluegrass", new ApnParameters("http://mms.iot1.com/bluegrass/mms.php", null, null));

        //Bluegrass Cellular (Report Count: 1, Last Report: 2014-10-04 21:25:46)
        put("310000|Verizon|311480|Bluegrass Cellular", new ApnParameters("http://m.iot1.com/bluegrass/mms.php", null, null));

        //Bluegrass Cellular (Report Count: 1, Last Report: 2015-03-16 05:29:57)
        put("311480|Bluegrass Cellular|31100|", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //bob (Report Count: 16, Last Report: 2015-04-02 10:07:55)
        put("23211||23201|bob", new ApnParameters("http://mmsc.bob.at", "194.48.124.7", 8001));

        //Boost (Report Count: 17, Last Report: 2015-04-04 16:32:16)
        put("311870|Boost Mobile|31000|Boost", new ApnParameters("null", null, null));

        //Boost (Report Count: 10, Last Report: 2015-03-14 01:50:02)
        put("311870|Boost Mobile|3307|Boost", new ApnParameters("null", null, null));

        //BOOST (Report Count: 3, Last Report: 2015-04-08 01:24:15)
        put("50501|BOOST|50501|BOOST", new ApnParameters("http://mmsc.telstra.com:8002", "10.1.1.180", 80));

        //Boost Mobile (Report Count: 2, Last Report: 2014-12-06 00:23:51)
        put("310120|Sprint|310120|Boost Mobile", new ApnParameters("http://mm.myboostmobile.com", "68.28.31.7", 80));

        //Boost Mobile (Report Count: 2, Last Report: 2015-02-28 14:57:22)
        put("310120|Sprint|311870|Boost Mobile", new ApnParameters("http://mm.myboostmobile.com", "68.28.31.7", 80));

        //Boost Mobile (Report Count: 1, Last Report: 2014-11-29 06:00:14)
        put("311870|Boost Mobile|00000|", new ApnParameters("http://mm.myboostmobile.com", "68.28.31.7", 80));

        //Boost Mobile (Report Count: 2, Last Report: 2015-04-02 03:18:39)
        put("311870|Boost Mobile|311490|Boost Mobile", new ApnParameters("http://mm.myboostmobile.com", "68.28.31.7", 80));

        //Boost Mobile (Report Count: 3745, Last Report: 2015-04-08 16:56:39)
        put("311870|Boost Mobile|311870|Boost Mobile", new ApnParameters("http://mm.myboostmobile.com", "68.28.31.7", 80));

        //Boost Mobile (Report Count: 1, Last Report: 2015-03-20 21:29:44)
        put("311870|Boost Mobile||", new ApnParameters("null", null, null));

        //Boost Mobile (Report Count: 3, Last Report: 2015-03-19 14:25:13)
        put("311870||000000|Boost Mobile", new ApnParameters("http://mm.myboostmobile.com", "68.28.31.7", 80));

        //Boost Mobile (Report Count: 1, Last Report: 2014-08-04 15:06:42)
        put("311870||31000|Boost Mobile", new ApnParameters("http://mm.myboostmobile.com", "68.28.31.7", 80));

        //Boost Mobile (Report Count: 4, Last Report: 2015-03-17 21:48:28)
        put("311870||311870|Boost Mobile", new ApnParameters("null", null, null));

        //Boost Mobile (Report Count: 2, Last Report: 2014-09-23 19:34:30)
        put("3118790|Boost Mobile|3118790|Boost Mobile", new ApnParameters("null", null, null));

        //Boost Mobile (Report Count: 2, Last Report: 2015-03-07 00:08:39)
        put("|Boost Mobile|311870|Boost Mobile", new ApnParameters("http://mm.myboostmobile.com", "68.28.31.7", 80));

        //Boost Mobile (Report Count: 1, Last Report: 2015-03-22 17:27:42)
        put("|Sprint|31000|Boost Mobile", new ApnParameters("http://mm.myboostmobile.com", "68.28.31.7", 80));

        //Boost Mobile (Report Count: 1, Last Report: 2015-03-10 20:22:14)
        put("||311870|Boost Mobile", new ApnParameters("null", null, null));

        //Bouygtel (Report Count: 1, Last Report: 2014-11-09 16:45:21)
        put("20820|BOUYGTEL|20820|Bouygtel", new ApnParameters("http://mms.bouyguestelecom.fr/mms/wapenc", "62.201.129.225", 8080));

        //Bouygtel (Report Count: 35, Last Report: 2015-04-02 10:21:16)
        put("20820|Bouygues Telecom|20820|Bouygtel", new ApnParameters("http://mms.bouyguestelecom.fr/mms/wapenc", "62.201.129.226", 8080));

        //BOUYGTEL (Report Count: 4, Last Report: 2015-02-26 12:21:20)
        put("20820||20820|BOUYGTEL", new ApnParameters("http://mms.bouyguestelecom.fr/mms/wapenc", "62.201.129.226", 8080));

        //Bouygues (Report Count: 1, Last Report: 2014-11-27 10:55:51)
        put("20820|Bouygues|20820|", new ApnParameters("http://mms.bouyguestelecom.fr/mms/wapenc", "62.201.129.226", 8080));

        //Bouygues (Report Count: 1, Last Report: 2015-02-04 08:32:18)
        put("20820|Bouygues Telecom|20820|Bouygues ", new ApnParameters("http://mms.bouyguestelecom.fr/mms/wapenc", "62.201.129.226", 8080));

        //Bouygues Telecom (Report Count: 1, Last Report: 2014-11-23 11:50:41)
        put("20820|,|20820|Bouygues Telecom", new ApnParameters("http://mms.bouyguestelecom.fr/mms/wapenc", "62.201.129.226", 8080));

        //Bouygues Telecom (Report Count: 5, Last Report: 2015-03-09 13:29:28)
        put("20820|Bouygues Telecom|20820|", new ApnParameters("http://mms.bouyguestelecom.fr/mms/wapenc", "62.201.129.226", 8080));

        //Bouygues Telecom (Report Count: 106, Last Report: 2015-04-07 18:11:17)
        put("20820|Bouygues Telecom|20820|Bouygues Telecom", new ApnParameters("http://mms.bouyguestelecom.fr/mms/wapenc", "62.201.129.226", 8080));

        //Bouygues Telecom (Report Count: 2, Last Report: 2015-01-10 19:35:13)
        put("20820|Bouygues|20820|Bouygues Telecom", new ApnParameters("http://mms.bouyguestelecom.fr/mms/wapenc", "62.201.129.226", 8080));

        //Bouygues Telecom (Report Count: 1, Last Report: 2014-12-12 12:50:36)
        put("20820|UNIVERSAL|20820|Bouygues Telecom", new ApnParameters("http://mms.bouyguestelecom.fr/mms/wapenc", "62.201.129.226", 8080));

        //Bouygues Telecom (Report Count: 5, Last Report: 2015-03-14 17:13:01)
        put("20820||20820|Bouygues Telecom", new ApnParameters("http://mms.bouyguestelecom.fr/mms/wapenc", "62.201.129.226", 8080));

        //BouyguesTel (Report Count: 1, Last Report: 2014-12-14 17:49:22)
        put("20820|Bouygues Telecom|20820|BouyguesTel", new ApnParameters("http://mms.bouyguestelecom.fr/mms/wapenc", "62.201.129.225", 8080));

        //BrightSpot (Report Count: 18, Last Report: 2015-04-06 00:23:25)
        put("310260||310260|BrightSpot", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //BSNL (Report Count: 1, Last Report: 2015-02-20 20:46:56)
        put("40459|BSNL MOBILE|40459|BSNL", new ApnParameters("http://10.4.42.21:8002/", "10.4.42.15", 8080));

        //BSNL MOBILE (Report Count: 4, Last Report: 2015-03-18 14:19:42)
        put("40458|BSNL MOBILE|40458|BSNL MOBILE", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //BSNL MOBILE (Report Count: 1, Last Report: 2015-02-02 15:32:15)
        put("40471|BSNL MOBILE|40471|BSNL MOBILE", new ApnParameters("http://bsnlmmsc.in:8514", "10.210.10.11", 8080));

        //BSNL Mobile (Report Count: 1, Last Report: 2015-02-15 14:13:19)
        put("40472|BSNL Mobile|40472|BSNL Mobile", new ApnParameters("http://bsnlmmsc.in:8514", "10.210.10.11", 8080));

        //BSNL MOBILE (Report Count: 1, Last Report: 2015-01-08 11:34:53)
        put("40473|BSNL MOBILE|40473|BSNL MOBILE", new ApnParameters("http://100.1.201.171:10021/mmsc/", "100.1.201.172", 8799));

        //BSNL Mobile (Report Count: 1, Last Report: 2015-03-21 00:37:27)
        put("40480|BSNL Mobile|40480|BSNL Mobile", new ApnParameters("http://bsnlmmsc.in:8514", "10.210.10.11", 8080));

        //BT (Report Count: 1, Last Report: 2015-03-13 21:37:31)
        put("23430||23430|BT", new ApnParameters("http://MMS/", "149.254.201.135", 8080));

        //BY VELCOM (Report Count: 1, Last Report: 2015-01-22 16:22:54)
        put("25701|velcom|25701|BY VELCOM", new ApnParameters("http://mmsc", "192.168.192.168", 8080));

        //BYTEL (Report Count: 85, Last Report: 2015-04-03 05:32:33)
        put("20820|Bouygues Telecom|20820|BYTEL", new ApnParameters("http://mms.bouyguestelecom.fr/mms/wapenc", "62.201.129.226", 8080));

        //BYTEL (Report Count: 3, Last Report: 2015-03-18 20:58:55)
        put("20820||20820|BYTEL", new ApnParameters("http://mms.bouyguestelecom.fr/mms/wapenc", "62.201.129.226", 8080));

        //C le mobile (Report Count: 4, Last Report: 2015-02-04 23:29:19)
        put("20801|C le mobile|20801|C le mobile", new ApnParameters("http://mms.orange.fr", "192.168.10.200", 8080));

        //C le mobile (Report Count: 2, Last Report: 2015-01-31 14:59:26)
        put("20810|C le mobile|20810|C le mobile ", new ApnParameters("http://mms1", "10.151.0.1", 8080));

        //C Spire (Report Count: 15, Last Report: 2015-04-06 22:41:07)
        put("311230|C Spire |311230|C Spire", new ApnParameters("http://pix.cspire.com/servlets/mms", "66.175.144.91", 80));

        //C Spire (Report Count: 6, Last Report: 2015-02-26 15:50:22)
        put("311230|C Spire|310000|", new ApnParameters("http://pix.cspire.com/servlets/mms", "66.175.144.91", 80));

        //C Spire (Report Count: 3, Last Report: 2015-04-06 21:05:23)
        put("311230|C Spire|31000|", new ApnParameters("http://pix.cspire.com/servlets/mms", "66.175.144.91", 80));

        //C Spire (Report Count: 1, Last Report: 2015-04-06 16:46:00)
        put("311230|C Spire|31000|C Spire", new ApnParameters("http://pix.cspire.com/servlets/mms", "66.175.144.91", 80));

        //C Spire (Report Count: 1, Last Report: 2015-03-13 16:51:57)
        put("311230|C Spire|3307|", new ApnParameters("http://pix.cspire.com/servlets/mms", "66.175.144.91", 80));

        //C Spire (Report Count: 5, Last Report: 2015-02-22 06:07:40)
        put("311230|C Spire|3307|C Spire", new ApnParameters("http://pix.cspire.com/servlets/mms:80", null, null));

        //C Spire (Report Count: 13, Last Report: 2015-03-21 03:34:16)
        put("311230|C-Spire|310120|C Spire", new ApnParameters("http://pix.cspire.com/servlets/mms", "66.175.144.91", 80));

        //C Spire (Report Count: 1, Last Report: 2015-03-14 23:48:15)
        put("311230||311230|C Spire", new ApnParameters("", null, null));

        //C Spire (Report Count: 1, Last Report: 2015-01-30 15:59:37)
        put("311230|C Spire |311230|C Spire ", new ApnParameters("http://pix.cspire.com", null, null));

        //callmobile.de (Report Count: 2, Last Report: 2015-01-01 10:35:29)
        put("26201|callmobile.de|26201| ", new ApnParameters("http://mms.t-mobile.de/servlets/mms", "172.28.23.131", 8008));

        //CamGSM (Report Count: 1, Last Report: 2015-02-19 00:18:30)
        put("45601|Cellcard|45601|CamGSM ", new ApnParameters("http://mms.mobitel.com.kh/mmsc", "203.144.95.98", 3130));

        //CamGSM (Report Count: 1, Last Report: 2014-09-09 07:52:11)
        put("45601||45601|CamGSM ", new ApnParameters("http://mms.mobitel.com.kh/mmsc", "203.144.95.98", 3130));

        //CAN Rogers Wireless Inc. (Report Count: 3, Last Report: 2015-03-05 20:24:50)
        put("302370||302720|CAN Rogers Wireless Inc.", new ApnParameters("http://mms.fido.ca", "205.151.11.13", 80));

        //CAN Rogers Wireless Inc. (Report Count: 8, Last Report: 2015-03-27 01:58:43)
        put("302720|ROGERS|302720|CAN Rogers Wireless Inc.", new ApnParameters("http://mms.gprs.rogers.com", "10.128.1.69", 80));

        //CBW (Report Count: 1, Last Report: 2014-11-13 22:56:55)
        put("310420||310420|CBW", new ApnParameters("http://mms.gocbw.com:8088/mms", "216.68.79.202", 80));

        //CC Network (Report Count: 8, Last Report: 2015-03-22 15:32:19)
        put("310260||310260|CC Network", new ApnParameters("http://wholesale.mmsmvno.com/mms/wapenc", null, null));

        //CC Network (Report Count: 1, Last Report: 2015-03-06 18:22:58)
        put("310410||310410|CC Network", new ApnParameters("http://mmsc.mobile.att.net", "proxy.mobile.att.net", 80));

        //CELCOM (Report Count: 2, Last Report: 2015-03-24 11:45:48)
        put("50213||50219|CELCOM", new ApnParameters("http://mms.celcom.net.my", "10.128.1.242", 8080));

        //CELCOM (Report Count: 5, Last Report: 2015-03-18 03:24:13)
        put("50219||50219|CELCOM", new ApnParameters("http://mms.celcom.net.my", "10.128.1.242", 8080));

        //Celcom Malaysia Sdn Bhd (Report Count: 3, Last Report: 2015-03-11 07:25:06)
        put("50219|Celcom|50219|Celcom Malaysia Sdn Bhd", new ApnParameters("http://mms.celcom.net.my", "10.128.1.242", 8080));

        //Cell (Report Count: 1, Last Report: 2014-10-08 16:14:33)
        put("310260||310320|Cell", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //cell c (Report Count: 4, Last Report: 2015-03-21 12:53:37)
        put("65507| Cell C |65507|cell c", new ApnParameters("http://mms.cmobile.co.za", "196.31.116.250", 8080));

        //Cell C (Report Count: 5, Last Report: 2015-04-02 16:17:01)
        put("65507|Cell C|65507|Cell C", new ApnParameters("http://mms.cmobile.co.za", "196.31.116.250", 8080));

        //Cell One of NE Colorado (Report Count: 1, Last Report: 2014-10-18 14:46:42)
        put("310260||310450|Cell One of NE Colorado", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //Cell One of NE Colorado (Report Count: 1, Last Report: 2014-12-17 21:09:42)
        put("310450||310450|Cell One of NE Colorado", new ApnParameters("http://mms.viaero.com", "10.168.3.23", 9401));

        //Cellcom (Report Count: 18, Last Report: 2015-03-25 11:09:42)
        put("42502|Cellcom|42502|Cellcom", new ApnParameters("http://mms.cellcom.co.il", "vwapm2.ain.co.il", 8080));

        //Cellcom (Report Count: 3, Last Report: 2015-04-02 23:01:04)
        put("310600|Verizon|310120|Cellcom ", new ApnParameters("http://mms.cellcom.com/cellcom/mms.php", null, null));

        //Cellcom IL (Report Count: 4, Last Report: 2015-03-20 14:08:39)
        put("42502|Cellcom|42502|Cellcom IL", new ApnParameters("http://mms.cellcom.co.il", "vwapm2.ain.co.il", 8080));

        //Cellcom Israel (Report Count: 2, Last Report: 2015-01-03 19:37:16)
        put("42502|Cellcom|42502|Cellcom Israel", new ApnParameters("http://mms.cellcom.co.il", "172.31.29.38", 8080));

        //CellOne (Report Count: 1, Last Report: 2014-11-06 13:57:09)
        put("40457|BSNL 3G|40457|CellOne", new ApnParameters("http://bsnlmmsc.in", "10.210.10.11", 8080));

        //CellOne (Report Count: 1, Last Report: 2014-08-05 01:24:38)
        put("40458|CellOne|40458|CellOne", new ApnParameters("http://10.4.42.21:8002/", "10.4.42.15", 8080));

        //CellOne (Report Count: 1, Last Report: 2015-03-08 06:56:05)
        put("40466|BSNL MOBILE|40466|CellOne", new ApnParameters("http://10.4.42.21:8002/", "10.4.42.15", 8080));

        //CellOne (Report Count: 3, Last Report: 2015-02-23 15:53:53)
        put("40471|BSNL MOBILE|40471|CellOne", new ApnParameters("http://bsnlmmsc.in:8514", "10.210.10.11", 8080));

        //CellOne (Report Count: 1, Last Report: 2015-02-20 05:43:45)
        put("40472|BSNL Mobile|40472|CellOne", new ApnParameters("http://10.4.42.21:8002/", "10.4.42.15", 8080));

        //CellOne (Report Count: 1, Last Report: 2015-02-11 08:56:16)
        put("40473|BSNL MOBILE|40473|CellOne", new ApnParameters("http://bsnlmmsc.in:8514", "10.210.10.11", 8080));

        //CellOne (Report Count: 1, Last Report: 2015-03-29 01:39:12)
        put("40480||40466|CellOne", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Cellone A.P. (Report Count: 1, Last Report: 2015-01-15 11:35:49)
        put("40473|BSNL Mobile|40473|Cellone A.P.", new ApnParameters("http://10.4.42.21:8002", "10.4.42.15", 8080));

        //Cellone Gujrat (Report Count: 1, Last Report: 2014-12-17 06:51:50)
        put("40457|BSNL 3G|40457|Cellone Gujrat", new ApnParameters("http://bsnlmmsc.in:8514", "10.210.10.11", 8080));

        //Cellone Gujrat (Report Count: 1, Last Report: 2015-03-11 06:47:00)
        put("40457|CellOne|40457|Cellone Gujrat", new ApnParameters("http://bsnlmmsc.in:8514", "10.210.10.11", 8080));

        //Cellone Kerala (Report Count: 1, Last Report: 2015-03-08 10:07:25)
        put("40472|BSNL MOBILE|40472|Cellone Kerala", new ApnParameters("http://10.4.42.21:8002/", "10.4.42.15", 8080));

        //Cellone Kerala (Report Count: 1, Last Report: 2015-01-13 17:17:55)
        put("40472|CellOne|40472|Cellone Kerala", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Cellone Orissa (Report Count: 1, Last Report: 2015-03-06 05:11:06)
        put("40476|BSNL MOBILE|40476|Cellone Orissa", new ApnParameters("http://10.4.42.21:8002", "10.4.42.15", 8080));

        //CELLONE PARTNER (Report Count: 1, Last Report: 2014-09-25 19:24:08)
        put("311190||310410|CELLONE PARTNER", new ApnParameters("http://mms.cellular1.net", "10.10.0.97", 9201));

        //Cellone U.P.(E) (Report Count: 1, Last Report: 2015-04-07 07:14:04)
        put("40455|BSNL MOBILE|40455|Cellone U.P.(E)", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Cellular (Report Count: 1, Last Report: 2014-10-18 02:21:02)
        put("310410|Verizon|310410|Cellular", new ApnParameters("http://mmsc.cingular.com", "wireless.cingular.com", 80));

        //CELLULAR AT SEA (Report Count: 1, Last Report: 2015-03-17 15:15:33)
        put("310260||310260|CELLULAR AT SEA", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //Cellular One (Report Count: 1, Last Report: 2015-03-13 17:47:50)
        put("310260|Verizon|310260|Cellular One", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //Cellular One (Report Count: 6, Last Report: 2015-02-25 20:42:49)
        put("310260||310260|Cellular One", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //Cellular One (Report Count: 1, Last Report: 2015-01-23 16:21:17)
        put("310260||310570|Cellular One", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //Cellular One (Report Count: 1, Last Report: 2015-04-03 20:57:38)
        put("310570|Cellular One|310260|Cellular One", new ApnParameters("http://mmsc.mtpcs.csky.us:6672/", "209.4.229.229", 9201));

        //Cellular One (Report Count: 2, Last Report: 2015-02-17 21:25:15)
        put("310570|Cellular One|310570|Cellular One", new ApnParameters("http://mmsc.mtpcs.csky.us:6672/", "209.4.229.229", 9201));

        //CELLULARONE (Report Count: 4, Last Report: 2015-03-24 12:13:23)
        put("311190||311190|CELLULARONE", new ApnParameters("http://mms.cellular1.net", "10.10.0.97", 9201));

        //CEZ (Report Count: 1, Last Report: 2015-01-17 08:21:44)
        put("23002|CEZ|23002|CEZ", new ApnParameters("http://mms.o2active.cz:8002", "160.218.160.218", 8080));

        //CH (Report Count: 16, Last Report: 2015-03-30 09:22:12)
        put("42502|Cellcom|42502|CH", new ApnParameters("http://mms.cellcom.co.il", "vwapm2.ain.co.il", 8080));

        //Chameleon (Report Count: 24, Last Report: 2015-03-13 15:32:49)
        put("310000|Chameleon|310000|Chameleon", new ApnParameters("http://mms.sprintpcs.com", "68.28.31.7", 80));

        //Chameleon (Report Count: 1, Last Report: 2015-03-23 01:49:09)
        put("31000|Chameleon|31000|Chameleon", new ApnParameters("null", null, null));

        //chatr (Report Count: 2, Last Report: 2015-02-14 23:29:21)
        put("302720|chatr|302720|chatr", new ApnParameters("http://mms.chatrwireless.com", "205.151.11.11", 8080));

        //CHINA MOBILE (Report Count: 1, Last Report: 2015-03-14 11:29:41)
        put("46000||46000|CHINA MOBILE", new ApnParameters("http://mmsc.monternet.com", "10.0.0.172", 80));

        //CHINA MOBILE (Report Count: 2, Last Report: 2015-04-02 07:45:36)
        put("46002|CMCC|46000|CHINA MOBILE", new ApnParameters("http://mmsc.monternet.com", "10.0.0.172", 80));

        //CHINA MOBILE (Report Count: 1, Last Report: 2015-02-12 02:15:16)
        put("46002||46000|CHINA MOBILE", new ApnParameters("http://mmsc.monternet.com", "10.0.0.172", 80));

        //China Mobile (Report Count: 1, Last Report: 2014-10-12 20:53:08)
        put("46002||46000|China Mobile", new ApnParameters("http://mmsc.monternet.com", "10.0.0.172", 80));

        //China Mobile HK (Report Count: 1, Last Report: 2014-09-12 06:14:26)
        put("45412|PEOPLES|45412|China Mobile HK", new ApnParameters("http://mms.hk.chinamobile.com/mms", null, null));

        //CHN-CUGSM,null (Report Count: 1, Last Report: 2015-03-16 23:15:50)
        put("46001|null|46001|CHN-CUGSM,null", new ApnParameters("http://mmsc.myuni.com.cn/", "10.0.0.172", 80));

        //CHN-UNICOM (Report Count: 3, Last Report: 2015-03-02 01:37:32)
        put("46001||46001|CHN-UNICOM", new ApnParameters("http://mmsc.myuni.com.cn", "10.0.0.172", 80));

        //Chunghwa Telecom (Report Count: 2, Last Report: 2015-03-27 03:15:25)
        put("46692||46692|Chunghwa Telecom", new ApnParameters("http://mms.emome.net:8002", "10.1.1.1", 8080));

        //CinBell USA (Report Count: 2, Last Report: 2014-11-25 00:27:20)
        put("310420||310260|CinBell USA", new ApnParameters("http://mms.gocbw.com:8088/mms", "216.68.79.202", 80));

        //Cincinnati Bell (Report Count: 3, Last Report: 2014-12-21 16:34:57)
        put("310420||310420|Cincinnati Bell", new ApnParameters("http://mms.gocbw.com:8088/mms", "216.68.79.202", 80));

        //Cincinnati Bell USA (Report Count: 2, Last Report: 2014-10-01 10:45:57)
        put("310420||310420|Cincinnati Bell USA", new ApnParameters("http://mms.gocbw.com:8088/mms", "216.68.79.202", 80));

        //CITYFONE (Report Count: 4, Last Report: 2015-02-11 02:02:11)
        put("302720|CITYFONE|302720|CITYFONE", new ApnParameters("http://mms.gprs.rogers.com", "mmsproxy.rogers.com", 80));

        //CLARO (Report Count: 7, Last Report: 2015-04-02 15:56:09)
        put("330110|CLARO P.R.|330110|CLARO", new ApnParameters("http://mmsg.claropr.com:10021/mmsc", "10.50.38.3", 8799));

        //Claro (Report Count: 1, Last Report: 2014-09-11 04:51:55)
        put("70401|Claro|70401|Claro", new ApnParameters("http://mms.ideasclaro.com:8002", "216.230.133.66", 8080));

        //CLARO (Report Count: 1, Last Report: 2015-03-07 15:43:24)
        put("708001|Claro HND|708001|CLARO", new ApnParameters("http://10.6.32.27/servlets/mms", "10.6.32.2", 8080));

        //CLARO (Report Count: 1, Last Report: 2015-02-26 13:41:39)
        put("71021||71021|CLARO", new ApnParameters("http://10.6.32.27/servlets/mms", "10.6.32.2", 8080));

        //Claro (Report Count: 8, Last Report: 2015-03-17 21:23:17)
        put("71610|Claro|71610|Claro", new ApnParameters("http://claro/servlets/mms", "192.168.231.30", 80));

        //Claro (Report Count: 1, Last Report: 2015-04-04 16:20:21)
        put("732101|Claro|732101|Claro", new ApnParameters("http://www.comcel.com.co/mms/", "198.228.90.225", 8799));

        //Claro AR (Report Count: 15, Last Report: 2015-03-27 23:01:53)
        put("722310|Claro AR|722310|Claro AR", new ApnParameters("http://mms.claro.com.ar", null, null));

        //CLARO P.R. (Report Count: 1, Last Report: 2015-02-21 11:57:07)
        put("330110|CLARO P.R.|330110|CLARO P.R.", new ApnParameters("http://mmsg.claropr.com:10021/mmsc", "10.50.38.3", 8799));

        //Claro UY (Report Count: 3, Last Report: 2015-03-15 11:37:19)
        put("74810|Claro UY|74810|Claro UY", new ApnParameters("http://mms.claro.com.uy", null, null));

        //ClearTalk (Report Count: 1, Last Report: 2014-11-16 00:52:16)
        put("310120|Sprint|310120|ClearTalk", new ApnParameters("null", null, null));

        //ClearTalk (Report Count: 1, Last Report: 2015-03-27 16:03:34)
        put("311750|ClearTalk|31000|", new ApnParameters("http://mmsc.cleartalk.csky.us", null, null));

        //ClearTalk (Report Count: 1, Last Report: 2015-03-18 19:18:50)
        put("311750|ClearTalk|311750|", new ApnParameters("http://mms.cleartalk.us/cleartalk/mms.php", null, null));

        //Clixster (Report Count: 1, Last Report: 2014-11-01 10:00:08)
        put("50216|Clixster|50216|Clixster", new ApnParameters("http://mms.digi.com.my/servlets/mms", "203.92.128.160", 80));

        //CMCC (Report Count: 3, Last Report: 2015-03-15 03:52:31)
        put("46000|CMCC|46000|CMCC", new ApnParameters("http://mmsc.monternet.com", "10.0.0.172", 80));

        //CMCC (Report Count: 1, Last Report: 2014-09-25 03:21:33)
        put("46000||46000|CMCC", new ApnParameters("http://mmsc.monternet.com/", "10.0.0.172", 80));

        //CMCC (Report Count: 2, Last Report: 2015-02-27 05:23:53)
        put("46002|CMCC|46000|CMCC", new ApnParameters("http://mmsc.monternet.com", "10.0.0.172", 80));

        //CMCC (Report Count: 2, Last Report: 2014-12-08 08:02:26)
        put("46002||46000|CMCC", new ApnParameters("http://mmsc.monternet.com", "10.0.0.172", 80));

        //CMHK (Report Count: 2, Last Report: 2015-01-26 09:42:37)
        put("45412|CMHK|45412|CMHK", new ApnParameters("http://mms.hk.chinamobile.com/mms", null, null));

        //CMHK (Report Count: 4, Last Report: 2015-03-19 15:37:02)
        put("45412|CMHK|45413|CMHK", new ApnParameters("http://mms.hk.chinamobile.com/mms", null, null));

        //COMMNET (Report Count: 2, Last Report: 2015-03-06 12:05:25)
        put("310260||310260|COMMNET", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //Comviq (Report Count: 1, Last Report: 2014-11-12 12:22:57)
        put("24007|Comviq|24005|Comviq", new ApnParameters("http://mmsc.tele2.se", "130.244.202.30", 8080));

        //Comviq (Report Count: 1, Last Report: 2014-09-25 12:00:27)
        put("24007|Comviq|24007|Comviq", new ApnParameters("http://mmsc.tele2.se", "130.244.202.30", 8080));

        //Comviq SE (Report Count: 1, Last Report: 2015-04-05 07:10:25)
        put("24007|Comviq|24005|Comviq SE", new ApnParameters("http://mmsc.tele2.se", "130.244.202.30", 8080));

        //congstar (Report Count: 8, Last Report: 2015-04-03 16:23:13)
        put("26201|congstar|26201| ", new ApnParameters("http://mms.t-mobile.de/servlets/mms", "172.28.23.131", 8008));

        //congstar (Report Count: 11, Last Report: 2015-03-31 16:02:16)
        put("26201|congstar|26201|congstar", new ApnParameters("http://mms.t-mobile.de/servlets/mms", "172.28.23.131", 8008));

        //Contact (Report Count: 2, Last Report: 2015-01-10 05:59:09)
        put("20820|Bouygues Telecom|20802|Contact", new ApnParameters("http://mms.bouyguestelecom.fr/mms/wapenc", "62.201.129.226", 8080));

        //CORIOLIS (Report Count: 1, Last Report: 2015-03-23 09:23:31)
        put("20810|,|20810|CORIOLIS", new ApnParameters("http://mmsdebitel", "10.143.156.3", 8080));

        //CORIOLIS (Report Count: 3, Last Report: 2015-03-23 21:43:51)
        put("20810|CORIOLIS|20810|CORIOLIS", new ApnParameters("http://mmscoriolis", "10.143.156.6", 8080));

        //Cricket (Report Count: 1, Last Report: 2014-11-08 02:45:04)
        put("310005|CricKet |31000|Cricket", new ApnParameters("http://mms.mycricket.com/servlets/mms", null, null));

        //Cricket (Report Count: 1, Last Report: 2014-10-14 12:43:33)
        put("310007|CricKet |31000|Cricket", new ApnParameters("http://mms.mycricket.com/servlets/mms", null, null));

        //Cricket (Report Count: 1, Last Report: 2014-12-04 00:09:23)
        put("310008|CricKet |31000|Cricket", new ApnParameters("http://mms.mycricket.com/servlets/mms", null, null));

        //CricKet (Report Count: 9, Last Report: 2015-03-13 22:32:06)
        put("310016|CricKet|123456|CricKet", new ApnParameters("http://mms.mycricket.com/servlets/mms", null, null));

        //CricKet (Report Count: 46, Last Report: 2015-04-04 19:54:48)
        put("310016|CricKet|31000|CricKet", new ApnParameters("http://mms.mycricket.com/servlets/mms", null, null));

        //CricKet (Report Count: 3, Last Report: 2014-10-31 18:24:51)
        put("310016|CricKet|31001|CricKet", new ApnParameters("http://mms.mycricket.com/servlets/mms", null, null));

        //CricKet (Report Count: 38, Last Report: 2015-04-06 21:34:39)
        put("310016|CricKet|310090|CricKet", new ApnParameters("http://mms.mycricket.com/servlets/mms", null, null));

        //CricKet (Report Count: 1, Last Report: 2015-03-27 02:49:54)
        put("310016|CricKet|310120|CricKet", new ApnParameters("http://mms.mycricket.com/servlets/mms", null, null));

        //Cricket (Report Count: 9, Last Report: 2015-03-31 21:19:20)
        put("310090|CricKet |31000|Cricket", new ApnParameters("http://mms.mycricket.com/servlets/mms", null, null));

        //Cricket (Report Count: 7, Last Report: 2015-02-01 01:23:52)
        put("310090|CricKet |31001|Cricket", new ApnParameters("http://mms.mycricket.com/servlets/mms", null, null));

        //Cricket (Report Count: 13, Last Report: 2015-04-02 12:14:49)
        put("310090|CricKet |310090|Cricket", new ApnParameters("http://mms.mycricket.com/servlets/mms", null, null));

        //cricket (Report Count: 4, Last Report: 2014-12-08 19:48:22)
        put("310150|Verizon|310410|cricket", new ApnParameters("http://mmsc.aiowireless.net", "proxy.aiowireless.net", 80));

        //cricket (Report Count: 1, Last Report: 2015-01-30 20:10:12)
        put("310150|aio|310410|cricket", new ApnParameters("http://mmsc.aiowireless.net", "proxy.aiowireless.net", 80));

        //cricket (Report Count: 1, Last Report: 2015-01-23 02:31:11)
        put("310150|cricket@|310410|cricket", new ApnParameters("http://mmsc.aiowireless.net", "proxy.aiowireless.net", 80));

        //cricket (Report Count: 9, Last Report: 2015-04-02 21:47:23)
        put("310150|cricket|310410|cricket", new ApnParameters("http://mmsc.aiowireless.net", "proxy.aiowireless.net", 80));

        //cricket (Report Count: 4031, Last Report: 2015-04-08 17:20:52)
        put("310150||310410|cricket", new ApnParameters("http://mmsc.aiowireless.net", "proxy.aiowireless.net", 80));

        //Cricket (Report Count: 1, Last Report: 2014-08-19 20:40:43)
        put("311230|C-Spire|311480|Cricket", new ApnParameters("http://mms.mycricket.com/servlets/mms", "wap.mycricket.com", 8080));

        //CricKet (Report Count: 5, Last Report: 2015-02-19 02:03:21)
        put("310090|CricKet |31000|CricKet ", new ApnParameters("http://mms.mycricket.com/servlets/mms", null, null));

        //CSL (Report Count: 1, Last Report: 2014-11-24 02:47:50)
        put("45400|CSL|45400|CSL", new ApnParameters("http://192.168.58.171:8002", "192.168.59.51", 8080));

        //CSL (Report Count: 1, Last Report: 2014-12-12 08:21:00)
        put("45400|one2free|45400|CSL", new ApnParameters("http://192.168.58.171:8002/", "192.168.59.51", 8080));

        //CSL Hemat (Report Count: 3, Last Report: 2014-11-08 14:35:10)
        put("45400|CSL Hemat|45400|CSL Hemat", new ApnParameters("http://192.168.58.171:8002", "192.168.59.51", 8080));

        //CSL Hemat (Report Count: 2, Last Report: 2015-03-09 12:10:47)
        put("45400|CSL|45400|CSL Hemat", new ApnParameters("http://192.168.58.171:8002", "192.168.59.51", 8080));

        //csl. (Report Count: 1, Last Report: 2015-03-27 05:13:05)
        put("45400|csl.|45400|csl.", new ApnParameters("http://192.168.58.171:8002", "192.168.59.51", 8080));

        //csl. (Report Count: 1, Last Report: 2015-03-16 10:49:14)
        put("45419|csl.|45419|csl.", new ApnParameters("http://3gmms.pccwmobile.com:8080/was", "10.140.14.10", 8080));

        //CU (Report Count: 1, Last Report: 2015-01-13 02:41:45)
        put("46001||46001|CU", new ApnParameters("http://mmsc.myuni.com.cn", "10.0.0.172", 80));

        //Custom (Report Count: 2, Last Report: 2015-03-22 18:12:55)
        put("310000|Custom|310000|Custom", new ApnParameters("http://mms.plspictures.com", "68.28.31.7", 80));

        //CYTAMOBILE-VODAFONE (Report Count: 2, Last Report: 2015-03-14 08:47:16)
        put("28001|CytaVoda|28001|CYTAMOBILE-VODAFONE", new ApnParameters("http://mmsc.cyta.com.cy", "212.31.96.161", 8080));

        //DEFACE (Report Count: 1, Last Report: 2014-10-05 15:44:59)
        put("00101|DEFACE|00101|DEFACE", new ApnParameters("null", null, null));

        //Dialog (Report Count: 9, Last Report: 2015-01-26 16:17:22)
        put("41302|Dialog|41302|Dialog", new ApnParameters("http://mms.dialog.lk:3130/mmsc", "192.168.122.2", 8080));

        //Dialog (Report Count: 13, Last Report: 2015-04-01 14:12:47)
        put("41302|Dialog|41302|Dialog ", new ApnParameters("http://mms.dialog.lk:3130/mmsc", null, null));

        //DiGi (Report Count: 17, Last Report: 2015-03-27 14:37:18)
        put("50216|DiGi|50216|DiGi", new ApnParameters("http://mms.digi.com.my/servlets/mms", "203.92.128.160", 80));

        //DiGi Telecommunications (Report Count: 1, Last Report: 2015-01-31 05:55:22)
        put("50216|DiGi|50216|DiGi Telecommunications", new ApnParameters("http://mms.digi.com.my/servlets/mms", "203.92.128.160", 80));

        //DIGICEL (Report Count: 2, Last Report: 2015-03-23 23:22:52)
        put("310260||33805|DIGICEL", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //Digicel (Report Count: 2, Last Report: 2015-04-02 22:02:10)
        put("338050|Digicel|338050|Digicel", new ApnParameters("http://mms.digicelgroup.com", "172.16.7.12", 8080));

        //Digicel (Report Count: 1, Last Report: 2014-12-17 13:30:06)
        put("338050|Verizon|338050|Digicel", new ApnParameters("http://mms.digicelgroup.com", "172.16.7.12", 8080));

        //DIGICEL (Report Count: 7, Last Report: 2015-04-05 03:54:17)
        put("37413|DIGICEL|374130|DIGICEL", new ApnParameters("http://mmc.digiceltt.com/servlets/mms", "172.20.6.12", 8080));

        //Digicel (Report Count: 1, Last Report: 2015-02-08 22:00:26)
        put("73801|Digicel||", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //DIGICEL F (Report Count: 1, Last Report: 2015-01-01 22:17:11)
        put("20815|Free|34020|DIGICEL F", new ApnParameters("http://mms.free.fr", null, null));

        //Digital Roaming (Report Count: 9, Last Report: 2015-02-15 03:03:58)
        put("310120|Sprint|00000|Digital Roaming", new ApnParameters("http://mms.sprintpcs.com", "68.28.31.7", 80));

        //Digital Roaming (Report Count: 1, Last Report: 2014-08-10 00:34:12)
        put("310120|Sprint|31000|Digital Roaming", new ApnParameters("http://mms.sprintpcs.com", "68.28.31.7", 80));

        //Digital Roaming (Report Count: 1, Last Report: 2015-03-24 13:19:29)
        put("310120|Sprint|310120|Digital Roaming", new ApnParameters("http://mms.sprintpcs.com", "68.28.31.7", 80));

        //Digital Roaming (Report Count: 3, Last Report: 2014-12-11 15:46:13)
        put("310120|Sprint|31070|Digital Roaming", new ApnParameters("http://mms.sprintpcs.com", "68.28.31.7", 80));

        //disco (Report Count: 1, Last Report: 2015-04-02 18:03:52)
        put("26207||26207|disco", new ApnParameters("http://10.81.0.7:8002/", "82.113.100.5", 8080));

        //Djezzy (Report Count: 1, Last Report: 2014-11-18 09:27:28)
        put("60302|Djezzy|60302|Djezzy", new ApnParameters("http://172.24.97.152:10021/mmsc", "172.24.97.158", 8799));

        //DNA (Report Count: 1, Last Report: 2015-03-29 18:49:44)
        put("24412|DNA|24412|DNA", new ApnParameters("http://mmsc.dna.fi", "10.1.1.2", 8080));

        //Dolphin (Report Count: 2, Last Report: 2015-03-28 04:12:06)
        put("40469|DOLPHIN|40469|Dolphin", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Domestic Roaming (Report Count: 1, Last Report: 2015-02-15 03:11:06)
        put("310000|Motorola|31000|Domestic Roaming", new ApnParameters("http://127.0.0.1:18181", null, null));

        //Drillisch (Report Count: 1, Last Report: 2015-02-22 20:14:34)
        put("26207||26207|Drillisch", new ApnParameters("http://10.81.0.7:8002", "82.113.100.5", 8080));

        //DTAC (Report Count: 1, Last Report: 2014-08-17 03:54:33)
        put("52005|DTAC|52005|DTAC", new ApnParameters("http://mms2.dtac.co.th:8002/", "10.10.10.10", 8080));

        //DTAC (Report Count: 1, Last Report: 2014-12-25 02:31:35)
        put("52005|DTAC|52018|DTAC", new ApnParameters("http://mms2.dtac.co.th:8002/", "10.10.10.10", 8080));

        //DTAC (Report Count: 10, Last Report: 2015-04-02 07:33:55)
        put("52005||52005|DTAC", new ApnParameters("http://mms2.dtac.co.th:8002", "10.10.10.10", 8080));

        //DTAC (Report Count: 5, Last Report: 2015-03-22 05:34:14)
        put("52005||52018|DTAC", new ApnParameters("http://mms.dtac.co.th:8002", "203.155.200.133", 8080));

        //dtac (Report Count: 8, Last Report: 2015-02-23 03:06:28)
        put("52005||52018|dtac", new ApnParameters("http://mms2.dtac.co.th:8002/", "10.10.10.10", 8080));

        //DTAC (Report Count: 9, Last Report: 2015-03-31 07:45:58)
        put("52018||52018|DTAC", new ApnParameters("http://mms.dtac.co.th:8002", "203.155.200.133", 8080));

        //du (Report Count: 14, Last Report: 2015-02-14 14:26:39)
        put("42403|du|42403|du", new ApnParameters("http://mms.du.ae", "10.19.18.4", 8080));

        //Duet (Report Count: 2, Last Report: 2015-01-20 08:15:14)
        put("311530|Duet|311530|Duet", new ApnParameters("http://172.16.16.103/mms/", "172.16.16.102", 8080));

        //E-Plus (Report Count: 1, Last Report: 2015-03-02 11:02:20)
        put("26203|AY YILDIZ|26203|E-Plus", new ApnParameters("http://mms/eplus/", "212.23.97.153", 5080));

        //E-Plus (Report Count: 1, Last Report: 2015-03-29 19:48:39)
        put("26203|BASE|26203|E-Plus", new ApnParameters("http://mms/eplus/", "212.23.97.153", 5080));

        //E-Plus (Report Count: 2, Last Report: 2015-01-26 19:35:54)
        put("26203|MEDIONmobile|26203|E-Plus", new ApnParameters("http://mms/eplus", "212.23.97.153", 5080));

        //E-Plus (Report Count: 2, Last Report: 2015-02-27 10:02:47)
        put("26203||26203|E-Plus", new ApnParameters("http://mms/eplus/", "212.23.97.153", 5080));

        //E-Plus,null (Report Count: 1, Last Report: 2014-09-16 13:53:52)
        put("26203|MEDIONmobile|26203|E-Plus,null", new ApnParameters("http://mms/eplus/", "212.23.97.153", 5080));

        //EASTLINK (Report Count: 1, Last Report: 2015-02-02 21:48:08)
        put("302270|EASTLINK|302270|EASTLINK", new ApnParameters("http://mmss.mobi.eastlink.ca", "10.232.12.49", 8080));

        //EASTLINK (Report Count: 1, Last Report: 2014-11-13 05:22:27)
        put("302270||302270|EASTLINK", new ApnParameters("http://mmss.mobi.eastlink.ca", "10.232.12.49", 8080));

        //EE (Report Count: 176, Last Report: 2015-04-07 18:58:23)
        put("23430||23430|EE", new ApnParameters("http://mms/", "149.254.201.135", 8080));

        //EE (Report Count: 12, Last Report: 2015-03-29 10:23:58)
        put("23430||23433|EE", new ApnParameters("http://mms/", "149.254.201.135", 8080));

        //EE (Report Count: 38, Last Report: 2015-03-26 18:20:25)
        put("23433|EE|23430|EE", new ApnParameters("http://mms/", "149.254.201.135", 8080));

        //EE (Report Count: 5, Last Report: 2015-03-17 16:38:17)
        put("23433|EE|23433|EE", new ApnParameters("http://mms/", "149.254.201.135", 8080));

        //EE (Report Count: 4, Last Report: 2015-03-20 16:27:22)
        put("23433||23430|EE", new ApnParameters("http://mms/", "149.254.201.135", 8080));

        //EE (Report Count: 1, Last Report: 2015-01-14 07:40:25)
        put("25001|MTS|23430|EE", new ApnParameters("http://mms/", "149.254.201.135", 8080));

        //EI Telecom (Report Count: 3, Last Report: 2015-03-28 16:18:06)
        put("20826|EI Telecom|20810|EI Telecom", new ApnParameters("http://mmsnrj", "10.143.156.5", 8080));

        //EMT (Report Count: 3, Last Report: 2015-03-10 17:54:13)
        put("24801||24801|EMT", new ApnParameters("http://mms.emt.ee/servlets/mms", "217.71.32.82", 8080));

        //Era (Report Count: 1, Last Report: 2014-12-19 13:58:58)
        put("26002|T-Mobile.pl Q|26002|Era", new ApnParameters("http://mms.era.pl/servlets/mms", "213.158.194.226", 8080));

        //Era (Report Count: 10, Last Report: 2015-03-26 19:37:13)
        put("26002|T-Mobile.pl|26002|Era", new ApnParameters("http://mms.era.pl/servlets/mms", "213.158.194.226", 8080));

        //Era (Report Count: 1, Last Report: 2014-11-18 14:58:36)
        put("26002|heyah|26002|Era", new ApnParameters("http://mms.era.pl/servlets/mms", "213.158.194.226", 8080));

        //Era (Report Count: 5, Last Report: 2015-04-08 10:40:02)
        put("26002||26002|Era", new ApnParameters("http://mms.era.pl/servlets/mms", "213.158.194.226", 8080));

        //Era (Report Count: 1, Last Report: 2015-02-14 17:00:51)
        put("26006|PLAY|26002|Era", new ApnParameters("http://10.10.28.164/mms/wapenc", "10.10.25.5", 8080));

        //Era (Report Count: 1, Last Report: 2014-10-20 12:34:46)
        put("26006|Red Bull MOBILE|26002|Era", new ApnParameters("http://10.10.28.164/mms/wapenc", "10.10.25.5", 8080));

        //Etisalat (Report Count: 1, Last Report: 2015-01-15 10:36:38)
        put("41302|Dialog|41302|Etisalat", new ApnParameters("http://mms.dialog.lk:3130/mmsc", "192.168.122.2", 8080));

        //Etisalat (Report Count: 2, Last Report: 2015-03-12 16:42:50)
        put("41303|Etisalat|41303|Etisalat", new ApnParameters("http://mms.etisalat.lk:8085", "192.168.104.4", 9401));

        //ETISALAT (Report Count: 1, Last Report: 2015-01-25 19:26:02)
        put("42402|etisalat|42402|ETISALAT", new ApnParameters("http://mms/servlets/mms", "10.12.0.32", 8080));

        //etisalat (Report Count: 2, Last Report: 2015-03-21 23:40:17)
        put("60203|etisalat|60203|etisalat", new ApnParameters("http://10.71.131.7:38090/", "10.71.130.29", 8080));

        //ETISALAT (Report Count: 4, Last Report: 2015-01-19 21:55:55)
        put("62160|etisalat|62160|ETISALAT", new ApnParameters("http://10.71.170.30:38090/was", "10.71.170.5", 8080));

        //Extended Network (Report Count: 1, Last Report: 2014-12-03 19:55:36)
        put("311480|Verizon Wireless|310000|Extended Network", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Extended Network (Report Count: 6, Last Report: 2015-02-24 06:31:06)
        put("311480|Verizon Wireless|310004|Extended Network", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Extended Network (Report Count: 4, Last Report: 2015-03-30 13:58:55)
        put("311480|Verizon Wireless|31000|Extended Network", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Extended Network (Report Count: 5, Last Report: 2015-03-25 14:02:06)
        put("311480|Verizon Wireless|3107|Extended Network", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Extended Network (Report Count: 1, Last Report: 2014-11-01 20:06:37)
        put("311480|Verizon Wireless|311000|Extended Network", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Extended Network (Report Count: 1, Last Report: 2014-08-13 00:28:26)
        put("311480|Verizon Wireless|31135|Extended Network", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Extended Network (Report Count: 12, Last Report: 2015-03-27 17:55:38)
        put("311480|Verizon Wireless|311480|Extended Network", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Extended Network (Report Count: 1, Last Report: 2015-02-18 23:02:27)
        put("311480|Verizon Wireless|3167|Extended Network", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Extended Network (Report Count: 1, Last Report: 2014-09-24 07:01:48)
        put("311480|Verizon|31099|Extended Network", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Extended Network (Report Count: 5, Last Report: 2015-04-02 14:23:57)
        put("311480|Verizon|31100|Extended Network", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Extended Network (Report Count: 2, Last Report: 2015-03-02 22:53:41)
        put("311480|Verizon|31135|Extended Network", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Extended Network (Report Count: 22, Last Report: 2015-03-27 20:51:23)
        put("311480|Verizon|311480|Extended Network", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Extended Network (Report Count: 5, Last Report: 2015-02-25 23:32:28)
        put("311480||31000|Extended Network", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Extended Network (Report Count: 1, Last Report: 2015-02-20 05:24:48)
        put("311480||3107|Extended Network", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Extended Network (Report Count: 2, Last Report: 2015-03-20 01:35:59)
        put("311480||31100|Extended Network", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //fOrange (Report Count: 1, Last Report: 2014-10-27 17:12:30)
        put("34001||34001|fOrange", new ApnParameters("http://193.251.160.246/servlets/mms", "10.0.0.10", 8082));

        //F - Contact (Report Count: 1, Last Report: 2014-11-26 16:41:48)
        put("20820|Bouygues Telecom|20802|F - Contact", new ApnParameters("http://mms.bouyguestelecom.fr/mms/wapenc", "62.201.129.226", 8080));

        //F - Contact (Report Count: 2, Last Report: 2014-12-29 09:44:35)
        put("20820|Bouygues Telecom|20888|F - Contact", new ApnParameters("http://mms.bouyguestelecom.fr/mms/wapenc", "62.201.129.226", 8080));

        //F Bouygues (Report Count: 1, Last Report: 2014-11-24 19:44:40)
        put("20820|BOUYGTEL|20820|F Bouygues", new ApnParameters("http://mms.bouyguestelecom.fr/mms/wapenc", "62.201.129.226", 8080));

        //F Bouygues (Report Count: 24, Last Report: 2015-04-03 07:51:05)
        put("20820|Bouygues Telecom|20820|F Bouygues", new ApnParameters("http://mms.bouyguestelecom.fr/mms/wapenc", "62.201.129.226", 8080));

        //F Bouygues (Report Count: 1, Last Report: 2014-10-23 19:03:28)
        put("20820|UNIVERSAL|20820|F Bouygues", new ApnParameters("http://mms.bouyguestelecom.fr/mms/wapenc", "62.201.129.226", 8080));

        //F Bouygues (Report Count: 3, Last Report: 2015-03-09 21:46:28)
        put("20820||20820|F Bouygues", new ApnParameters("http://mms.bouyguestelecom.fr/mms/wapenc", "62.201.129.226", 8080));

        //F SFR (Report Count: 1, Last Report: 2015-02-17 17:32:24)
        put("20810|,|20810|F SFR", new ApnParameters("http://mms1", "10.151.0.1", 8080));

        //F SFR (Report Count: 1, Last Report: 2015-03-10 15:52:35)
        put("20810|La Poste Mobile|20810|F SFR", new ApnParameters("http://mms1", "10.151.0.1", 8080));

        //F SFR (Report Count: 12, Last Report: 2015-03-29 11:55:45)
        put("20810|LeclercMobile|20810|F SFR", new ApnParameters("http://mms66", "10.143.156.9", 8080));

        //F SFR (Report Count: 1, Last Report: 2014-11-25 11:29:03)
        put("20810|Numericable|20810|F SFR", new ApnParameters("http://mms1", "10.151.0.1", 8080));

        //F SFR (Report Count: 2, Last Report: 2015-03-06 12:14:27)
        put("20810|PrixTel|20810|F SFR", new ApnParameters("http://mms1", "10.151.0.1", 8080));

        //F SFR (Report Count: 488, Last Report: 2015-04-08 16:14:48)
        put("20810||20810|F SFR", new ApnParameters("http://mms1", "10.151.0.1", 8080));

        //F SFR (Report Count: 2, Last Report: 2015-02-04 11:20:13)
        put("20823|Virgin|20810|F SFR", new ApnParameters("http://virginmms.fr", "10.6.10.1", 8080));

        //F SFR (Report Count: 1, Last Report: 2014-12-18 16:24:35)
        put("22801|Swisscom|20810|F SFR", new ApnParameters("http://mms.natel.ch:8079", "192.168.210.2", 8080));

        //F SFR (Report Count: 1, Last Report: 2015-03-01 12:37:40)
        put("302610||20810|F SFR", new ApnParameters("http://mms.bell.ca/mms/wapenc", null, null));

        //F-Bouygues Telecom (Report Count: 313, Last Report: 2015-04-08 16:46:52)
        put("20820|Bouygues Telecom|20820|F-Bouygues Telecom", new ApnParameters("http://mms.bouyguestelecom.fr/mms/wapenc", "62.201.129.226", 8080));

        //F-Bouygues Telecom (Report Count: 1, Last Report: 2014-09-20 14:54:59)
        put("20820|Verizon|20820|F-Bouygues Telecom", new ApnParameters("http://mms.bouyguestelecom.fr/mms/wapenc", "62.201.129.226", 8080));

        //F-Bouygues Telecom (Report Count: 2, Last Report: 2015-03-10 11:34:39)
        put("20820||20820|F-Bouygues Telecom", new ApnParameters("http://mms.bouyguestelecom.fr/mms/wapenc", "62.201.129.226", 8080));

        //F-Bouygues Telecom (Report Count: 2, Last Report: 2015-01-25 21:39:01)
        put("310260||20820|F-Bouygues Telecom", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //F-Orange (Report Count: 1, Last Report: 2015-03-19 18:00:43)
        put("310410||34001|F-Orange", new ApnParameters("http://mmsc.mobile.att.net", "proxy.mobile.att.net", 80));

        //F-Orange (Report Count: 6, Last Report: 2015-03-27 18:58:42)
        put("34001||34001|F-Orange", new ApnParameters("http://193.251.160.246/servlets/mms", "10.0.0.10", 8082));

        //Family Mobile (Report Count: 1, Last Report: 2015-03-28 20:57:26)
        put("310260|Family Mobile@|310260|Family Mobile", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //Family Mobile (Report Count: 3, Last Report: 2015-04-05 23:01:43)
        put("310260|Family Mobile|310260|Family Mobile", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //Family Mobile (Report Count: 2, Last Report: 2014-10-21 10:38:53)
        put("310260|Verizon|310260|Family Mobile", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //Family Mobile (Report Count: 775, Last Report: 2015-04-07 12:45:57)
        put("310260||310260|Family Mobile", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //femto (Report Count: 1, Last Report: 2015-01-10 23:25:07)
        put("722341|Personal|72234|femto", new ApnParameters("http://mms.personal.com", "172.25.7.31", 8080));

        //Femtocell Orange (Report Count: 6, Last Report: 2015-02-21 13:41:20)
        put("20801|Orange F|20801|Femtocell Orange", new ApnParameters("http://mms.orange.fr/", "192.168.10.200", 8080));

        //Fido (Report Count: 4, Last Report: 2015-02-21 03:27:32)
        put("302370|Fido@|302720|Fido", new ApnParameters("http://mms.fido.ca", "205.151.11.13", 80));

        //Fido (Report Count: 1436, Last Report: 2015-04-07 18:47:23)
        put("302370||302720|Fido", new ApnParameters("http://mms.fido.ca", "mmsproxy.fido.ca", 80));

        //FJ VODAFONE (Report Count: 1, Last Report: 2014-11-05 19:10:31)
        put("54201||54201|FJ VODAFONE", new ApnParameters("http://pxt.vodafone.net.fj/pxtsend", "10.202.2.40", 8080));

        //FLEXPLUS (Report Count: 1, Last Report: 2014-12-26 22:34:41)
        put("310320|Cellular ONE|310320|FLEXPLUS", new ApnParameters("http://mmsc.c1neaz.csky.us:6672", "209.4.229.94", 9401));

        //fonic (Report Count: 1, Last Report: 2014-11-24 15:57:02)
        put("26207|fonic|26207|fonic", new ApnParameters("http://10.81.0.7:8002", "82.113.100.5", 8080));

        //FONIC (Report Count: 3, Last Report: 2015-01-27 07:14:23)
        put("26207||26207|FONIC", new ApnParameters("http://10.81.0.7:8002/", "82.113.100.6", 8080));

        //France Orange (Report Count: 3, Last Report: 2015-04-07 14:07:44)
        put("20815|Free|20801|France Orange", new ApnParameters("http://mms.free.fr", null, null));

        //Free (Report Count: 1, Last Report: 2014-10-11 12:28:30)
        put("20815|,|20801|Free", new ApnParameters("http://mms.free.fr", null, null));

        //Free (Report Count: 271, Last Report: 2015-04-08 15:49:00)
        put("20815|Free|20801|Free", new ApnParameters("http://mms.free.fr/", null, null));

        //Free (Report Count: 2, Last Report: 2015-02-10 17:32:05)
        put("20815|Free|20802|Free", new ApnParameters("http://mms.free.fr", null, null));

        //Free (Report Count: 425, Last Report: 2015-04-07 19:18:24)
        put("20815|Free|20815|Free", new ApnParameters("http://mms.free.fr", null, null));

        //Free (Report Count: 3, Last Report: 2015-03-27 14:41:33)
        put("20815||20801|Free", new ApnParameters("http://mms.free.fr", null, null));

        //Free (Report Count: 3, Last Report: 2015-01-14 21:18:49)
        put("20815||20815|Free", new ApnParameters("http://mms.free.fr", null, null));

        //Free 4G (Report Count: 1, Last Report: 2015-03-15 17:59:04)
        put("20815|Free|20815|Free 4G", new ApnParameters("http://212.27.40.225", null, null));

        //GCI (Report Count: 1, Last Report: 2014-06-22 16:50:34)
        put("311370|GCI|311370|GCI", new ApnParameters("http://mmsc.gci.csky.us:6672", "209.4.229.92", 9201));

        //GCI (Report Count: 2, Last Report: 2015-03-17 05:02:25)
        put("311370|Verizon|311370|GCI", new ApnParameters("http://mmsc.gci.csky.us:6672", "209.4.229.92", 9201));

        //GCI (Report Count: 11, Last Report: 2015-03-30 04:32:48)
        put("311370||311370|GCI", new ApnParameters("http://mmsc.gci.net", "24.237.158.34", 9201));

        //Ge org! (Report Count: 1, Last Report: 2015-02-03 18:12:11)
        put("23207||23203|Ge org!", new ApnParameters("http://relay.mms.telering.at", "212.95.31.50", 8080));

        //GEO-GEOCELL (Report Count: 1, Last Report: 2014-12-12 19:51:49)
        put("28201|Geocell|28201|GEO-GEOCELL", new ApnParameters("http://mms.geocell.com.ge/cmmsc/post", "10.11.240.7", 8080));

        //Geocell (Report Count: 1, Last Report: 2015-03-07 14:01:47)
        put("28201|Geocell|28201|Geocell", new ApnParameters("http://mms.geocell.com.ge/cmmsc/post", "10.11.240.7", 8080));

        //giffgaff (Report Count: 9, Last Report: 2015-03-20 09:14:25)
        put("23410|giffgaff|23410|giffgaff", new ApnParameters("http://mmsc.mediamessaging.co.uk:8002", "193.113.200.195", 8080));

        //Globe Telecom (Report Count: 1, Last Report: 2015-02-07 01:02:32)
        put("51502|ABS-CBN|51502|Globe Telecom", new ApnParameters("http://mmscenter.suncellular.com.ph", "202.138.159.78", 8080));

        //Globe Telecom-PH (Report Count: 1, Last Report: 2014-11-13 20:56:44)
        put("310260||51502|Globe Telecom-PH", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //GoSmart (Report Count: 1, Last Report: 2014-11-16 18:30:27)
        put("310260|Verizon|310260|GoSmart", new ApnParameters("", null, null));

        //GoSmart (Report Count: 128, Last Report: 2015-04-08 04:34:18)
        put("310260||310260|GoSmart", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //GrameenPhone (Report Count: 1, Last Report: 2015-02-08 12:56:43)
        put("47001|BGD-GP|47001|GrameenPhone", new ApnParameters("http://mmsc.grameenphone.com/servlets/mms", null, null));

        //Grameenphone (Report Count: 3, Last Report: 2014-12-18 16:03:07)
        put("47001|BGD-GP|47001|Grameenphone", new ApnParameters("http://mms.gpsurf.net/servlets/mms", "10.128.1.2", 8080));

        //GrameenPhone (Report Count: 24, Last Report: 2015-01-09 17:11:24)
        put("47001||47001|GrameenPhone", new ApnParameters("http://mms.gpsurf.net/servlets/mms", "10.128.1.2", 8080));

        //Grameenphone (Report Count: 10, Last Report: 2015-03-12 07:05:04)
        put("47001||47001|Grameenphone", new ApnParameters("http://mmsc1:10021/mmsc/01", "10.10.55.34", 8799));

        //H2O (Report Count: 2, Last Report: 2015-03-12 18:18:11)
        put("310410|H2O|310410|H2O", new ApnParameters("http://mmsc.cingular.com", "66.209.11.33", 80));

        //H2O (Report Count: 110, Last Report: 2015-04-07 15:36:59)
        put("310410||310410|H2O", new ApnParameters("http://mmsc.cingular.com", "66.209.11.33", 80));

        //H2O Wireless (Report Count: 4, Last Report: 2015-03-17 16:31:03)
        put("310410||310410|H2O Wireless", new ApnParameters("http://mmsc.cingular.com", "66.209.11.33", 80));

        //halebop (Report Count: 1, Last Report: 2014-11-08 07:32:03)
        put("24001|halebop|24005|halebop", new ApnParameters("http://mmss/", "193.209.134.132", 80));

        //hallon (Report Count: 1, Last Report: 2015-01-16 16:39:58)
        put("24002|hallon|24002|hallon", new ApnParameters("http://mms.tre.se", "mmsproxy.tre.se", 8799));

        //hallon (Report Count: 1, Last Report: 2015-02-11 10:51:04)
        put("24002|hallon|24004|hallon", new ApnParameters("http://mms", "mmsproxy.tre.se", 8799));

        //helloMobil (Report Count: 1, Last Report: 2014-12-25 15:44:46)
        put("26207|helloMobil@|26207|helloMobil", new ApnParameters("http://10.81.0.7:8002", "82.113.100.5", 8080));

        //helloMobil (Report Count: 1, Last Report: 2015-03-12 06:51:54)
        put("26207||26207|helloMobil", new ApnParameters("http://10.81.0.7:8002", "82.113.100.5", 8080));

        //HOME (Report Count: 1, Last Report: 2014-12-12 20:47:15)
        put("302720|Petro-Canada|302720|HOME", new ApnParameters("http://mms.gprs.rogers.com", "10.128.1.69", 80));

        //Home (Report Count: 1, Last Report: 2014-08-04 00:41:02)
        put("310000|Defalut|31000|Home", new ApnParameters("http://mms.mobipcs.com", null, null));

        //Home (Report Count: 7, Last Report: 2015-03-24 16:38:29)
        put("310000|Default|310000|Home", new ApnParameters("http://pix.cspire.com", null, null));

        //Home (Report Count: 1, Last Report: 2015-01-29 23:47:23)
        put("310000|Default|31000|Home", new ApnParameters("http://pix.cspire.com", null, null));

        //Home (Report Count: 1, Last Report: 2015-02-24 00:46:37)
        put("310000|Default|31123|Home", new ApnParameters("http://pix.cspire.com", null, null));

        //Home (Report Count: 1, Last Report: 2014-12-24 15:20:40)
        put("310000|Default|31135|Home", new ApnParameters("http://mms.nemont.mobi/mms/", null, null));

        //Home (Report Count: 1, Last Report: 2015-04-02 19:45:04)
        put("310005|Default||Home", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Home (Report Count: 3, Last Report: 2015-02-22 20:40:18)
        put("310016|CricKet|31000|Home", new ApnParameters("http://mms.mycricket.com/servlets/mms", null, null));

        //HOME (Report Count: 5, Last Report: 2015-04-08 01:40:25)
        put("310260|Home|310260|HOME", new ApnParameters("http://mms.tracfone.com", "216.155.165.40", 8080));

        //HOME (Report Count: 260, Last Report: 2015-04-07 23:39:21)
        put("310260||310260|HOME", new ApnParameters("http://mms.tracfone.com", null, null));

        //Home (Report Count: 2, Last Report: 2014-12-25 13:09:29)
        put("310330|Alltel|310000|Home", new ApnParameters("null", null, null));

        //HOME (Report Count: 17, Last Report: 2015-03-26 21:52:32)
        put("310410|HOME|310410|HOME", new ApnParameters("http://mms-tf.net", "mms3.tracfone.com", 80));

        //HOME (Report Count: 5, Last Report: 2014-11-01 03:32:58)
        put("310410|Home@|310410|HOME", new ApnParameters("http://mmsc.cingular.com", "66.209.11.33", 80));

        //HOME (Report Count: 3, Last Report: 2015-01-25 22:01:05)
        put("310410|Verizon|310410|HOME", new ApnParameters("http://mms-tf.net", "mms3.tracfone.com", 80));

        //HOME (Report Count: 774, Last Report: 2015-04-08 15:21:24)
        put("310410||310410|HOME", new ApnParameters("http://mms-tf.net", "mms3.tracfone.com", 80));

        //Home (Report Count: 1, Last Report: 2014-09-14 17:20:38)
        put("310470|nTelos|310000|Home", new ApnParameters("http://mms.ntelospcs.net", null, null));

        //Home (Report Count: 6, Last Report: 2015-04-06 16:02:03)
        put("310470|nTelos|310120|Home", new ApnParameters("http://mms.ntelospcs.net", null, null));

        //Home (Report Count: 3, Last Report: 2015-03-27 22:16:43)
        put("310470|nTelos|310470|Home", new ApnParameters("http://mms.ntelospcs.net", null, null));

        //Home (Report Count: 1, Last Report: 2015-02-24 22:39:28)
        put("310750|Appalachian|311480|Home", new ApnParameters("", null, null));

        //Home (Report Count: 1, Last Report: 2015-02-27 15:14:11)
        put("311230|C Spire |311230|Home", new ApnParameters("", null, null));

        //Home (Report Count: 3, Last Report: 2015-03-15 00:27:17)
        put("311230|Default|310120|Home", new ApnParameters("http://pix.cspire.com/servlets/mms", null, null));

        //Home (Report Count: 13, Last Report: 2015-04-08 14:21:05)
        put("311230|Default|311230|Home", new ApnParameters("http://pix.cspire.com/servlets/mms", null, null));

        //Home (Report Count: 1, Last Report: 2014-09-23 15:18:15)
        put("312420|Default|312420|Home", new ApnParameters("http://mms.ntwls.net/nex-tech/mms.php", null, null));

        //Home (Report Count: 2, Last Report: 2014-10-20 11:50:07)
        put("330120|Open Mobile|330120|Home", new ApnParameters("", null, null));

        //Home Zone (Report Count: 10, Last Report: 2015-04-07 23:46:56)
        put("310000|Home Zone|31000|", new ApnParameters("http://mms.vzwreseller.com/servlets/mms", null, null));

        //HOT mobile (Report Count: 8, Last Report: 2015-03-27 10:36:31)
        put("42507|HOT mobile|42501|HOT mobile", new ApnParameters("http://mms.hotmobile.co.il", "80.246.131.99", 80));

        //HOT mobile (Report Count: 15, Last Report: 2015-04-05 22:11:14)
        put("42507|HOT mobile|42507|HOT mobile", new ApnParameters("http://mms.hotmobile.co.il", "80.246.131.99", 80));

        //HR VIP (Report Count: 1, Last Report: 2015-04-04 08:46:43)
        put("21910||21910|HR VIP", new ApnParameters("http://mms.vipnet.hr/servlets/mms", "212.91.99.91", 8080));

        //HT HR (Report Count: 1, Last Report: 2014-12-11 13:02:16)
        put("21901|,|21901|HT HR", new ApnParameters("http://mms.t-mobile.hr/servlets/mms", "10.12.0.4", 8080));

        //HT HR (Report Count: 5, Last Report: 2015-02-26 18:01:24)
        put("21901|HT HR|21901|HT HR", new ApnParameters("http://mms.t-mobile.hr/servlets/mms", "10.12.0.4", 8080));

        //HTC (Report Count: 31, Last Report: 2015-04-01 10:04:04)
        put("000000|HTC|000000|HTC", new ApnParameters("null", null, null));

        //HTC (Report Count: 2, Last Report: 2014-10-13 19:21:45)
        put("000000|HTC|310120|HTC", new ApnParameters("null", null, null));

        //HTC (Report Count: 1, Last Report: 2014-11-22 03:49:24)
        put("310000|HTC|310000|HTC", new ApnParameters("null", null, null));

        //HTC (Report Count: 1, Last Report: 2015-02-13 08:29:51)
        put("310000|Virgin Mobile|310000|HTC", new ApnParameters("null", null, null));

        //HTC (Report Count: 2, Last Report: 2015-04-07 12:30:38)
        put("310120|HTC|310120|HTC", new ApnParameters("http://mms.sprintpcs.com", "68.28.31.7", 80));

        //Hutch (Report Count: 1, Last Report: 2015-02-10 08:16:05)
        put("40411|Hutch|40411|Hutch", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Hutch (Report Count: 1, Last Report: 2014-11-13 06:15:46)
        put("40430|Hutch|40430|Hutch", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Hutch (Report Count: 1, Last Report: 2015-03-22 16:11:52)
        put("41308|Hutch|41308|Hutch", new ApnParameters("http://192.168.50.165", "192.168.50.163", 8080));

        //I TIM (Report Count: 9, Last Report: 2015-03-07 13:07:29)
        put("22201||22201|I TIM", new ApnParameters("http://mms.tim.it/servlets/mms", "213.230.130.89", 80));

        //I WIND (Report Count: 5, Last Report: 2015-03-26 09:27:59)
        put("22288||22288|I WIND", new ApnParameters("http://mms.wind.it", "212.245.244.100", 8080));

        //I WIND (Report Count: 1, Last Report: 2014-11-22 20:55:37)
        put("26003|Orange|22288|I WIND", new ApnParameters("http://mms.orange.pl", "192.168.6.104", 8080));

        //i wireless (Report Count: 1, Last Report: 2014-10-09 18:19:58)
        put("310260||310770|i wireless", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //i wireless (Report Count: 1, Last Report: 2015-03-09 14:28:42)
        put("310770|i wireless@|310770|i wireless", new ApnParameters("http://mmsc.iwireless.dataonair.net:6672", "209.4.229.31", 9401));

        //i wireless (Report Count: 1, Last Report: 2014-11-09 13:09:38)
        put("310770|i wireless|310770|i wireless", new ApnParameters("http://mmsc.iwireless.dataonair.net:6672", "209.4.229.31", 9401));

        //i wireless (Report Count: 19, Last Report: 2015-03-30 20:58:01)
        put("310770||310770|i wireless", new ApnParameters("http://mmsc.iwireless.dataonair.net:6672", "209.4.229.31", 9401));

        //ICE (Report Count: 1, Last Report: 2015-03-25 19:56:24)
        put("71201|Kolbi ICE|71201|ICE", new ApnParameters("http://mmsice", "10.184.202.24", 8080));

        //IDEA (Report Count: 1, Last Report: 2015-03-20 05:42:07)
        put("40404|!dea|40411|IDEA", new ApnParameters("http://10.4.42.21:8002/", "10.4.42.15", 8080));

        //IDEA (Report Count: 1, Last Report: 2014-10-28 00:59:09)
        put("40404|Idea|40411|IDEA", new ApnParameters("http://10.4.42.21:8002", "10.4.42.15", 8080));

        //IDEA (Report Count: 3, Last Report: 2015-01-04 11:43:10)
        put("40407|!dea|40407|IDEA", new ApnParameters("http://10.4.42.21:8002/", "10.4.42.15", 8080));

        //IDEA (Report Count: 1, Last Report: 2015-04-07 17:12:01)
        put("40407|IDEA|40407|IDEA", new ApnParameters("http://10.4.42.21:8002", "10.4.42.15", 8080));

        //IDEA (Report Count: 1, Last Report: 2014-10-20 15:44:27)
        put("40407|Idea|40407|IDEA", new ApnParameters("http://10.4.42.21:8002/", "10.4.42.15", 8080));

        //IDEA (Report Count: 1, Last Report: 2015-01-26 11:28:18)
        put("40414|!dea|40414|IDEA", new ApnParameters("http://100.1.201.171:10021/mmsc/", "100.1.201.172", 8799));

        //IDEA (Report Count: 1, Last Report: 2014-10-01 00:23:05)
        put("40414|Idea|40414|IDEA", new ApnParameters("http://10.4.42.21:8002", "10.4.42.15", 8080));

        //IDEA (Report Count: 1, Last Report: 2014-07-18 07:26:27)
        put("40414|Spice|40414|IDEA", new ApnParameters("http://10.4.42.21:8002/", "10.4.42.15", 8080));

        //IDEA (Report Count: 4, Last Report: 2015-03-25 17:43:26)
        put("40419|!dea|40419|IDEA", new ApnParameters("http://10.4.42.21:8002/", "10.4.42.15", 8080));

        //IDEA (Report Count: 1, Last Report: 2015-02-02 08:55:23)
        put("40419|IDEA|40419|IDEA", new ApnParameters("http://10.4.42.21:8002", "10.4.42.15", 8080));

        //IDEA (Report Count: 1, Last Report: 2015-03-14 03:43:42)
        put("40419|Idea - Kerala|40419|IDEA", new ApnParameters("http://10.4.42.21:8002/", "10.4.42.15", 8080));

        //IDEA (Report Count: 4, Last Report: 2015-04-08 17:08:07)
        put("40419|Idea|40419|IDEA", new ApnParameters("http://10.4.42.21:8002/", "10.4.42.15", 8080));

        //IDEA (Report Count: 1, Last Report: 2014-12-28 13:20:56)
        put("40422|!dea|40411|IDEA", new ApnParameters("http://10.4.42.21:8002/", "10.4.42.15", 8080));

        //IDEA (Report Count: 34, Last Report: 2015-03-23 12:48:07)
        put("40422|!dea|40422|IDEA", new ApnParameters("http://10.4.42.21:8002/", "10.4.42.15", 8080));

        //IDEA (Report Count: 1, Last Report: 2014-12-23 03:30:18)
        put("40422|!dea|405799|IDEA", new ApnParameters("http://10.4.42.21:8002", "10.4.42.15", 8080));

        //IDEA (Report Count: 4, Last Report: 2015-03-29 02:57:01)
        put("40422|IDEA|40422|IDEA", new ApnParameters("http://10.4.42.21:8002", "10.4.42.15", 8080));

        //IDEA (Report Count: 1, Last Report: 2015-01-27 09:14:22)
        put("40422|Idea - Maharashtra|40422|IDEA", new ApnParameters("http://10.4.42.21:8002/", "10.4.42.15", 8080));

        //IDEA (Report Count: 7, Last Report: 2015-04-04 10:06:54)
        put("40422|Idea|40422|IDEA", new ApnParameters("http://10.4.42.21:8002/", "10.4.42.15", 8080));

        //IDEA (Report Count: 4, Last Report: 2014-12-25 05:40:07)
        put("40422||40422|IDEA", new ApnParameters("http://10.4.42.21:8002", "10.4.42.15", 8080));

        //IDEA (Report Count: 11, Last Report: 2015-04-04 11:56:20)
        put("40424|!dea|40424|IDEA", new ApnParameters("http://10.4.42.21:8002/", "10.4.42.15", 8080));

        //IDEA (Report Count: 1, Last Report: 2014-12-31 17:59:45)
        put("40424|IDEA|40424|IDEA", new ApnParameters("http://10.4.42.21:8002/", "10.4.42.15", 8080));

        //Idea (Report Count: 4, Last Report: 2015-01-11 10:50:00)
        put("40444|!dea|40444|Idea", new ApnParameters("http://10.4.42.21:8002", "10.4.42.15", 8080));

        //Idea (Report Count: 1, Last Report: 2014-09-25 02:31:15)
        put("40444|Spice|40444|Idea", new ApnParameters("http://10.4.42.21:8002", "10.4.42.15", 8080));

        //IDEA (Report Count: 1, Last Report: 2015-02-06 02:33:52)
        put("40446|Vodafone IN|40419|IDEA", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //IDEA (Report Count: 5, Last Report: 2015-02-03 13:31:11)
        put("40456|!dea|40456|IDEA", new ApnParameters("http://10.4.42.21:8002/", "10.4.42.15", 8080));

        //IDEA (Report Count: 21, Last Report: 2015-03-28 08:46:40)
        put("40478|!dea|40478|IDEA", new ApnParameters("http://10.4.42.21:8002/", "10.4.42.15", 8080));

        //IDEA (Report Count: 10, Last Report: 2015-03-14 17:39:13)
        put("40478|Idea|40478|IDEA", new ApnParameters("http://10.4.42.21:8002/", "10.4.42.15", 8080));

        //IDEA (Report Count: 1, Last Report: 2014-12-31 04:36:01)
        put("40478|I¤ea|40478|IDEA", new ApnParameters("http://10.4.42.21:8002/", "10.4.42.15", 8080));

        //IDEA (Report Count: 1, Last Report: 2014-12-20 05:58:01)
        put("40478||40478|IDEA", new ApnParameters("http://10.4.42.21:8002", "10.4.42.15", 8080));

        //Idea (Report Count: 2, Last Report: 2015-02-05 05:29:18)
        put("40478||40478|Idea", new ApnParameters("http://10.4.42.21:8002/", "10.4.42.15", 8080));

        //IDEA (Report Count: 1, Last Report: 2015-02-09 16:46:29)
        put("40487|!dea|40412|IDEA", new ApnParameters("http://10.4.42.21:8002", "10.4.42.15", 8080));

        //IDEA (Report Count: 6, Last Report: 2015-03-19 17:07:42)
        put("40487|!dea|40487|IDEA", new ApnParameters("http://10.4.42.21:8002/", "10.4.42.15", 8080));

        //IDEA (Report Count: 1, Last Report: 2014-09-25 10:16:17)
        put("40487|Idea|40487|IDEA", new ApnParameters("http://10.4.42.21:8002/", "10.4.42.15", 8080));

        //IDEA (Report Count: 5, Last Report: 2015-03-26 00:39:03)
        put("40489|!dea|40489|IDEA", new ApnParameters("http://10.4.42.21:8002/", "10.4.42.15", 8080));

        //Idea (Report Count: 1, Last Report: 2014-11-25 08:52:25)
        put("40492|airtel|40492|Idea", new ApnParameters("http://10.4.42.21:8002/", "10.4.42.15", 8080));

        //IDEA (Report Count: 1, Last Report: 2015-01-01 05:36:55)
        put("405025||40407|IDEA", new ApnParameters("http://10.4.42.21:8002", "10.4.42.15", 8080));

        //IDEA (Report Count: 1, Last Report: 2015-02-08 17:00:08)
        put("40570|!dea|40552|IDEA", new ApnParameters("http://10.4.42.21:8002/", "10.4.42.15", 8080));

        //IDEA (Report Count: 1, Last Report: 2015-03-27 13:25:38)
        put("405799|!dea|40422|IDEA", new ApnParameters("http://10.4.42.21:8002/", "10.4.42.15", 8080));

        //IDEA (Report Count: 4, Last Report: 2015-01-25 10:20:47)
        put("405799|!dea|405799|IDEA", new ApnParameters("http://10.4.42.21:8002/", "10.4.42.15", 8080));

        //Idea (Report Count: 3, Last Report: 2015-03-09 16:55:51)
        put("405799|Idea|40492|Idea", new ApnParameters("http://10.4.42.21:8002/", "10.4.42.15", 8080));

        //IDEA (Report Count: 2, Last Report: 2015-01-15 18:06:26)
        put("405799|Idea|405799|IDEA", new ApnParameters("http://10.4.42.21:8002/", "10.4.42.15", 8080));

        //Idea (Report Count: 1, Last Report: 2014-10-14 13:14:37)
        put("405799|Idea|405799|Idea", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //IDEA (Report Count: 1, Last Report: 2014-11-11 14:16:18)
        put("405845|Idea|405845|IDEA", new ApnParameters("http://10.4.42.21:8002/", "10.4.42.15", 8080));

        //Idea (Report Count: 1, Last Report: 2015-02-23 11:33:35)
        put("405848|!dea|405848|Idea", new ApnParameters("http://mms1.live.vodafone.in/mms/", null, null));

        //IDEA (Report Count: 1, Last Report: 2014-10-27 15:37:39)
        put("405848|!dea|405850|IDEA", new ApnParameters("http://10.4.42.21:8002/", "10.4.42.15", 8080));

        //Idea (Report Count: 4, Last Report: 2015-03-20 05:21:49)
        put("405850|!dea|405850|Idea", new ApnParameters("http://10.4.42.21:8002/", "10.4.42.15", 8080));

        //Idea (Report Count: 3, Last Report: 2015-01-05 03:42:08)
        put("405852|Idea|405852|Idea", new ApnParameters("http://10.4.42.21:8002/", "10.4.42.15", 8080));

        //IDEA Assam (Report Count: 1, Last Report: 2015-04-04 12:08:54)
        put("405845|Idea|405845|IDEA Assam", new ApnParameters("http://10.4.42.21:8002/", "10.4.42.15", 8080));

        //IDEA Harayana (Report Count: 1, Last Report: 2015-02-20 13:24:26)
        put("40412|!dea|40412|IDEA Harayana", new ApnParameters("http://10.4.42.21:8002/", "10.4.42.15", 8080));

        //IDEA Harayana (Report Count: 1, Last Report: 2015-03-11 03:59:38)
        put("40412|IDEA|40412|IDEA Harayana", new ApnParameters("http://10.4.42.21:8002/", "10.4.42.15", 8080));

        //IDEA Maharashtra (Report Count: 1, Last Report: 2015-02-20 04:26:22)
        put("40422|!dea|40422|IDEA Maharashtra", new ApnParameters("http://10.4.42.21:8002", "10.4.42.15", 8080));

        //IDEA Maharashtra (Report Count: 1, Last Report: 2015-01-15 00:02:58)
        put("40422|IDEA|40422|IDEA Maharashtra", new ApnParameters("http://10.4.42.21:8002/", "10.4.42.15", 8080));

        //IDEA MP (Report Count: 6, Last Report: 2015-03-28 10:24:17)
        put("40478|!dea|40478|IDEA MP", new ApnParameters("http://10.4.42.21:8002/", "10.4.42.15", 8080));

        //IDEA MP (Report Count: 2, Last Report: 2015-03-03 06:47:38)
        put("40478|Idea|40478|IDEA MP", new ApnParameters("http://10.4.42.21:8002/", "10.4.42.15", 8080));

        //IDEA U.P.(E) (Report Count: 1, Last Report: 2015-03-06 07:25:14)
        put("40489|!dea|40489|IDEA U.P.(E)", new ApnParameters("http://10.4.42.21:8002", "10.4.42.15", 8080));

        //IL Cellcom (Report Count: 2, Last Report: 2014-11-09 16:50:59)
        put("42502|Cellcom|42502|IL Cellcom", new ApnParameters("http://mms.cellcom.co.il", "172.31.29.38", 8080));

        //IL Cellcom (Report Count: 1, Last Report: 2014-11-11 12:57:55)
        put("42508|GOLAN T|42502|IL Cellcom", new ApnParameters("http://10.224.228.81", "10.224.228.81", 80));

        //IL Pelephone (Report Count: 5, Last Report: 2015-01-07 18:27:56)
        put("42503|Pelephone|42503|IL Pelephone", new ApnParameters("http://mmsu.pelephone.net.il", "10.170.252.104", 9093));

        //IL Pelephone (Report Count: 1, Last Report: 2014-11-10 12:00:44)
        put("42503|Rami Levy|42503|IL Pelephone", new ApnParameters("http://mmsu.pelephone.net.il", "10.170.252.104", 9093));

        //INA-airtel (Report Count: 1, Last Report: 2014-12-07 06:08:59)
        put("40470|Airtel|40470|INA-airtel", new ApnParameters("http://100.1.201.171:10021/mmsc", "100.1.201.172", 8799));

        //INA-IDEA (Report Count: 1, Last Report: 2014-11-06 05:48:33)
        put("40404|!dea|40411|INA-IDEA", new ApnParameters("http://10.4.42.21:8002/", "10.4.42.15", 8080));

        //INA-Idea (Report Count: 1, Last Report: 2015-03-28 03:17:08)
        put("40422|!dea|40470|INA-Idea", new ApnParameters("http://10.4.42.21:8002", "10.4.42.15", 8080));

        //IND airtel (Report Count: 13, Last Report: 2015-03-27 11:04:32)
        put("40410|Airtel|40410|IND airtel", new ApnParameters("http://100.1.201.171:10021/mmsc", "100.1.201.172", 8799));

        //IND airtel (Report Count: 2, Last Report: 2014-09-20 08:05:26)
        put("40431|airtel|40431|IND airtel", new ApnParameters("http://100.1.201.171:10021/mmsc", "100.1.201.172", 8799));

        //IND airtel (Report Count: 2, Last Report: 2015-01-25 09:42:11)
        put("40440|airtel|40440|IND airtel", new ApnParameters("http://100.1.201.171:10021/mmsc", "100.1.201.172", 8799));

        //IND airtel (Report Count: 2, Last Report: 2015-01-06 01:31:44)
        put("40445|Airtel|40445|IND airtel", new ApnParameters("http://100.1.201.171:10021/mmsc", "100.1.201.172", 8799));

        //IND airtel (Report Count: 7, Last Report: 2015-03-05 15:35:57)
        put("40449|airtel|40449|IND airtel", new ApnParameters("http://100.1.201.171:10021/mmsc", "100.1.201.172", 8799));

        //IND airtel (Report Count: 1, Last Report: 2015-03-03 08:29:42)
        put("40470|airtel|40470|IND airtel", new ApnParameters("http://100.1.201.171:10021/mmsc", "100.1.201.172", 8799));

        //IND airtel (Report Count: 2, Last Report: 2015-02-02 18:42:01)
        put("40470|airtel|40492|IND airtel", new ApnParameters("http://100.1.201.171:10021/mmsc", "100.1.201.172", 8799));

        //IND airtel (Report Count: 1, Last Report: 2014-12-12 14:07:04)
        put("40486|Hutch|40445|IND airtel", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //IND airtel (Report Count: 5, Last Report: 2015-03-25 09:42:03)
        put("40486|Vodafone IN|40445|IND airtel", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //IND airtel (Report Count: 1, Last Report: 2014-09-26 07:53:01)
        put("40490|AirTel|40490|IND airtel", new ApnParameters("http://100.1.201.171:10021/mmsc", "100.1.201.172", 8799));

        //IND airtel (Report Count: 2, Last Report: 2015-03-27 11:58:51)
        put("40492|airtel|40492|IND airtel", new ApnParameters("http://100.1.201.171:10021/mmsc", "100.1.201.172", 8799));

        //IND airtel (Report Count: 1, Last Report: 2014-10-01 04:39:55)
        put("40493|airtel|40493|IND airtel", new ApnParameters("http://100.1.201.171:10021/mmsc", "100.1.201.172", 8799));

        //IND airtel (Report Count: 1, Last Report: 2015-03-10 18:03:36)
        put("40494|airtel|40440|IND airtel", new ApnParameters("http://100.1.201.171:10021/mmsc", "100.1.201.172", 8799));

        //IND airtel (Report Count: 6, Last Report: 2015-04-04 14:44:30)
        put("40494|airtel|40494|IND airtel", new ApnParameters("http://100.1.201.171:10021/mmsc", "100.1.201.172", 8799));

        //IND airtel (Report Count: 4, Last Report: 2015-02-13 10:00:05)
        put("40495|Airtel|40495|IND airtel", new ApnParameters("http://100.1.201.171:10021/mmsc", "100.1.201.172", 8799));

        //IND airtel (Report Count: 1, Last Report: 2015-02-14 07:49:45)
        put("40496|airtel|40496|IND airtel", new ApnParameters("http://100.1.201.171:10021/mmsc", "100.1.201.172", 8799));

        //IND airtel (Report Count: 1, Last Report: 2015-03-19 13:04:27)
        put("40497|airtel|40497|IND airtel", new ApnParameters("http://100.1.201.171:10021/mmsc", "100.1.201.172", 8799));

        //IND airtel (Report Count: 1, Last Report: 2014-10-19 01:07:02)
        put("40551|airtel|40551|IND airtel", new ApnParameters("http://100.1.201.171:10021/mmsc", "100.1.201.172", 8799));

        //IND airtel (Report Count: 3, Last Report: 2015-04-03 09:33:35)
        put("40552|Airtel|40552|IND airtel", new ApnParameters("http://100.1.201.171:10021/mmsc", "100.1.201.172", 8799));

        //IND airtel (Report Count: 1, Last Report: 2014-11-21 11:06:58)
        put("40554|airtel|40554|IND airtel", new ApnParameters("http://100.1.201.171:10021/mmsc", "100.1.201.172", 8799));

        //IND airtel (Report Count: 1, Last Report: 2015-02-28 08:22:11)
        put("|TATA DOCOMO|40402|IND airtel", new ApnParameters("http://mmsc/", "10.124.26.94", 8799));

        //IND TELKOMSEL (Report Count: 2, Last Report: 2015-03-18 11:18:38)
        put("51010||51010|IND TELKOMSEL", new ApnParameters("http://mms.telkomsel.com", "10.1.89.150", 8000));

        //IND XL (Report Count: 6, Last Report: 2015-03-25 07:56:40)
        put("51011|Axis|51011|IND XL", new ApnParameters("http://mmc.xl.net.id/servlets/mms", "202.152.240.50", 8080));

        //IND XL (Report Count: 38, Last Report: 2015-04-04 09:30:32)
        put("51011|XL Axiata|51011|IND XL", new ApnParameters("http://mmc.xl.net.id/servlets/mms", "202.152.240.50", 8080));

        //IND XL (Report Count: 1, Last Report: 2015-03-23 07:08:17)
        put("51011|XL STAR|51011|IND XL", new ApnParameters("http://mmc.xl.net.id/servlets/mms", "202.152.240.50", 8080));

        //IND XL (Report Count: 3, Last Report: 2015-01-30 11:33:33)
        put("51011|XL|51011|IND XL", new ApnParameters("http://mmc.xl.net.id/servlets/mms", "202.152.240.50", 8080));

        //IND-TELKOMSEL (Report Count: 1, Last Report: 2014-11-14 13:02:02)
        put("51010|T-Sel|51010|IND-TELKOMSEL", new ApnParameters("http://mms.telkomsel.com", "10.1.89.150", 8000));

        //IND-TELKOMSEL (Report Count: 1, Last Report: 2014-10-17 08:49:43)
        put("51010|TELKOMSEL|51010|IND-TELKOMSEL", new ApnParameters("http://mms.telkomsel.com", "10.1.89.150", 8000));

        //IND-TELKOMSEL (Report Count: 16, Last Report: 2015-03-21 06:49:03)
        put("51010||51010|IND-TELKOMSEL", new ApnParameters("http://mms.telkomsel.com", "10.1.89.150", 8000));

        //IND-XL (Report Count: 1, Last Report: 2014-12-02 05:57:50)
        put("51011|XL Axiata|51011|IND-XL", new ApnParameters("http://mmc.xl.net.id/servlets/mms", "202.152.240.50", 8080));

        //Indicador de itinerancia desactivado (Report Count: 2, Last Report: 2014-12-15 02:35:49)
        put("310120|Sprint|311490|Indicador de itinerancia desactivado", new ApnParameters("http://mms.sprintpcs.com", "68.28.31.7", 80));

        //Indicador de roaming desactivado (Report Count: 2, Last Report: 2015-03-29 00:15:33)
        put("31000||123456|Indicador de roaming desactivado", new ApnParameters("http://mms.vzwreseller.com/", null, null));

        //Indikator Roaming Mati (Report Count: 2, Last Report: 2014-11-22 02:02:00)
        put("51009|smartfren|51009|Indikator Roaming Mati", new ApnParameters("http://10.17.93.103:8080", "10.17.27.250", 8080));

        //INDOSAT (Report Count: 4, Last Report: 2014-11-04 01:54:19)
        put("51001|INDOSAT|51001| ", new ApnParameters("http://mmsc.indosat.com", "10.19.19.19", 8080));

        //INDOSAT (Report Count: 39, Last Report: 2015-04-05 08:24:31)
        put("51001|INDOSAT|51001|INDOSAT", new ApnParameters("http://mmsc.indosat.com", "10.19.19.19", 8080));

        //INDOSAT (Report Count: 2, Last Report: 2015-03-01 07:02:01)
        put("51001|MENTARI|51001|INDOSAT", new ApnParameters("http://mmsc.indosat.com", "10.19.19.19", 8080));

        //INDOSAT (Report Count: 1, Last Report: 2015-01-20 22:07:56)
        put("51001||51001|INDOSAT", new ApnParameters("http://10.17.93.103:8080", "10.17.27.250", 8080));

        //Indosat@ (Report Count: 1, Last Report: 2015-02-05 06:53:12)
        put("51001|INDOSAT|51001|Indosat@", new ApnParameters("http://mmsc.indosat.com", "10.19.19.19", 8080));

        //Inland (Report Count: 4, Last Report: 2014-12-07 22:20:05)
        put("310058|Inland|31000|Inland", new ApnParameters("http://mms.inland3g.com/inland/mms.php", null, null));

        //inland (Report Count: 4, Last Report: 2014-12-31 02:54:23)
        put("310580|inland|31000|", new ApnParameters("http://mms.inland3g.com/inland/mms.php", null, null));

        //Iowa Wireless USA (Report Count: 3, Last Report: 2015-01-15 01:02:44)
        put("310260||310770|Iowa Wireless USA", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //IQ (Report Count: 3, Last Report: 2015-03-07 16:45:16)
        put("24007|Tele2|24007|IQ", new ApnParameters("http://mmsc.tele2.se", "130.244.202.30", 8080));

        //Iusacell (Report Count: 2, Last Report: 2014-11-27 14:27:27)
        put("334050|IUSACELL|334050|Iusacell", new ApnParameters("http://mms.iusacell3g.com/", null, null));

        //Iusacell (Report Count: 2, Last Report: 2015-02-28 05:25:12)
        put("334050|UNEFON|334050|Iusacell", new ApnParameters("http://mms.iusacell3g.com/", null, null));

        //Iusacell NextG (Report Count: 1, Last Report: 2014-10-01 00:45:17)
        put("334050|UNEFON|334050|Iusacell NextG", new ApnParameters("http://mms.iusacell3g.com/", null, null));

        //iWireless (Report Count: 1, Last Report: 2014-11-15 04:41:57)
        put("310770|iWireless|310260|iWireless", new ApnParameters("http://mmsc.iwireless.dataonair.net:6672", null, null));

        //iWireless (Report Count: 1, Last Report: 2014-10-28 13:30:03)
        put("310770|iWireless|310770|iWireless", new ApnParameters("http://mmsc.iwireless.dataonair.net:6672", "209.4.229.31", 9401));

        //iWireless (Report Count: 8, Last Report: 2015-04-03 23:44:52)
        put("310770||310260|iWireless", new ApnParameters("http://mmsc.iwireless.dataonair.net:6672", "209.4.229.31", 9401));

        //iWireless (Report Count: 70, Last Report: 2015-04-02 03:55:07)
        put("310770||310770|iWireless", new ApnParameters("http://mmsc.iwireless.dataonair.net:6672", "209.4.229.31", 9401));

        //IWS (Report Count: 1, Last Report: 2014-07-12 16:46:33)
        put("310770||310770|IWS", new ApnParameters("http://mmsc.iwireless.dataonair.net:6672", "209.4.229.31", 9401));

        //ja! mobil (Report Count: 1, Last Report: 2014-12-29 11:07:12)
        put("26201|ja! mobil|26201| ", new ApnParameters("http://mms-t-mobile.de/servlets/mms", "172.28.23.131", 8008));

        //jamesvalley (Report Count: 1, Last Report: 2015-03-13 21:51:52)
        put("310920|jamesvalley|31060|", new ApnParameters("http://m.iot1.com/jamesvalley/mms.php", "m.iot1.com", 9201));

        //Joe (Report Count: 7, Last Report: 2015-03-03 22:27:41)
        put("20810|Joe|20810|Joe", new ApnParameters("http://mms1", "10.151.0.1", 8080));

        //Kabayan (Report Count: 1, Last Report: 2015-03-19 08:45:09)
        put("45412|Kabayan|45413|Kabayan", new ApnParameters("http://mms.hk.chinamobile.com/mms", "172.31.31.36", 8080));

        //KabelBW (Report Count: 1, Last Report: 2014-12-09 18:40:32)
        put("26207||26207|KabelBW", new ApnParameters("http://10.81.0.7:8002", "82.113.100.5", 8080));

        //klarmobil (Report Count: 1, Last Report: 2015-03-07 19:54:31)
        put("26201|klarmobil|26201| ", new ApnParameters("http://mms.t-mobile.de/servlets/mms", "172.28.23.131", 8008));

        //klarmobil (Report Count: 4, Last Report: 2015-01-30 15:55:56)
        put("26207||26207|klarmobil", new ApnParameters("http://10.81.0.7:8002", "82.113.100.5", 8080));

        //Koodo (Report Count: 1, Last Report: 2015-01-19 03:10:31)
        put("302220|Home|302220|Koodo", new ApnParameters("http://aliasredirect.net/proxy/koodo/mmsc", "74.49.0.18", 80));

        //Koodo (Report Count: 2, Last Report: 2015-04-08 03:29:05)
        put("302220|TELUS@|302220|Koodo", new ApnParameters("http://aliasredirect.net/proxy/koodo/mmsc", "74.49.0.18", 80));

        //Koodo (Report Count: 3895, Last Report: 2015-04-08 17:19:36)
        put("302220||302220|Koodo", new ApnParameters("http://aliasredirect.net/proxy/koodo/mmsc", "74.49.0.18", 80));

        //KOR SK Telecom (Report Count: 1, Last Report: 2014-11-29 03:35:59)
        put("45005|SKTelecom|45005|KOR SK Telecom", new ApnParameters("http//omms.nate.com:9082/oma_mms", "smart.nate.com", 9093));

        //KT (Report Count: 1, Last Report: 2015-04-02 07:29:05)
        put("45008||45008|KT", new ApnParameters("", null, null));

        //KT, VIVA (Report Count: 3, Last Report: 2015-04-05 00:47:52)
        put("41904|VIVA|41904|KT, VIVA", new ApnParameters("http://172.16.128.80:38090/was", "172.16.128.228", 8080));

        //KYIVSTAR (Report Count: 3, Last Report: 2015-03-20 23:47:58)
        put("25503|KYIVSTAR|25503|KYIVSTAR", new ApnParameters("http://mms.kyivstar.net", "10.10.10.10", 8080));

        //Kyivstar (Report Count: 1, Last Report: 2015-02-26 16:28:50)
        put("25503|KYIVSTAR|25503|Kyivstar", new ApnParameters("http://mmsc:8002/", "192.168.10.10", 8080));

        //Kyivstar (Report Count: 1, Last Report: 2015-01-12 15:48:57)
        put("25503||25503|Kyivstar", new ApnParameters("http://mms.kyivstar.net", "10.10.10.10", 8080));

        //KZ KCELL (Report Count: 1, Last Report: 2015-01-07 15:47:46)
        put("40102|ACTIV|40102|KZ KCELL", new ApnParameters("http://mms.kcell.kz/post", "195.47.255.7", 8080));

        //La Poste Mobile (Report Count: 1, Last Report: 2015-01-02 20:03:58)
        put("20810|,|20810|La Poste Mobile", new ApnParameters("http://mmsdebitel", "10.143.156.3", 8080));

        //La Poste Mobile (Report Count: 35, Last Report: 2015-04-06 11:20:46)
        put("20810|La Poste Mobile|20810|La Poste Mobile", new ApnParameters("http://mmsdebitel", "10.143.156.3", 8080));

        //leaco (Report Count: 1, Last Report: 2015-03-01 23:48:48)
        put("311310|leaco|31000|", new ApnParameters("http://204.181.155.217/mms/", null, null));

        //Lebara (Report Count: 1, Last Report: 2015-02-14 22:43:57)
        put("23415|Lebara|23415|Lebara", new ApnParameters("http://mms.lebara.co.uk/servlets/mms", "212.183.137.12", 8799));

        //Lebara (Report Count: 1, Last Report: 2014-11-19 14:08:52)
        put("26201|Lebara|26201|Lebara", new ApnParameters("http://mms.t-mobile.de/servlets/mms", "172.28.23.131", 8008));

        //LeclercMobile (Report Count: 4, Last Report: 2015-02-21 18:10:24)
        put("20810|LeclercMobile|20810|LeclercMobile", new ApnParameters("http://mms66", "10.143.156.9", 8080));

        //LG U+ (Report Count: 6, Last Report: 2015-04-01 22:51:37)
        put("45006|LGU+|45006|LG U+", new ApnParameters("http://omammsc.uplus.co.kr:9084", null, null));

        //LGE Lab (Report Count: 1, Last Report: 2015-01-19 09:13:38)
        put("45000|2400 2924|45000|LGE Lab", new ApnParameters("http://203.229.247.19/mm1", "203.229.247.28", 8000));

        //LGU+ (Report Count: 1, Last Report: 2015-02-02 11:25:47)
        put("45006|LGU+|45006|", new ApnParameters("http://omammsc.uplus.co.kr:9084", null, null));

        //LIDL MOBILE (Report Count: 5, Last Report: 2015-03-18 19:23:16)
        put("26207||26207|LIDL MOBILE", new ApnParameters("http://10.81.0.7:8002", "82.113.100.5", 8080));

        //Lidl mobile (Report Count: 2, Last Report: 2014-12-25 15:43:39)
        put("26207||26207|Lidl mobile", new ApnParameters("http://10.81.0.7:8002/", "82.113.100.6", 8080));

        //life:) (Report Count: 1, Last Report: 2015-03-20 11:09:31)
        put("25506|life:)|25506|", new ApnParameters("http://mms.life.com.ua/cmmsc/post", "212.58.162.230", 8080));

        //life:) (Report Count: 14, Last Report: 2015-04-08 15:05:08)
        put("25506|life:)|25506|life:)", new ApnParameters("http://mms.life.com.ua/cmmsc/post", "212.58.162.230", 8080));

        //life:) (Report Count: 1, Last Report: 2015-04-05 18:01:41)
        put("25506||25506|life:)", new ApnParameters("http://mms.life", "10.10.10.10", 8080));

        //LIME (Report Count: 4, Last Report: 2015-03-02 13:59:49)
        put("338180|LIME|338180|LIME", new ApnParameters("http://mmsc", "10.20.5.34", 8799));

        //LMT (Report Count: 1, Last Report: 2014-11-27 21:09:40)
        put("24701|LMT|24701|LMT", new ApnParameters("http://mmsc.lmt.lv/mmsc", "212.93.97.201", 80));

        //LMT GSM (Report Count: 1, Last Report: 2014-12-29 20:51:55)
        put("24701|LMT|24701|LMT GSM", new ApnParameters("http://mmsc.lmt.lv/mmsc", null, null));

        //LMT GSM (Report Count: 1, Last Report: 2014-10-03 12:44:48)
        put("24701||24701|LMT GSM", new ApnParameters("http://mmsc.lmt.lv/mmsc", null, null));

        //LV LMT (Report Count: 1, Last Report: 2014-12-08 08:51:43)
        put("24701|LMT LV|24701|LV LMT", new ApnParameters("http://mmsc.lmt.lv/mmsc", "212.93.97.201", 8080));

        //LV LMT (Report Count: 2, Last Report: 2015-02-11 14:30:01)
        put("24701|LMT|24701|LV LMT", new ApnParameters("http://mmsc.lmt.lv/mmsc", null, null));

        //LV LMT (Report Count: 2, Last Report: 2015-03-01 08:05:17)
        put("24701||24701|LV LMT", new ApnParameters("http://mmsc.lmt.lv/mmsc", null, null));

        //Lycamobile (Report Count: 1, Last Report: 2015-04-08 08:03:47)
        put("23426|Lycamobile|23410|Lycamobile", new ApnParameters("http://mms.um.three.co.uk:10021/mmsc", "mms.three.co.uk", 8799));

        //Lycamobile (Report Count: 16, Last Report: 2015-03-11 23:51:41)
        put("310260|Lycamobile|310260|Lycamobile", new ApnParameters("http://lyca.mmsmvno.com/mms/wapenc", null, null));

        //M1 (Report Count: 1, Last Report: 2015-03-19 08:31:21)
        put("52503||52503|M1", new ApnParameters("http://mmsgw:8002/", "172.16.14.10", 8080));

        //MAGTI-GSM-GEO (Report Count: 2, Last Report: 2015-03-20 16:44:23)
        put("28202|MAGTICOM|28202|MAGTI-GSM-GEO", new ApnParameters("http://mms.magticom.ge", "81.95.160.16", 9401));

        //MAGTICOM (Report Count: 2, Last Report: 2015-03-27 11:03:59)
        put("28202|MAGTICOM|28202|MAGTICOM", new ApnParameters("http://mms.magtigsm.ge/", "81.95.160.16", 9401));

        //Maritime (Report Count: 1, Last Report: 2014-11-04 01:59:18)
        put("310410||310410|Maritime", new ApnParameters("http://mmsc.mobile.att.net", "proxy.mobile.att.net", 80));

        //Maxis (Report Count: 2, Last Report: 2014-10-20 17:23:04)
        put("50212||50212|Maxis", new ApnParameters("http://172.16.74.100:10021/mmsc", "202.75.133.49", 80));

        //MEDIONmobile (Report Count: 10, Last Report: 2015-04-06 14:32:37)
        put("26203|MEDIONmobile|26203|MEDIONmobile", new ApnParameters("http://mms/eplus/", "212.23.97.153", 5080));

        //MEDIONmobile (Report Count: 2, Last Report: 2015-01-21 18:04:24)
        put("26203||26203|MEDIONmobile", new ApnParameters("http://mms/eplus/", "212.23.97.153", 5080));

        //MEGACOM (Report Count: 1, Last Report: 2014-10-09 16:09:12)
        put("43705|MegaCom|43705|MEGACOM", new ApnParameters("http://10.230.3.19:8002", "10.230.3.18", 8080));

        //MegaFon (Report Count: 88, Last Report: 2015-04-08 17:31:15)
        put("25002|MegaFon|25002|MegaFon", new ApnParameters("http://mmsc:8002", "10.10.10.10", 8080));

        //MegaFon (Report Count: 1, Last Report: 2015-03-08 11:19:17)
        put("25002||25002|MegaFon", new ApnParameters("http://mmsc:8002", "10.10.10.10", 8080));

        //MegaFon (Report Count: 1, Last Report: 2015-03-05 09:11:06)
        put("25011|YOTA|25002|MegaFon", new ApnParameters("http://mmsc:8002", "10.10.10.10", 8080));

        //MegaFon OJSC (Report Count: 1, Last Report: 2014-11-23 09:54:53)
        put("25002|MegaFon|25002|MegaFon OJSC", new ApnParameters("http://mmsc:8002", "10.10.10.10", 8080));

        //MegaFon OJSC 4G (Report Count: 1, Last Report: 2014-12-12 14:13:46)
        put("25002|MegaFon|25002|MegaFon OJSC 4G", new ApnParameters("http://mmsc:8002", "10.10.10.10", 8080));

        //MegaFon RUS (Report Count: 18, Last Report: 2015-03-25 18:13:05)
        put("25002|MegaFon|25002|MegaFon RUS", new ApnParameters("http://mmsc:8002", "10.10.10.10", 8080));

        //MegaFon@ (Report Count: 2, Last Report: 2015-04-08 16:02:04)
        put("25002|MegaFon|25002|MegaFon@", new ApnParameters("http://mmsc:8002", "10.10.10.10", 8080));

        //MENTARI (Report Count: 1, Last Report: 2015-04-03 13:43:51)
        put("51001|MENTARI|51001|MENTARI", new ApnParameters("http://mms.telkomsel.com", "10.1.89.150", 8000));

        //MERCHANTRADE (Report Count: 4, Last Report: 2015-03-21 04:03:26)
        put("50219|MERCHANTRADE|50219|", new ApnParameters("http://mms.celcom.net.my", "10.128.1.242", 8080));

        //MERCHANTRADE (Report Count: 1, Last Report: 2015-01-26 09:12:24)
        put("50219|MERCHANTRADE|50219|MERCHANTRADE", new ApnParameters("http://mms.digi.com.my/servlets/mms", "203.92.128.160", 80));

        //Meteor (Report Count: 5, Last Report: 2015-03-17 16:14:47)
        put("27203||27203|Meteor", new ApnParameters("http://mms.mymeteor.ie", "10.85.85.85", 8799));

        //MetroPCS (Report Count: 1, Last Report: 2014-06-25 16:50:47)
        put("25001|MTS|310260|MetroPCS", new ApnParameters("http://metropcs.mmsmvno.com/mms/wapenc", null, null));

        //MetroPCS (Report Count: 2, Last Report: 2015-03-04 21:14:31)
        put("310260|MetroPCS@|310260|MetroPCS", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //MetroPCS (Report Count: 6, Last Report: 2015-04-01 23:03:39)
        put("310260|MetroPCS|310260|MetroPCS", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //MetroPCS (Report Count: 5, Last Report: 2015-02-04 05:29:52)
        put("310260|Verizon|310260|MetroPCS", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //MetroPCS (Report Count: 6096, Last Report: 2015-04-08 17:28:45)
        put("310260||310260|MetroPCS", new ApnParameters("http://metropcs.mmsmvno.com/mms/wapenc", null, null));

        //MetroPCS (Report Count: 2, Last Report: 2015-01-24 21:33:23)
        put("||310260|MetroPCS", new ApnParameters("http://metropcs.mmsmvno.com/mms/wapenc", null, null));

        //Mid-Rivers Communications (Report Count: 1, Last Report: 2014-10-11 13:32:06)
        put("310900|Verizon|310120|Mid-Rivers Communications", new ApnParameters("http://mmsc.midrivers.csky.us:6672/", null, null));

        //MILEGO DNIA (Report Count: 1, Last Report: 2015-02-23 07:44:36)
        put("26003|MILEGO DNIA||", new ApnParameters("http://mms.plusgsm.pl:8002", "212.2.96.16", 8080));

        //MOBIFONE (Report Count: 1, Last Report: 2014-10-15 12:29:59)
        put("45201|null|45201|MOBIFONE", new ApnParameters("http://203.162.21.114/mmsc", "203.162.21.114", 8080));

        //Mobifone (Report Count: 7, Last Report: 2015-03-25 05:44:47)
        put("45201||45201|Mobifone", new ApnParameters("http://203.162.21.114/mmsc", "203.162.21.114", 3130));

        //Mobiho (Report Count: 1, Last Report: 2015-03-02 16:49:11)
        put("20820|Mobiho|20820|Mobiho", new ApnParameters("http://mms.orange.fr/", "192.168.10.200", 8080));

        //mobilcom-debitel (Report Count: 6, Last Report: 2015-03-31 08:12:53)
        put("26202|mobilcom-debitel|26202|mobilcom-debitel", new ApnParameters("http://139.7.24.1/servlets/mms", "139.7.29.17", 80));

        //mobilcom-debitel (Report Count: 2, Last Report: 2015-01-30 06:54:21)
        put("26203|mobilcom-debitel|26203|mobilcom-debitel", new ApnParameters("http://mms/eplus/", "212.23.97.153", 5080));

        //Mobile (Report Count: 16, Last Report: 2015-03-15 16:35:21)
        put("310260||310260|Mobile", new ApnParameters("http://wholesale.mmsmvno.com/mms/wapenc", null, null));

        //Mobile Norway (Report Count: 1, Last Report: 2014-10-25 13:23:09)
        put("24007|Tele2 NO|24205|Mobile Norway", new ApnParameters("http://mmsc.tele2.no", "193.12.40.14", 8080));

        //Mobile Norway (Report Count: 1, Last Report: 2014-12-31 10:24:40)
        put("24007|Tele2|24205|Mobile Norway", new ApnParameters("http://mmsc.tele2.no", "193.12.40.14", 8080));

        //Mobile Norway (Report Count: 1, Last Report: 2015-02-18 20:39:30)
        put("24205|One Call|24205|Mobile Norway", new ApnParameters("http://mms.nwn.no", "188.149.250.10", 80));

        //Mobile Telesystems (Report Count: 1, Last Report: 2015-01-19 15:37:37)
        put("25001|MTS|25001|Mobile Telesystems", new ApnParameters("http://mmsc", "192.168.192.192", 8080));

        //mobile-8 (Report Count: 2, Last Report: 2015-02-15 00:24:53)
        put("51028|mobile-8|51009|mobile-8", new ApnParameters("http://10.17.93.103:8080", "10.17.27.250", 8080));

        //mobilenation (Report Count: 1, Last Report: 2015-03-13 17:49:58)
        put("310000|mobilenation|31000|", new ApnParameters("http://mms.mymn3g.net", "mms.mymn3g.net", 8081));

        //MOBILICITY (Report Count: 1, Last Report: 2014-09-12 02:33:46)
        put("302320||302320|MOBILICITY", new ApnParameters("hhtp://mms.mobilicity.net", "10.100.3.4", 8080));

        //Mobilicity (Report Count: 36, Last Report: 2015-03-24 23:52:31)
        put("302320||302320|Mobilicity", new ApnParameters("http://mms.mobilicity.net", "10.100.3.4", 8080));

        //Mobilink (Report Count: 11, Last Report: 2015-04-06 20:04:33)
        put("41001|Mobilink|41001|Mobilink", new ApnParameters("http://mms/", "172.25.20.12", 8080));

        //mobilR (Report Count: 1, Last Report: 2014-10-20 13:23:20)
        put("21406|mobilR|21401|mobilR", new ApnParameters("http://mms.mundo-r.com", "10.0.157.169", 8080));

        //mobily (Report Count: 3, Last Report: 2015-02-19 12:52:37)
        put("42003|mobily|42003|mobily", new ApnParameters("http://10.3.3.133:9090/was", "10.3.2.133", 8080));

        //Mobily-KSA (Report Count: 1, Last Report: 2015-02-07 12:20:35)
        put("42003|mobily|42003|Mobily-KSA", new ApnParameters("http://10.3.3.133:9090/was", "10.3.2.133", 8080));

        //mobipcs (Report Count: 1, Last Report: 2015-04-06 20:38:04)
        put("310000|mobipcs|310000|", new ApnParameters("http://mms.mobipcs.com", null, null));

        //Mobistar (Report Count: 24, Last Report: 2015-04-03 22:42:33)
        put("20610||20610|Mobistar", new ApnParameters("http://mmsc.mobistar.be", "212.65.63.143", 8080));

        //MOBITEL (Report Count: 2, Last Report: 2015-02-25 16:57:28)
        put("29341|IPKO|29341|MOBITEL", new ApnParameters("http://mms.mobitel.si/servlets/mms", "213.229.249.40", 8080));

        //MOBITEL (Report Count: 32, Last Report: 2015-03-13 14:28:45)
        put("29341|Mobitel|29341|MOBITEL", new ApnParameters("http://mms.mobitel.si/servlets/mms", "213.229.249.40", 8080));

        //MOBITEL (Report Count: 1, Last Report: 2015-02-21 16:15:21)
        put("29341||29341|MOBITEL", new ApnParameters("http://mms.mobitel.si/servlets/mms", "213.229.249.40", 8080));

        //Mobitel (Report Count: 2, Last Report: 2015-02-02 18:08:21)
        put("41301|Mobitel |41301|Mobitel", new ApnParameters("http://192.168.50.165", "192.168.50.163", 8080));

        //Mobitel (Report Count: 7, Last Report: 2015-03-22 06:24:50)
        put("41301|Mobitel|41301|Mobitel", new ApnParameters("http://192.168.50.165", "192.168.50.163", 8080));

        //MOLDCELL (Report Count: 4, Last Report: 2015-03-04 20:57:05)
        put("25902|MOLDCELL|25902|MOLDCELL", new ApnParameters("http://mms", null, null));

        //MOR IAM (Report Count: 1, Last Report: 2015-01-12 00:07:44)
        put("60401|Maroc Telecom|60401|MOR IAM", new ApnParameters("http://mms:8002/", "10.16.35.50", 8080));

        //MOR MEDITEL (Report Count: 1, Last Report: 2015-03-09 10:27:15)
        put("60400|Meditel|60400|MOR MEDITEL", new ApnParameters("http://mms.meditel.ma:8088/mms", "10.8.8.9", 8080));

        //Motorola (Report Count: 2, Last Report: 2015-03-23 03:32:16)
        put("310000|Chameleon|310000|Motorola", new ApnParameters("http://mms.sprintpcs.com", "68.28.31.7", 80));

        //Motorola (Report Count: 2, Last Report: 2015-03-01 19:17:04)
        put("312530|Sprint|312530|Motorola", new ApnParameters("http://mmsc.vmobl.com:8088", "68.28.31.2", 80));

        //MOVILNET (Report Count: 2, Last Report: 2015-03-22 21:14:09)
        put("73406|MOVILNET|73406|MOVILNET", new ApnParameters("http://mms2.movilnet.com.ve/servlets/mms", "192.168.16.12", 8080));

        //movistar (Report Count: 1, Last Report: 2015-02-14 08:01:57)
        put("21407|movistar||", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", "216.155.165.50", 8080));

        //Movistar (Report Count: 20, Last Report: 2015-04-01 16:24:19)
        put("21407||21407|Movistar", new ApnParameters("http://mms.movistar.com", "10.138.255.5", 8080));

        //Movistar (Report Count: 1, Last Report: 2014-09-08 18:46:48)
        put("26201|Telekom.de|21407|Movistar", new ApnParameters("http://mms.t-mobile.de/servlets/mms", "172.28.23.131", 8008));

        //Movistar (Report Count: 1, Last Report: 2015-02-21 12:44:22)
        put("26207||21407|Movistar", new ApnParameters("http://10.81.0.7:8002", "82.113.100.5", 8080));

        //movistar (Report Count: 3, Last Report: 2015-01-28 17:31:42)
        put("310260||33403|movistar", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //Movistar (Report Count: 1, Last Report: 2015-01-07 03:21:39)
        put("310260||71204|Movistar", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //Movistar (Report Count: 1, Last Report: 2014-12-25 21:47:58)
        put("310260||72207|Movistar", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //movistar (Report Count: 8, Last Report: 2015-03-12 17:35:47)
        put("334030|Movistar|33403|movistar", new ApnParameters("http://mms.movistar.mx", "10.2.20.1", 80));

        //Movistar (Report Count: 2, Last Report: 2015-01-24 01:45:11)
        put("71204|movistar|71204|Movistar", new ApnParameters("http://mms.movistar.cr", "10.221.79.83", 80));

        //movistar (Report Count: 2, Last Report: 2015-03-23 21:26:36)
        put("71606|movistar|71606|movistar", new ApnParameters("http://mmsc.telefonicamovistar.com.pe:8088/mms/", "200.4.196.118", 8080));

        //Movistar (Report Count: 11, Last Report: 2015-04-01 16:55:36)
        put("72207|Movistar|72207|Movistar", new ApnParameters("http://mms.movistar.com.ar", "200.68.32.239", 8080));

        //movistar (Report Count: 2, Last Report: 2014-11-05 21:25:56)
        put("73002|movistar|73002|movistar", new ApnParameters("http://mms.movistar.cl", "172.17.8.10", 8080));

        //movistar (Report Count: 5, Last Report: 2015-02-12 02:26:58)
        put("73404|movistar|73404|movistar", new ApnParameters("http://mms.movistar.com.ve:8088/mms", "200.35.64.73", 9001));

        //Movistar (Report Count: 1, Last Report: 2014-11-07 14:25:44)
        put("74000|Movistar|74000|Movistar", new ApnParameters("http://mms.movistar.com.ec:8088/mms/", "10.3.5.50", 9001));

        //movistar (Report Count: 1, Last Report: 2015-02-18 20:42:09)
        put("74807|movistar|74807|movistar", new ApnParameters("http://mmsc.movistar.com.uy", "10.0.2.29", 8080));

        //Movistar España (Report Count: 1, Last Report: 2015-03-12 19:14:27)
        put("21407|movistar|21407|Movistar España", new ApnParameters("http://mms.movistar.com", "10.138.255.5", 8080));

        //mt:s (Report Count: 13, Last Report: 2015-02-21 12:32:28)
        put("22003|mt:s|22003|mt:s", new ApnParameters("http://mms.mts064.telekom.rs/mms/wapenc", "172.17.85.131", 8080));

        //MTC (Report Count: 1, Last Report: 2015-01-25 09:43:19)
        put("64901|MTC NAM|64901|MTC", new ApnParameters("http://www.mtcmobile.com.na/", "10.40.10.252", 80));

        //MTC NAMIBIA (Report Count: 5, Last Report: 2015-03-01 18:42:39)
        put("64901|MTC NAM|64901|MTC NAMIBIA", new ApnParameters("http://www.mtcmobile.com.na/", "10.40.10.252", 80));

        //Mtel (Report Count: 1, Last Report: 2015-01-02 15:50:36)
        put("28401|M-TEL GSM BG|28401|Mtel", new ApnParameters("http://mmsc/", "10.150.0.33", 8080));

        //Mtel (Report Count: 3, Last Report: 2015-03-28 10:36:55)
        put("28401|Mtel|28401|Mtel", new ApnParameters("http://mmsc/", "10.150.0.33", 8080));

        //MTN (Report Count: 1, Last Report: 2015-04-02 20:34:26)
        put("65510||65510|MTN", new ApnParameters("http://mms.mtn.co.za/mms/wapenc", "196.11.240.241", 8080));

        //MTN - NG (Report Count: 4, Last Report: 2015-04-05 09:57:04)
        put("62130||62130|MTN - NG", new ApnParameters("http://10.199.212.8/servlets/mms", "10.199.212.2", 8080));

        //MTN Group (Report Count: 1, Last Report: 2015-02-20 09:12:25)
        put("65510||65510|MTN Group", new ApnParameters("http://mms.mtn.co.za/mms/wapenc", "196.11.240.241", 8080));

        //MTN Irancell (Report Count: 1, Last Report: 2015-02-13 22:59:10)
        put("43235|Irancell|43235|MTN Irancell", new ApnParameters("http://mms:8002", "10.131.26.138", 8080));

        //MTN Networks(Dialog) (Report Count: 1, Last Report: 2014-11-10 04:54:39)
        put("41302|Dialog|41302|MTN Networks(Dialog) ", new ApnParameters("http://mms.dialog.lk:3130/mmsc", null, null));

        //MTN NG (Report Count: 3, Last Report: 2015-04-01 11:24:49)
        put("62130||62130|MTN NG", new ApnParameters("http://10.199.212.8/servlets/mms", "10.199.212.2", 8080));

        //MTN Nigeria (Report Count: 1, Last Report: 2014-12-02 05:56:04)
        put("62130||62130|MTN Nigeria", new ApnParameters("http://10.199.212.8/servlets/mms", "10.199.212.2", 8080));

        //MTN SA (Report Count: 8, Last Report: 2015-03-27 11:13:01)
        put("65510||65510|MTN SA", new ApnParameters("http://mms.mtn.co.za/mms/wapenc", "196.11.240.241", 8080));

        //MTN SA,Vodacom SA (Report Count: 1, Last Report: 2014-11-15 08:27:08)
        put("65510|,|65510|MTN SA,Vodacom SA", new ApnParameters("http://mms.mtn.co.za/mms/wapenc", "196.11.240.241", 8080));

        //MTN-NG (Report Count: 2, Last Report: 2015-04-02 17:44:26)
        put("62130||62130|MTN-NG", new ApnParameters("http://10.199.212.8/servlets/mms", "10.199.212.2", 8080));

        //MTN-SA (Report Count: 1, Last Report: 2015-01-28 09:04:37)
        put("65510| Cell C|65510|MTN-SA", new ApnParameters("http://mms.mtn.co.za/mms/wapenc", "196.11.240.241", 8080));

        //MTN-SA (Report Count: 71, Last Report: 2015-04-07 07:23:53)
        put("65510||65510|MTN-SA", new ApnParameters("http://mms.mtn.co.za/mms/wapenc", "196.11.240.241", 8080));

        //MTS (Report Count: 2, Last Report: 2015-02-01 16:43:53)
        put("22003|mt:s|22003|MTS", new ApnParameters("http://mms.mts064.telekom.rs/mms/wapenc", "172.17.85.131", 8080));

        //MTS (Report Count: 29, Last Report: 2015-04-07 07:11:43)
        put("25001|MTS RUS|25001|MTS", new ApnParameters("http://mmsc", "192.168.192.192", 8080));

        //MTS (Report Count: 2, Last Report: 2015-02-22 07:13:42)
        put("25001|MTS-RUS|25001|MTS", new ApnParameters("http://mmsc", "192.168.192.192", 8080));

        //MTS (Report Count: 2, Last Report: 2014-12-24 16:31:52)
        put("25501|MTS UKR|25501|MTS", new ApnParameters("http://mms.kyivstar.net", "10.10.10.10", 8080));

        //MTS (Report Count: 1, Last Report: 2015-03-03 05:38:27)
        put("25501|UMC|25501|MTS", new ApnParameters("http://mms/", "192.168.10.10", 8080));

        //MTS (Report Count: 5, Last Report: 2015-02-25 17:06:20)
        put("302370|MTS|302720|MTS", new ApnParameters("http://mmsc2.mts.net/", "209.4.229.90", 9401));

        //MTS (Report Count: 11, Last Report: 2015-03-08 07:54:02)
        put("302660|MTS|302720|MTS", new ApnParameters("http://mmsc2.mts.net/", "wapgw1.mts.net", 9201));

        //MTS ARM (Report Count: 1, Last Report: 2015-01-14 14:21:36)
        put("28305|MTS Armenia|28305|MTS ARM", new ApnParameters("http://mms.vivacell.am/mmsc", "83.217.226.72", 8080));

        //MTS BY (Report Count: 2, Last Report: 2015-03-23 15:55:16)
        put("25702|MTS.BY|25702|MTS BY", new ApnParameters("http://mmsc", "192.168.192.168", 8080));

        //MTS RUS (Report Count: 1, Last Report: 2014-11-04 14:36:38)
        put("25001|Beeline|25001|MTS RUS", new ApnParameters("http://mmsc", "192.168.192.192", 8080));

        //MTS RUS (Report Count: 52, Last Report: 2015-04-07 13:38:44)
        put("25001|MTS RUS|25001|MTS RUS", new ApnParameters("http://mmsc", "192.168.192.192", 8080));

        //MTS RUS (Report Count: 1, Last Report: 2015-01-27 17:09:04)
        put("25001|MTS-RUS|25001|MTS RUS", new ApnParameters("http://mmsc", "192.168.192.192", 8080));

        //MTS RUS (Report Count: 5, Last Report: 2015-02-21 11:21:14)
        put("25001||25001|MTS RUS", new ApnParameters("http://mmsc", "192.168.192.192", 8080));

        //MTS RUS@ (Report Count: 2, Last Report: 2015-01-12 08:34:43)
        put("25001|MTS RUS|25001|MTS RUS@", new ApnParameters("http://mmsc", "192.168.192.192", 8080));

        //MTS UKR (Report Count: 2, Last Report: 2015-04-03 18:58:17)
        put("25501|Jeans|25501|MTS UKR", new ApnParameters("http://mmsc:8002", "192.168.10.10", 8080));

        //MTS UKR (Report Count: 6, Last Report: 2015-04-08 12:45:58)
        put("25501|MTS UKR|25501|MTS UKR", new ApnParameters("http://mmsc:8002/", "192.168.10.10", 8080));

        //MTS UKR (Report Count: 1, Last Report: 2014-12-03 17:18:19)
        put("25501|UMC|25501|MTS UKR", new ApnParameters("http://mmsc:8002/", "192.168.10.10", 8080));

        //MTS UKR (Report Count: 1, Last Report: 2015-03-11 11:17:21)
        put("25501||25501|MTS UKR", new ApnParameters("http://mmsc:8002/", "192.168.10.10", 8080));

        //MTS-RUS (Report Count: 1, Last Report: 2014-10-11 11:55:33)
        put("25001|Kuban-GSM|25001|MTS-RUS", new ApnParameters("http://mmsc", "192.168.192.192", 9201));

        //MTS-RUS (Report Count: 15, Last Report: 2015-03-07 15:06:00)
        put("25001|MTS RUS|25001|MTS-RUS", new ApnParameters("http://mmsc", "192.168.192.192", 8080));

        //MTS-RUS (Report Count: 1, Last Report: 2015-01-28 10:37:30)
        put("25001|MTS-RUS|25001|MTS-RUS", new ApnParameters("http://mmsc", "10.10.30.60", 8080));

        //MTS-RUS (Report Count: 1, Last Report: 2014-12-31 17:08:52)
        put("25001||25001|MTS-RUS", new ApnParameters("http://mmsc", "192.168.192.192", 8080));

        //MTSUA (Report Count: 3, Last Report: 2014-12-11 09:27:22)
        put("25501|UMC|25501|MTSUA", new ApnParameters("http://mms.life.com.ua/cmmsc/post", "212.58.162.230", 8080));

        //MTV Mobile (Report Count: 1, Last Report: 2014-12-20 18:02:16)
        put("26203|MTV Mobile|26203|MTV Mobile", new ApnParameters("http://mms/eplus/", "212.23.97.153", 5080));

        //My Black Wireless (Report Count: 3, Last Report: 2014-10-09 16:32:18)
        put("310410||310410|My Black Wireless", new ApnParameters("http://mmsc.cingular.com", "66.209.11.33", 80));

        //MY CELCOM (Report Count: 5, Last Report: 2015-03-23 03:00:09)
        put("50219|ALTEL|50219|MY CELCOM", new ApnParameters("http://mms.celcom.net.my", "10.128.1.242", 8080));

        //MY CELCOM (Report Count: 1, Last Report: 2015-03-14 00:56:01)
        put("50219|Celcom|50219|MY CELCOM", new ApnParameters("http://mms.celcom.net.my", "10.128.1.242", 8080));

        //MY CELCOM (Report Count: 26, Last Report: 2015-03-22 14:32:58)
        put("50219|MERCHANTRADE|50219|MY CELCOM", new ApnParameters("http://mms.celcom.net.my", "10.128.1.242", 8080));

        //MY CELCOM (Report Count: 1, Last Report: 2015-02-18 00:35:10)
        put("50219|REDtone|50219|MY CELCOM", new ApnParameters("http://mms.celcom.net.my", "10.128.1.242", 8080));

        //MY CELCOM (Report Count: 13, Last Report: 2015-04-01 01:10:11)
        put("50219|TuneTalk|50219|MY CELCOM", new ApnParameters("http://mms.celcom.net.my", "10.128.1.242", 8080));

        //MY CELCOM (Report Count: 1, Last Report: 2015-01-06 06:00:18)
        put("50219|redONE|50219|MY CELCOM", new ApnParameters("http://mms.celcom.net.my", "10.128.1.242", 8080));

        //MY CELCOM (Report Count: 1, Last Report: 2014-10-14 13:31:35)
        put("50219||50216|MY CELCOM", new ApnParameters("http://mms.celcom.net.my", "10.128.1.242", 8080));

        //MY CELCOM (Report Count: 208, Last Report: 2015-04-08 15:43:00)
        put("50219||50219|MY CELCOM", new ApnParameters("http://mms.celcom.net.my", "10.128.1.242", 8080));

        //MY CELCOM,null (Report Count: 1, Last Report: 2015-02-05 04:44:43)
        put("50219||50219|MY CELCOM,null", new ApnParameters("http://mms.celcom.net.my", "10.128.1.242", 8080));

        //MY DiGi (Report Count: 7, Last Report: 2015-04-07 17:59:57)
        put("50216|DiGi|50216|MY DiGi", new ApnParameters("http://mms.digi.com.my/servlets/mms", "203.92.128.160", 80));

        //MY MAXIS (Report Count: 2, Last Report: 2015-02-08 18:54:25)
        put("50212|Maxis|50212|MY MAXIS", new ApnParameters("http://172.16.74.100:10021/mmsc", "202.75.133.49", 80));

        //MY MAXIS (Report Count: 1, Last Report: 2014-11-17 23:35:49)
        put("50212|null|50212|MY MAXIS", new ApnParameters("http://172.16.74.100:10021/mmsc", "202.75.133.49", 80));

        //MY MAXIS (Report Count: 216, Last Report: 2015-04-08 04:06:12)
        put("50212||50212|MY MAXIS", new ApnParameters("http://172.16.74.100:10021/mmsc", "202.75.133.49", 80));

        //MY MAXIS (Report Count: 1, Last Report: 2014-12-01 08:07:34)
        put("50216|DiGi|50216|MY MAXIS", new ApnParameters("http://mms.digi.com.my/servlets/mms", "203.92.128.160", 80));

        //MY MAXIS (Report Count: 1, Last Report: 2014-12-17 01:58:43)
        put("50219|TuneTalk|50219|MY MAXIS", new ApnParameters("http://mms.celcom.net.my", "10.128.1.242", 8080));

        //MY MAXIS (Report Count: 1, Last Report: 2015-02-22 17:53:46)
        put("50219||50219|MY MAXIS", new ApnParameters("http://mms.celcom.net.my", "10.128.1.242", 8080));

        //My Network (Report Count: 63, Last Report: 2015-04-07 15:56:51)
        put("310410||310410|My Network", new ApnParameters("http://mmsc.mobile.att.net", "proxy.mobile.att.net", 80));

        //MY XOX (Report Count: 3, Last Report: 2015-02-13 01:14:15)
        put("50219|MY XOX|50219|MY XOX", new ApnParameters("http://mms.celcom.net.my", "10.128.1.242", 8080));

        //N Telenor (Report Count: 7, Last Report: 2015-02-28 09:29:02)
        put("24201|TELENOR|24201|N Telenor", new ApnParameters("http://mmsc", "10.10.10.11", 8080));

        //N Telenor (Report Count: 5, Last Report: 2015-03-02 16:27:35)
        put("24201|Talkmore|24201|N Telenor", new ApnParameters("http://mmsc/", "10.10.10.11", 8080));

        //N Telenor (Report Count: 4, Last Report: 2015-03-06 23:11:10)
        put("24201|Telenor|24201|N Telenor", new ApnParameters("http://mmsc/", "10.10.10.11", 8080));

        //N Telenor (Report Count: 1, Last Report: 2015-01-26 23:23:14)
        put("24201|djuice|24201|N Telenor", new ApnParameters("http://mmsc", "mms-proxy.telenor.no", 8080));

        //N Telenor (Report Count: 1, Last Report: 2015-01-14 18:51:37)
        put("24205|MyCall|24201|N Telenor", new ApnParameters("http://mms.nwn.no", "188.149.250.10", 80));

        //N Telenor (Report Count: 3, Last Report: 2015-02-04 22:10:29)
        put("24205|One Call|24201|N Telenor", new ApnParameters("http://mms.nwn.no", "188.149.250.10", 80));

        //Naked Mobile (Report Count: 3, Last Report: 2015-01-02 08:39:43)
        put("310320|Naked Mobile|310320|Naked Mobile", new ApnParameters("http://mmsc.c1neaz.csky.us:6672", "209.4.229.94", 9401));

        //Nar (Report Count: 1, Last Report: 2015-02-15 18:51:49)
        put("40004|Nar|40004|Nar", new ApnParameters("http://mmsc", "10.20.0.40", 9401));

        //Natcom (Report Count: 1, Last Report: 2015-01-23 00:05:16)
        put("37203|NATCOM|37203|Natcom", new ApnParameters("http://natcom.mmsc.com", "192.168.3.134", 8000));

        //nawras (Report Count: 1, Last Report: 2014-10-24 14:41:19)
        put("42203|nawras|42203|nawras", new ApnParameters("http://10.128.240.16/servlets/mms", null, null));

        //NCC (Report Count: 1, Last Report: 2014-11-30 17:23:56)
        put("25003|Rostelecom|25003|NCC", new ApnParameters("http://10.0.3.50", "10.0.3.20", 8080));

        //Ncell (Report Count: 1, Last Report: 2014-11-21 16:29:00)
        put("42902|NCELL|42902|Ncell", new ApnParameters("http://192.168.19.15", "192.168.19.15", 8080));

        //NECCI (Report Count: 2, Last Report: 2014-12-24 13:48:47)
        put("310450||310450|NECCI", new ApnParameters("http://mms.viaero.com", "10.168.3.23", 9401));

        //NEP (Report Count: 1, Last Report: 2015-02-17 02:37:52)
        put("310260||310290|NEP", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //NEP Wireless (Report Count: 1, Last Report: 2015-02-13 20:19:50)
        put("310290||310290|NEP Wireless", new ApnParameters("http://mmsgprs.com", null, null));

        //NetCom (Report Count: 1, Last Report: 2014-11-18 18:25:26)
        put("24007|Tele2 NO|24202|NetCom", new ApnParameters("http://mmsc.tele2.no/", "193.12.40.14", 8080));

        //NetCom (Report Count: 4, Last Report: 2014-12-23 15:59:53)
        put("24202|Chess|24202|NetCom", new ApnParameters("http://mm/", "212.169.66.4", 8080));

        //NetCom (Report Count: 4, Last Report: 2015-03-10 21:19:12)
        put("24202|NetCom|24202|NetCom", new ApnParameters("http://mm/", "212.169.66.4", 8080));

        //NetCom (Report Count: 1, Last Report: 2015-02-03 23:14:32)
        put("24205|One Call|24202|NetCom", new ApnParameters("http://mms.nwn.no", "188.149.250.10", 80));

        //Network Extender (Report Count: 1, Last Report: 2014-07-29 12:05:18)
        put("310002|Verizon Wireless|31000|Network Extender", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Network Extender (Report Count: 1, Last Report: 2015-01-02 00:52:13)
        put("311480|Verizon Wireless|00000|Network Extender", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Network Extender (Report Count: 2, Last Report: 2015-02-21 17:47:55)
        put("311480|Verizon Wireless|31000|Network Extender", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Network Extender (Report Count: 5, Last Report: 2015-02-09 00:30:46)
        put("311480|Verizon Wireless|311480|Network Extender", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Network Extender (Report Count: 5, Last Report: 2015-03-11 02:11:32)
        put("311480|Verizon|31000|Network Extender", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Network Extender (Report Count: 73, Last Report: 2015-04-04 20:15:51)
        put("311480|Verizon|311480|Network Extender", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Network Extender (Report Count: 1, Last Report: 2015-04-03 02:20:59)
        put("311480||00000|Network Extender", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //netzclub (Report Count: 3, Last Report: 2015-04-07 15:45:20)
        put("26207||26207|netzclub", new ApnParameters("http://10.81.0.7:8002", "82.113.100.5", 8080));

        //neuf (Report Count: 1, Last Report: 2015-03-21 12:04:44)
        put("20810|neuf|20810|neuf", new ApnParameters("http://mms1", "10.151.0.1", 8080));

        //Nex-Tech Wireless (Report Count: 1, Last Report: 2015-01-13 18:11:27)
        put("312420|Nex-Tech Wireless|310000|Nex-Tech Wireless", new ApnParameters("http://mms.ntwls.net/nex-tech/mms.php", null, null));

        //Nex-Tech Wireless (Report Count: 1, Last Report: 2014-09-19 15:01:47)
        put("312420|Nex-Tech Wireless|31000|Nex-Tech Wireless", new ApnParameters("http://mms.ntwls.net/nex-tech/mms.php", null, null));

        //Nextel (Report Count: 4, Last Report: 2015-03-05 07:01:21)
        put("334090|Nextel|334090|Nextel", new ApnParameters("http://3gmms.nexteldata.com.mx", "129.192.129.104", 8080));

        //Nextel (Report Count: 1, Last Report: 2014-09-09 22:51:57)
        put("72439|Nextel|72439|Nextel", new ApnParameters("http://3gmms.nextel3g.net.br", "129.192.129.104", 8080));

        //nju (Report Count: 1, Last Report: 2014-10-20 17:34:26)
        put("26003||26003|nju", new ApnParameters("http://mms.plusgsm.pl:8002", "212.2.96.16", 8080));

        //NL KPN (Report Count: 1, Last Report: 2015-01-16 14:41:09)
        put("20408|Hi|20408|NL KPN", new ApnParameters("http://mp.mobiel.kpn/mmsc", "10.10.100.20", 5080));

        //NL KPN (Report Count: 3, Last Report: 2015-03-11 17:59:05)
        put("20408|KPN|20408|NL KPN", new ApnParameters("http://mp.mobiel.kpn/mmsc", "10.10.100.20", 5080));

        //NL KPN (Report Count: 1, Last Report: 2015-02-27 11:28:32)
        put("20408|simyo|20408|NL KPN", new ApnParameters("http://mp.mobiel.kpn/mmsc", "10.10.100.20", 5080));

        //NL KPN (Report Count: 1, Last Report: 2014-12-17 09:51:18)
        put("20412|NL Telfort|20408|NL KPN", new ApnParameters("http://mms", "193.113.200.195", 8080));

        //Northeast Wireless (Report Count: 1, Last Report: 2015-02-19 19:59:30)
        put("302220||311710|Northeast Wireless", new ApnParameters("http://aliasredirect.net/proxy/koodo/mmsc", "74.49.0.18", 80));

        //NOS (Report Count: 3, Last Report: 2015-03-04 22:43:13)
        put("26803|NOS|26803|NOS", new ApnParameters("http://mmsc:10021/mmsc", "62.169.66.5", 8799));

        //NRJ Mobile (Report Count: 2, Last Report: 2015-01-01 21:34:10)
        put("20801|NRJ Mobile|20801|NRJ Mobile", new ApnParameters("http://mms.orange.fr", "192.168.10.200", 8080));

        //NRJ Mobile (Report Count: 5, Last Report: 2015-03-11 19:44:15)
        put("20810|NRJ Mobile|20810|NRJ Mobile", new ApnParameters("http://mmsnrj", "10.143.156.5", 8080));

        //NRJ Mobile (Report Count: 4, Last Report: 2014-10-02 18:04:19)
        put("20826|NRJ Mobile|20810|NRJ Mobile", new ApnParameters("http://mmsnrj", "10.143.156.5", 8080));

        //nTelos (Report Count: 6, Last Report: 2015-02-11 23:54:33)
        put("310470|nTelos|310000|nTelos", new ApnParameters("http://mms.ntelospcs.net", null, null));

        //nTelos (Report Count: 1, Last Report: 2015-01-30 14:34:52)
        put("310470|nTelos|310470|nTelos", new ApnParameters("http://mms.ntelospcs.net", null, null));

        //nTelos (Report Count: 1, Last Report: 2015-02-17 17:31:08)
        put("310470|nTelos|31047|nTelos", new ApnParameters("", null, null));

        //nTelos (Report Count: 2, Last Report: 2015-03-14 23:26:19)
        put("310470|nTelos|3307|nTelos", new ApnParameters("http://mms.ntelospcs.net", null, null));

        //ntelos (Report Count: 16, Last Report: 2015-04-06 15:09:53)
        put("310470|ntelos|310000|", new ApnParameters("http://mms.ntelospcs.net/", null, null));

        //NTT DOCOMO (Report Count: 1, Last Report: 2014-09-23 10:56:13)
        put("310260||44010|NTT DOCOMO", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //null (Report Count: 1, Last Report: 2014-12-10 03:31:11)
        put("44010||null|null", new ApnParameters("http://mms", "andmms.softbank.ne.jp", 8080));

        //null (Report Count: 1, Last Report: 2014-10-03 14:52:29)
        put("52015||null|null", new ApnParameters("http://mms.mobilelife.co.th", "203.170.229.34", 8080));

        //null,CELLULARONE (Report Count: 1, Last Report: 2014-11-12 15:43:06)
        put("311190|null|311190|null,CELLULARONE", new ApnParameters("null", null, null));

        //Numericable (Report Count: 15, Last Report: 2015-03-30 21:17:11)
        put("20810|Numericable|20810|Numericable", new ApnParameters("http://mms1", "10.151.0.1", 8080));

        //Numericable (Report Count: 1, Last Report: 2014-11-25 22:30:43)
        put("20810||20810|Numericable", new ApnParameters("http://mms1", "10.151.0.1", 8080));

        //O2 (Report Count: 1, Last Report: 2015-01-17 19:49:52)
        put("23410||23410|O2", new ApnParameters("http://mmsc.mms.o2.co.uk", "193.113.200.195", -1));

        //O2 (Germany) GmbH & Co. OHG (Report Count: 1, Last Report: 2014-12-07 20:23:57)
        put("26207|Willkommen|26207|O2 (Germany) GmbH & Co. OHG", new ApnParameters("http://10.81.0.7:8002", "82.113.100.5", 8080));

        //O2 (Germany) GmbH & Co. OHG,Vodafone.de (Report Count: 1, Last Report: 2015-03-29 03:48:55)
        put("26243|,Lycamobile|26202|O2 (Germany) GmbH & Co. OHG,Vodafone.de", new ApnParameters("http://10.81.0.7:8002", "82.113.100.5", 8080));

        //O2 - CZ (Report Count: 9, Last Report: 2015-01-16 07:32:47)
        put("23002|O2-CZ|23002|O2 - CZ ", new ApnParameters("http://mms.o2active.cz:8002", "160.218.160.218", 8080));

        //O2 - CZ (Report Count: 1, Last Report: 2015-02-23 08:22:22)
        put("23002||23002|O2 - CZ ", new ApnParameters("http://mms.o2active.cz:8002", "160.218.160.218", 8080));

        //o2 - de (Report Count: 2, Last Report: 2014-12-03 16:57:33)
        put("26207|FONIC|26207|o2 - de", new ApnParameters("http://10.81.0.7:8002", "82.113.100.5", 8080));

        //o2 - de (Report Count: 1, Last Report: 2015-01-11 14:29:17)
        put("26207|Talkline|26207|o2 - de", new ApnParameters("http://10.81.0.7:8002", "82.113.100.5", 8080));

        //o2 - de (Report Count: 1, Last Report: 2014-11-17 16:48:39)
        put("26207|Tchibo|26207|o2 - de", new ApnParameters("http://10.81.0.7:8002", "82.113.100.5", 8080));

        //o2 - de (Report Count: 1, Last Report: 2015-01-21 08:02:52)
        put("26207|Telco|26207|o2 - de", new ApnParameters("http://10.81.0.7:8002", "82.113.100.5", 8080));

        //o2 - de (Report Count: 1, Last Report: 2014-12-10 22:59:46)
        put("26207|Willkommen|26207|o2 - de", new ApnParameters("http://10.81.0.7:8002", "82.113.100.5", 8080));

        //o2 - de (Report Count: 1, Last Report: 2014-12-18 17:40:56)
        put("26207|disco|26207|o2 - de", new ApnParameters("http://10.81.0.7:8002", "82.113.100.5", 8080));

        //o2 - de (Report Count: 2, Last Report: 2015-01-22 12:20:00)
        put("26207|maXXim|26207|o2 - de", new ApnParameters("http://10.81.0.7:8002", "82.113.100.6", 8080));

        //o2 - de (Report Count: 1, Last Report: 2015-03-28 10:31:05)
        put("26207|mobilcom-debitel|26207|o2 - de", new ApnParameters("http://10.81.0.7:8002", "82.113.100.5", 8080));

        //o2 - de (Report Count: 57, Last Report: 2015-03-27 12:15:58)
        put("26207|o2 - de|26207|o2 - de", new ApnParameters("http://10.81.0.7:8002", "82.113.100.5", 8080));

        //o2 - de (Report Count: 6, Last Report: 2015-04-07 18:27:10)
        put("26207||26207|o2 - de", new ApnParameters("http://10.81.0.7:8002", "82.113.100.5", 8080));

        //O2 - IRL (Report Count: 2, Last Report: 2014-10-30 13:13:50)
        put("27202||27202|O2 - IRL", new ApnParameters("http://mmsc.mms.o2.ie:8002", "62.40.32.40", 8080));

        //O2 - UK (Report Count: 37, Last Report: 2015-04-05 20:04:32)
        put("23410|TESCO|23410|O2 - UK", new ApnParameters("http://mmsc.mms.o2.co.uk:8002", "193.113.200.195", 8080));

        //O2 - UK (Report Count: 45, Last Report: 2015-04-04 14:43:01)
        put("23410|giffgaff|23410|O2 - UK", new ApnParameters("http://mmsc.mediamessaging.co.uk:8002", "193.113.200.195", 8080));

        //O2 - UK (Report Count: 183, Last Report: 2015-04-07 15:31:24)
        put("23410||23410|O2 - UK", new ApnParameters("http://mmsc.mms.o2.co.uk:8002", "82.132.254.1", 8080));

        //O2 -UK (Report Count: 3, Last Report: 2015-01-15 12:58:28)
        put("23410||23410|O2 -UK", new ApnParameters("http://mmsc.mms.o2.co.uk:8002", "193.113.200.195", 8080));

        //o2 IRL (Report Count: 4, Last Report: 2015-01-07 13:23:06)
        put("27202||27202|o2 IRL", new ApnParameters("http://mmsc.mms.o2.ie:8002", "62.40.32.40", 8080));

        //O2-CZ (Report Count: 1, Last Report: 2015-03-31 14:02:16)
        put("23002|O2-CZ|23002|O2-CZ", new ApnParameters("http://mms.o2active.cz:8002", "160.218.160.218", 8080));

        //O2-CZ (Report Count: 1, Last Report: 2015-01-24 18:26:17)
        put("23002|O2-CZ||", new ApnParameters("http://mms.o2active.cz:8002", "160.218.160.218", 8080));

        //O2-IRL (Report Count: 6, Last Report: 2015-03-16 14:21:29)
        put("27202||27202|O2-IRL", new ApnParameters("http://mmsc.mms.o2.ie:8002", "62.40.32.40", 8080));

        //O2-Signal-Box (Report Count: 1, Last Report: 2015-03-10 18:41:20)
        put("26207|o2 - de|26207|O2-Signal-Box", new ApnParameters("http://10.81.0.7:8002", "82.113.100.5", 8080));

        //Off Network (Report Count: 1, Last Report: 2015-02-16 19:33:49)
        put("310410||310020|Off Network", new ApnParameters("http://mmsc.mobile.att.net", "proxy.mobile.att.net", 80));

        //Off Network (Report Count: 1, Last Report: 2014-09-21 18:03:06)
        put("310410||311030|Off Network", new ApnParameters("http://mmsc.mobile.att.net", "proxy.mobile.att.net", 80));

        //Oi (Report Count: 1, Last Report: 2015-01-11 11:38:25)
        put("310260||72431|Oi", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //Oi (Report Count: 2, Last Report: 2015-02-16 16:07:23)
        put("72431|Oi|72431|Oi", new ApnParameters("http://200.222.42.204:8002", "192.168.10.50", 3128));

        //Oi (Report Count: 1, Last Report: 2015-02-23 18:13:33)
        put("72431||72431|Oi", new ApnParameters("http://200.222.42.204:8002/", "192.168.10.50", 3128));

        //Oi,TIM (Report Count: 1, Last Report: 2015-01-18 19:07:20)
        put("72431|Oi|72431|Oi,TIM", new ApnParameters("http://200.222.42.204:8002", "192.168.10.50", 3128));

        //OJSC VimpelCom (Report Count: 1, Last Report: 2014-12-16 10:10:47)
        put("25099|Beeline|25099|OJSC VimpelCom", new ApnParameters("http://mms", "192.168.94.23", 8080));

        //olleh (Report Count: 3, Last Report: 2015-03-27 01:40:27)
        put("45008|KTF|45008|olleh", new ApnParameters("http://mmsc.ktfwing.com:9082", null, null));

        //olleh (Report Count: 11, Last Report: 2015-03-16 14:03:35)
        put("45008|KT|45008|olleh", new ApnParameters("http://mmsc.ktfwing.com:9082", null, null));

        //olleh (Report Count: 1, Last Report: 2015-01-07 05:55:34)
        put("45008|Verizon|45008|olleh", new ApnParameters("http://mmsc.ktfwing.com:9082", null, null));

        //olleh (Report Count: 41, Last Report: 2015-04-03 05:21:01)
        put("45008||45008|olleh", new ApnParameters("http://mmsc.ktfwing.com:9082", null, null));

        //OMNITEL LT (Report Count: 2, Last Report: 2015-02-09 13:50:34)
        put("24601|Omnitel|24601|OMNITEL LT", new ApnParameters("http://mms.omnitel.net:8002/", "194.176.32.149", 8080));

        //OMNITEL LT (Report Count: 1, Last Report: 2014-10-15 17:41:17)
        put("24601|ezys|24601|OMNITEL LT", new ApnParameters("http://mms.omnitel.net:8002/", "194.176.32.149", 8080));

        //OMNITEL LT (Report Count: 1, Last Report: 2014-09-25 08:35:03)
        put("24601||24601|OMNITEL LT", new ApnParameters("http://mms.omnitel.net:8002/", "194.176.32.149", 8080));

        //ONE (Report Count: 1, Last Report: 2015-03-04 18:26:30)
        put("29402|ONE|29402|ONE", new ApnParameters("http://mm", "212.158.178.36", 8080));

        //One Call (Report Count: 1, Last Report: 2015-04-04 22:01:42)
        put("24205|One Call|24202|One Call", new ApnParameters("http://mms.nwn.no", "188.149.250.10", 80));

        //One Call (Report Count: 4, Last Report: 2014-12-20 09:43:11)
        put("24205|One Call|24205|One Call", new ApnParameters("http://mms.nwn.no", "188.149.250.10", 80));

        //one2free (Report Count: 3, Last Report: 2015-03-25 04:04:22)
        put("45400|one2free|45400|one2free", new ApnParameters("http://192.168.58.171:8002", "192.168.59.51", 8080));

        //ONLY (Report Count: 1, Last Report: 2015-03-26 06:01:16)
        put("64702|ONLY|64702|ONLY", new ApnParameters("http://mmsc.only.fr:9091", "mmsc.only.fr", 9091));

        //ONO (Report Count: 1, Last Report: 2015-03-17 16:01:56)
        put("21418|ONO|21407|ONO", new ApnParameters("http://mms.ono.com", "10.126.0.50", 8080));

        //Ooredoo (Report Count: 1, Last Report: 2014-07-31 05:01:17)
        put("41903|KT WATANIYA|41903|Ooredoo", new ApnParameters("http://action.wataniya.com", "194.126.53.64", 8080));

        //Ooredoo (Report Count: 1, Last Report: 2014-09-07 20:49:42)
        put("41903|Ooredoo|41903|Ooredoo", new ApnParameters("http://action.wataniya.com", "194.126.53.64", 8080));

        //Ooredoo (Report Count: 3, Last Report: 2015-03-06 00:43:50)
        put("42701|Ooredoo|42701|Ooredoo", new ApnParameters("http://mmsr.ooredoomms.qa", "10.23.8.3", 8080));

        //Ooredoo (Report Count: 1, Last Report: 2014-10-13 06:42:23)
        put("47202|Ooredoo|47202|Ooredoo", new ApnParameters("http://mms.ooredoo.mv", "172.24.10.20", 8080));

        //Ooredoo (Report Count: 1, Last Report: 2015-03-04 14:59:14)
        put("60303|Ooredoo|60303|Ooredoo", new ApnParameters("http://10.10.111.1", "192.168.52.3", 3128));

        //Ooredoo@ (Report Count: 1, Last Report: 2015-03-03 01:46:52)
        put("60303|ooredoo|60303|Ooredoo@", new ApnParameters("http://10.10.111.1", "192.168.52.3", 3128));

        //Open Mobile (Report Count: 7, Last Report: 2015-04-08 12:59:16)
        put("330000|Home Zone|31000|Open Mobile", new ApnParameters("http://mms.openmobilepr.com:1981", null, null));

        //Open Mobile (Report Count: 2, Last Report: 2015-04-08 12:07:31)
        put("330120|Open Mobile|31000|Open Mobile", new ApnParameters("http://mms.openmobilepr.com", null, null));

        //Open Mobile (Report Count: 14, Last Report: 2015-04-07 22:27:25)
        put("330120|Open Mobile|330120|Open Mobile", new ApnParameters("http://mms.openmobilepr.com", null, null));

        //Open Mobile (Report Count: 1, Last Report: 2015-03-16 13:31:26)
        put("330120|Verizon|330120|Open Mobile", new ApnParameters("null", null, null));

        //openmobile (Report Count: 8, Last Report: 2015-04-08 00:09:29)
        put("330000|openmobile|31000|", new ApnParameters("http://mms.openmobilepr.com:1981/", null, null));

        //Optus (Report Count: 6, Last Report: 2014-10-02 11:38:34)
        put("50502|TPG|50502|Optus", new ApnParameters("http://mmsc.optus.com.au:8002/", "61.88.190.10", 8070));

        //Optus (Report Count: 4, Last Report: 2015-01-01 04:26:15)
        put("50502|YES OPTUS|50502|Optus", new ApnParameters("http://mmsc.optus.com.au:8002/", "61.88.190.10", 8070));

        //Optus (Report Count: 1, Last Report: 2014-10-20 02:03:38)
        put("50502||50502|Optus", new ApnParameters("http://mmsc.optus.com.au:8002/", "61.88.190.10", 8070));

        //Orange (Report Count: 3, Last Report: 2015-03-11 20:46:27)
        put("20801|Orange F|20801|Orange", new ApnParameters("http://mms.orange.fr", "192.168.10.200", 8080));

        //Orange (Report Count: 3, Last Report: 2015-03-10 18:39:14)
        put("20815|Free|20801|Orange", new ApnParameters("http://mms.free.fr/", null, null));

        //Orange (Report Count: 4, Last Report: 2015-03-06 13:20:41)
        put("21403|Orange|21403|Orange", new ApnParameters("http://mms.orange.es", "172.22.188.25", 8080));

        //Orange (Report Count: 10, Last Report: 2015-04-04 07:19:47)
        put("22610|orange|22610|Orange", new ApnParameters("http://wap.mms.orange.ro:8002", "62.217.247.252", 8799));

        //Orange (Report Count: 1, Last Report: 2014-11-14 15:46:15)
        put("23430||23433|Orange", new ApnParameters("http://mmsc.t-mobile.co.uk:8002", "149.254.201.135", 8080));

        //Orange (Report Count: 2, Last Report: 2015-01-30 17:33:42)
        put("23433|EE|23433|Orange", new ApnParameters("http://mms/", "149.254.201.135", 8080));

        //Orange (Report Count: 1, Last Report: 2015-01-10 20:41:16)
        put("25901|Orange|25901|Orange", new ApnParameters("http://mms/mms", "192.168.127.125", 3128));

        //Orange (Report Count: 2, Last Report: 2015-01-29 11:35:24)
        put("26003|Orange POP|26003|Orange", new ApnParameters("http://mms.orange.pl", "192.168.6.104", 8080));

        //Orange (Report Count: 28, Last Report: 2015-02-06 17:59:27)
        put("26003|Orange|26003|Orange", new ApnParameters("http://mms.orange.pl", "192.168.6.104", 8080));

        //Orange (Report Count: 2, Last Report: 2015-03-02 10:42:21)
        put("26003|nju|26003|Orange", new ApnParameters("http://mms.orange.pl", "192.168.6.104", 8080));

        //Orange (Report Count: 1, Last Report: 2015-01-16 17:19:18)
        put("40420|Orange|40420|Orange", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //ORANGE (Report Count: 3, Last Report: 2014-11-14 09:09:50)
        put("42501|012 Mobile|42501|ORANGE", new ApnParameters("http://192.168.220.15/servlets/mms", null, null));

        //ORANGE (Report Count: 46, Last Report: 2015-04-01 09:08:40)
        put("42501|orange|42501|ORANGE", new ApnParameters("http://192.168.220.15/servlets/mms", null, null));

        //ORANGE (Report Count: 1, Last Report: 2015-01-01 08:33:14)
        put("61701|CELLPLUS |61701|ORANGE", new ApnParameters("http://10.2.1.20:8514", "10.2.1.20", 8080));

        //Orange (Report Count: 1, Last Report: 2015-01-22 15:09:59)
        put("61701|Orange|61701|Orange", new ApnParameters("http://10.2.1.20:8514", "10.2.1.20", 8080));

        //Orange (Report Count: 1, Last Report: 2014-12-21 17:53:07)
        put("20801|Orange|20801|Orange ", new ApnParameters("http://mms.orange.fr", "192.168.10.200", 8080));

        //Orange AT (Report Count: 2, Last Report: 2015-03-20 18:39:04)
        put("23205||23205|Orange AT", new ApnParameters("http://mmsc.orange.at/mms/wapenc", "194.24.128.118", 8080));

        //Orange CH (Report Count: 3, Last Report: 2015-03-17 21:35:30)
        put("22803|Orange CH|22803|Orange CH", new ApnParameters("http://192.168.151.3:8002", "192.168.151.2", 8080));

        //Orange CI (Report Count: 1, Last Report: 2014-10-24 13:48:58)
        put("61203|Orange|61203|Orange CI", new ApnParameters("http://172.20.6.1/servlets/mms", "172.20.4.33", 8080));

        //Orange F (Report Count: 1, Last Report: 2014-11-18 10:43:07)
        put("20801|,|20801|Orange F", new ApnParameters("http://mms.orange.fr/", "192.168.10.200", 8080));

        //Orange F (Report Count: 590, Last Report: 2015-04-08 05:31:11)
        put("20801|Orange F|20801|Orange F", new ApnParameters("http://mms.orange.fr", "192.168.10.200", 8080));

        //Orange F (Report Count: 1, Last Report: 2015-01-15 17:24:50)
        put("20801|Orange F|20888|Orange F", new ApnParameters("http://mms.orange.fr", "192.168.10.200", 8080));

        //Orange F (Report Count: 1, Last Report: 2015-03-03 20:22:40)
        put("20801|Orange|20801|Orange F", new ApnParameters("http://mms.orange.fr", "192.168.10.200", 8080));

        //Orange F (Report Count: 1, Last Report: 2015-02-12 08:25:44)
        put("20801||20801|Orange F", new ApnParameters("http://mms.orange.fr", "192.168.10.200", 8080));

        //Orange F (Report Count: 39, Last Report: 2015-04-05 09:49:24)
        put("20815|Free|20801|Orange F", new ApnParameters("http://mms.free.fr/", null, null));

        //Orange F 3G (Report Count: 1, Last Report: 2014-11-26 20:01:24)
        put("20815|Free|20801|Orange F 3G", new ApnParameters("http://mms.free.fr", null, null));

        //Orange F,null (Report Count: 1, Last Report: 2015-01-31 19:22:04)
        put("20801|Orange F|20801|Orange F,null", new ApnParameters("http://mms.orange.fr", "192.168.10.200", 8080));

        //ORANGE IL (Report Count: 10, Last Report: 2015-03-21 08:07:18)
        put("42501|orange|42501|ORANGE IL", new ApnParameters("http://192.168.220.15/servlets/mms", null, null));

        //Orange MD (Report Count: 1, Last Report: 2015-03-19 19:51:46)
        put("25901|Orange|25901|Orange MD", new ApnParameters("http://mms/mms", "192.168.127.125", 3128));

        //Orange re (Report Count: 4, Last Report: 2015-01-23 03:10:50)
        put("64700|Orange re|64700|Orange re", new ApnParameters("http://mms.orange.re/", "192.168.10.200", 8080));

        //Orange RO (Report Count: 2, Last Report: 2015-01-24 19:51:31)
        put("22610|orange|22610|Orange RO", new ApnParameters("http://wap.mms.orange.ro:8002", "62.217.247.252", 8799));

        //Orange T-Mobile (Report Count: 2, Last Report: 2015-01-24 20:09:08)
        put("23433|Orange|23430|Orange T-Mobile", new ApnParameters("http://mms/", "149.254.201.135", 8080));

        //Orange-IL (Report Count: 8, Last Report: 2015-03-10 14:25:19)
        put("42501|orange|42501|Orange-IL", new ApnParameters("http://192.168.220.15/servlets/mms", null, null));

        //Orange_Senegal (Report Count: 4, Last Report: 2015-02-26 11:24:49)
        put("60801|ORANGE SN|60801|Orange_Senegal", new ApnParameters("http://mmsalize/servlets/mms", "172.16.30.9", 8080));

        //Ortel D (Report Count: 1, Last Report: 2014-12-30 18:37:59)
        put("26203|Ortel D|26203|Ortel D", new ApnParameters("http://mms/eplus/", "212.23.97.153", 5080));

        //P NOS (Report Count: 1, Last Report: 2015-02-05 16:38:34)
        put("26803|NOS|26803|P NOS", new ApnParameters("http://mmsc:10021/mmsc", "62.169.66.5", 8799));

        //P OPTIMUS (Report Count: 7, Last Report: 2015-03-15 01:17:19)
        put("26803|NOS|26803|P OPTIMUS", new ApnParameters("http://mmsc:10021/mmsc", "62.169.66.5", 8799));

        //P OPTIMUS (Report Count: 4, Last Report: 2015-04-04 22:18:47)
        put("26803|WTF|26803|P OPTIMUS", new ApnParameters("http://mmsc:10021/mmsc", "62.169.66.5", 8799));

        //PC mobile (Report Count: 127, Last Report: 2015-04-07 01:25:30)
        put("302220||302220|PC mobile", new ApnParameters("http://aliasredirect.net/proxy/mb/mmsc", "74.49.0.18", 80));

        //PC mobile (Report Count: 10, Last Report: 2015-03-24 21:37:32)
        put("302610||302610|PC mobile", new ApnParameters("http://mms.bell.ca/mms/wapenc", null, null));

        //PCCW-HKT (Report Count: 1, Last Report: 2015-01-25 05:11:28)
        put("45419|PCCW-HKT|45419|PCCW-HKT", new ApnParameters("http://3gmms.pccwmobile.com:8080/was", "10.140.14.10", 8080));

        //Pelephone (Report Count: 43, Last Report: 2015-04-07 10:13:15)
        put("42503|Pelephone|42503|Pelephone", new ApnParameters("http://mmsu.pelephone.net.il", "10.170.252.104", 9093));

        //Pelephone (Report Count: 1, Last Report: 2015-03-04 13:01:40)
        put("42503|Rami Levy|42503|Pelephone", new ApnParameters("http://mmsu.pelephone.net.il", "10.170.252.104", 9093));

        //PENNY MOBIL (Report Count: 1, Last Report: 2014-11-13 18:38:27)
        put("26201|PENNY MOBIL|26201|PENNY MOBIL", new ApnParameters("http://10.81.0.7:8002/", "82.113.100.5", 8080));

        //Personal (Report Count: 1, Last Report: 2014-10-30 16:48:20)
        put("722341|Personal|72234|Personal", new ApnParameters("http://mms.personal.com", "172.25.7.31", 8080));

        //Personal (Report Count: 4, Last Report: 2015-03-21 13:05:01)
        put("72234|Personal|72234|Personal", new ApnParameters("http://mms.personal.com", "172.25.7.31", 8080));

        //PH Sun Cellular (Report Count: 2, Last Report: 2015-02-11 08:32:23)
        put("51505|SUN|51505|PH Sun Cellular", new ApnParameters("http://mmscenter.suncellular.com.ph", "202.138.159.78", 8080));

        //Pine Cellular (Report Count: 6, Last Report: 2015-02-06 03:59:35)
        put("311080||311080|Pine Cellular", new ApnParameters("http://69.8.34.146/mms/", "69.8.34.146", 9401));

        //Pioneer (Report Count: 2, Last Report: 2015-04-05 22:23:09)
        put("310000|Verizon|311480|Pioneer ", new ApnParameters("http://mms1.zsend.com", null, null));

        //PLATEAU (Report Count: 1, Last Report: 2015-03-03 03:28:43)
        put("310100|PLATEAU@|310100|PLATEAU", new ApnParameters("http://mms", "172.23.253.206", 8080));

        //PLATEAU (Report Count: 2, Last Report: 2014-12-17 03:13:28)
        put("310100||310100|PLATEAU", new ApnParameters("http://mms", "172.23.253.206", 8080));

        //Plateau Wireless (Report Count: 4, Last Report: 2015-04-02 21:06:19)
        put("310100||310100|Plateau Wireless", new ApnParameters("http://mms", "172.23.253.206", 8080));

        //PLAY (Report Count: 2, Last Report: 2014-11-01 17:02:16)
        put("26006|PLAY|26006|PLAY", new ApnParameters("http://10.10.28.164/mms/wapenc", "10.10.25.5", 8080));

        //Play (Report Count: 1, Last Report: 2015-01-24 13:08:43)
        put("26006|Red Bull MOBILE|26006|Play", new ApnParameters("http://10.10.28.164/mms/wapenc", "10.10.25.5", 8080));

        //PLAY (Report Count: 6, Last Report: 2015-03-23 12:39:57)
        put("26006||26006|PLAY", new ApnParameters("http://10.10.28.164/mms/wapenc", "10.10.25.5", 8080));

        //PLAY (Plus) (Report Count: 1, Last Report: 2015-01-28 09:00:40)
        put("26006||26001|PLAY (Plus)", new ApnParameters("http://10.10.28.164/mms/wapenc", "10.10.25.5", 8080));

        //PLAY (T-Mobile) (Report Count: 1, Last Report: 2015-04-05 11:54:40)
        put("26006||26002|PLAY (T-Mobile)", new ApnParameters("http://mmsc.play.pl/mms/wapenc", null, null));

        //PLAY(T-Mobile) (Report Count: 1, Last Report: 2015-01-01 18:03:40)
        put("26006||26002|PLAY(T-Mobile)", new ApnParameters("http://mmsc.play.pl/mms/wapenc", null, null));

        //Plus (Report Count: 1, Last Report: 2014-12-29 16:42:44)
        put("26001|Plus|26001|Plus", new ApnParameters("http://mms.plusgsm.pl:8002", "212.2.96.16", 8080));

        //Plus (Report Count: 56, Last Report: 2015-04-08 08:17:44)
        put("26001||26001|Plus", new ApnParameters("http://mms.plusgsm.pl:8002", "212.2.96.16", 8080));

        //Plus (Report Count: 1, Last Report: 2015-04-06 06:19:39)
        put("26017||26001|Plus", new ApnParameters("http://mmsc.play.pl/mms/wapenc", null, null));

        //Plus (Report Count: 1, Last Report: 2015-04-03 16:07:17)
        put("||26001|Plus", new ApnParameters("http://mms.plusgsm.pl:8002", "212.2.96.16", 8080));

        //Plus,null (Report Count: 1, Last Report: 2015-02-28 02:54:45)
        put("26001||26001,null|Plus,null", new ApnParameters("http://mms.plusgsm.pl:8002", "212.2.96.16", 8080));

        //POST (Report Count: 1, Last Report: 2015-02-02 08:42:23)
        put("27001||27001|POST", new ApnParameters("http://mmsc.pt.lu:80", "194.154.192.88", 8080));

        //Preferred System (Report Count: 1, Last Report: 2014-07-19 12:33:00)
        put("310005|Verizon Wireless|311480|Preferred System", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Preferred System (Report Count: 1, Last Report: 2014-12-07 01:39:56)
        put("311480|Verizon Wireless|311580|Preferred System", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //PrixTel (Report Count: 4, Last Report: 2015-04-07 16:38:14)
        put("20810|PrixTel|20810|PrixTel", new ApnParameters("http://mms1", "10.151.0.1", 8080));

        //PROXIMUS (Report Count: 3, Last Report: 2015-03-18 16:27:13)
        put("20601||20601|PROXIMUS", new ApnParameters("http://mmsc.proximus.be/mms", "10.55.14.75", 8080));

        //PTEL MOBILE (Report Count: 34, Last Report: 2015-04-06 04:16:07)
        put("310260||310260|PTEL MOBILE", new ApnParameters("http://wholesale.mmsmvno.com/mms/wapenc", null, null));

        //Public Mobile (Report Count: 1, Last Report: 2014-09-11 12:15:26)
        put("302220|TELUS@|302220|Public Mobile ", new ApnParameters("http://aliasredirect.net/proxy/mmsc", "74.49.0.18", 80));

        //Public Mobile (Report Count: 29, Last Report: 2015-04-06 01:44:26)
        put("302220||302220|Public Mobile ", new ApnParameters("http://aliasredirect.net/proxy/mb/mmsc", "74.49.0.18", 80));

        //Pure GSM (Report Count: 7, Last Report: 2015-02-20 19:53:41)
        put("310410||310410|Pure GSM", new ApnParameters("http://mmsc.cingular.com", "proxy.mvno.telrite.com", 80));

        //READY SIM (Report Count: 1, Last Report: 2015-02-21 05:02:51)
        put("310260||310260|READY SIM", new ApnParameters("http://wholesale.mmsmvno.com/mms/wapenc", null, null));

        //Red Bull MOBILE (Report Count: 1, Last Report: 2015-02-21 18:28:26)
        put("26006|Red Bull MOBILE|26006|Red Bull MOBILE", new ApnParameters("http://10.10.28.164/mms/wapenc", "10.10.25.5", 8080));

        //Red extendida (Report Count: 1, Last Report: 2014-10-19 03:02:31)
        put("311480|Verizon Wireless|31000|Red extendida", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //RED POCKET (Report Count: 2, Last Report: 2014-12-01 22:55:39)
        put("310260||310260|RED POCKET", new ApnParameters("", null, null));

        //Red Pocket (Report Count: 1, Last Report: 2014-05-30 03:41:38)
        put("310410|AT&T@|310410|Red Pocket", new ApnParameters("http://mmsc.cingular.com", "66.209.11.33", 80));

        //Red Pocket (Report Count: 4, Last Report: 2014-12-11 20:38:19)
        put("310410|Red Pocket|310410|Red Pocket", new ApnParameters("http://mmsc.cingular.com", "66.209.11.33", 80));

        //Red Pocket (Report Count: 9, Last Report: 2015-02-24 19:41:49)
        put("310410||310410|Red Pocket", new ApnParameters("http://mmsc.cingular.com", "66.209.11.33", 80));

        //redONE (Report Count: 8, Last Report: 2015-04-08 02:43:06)
        put("50219|redONE|50219|redONE", new ApnParameters("http://mms.celcom.net.my", "10.128.1.242", 8080));

        //REDtone (Report Count: 1, Last Report: 2015-01-19 09:42:02)
        put("50219|REDtone|50219|REDtone", new ApnParameters("http://mms.celcom.net.my", "10.128.1.242", 8080));

        //REGLO Mobile (Report Count: 3, Last Report: 2015-04-02 15:28:45)
        put("20810|REGLO Mobile|20810|REGLO Mobile", new ApnParameters("http://mms1", "10.151.0.1", 8080));

        //Rekanan (Report Count: 1, Last Report: 2014-10-19 13:30:11)
        put("45412|Rekanan|45412|Rekanan", new ApnParameters("http://mms.hk.chinamobile.com/mms", null, null));

        //Rekanan (Report Count: 1, Last Report: 2014-10-27 14:56:13)
        put("45412|Rekanan|45413|Rekanan", new ApnParameters("http://mms.hk.chinamobile.com/mms", null, null));

        //Reliance (Report Count: 2, Last Report: 2015-02-10 00:58:03)
        put("40467|Reliance|40467|Reliance", new ApnParameters("http://10.4.42.21:8002/", "10.4.42.15", 8080));

        //Reliance (Report Count: 1, Last Report: 2015-02-25 09:33:36)
        put("40505|Reliance|40505|Reliance", new ApnParameters("http://100.1.201.171:10021/mmsc", "100.1.201.172", 8799));

        //Reliance (Report Count: 2, Last Report: 2015-02-27 09:54:19)
        put("40506|Reliance|40506|Reliance", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Reliance (Report Count: 1, Last Report: 2014-12-12 13:09:49)
        put("40509|Reliance||", new ApnParameters("http://100.1.201.171:10021/mmsc", "100.1.201.172", 8799));

        //Reliance (Report Count: 1, Last Report: 2015-02-19 00:17:05)
        put("40520|Reliance|40520|Reliance", new ApnParameters("http://mmsc/", "10.124.26.94", 8799));

        //Reliance (Report Count: 1, Last Report: 2014-10-17 20:41:49)
        put("405803|AIRCEL|40510|Reliance", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Republic (Report Count: 12, Last Report: 2015-03-08 02:50:23)
        put("310000|Motorola|310000|Republic", new ApnParameters("http://127.0.0.1:18181", null, null));

        //Republic (Report Count: 5, Last Report: 2015-04-05 23:00:54)
        put("310000|Republic|310000|Republic", new ApnParameters("http://127.0.0.1:18181", null, null));

        //RO ORANGE (Report Count: 51, Last Report: 2015-04-05 20:48:46)
        put("22610|orange|22610|RO ORANGE", new ApnParameters("http://wap.mms.orange.ro:8002", "62.217.247.252", 8799));

        //RO Vodafone RO (Report Count: 8, Last Report: 2014-12-31 13:54:33)
        put("22601|Vodafone RO|22601|RO Vodafone RO", new ApnParameters("http://multimedia/servlets/mms", "193.230.161.231", 8080));

        //ROAM (Report Count: 1, Last Report: 2015-03-29 18:59:17)
        put("310410||310410|ROAM", new ApnParameters("http://mmsc.cingular.com", "wireless.cingular.com", 80));

        //Roam Mobility (Report Count: 12, Last Report: 2015-04-08 05:18:50)
        put("310260||310260|Roam Mobility", new ApnParameters("http://wholesale.mmsmvno.com/mms/wapenc", null, null));

        //Roaming (Report Count: 1, Last Report: 2014-07-27 02:42:13)
        put("310000|Verizon|311480|Roaming", new ApnParameters("http://mms.ekn.com", null, null));

        //Roaming (Report Count: 1, Last Report: 2015-01-04 19:12:18)
        put("311480|Verizon Wireless|31000|Roaming", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Roaming (Report Count: 3, Last Report: 2015-01-31 03:22:51)
        put("311480|Verizon|311480|Roaming", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Roaming (Report Count: 9, Last Report: 2015-03-21 13:33:41)
        put("311580|US Cellular|311580|Roaming", new ApnParameters("http://mmsc1.uscc.net/mmsc/MMS", null, null));

        //Roaming Indicator Off (Report Count: 1, Last Report: 2015-02-24 23:37:54)
        put("310000|Chameleon|31000|Roaming Indicator Off", new ApnParameters("http://mms.plspictures.com", "68.28.31.7", 80));

        //Roaming Indicator Off (Report Count: 174, Last Report: 2015-04-08 03:39:57)
        put("31000||123456|Roaming Indicator Off", new ApnParameters("http://mms.vzwreseller.com/", null, null));

        //Roaming Indicator Off (Report Count: 63, Last Report: 2015-04-08 13:02:13)
        put("31000||31000|Roaming Indicator Off", new ApnParameters("http://mms.vzwreseller.com/", null, null));

        //Roaming Indicator Off (Report Count: 1, Last Report: 2014-10-05 00:14:27)
        put("310120|Sprint|31000|Roaming Indicator Off", new ApnParameters("null", null, null));

        //Roaming Indicator Off (Report Count: 12, Last Report: 2015-03-03 16:08:10)
        put("310120|Sprint|310120|Roaming Indicator Off", new ApnParameters("http://mms.sprintpcs.com", "68.28.31.7", 80));

        //Roaming Indicator Off (Report Count: 1, Last Report: 2014-09-13 13:59:32)
        put("311480|Verizon Wireless|31000|Roaming Indicator Off", new ApnParameters("null", null, null));

        //Roaming Indicator Off (Report Count: 2, Last Report: 2014-12-17 00:33:06)
        put("311480|Verizon Wireless|311480|Roaming Indicator Off", new ApnParameters("null", null, null));

        //Roaming Indicator Off (Report Count: 1, Last Report: 2015-03-28 18:42:52)
        put("311480|Verizon|311480|Roaming Indicator Off", new ApnParameters("null", null, null));

        //Roaming Indicator Off (Report Count: 33, Last Report: 2015-04-06 18:41:00)
        put("311480||123456|Roaming Indicator Off", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Roaming Indicator Off (Report Count: 16, Last Report: 2015-04-08 12:13:03)
        put("311480||31000|Roaming Indicator Off", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Roaming Indicator Off (Report Count: 1, Last Report: 2014-12-13 21:49:09)
        put("311580|U.S. Cellular|31000|Roaming Indicator Off", new ApnParameters("http://mmsc1.uscc.net/mmsc/MMS", null, null));

        //Roaming Indicator Off (Report Count: 7, Last Report: 2015-03-14 21:06:22)
        put("311580|U.S. Cellular|311580|Roaming Indicator Off", new ApnParameters("http://mmsc1.uscc.net/mmsc/MMS", null, null));

        //Roaming Indicator Off (Report Count: 2, Last Report: 2014-12-27 01:17:52)
        put("311870|Boost Mobile|000000|Roaming Indicator Off", new ApnParameters("http://mm.myboostmobile.com", "68.28.31.7", 80));

        //Roaming Indicator Off (Report Count: 10, Last Report: 2015-03-24 19:18:08)
        put("311870|Boost Mobile|31000|Roaming Indicator Off", new ApnParameters("http://mm.myboostmobile.com", "68.28.31.7", 80));

        //Roaming Indicator Off (Report Count: 1, Last Report: 2015-03-03 00:13:22)
        put("|Sprint|310120|Roaming Indicator Off", new ApnParameters("http://mms.sprintpcs.com", "68.28.31.7", 80));

        //Roaming Indicator Off (Report Count: 1, Last Report: 2014-11-23 06:07:05)
        put("||000000|Roaming Indicator Off", new ApnParameters("http://mms.sprintpcs.com", "68.28.31.7", 80));

        //Roaming Indicator Off (Report Count: 2, Last Report: 2014-12-12 21:29:22)
        put("||31000|Roaming Indicator Off", new ApnParameters("http://mms.sprintpcs.com", "68.28.31.7", 80));

        //Roaming Indicator On (Report Count: 2, Last Report: 2015-02-27 20:40:19)
        put("310000||31000|Roaming Indicator On", new ApnParameters("http://mms.vzwreseller.com/servlets/mms", null, null));

        //Roaming Indicator On (Report Count: 1, Last Report: 2015-02-11 04:28:09)
        put("311580|US Cellular|31000|Roaming Indicator On", new ApnParameters("http://mmsc1.uscc.net/mmsc/MMS", null, null));

        //Robi (Report Count: 1, Last Report: 2015-01-08 14:52:36)
        put("47002|AKTEL|47002|Robi", new ApnParameters("http://mms.gpsurf.net/servlets/mms", "10.128.1.2", 8080));

        //Robi (Report Count: 3, Last Report: 2014-12-19 04:51:01)
        put("47002|Robi|47002|Robi", new ApnParameters("http://10.16.18.40:38090/was", "10.16.18.77", 9028));

        //ROGERS (Report Count: 5, Last Report: 2015-03-12 12:32:01)
        put("302370||302720|ROGERS", new ApnParameters("http://mms.fido.ca", "205.151.11.13", -1));

        //Rogers (Report Count: 4, Last Report: 2015-03-31 15:20:40)
        put("302370||302720|Rogers", new ApnParameters("http://mms.fido.ca", "205.151.11.13", 80));

        //ROGERS (Report Count: 2, Last Report: 2015-03-28 15:00:29)
        put("302500|Videotron|302720|ROGERS", new ApnParameters("http://media.videotron.com", null, null));

        //ROGERS (Report Count: 1, Last Report: 2015-03-08 23:33:37)
        put("302720|ROGERS@|302720|ROGERS", new ApnParameters("http://mms.gprs.rogers.com", "10.128.1.69", 80));

        //ROGERS (Report Count: 536, Last Report: 2015-04-08 14:22:43)
        put("302720|ROGERS|302720|ROGERS", new ApnParameters("http://mms.gprs.rogers.com", "mmsproxy.rogers.com", 80));

        //Rogers (Report Count: 2, Last Report: 2015-04-04 15:24:34)
        put("310260||302720|Rogers", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //Rogers Wireless (Report Count: 4, Last Report: 2015-02-10 00:01:36)
        put("302370||302720|Rogers Wireless", new ApnParameters("http://mms.fido.ca", "205.151.11.13", 80));

        //Rogers Wireless (Report Count: 5, Last Report: 2014-11-10 18:49:02)
        put("302720|ROGERS|302720|Rogers Wireless", new ApnParameters("http://mms.gprs.rogers.com", "10.128.1.69", 80));

        //Rogers Wireless (Report Count: 1, Last Report: 2014-11-05 02:35:09)
        put("302720|Tbaytel / Rogers|302720|Rogers Wireless", new ApnParameters("http://mms.gprs.rogers.com", "10.128.1.69", 80));

        //Rogers Wireless (Report Count: 2, Last Report: 2014-12-27 00:39:48)
        put("310260||302720|Rogers Wireless", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //Rogers Wireless 3G (Report Count: 1, Last Report: 2015-01-18 03:47:18)
        put("302720|ROGERS|302720|Rogers Wireless 3G", new ApnParameters("http://mms.gprs.rogers.com", "10.128.1.69", 80));

        //ROK Mobile (Report Count: 5, Last Report: 2015-03-30 17:32:00)
        put("310260||310260|ROK Mobile", new ApnParameters("http://wholesale.mmsmvno.com/mms/wapenc", null, null));

        //ROSTELECOM (Report Count: 1, Last Report: 2015-01-20 09:52:26)
        put("25003|NCC|25003|ROSTELECOM", new ApnParameters("http://mmsc", "192.168.192.192", 8080));

        //RSA CELL C (Report Count: 1, Last Report: 2015-02-23 18:10:52)
        put("65507| Cell C|65507|RSA CELL C", new ApnParameters("http://mms.cmobile.co.za", "196.31.116.250", 8080));

        //RSA CELL C (Report Count: 1, Last Report: 2015-02-17 06:31:54)
        put("65507|Cell C|65507|RSA CELL C", new ApnParameters("http://mms.cmobile.co.za", "196.31.116.250", 8080));

        //S COMVIQ (Report Count: 11, Last Report: 2015-03-09 12:00:40)
        put("24007|Comviq|24007|S COMVIQ", new ApnParameters("http://mmsc.tele2.se", "130.244.202.30", 8080));

        //S COMVIQ (Report Count: 11, Last Report: 2015-03-11 06:15:56)
        put("24007|Tele2|24007|S COMVIQ", new ApnParameters("http://mmsc.tele2.se", "130.244.202.30", 8080));

        //SA Al Jawal (Report Count: 2, Last Report: 2015-03-25 08:16:11)
        put("42001|STC|42001|SA Al Jawal", new ApnParameters("http://mms.net.sa:8002", "10.1.1.1", 8080));

        //Safaricom (Report Count: 39, Last Report: 2015-04-08 16:15:29)
        put("63902|Safaricom|63902|Safaricom", new ApnParameters("http://mms.gprs.safaricom.com", "172.22.2.38", 8080));

        //Samsung (Report Count: 67, Last Report: 2015-03-20 22:39:21)
        put("310000|Samsung|310000|Samsung", new ApnParameters("http://mm.myboostmobile.com", "68.28.31.7", 80));

        //Samsung (Report Count: 5, Last Report: 2015-02-12 17:22:23)
        put("310120|Sprint|000000|Samsung", new ApnParameters("http://mms.plspictures.com", "68.28.31.7", 80));

        //SASKTEL (Report Count: 1, Last Report: 2014-11-19 15:33:31)
        put("302370|MTS|302780|SASKTEL", new ApnParameters("http://mmsc2.mts.net/", "wapgw1.mts.net", 9201));

        //SaskTel (Report Count: 15, Last Report: 2015-03-29 06:50:54)
        put("302780||302780|SaskTel", new ApnParameters("http://mms.sasktel.com", "mig.sasktel.com", 80));

        //Saunalahti (Report Count: 1, Last Report: 2014-11-27 18:21:45)
        put("24405|Saunalahti|24405|Saunalahti", new ApnParameters("http://mms.elisa.fi", "213.161.41.57", 80));

        //Searching for Service (Report Count: 1, Last Report: 2015-03-07 03:42:35)
        put("310000|Verizon|310000|Searching for Service", new ApnParameters("null", null, null));

        //Searching for Service (Report Count: 3, Last Report: 2015-01-28 00:48:02)
        put("311480|Verizon|00000|Searching for Service", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Searching for Service (Report Count: 1, Last Report: 2015-03-28 03:59:37)
        put("311480||002127|Searching for Service", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Searching for Service (Report Count: 1, Last Report: 2015-03-12 03:56:06)
        put("311870||311870|Searching for Service", new ApnParameters("http://mm.myboostmobile.com", "68.28.31.7", 80));

        //SFR (Report Count: 1, Last Report: 2014-11-13 19:07:23)
        put("20810|Darty|20810|SFR", new ApnParameters("http://mms.bouyguestelecom.fr/mms/wapenc", "62.201.129.226", 8080));

        //SFR (Report Count: 4, Last Report: 2015-02-10 20:56:56)
        put("20810|Joe|20810|SFR", new ApnParameters("http://mms1", "10.151.0.1", 8080));

        //SFR (Report Count: 7, Last Report: 2015-02-25 11:19:26)
        put("20810|La Poste Mobile|20810|SFR", new ApnParameters("http://mms1", "10.151.0.1", 8080));

        //SFR (Report Count: 4, Last Report: 2015-02-15 14:35:37)
        put("20810|LeclercMobile|20810|SFR", new ApnParameters("http://mms66", "10.143.156.9", 8080));

        //SFR (Report Count: 1, Last Report: 2014-11-09 15:39:07)
        put("20810|Numericable|20810|SFR", new ApnParameters("http://mms1", "10.151.0.1", 8080));

        //SFR (Report Count: 1, Last Report: 2014-08-24 17:05:01)
        put("20810|PrixTel|20810|SFR", new ApnParameters("http://mms1", "10.151.0.1", 8080));

        //SFR (Report Count: 1, Last Report: 2015-02-23 15:08:41)
        put("20810|SFR|20810|SFR", new ApnParameters("http://mms1", "10.151.0.1", 8080));

        //SFR (Report Count: 119, Last Report: 2015-04-06 14:06:55)
        put("20810||20810|SFR", new ApnParameters("http://mms1", "10.151.0.1", 8080));

        //SFR (Report Count: 9, Last Report: 2015-04-04 19:15:08)
        put("20823|Virgin|20810|SFR", new ApnParameters("http://virginmms.fr", "10.6.10.1", 8080));

        //SFR (Contact) (Report Count: 1, Last Report: 2014-11-17 18:57:11)
        put("20801|Orange F|20813|SFR (Contact)", new ApnParameters("http://mms.orange.fr", "192.168.10.200", 8080));

        //SFR Femto (Report Count: 9, Last Report: 2015-03-29 18:32:54)
        put("20810||20811|SFR Femto", new ApnParameters("http://mms1", "10.151.0.1", 8080));

        //SFR Femto En (Report Count: 1, Last Report: 2014-11-04 20:59:54)
        put("20810||20810|SFR Femto En", new ApnParameters("http://mms1", "10.151.0.1", 8080));

        //SFR Femto Ent (Report Count: 3, Last Report: 2015-03-27 17:03:08)
        put("20810||20810|SFR Femto Ent", new ApnParameters("http://mms1", "10.151.0.1", 8080));

        //SFR Femto Ent,null (Report Count: 1, Last Report: 2015-01-01 13:15:15)
        put("20810||20810|SFR Femto Ent,null", new ApnParameters("http://mms1", "10.151.0.1", 8080));

        //SFR REUNION (Report Count: 2, Last Report: 2015-03-27 13:29:14)
        put("64710||64710|SFR REUNION", new ApnParameters("http://mms", "10.0.224.145", 8080));

        //SFR RU (Report Count: 3, Last Report: 2015-03-25 08:10:04)
        put("64710||64710|SFR RU", new ApnParameters("http://mms", "10.0.224.145", 8080));

        //SGP-M1 (Report Count: 7, Last Report: 2015-03-08 13:52:45)
        put("52503|M1-3GSM|52503|SGP-M1", new ApnParameters("http://mmsgw:8002", "172.16.14.10", 8080));

        //SGP-M1 (Report Count: 6, Last Report: 2015-02-18 02:01:51)
        put("52503||52503|SGP-M1", new ApnParameters("http://mmsgw:8002", "172.16.14.10", 8080));

        //Si.mobil (Report Count: 1, Last Report: 2014-07-08 00:08:15)
        put("29340|SIMOBIL|29340|Si.mobil", new ApnParameters("http://mmc/", "80.95.224.46", 9201));

        //Si.mobil (Report Count: 1, Last Report: 2015-02-15 19:21:59)
        put("29340|bob|29340|Si.mobil", new ApnParameters("http://mmc", "80.95.224.46", 9201));

        //Si.mobil (Report Count: 29, Last Report: 2015-03-29 10:09:14)
        put("29340||29340|Si.mobil", new ApnParameters("http://mmc/", "80.95.224.46", 9201));

        //Simple Mobile (Report Count: 22, Last Report: 2015-03-22 17:00:14)
        put("310260|Simple Mobile|310260|Simple Mobile", new ApnParameters("http://smpl.mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //Simple Mobile (Report Count: 5, Last Report: 2015-01-11 04:21:22)
        put("310260|Verizon|310260|Simple Mobile", new ApnParameters("http://smpl.mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //Simple Mobile (Report Count: 272, Last Report: 2015-04-07 23:43:44)
        put("310260||310260|Simple Mobile", new ApnParameters("http://smpl.mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //simyo (Report Count: 1, Last Report: 2014-11-28 20:49:05)
        put("20408|simyo|20408|simyo", new ApnParameters("http://mp.mobiel.kpn/mmsc", "10.10.100.20", 5080));

        //simyo (Report Count: 4, Last Report: 2015-01-17 07:13:45)
        put("26203|simyo|26203|simyo", new ApnParameters("http://mms/eplus/", "212.23.97.153", 5080));

        //SingTel (Report Count: 49, Last Report: 2015-04-06 08:39:26)
        put("52501|SingTel|52501|SingTel", new ApnParameters("http://mms.singtel.com:10021/mmsc", "165.21.42.84", 8080));

        //Skinny (Report Count: 1, Last Report: 2015-02-06 04:41:29)
        put("53005|Skinny|53005|Skinny", new ApnParameters("http://lsmmsc.xtra.co.nz", "210.55.11.73", 80));

        //SKTelecom (Report Count: 1, Last Report: 2015-01-16 02:14:49)
        put("45005|SKTelecom|45005|", new ApnParameters("http://omms.nate.com:9082/oma_mms", "lteoma.nate.com", 9093));

        //SKTelecom (Report Count: 29, Last Report: 2015-04-02 01:36:54)
        put("45005|SKTelecom|45005|SKTelecom", new ApnParameters("http://omms.nate.com:9082/oma_mms", "smart.nate.com", 9093));

        //SKTelecom (Report Count: 20, Last Report: 2015-03-26 13:14:30)
        put("45005||45005|SKTelecom", new ApnParameters("http://omms.nate.com:9082/oma_mms", "smart.nate.com", 9093));

        //smart (Report Count: 3, Last Report: 2015-02-04 13:36:39)
        put("45606|Smart|45606|smart", new ApnParameters("http://mmsc", "10.12.1.142", 8080));

        //SMART (Report Count: 12, Last Report: 2015-03-31 09:45:39)
        put("51503|SMART Buddy|51503|SMART", new ApnParameters("http://10.102.61.238:8002", "10.102.61.46", 8080));

        //Smart (Report Count: 1, Last Report: 2014-12-21 23:07:07)
        put("51503|SMART GOLD 3G|51503|Smart", new ApnParameters("http://10.102.61.238:8002", "10.102.61.46", 8080));

        //SMART (Report Count: 18, Last Report: 2015-04-03 01:52:57)
        put("51503|SMART Prepaid|51503|SMART", new ApnParameters("http://10.102.61.238:8002", "10.102.61.46", 8080));

        //SMART (Report Count: 8, Last Report: 2015-02-19 21:38:21)
        put("51503|Talk N Text|51503|SMART", new ApnParameters("http://10.102.61.238:8002", "10.102.61.46", 8080));

        //SMART (Report Count: 1, Last Report: 2014-11-08 13:16:22)
        put("51505|SUN|51503|SMART", new ApnParameters("http://mmscenter.suncellular.com.ph", "202.138.159.78", 8080));

        //SMART Buddy (Report Count: 3, Last Report: 2015-03-19 03:53:30)
        put("51503|SMART Buddy|51503|SMART Buddy", new ApnParameters("http://10.102.61.238:8002", "10.102.61.46", 8080));

        //SMART Prepaid (Report Count: 5, Last Report: 2015-03-27 00:31:58)
        put("51503|SMART Prepaid|51503|SMART Prepaid", new ApnParameters("http://10.102.61.238:8002", "10.102.61.46", 8080));

        //smartfren (Report Count: 3, Last Report: 2015-01-23 23:49:34)
        put("51009|smartfren|00000|smartfren", new ApnParameters("http://10.17.93.103:8080", "10.17.27.250", 8080));

        //smartfren (Report Count: 44, Last Report: 2015-04-06 07:41:57)
        put("51009|smartfren|51009|smartfren", new ApnParameters("http://10.17.93.103:8080", "10.17.27.250", 8080));

        //smartfren (Report Count: 3, Last Report: 2015-02-26 11:50:36)
        put("51009||51009|smartfren", new ApnParameters("http://10.17.93.103:8080", "10.17.27.250", 8080));

        //smartfren (Report Count: 19, Last Report: 2015-04-06 03:20:59)
        put("51028|smartfren|51009|smartfren", new ApnParameters("http://10.17.93.103:8080", "10.17.27.250", 8080));

        //Smartfren (Report Count: 3, Last Report: 2015-03-19 10:51:08)
        put("51028||51009|Smartfren", new ApnParameters("http://10.17.93.103:8080", "10.17.27.250", 8080));

        //smartfren (Report Count: 2, Last Report: 2015-04-02 14:48:38)
        put("||51009|smartfren", new ApnParameters("http://10.17.93.103:8080", "10.17.27.250", 8080));

        //SmarTone HK (Report Count: 13, Last Report: 2015-03-24 09:16:37)
        put("45406||45406|SmarTone HK", new ApnParameters("http://mms.smartone.com/server", "10.9.9.9", 8080));

        //SmarToneVodafone (Report Count: 3, Last Report: 2015-03-08 12:01:59)
        put("45406||45406|SmarToneVodafone", new ApnParameters("http://mms.smartone.com/server", "10.9.9.9", 8080));

        //SoftBank (Report Count: 2, Last Report: 2015-04-06 23:44:21)
        put("44000|Y!mobile|44020|SoftBank", new ApnParameters("http://mms.emx.ymobile1.ne.jp", "mmsproxy.emx.ymobile1.ne.jp", 8080));

        //SoftBank (Report Count: 95, Last Report: 2015-04-06 14:52:39)
        put("44020|SoftBank|44020|SoftBank", new ApnParameters("http://mms-s", "andmms.plusacs.ne.jp", 8080));

        //SoftBank (Report Count: 65, Last Report: 2015-04-06 12:36:02)
        put("44020|Vodafone|44020|SoftBank", new ApnParameters("http://mms/", "mmsopen.softbank.ne.jp", 8080));

        //SoftBank (Report Count: 14, Last Report: 2015-04-05 17:58:22)
        put("44020|Y!mobile|44020|SoftBank", new ApnParameters("http://mms-s", "andmms.plusacs.ne.jp", 8080));

        //Solavei (Report Count: 50, Last Report: 2015-04-02 02:04:57)
        put("310260||310260|Solavei", new ApnParameters("http://solavei.mmsmvno.com/mms/wapenc", null, null));

        //Solo (Report Count: 1, Last Report: 2015-01-08 22:32:50)
        put("302610||302610|Solo", new ApnParameters("http://mms.bell.ca/mms/wapenc", null, null));

        //Sonera (Report Count: 3, Last Report: 2015-03-21 19:00:53)
        put("24491||24491|Sonera", new ApnParameters("http://mms.sonera.fi:8002", "195.156.25.33", 80));

        //Spark (Report Count: 1, Last Report: 2015-03-12 00:40:52)
        put("53005|Skinny|53005|Spark", new ApnParameters("http://mms.mmsaccess.co.nz", "210.55.11.73", 80));

        //Spark NZ (Report Count: 10, Last Report: 2015-04-02 22:04:32)
        put("53005|Skinny|53005|Spark NZ", new ApnParameters("http://lsmmsc.xtra.co.nz", "210.55.11.73", 80));

        //Spark NZ (Report Count: 32, Last Report: 2015-04-07 01:57:02)
        put("53005|Spark NZ|53005|Spark NZ", new ApnParameters("http://lsmmsc.xtra.co.nz", "210.55.11.73", 80));

        //Spark NZ (Report Count: 3, Last Report: 2014-11-21 21:04:25)
        put("53005|Telecom NZ|53005|Spark NZ", new ApnParameters("http://lsmmsc.xtra.co.nz", "210.55.11.73", 80));

        //SpeakOut (Report Count: 2, Last Report: 2014-10-30 23:46:32)
        put("302720|SpeakOut|302720|SpeakOut", new ApnParameters("mms.gprs.rogers.com", "mmsproxy.rogers.com", 80));

        //Spot Mobile (Report Count: 1, Last Report: 2014-07-07 03:28:35)
        put("310260||310260|Spot Mobile", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //Sprint (Report Count: 1, Last Report: 2014-10-15 02:05:31)
        put("25001|Sprint|31000|Sprint", new ApnParameters("http://mms.sprintpcs.com", "68.28.31.7", 80));

        //Sprint (Report Count: 2, Last Report: 2015-03-03 21:40:38)
        put("310006|Sprint|310120|Sprint", new ApnParameters("http://mms.sprintpcs.com", "68.28.31.7", 80));

        //SPRINT (Report Count: 15, Last Report: 2015-03-12 11:56:37)
        put("310120|SPRINT|310120|SPRINT", new ApnParameters("", null, null));

        //Sprint (Report Count: 1, Last Report: 2014-10-03 17:20:49)
        put("310120|Sprint|000000|Sprint", new ApnParameters("http://mms.sprintpcs.com", "68.28.31.7", 80));

        //Sprint (Report Count: 4, Last Report: 2015-02-08 22:05:28)
        put("310120|Sprint|00000|Sprint", new ApnParameters("http://mms.sprintpcs.com", "68.28.31.7", 80));

        //Sprint (Report Count: 1, Last Report: 2014-12-06 22:27:17)
        put("310120|Sprint|001001|Sprint", new ApnParameters("http://mms.sprintpcs.com", "68.28.31.7", 80));

        //Sprint (Report Count: 3, Last Report: 2015-03-20 22:11:09)
        put("310120|Sprint|123456|Sprint", new ApnParameters("http://mms.sprintpcs.com", "68.28.31.7", 80));

        //Sprint (Report Count: 1, Last Report: 2015-01-21 11:18:03)
        put("310120|Sprint|31000|", new ApnParameters("http://mms.sprintpcs.com", "68.28.31.7", 80));

        //Sprint (Report Count: 12, Last Report: 2015-03-17 01:16:50)
        put("310120|Sprint|31000| ", new ApnParameters("http://mms.plspictures.com", "68.28.31.7", -1));

        //Sprint (Report Count: 130, Last Report: 2015-03-22 00:56:41)
        put("310120|Sprint|31000|Sprint", new ApnParameters("http://mms.sprintpcs.com", "68.28.31.7", 80));

        //Sprint (Report Count: 4, Last Report: 2015-03-30 21:17:00)
        put("310120|Sprint|310120|", new ApnParameters("null", null, null));

        //Sprint (Report Count: 12, Last Report: 2015-04-05 13:55:57)
        put("310120|Sprint|310120| ", new ApnParameters("http://mms.plspictures.com", "68.28.31.7", 80));

        //Sprint (Report Count: 3196, Last Report: 2015-04-08 17:05:00)
        put("310120|Sprint|310120|Sprint", new ApnParameters("http://mms.sprintpcs.com", "68.28.31.7", 80));

        //Sprint (Report Count: 63, Last Report: 2015-03-29 21:29:04)
        put("310120|Sprint|31070|Sprint", new ApnParameters("http://mms.sprintpcs.com", "68.28.31.7", 80));

        //Sprint (Report Count: 14, Last Report: 2015-03-06 08:29:00)
        put("310120|Sprint|311480|Sprint", new ApnParameters("http://mms.sprintpcs.com", "68.28.31.7", 80));

        //Sprint (Report Count: 10, Last Report: 2015-01-25 01:53:09)
        put("310120|Sprint|311490|Sprint", new ApnParameters("http://mms.sprintpcs.com", "68.28.31.7", 80));

        //Sprint (Report Count: 26, Last Report: 2015-03-31 00:24:15)
        put("310120|Sprint|311870|Sprint", new ApnParameters("http://mms.sprintpcs.com", "68.28.31.7", 80));

        //Sprint (Report Count: 1, Last Report: 2015-02-09 05:21:10)
        put("310120||31000|Sprint", new ApnParameters("http://mms.sprintpcs.com", "68.28.31.7", 80));

        //Sprint (Report Count: 1, Last Report: 2014-12-07 03:12:36)
        put("310120||310120|Sprint", new ApnParameters("http://mms.sprintpcs.com", "68.28.31.7", 80));

        //Sprint (Report Count: 1, Last Report: 2015-04-05 18:44:58)
        put("310120||311870|Sprint", new ApnParameters("http://mms.sprintpcs.com", "68.28.31.7", 80));

        //Sprint (Report Count: 13, Last Report: 2015-03-05 22:12:47)
        put("31012|Sprint|31000|Sprint", new ApnParameters("http://mms.sprintpcs.com", "68.28.31.7", 80));

        //Sprint (Report Count: 36, Last Report: 2015-03-23 14:05:46)
        put("31012|Sprint|310120|Sprint", new ApnParameters("http://mms.sprintpcs.com", "68.28.31.7", 80));

        //Sprint (Report Count: 17, Last Report: 2015-03-07 19:46:58)
        put("31012|Sprint|311480|Sprint", new ApnParameters("http://mms.sprintpcs.com", "68.28.31.7", 80));

        //Sprint (Report Count: 1, Last Report: 2015-02-11 14:46:19)
        put("310260|Sprint|310120|Sprint", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //Sprint (Report Count: 1, Last Report: 2015-03-23 22:38:17)
        put("311230|C-Spire|310120|Sprint", new ApnParameters("http://pix.cspire.com/servlets/mms", "66.175.144.91", 80));

        //Sprint (Report Count: 98, Last Report: 2015-04-08 14:35:53)
        put("311870|Boost Mobile|311870|Sprint", new ApnParameters("null", null, null));

        //Sprint (Report Count: 77, Last Report: 2015-04-07 15:53:44)
        put("312530|Sprint|312530|Sprint", new ApnParameters("http://mmsc.vmobl.com:8088", "68.28.31.2", 80));

        //Sprint (Report Count: 1, Last Report: 2015-02-21 08:15:19)
        put("334030|Movistar|310120|Sprint", new ApnParameters("", null, null));

        //Sprint (Report Count: 1, Last Report: 2014-12-29 00:54:00)
        put("|Sprint|00000|Sprint", new ApnParameters("http://mms.sprintpcs.com", "68.28.31.7", 80));

        //Sprint (Report Count: 1, Last Report: 2015-01-18 20:03:27)
        put("|Sprint|310000|Sprint", new ApnParameters("http://mms.sprintpcs.com", "68.28.31.7", 80));

        //Sprint (Report Count: 7, Last Report: 2015-01-13 22:46:46)
        put("|Sprint|31000|Sprint", new ApnParameters("http://mms.sprintpcs.com", "68.28.31.7", 80));

        //Sprint (Report Count: 34, Last Report: 2015-04-06 11:53:53)
        put("|Sprint|310120|Sprint", new ApnParameters("http://mms.sprintpcs.com", "68.28.31.7", 80));

        //Sprint (Report Count: 2, Last Report: 2015-02-03 16:54:53)
        put("|Sprint|31070|Sprint", new ApnParameters("http://mms.sprintpcs.com", "68.28.31.7", 80));

        //Sprint (Report Count: 4, Last Report: 2015-03-10 19:48:08)
        put("||31000|Sprint", new ApnParameters("http://mm.myboostmobile.com", "68.28.31.7", 80));

        //Sprint (Report Count: 2, Last Report: 2015-02-17 13:46:26)
        put("||310120|Sprint", new ApnParameters("http://mms.sprintpcs.com", "68.28.31.7", 80));

        //Sprint (Report Count: 1, Last Report: 2014-12-25 16:52:40)
        put("|||Sprint", new ApnParameters("http://mms.sprintpcs.com", "68.28.31.7", 80));

        //SRI airtel (Report Count: 2, Last Report: 2015-03-14 16:46:02)
        put("41305|airtel|41305|SRI airtel", new ApnParameters("http://mmsc/", "10.200.184.86", 8080));

        //SRI DIALOG (Report Count: 29, Last Report: 2015-04-02 05:44:38)
        put("41302|Dialog|41302|SRI DIALOG", new ApnParameters("http://mms.dialog.lk:3130/mmsc", null, null));

        //SRI DIALOG (Report Count: 1, Last Report: 2014-12-04 11:47:31)
        put("41302|Subscription 1|41302|SRI DIALOG", new ApnParameters("http://mms.dialog.lk:3130/mmsc", "192.168.122.2", 8080));

        //srtcomm (Report Count: 1, Last Report: 2015-03-17 23:31:10)
        put("311610|srtcomm|31000|", new ApnParameters("http://mms.iot1.com/srt/mms.php", "mms.iot1.com", 9201));

        //STARHUB (Report Count: 10, Last Report: 2015-03-22 08:51:13)
        put("52505|StarHub|52505|STARHUB", new ApnParameters("http://mms.starhubgee.com.sg:8002/", "10.12.1.80", 80));

        //StarHub (Report Count: 5, Last Report: 2015-03-18 08:36:15)
        put("52505|StarHub|52505|StarHub", new ApnParameters("http://mms.starhubgee.com.sg:8002", "10.12.1.80", 80));

        //STC (Report Count: 1, Last Report: 2015-03-03 17:52:30)
        put("42001|STC|42001|STC", new ApnParameters("http://mms.net.sa:8002", "10.1.1.1", 8080));

        //STRATA (Report Count: 1, Last Report: 2014-10-30 18:47:53)
        put("310000|Verizon|311480|STRATA", new ApnParameters("http://mms.rinawireless.com", null, null));

        //SUN (Report Count: 1, Last Report: 2014-12-01 16:06:48)
        put("51503|SMART Prepaid|51503|SUN", new ApnParameters("http://10.102.61.238:8002", "10.102.61.46", 8080));

        //SUN (Report Count: 3, Last Report: 2015-02-14 02:32:35)
        put("51505|SUN|51505|SUN", new ApnParameters("http://mmscenter.suncellular.com.ph", "202.138.159.78", 8080));

        //Sunrise (Report Count: 1, Last Report: 2015-03-26 06:39:44)
        put("20801|Orange F|22802|Sunrise", new ApnParameters("http://mms.orange.fr", "192.168.10.200", 8080));

        //Sunrise (Report Count: 1, Last Report: 2014-12-08 12:25:33)
        put("22802|Lebara|22802|Sunrise", new ApnParameters("http://mmsc.sunrise.ch", "212.35.34.75", 8080));

        //Sunrise (Report Count: 13, Last Report: 2015-03-16 12:22:37)
        put("22802|Sunrise|22802|Sunrise", new ApnParameters("http://mmsc.sunrise.ch", "212.35.34.75", 8080));

        //Sunrise (Report Count: 1, Last Report: 2015-03-05 15:51:13)
        put("22802|yallo|22802|Sunrise", new ApnParameters("http://mmsc.sunrise.ch", "212.35.34.75", 8080));

        //SVYAZNOY (Report Count: 1, Last Report: 2015-02-13 11:39:53)
        put("25001|SVYAZNOY|25001|SVYAZNOY", new ApnParameters("http://mmsc", "192.168.192.192", 8080));

        //SWEDEN (Report Count: 3, Last Report: 2015-03-31 10:29:37)
        put("24008|Telenor SE|24004|SWEDEN", new ApnParameters("http://mms", "172.30.253.241", 8799));

        //Sweden 3G (Report Count: 1, Last Report: 2014-10-17 17:59:47)
        put("24001|halebop|24005|Sweden 3G", new ApnParameters("http://mmss/", "193.209.134.132", 8080));

        //Sweden 3G (Report Count: 8, Last Report: 2015-03-17 14:55:54)
        put("24007|Comviq|24005|Sweden 3G", new ApnParameters("http://mmsc.tele2.se", "130.244.202.30", 8080));

        //Sweden 3G (Report Count: 1, Last Report: 2014-12-04 07:29:01)
        put("24007|Tele2 Ftg|24005|Sweden 3G", new ApnParameters("http://mmsc.tele2.se", "130.244.202.30", 8080));

        //Sweden 3G (Report Count: 1, Last Report: 2015-03-23 13:24:50)
        put("24007|Tele2Comviq|24005|Sweden 3G", new ApnParameters("http://mmsc.tele2.se", "130.244.202.30", 8080));

        //Sweden 3G (Report Count: 5, Last Report: 2015-03-02 19:34:58)
        put("24007|Tele2|24005|Sweden 3G", new ApnParameters("http://mmsc.tele2.se", "130.244.202.30", 8080));

        //Sweden 3G (Report Count: 1, Last Report: 2014-07-23 11:38:49)
        put("24205|One Call|24005|Sweden 3G", new ApnParameters("http://mms.nwn.no", "188.149.250.10", 80));

        //Sweden Mobile (Report Count: 1, Last Report: 2015-04-06 10:10:49)
        put("24007|Comviq|24024|Sweden Mobile", new ApnParameters("http://mmsc.tele2.se", "130.244.202.30", 8080));

        //Swedfone (Report Count: 1, Last Report: 2014-09-08 04:04:01)
        put("24002|Swedfone|24002|Swedfone", new ApnParameters("http://mms.tre.se", "172.16.53.11", 8799));

        //Swisscom (Report Count: 2, Last Report: 2015-02-12 07:37:58)
        put("22801|M-Budget Mobile|22801|Swisscom", new ApnParameters("http://mms.natel.ch:8079", "192.168.210.2", 8080));

        //Swisscom (Report Count: 1, Last Report: 2015-04-04 16:20:45)
        put("22801|M-Budget|22801|Swisscom", new ApnParameters("http://mms.natel.ch:8079", "192.168.210.2", 8080));

        //Swisscom (Report Count: 41, Last Report: 2015-04-07 15:16:57)
        put("22801|Swisscom|22801|Swisscom", new ApnParameters("http://mms.natel.ch:8079", "192.168.210.2", 8080));

        //Swisscom (Report Count: 1, Last Report: 2015-03-28 18:44:28)
        put("22801||22801|Swisscom", new ApnParameters("http://mms.natel.ch:8079", "192.168.210.2", 8080));

        //Swisscom,Sunrise (Report Count: 1, Last Report: 2014-12-13 16:37:45)
        put("22801|Swisscom,Netto|22801|Swisscom,Sunrise", new ApnParameters("http://mms.natel.ch:8079", "192.168.210.2", 8080));

        //T - Mobile (Report Count: 2, Last Report: 2014-12-31 21:11:25)
        put("310260|T - Mobile|310260|T - Mobile", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //T - Mobile (Report Count: 18, Last Report: 2015-03-21 21:33:55)
        put("310260|T-Mobile|310260|T - Mobile", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //T - Mobile (Report Count: 13, Last Report: 2015-03-16 11:57:58)
        put("310260||310260|T - Mobile", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //T Star (Report Count: 1, Last Report: 2014-12-12 02:50:35)
        put("302610||46689|T Star", new ApnParameters("http://mms.bell.ca/mms/wapenc", null, null));

        //T-2 (Report Count: 2, Last Report: 2015-02-14 08:02:26)
        put("29364|T-2|29364|T-2", new ApnParameters("http://www.mms.t-2.net:8002", "172.20.18.137", 8080));

        //T-CDMA 64 (Report Count: 2, Last Report: 2015-03-17 21:11:24)
        put("310002|Verizon Wireless|123456|T-CDMA 64", new ApnParameters("http://mms.vzwreseller.com/sevlets/mms", null, null));

        //T-CDMA 64 (Report Count: 2, Last Report: 2015-04-05 01:22:32)
        put("310002|Verizon Wireless|311480|T-CDMA 64", new ApnParameters("http://mms.vzwreseller.com/sevlets/mms", null, null));

        //T-CDMA 64 (Report Count: 2, Last Report: 2015-03-09 22:38:17)
        put("310003|Verizon Wireless|311480|T-CDMA 64", new ApnParameters("http://mms.vzwreseller.com/sevlets/mms", null, null));

        //T-CDMA 64 (Report Count: 2, Last Report: 2015-02-26 11:26:26)
        put("310004|Verizon Wireless|311480|T-CDMA 64", new ApnParameters("http://mms.vzwreseller.com/sevlets/mms", null, null));

        //T-CDMA 64 (Report Count: 2, Last Report: 2015-01-16 18:46:15)
        put("310004|Verizon|000000|T-CDMA 64", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //T-CDMA 64 (Report Count: 1, Last Report: 2015-03-26 19:30:48)
        put("310004||311480|T-CDMA 64", new ApnParameters("http://mms.vzwreseller.com/sevlets/mms", null, null));

        //T-CDMA 64 (Report Count: 5, Last Report: 2015-04-03 11:18:40)
        put("310005|Verizon Wireless|311480|T-CDMA 64", new ApnParameters("http://mms.vzwreseller.com/sevlets/mms", null, null));

        //T-CDMA 64 (Report Count: 2, Last Report: 2015-03-03 23:48:19)
        put("310006|Verizon Wireless|311480|T-CDMA 64", new ApnParameters("http://mms.vzwreseller.com/sevlets/mms", null, null));

        //T-CDMA 64 (Report Count: 3, Last Report: 2015-02-20 23:37:29)
        put("310007|Verizon Wireless|311480|T-CDMA 64", new ApnParameters("http://mms.vzwreseller.com/sevlets/mms", null, null));

        //T-CDMA 64 (Report Count: 5, Last Report: 2015-04-02 02:06:46)
        put("310008|Verizon Wireless|311480|T-CDMA 64", new ApnParameters("http://mms.vzwreseller.com/sevlets/mms", null, null));

        //T-CDMA 64 (Report Count: 2, Last Report: 2015-03-02 03:32:13)
        put("310009|Verizon Wireless|311480|T-CDMA 64", new ApnParameters("http://mms.vzwreseller.com/sevlets/mms", null, null));

        //T-CDMA 64 (Report Count: 1, Last Report: 2015-01-13 15:33:54)
        put("31000||311480|T-CDMA 64", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //T-CDMA 64 (Report Count: 1, Last Report: 2014-10-14 18:07:44)
        put("311480|Verizon Wireless|311480|T-CDMA 64", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //T-Mobile (Report Count: 1, Last Report: 2014-09-18 05:52:02)
        put("12345|T-Mobile|310260|T-Mobile", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //T-Mobile (Report Count: 1, Last Report: 2015-03-13 12:29:36)
        put("21901|HT HR|21901|T-Mobile", new ApnParameters("http://mms.t-mobile.hr/servlets/mms", "10.12.0.4", 8080));

        //T-Mobile (Report Count: 1, Last Report: 2015-01-11 19:47:13)
        put("23430|T-Mobile|23430|T-Mobile", new ApnParameters("http://mmsc.t-mobile.co.uk:8002", "149.254.201.135", 8080));

        //T-Mobile (Report Count: 32, Last Report: 2015-04-08 12:56:27)
        put("23430||23430|T-Mobile", new ApnParameters("http://mms/", "149.254.201.135", 8080));

        //T-Mobile (Report Count: 3, Last Report: 2015-02-07 13:01:25)
        put("26201|Telekom.de|26201|T-Mobile", new ApnParameters("http://mms.t-mobile.de/servlets/mms", "172.28.23.131", 8008));

        //T-Mobile (Report Count: 2, Last Report: 2015-02-08 17:08:04)
        put("26201|mobilcom-debitel|26201|T-Mobile", new ApnParameters("http://mms.t-mobile.de/servlets/mms", "172.28.23.131", 8008));

        //T-Mobile (Report Count: 4, Last Report: 2015-03-17 22:24:54)
        put("302490||310260|T-Mobile", new ApnParameters("http://mms.windmobile.ca", "74.115.197.70", 8080));

        //T-Mobile (Report Count: 1, Last Report: 2015-01-27 14:56:30)
        put("310260|Get More...|310260|T-Mobile", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //T-Mobile (Report Count: 6, Last Report: 2014-12-20 04:28:58)
        put("310260|Home|310260|T-Mobile", new ApnParameters("http://mmsc.tracfone.com", null, null));

        //T-Mobile (Report Count: 4, Last Report: 2014-12-23 07:21:07)
        put("310260|MetroPCS|310260|T-Mobile", new ApnParameters("http://metropcs.mmsmvno.com/mms/wapenc", null, null));

        //T-Mobile (Report Count: 2, Last Report: 2015-02-10 21:43:22)
        put("310260|Simple Mobile|310260|T-Mobile", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //T-Mobile (Report Count: 7, Last Report: 2015-03-30 01:25:47)
        put("310260|Sprint|310260|T-Mobile", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //T-Mobile (Report Count: 1, Last Report: 2015-02-24 22:27:51)
        put("310260|T-Mobile |310260|T-Mobile", new ApnParameters("http://wirelessfour.mmsmvno.com/mms/wapenc", null, null));

        //T-Mobile (Report Count: 18, Last Report: 2015-04-07 18:41:55)
        put("310260|T-Mobile@|310260|T-Mobile", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //T-Mobile (Report Count: 817, Last Report: 2015-04-08 16:16:34)
        put("310260|T-Mobile|310260|T-Mobile", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //T-Mobile (Report Count: 137, Last Report: 2015-04-08 16:25:26)
        put("310260|Verizon|310260|T-Mobile", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //T-Mobile (Report Count: 1, Last Report: 2015-03-17 11:38:30)
        put("310260||23430|T-Mobile", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //T-Mobile (Report Count: 10266, Last Report: 2015-04-08 17:41:03)
        put("310260||310260|T-Mobile", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //T-Mobile (Report Count: 2, Last Report: 2015-03-26 00:30:38)
        put("310770||310260|T-Mobile", new ApnParameters("http://mmsc.iwireless.dataonair.net:6672", "209.4.229.31", 9201));

        //T-Mobile (Report Count: 3, Last Report: 2015-03-30 23:14:56)
        put("311370||310260|T-Mobile", new ApnParameters("http://mmsc.gci.net", "24.237.158.34", 9201));

        //T-Mobile (Report Count: 2, Last Report: 2015-03-01 23:30:58)
        put("310260|BrightSpot|310260|T-Mobile ", new ApnParameters("http://brtspt.mmsmvno.com/mms/wapenc", null, null));

        //T-Mobile (Report Count: 2, Last Report: 2014-12-14 04:59:01)
        put("310260|Family Mobile|310260|T-Mobile ", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //T-Mobile (Report Count: 3, Last Report: 2015-04-07 15:34:47)
        put("310260|Home|310260|T-Mobile ", new ApnParameters("http://mms.tracfone.com", null, null));

        //T-Mobile (Report Count: 1, Last Report: 2015-02-27 13:55:44)
        put("310260|Lycamobile|310260|T-Mobile ", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //T-Mobile (Report Count: 1, Last Report: 2014-11-23 21:54:18)
        put("310260|MetroPCS@|310260|T-Mobile ", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //T-Mobile (Report Count: 2, Last Report: 2014-10-18 02:06:10)
        put("310260|MetroPCS|310260|T-Mobile ", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //T-Mobile (Report Count: 111, Last Report: 2015-04-07 17:30:06)
        put("310260||310260|T-Mobile ", new ApnParameters("http://wholesale.mmsmvno.com/mms/wapenc", null, null));

        //T-Mobile A (Report Count: 29, Last Report: 2015-03-20 14:21:04)
        put("23203||23203|T-Mobile A", new ApnParameters("http://mmsc.t-mobile.at/servlets/mms", "10.12.0.20", 80));

        //T-Mobile CZ (Report Count: 2, Last Report: 2015-04-07 18:30:34)
        put("23001|Mobil CZ|23001|T-Mobile CZ", new ApnParameters("http://mms", "10.0.0.10", 80));

        //T-Mobile CZ (Report Count: 1, Last Report: 2015-02-10 16:22:06)
        put("23001|PAEGAS CZ|23001|T-Mobile CZ", new ApnParameters("http://mms", "10.0.0.10", 80));

        //T-Mobile CZ (Report Count: 10, Last Report: 2015-03-19 08:13:22)
        put("23001|T-Mobile CZ|23001|T-Mobile CZ", new ApnParameters("http://mms", "10.0.0.10", 80));

        //T-Mobile D (Report Count: 1, Last Report: 2015-01-25 19:21:17)
        put("26002|T-Mobile.pl Q|26201|T-Mobile D", new ApnParameters("http://mms/servlets/mms", "213.158.194.226", 8080));

        //T-Mobile D (Report Count: 1, Last Report: 2014-11-28 17:12:54)
        put("26201|Privat|26201|T-Mobile D", new ApnParameters("http://mms.t-mobile.de/servlets/mms", "172.28.23.131", 8008));

        //T-Mobile D (Report Count: 4, Last Report: 2015-02-04 10:54:22)
        put("26201|Telekom.de|26201|T-Mobile D", new ApnParameters("http://mms.t-mobile.de/servlets/mms", "172.28.23.131", 8008));

        //T-Mobile D (Report Count: 1, Last Report: 2015-01-21 13:09:13)
        put("26201|klarmobil|26201|T-Mobile D", new ApnParameters("http://mms.t-mobile.de/servlets/mms", "172.28.23.131", 8008));

        //T-Mobile D (Report Count: 2, Last Report: 2015-03-07 08:59:56)
        put("26201||26201|T-Mobile D", new ApnParameters("http://mms.t-mobile.de/servlets/mms", "172.28.23.131", 8008));

        //T-Mobile HR (Report Count: 1, Last Report: 2014-12-31 14:28:11)
        put("21901|HT HR|21901|T-Mobile HR", new ApnParameters("http://mms.t-mobile.hr/servlets/mms", "10.12.0.4", 8080));

        //T-Mobile HR (Report Count: 2, Last Report: 2015-01-01 14:50:12)
        put("21901|T-Mobile HR|21901|T-Mobile HR", new ApnParameters("http://mms.t-mobile.hr/servlets/mms", "10.12.0.4", 8080));

        //T-Mobile NL (Report Count: 1, Last Report: 2015-02-08 19:30:30)
        put("20402|Tele2|20416|T-Mobile NL", new ApnParameters("http://mmsc.tele2.nl", "193.12.40.64", 8080));

        //T-Mobile Orange (Report Count: 6, Last Report: 2015-02-14 10:09:59)
        put("23430||23433|T-Mobile Orange", new ApnParameters("http://mms/", "149.254.201.135", 8080));

        //T-Mobile UK (Report Count: 1, Last Report: 2014-11-03 10:07:05)
        put("23430|Virgin@|23430|T-Mobile UK", new ApnParameters("http://mms.virginmobile.co.uk:8002", "193.30.166.2", 8080));

        //T-Mobile UK (Report Count: 9, Last Report: 2015-02-12 23:03:32)
        put("23430||23430|T-Mobile UK", new ApnParameters("http://mms/", "149.254.201.135", 8080));

        //T-Mobile UK (Report Count: 1, Last Report: 2014-08-18 08:29:23)
        put("23433|@@@@@@@@@@@@@@@@|23430|T-Mobile UK", new ApnParameters("http://mms/", "149.254.201.135", 8080));

        //T-Mobile UK (Report Count: 5, Last Report: 2015-03-22 17:13:54)
        put("23433|EE|23430|T-Mobile UK", new ApnParameters("http://mms/", "149.254.201.135", 8080));

        //T-Mobile.pl (Report Count: 7, Last Report: 2015-03-30 13:46:07)
        put("26002|T-Mobile.pl|26002|T-Mobile.pl", new ApnParameters("http://mms/servlets/mms", "213.158.194.226", 8080));

        //T-Mobile.pl (Report Count: 2, Last Report: 2015-02-26 21:53:32)
        put("26002||26002|T-Mobile.pl", new ApnParameters("http://mms/servlets/mms", "213.158.194.226", 8080));

        //T-Sel (Report Count: 1, Last Report: 2015-03-11 04:47:00)
        put("51010|T-Sel|51010|T-Sel", new ApnParameters("http://mms.telkomsel.com", "10.1.89.150", 8000));

        //Talk N Text (Report Count: 2, Last Report: 2015-04-06 13:01:20)
        put("51503|Talk N Text|51503|Talk N Text", new ApnParameters("http://10.102.61.238:8002", "10.102.61.46", 8080));

        //Talkline (Report Count: 1, Last Report: 2014-11-06 17:11:03)
        put("26202|Talkline|26202|Talkline", new ApnParameters("http://139.7.24.1/servlets/mms", "139.7.29.17", 80));

        //TALKLINE (Report Count: 1, Last Report: 2015-01-17 12:15:54)
        put("26203|TALKLINE|26203|TALKLINE", new ApnParameters("http://mms/eplus", "212.23.97.153", 5080));

        //Talkmobile (Report Count: 3, Last Report: 2015-04-08 10:17:54)
        put("23415|Talkmobile|23415|Talkmobile", new ApnParameters("http://mms.talkmobile.co.uk/servlets/mms", "212.183.137.12", 8799));

        //TalkTalk (Report Count: 7, Last Report: 2015-04-08 14:51:33)
        put("23415|TalkTalk|23415|TalkTalk", new ApnParameters("http://mms.talktalk.co.uk/servlets/mms", "212.183.137.12", 8799));

        //tata (Report Count: 1, Last Report: 2014-10-28 19:31:07)
        put("405045|TATA DOCOMO|405045|tata", new ApnParameters("http://mmsc/", "10.124.26.94", 8799));

        //TATA DOCOMO (Report Count: 2, Last Report: 2015-03-08 03:33:11)
        put("405025|TATA DOCOMO|405025|TATA DOCOMO", new ApnParameters("http://mmsc/", "10.124.26.94", 8799));

        //TATA DOCOMO (Report Count: 2, Last Report: 2014-11-13 16:39:16)
        put("405030|TATA DOCOMO|405030|TATA DOCOMO", new ApnParameters("http://mmsc/", "10.124.26.94", 8799));

        //TATA DOCOMO (Report Count: 2, Last Report: 2015-02-04 17:29:41)
        put("405031|TATA DOCOMO|405031|TATA DOCOMO", new ApnParameters("http://mmsc/", "10.124.26.94", 8799));

        //TATA DOCOMO (Report Count: 9, Last Report: 2015-04-05 06:54:48)
        put("405034|TATA DOCOMO|405034|TATA DOCOMO", new ApnParameters("http://mmsc/", "10.124.26.94", 8799));

        //TATA DOCOMO (Report Count: 1, Last Report: 2015-01-06 13:42:53)
        put("405035|TATA DOCOMO|405035|TATA DOCOMO", new ApnParameters("http://mmsc/", "10.124.26.94", 8799));

        //TATA DOCOMO (Report Count: 2, Last Report: 2015-02-09 04:47:23)
        put("405036|TATA DOCOMO|405036|TATA DOCOMO", new ApnParameters("http://mmsc/", "10.124.26.94", 8799));

        //TATA DOCOMO (Report Count: 2, Last Report: 2015-03-17 15:45:06)
        put("405037|TATA DOCOMO|405037|TATA DOCOMO", new ApnParameters("http://mmsc/", "10.124.26.94", 8799));

        //TATA DOCOMO (Report Count: 1, Last Report: 2015-03-04 09:20:01)
        put("405038|TATA DOCOMO|405038|TATA DOCOMO", new ApnParameters("http://10.4.42.21:8002/", "10.4.42.15", 8080));

        //TATA DOCOMO (Report Count: 1, Last Report: 2014-10-05 01:12:19)
        put("405039|TATA DOCOMO|405037|TATA DOCOMO", new ApnParameters("http://mmsc/", "10.124.26.94", 8799));

        //TATA DOCOMO (Report Count: 1, Last Report: 2014-10-01 19:54:09)
        put("405041|TATA DOCOMO|405041|TATA DOCOMO", new ApnParameters("http://10.50.1.166/servlets/mms", "172.17.83.69", 8080));

        //TATA DOCOMO (Report Count: 1, Last Report: 2014-08-05 14:57:58)
        put("405042|TATA DOCOMO|405042|TATA DOCOMO", new ApnParameters("http://mmsc/", "10.124.26.94", 8799));

        //TATA DOCOMO (Report Count: 3, Last Report: 2015-04-07 00:40:08)
        put("405044|TATA DOCOMO|405044|TATA DOCOMO", new ApnParameters("http://mmsc/", "10.124.26.94", 8799));

        //TATA DOCOMO (Report Count: 5, Last Report: 2014-12-28 14:05:45)
        put("405045|TATA DOCOMO|405045|TATA DOCOMO", new ApnParameters("http://mmsc/", "10.124.26.94", 8799));

        //TATA DOCOMO (Report Count: 2, Last Report: 2015-03-19 07:30:43)
        put("405046|TATA DOCOMO|405046|TATA DOCOMO", new ApnParameters("http://100.1.201.171:10021/mmsc", "100.1.201.172", 8799));

        //Tbaytel / Rogers (Report Count: 9, Last Report: 2015-03-13 23:12:24)
        put("302720|Tbaytel / Rogers|302720|Tbaytel / Rogers", new ApnParameters("http://mms.gprs.rogers.com", "mmsproxy.rogers.com", 80));

        //Tchibo (Report Count: 2, Last Report: 2015-03-27 19:40:57)
        put("26207||26207|Tchibo", new ApnParameters("http://10.81.0.7:8002/", "82.113.100.8", 8080));

        //TELCEL (Report Count: 26, Last Report: 2015-03-31 22:14:03)
        put("334020|TELCEL|334020|TELCEL", new ApnParameters("http://mms.itelcel.com/servlets/mms", "148.233.151.240", 8080));

        //TELCEL GSM (Report Count: 1, Last Report: 2015-01-05 00:15:13)
        put("334020|TELCEL GSM|334020|TELCEL GSM", new ApnParameters("http://mms.itelcel.com/servlets/mms", "148.233.151.240", 8080));

        //TELCEL GSM (Report Count: 4, Last Report: 2014-11-21 21:43:06)
        put("334020|TELCEL|334020|TELCEL GSM", new ApnParameters("http://mms.itelcel.com/servlets/mms", "148.233.151.240", 8080));

        //Telco (Report Count: 2, Last Report: 2015-02-16 15:21:09)
        put("26207||26207|Telco", new ApnParameters("http://10.81.0.7:8002/", "82.113.100.43", 8080));

        //tele.ring (Report Count: 5, Last Report: 2015-03-25 18:23:58)
        put("23207||23203|tele.ring", new ApnParameters("http://relay.mms.telering.at", "212.95.31.50", 80));

        //Tele2 (Report Count: 5, Last Report: 2015-01-31 16:07:10)
        put("21902|TELE2|21902|Tele2", new ApnParameters("http://mmsc.tele2.hr", "193.12.40.66", 8080));

        //Tele2 (Report Count: 2, Last Report: 2014-12-07 14:28:31)
        put("24007|Tele2|24005|Tele2", new ApnParameters("http://mmsc.tele2.se", "130.244.202.30", 8080));

        //Tele2 (Report Count: 1, Last Report: 2015-03-17 18:34:08)
        put("24007|Tele2|24007|", new ApnParameters("http://mmsc.tele2.se", "130.244.202.30", 8080));

        //TELE2 (Report Count: 4, Last Report: 2014-11-22 05:53:11)
        put("24702|LV TELE2|24702|TELE2", new ApnParameters("http://mmsc.tele2.lv/", "193.12.40.38", 8080));

        //TELE2 (Report Count: 7, Last Report: 2015-03-08 13:58:18)
        put("25020|TELE2|25020|TELE2", new ApnParameters("http://mmsc.tele2.ru", "193.12.40.65", 9201));

        //TELE2 (Report Count: 1, Last Report: 2014-11-21 06:04:16)
        put("25020||25020|TELE2", new ApnParameters("http://mmsc.tele2.ru", "193.12.40.65", 9201));

        //Tele2 (Report Count: 1, Last Report: 2015-01-02 19:17:44)
        put("26203|Tele2|26203|Tele2", new ApnParameters("http://mms/eplus/", "212.23.97.153", 5080));

        //Tele2 EE (Report Count: 2, Last Report: 2015-01-13 15:33:22)
        put("24803|Tele2 EE|24803|Tele2 EE", new ApnParameters("http://mmsc.tele2.ee", "193.12.40.6", 8080));

        //Tele2 LT (Report Count: 1, Last Report: 2015-03-26 15:03:11)
        put("24603|PILDYK|24603|Tele2 LT", new ApnParameters("http://mmsc.tele2.lt", "193.12.40.29", 8080));

        //Tele2 LT (Report Count: 2, Last Report: 2015-03-01 12:39:05)
        put("24603|Tele2|24603|Tele2 LT", new ApnParameters("http://mmsc.tele2.lt/", "193.12.40.29", 8080));

        //Tele2 LV (Report Count: 1, Last Report: 2014-11-29 09:05:39)
        put("24702|LV TELE2|24702|Tele2 LV", new ApnParameters("http://mmsc.tele2.lv", "193.12.40.38", 8080));

        //TELE2 RU (Report Count: 4, Last Report: 2015-02-27 13:12:29)
        put("25020|TELE2|25020|TELE2 RU", new ApnParameters("http://mmsc", "192.168.192.192", 8080));

        //TELE2 RU (Report Count: 1, Last Report: 2015-01-30 12:24:43)
        put("25020||25001|TELE2 RU", new ApnParameters("http://mmsc", "192.168.192.192", 8080));

        //Tele2 SE (Report Count: 1, Last Report: 2014-12-01 22:27:47)
        put("24007|Comviq|24007|Tele2 SE", new ApnParameters("http://mmsc.tele2.se", "130.244.202.30", 8080));

        //Tele2 SE (Report Count: 1, Last Report: 2014-09-17 06:02:30)
        put("24007|Tele2|24007|Tele2 SE", new ApnParameters("http://mmsc.tele2.se", "130.244.202.30", 8080));

        //Telecom NZ (Report Count: 3, Last Report: 2015-03-12 01:10:25)
        put("53005|Spark NZ|53005|Telecom NZ", new ApnParameters("http://lsmmsc.xtra.co.nz", "210.55.11.73", 80));

        //Telecom NZ (Report Count: 5, Last Report: 2015-01-20 03:11:03)
        put("53005|Telecom NZ|53005|Telecom NZ", new ApnParameters("http://lsmmsc.xtra.co.nz", "210.55.11.73", 80));

        //Telecom NZ (Report Count: 1, Last Report: 2015-01-17 22:43:10)
        put("53005|slingshot|53005|Telecom NZ", new ApnParameters("http://lsmmsc.xtra.co.nz", "210.55.11.73", 80));

        //Telefonica Moviles Uruguay (Report Count: 1, Last Report: 2015-03-20 22:36:07)
        put("74807|Movistar|74807|Telefonica Moviles Uruguay", new ApnParameters("http://mmsc.movistar.com.uy", "10.0.2.29", 8080));

        //Telefonica O2 UK Limited (Report Count: 2, Last Report: 2014-12-27 21:21:25)
        put("23410||23410|Telefonica O2 UK Limited", new ApnParameters("http://mmsc.mms.o2.co.uk:8002", "193.113.200.195", 8080));

        //Telekom HU (Report Count: 1, Last Report: 2015-03-17 10:23:41)
        put("21630|T-Mobile H|21630|Telekom HU", new ApnParameters("http://mms.t-mobile.hu/servlets/mms", "212.51.126.10", 8080));

        //Telekom HU (Report Count: 11, Last Report: 2015-04-04 13:41:39)
        put("21630|Telekom HU|21630|Telekom HU", new ApnParameters("http://mms.t-mobile.hu/servlets/mms", "212.51.126.10", 8080));

        //Telekom.de (Report Count: 1, Last Report: 2015-01-25 01:43:31)
        put("23433|EE|26201|Telekom.de", new ApnParameters("http://mms/", "149.254.201.135", 8080));

        //Telekom.de (Report Count: 1, Last Report: 2014-12-05 11:26:10)
        put("26201|Business|26201|Telekom.de", new ApnParameters("http://mms.t-mobile.de/servlets/mms", "172.28.23.131", 8008));

        //Telekom.de (Report Count: 2, Last Report: 2015-02-07 16:55:07)
        put("26201|TALKLINE|26201|Telekom.de", new ApnParameters("http://mms.t-mobile.de/servlets/mms", "172.28.23.131", 8008));

        //Telekom.de (Report Count: 71, Last Report: 2015-04-08 16:02:02)
        put("26201|Telekom.de|26201|Telekom.de", new ApnParameters("http://mms.t-mobile.de/servlets/mms", "172.28.23.131", 8008));

        //Telekom.de (Report Count: 2, Last Report: 2015-02-06 18:40:24)
        put("26201|callmobile.de|26201|Telekom.de", new ApnParameters("http://mms.t-mobile.de/servlets/mms", "172.28.23.131", 8008));

        //Telekom.de (Report Count: 16, Last Report: 2015-04-08 10:35:18)
        put("26201|congstar|26201|Telekom.de", new ApnParameters("http://mms.t-mobile.de/servlets/mms", "172.28.23.131", 8008));

        //Telekom.de (Report Count: 6, Last Report: 2015-03-31 10:57:36)
        put("26201|klarmobil|26201|Telekom.de", new ApnParameters("http://mms.t-mobile.de/servlets/mms", "172.28.23.131", 8008));

        //Telekom.de (Report Count: 4, Last Report: 2015-04-06 18:27:44)
        put("26201|mobilcom-debitel|26201|Telekom.de", new ApnParameters("http://mms.t-mobile.de/servlets/mms", "172.28.23.131", 8008));

        //Telekom.de (Report Count: 5, Last Report: 2015-04-06 14:41:18)
        put("26201|mobilcom|26201|Telekom.de", new ApnParameters("http://mms.t-mobile.de/servlets/mms", "172.28.23.131", 8008));

        //Telekom.de (Report Count: 12, Last Report: 2015-04-05 18:24:27)
        put("26201||26201|Telekom.de", new ApnParameters("http://mms.t-mobile.de/servlets/mms", "172.28.23.131", 8008));

        //TELEKOM.RO (Report Count: 1, Last Report: 2015-02-03 15:34:12)
        put("22603|TELEKOM.RO|22606|TELEKOM.RO", new ApnParameters("http://mmsc1.mms.cosmote.ro:8002", "10.252.1.62", 8080));

        //Telenor (Report Count: 1, Last Report: 2014-09-30 19:45:21)
        put("24201|TELENOR|24201|Telenor", new ApnParameters("http://mmsc/", "10.10.10.11", 8080));

        //Telenor (Report Count: 1, Last Report: 2015-03-09 20:22:14)
        put("24201|Telenor|24201|Telenor", new ApnParameters("http://mmsc/", "mms-proxy.telenor.no", 8080));

        //Telenor (Report Count: 14, Last Report: 2015-03-26 02:03:19)
        put("41006||41006|Telenor", new ApnParameters("http://mmstelenor", "172.18.19.11", 8080));

        //Telenor HU (Report Count: 3, Last Report: 2015-03-03 15:42:03)
        put("21601|Telenor HU|21601|Telenor HU", new ApnParameters("http://mmsc.telenor.hu/", "84.225.255.1", 8080));

        //Telenor Pakistan (Report Count: 1, Last Report: 2015-04-07 13:35:18)
        put("41006|Telenor|41006|Telenor Pakistan", new ApnParameters("http://mmstelenor", "172.18.19.11", 8080));

        //Telenor Pakistan (Report Count: 1, Last Report: 2015-03-25 12:35:11)
        put("41006||41006|Telenor Pakistan", new ApnParameters("http://mmstelenor", "172.18.19.11", 8080));

        //Telenor PK (Report Count: 20, Last Report: 2015-04-03 07:36:08)
        put("41006||41006|Telenor PK", new ApnParameters("http://mmstelenor", "172.18.19.11", 8080));

        //Telenor SE (Report Count: 17, Last Report: 2015-03-24 20:00:55)
        put("24008|Telenor SE|24008|Telenor SE", new ApnParameters("http://mms", "172.30.253.241", 8799));

        //TelenorH (Report Count: 1, Last Report: 2014-11-27 17:30:10)
        put("21601|Telenor HU|21601|TelenorH", new ApnParameters("http://mmsc.telenor.hu/", "84.225.255.1", 8080));

        //Teletalk (Report Count: 1, Last Report: 2015-03-08 16:15:08)
        put("47004|Teletalk 3G|47004|Teletalk", new ApnParameters("http://10.5.4.22:38090/was", "10.5.4.40", 8080));

        //Telia (Report Count: 1, Last Report: 2015-03-27 10:38:47)
        put("24001|Telia|24001|Telia", new ApnParameters("http://mmss/", "193.209.134.132", 80));

        //TeliaDK (Report Count: 1, Last Report: 2015-01-14 19:15:49)
        put("23820|DLG Tele|23820|TeliaDK", new ApnParameters("http://mms.telia.dk", "193.209.134.131", 8080));

        //TeliaDK (Report Count: 1, Last Report: 2014-10-03 07:20:10)
        put("23820|TELIA DK|23820|TeliaDK", new ApnParameters("http://mms.telia.dk", "193.209.134.131", 8080));

        //TelkomSA (Report Count: 1, Last Report: 2015-01-09 11:38:47)
        put("65502|TelkomSA|65502|TelkomSA", new ApnParameters("http://mms.8ta.com:38090/was", "41.151.254.162", 8080));

        //TELKOMSEL (Report Count: 1, Last Report: 2015-02-20 12:15:18)
        put("51010|IND TELKOMSEL|51010|TELKOMSEL", new ApnParameters("http://mms.telkomsel.com", "10.1.89.150", 8000));

        //TELKOMSEL (Report Count: 7, Last Report: 2015-03-12 01:26:19)
        put("51010|T-Sel|51010|TELKOMSEL", new ApnParameters("http://mms.telkomsel.com", "10.1.89.150", 8000));

        //TELKOMSEL (Report Count: 24, Last Report: 2015-03-29 05:25:49)
        put("51010|TELKOMSEL|51010|TELKOMSEL", new ApnParameters("http://mms.telkomsel.com", "10.1.89.150", 8000));

        //TELKOMSEL (Report Count: 1, Last Report: 2014-12-02 12:40:10)
        put("51010|XL Axiata|51010|TELKOMSEL", new ApnParameters("http://mms.telkomsel.com", "10.1.89.150", 8000));

        //TELKOMSEL (Report Count: 234, Last Report: 2015-04-08 10:42:33)
        put("51010||51010|TELKOMSEL", new ApnParameters("http://mms.telkomsel.com", "10.1.89.150", 8000));

        //TELKOMSEL IND (Report Count: 2, Last Report: 2015-01-20 03:22:06)
        put("51010||51010|TELKOMSEL IND", new ApnParameters("http://mms.telkomsel.com", "10.1.89.150", 8000));

        //TELMORE (Report Count: 1, Last Report: 2014-12-30 17:42:01)
        put("23801|TELMORE|23801|TELMORE", new ApnParameters("http://mmsc.tdc.dk:8002", "194.182.251.15", 8080));

        //Telstra (Report Count: 1, Last Report: 2014-11-07 08:02:46)
        put("50501| |50501|Telstra", new ApnParameters("http://mmsc.telstra.com:8002", "10.1.1.180", 80));

        //Telstra (Report Count: 3, Last Report: 2015-02-28 08:44:22)
        put("50501|BOOST|50501|Telstra", new ApnParameters("http://mmsc.telstra.com:8002", "10.1.1.180", 80));

        //Telstra (Report Count: 61, Last Report: 2015-04-06 05:43:37)
        put("50501|Telstra|50501|Telstra", new ApnParameters("http://mmsc.telstra.com:8002", "10.1.1.180", 80));

        //Telstra Mobile (Report Count: 1, Last Report: 2015-02-16 00:45:26)
        put("310260||50501|Telstra Mobile", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //Telstra Mobile (Report Count: 1, Last Report: 2014-10-18 04:34:55)
        put("45406||50501|Telstra Mobile", new ApnParameters("http://mms.smartone.com/server", "10.9.9.9", 8080));

        //Telstra Mobile (Report Count: 4, Last Report: 2015-01-11 21:26:38)
        put("50501| |50501|Telstra Mobile", new ApnParameters("http://mmsc.telstra.com:8002/", "10.1.1.180", 80));

        //Telstra Mobile (Report Count: 15, Last Report: 2015-03-18 03:17:54)
        put("50501|ALDImobile|50501|Telstra Mobile", new ApnParameters("http://mmsc.telstra.com:8002/", "10.1.1.180", 80));

        //Telstra Mobile (Report Count: 25, Last Report: 2015-03-27 13:32:49)
        put("50501|BOOST|50501|Telstra Mobile", new ApnParameters("http://mmsc.telstra.com:8002/", "10.1.1.180", 80));

        //Telstra Mobile (Report Count: 161, Last Report: 2015-04-04 23:28:50)
        put("50501|Telstra|50501|Telstra Mobile", new ApnParameters("http://mmsc.telstra.com:8002/", "10.1.1.180", 80));

        //Telstra Mobile (Report Count: 1, Last Report: 2014-10-18 05:07:25)
        put("50501||50501|Telstra Mobile", new ApnParameters("http://mmsc.telstra.com:8002", "10.1.1.180", 80));

        //TELUS (Report Count: 1, Last Report: 2015-03-03 15:08:45)
        put("302220|Home|302220|TELUS", new ApnParameters("http://aliasredirect.net/proxy/mmsc", "74.49.0.18", 80));

        //TELUS (Report Count: 3, Last Report: 2015-02-03 17:26:11)
        put("302220|SIM1|302220|TELUS", new ApnParameters("http://aliasredirect.net/proxy/mmsc", "74.49.0.18", 80));

        //TELUS (Report Count: 1, Last Report: 2014-12-21 05:27:24)
        put("302220|Verizon|302220|TELUS", new ApnParameters("http://ALIASREDIRECT.NET/PROXY/MMSC", "74.49.0.18", 80));

        //TELUS (Report Count: 688, Last Report: 2015-04-08 17:30:58)
        put("302220||302220|TELUS", new ApnParameters("http://aliasredirect.net/proxy/mmsc", "74.49.0.18", 80));

        //TELUS (Report Count: 1, Last Report: 2015-01-15 18:29:29)
        put("302221||302221|TELUS", new ApnParameters("http://aliasredirect.net/proxy/ammsc", "209.29.243.151", 80));

        //TELUS (Report Count: 1, Last Report: 2014-06-19 00:07:55)
        put("311480||302220|TELUS", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //TESCO (Report Count: 32, Last Report: 2015-04-05 10:35:18)
        put("23410|TESCO|23410|TESCO", new ApnParameters("http://mmsc.mms.o2.co.uk:8002", "193.113.200.195", 8080));

        //Tesco Mobile (Report Count: 4, Last Report: 2015-03-20 22:48:00)
        put("27211|Tesco Mobile|27202|Tesco Mobile", new ApnParameters("http://10.1.11.68/servlets/mms", "10.1.11.19", 8080));

        //TextNow (Report Count: 1, Last Report: 2014-12-17 02:13:14)
        put("310000|TextNow|310000|TextNow", new ApnParameters("null", null, null));

        //TextNow (Report Count: 1, Last Report: 2015-01-28 15:53:57)
        put("310120|Sprint|31000|TextNow", new ApnParameters("http://mms.sprintpcs.com", "68.28.31.7", 80));

        //TextNow (Report Count: 1, Last Report: 2014-10-26 02:45:20)
        put("310120|Sprint|310120|TextNow", new ApnParameters("http://mms.sprintpcs.com", "68.28.31.7", 80));

        //TH 3G+ (Report Count: 2, Last Report: 2015-02-02 06:56:20)
        put("52000|TRUE-H|52000|TH 3G+", new ApnParameters("http://mms.trueh.com:8002/", "10.4.7.39", 8080));

        //TH GSM (Report Count: 5, Last Report: 2015-04-06 11:57:47)
        put("52003|AIS|52001|TH GSM", new ApnParameters("http://mms.ais.co.th", "203.170.229.34", 8080));

        //TH GSM (Report Count: 1, Last Report: 2014-11-30 03:56:04)
        put("52003||52001|TH GSM", new ApnParameters("http://mms.ais.co.th", "203.170.229.34", 8080));

        //thumbcellular (Report Count: 1, Last Report: 2015-01-30 20:30:33)
        put("311050|thumbcellular|31000|", new ApnParameters("http://mms.thumbcell.com/thumb/mms.php", null, null));

        //TIGO (Report Count: 12, Last Report: 2015-04-03 18:22:13)
        put("70402|TIGO|70402|TIGO", new ApnParameters("http://mms", "10.16.17.12", 8888));

        //TIGO (Report Count: 2, Last Report: 2015-03-19 19:03:40)
        put("708020|TIGO|70802|TIGO", new ApnParameters("http://mms", "10.16.17.12", 8888));

        //TIGO (Report Count: 2, Last Report: 2015-03-29 18:58:46)
        put("70802|TIGO|70802|TIGO", new ApnParameters("http://mms", "10.16.17.12", 8888));

        //TIGO (Report Count: 1, Last Report: 2014-11-21 20:23:55)
        put("74404|TIGO|74404|TIGO", new ApnParameters("http://mms", "10.16.17.12", 8888));

        //TIM (Report Count: 9, Last Report: 2015-03-21 07:21:30)
        put("72402|TIM|72402|TIM", new ApnParameters("http://mms.tim.br", "200.179.66.242", 8080));

        //TIM (Report Count: 2, Last Report: 2015-02-16 09:56:07)
        put("72403|TIM|72403|TIM", new ApnParameters("http://mms.tim.br", "200.179.66.242", 8080));

        //TIM (Report Count: 1, Last Report: 2015-02-14 12:02:30)
        put("72403|TIM|72404|TIM", new ApnParameters("http://mms.tim.br", "200.179.66.242", 8080));

        //TIM (Report Count: 9, Last Report: 2015-03-24 14:02:07)
        put("72404|TIM|72404|TIM", new ApnParameters("http://mms.tim.br", "200.179.66.242", 8080));

        //TIM 19 (Report Count: 1, Last Report: 2014-10-29 05:15:48)
        put("72403|TIM|72403|TIM 19", new ApnParameters("http://mms.tim.br", "200.179.66.242", 8080));

        //TIM 21 (Report Count: 1, Last Report: 2014-12-17 01:04:27)
        put("72402|TIM|72402|TIM 21", new ApnParameters("http://mms.tim.br", "200.179.66.242", 8080));

        //TIM 31 (Report Count: 2, Last Report: 2015-04-02 22:56:53)
        put("72402|TIM|72402|TIM 31", new ApnParameters("http://mms.tim.br/", "200.179.66.242", 8080));

        //TIM 41 (Report Count: 1, Last Report: 2014-12-01 07:56:47)
        put("72404|TIM|72404|TIM 41", new ApnParameters("http://mms.tim.br/", "200.179.66.242", 8080));

        //TIM 45 (Report Count: 1, Last Report: 2014-11-21 12:15:23)
        put("72404|TIM|72404|TIM 45", new ApnParameters("http://mms.tim.br/", "200.179.66.242", 8080));

        //TIM 91 (Report Count: 1, Last Report: 2014-09-29 21:03:24)
        put("72402||72402|TIM 91", new ApnParameters("http://mms.tim.br/", "200.179.66.242", 8080));

        //TIM BRASIL (Report Count: 1, Last Report: 2014-12-03 23:17:26)
        put("72404|TIM|72404|TIM BRASIL", new ApnParameters("http://mms.tim.br", "200.179.66.242", 8080));

        //Ting (Report Count: 1, Last Report: 2015-04-01 20:15:19)
        put("310260|Ting|310260|Ting", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //Ting (Report Count: 3, Last Report: 2015-03-11 16:30:42)
        put("310260||310260|Ting", new ApnParameters("http://wholesale.mmsmvno.com/mms/wapenc", null, null));

        //TMO (Report Count: 2, Last Report: 2015-01-27 19:23:44)
        put("310260||310260|TMO", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //TMO D (Report Count: 3, Last Report: 2015-03-23 10:24:15)
        put("26201|congstar|26201|TMO D", new ApnParameters("http://mms.t-mobile.de/servlets/mms", "172.28.23.131", 8008));

        //TN mobile (Report Count: 1, Last Report: 2015-02-14 13:19:26)
        put("64903|TN MOBILE|64903|TN mobile", new ApnParameters("http://www.mtcmobile.com.na/", "10.40.10.252", 80));

        //tomato (Report Count: 1, Last Report: 2014-10-21 13:03:47)
        put("21910|tomato|21910|tomato", new ApnParameters("http://mms.tomato.com.hr/servlets/mms", "212.91.99.91", 8080));

        //TPG (Report Count: 1, Last Report: 2014-07-16 06:43:24)
        put("50502|TPG|50502|TPG", new ApnParameters("http://mmsc.optus.com.au:8002/", "61.88.190.10", 8070));

        //TR TURKCELL (Report Count: 3, Last Report: 2015-03-24 22:56:10)
        put("28601||28601|TR TURKCELL", new ApnParameters("http://mms.turkcell.com.tr/servlets/mms", "212.252.169.217", 8080));

        //TracFone (Report Count: 495, Last Report: 2015-04-08 16:16:57)
        put("310000|TracFone|310000| ", new ApnParameters("http://mms.vzwreseller.com/servlets/mms", null, null));

        //Tracfone (Report Count: 83, Last Report: 2015-04-08 17:42:40)
        put("31000|Tracfone|310000|", new ApnParameters("http://mms.vzwreseller.com/servlets/mms", null, null));

        //Tracfone (Report Count: 28, Last Report: 2015-04-07 00:24:43)
        put("31000|Tracfone|31000|", new ApnParameters("http://mms.vzwreseller.com/servlets/mms", null, null));

        //TracFone (Report Count: 6, Last Report: 2015-03-29 04:41:59)
        put("310410||310410|TracFone", new ApnParameters("http://mms-tf.net", "mms3.tracfone.com", 80));

        //True Move (Report Count: 1, Last Report: 2015-03-16 13:39:00)
        put("52000||52000|True Move", new ApnParameters("http://mms.trueh.com:8002", "10.4.7.39", 8080));

        //TRUE-H (Report Count: 1, Last Report: 2014-07-19 10:41:10)
        put("52000|TRUE-H|52000|TRUE-H", new ApnParameters("http://mms.trueh.com:8002", "10.4.7.39", 8080));

        //TRUE-H (Report Count: 1, Last Report: 2015-03-22 02:58:29)
        put("52000|TRUE-H|52099|TRUE-H", new ApnParameters("http://mms.ais.co.th", "203.170.229.34", 8080));

        //True-H 2100 (Report Count: 1, Last Report: 2015-01-23 08:55:39)
        put("52004|TRUE-H|52004|True-H 2100", new ApnParameters("http://mms.trueh.com:8002", "10.4.7.39", 8080));

        //TSEL (Report Count: 1, Last Report: 2014-12-07 02:56:11)
        put("51010||51010|TSEL", new ApnParameters("http://mms.telkomsel.com", "10.1.89.150", 8000));

        //TSTT (Report Count: 3, Last Report: 2015-03-26 03:38:31)
        put("374129||37412|TSTT", new ApnParameters("http://192.168.210.104/mmrelay.app", "192.168.210.104", 8080));

        //Tuenti (Report Count: 2, Last Report: 2015-03-01 17:30:10)
        put("21405|Tuenti|21407|Tuenti", new ApnParameters("http://tuenti.com", "10.138.255.43", 8080));

        //TuneTalk (Report Count: 3, Last Report: 2014-12-25 04:04:08)
        put("50219|TuneTalk|50219|TuneTalk", new ApnParameters("http://mms.celcom.net.my", "10.128.1.242", 8080));

        //TUNTEL (Report Count: 1, Last Report: 2015-04-08 15:17:30)
        put("60502|TUNTEL|60502|TUNTEL", new ApnParameters("http://192.168.0.3:19090/was", "192.168.0.2", 8080));

        //TURKCELL (Report Count: 8, Last Report: 2015-03-01 18:43:08)
        put("28601||28601|TURKCELL", new ApnParameters("http://mms.turkcell.com.tr/servlets/mms", "212.252.169.217", 8080));

        //TUS (Report Count: 1, Last Report: 2014-12-22 20:25:36)
        put("29370||29370|TUS", new ApnParameters("http://mms.tusmobil.si:8002", "91.185.221.85", 8080));

        //TUSMOBIL (Report Count: 6, Last Report: 2015-01-31 10:33:31)
        put("29370|TUSMOBIL|29370|TUSMOBIL", new ApnParameters("http://mms.tusmobil.si:8002", "91.185.221.85", 8080));

        //TW Mobile (Report Count: 4, Last Report: 2015-03-15 12:53:41)
        put("46697||46697|TW Mobile", new ApnParameters("http://mms.catch.net.tw", "10.1.1.2", 80));

        //TWM (Report Count: 1, Last Report: 2014-12-12 06:05:06)
        put("46697||46697|TWM", new ApnParameters("http://mms.catch.net.tw", "10.1.1.2", 80));

        //U Mobile (Report Count: 11, Last Report: 2015-03-26 15:41:53)
        put("50218||50212|U Mobile", new ApnParameters("http://10.30.3.11/servlets/mms", "10.30.5.11", 8080));

        //U Mobile (Report Count: 46, Last Report: 2015-04-07 05:21:22)
        put("50218||50218|U Mobile", new ApnParameters("http://10.30.3.11/servlets/mms", "10.30.5.11", 8080));

        //U Mobile (Report Count: 1, Last Report: 2014-12-16 11:49:36)
        put("50219||50219|U Mobile", new ApnParameters("http://mms.celcom.net.my", "10.128.1.242", 8080));

        //U.S. Cellular (Report Count: 3, Last Report: 2015-03-21 16:31:47)
        put("11111|U.S. Cellular|31000|U.S. Cellular", new ApnParameters("http://mmsc1.uscc.net/mmsc/MMS", null, null));

        //U.S. Cellular (Report Count: 2, Last Report: 2015-03-28 02:13:15)
        put("11111|U.S. Cellular|311580|U.S. Cellular", new ApnParameters("http://mmsc1.uscc.net/mmsc/MMS", null, null));

        //U.S. Cellular (Report Count: 2, Last Report: 2015-02-18 23:03:33)
        put("11111|US Cellular|31000|U.S. Cellular", new ApnParameters("http://mmsc1.uscc.net/mmsc/MMS", null, null));

        //U.S. Cellular (Report Count: 7, Last Report: 2015-03-21 23:22:19)
        put("11111|US Cellular|311580|U.S. Cellular", new ApnParameters("http://mmsc1.uscc.net/mmsc/MMS", null, null));

        //U.S. Cellular (Report Count: 3, Last Report: 2015-03-12 01:44:19)
        put("310260||311580|U.S. Cellular", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //U.S. Cellular (Report Count: 1, Last Report: 2015-03-30 14:28:40)
        put("311002|US Cellular|31000|U.S. Cellular", new ApnParameters("http://mmsc1.uscc.net/mmsc/MMS", null, null));

        //U.S. Cellular (Report Count: 2, Last Report: 2014-12-21 07:14:09)
        put("311005|US Cellular|31000|U.S. Cellular", new ApnParameters("http://mmsc1.uscc.net/mmsc/MMS", null, null));

        //U.S. Cellular (Report Count: 1, Last Report: 2015-03-28 23:51:25)
        put("311008|US Cellular|31000|U.S. Cellular", new ApnParameters("http://mmsc1.uscc.net/mmsc/MMS", null, null));

        //U.S. Cellular (Report Count: 2, Last Report: 2014-12-07 03:06:06)
        put("311009|US Cellular|31000|U.S. Cellular", new ApnParameters("http://mmsc1.uscc.net/mmsc/MMS", null, null));

        //U.S. Cellular (Report Count: 1, Last Report: 2014-12-01 20:11:00)
        put("311009|US Cellular|310120|U.S. Cellular", new ApnParameters("http://mmsc1.uscc.net/mmsc/MMS", null, null));

        //U.S. Cellular (Report Count: 1, Last Report: 2015-02-14 22:21:53)
        put("31100|U.S. Cellular|311580|U.S. Cellular", new ApnParameters("http://mmsc1.uscc.net/mmsc/MMS", null, null));

        //U.S. Cellular (Report Count: 10, Last Report: 2015-02-25 21:53:20)
        put("31100|US Cellular|311580|U.S. Cellular", new ApnParameters("http://mmsc1.uscc.net/mmsc/MMS", null, null));

        //U.S. Cellular (Report Count: 1, Last Report: 2015-01-19 15:13:03)
        put("311220|U.S. Cellular|25500|U.S. Cellular", new ApnParameters("http://mmsc1.uscc.net/mmsc/MMS", null, null));

        //U.S. Cellular (Report Count: 6, Last Report: 2015-04-03 22:34:21)
        put("311220|U.S. Cellular|310000|U.S. Cellular", new ApnParameters("http://mmsc1.uscc.net/mmsc/MMS", null, null));

        //U.S. Cellular (Report Count: 39, Last Report: 2015-04-07 03:53:42)
        put("311220|U.S. Cellular|31000|U.S. Cellular", new ApnParameters("http://mmsc1.uscc.net/mmsc/MMS", null, null));

        //U.S. Cellular (Report Count: 2, Last Report: 2015-01-23 00:46:36)
        put("311220|U.S. Cellular|31099|U.S. Cellular", new ApnParameters("http://mmsc1.uscc.net/mmsc/MMS", null, null));

        //U.S. Cellular (Report Count: 1, Last Report: 2014-12-24 00:43:00)
        put("311220|U.S.Cellular|311480|U.S. Cellular", new ApnParameters("null", null, null));

        //U.S. Cellular (Report Count: 2, Last Report: 2015-03-29 13:57:36)
        put("311480|Verizon Wireless|311580|U.S. Cellular", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //U.S. Cellular (Report Count: 22, Last Report: 2015-04-03 21:50:55)
        put("311580|U.S. Cellular|310000|U.S. Cellular", new ApnParameters("http://mmsc1.uscc.net/mmsc/MMS", null, null));

        //U.S. Cellular (Report Count: 77, Last Report: 2015-04-06 17:44:04)
        put("311580|U.S. Cellular|31000|U.S. Cellular", new ApnParameters("http://mmsc1.uscc.net/mmsc/MMS", null, null));

        //U.S. Cellular (Report Count: 8, Last Report: 2015-04-06 14:17:11)
        put("311580|U.S. Cellular|31099|U.S. Cellular", new ApnParameters("http://mmsc1.uscc.net/mmsc/MMS", null, null));

        //U.S. Cellular (Report Count: 358, Last Report: 2015-04-08 00:43:26)
        put("311580|U.S. Cellular|311580|U.S. Cellular", new ApnParameters("http://mmsc1.uscc.net/mmsc/MMS", null, null));

        //U.S. Cellular (Report Count: 1, Last Report: 2014-09-29 03:52:08)
        put("311580|US Cellular|310000|U.S. Cellular", new ApnParameters("http://mmsc1.uscc.net/mmsc/MMS", null, null));

        //U.S. Cellular (Report Count: 32, Last Report: 2015-03-31 00:56:38)
        put("311580|US Cellular|31000|U.S. Cellular", new ApnParameters("http://mmsc1.uscc.net/mmsc/MMS", null, null));

        //U.S. Cellular (Report Count: 6, Last Report: 2015-03-23 19:01:39)
        put("311580|US Cellular|31099|U.S. Cellular", new ApnParameters("http://mmsc1.uscc.net/mmsc/MMS", null, null));

        //U.S. Cellular (Report Count: 413, Last Report: 2015-04-08 11:45:05)
        put("311580|US Cellular|311580|U.S. Cellular", new ApnParameters("http://mmsc1.uscc.net/mmsc/MMS", null, null));

        //U.S. Cellular® (Report Count: 1, Last Report: 2015-04-04 23:08:05)
        put("311580|U.S. Cellular|311220|U.S. Cellular®", new ApnParameters("http://mmsc1.uscc.net/mmsc/MMS", null, null));

        //UA-KYIVSTAR (Report Count: 2, Last Report: 2015-01-14 14:25:25)
        put("25503|KYIVSTAR|25503|UA-KYIVSTAR", new ApnParameters("http://mms.kyivstar.net", "10.10.10.10", 8080));

        //UCell (Report Count: 1, Last Report: 2015-04-01 17:06:16)
        put("43405|UCell|43405|UCell", new ApnParameters("http:a?? a?? mmsc:8002", "10.64.164.10", 8080));

        //Ufone (Report Count: 1, Last Report: 2014-08-22 15:07:34)
        put("41003|Ufone|41003|Ufone", new ApnParameters("http://10.81.6.11:8080", "10.81.6.33", 8000));

        //Ultra.me (Report Count: 1, Last Report: 2015-01-17 23:33:26)
        put("310260|Ultra.me|310260|Ultra.me", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //Ultra.me (Report Count: 94, Last Report: 2015-04-08 16:24:16)
        put("310260||310260|Ultra.me", new ApnParameters("http://wholesale.mmsmvno.com/mms/wapenc", null, null));

        //UNICEL (Report Count: 1, Last Report: 2014-11-09 18:48:38)
        put("310260|Verizon|310260|UNICEL", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //uninor (Report Count: 1, Last Report: 2015-03-22 16:32:08)
        put("405818|uninor|405818|uninor", new ApnParameters("http://10.58.2.120", "10.58.10.59", 8080));

        //UNINOR (Report Count: 4, Last Report: 2015-03-26 21:46:05)
        put("405876|uninor|405876|UNINOR", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //UNINOR (Report Count: 1, Last Report: 2015-01-16 00:09:51)
        put("405879|uninor|405879|UNINOR", new ApnParameters("http://10.58.2.120", "10.58.10.59", 8080));

        //uninor (Report Count: 1, Last Report: 2014-10-19 14:43:55)
        put("405929|uninor|405929|uninor", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Union Telephone (Report Count: 1, Last Report: 2014-10-19 04:09:38)
        put("310260||310020|Union Telephone", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //Union Wireless (Report Count: 4, Last Report: 2015-04-02 19:58:17)
        put("310020|Union Wireless|310020|Union Wireless", new ApnParameters("http://mms.unionwireless.com", null, null));

        //Univision Mobile (Report Count: 16, Last Report: 2015-03-31 04:06:01)
        put("310260||310260|Univision Mobile", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //Unknown (Report Count: 1, Last Report: 2014-12-02 19:52:12)
        put("25001||25001|Unknown", new ApnParameters("http://mmsc", "192.168.192.192", 8080));

        //US (Report Count: 1, Last Report: 2014-10-12 04:16:14)
        put("310260|Lycamobile|310260|US", new ApnParameters("http://metropcs.mmsmvno.com/mms/wapenc", null, null));

        //US (Report Count: 6, Last Report: 2015-01-18 22:44:05)
        put("310260|T-Mobile|310260|US", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //US (Report Count: 72, Last Report: 2015-04-05 13:16:29)
        put("310260||310260|US", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //US - Union Telephone (Report Count: 3, Last Report: 2015-02-13 21:06:49)
        put("310260||310020|US - Union Telephone", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //US Cellular (Report Count: 4, Last Report: 2015-04-05 01:09:32)
        put("11111|US Cellular|311580|US Cellular", new ApnParameters("http://mmsc1.uscc.net/mmsc/MMS", null, null));

        //US Cellular (Report Count: 1, Last Report: 2015-03-20 18:45:37)
        put("311580|U.S. Cellular|311580|US Cellular", new ApnParameters("http://mmsc1.uscc.net/mmsc/MMS", null, null));

        //US Cellular (Report Count: 1, Last Report: 2015-01-29 02:57:04)
        put("311580|US Cellular|123456|US Cellular", new ApnParameters("http://mmsc1.uscc.net/mmsc/MMS", null, null));

        //US Cellular (Report Count: 6, Last Report: 2014-12-02 14:45:09)
        put("311580|US Cellular|31000|US Cellular", new ApnParameters("http://mmsc1.uscc.net/mmsc/MMS", null, null));

        //US Cellular (Report Count: 29, Last Report: 2015-04-03 14:27:07)
        put("311580|US Cellular|311580|US Cellular", new ApnParameters("http://mmsc1.uscc.net/mmsc/MMS", null, null));

        //US PLATEAU (Report Count: 5, Last Report: 2014-12-14 02:55:40)
        put("310100||310100|US PLATEAU", new ApnParameters("http://mms", "172.23.253.206", 8080));

        //USA - CellularOne (Report Count: 2, Last Report: 2014-10-26 01:10:17)
        put("310260||310320|USA - CellularOne", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //USA - Commnet (Report Count: 2, Last Report: 2015-03-02 16:51:16)
        put("310260||311040|USA - Commnet", new ApnParameters("http://metropcs.mmsmvno.com/mms/wapenc", null, null));

        //Velcom (Report Count: 1, Last Report: 2014-10-25 12:58:23)
        put("25701|VELCOM|25701|Velcom", new ApnParameters("http://mmsc", "10.200.15.15", 8080));

        //Verizon (Report Count: 1, Last Report: 2014-09-06 22:34:05)
        put("204043|Verizon|310004|", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon (Report Count: 1, Last Report: 2014-12-21 18:38:55)
        put("20404|Verizon|31000|", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon (Report Count: 2, Last Report: 2014-12-22 00:39:52)
        put("20404|Verizon|311480|", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon (Report Count: 107, Last Report: 2015-04-07 20:22:37)
        put("310000|Verizon|31000| ", new ApnParameters("null", null, null));

        //Verizon (Report Count: 321, Last Report: 2015-04-08 10:23:55)
        put("310000|Verizon|311480| ", new ApnParameters("null", null, null));

        //Verizon (Report Count: 2, Last Report: 2015-03-20 00:33:05)
        put("310004|Verizon|310000|", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon (Report Count: 8, Last Report: 2015-04-06 22:40:06)
        put("310004|Verizon|31000|", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon (Report Count: 1, Last Report: 2015-02-21 02:28:07)
        put("310012|Verizon|31000|", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon (Report Count: 1, Last Report: 2015-03-23 01:37:43)
        put("310012|Verizon|311480| ", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon (Report Count: 1, Last Report: 2015-02-10 22:32:19)
        put("310012|Verizon|3167|", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon (Report Count: 3, Last Report: 2014-09-29 15:11:29)
        put("310090|Verizon|31001|", new ApnParameters("null", null, null));

        //Verizon (Report Count: 4, Last Report: 2014-12-23 19:23:04)
        put("310120|Verizon|31000|", new ApnParameters("http://mms.sprintpcs.com", "68.28.31.7", 80));

        //Verizon (Report Count: 10, Last Report: 2015-01-17 05:03:12)
        put("310120|Verizon|311480|", new ApnParameters("http://mms.sprintpcs.com", "68.28.31.7", 80));

        //Verizon (Report Count: 1, Last Report: 2015-01-15 21:36:23)
        put("310120|Verizon|311870|", new ApnParameters("http://mms.sprintpcs.com", "68.28.31.7", 80));

        //Verizon (Report Count: 1, Last Report: 2015-02-17 01:16:14)
        put("310150|Verizon|310410|", new ApnParameters("http://mmsc.aiowireless.net", "proxy.aiowireless.net", 80));

        //Verizon (Report Count: 17, Last Report: 2015-04-02 00:51:02)
        put("310260|Verizon|00000|", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //Verizon (Report Count: 1, Last Report: 2014-09-28 17:04:04)
        put("310260|Verizon|31000|", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //Verizon (Report Count: 9, Last Report: 2015-04-07 18:38:04)
        put("310260|Verizon|311480|", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //Verizon (Report Count: 1, Last Report: 2014-11-07 05:35:47)
        put("310410|Verizon|311480|", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon (Report Count: 8, Last Report: 2015-03-13 05:13:48)
        put("311480|Verizon|00000| ", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon (Report Count: 1, Last Report: 2014-09-18 21:45:26)
        put("311480|Verizon|310004|", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon (Report Count: 42, Last Report: 2015-04-02 23:18:08)
        put("311480|Verizon|31000| ", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon (Report Count: 4, Last Report: 2015-03-21 05:09:26)
        put("311480|Verizon|31001|", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon (Report Count: 243, Last Report: 2015-04-08 01:29:01)
        put("311480|Verizon|311480| ", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon (Report Count: 1, Last Report: 2014-11-23 00:08:43)
        put("311580|Verizon|311220|", new ApnParameters("http://mmsc1.uscc.net/mmsc/MMS", null, null));

        //Verizon (Report Count: 2, Last Report: 2015-03-12 14:36:35)
        put("311750|Verizon|311480|", new ApnParameters("null", null, null));

        //Verizon (Report Count: 1, Last Report: 2014-09-20 02:04:28)
        put("316010|Verizon|311480| ", new ApnParameters("null", null, null));

        //Verizon (Report Count: 3, Last Report: 2014-10-24 21:26:47)
        put("|Verizon|310004| ", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon (Report Count: 6, Last Report: 2015-03-14 21:37:50)
        put("|Verizon|31000|", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon (Report Count: 4, Last Report: 2015-03-19 18:55:58)
        put("|Verizon|31000| ", new ApnParameters("null", null, null));

        //Verizon (Report Count: 1, Last Report: 2014-12-25 22:25:12)
        put("|Verizon|31007|", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon (Report Count: 6, Last Report: 2015-02-28 04:25:43)
        put("|Verizon|311480|", new ApnParameters("null", null, null));

        //Verizon Wireless (Report Count: 1, Last Report: 2014-10-24 22:35:48)
        put("20404|Verizon Wireless|311480|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 2, Last Report: 2014-12-06 16:27:21)
        put("310002|Verizon Wireless|310120|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 9, Last Report: 2015-02-06 23:23:37)
        put("310002|Verizon Wireless|311480|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 1, Last Report: 2014-12-12 23:42:42)
        put("310003|Verizon Wireless|310012|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 3, Last Report: 2015-02-24 16:32:59)
        put("310003|Verizon Wireless|311480|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 3, Last Report: 2014-10-08 21:25:16)
        put("310004|Verizon Wireless|31000|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 3, Last Report: 2015-01-02 02:54:52)
        put("310004|Verizon Wireless|311480|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 16, Last Report: 2015-03-20 18:55:46)
        put("310004|Verizon|123456|Verizon Wireless", new ApnParameters("", null, null));

        //Verizon Wireless (Report Count: 164, Last Report: 2015-04-08 03:02:54)
        put("310004|Verizon|310000|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 1, Last Report: 2014-11-01 20:28:46)
        put("310004|Verizon|310004|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 75, Last Report: 2015-04-06 16:02:10)
        put("310004|Verizon|31000|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 1, Last Report: 2014-08-19 01:17:14)
        put("310004|Verizon|310012|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 1, Last Report: 2015-01-12 20:08:48)
        put("310004||311480|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 1, Last Report: 2014-08-05 21:47:17)
        put("310005|Verizon Wireless|000000|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 1, Last Report: 2015-01-03 00:41:28)
        put("310005|Verizon Wireless|310004|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 1, Last Report: 2015-01-29 19:15:39)
        put("310005|Verizon Wireless|31000|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 9, Last Report: 2015-03-22 23:50:25)
        put("310005|Verizon Wireless|311480|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 1, Last Report: 2014-12-08 17:26:00)
        put("310005||311480|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 2, Last Report: 2014-10-03 11:21:13)
        put("310006|Verizon Wireless|311480|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 1, Last Report: 2014-08-26 03:03:01)
        put("310006|Verizon|311480|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 1, Last Report: 2014-12-27 06:53:59)
        put("310007|Verizon Wireless|310004|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 1, Last Report: 2014-11-11 18:27:58)
        put("310007|Verizon Wireless|310012|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 2, Last Report: 2015-02-13 00:01:41)
        put("310007|Verizon Wireless|311480|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 1, Last Report: 2015-03-17 22:59:22)
        put("310007||310004|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 1, Last Report: 2014-12-25 16:49:04)
        put("310008|Verizon Wireless|31000|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 2, Last Report: 2014-12-01 00:37:50)
        put("310008|Verizon Wireless|310120|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 3, Last Report: 2014-10-28 11:38:40)
        put("310008|Verizon Wireless|311480|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 1, Last Report: 2014-12-14 01:37:56)
        put("310009|Verizon Wireless|310120|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 5, Last Report: 2015-02-25 21:14:53)
        put("310009|Verizon Wireless|311480|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 1, Last Report: 2015-04-08 11:25:01)
        put("310009||31000|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 2, Last Report: 2015-04-04 17:51:36)
        put("310009||311480|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 11, Last Report: 2015-04-04 23:43:36)
        put("31000|Verizon Wireless|310004|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 6, Last Report: 2015-02-13 08:16:44)
        put("31000|Verizon Wireless|31000|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 2, Last Report: 2014-10-18 01:54:22)
        put("31000|Verizon Wireless|310012|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 2, Last Report: 2014-10-26 14:42:20)
        put("31000|Verizon Wireless|310120|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 33, Last Report: 2015-03-20 17:28:40)
        put("31000|Verizon Wireless|311480|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 5, Last Report: 2015-01-10 15:24:35)
        put("31000||311480|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 6, Last Report: 2015-03-18 19:56:12)
        put("310012|Verizon Wireless|31000|Verizon Wireless", new ApnParameters("null", null, null));

        //Verizon Wireless (Report Count: 30, Last Report: 2015-03-21 17:20:54)
        put("310012|Verizon Wireless|311480|Verizon Wireless", new ApnParameters("null", null, null));

        //Verizon Wireless (Report Count: 1, Last Report: 2014-11-16 20:09:07)
        put("310012|Verizon|31000|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 1, Last Report: 2015-01-06 02:55:33)
        put("310012|Verizon|311480|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 1, Last Report: 2015-01-20 00:40:24)
        put("311480|Verizon Wireless|000000|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 68, Last Report: 2015-04-05 22:31:50)
        put("311480|Verizon Wireless|00000|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 1, Last Report: 2014-07-25 16:04:19)
        put("311480|Verizon Wireless|001001|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 109, Last Report: 2015-04-08 13:16:53)
        put("311480|Verizon Wireless|123456|Verizon Wireless", new ApnParameters("null", null, null));

        //Verizon Wireless (Report Count: 2, Last Report: 2015-03-17 20:04:27)
        put("311480|Verizon Wireless|310000| ", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 226, Last Report: 2015-04-08 15:22:48)
        put("311480|Verizon Wireless|310000|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 16, Last Report: 2015-03-19 15:49:25)
        put("311480|Verizon Wireless|310004|", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 1023, Last Report: 2015-04-08 15:16:29)
        put("311480|Verizon Wireless|310004|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 79, Last Report: 2015-04-04 18:30:41)
        put("311480|Verizon Wireless|31000| ", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 629, Last Report: 2015-04-08 02:48:27)
        put("311480|Verizon Wireless|31000|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 9, Last Report: 2015-04-04 17:40:27)
        put("311480|Verizon Wireless|310012|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 4, Last Report: 2015-01-23 19:23:42)
        put("311480|Verizon Wireless|31007|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 10, Last Report: 2015-03-11 23:06:54)
        put("311480|Verizon Wireless|3107|", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 325, Last Report: 2015-04-07 21:40:12)
        put("311480|Verizon Wireless|3107|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 1, Last Report: 2014-08-08 19:06:51)
        put("311480|Verizon Wireless|31099|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 38, Last Report: 2015-04-08 00:33:40)
        put("311480|Verizon Wireless|311480| ", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 3634, Last Report: 2015-04-08 15:31:10)
        put("311480|Verizon Wireless|311480|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 5, Last Report: 2015-02-24 03:15:33)
        put("311480|Verizon Wireless|311580|", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 1, Last Report: 2015-02-11 19:16:29)
        put("311480|Verizon Wireless|311580|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 18, Last Report: 2015-04-08 12:12:36)
        put("311480|Verizon Wireless|3167| ", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 179, Last Report: 2015-04-08 05:25:31)
        put("311480|Verizon Wireless|3167|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 1, Last Report: 2015-02-09 17:44:48)
        put("311480|Verizon Wireless||Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 1, Last Report: 2014-07-14 12:53:36)
        put("311480|Verizon|00000|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 1, Last Report: 2014-10-15 21:14:51)
        put("311480|Verizon|123456|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 2, Last Report: 2014-11-13 18:24:35)
        put("311480|Verizon|310000|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 5, Last Report: 2015-04-06 13:55:11)
        put("311480|Verizon|310004|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 82, Last Report: 2015-04-07 02:53:56)
        put("311480|Verizon|31000|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 1, Last Report: 2014-09-11 20:29:43)
        put("311480|Verizon|31072|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 1, Last Report: 2014-09-06 03:08:17)
        put("311480|Verizon|31099|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 6177, Last Report: 2015-04-08 16:47:25)
        put("311480|Verizon|311480|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 61, Last Report: 2015-04-08 00:38:46)
        put("311480||00000|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 2, Last Report: 2015-01-29 01:18:05)
        put("311480||123456|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 1, Last Report: 2015-04-03 14:25:21)
        put("311480||26803|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 34, Last Report: 2015-04-08 03:55:00)
        put("311480||310000|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 480, Last Report: 2015-04-08 17:11:27)
        put("311480||310004|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 466, Last Report: 2015-04-08 17:10:27)
        put("311480||31000|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 3, Last Report: 2015-01-03 18:59:40)
        put("311480||310012|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 34, Last Report: 2015-04-05 01:31:02)
        put("311480||3107|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 324, Last Report: 2015-04-08 15:42:54)
        put("311480||311480|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 33, Last Report: 2015-04-08 12:45:42)
        put("311480||3167|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 5, Last Report: 2015-01-26 07:42:44)
        put("31148|Verizon Wireless|310004|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 3, Last Report: 2015-01-22 18:42:19)
        put("31148|Verizon Wireless|31000|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 1, Last Report: 2014-09-26 00:56:07)
        put("|Verizon|31000|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 1, Last Report: 2015-04-05 10:58:30)
        put("|Verizon|311480|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Verizon Wireless (Report Count: 1, Last Report: 2015-02-16 21:53:07)
        put("||311480|Verizon Wireless", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //VF-RO (Report Count: 1, Last Report: 2015-01-27 16:08:20)
        put("22601|Vodafone RO|22601|VF-RO", new ApnParameters("http://multimedia/servlets/mms", "193.230.161.231", 8080));

        //Viaero (Report Count: 1, Last Report: 2014-11-21 18:03:44)
        put("310450|VIAERO Wireless|310450|Viaero", new ApnParameters("http://mms.viaero.com", "10.168.3.23", 9401));

        //Viaero (Report Count: 3, Last Report: 2015-02-10 22:42:31)
        put("310450|Viaero|310450|Viaero", new ApnParameters("http://mms.viaero.com", "10.168.3.23", 9401));

        //Viaero (Report Count: 15, Last Report: 2015-03-25 18:21:01)
        put("310450||310450|Viaero", new ApnParameters("http://mms.viaero.com", "10.168.3.23", 9401));

        //Viaero Wireless (Report Count: 1, Last Report: 2014-11-02 03:18:16)
        put("310450|Viaero|310450|Viaero Wireless", new ApnParameters("http://mms.viero.com", "10.168.3.23", 9401));

        //Videotron (Report Count: 3, Last Report: 2015-02-01 20:03:54)
        put("302500|Videotron|302500|Videotron", new ApnParameters("http://media.videotron.com/", "10.208.89.17", 8080));

        //Videotron (Report Count: 111, Last Report: 2015-04-06 18:07:31)
        put("302500|Videotron|302720|Videotron", new ApnParameters("http://media.videotron.com", null, null));

        //Videotron (Report Count: 201, Last Report: 2015-04-06 13:14:56)
        put("302500|Vidéotron|302500|Videotron", new ApnParameters("http://media.videotron.com", null, null));

        //Videotron PRTNR (Report Count: 2, Last Report: 2014-12-01 17:05:59)
        put("302500|Videotron|302720|Videotron PRTNR", new ApnParameters("http://media.videotron.com", null, null));

        //Videotron PRTNR1 (Report Count: 1, Last Report: 2014-12-23 17:34:10)
        put("302500|Videotron|302720|Videotron PRTNR1", new ApnParameters("http://media.videotron.com", null, null));

        //Vietnam Mobile Telecom Services Company (Report Count: 1, Last Report: 2015-01-30 08:03:30)
        put("45201||45201|Vietnam Mobile Telecom Services Company", new ApnParameters("http://203.162.21.114/mmsc", "203.162.21.114", 3130));

        //Vietnam Telecoms Services Company (Report Count: 1, Last Report: 2014-09-26 10:22:14)
        put("45202|VINAPHONE|45202|Vietnam Telecoms Services Company", new ApnParameters("http://mms.vinaphone.com.vn", "10.1.10.46", 8000));

        //Vietnam Telecoms Services Company (Report Count: 1, Last Report: 2014-12-18 07:41:59)
        put("45202||45202|Vietnam Telecoms Services Company", new ApnParameters("http://mms.vinaphone.com.vn", "10.1.10.46", 8080));

        //Vietnamobile (Report Count: 3, Last Report: 2014-11-25 02:39:14)
        put("45205|Vietnamobile|45205|Vietnamobile", new ApnParameters("http://203.162.21.114/mmsc", "203.162.21.114", 3130));

        //Viettel (Report Count: 5, Last Report: 2015-01-06 02:59:01)
        put("45204|VIETTEL|45204|Viettel", new ApnParameters("http://mms.viettelmobile.com.vn/mms/wapenc", "192.168.233.10", 8080));

        //Viettel Mobile (Report Count: 9, Last Report: 2015-03-17 02:58:09)
        put("45204|VIETTEL|45204|Viettel Mobile", new ApnParameters("http://mms.viettelmobile.com.vn/mms/wapenc", "192.168.233.10", 8080));

        //VINAPHONE (Report Count: 4, Last Report: 2015-03-09 12:52:25)
        put("45202|VINAPHONE|45202|VINAPHONE", new ApnParameters("http://mms.vinaphone.com.vn", "10.1.10.46", 8000));

        //Vinaphone (Report Count: 1, Last Report: 2015-03-31 13:35:12)
        put("45202||45202|Vinaphone", new ApnParameters("http://mms.vinaphone.com.vn", "10.1.10.46", 8000));

        //Vip MK (Report Count: 2, Last Report: 2014-11-08 16:56:17)
        put("29403|Vip MK|29403|Vip MK ", new ApnParameters("http://mmsc.vipoperator.com.mk", "78.40.0.1", 8080));

        //Vip SRB (Report Count: 1, Last Report: 2014-10-30 16:01:03)
        put("22005|Vip SRB|22005|Vip SRB", new ApnParameters("http://mmsc.vipmobile.rs/", "212.15.182.82", 8080));

        //Vip SRB (Report Count: 1, Last Report: 2015-02-25 21:18:53)
        put("22005|Vip SRB||", new ApnParameters("http://mms.vipmobile.rs", null, null));

        //Vip SRB (Report Count: 3, Last Report: 2015-03-23 10:57:36)
        put("22005|Vip SRB|22005|Vip SRB ", new ApnParameters("http://mms.vipmobile.rs/", null, null));

        //Virgin (Report Count: 76, Last Report: 2015-04-06 07:40:24)
        put("20823|Virgin|20810|Virgin", new ApnParameters("http://virginmms.fr", "10.6.10.1", 8080));

        //Virgin (Report Count: 3, Last Report: 2015-01-27 11:47:48)
        put("20823|Virgin|20820|Virgin", new ApnParameters("http://virginmms.fr", "10.6.10.1", 8080));

        //Virgin (Report Count: 5, Last Report: 2015-03-07 21:53:04)
        put("23430|Virgin|23430|Virgin", new ApnParameters("http://mms.virginmobile.co.uk:8002", "193.30.166.2", 8080));

        //Virgin (Report Count: 63, Last Report: 2015-03-27 18:04:20)
        put("23430||23430|Virgin", new ApnParameters("http://mms.virginmobile.co.uk:8002", "193.30.166.2", 8080));

        //virgin (Report Count: 6, Last Report: 2015-03-12 18:29:37)
        put("23430||23433|virgin", new ApnParameters("http://mms.virginmobile.co.uk:8002", "193.30.166.2", 8080));

        //VIRGIN (Report Count: 928, Last Report: 2015-04-08 17:00:18)
        put("302610||302610|VIRGIN", new ApnParameters("http://mms.bell.ca/mms/wapenc", null, null));

        //Virgin (Report Count: 2, Last Report: 2014-12-24 08:51:06)
        put("50502|Virgin Mobile|50502|Virgin", new ApnParameters("http://mmsc.optus.com.au:8002/", "61.88.190.10", 8070));

        //Virgin Mobile (Report Count: 1, Last Report: 2014-12-10 09:46:35)
        put("50502|Virgin Mobile|50502|Virgin Mobile", new ApnParameters("http://mmsc.optus.com.au:8002/", "61.88.190.10", 8070));

        //Virgin Mobile (Report Count: 6, Last Report: 2015-04-07 19:20:24)
        put("310000|Virgin Mobile|310000|Virgin Mobile", new ApnParameters("http://mms.plspictures.com", "68.28.31.7", 80));

        //Virgin Mobile (Report Count: 1, Last Report: 2014-10-23 02:04:39)
        put("310120|Sprint|31000|Virgin Mobile", new ApnParameters("http://mmsc.vmobl.com:8088/mms?type=null", "68.28.31.7", 80));

        //Virgin Mobile (Report Count: 1, Last Report: 2014-07-02 08:28:22)
        put("310120|Sprint|310120|Virgin Mobile", new ApnParameters("http://mmsc.vmobl.com:8088/mms?type=null", "68.28.31.7", 80));

        //Virgin Mobile (Report Count: 2, Last Report: 2014-12-25 18:08:56)
        put("31012|Sprint|310120|Virgin Mobile", new ApnParameters("http://mmsc.vmobl.com:8088/mms?type=null", "68.28.31.7", 80));

        //Virgin Mobile (Report Count: 276, Last Report: 2015-04-08 12:36:05)
        put("311490|Virgin Mobile|311490|Virgin Mobile", new ApnParameters("http://mmsc.vmobl.com:8088/mms?", null, null));

        //Virgin Mobile (Report Count: 1, Last Report: 2015-01-22 13:43:52)
        put("|Sprint|310120|Virgin Mobile", new ApnParameters("http://mmsc.vmobl.com:8088/mms?type=null", "68.28.31.7", 80));

        //Virgin Mobile US (Report Count: 4, Last Report: 2015-02-07 01:45:58)
        put("310120|Sprint|311490|Virgin Mobile US", new ApnParameters("http://mmsc.vmobl.com:8088/mms?type=null", "68.28.31.7", 80));

        //VIVA (Report Count: 5, Last Report: 2015-03-25 14:33:42)
        put("41904|VIVA|41904|VIVA", new ApnParameters("http://172.16.128.80:38090/was", "172.16.128.228", 8080));

        //VIVA KW (Report Count: 1, Last Report: 2015-04-01 06:51:13)
        put("41904|VIVA|41904|VIVA KW", new ApnParameters("http://172.16.128.80:38090/was", "172.16.128.228", 8080));

        //VIVO (Report Count: 2, Last Report: 2015-02-07 05:53:17)
        put("72406|VIVO|72406|VIVO", new ApnParameters("http://termnat.vivomms.com.br:8088/mms", "200.142.130.104", 80));

        //VIVO (Report Count: 4, Last Report: 2015-03-17 15:21:29)
        put("72410|VIVO|72410|VIVO", new ApnParameters("http://termnat.vivomms.com.br:8088/mms", "200.142.130.104", 80));

        //VIVO (Report Count: 9, Last Report: 2015-04-06 12:18:41)
        put("72411|VIVO|72411|VIVO", new ApnParameters("http://termnat.vivomms.com.br:8088/mms", "200.142.130.104", 80));

        //VIVO (Report Count: 3, Last Report: 2015-02-03 20:40:17)
        put("72411||72411|VIVO", new ApnParameters("http://termnat.vivomms.com.br:8088/mms", "200.142.130.104", 80));

        //VIVO,CLARO BR (Report Count: 1, Last Report: 2015-03-04 15:12:36)
        put("72405|Claro BR|72405|VIVO,CLARO BR", new ApnParameters("http://termnat.vivomms.com.br:8088/mms", "200.142.130.104", 80));

        //VIVO,Oi (Report Count: 1, Last Report: 2014-12-31 18:01:54)
        put("72406|VIVO|72406|VIVO,Oi", new ApnParameters("http://termnat.vivomms.com.br:8088/mms", "200.142.130.104", 80));

        //VMS(Mobiphone) (Report Count: 1, Last Report: 2014-09-12 15:14:35)
        put("45201|MOBIFONE|45201|VMS(Mobiphone)", new ApnParameters("http://203.162.21.114/mmsc", "203.162.21.114", 8080));

        //VMS(Mobiphone) (Report Count: 1, Last Report: 2014-09-06 05:27:59)
        put("45201|Mobifone|45201|VMS(Mobiphone)", new ApnParameters("http://203.162.21.114/mmsc", "203.162.21.114", 3130));

        //VN MobiFone (Report Count: 3, Last Report: 2014-11-12 00:56:54)
        put("45201|MOBIFONE|45201|VN MobiFone", new ApnParameters("http://203.162.21.114/mmsc", "203.162.21.114", 8080));

        //VN MOBIFONE (Report Count: 5, Last Report: 2015-03-08 00:29:00)
        put("45201||45201|VN MOBIFONE", new ApnParameters("http://203.162.21.114/mmsc", "203.162.21.114", 3130));

        //VN MobiFone (Report Count: 1, Last Report: 2014-10-18 10:41:29)
        put("45201||45201|VN MobiFone", new ApnParameters("http://203.162.21.114/mmsc", "203.162.21.114", 8080));

        //VN VINAPHONE (Report Count: 2, Last Report: 2015-03-06 18:28:07)
        put("45202|VINAPHONE|45202|VN VINAPHONE", new ApnParameters("http://mms.vinaphone.com.vn", "10.1.10.46", 8000));

        //VN VINAPHONE (Report Count: 2, Last Report: 2015-02-07 00:20:32)
        put("45202||45202|VN VINAPHONE", new ApnParameters("http://mms.vinaphone.com.vn", "10.1.10.46", 8000));

        //Vodacom SA (Report Count: 1, Last Report: 2015-01-27 17:33:38)
        put("65501||65501|Vodacom SA", new ApnParameters("http://mms.cmobile.co.za", "196.31.116.250", 8080));

        //VodaCom-MZ (Report Count: 1, Last Report: 2015-03-06 12:16:45)
        put("64304|Vodacom|64304|VodaCom-MZ", new ApnParameters("http://mms.vm.co.mz", "10.201.47.14", 8080));

        //VodaCom-SA (Report Count: 1, Last Report: 2014-11-17 18:38:18)
        put("310260||65501|VodaCom-SA", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //VodaCom-SA (Report Count: 7, Last Report: 2015-03-19 07:36:35)
        put("65501||65501|VodaCom-SA", new ApnParameters("http://mmsc.vodacom4me.co.za", "196.6.128.13", 8080));

        //Vodacom-SA (Report Count: 2, Last Report: 2015-03-29 19:27:20)
        put("65501||65501|Vodacom-SA", new ApnParameters("http://mmsc.vodacom4me.co.za/", "196.6.128.13", 8080));

        //VodaCom-SA (Report Count: 1, Last Report: 2015-04-03 13:28:14)
        put("65507| Cell C |65501|VodaCom-SA", new ApnParameters("http://mms.cmobile.co.za", "196.31.116.250", 8080));

        //VodaCom-SA (Report Count: 1, Last Report: 2014-12-27 10:47:24)
        put("65507|Cell C|65501|VodaCom-SA", new ApnParameters("http://mms.cmobile.co.za", "196.31.116.250", 8080));

        //Vodafone (Report Count: 1, Last Report: 2015-01-11 09:34:54)
        put("40411|Vodafone|40411|Vodafone", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Vodafone (Report Count: 5, Last Report: 2015-03-06 01:11:34)
        put("40413|Vodafone IN|40407|Vodafone", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Vodafone (Report Count: 9, Last Report: 2014-12-12 18:47:18)
        put("40420|Vodafone IN|40420|Vodafone", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Vodafone (Report Count: 1, Last Report: 2015-01-24 14:25:47)
        put("40427|Vodafone IN|40427|Vodafone", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Vodafone (Report Count: 1, Last Report: 2014-12-16 17:35:27)
        put("40430|Vodafone IN|40430|Vodafone", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Vodafone (Report Count: 1, Last Report: 2015-02-09 18:43:29)
        put("40446|Vodafone IN|40419|Vodafone", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Vodafone (Report Count: 1, Last Report: 2015-03-17 13:49:49)
        put("40460|Vodafone IN|40460|Vodafone", new ApnParameters("http://10.4.42.21:8002/", "10.4.42.15", 8080));

        //Vodafone (Report Count: 1, Last Report: 2015-03-19 15:43:29)
        put("40460||40460|Vodafone", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Vodafone (Report Count: 1, Last Report: 2014-12-25 07:50:08)
        put("40470|airtel|40470|Vodafone", new ApnParameters("http://100.1.201.171:10021/mmsc", "100.1.201.172", 8799));

        //Vodafone (Report Count: 1, Last Report: 2015-02-18 10:37:35)
        put("40484|Vodafone IN|40407|Vodafone", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Vodafone (Report Count: 8, Last Report: 2015-03-06 11:14:39)
        put("40486|Vodafone IN|40445|Vodafone", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Vodafone (Report Count: 1, Last Report: 2015-02-09 14:25:03)
        put("40486||40445|Vodafone", new ApnParameters("http://mms1.live.vodafone.in/mms/", null, null));

        //Vodafone (Report Count: 1, Last Report: 2014-12-19 08:08:10)
        put("40488|Vodafone IN|40414|Vodafone", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Vodafone (Report Count: 1, Last Report: 2014-08-07 05:20:37)
        put("40488|Vodafone IN|40488|Vodafone", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Vodafone (Report Count: 2, Last Report: 2015-03-28 17:53:46)
        put("40566|Vodafone IN|40497|Vodafone", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Vodafone (Report Count: 1, Last Report: 2015-01-26 14:18:57)
        put("40566||40566|Vodafone", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //VODAFONE (Report Count: 1, Last Report: 2015-01-19 14:38:18)
        put("405756|Vodafone IN|40478|VODAFONE", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Vodafone (Report Count: 2, Last Report: 2015-01-02 07:46:12)
        put("50503||50503|Vodafone", new ApnParameters("http://pxt.vodafone.net.au/pxtsend", "10.202.2.60", 8080));

        //Vodafone (Report Count: 1, Last Report: 2015-02-20 16:17:00)
        put("60202|vodafone|60202|Vodafone", new ApnParameters("http://mms.vodafone.com.eg/servlets/mms", "163.121.178.2", 8080));

        //Vodafone (Report Count: 1, Last Report: 2015-04-01 06:14:44)
        put("50503||50503|Vodafone ", new ApnParameters("http://pxt.vodafone.net.au", "10.202.2.60", 8080));

        //vodafone AU (Report Count: 1, Last Report: 2014-12-04 23:02:08)
        put("310260||50503|vodafone AU", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //Vodafone AU (Report Count: 1, Last Report: 2014-12-11 03:34:26)
        put("50503|Lebara|50503|Vodafone AU", new ApnParameters("http://purtona.mms/send", "10.202.2.20", 8080));

        //vodafone AU (Report Count: 2, Last Report: 2015-01-08 05:56:44)
        put("50503|Lebara|50503|vodafone AU", new ApnParameters("http://purtona.mms/mmssend", "10.202.2.20", 8080));

        //vodafone AU (Report Count: 1, Last Report: 2014-12-25 07:54:47)
        put("50503|PennyTel|50503|vodafone AU", new ApnParameters("http://pxt.vodafone.net.au/pxtsend", "10.202.2.60", 8080));

        //Vodafone AU (Report Count: 2, Last Report: 2015-02-03 05:14:51)
        put("50503|Think|50503|Vodafone AU", new ApnParameters("http://pxt.vodafone.net.au/pxtsend", "10.202.2.60", 8080));

        //Vodafone AU (Report Count: 1, Last Report: 2014-09-22 11:15:52)
        put("50503|TransACT|50503|Vodafone AU", new ApnParameters("http://pxt.vodafone.net.au/pxtsend", "10.202.2.60", 8080));

        //vodafone AU (Report Count: 2, Last Report: 2015-02-20 22:45:49)
        put("50503|Vodafone AU|50503|vodafone AU", new ApnParameters("http://pxt.vodafone.net.au/pxtsend", "10.202.2.60", 8080));

        //Vodafone AU (Report Count: 177, Last Report: 2015-04-05 01:02:42)
        put("50503||50503|Vodafone AU", new ApnParameters("http://pxt.vodafone.net.au/pxtsend", "10.202.2.60", 8080));

        //Vodafone AU,null (Report Count: 2, Last Report: 2015-04-08 00:27:08)
        put("50503||50503|Vodafone AU,null", new ApnParameters("http://pxt.vodafone.net.au/pxtsend", "10.202.2.60", 8080));

        //Vodafone AU,vodafone AU (Report Count: 1, Last Report: 2014-10-04 11:30:42)
        put("50503||50503|Vodafone AU,vodafone AU", new ApnParameters("http://pxt.vodafone.net.au/pxtsend", "10.202.2.60", 8080));

        //Vodafone Australia (Report Count: 3, Last Report: 2015-04-07 08:44:43)
        put("50503||50503|Vodafone Australia", new ApnParameters("http://pxt.vodafone.net.au/pxtsend", "10.202.2.60", 8080));

        //Vodafone Chennai (Report Count: 1, Last Report: 2015-03-12 14:22:23)
        put("40484|Vodafone IN|40484|Vodafone Chennai", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Vodafone CZ (Report Count: 6, Last Report: 2015-04-07 12:10:40)
        put("23003|Vodafone CZ|23003|Vodafone CZ", new ApnParameters("http://mms", "10.11.10.111", 80));

        //Vodafone CZ (Report Count: 1, Last Report: 2014-11-25 17:45:24)
        put("23003||23003|Vodafone CZ", new ApnParameters("http://mms", "10.11.10.111", 80));

        //Vodafone Delhi (Report Count: 1, Last Report: 2015-02-27 18:01:30)
        put("40411|Vodafone IN|40411|Vodafone Delhi", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //vodafone ES (Report Count: 13, Last Report: 2015-04-08 11:14:13)
        put("21401||21401|vodafone ES", new ApnParameters("http://mmsc.vodafone.es/servlets/mms", "212.73.32.10", 80));

        //Vodafone Fiji (Report Count: 3, Last Report: 2015-02-28 19:47:48)
        put("54201|Vodafone|54201|Vodafone Fiji", new ApnParameters("http://pxt.vodafone.net.fj/pxtsend", "10.202.2.40", 8080));

        //VODAFONE GR (Report Count: 1, Last Report: 2015-01-06 17:47:01)
        put("20205|vodafone GR|20205|VODAFONE GR", new ApnParameters("http://mms.vodafone.gr", "213.249.19.49", 5080));

        //vodafone HU (Report Count: 1, Last Report: 2015-03-10 16:38:19)
        put("21670||21670|vodafone HU", new ApnParameters("http://mms.vodafone.hu/servlets/mms", "80.244.97.2", 8080));

        //vodafone IE (Report Count: 16, Last Report: 2015-04-03 20:53:11)
        put("27201|vodafone IE|27201|vodafone IE", new ApnParameters("http://www.vodafone.ie/mms", "10.24.59.200", 80));

        //Vodafone IN (Report Count: 1, Last Report: 2015-02-16 19:34:50)
        put("310260||40484|Vodafone IN", new ApnParameters("http://mms.msg.eng.t-mobile.com/mms/wapenc", null, null));

        //Vodafone IN (Report Count: 2, Last Report: 2014-11-26 06:57:55)
        put("40401|Vodafone IN|40401|Vodafone IN", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Vodafone In (Report Count: 1, Last Report: 2015-01-28 02:42:46)
        put("40404|!dea|40411|Vodafone In", new ApnParameters("http://10.4.42.21:8002", "10.4.42.15", 8080));

        //Vodafone IN (Report Count: 2, Last Report: 2014-12-01 05:06:17)
        put("40405|Hutch|40405|Vodafone IN", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Vodafone IN (Report Count: 1, Last Report: 2015-03-25 08:20:14)
        put("40405|Vodafone IN|40405|Vodafone IN", new ApnParameters("http://mms1.live.vodafone.in/mms/", null, null));

        //Vodafone In (Report Count: 38, Last Report: 2015-04-03 10:36:28)
        put("40405|Vodafone IN|40405|Vodafone In", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Vodafone IN (Report Count: 2, Last Report: 2015-01-10 08:46:48)
        put("40405||40405|Vodafone IN", new ApnParameters("http://mms1.live.vodafone.in/mms/", null, null));

        //Vodafone IN (Report Count: 6, Last Report: 2015-04-04 03:48:03)
        put("40411|Hutch|40411|Vodafone IN", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Vodafone IN (Report Count: 1, Last Report: 2014-10-01 20:19:47)
        put("40411|Vodafone IN|40401|Vodafone IN", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Vodafone IN (Report Count: 82, Last Report: 2015-04-07 17:10:23)
        put("40411|Vodafone IN|40411|Vodafone IN", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Vodafone IN (Report Count: 1, Last Report: 2015-03-12 15:01:02)
        put("40411|Vodafone IN|40420|Vodafone IN", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Vodafone IN (Report Count: 1, Last Report: 2014-10-25 13:12:14)
        put("40411|uninor|405752|Vodafone IN", new ApnParameters("http://mms1.live.vodafone.in/mms/", null, null));

        //VODAFONE IN (Report Count: 1, Last Report: 2015-01-21 10:22:03)
        put("40411||40411|VODAFONE IN", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Vodafone In (Report Count: 2, Last Report: 2015-02-16 13:29:29)
        put("40412|!dea|40411|Vodafone In", new ApnParameters("http://10.4.42.21:8002/", "10.4.42.15", 8080));

        //Vodafone IN (Report Count: 1, Last Report: 2015-03-06 17:15:58)
        put("40413|Vodafone IN|40407|Vodafone IN", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Vodafone IN (Report Count: 14, Last Report: 2015-04-02 01:52:57)
        put("40413|Vodafone IN|40413|Vodafone IN", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Vodafone IN (Report Count: 1, Last Report: 2014-06-13 06:44:52)
        put("40413|Vodafone IN|40427|Vodafone IN", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Vodafone IN (Report Count: 1, Last Report: 2015-02-20 11:25:27)
        put("40413|Vodafone IN|40443|Vodafone IN", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Vodafone IN (Report Count: 20, Last Report: 2015-04-04 05:25:25)
        put("40415|Vodafone IN|40415|Vodafone IN", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Vodafone IN (Report Count: 3, Last Report: 2015-03-23 06:02:40)
        put("40420|Hutch|40420|Vodafone IN", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Vodafone IN (Report Count: 2, Last Report: 2015-02-04 09:12:17)
        put("40420|International|40420|Vodafone IN", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //VODAFONE IN (Report Count: 7, Last Report: 2015-04-05 01:53:50)
        put("40420|Orange|40420|VODAFONE IN", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //VODAFONE IN (Report Count: 1, Last Report: 2014-10-14 16:49:23)
        put("40420|Vodafone - Mumbai|40420|VODAFONE IN", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //VODAFONE IN (Report Count: 1, Last Report: 2015-03-31 07:31:28)
        put("40420|Vodafone IN|40420|VODAFONE IN", new ApnParameters("http://100.1.201.171:10021/mmsc/", "100.1.201.172", 8799));

        //Vodafone IN (Report Count: 109, Last Report: 2015-03-31 10:51:56)
        put("40420|Vodafone IN|40420|Vodafone IN", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Vodafone IN (Report Count: 2, Last Report: 2015-03-13 16:06:02)
        put("40420|Vodafone IN|40427|Vodafone IN", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Vodafone IN (Report Count: 1, Last Report: 2014-09-08 14:21:02)
        put("40427|BPL Mobile|40427|Vodafone IN", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Vodafone IN (Report Count: 1, Last Report: 2014-12-31 12:55:22)
        put("40427|Hutch|40427|Vodafone IN", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Vodafone IN (Report Count: 49, Last Report: 2015-04-04 14:58:50)
        put("40427|Vodafone IN|40427|Vodafone IN", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Vodafone IN (Report Count: 4, Last Report: 2015-01-02 15:23:02)
        put("40427|Vodafone IN|405751|Vodafone IN", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Vodafone IN (Report Count: 1, Last Report: 2014-12-25 16:54:30)
        put("40427||40427|Vodafone IN", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Vodafone IN (Report Count: 1, Last Report: 2014-07-14 11:41:02)
        put("40430|Hutch|40430|Vodafone IN", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Vodafone IN (Report Count: 11, Last Report: 2015-04-03 06:00:07)
        put("40430|Vodafone IN|40430|Vodafone IN", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Vodafone IN (Report Count: 1, Last Report: 2014-12-01 18:36:14)
        put("40443|BPL Mobile|40443|Vodafone IN", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Vodafone IN (Report Count: 1, Last Report: 2015-04-06 01:52:27)
        put("40443|Hutch|40443|Vodafone IN", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Vodafone IN (Report Count: 18, Last Report: 2015-04-05 14:25:31)
        put("40443|Vodafone IN|40443|Vodafone IN", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Vodafone IN (Report Count: 3, Last Report: 2015-02-01 08:53:07)
        put("40443|Vodafone IN|40484|Vodafone IN", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Vodafone IN (Report Count: 1, Last Report: 2015-02-24 14:28:40)
        put("40443|Vodafone IN|40486|Vodafone IN", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Vodafone IN (Report Count: 2, Last Report: 2015-03-07 10:04:38)
        put("40443||40443|Vodafone IN", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Vodafone IN (Report Count: 2, Last Report: 2015-02-11 12:17:31)
        put("40446|Vodafone IN|40446|Vodafone IN", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Vodafone IN (Report Count: 1, Last Report: 2015-01-14 23:26:41)
        put("40460|Vodafone IN|40401|Vodafone IN", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Vodafone IN (Report Count: 1, Last Report: 2015-03-29 14:21:50)
        put("40460|Vodafone IN|40405|Vodafone IN", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Vodafone IN (Report Count: 8, Last Report: 2015-03-02 09:44:41)
        put("40460|Vodafone IN|40460|Vodafone IN", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Vodafone IN (Report Count: 2, Last Report: 2014-12-26 07:29:37)
        put("40484|Vodafone IN|40443|Vodafone IN", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Vodafone IN (Report Count: 19, Last Report: 2015-04-01 07:13:41)
        put("40484|Vodafone IN|40484|Vodafone IN", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Vodafone IN (Report Count: 1, Last Report: 2014-07-03 07:18:46)
        put("40484||40484|Vodafone IN", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Vodafone IN (Report Count: 1, Last Report: 2014-12-01 13:26:50)
        put("40485|Reliance|40567|Vodafone IN", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Vodafone IN (Report Count: 1, Last Report: 2014-12-29 12:45:54)
        put("40486|Vodafone IN|40445|Vodafone IN", new ApnParameters("http://mms1.hutchworld.co.in/mms/", null, null));

        //Vodafone IN (Report Count: 12, Last Report: 2015-04-03 06:41:24)
        put("40486|Vodafone IN|40486|Vodafone IN", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Vodafone IN (Report Count: 1, Last Report: 2014-11-29 08:59:53)
        put("40488|Vodafone IN|40401|Vodafone IN", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Vodafone IN (Report Count: 4, Last Report: 2015-03-11 09:50:42)
        put("40488|Vodafone IN|40488|Vodafone IN", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Vodafone IN (Report Count: 4, Last Report: 2015-03-28 15:44:25)
        put("40566|Vodafone IN|40566|Vodafone IN", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //VODAFONE IN (Report Count: 1, Last Report: 2015-01-20 11:56:03)
        put("40567|AIRCEL|40411|VODAFONE IN", new ApnParameters("http://mms1.live.vodafone.in/mms/", null, null));

        //Vodafone IN (Report Count: 1, Last Report: 2014-10-03 21:30:43)
        put("40567|AIRCEL|40567|Vodafone IN", new ApnParameters("http://mms1.live.vodafone.in/mms/", null, null));

        //Vodafone In (Report Count: 1, Last Report: 2015-02-14 07:31:42)
        put("40567|Vodafone IN|40430|Vodafone In", new ApnParameters("http://mms1.live.vodafone.in/mms/", null, null));

        //Vodafone IN (Report Count: 10, Last Report: 2015-04-04 14:51:34)
        put("40567|Vodafone IN|40567|Vodafone IN", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //VODAFONE IN (Report Count: 1, Last Report: 2015-02-03 01:54:52)
        put("40567|airtel|40411|VODAFONE IN", new ApnParameters("http://mms1.live.vodafone.in/mms/", null, null));

        //Vodafone IN (Report Count: 1, Last Report: 2015-02-27 08:31:13)
        put("40567||405753|Vodafone IN", new ApnParameters("http://mms1.live.vodafone.in/mms/", null, null));

        //Vodafone IN (Report Count: 1, Last Report: 2015-03-26 16:14:25)
        put("405752|Vodafone IN|40405|Vodafone IN", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Vodafone IN (Report Count: 2, Last Report: 2015-01-07 17:20:58)
        put("405752|Vodafone IN|405752|Vodafone IN", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Vodafone IN (Report Count: 8, Last Report: 2015-03-21 16:16:11)
        put("405756|Vodafone IN|405756|Vodafone IN", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Vodafone IN (Report Count: 1, Last Report: 2014-11-25 06:04:56)
        put("405800|Aircel|40566|Vodafone IN", new ApnParameters("http://100.1.201.171:10021/mmsc", "100.1.201.172", 8799));

        //Vodafone IN (Report Count: 1, Last Report: 2015-01-06 07:15:35)
        put("405932|Videocon|405752|Vodafone IN", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //vodafone IT (Report Count: 30, Last Report: 2015-04-06 21:10:07)
        put("22210||22210|vodafone IT", new ApnParameters("http://mms.vodafone.it/servlets/mms", "10.128.224.10", 80));

        //Vodafone Maharashtra (Report Count: 1, Last Report: 2014-10-01 06:41:46)
        put("40427|Vodafone IN|40427|Vodafone Maharashtra", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Vodafone Mumbai (Report Count: 1, Last Report: 2015-02-14 07:29:07)
        put("40420|Vodafone IN|40420|Vodafone Mumbai", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //vodafone NL (Report Count: 1, Last Report: 2014-10-25 15:15:20)
        put("20404|vodafone NL|310004|", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //vodafone NL (Report Count: 13, Last Report: 2015-03-05 20:40:09)
        put("20404||20404|vodafone NL", new ApnParameters("http://mmsc.mms.vodafone.nl", "192.168.251.150", 8799));

        //vodafone NZ (Report Count: 1, Last Report: 2014-10-24 06:13:15)
        put("53001|vodafone NZ|53001|vodafone NZ", new ApnParameters("http://pxt.vodafone.net.nz/pxtsend", "172.30.38.3", 8080));

        //vodafone P (Report Count: 142, Last Report: 2015-04-07 19:47:07)
        put("26801||26801|vodafone P", new ApnParameters("http://mms.vodafone.pt/servlets/mms", "iproxy.vodafone.pt", 80));

        //Vodafone Punjab (Report Count: 3, Last Report: 2015-03-21 09:40:49)
        put("40488|Vodafone IN|40488|Vodafone Punjab", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Vodafone Qatar (Report Count: 7, Last Report: 2015-03-31 02:48:32)
        put("42702|Vodafone Qatar|42702|Vodafone Qatar", new ApnParameters("http://mms.vodafone.com.qa/mmsc", "10.101.97.102", 80));

        //Vodafone Qatar (Report Count: 2, Last Report: 2014-10-30 08:39:23)
        put("42702||42702|Vodafone Qatar", new ApnParameters("http://mms.vodafone.com.qa/mmsc", "10.101.97.102", 80));

        //Vodafone RO (Report Count: 20, Last Report: 2015-03-27 13:54:10)
        put("22601|Vodafone RO|22601|Vodafone RO", new ApnParameters("http://multimedia/servlets/mms", "193.230.161.231", 8080));

        //Vodafone T.N. (Report Count: 3, Last Report: 2015-03-11 09:08:40)
        put("40443|Vodafone IN|40443|Vodafone T.N.", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //VODAFONE TR (Report Count: 1, Last Report: 2014-12-02 09:38:37)
        put("28602||28602|VODAFONE TR", new ApnParameters("http://217.31.233.18:6001/MM1Servlet", "217.31.233.18", 9401));

        //vodafone UK (Report Count: 1, Last Report: 2015-03-14 13:54:32)
        put("23415|Lebara|23415|vodafone UK", new ApnParameters("http://mms.lebara.co.uk/servlets/mms", "212.183.137.12", 8799));

        //vodafone UK (Report Count: 7, Last Report: 2015-03-29 10:22:33)
        put("23415|TalkTalk|23415|vodafone UK", new ApnParameters("http://mms.talktalk.co.uk/servlets/mms", "212.183.137.12", 8799));

        //vodafone UK (Report Count: 14, Last Report: 2015-04-01 20:29:00)
        put("23415|Talkmobile|23415|vodafone UK", new ApnParameters("http://mms.talkmobile.co.uk/servlets/mms", "212.183.137.12", 8799));

        //vodafone UK (Report Count: 2, Last Report: 2015-03-11 18:35:54)
        put("23415|vodafone UK|23415|vodafone UK", new ApnParameters("http://mms.vodafone.co.uk/servlets/mms", "212.183.137.12", 8799));

        //vodafone UK (Report Count: 281, Last Report: 2015-04-07 17:24:52)
        put("23415||23415|vodafone UK", new ApnParameters("http://mms.vodafone.co.uk/servlets/mms", "212.183.137.12", 8799));

        //Vodafone W.B. & A&N (Report Count: 1, Last Report: 2015-03-05 06:37:25)
        put("40567|Vodafone IN|40567|Vodafone W.B. & A&N", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //Vodafone.de (Report Count: 1, Last Report: 2015-02-20 16:18:59)
        put("20404|Verizon|26202|Vodafone.de", new ApnParameters("http://mms.vtext.com/servlets/mms", null, null));

        //Vodafone.de (Report Count: 1, Last Report: 2015-03-19 06:11:44)
        put("20810||26202|Vodafone.de", new ApnParameters("http://mms1", "10.151.0.1", 8080));

        //Vodafone.de (Report Count: 31, Last Report: 2015-04-02 13:38:50)
        put("26202|1&1|26202|Vodafone.de", new ApnParameters("http://139.7.24.1/servlets/mms", "139.7.29.17", 80));

        //Vodafone.de (Report Count: 2, Last Report: 2015-02-08 18:32:20)
        put("26202|BILDmobil|26202|Vodafone.de", new ApnParameters("http://139.7.24.1/servlets/mms", "139.7.29.17", 80));

        //Vodafone.de (Report Count: 1, Last Report: 2015-01-06 13:13:45)
        put("26202|Business|26202|Vodafone.de", new ApnParameters("http://139.7.24.1/servlets/mms", "139.7.29.17", 80));

        //Vodafone.de (Report Count: 3, Last Report: 2015-02-17 18:32:28)
        put("26202|DeutschlandSIM|26202|Vodafone.de", new ApnParameters("http://139.7.24.1/servlets/mms", "139.7.29.17", 80));

        //Vodafone.de (Report Count: 3, Last Report: 2015-04-02 13:34:13)
        put("26202|FYVE|26202|Vodafone.de", new ApnParameters("http://139.7.24.1/servlets/mms", "139.7.29.17", 80));

        //Vodafone.de (Report Count: 2, Last Report: 2015-02-08 16:34:10)
        put("26202|ROSSMANN mobil|26202|Vodafone.de", new ApnParameters("http://139.7.24.1/servlets/mms", "139.7.29.17", 80));

        //Vodafone.de (Report Count: 1, Last Report: 2015-01-01 10:14:22)
        put("26202|Talkline|26202|Vodafone.de", new ApnParameters("http://139.7.24.1/servlets/mms", "139.7.29.17", 80));

        //Vodafone.de (Report Count: 1, Last Report: 2015-03-01 15:48:13)
        put("26202|Telco|26202|Vodafone.de", new ApnParameters("http://139.7.24.1/servlets/mms", "139.7.29.17", 80));

        //Vodafone.de (Report Count: 1, Last Report: 2015-03-11 22:47:06)
        put("26202|Vodafone.de|26202|Vodafone.de", new ApnParameters("http://139.7.24.1/servlets/mms", "139.7.29.17", 80));

        //Vodafone.de (Report Count: 4, Last Report: 2015-02-23 22:16:14)
        put("26202|Willkommen|26202|Vodafone.de", new ApnParameters("http://139.7.24.1/servlets/mms", "139.7.29.17", 80));

        //Vodafone.de (Report Count: 1, Last Report: 2015-02-16 07:37:07)
        put("26202|callmobile.de|26202|Vodafone.de", new ApnParameters("http://139.7.24.1/servlets/mms", "139.7.29.17", 80));

        //Vodafone.de (Report Count: 1, Last Report: 2015-03-26 16:17:11)
        put("26202|discoTEL|26202|Vodafone.de", new ApnParameters("http://139.7.24.1/servlets/mms", "139.7.29.17", 80));

        //Vodafone.de (Report Count: 1, Last Report: 2014-11-18 16:42:27)
        put("26202|helloMobil|26202|Vodafone.de", new ApnParameters("http://139.7.24.1/servlets/mms", "139.7.29.17", 80));

        //Vodafone.de (Report Count: 3, Last Report: 2015-04-02 11:54:28)
        put("26202|klarmobil|26202|Vodafone.de", new ApnParameters("http://139.7.24.1/servlets/mms", "139.7.29.17", 80));

        //Vodafone.de (Report Count: 1, Last Report: 2014-12-09 11:47:43)
        put("26202|mobilcom debitel|26202|Vodafone.de", new ApnParameters("http://139.7.24.1/servlets/mms", "139.7.29.17", 80));

        //Vodafone.de (Report Count: 14, Last Report: 2015-04-01 10:37:30)
        put("26202|mobilcom-debitel|26202|Vodafone.de", new ApnParameters("http://139.7.24.1/servlets/mms", "139.7.29.17", 80));

        //Vodafone.de (Report Count: 1, Last Report: 2015-03-30 14:41:10)
        put("26202|otelo.de|26202|Vodafone.de", new ApnParameters("http://139.7.24.1/servlets/mms", "139.7.29.17", 80));

        //Vodafone.de (Report Count: 157, Last Report: 2015-04-08 14:31:45)
        put("26202||26202|Vodafone.de", new ApnParameters("http://139.7.24.1/servlets/mms", "139.7.29.17", 80));

        //Vodafone.de,null (Report Count: 1, Last Report: 2015-01-18 13:55:43)
        put("26202||26202|Vodafone.de,null", new ApnParameters("http://139.7.24.1/servlets/mms", "139.7.29.17", 80));

        //VODAFONEGR (Report Count: 2, Last Report: 2015-01-30 20:32:43)
        put("20205|vodafone GR|20205|VODAFONEGR", new ApnParameters("http://mms.vodafone.gr", "213.249.19.49", 5080));

        //VODAFONEIN (Report Count: 2, Last Report: 2015-03-21 12:29:54)
        put("40411|Vodafone IN|40411|VODAFONEIN", new ApnParameters("http://mms1.live.vodafone.in/mms/", "10.10.1.100", 9401));

        //VOXmobile (Report Count: 1, Last Report: 2014-10-25 14:20:27)
        put("27099|Orange|27099|VOXmobile", new ApnParameters("http://mms.orange.lu", "212.88.139.44", 8080));

        //Warid (Report Count: 3, Last Report: 2015-03-16 03:25:26)
        put("41007|Warid|41007|Warid", new ApnParameters("http://10.4.0.132/servlets/mms", "10.4.2.1", 8080));

        //Warid (Report Count: 1, Last Report: 2014-11-12 22:31:09)
        put("41007||41007|Warid", new ApnParameters("http://10.4.0.132/servlets/MMS", "10.4.2.1", 8080));

        //WaridTel (Report Count: 5, Last Report: 2015-03-23 18:44:02)
        put("41007|Warid|41007|WaridTel", new ApnParameters("http://10.4.0.132/servlets/MMS", "10.4.2.1", 8080));

        //West Central Wireless (Report Count: 1, Last Report: 2014-10-13 00:26:18)
        put("310180|West Central|310180|West Central Wireless", new ApnParameters("http://mms.wcc.net", "209.55.70.246", 80));

        //Wilkes (Report Count: 1, Last Report: 2015-02-25 19:23:07)
        put("310260||310260|Wilkes", new ApnParameters("http://metropcs.mmsmvno.com/mms/wapenc", null, null));

        //Willkommen (Report Count: 1, Last Report: 2015-01-07 16:33:12)
        put("26202|Willkommen|26202|Willkommen", new ApnParameters("http://139.7.24.1/servlets/mms", "139.7.29.17", 80));

        //Willkommen (Report Count: 2, Last Report: 2015-02-07 15:24:25)
        put("26207||26207|Willkommen", new ApnParameters("http://10.81.0.7:8002", "82.113.100.5", 8080));

        //WIN (Report Count: 1, Last Report: 2015-02-01 17:24:38)
        put("25001|MTS RUS|25032|WIN", new ApnParameters("http://mmsc", "192.168.192.192", 8080));

        //WIND (Report Count: 1, Last Report: 2014-12-08 11:58:01)
        put("22288||22288|WIND", new ApnParameters("http://mms.wind.it", "212.245.244.100", 8080));

        //WIND (Report Count: 22, Last Report: 2015-04-02 19:14:00)
        put("302490||302490|WIND", new ApnParameters("http://mms.windmobile.ca", "74.115.197.70", 8080));

        //WSM-DIGICEL (Report Count: 1, Last Report: 2015-01-13 03:13:47)
        put("54900|DIGICEL|54900|WSM-DIGICEL", new ApnParameters("http://mms.digicelsamoa.net:8990", "10.148.122.12", 8080));

        //WTF (Report Count: 2, Last Report: 2015-03-09 19:37:19)
        put("26803|WTF|26803|WTF", new ApnParameters("http://mmsc:10021/mmsc", "62.169.66.5", 8799));

        //XL (Report Count: 4, Last Report: 2015-03-07 14:07:11)
        put("51008|AXIS+|51011|XL", new ApnParameters("http://mmsc.axis", "10.8.3.8", 8080));

        //XL (Report Count: 2, Last Report: 2014-10-10 13:35:55)
        put("51008|AXIS|51008|XL", new ApnParameters("http://mmsc.axis", "10.8.3.8", 8080));

        //XL (Report Count: 27, Last Report: 2015-04-06 08:03:06)
        put("51011|Axis|51011|XL", new ApnParameters("http://mmc.xl.net.id/servlets/mms", "202.152.240.50", 8080));

        //XL (Report Count: 161, Last Report: 2015-04-08 14:54:45)
        put("51011|XL Axiata|51011|XL", new ApnParameters("http://mmc.xl.net.id/servlets/mms", "202.152.240.50", 8080));

        //XL (Report Count: 1, Last Report: 2015-02-25 04:33:46)
        put("51011|XL STAR|51011|XL", new ApnParameters("http://mmc.xl.net.id/servlets/mms", "202.152.240.50", 8080));

        //XL (Report Count: 11, Last Report: 2015-03-04 08:41:52)
        put("51011|XL|51011|XL", new ApnParameters("http://mmc.xl.net.id/servlets/mms", "202.152.240.50", 8080));

        //XL (Report Count: 1, Last Report: 2014-12-10 09:37:41)
        put("51011||51011|XL", new ApnParameters("http://mmsc.xl.net.id/servlets/mms", "202.152.240.50", 8080));

        //XL Axiata (Report Count: 14, Last Report: 2015-04-08 05:53:51)
        put("51011|XL Axiata|51011|XL Axiata", new ApnParameters("http://mmc.xl.net.id/servlets/mms", "202.152.240.50", 8080));

        //XL Axiata (Report Count: 1, Last Report: 2015-02-05 10:39:42)
        put("51011|XL Axiata||", new ApnParameters("http://mmc.xl.net.id/servlets/mms", "202.152.240.50", 8080));

        //XL Axiata (Report Count: 3, Last Report: 2015-03-17 07:58:58)
        put("51011||51011|XL Axiata", new ApnParameters("http://10.17.93.103:8080", "10.17.27.250", 8080));

        //XL IND (Report Count: 5, Last Report: 2015-01-17 04:11:12)
        put("51011|XL Axiata|51011|XL IND", new ApnParameters("http://mmc.xl.net.id/servlets/mms", "202.152.240.50", 8080));

        //XL STAR (Report Count: 1, Last Report: 2014-12-05 04:56:13)
        put("51011|XL STAR|51011|XL STAR", new ApnParameters("http://mmsc.xl.net.id/servlets/mms", "202.152.240.50", 8080));

        //Y!mobile (Report Count: 4, Last Report: 2015-03-20 10:15:00)
        put("44000|Y!mobile|44000|Y!mobile", new ApnParameters("http://mms.emx.ymobile1.ne.jp", "mmsproxy.emx.ymobile1.ne.jp", 8080));

        //yallo (Report Count: 1, Last Report: 2015-03-09 15:05:47)
        put("22802|yallo|22802|yallo", new ApnParameters("http://mmsc.sunrise.ch", "212.35.34.75", 8080));

        //YES OPTUS (Report Count: 2, Last Report: 2014-10-09 07:08:15)
        put("50502|TPG|50502|YES OPTUS", new ApnParameters("http://mmsc.optus.com.au:8002/", "61.88.190.10", 8070));

        //YES OPTUS (Report Count: 1, Last Report: 2015-03-09 07:05:10)
        put("50502|Virgin Mobile|50502|YES OPTUS", new ApnParameters("http://mmsc.optus.com.au:8002/", "61.88.190.10", 8070));

        //YES OPTUS (Report Count: 13, Last Report: 2015-03-29 08:35:47)
        put("50502|YES OPTUS|50502|YES OPTUS", new ApnParameters("http://mmsc.optus.com.au:8002/", "61.88.190.10", 8070));

        //YES OPTUS (Report Count: 3, Last Report: 2014-10-28 10:25:42)
        put("50502|amaysim|50502|YES OPTUS", new ApnParameters("http://mmsc.optus.com.au:8002/", "61.88.190.10", 8070));

        //YES OPTUS (Report Count: 3, Last Report: 2014-10-28 08:02:15)
        put("50502||50502|YES OPTUS", new ApnParameters("http://mmsc.optus.com.au:8002/", "61.88.190.10", 8070));

        //YouPhone (Report Count: 8, Last Report: 2015-03-30 07:36:58)
        put("42514|YouPhone|42501|YouPhone", new ApnParameters("http://192.168.220.15/servlets/mms", null, null));

        //YouPhone IL (Report Count: 1, Last Report: 2014-12-24 19:39:22)
        put("42514|YouPhone|42503|YouPhone IL", new ApnParameters("http://192.168.220.15/servlets/mms", null, null));

        //yourfone.de (Report Count: 1, Last Report: 2015-03-24 06:29:48)
        put("26203|yourfone.de|26203|yourfone.de", new ApnParameters("http://mms/eplus/", "212.23.97.153", 5080));

        //yourfone.de (Report Count: 1, Last Report: 2015-03-01 12:20:11)
        put("26203||26203|yourfone.de", new ApnParameters("http://mms/eplus/", "212.23.97.153", 5080));

        //Zact Mobile (Report Count: 1, Last Report: 2014-11-05 12:11:45)
        put("310000|Zact Mobile|310000|Zact Mobile", new ApnParameters("null", null, null));

        //Zain JO (Report Count: 2, Last Report: 2014-12-06 12:47:18)
        put("41601|zain JO|41601|Zain JO", new ApnParameters("http://mms.jo.zain.com/", "192.168.55.10", 80));

        //zain JO (Report Count: 2, Last Report: 2015-01-19 11:35:58)
        put("41601|zain JO|41601|zain JO", new ApnParameters("http://mms.jo.zain.com", "192.168.55.10", 80));

        //zain KW (Report Count: 13, Last Report: 2015-03-20 08:13:15)
        put("41902|zain KW|41902|zain KW", new ApnParameters("http://mms.zain", "176.0.0.65", 8080));

        //ZONG (Report Count: 1, Last Report: 2015-02-20 14:32:37)
        put("41004|Verizon|41004|ZONG", new ApnParameters("http://10.81.6.11:8080", "10.81.6.33", 8000));

        //ZONG (Report Count: 15, Last Report: 2015-03-23 03:37:13)
        put("41004|ZONG|41004|ZONG", new ApnParameters("http://10.81.6.11:8080", "10.81.6.33", 8000));

        //Zong (Report Count: 1, Last Report: 2015-03-03 20:01:26)
        put("41004|ZONG|41004|Zong", new ApnParameters("http://mmstelenor", "172.18.19.11", 8080));

        //Zong (Report Count: 1, Last Report: 2015-03-05 13:42:45)
        put("41004|Zong-Man Utd|41004|Zong", new ApnParameters("http://10.81.6.11:8080", "10.81.6.33", 8000));

        //£airtel (Report Count: 1, Last Report: 2015-01-26 11:31:31)
        put("40402|airtel|40402|£airtel", new ApnParameters("http://100.1.201.171:10021/mmsc", "100.1.201.172", 8799));

        //£airtel (Report Count: 1, Last Report: 2015-01-15 07:07:16)
        put("40440|airtel|40440|£airtel", new ApnParameters("http://100.1.201.171:10021/mmsc", "100.1.201.172", 8799));

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
