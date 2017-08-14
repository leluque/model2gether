package br.com.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class RequestHelper {

    private static final String locationService = "http://www.geoplugin.net/json.gp?ip=";
    private static final Map<String, String> countryLanguage = new HashMap(210);

    static {
        countryLanguage.put("ZA", "af");
        countryLanguage.put("ET", "am");
        countryLanguage.put("AE", "ar");
        countryLanguage.put("BH", "ar");
        countryLanguage.put("DZ", "ar");
        countryLanguage.put("EG", "ar");
        countryLanguage.put("IQ", "ar");
        countryLanguage.put("JO", "ar");
        countryLanguage.put("KW", "ar");
        countryLanguage.put("LB", "ar");
        countryLanguage.put("LY", "ar");
        countryLanguage.put("MA", "ar");
        countryLanguage.put("CL", "ar");
        countryLanguage.put("OM", "ar");
        countryLanguage.put("QA", "ar");
        countryLanguage.put("SA", "ar");
        countryLanguage.put("SY", "ar");
        countryLanguage.put("TN", "ar");
        countryLanguage.put("YE", "ar");
        countryLanguage.put("IN", "as");
        countryLanguage.put("AZ", "az");
        countryLanguage.put("AZ", "az");
        countryLanguage.put("RU", "ba");
        countryLanguage.put("BY", "be");
        countryLanguage.put("BG", "bg");
        countryLanguage.put("BD", "bn");
        countryLanguage.put("IN", "bn");
        countryLanguage.put("CN", "bo");
        countryLanguage.put("FR", "br");
        countryLanguage.put("BA", "bs");
        countryLanguage.put("BA", "bs");
        countryLanguage.put("ES", "ca");
        countryLanguage.put("FR", "co");
        countryLanguage.put("CZ", "cs");
        countryLanguage.put("GB", "cy");
        countryLanguage.put("DK", "da");
        countryLanguage.put("AT", "de");
        countryLanguage.put("CH", "de");
        countryLanguage.put("DE", "de");
        countryLanguage.put("LI", "de");
        countryLanguage.put("LU", "de");
        countryLanguage.put("DE", "ds");
        countryLanguage.put("MV", "dv");
        countryLanguage.put("GR", "el");
        countryLanguage.put("29", "en");
        countryLanguage.put("AU", "en");
        countryLanguage.put("BZ", "en");
        countryLanguage.put("CA", "en");
        countryLanguage.put("GB", "en");
        countryLanguage.put("IE", "en");
        countryLanguage.put("IN", "en");
        countryLanguage.put("JM", "en");
        countryLanguage.put("MY", "en");
        countryLanguage.put("NZ", "en");
        countryLanguage.put("PH", "en");
        countryLanguage.put("SG", "en");
        countryLanguage.put("TT", "en");
        countryLanguage.put("US", "en");
        countryLanguage.put("ZA", "en");
        countryLanguage.put("ZW", "en");
        countryLanguage.put("AR", "es");
        countryLanguage.put("BO", "es");
        countryLanguage.put("CL", "es");
        countryLanguage.put("CO", "es");
        countryLanguage.put("CR", "es");
        countryLanguage.put("DO", "es");
        countryLanguage.put("EC", "es");
        countryLanguage.put("ES", "es");
        countryLanguage.put("GT", "es");
        countryLanguage.put("HN", "es");
        countryLanguage.put("MX", "es");
        countryLanguage.put("NI", "es");
        countryLanguage.put("PA", "es");
        countryLanguage.put("PE", "es");
        countryLanguage.put("PR", "es");
        countryLanguage.put("PY", "es");
        countryLanguage.put("SV", "es");
        countryLanguage.put("US", "es");
        countryLanguage.put("UY", "es");
        countryLanguage.put("VE", "es");
        countryLanguage.put("EE", "et");
        countryLanguage.put("ES", "eu");
        countryLanguage.put("IR", "fa");
        countryLanguage.put("FI", "fi");
        countryLanguage.put("PH", "fi");
        countryLanguage.put("FO", "fo");
        countryLanguage.put("BE", "fr");
        countryLanguage.put("CA", "fr");
        countryLanguage.put("CH", "fr");
        countryLanguage.put("FR", "fr");
        countryLanguage.put("LU", "fr");
        countryLanguage.put("MC", "fr");
        countryLanguage.put("NL", "fy");
        countryLanguage.put("IE", "ga");
        countryLanguage.put("GB", "gd");
        countryLanguage.put("ES", "gl");
        countryLanguage.put("FR", "gs");
        countryLanguage.put("IN", "gu");
        countryLanguage.put("NG", "ha");
        countryLanguage.put("IL", "he");
        countryLanguage.put("IN", "hi");
        countryLanguage.put("BA", "hr");
        countryLanguage.put("HR", "hr");
        countryLanguage.put("DE", "hs");
        countryLanguage.put("HU", "hu");
        countryLanguage.put("AM", "hy");
        countryLanguage.put("ID", "id");
        countryLanguage.put("NG", "ig");
        countryLanguage.put("CN", "ii");
        countryLanguage.put("IS", "is");
        countryLanguage.put("CH", "it");
        countryLanguage.put("IT", "it");
        countryLanguage.put("CA", "iu");
        countryLanguage.put("CA", "iu");
        countryLanguage.put("JP", "ja");
        countryLanguage.put("GE", "ka");
        countryLanguage.put("KZ", "kk");
        countryLanguage.put("GL", "kl");
        countryLanguage.put("KH", "km");
        countryLanguage.put("IN", "kn");
        countryLanguage.put("IN", "ko");
        countryLanguage.put("KR", "ko");
        countryLanguage.put("KG", "ky");
        countryLanguage.put("LU", "lb");
        countryLanguage.put("LA", "lo");
        countryLanguage.put("LT", "lt");
        countryLanguage.put("LV", "lv");
        countryLanguage.put("NZ", "mi");
        countryLanguage.put("MK", "mk");
        countryLanguage.put("IN", "ml");
        countryLanguage.put("MN", "mn");
        countryLanguage.put("CN", "mn");
        countryLanguage.put("CA", "mo");
        countryLanguage.put("IN", "mr");
        countryLanguage.put("BN", "ms");
        countryLanguage.put("MY", "ms");
        countryLanguage.put("MT", "mt");
        countryLanguage.put("NO", "nb");
        countryLanguage.put("NP", "ne");
        countryLanguage.put("BE", "nl");
        countryLanguage.put("NL", "nl");
        countryLanguage.put("NO", "nn");
        countryLanguage.put("ZA", "ns");
        countryLanguage.put("FR", "oc");
        countryLanguage.put("IN", "or");
        countryLanguage.put("IN", "pa");
        countryLanguage.put("PL", "pl");
        countryLanguage.put("AF", "pr");
        countryLanguage.put("AF", "ps");
        countryLanguage.put("BR", "pt");
        countryLanguage.put("PT", "pt");
        countryLanguage.put("GT", "qu");
        countryLanguage.put("BO", "qu");
        countryLanguage.put("EC", "qu");
        countryLanguage.put("PE", "qu");
        countryLanguage.put("CH", "rm");
        countryLanguage.put("RO", "ro");
        countryLanguage.put("RU", "ru");
        countryLanguage.put("RW", "rw");
        countryLanguage.put("RU", "sa");
        countryLanguage.put("IN", "sa");
        countryLanguage.put("FI", "se");
        countryLanguage.put("NO", "se");
        countryLanguage.put("SE", "se");
        countryLanguage.put("LK", "si");
        countryLanguage.put("SK", "sk");
        countryLanguage.put("SI", "sl");
        countryLanguage.put("NO", "sm");
        countryLanguage.put("SE", "sm");
        countryLanguage.put("NO", "sm");
        countryLanguage.put("SE", "sm");
        countryLanguage.put("FI", "sm");
        countryLanguage.put("FI", "sm");
        countryLanguage.put("AL", "sq");
        countryLanguage.put("BA", "sr");
        countryLanguage.put("CS", "sr");
        countryLanguage.put("ME", "sr");
        countryLanguage.put("RS", "sr");
        countryLanguage.put("BA", "sr");
        countryLanguage.put("CS", "sr");
        countryLanguage.put("ME", "sr");
        countryLanguage.put("RS", "sr");
        countryLanguage.put("FI", "sv");
        countryLanguage.put("SE", "sv");
        countryLanguage.put("KE", "sw");
        countryLanguage.put("SY", "sy");
        countryLanguage.put("IN", "ta");
        countryLanguage.put("IN", "te");
        countryLanguage.put("TJ", "tg");
        countryLanguage.put("TH", "th");
        countryLanguage.put("TM", "tk");
        countryLanguage.put("ZA", "tn");
        countryLanguage.put("TR", "tr");
        countryLanguage.put("RU", "tt");
        countryLanguage.put("DZ", "tz");
        countryLanguage.put("CN", "ug");
        countryLanguage.put("UA", "uk");
        countryLanguage.put("PK", "ur");
        countryLanguage.put("UZ", "uz");
        countryLanguage.put("UZ", "uz");
        countryLanguage.put("VN", "vi");
        countryLanguage.put("SN", "wo");
        countryLanguage.put("ZA", "xh");
        countryLanguage.put("NG", "yo");
        countryLanguage.put("CN", "zh");
        countryLanguage.put("HK", "zh");
        countryLanguage.put("MO", "zh");
        countryLanguage.put("SG", "zh");
        countryLanguage.put("TW", "zh");
        countryLanguage.put("ZA", "zu");
    }

    public static String getClientIpAddr(HttpServletRequest request)
            throws UnknownHostException {
        String ip = request.getHeader("X-Forwarded-For");
        System.out.println("ip = " + ip);
        if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
            ip = request.getRemoteAddr();
        }
        if (ip.equalsIgnoreCase("0:0:0:0:0:0:0:1")) {
            InetAddress inetAddress = InetAddress.getLocalHost();
            ip = inetAddress.getHostAddress();
        }
        return ip;
    }

    public static String getCountryCode(HttpServletRequest request)
            throws MalformedURLException, IOException {
        String ip = getClientIpAddr(request);
        URLConnection connection = new URL("http://www.geoplugin.net/json.gp?ip=" + ip).openConnection();

        return "http://www.geoplugin.net/json.gp?ip=" + ip;
    }

    public static String getLanguageCode(HttpServletRequest request)
            throws MalformedURLException, IOException {
        return (String) countryLanguage.get(getCountryCode(request));
    }

    public static Locale getLocale(HttpServletRequest request)
            throws MalformedURLException, IOException {
        String countryCode = getCountryCode(request);
        String languageCode = (String) countryLanguage.get(getCountryCode(request));
        return new Locale(languageCode, countryCode);
    }
}
