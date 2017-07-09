package com.bchetty.jutils.net;

import com.bchetty.jutils.strings.StringUtils;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 * Class with URL utility methods.
 *
 * @author Babji, Chetty
 */
public class URLUtils {
    private final static Logger LOGGER = Logger.getLogger(URLUtils.class.getName());
    
    public static final Charset UTF_8 = Charset.forName("UTF-8");
    public static final String URL_PARAMETER_SEPARATOR = "&";
    public static final String URL_PARAMETER_VALUE_SEPARATOR = "=";
    public static final Pattern COOKIE_SPLITTER = Pattern.compile(";");
    public static final Pattern COOKIE_VALUE_SPLITTER = Pattern.compile("=");

    /**
     * Private Constructor - To make this class, a Non-instantiable class
     * (Utility Class Pattern).
     */
    private URLUtils() {}

    /**
     * Extract parameters from URL Query String and return them, stored in a
     * map.
     *
     * @param query
     * @return
     */
    public static Map<String, String> getParameterMap(final String query) {
        Map<String, String> parameterMap = new HashMap<String, String>();

        if (query != null && !query.isEmpty()) {
            try {
                for (String param : query.split(URL_PARAMETER_SEPARATOR)) {
                    String pair[] = param.split(URL_PARAMETER_VALUE_SEPARATOR);
                    String key = URLDecoder.decode(pair[0], UTF_8.name());

                    if (parameterMap.get(key) != null) {
                        continue;
                    }

                    String value = (pair.length > 1) ? URLDecoder.decode(pair[1], UTF_8.name()) : "";
                    parameterMap.put(key, value);
                }
            } catch (UnsupportedEncodingException unsupportedEx) {
                LOGGER.log(Level.SEVERE, unsupportedEx.getMessage());
                throw new IllegalStateException(unsupportedEx);
            }
        }

        return parameterMap;
    }

    /**
     * Build URL Query String from URL parameters.
     *
     * @param parameterMap
     * @return
     */
    public static String getQuery(final Map<String, Object> parameterMap) {
        if (parameterMap == null || parameterMap.isEmpty()) {
            return "";
        }

        StringBuilder sbQuery = new StringBuilder();
        Iterator<Map.Entry<String, Object>> iter = parameterMap.entrySet().iterator();

        while (iter.hasNext()) {
            Map.Entry<String, Object> entry = iter.next();

            if (entry.getKey() == null || entry.getValue() == null) {
                continue;
            }

            try {
                String encodedKey = URLEncoder.encode(entry.getKey(), UTF_8.name());
                String encodedValue = URLEncoder.encode(String.valueOf(entry.getValue()), UTF_8.name());

                if (sbQuery.length() > 0) {
                    sbQuery.append('&');
                }

                sbQuery.append(encodedKey).append('=').append(encodedValue);
            } catch (UnsupportedEncodingException unsupportedEx) {
                LOGGER.log(Level.SEVERE, unsupportedEx.getMessage());
                throw new IllegalStateException(unsupportedEx);
            }
        }

        return sbQuery.toString();
    }

    /**
     *
     * @param cookies
     * @return
     */
    public static Map<String, String> getCookieMap(String cookies) {
        Map cookieMap = new LinkedHashMap();
        for (String cookie : COOKIE_SPLITTER.split(cookies)) {
            String[] values = COOKIE_VALUE_SPLITTER.split(cookie);
            cookieMap.put(values[0], values[1]);
        }
        return cookieMap;
    }

    /**
     *
     * @param cookies
     * @return
     */
    public static Object getCookieString(Map<String, String> cookies) {
        StringBuilder buffer = new StringBuilder();
        for (Map.Entry cookie : cookies.entrySet()) {
            buffer.append(";")
                    .append((String) cookie.getKey())
                    .append("=")
                    .append((String) cookie.getValue());
        }

        return buffer.toString();
    }
    
    /**
     * Java function for decoding UTF8/URL encoded strings
     * Source:
     * 1) http://www.w3.org/International/O-URL-code.html
     * 2) http://www.w3.org/International/unescape.java
     * 
     * @param s
     * @return 
     */
    public static String unescape(String s) {
        if(StringUtils.isEmpty(s)) {
            return null;
        }
        
        StringBuilder sbuf = new StringBuilder();
        int l = s.length();
        int ch = -1;
        int b, sumb = 0;
        for (int i = 0, more = -1; i < l; i++) {
            /* Get next byte b from URL segment s */
            switch (ch = s.charAt(i)) {
                case '%':
                    ch = s.charAt(++i);
                    int hb = (Character.isDigit((char) ch)
                            ? ch - '0'
                            : 10 + Character.toLowerCase((char) ch) - 'a') & 0xF;
                    ch = s.charAt(++i);
                    int lb = (Character.isDigit((char) ch)
                            ? ch - '0'
                            : 10 + Character.toLowerCase((char) ch) - 'a') & 0xF;
                    b = (hb << 4) | lb;
                    break;
                case '+':
                    b = ' ';
                    break;
                default:
                    b = ch;
            }
            /* Decode byte b as UTF-8, sumb collects incomplete chars */
            if ((b & 0xc0) == 0x80) {			// 10xxxxxx (continuation byte)
                sumb = (sumb << 6) | (b & 0x3f);	// Add 6 bits to sumb
                if (--more == 0) {
                    sbuf.append((char) sumb); // Add char to sbuf
                }
            } else if ((b & 0x80) == 0x00) {		// 0xxxxxxx (yields 7 bits)
                sbuf.append((char) b);			// Store in sbuf
            } else if ((b & 0xe0) == 0xc0) {		// 110xxxxx (yields 5 bits)
                sumb = b & 0x1f;
                more = 1;				// Expect 1 more byte
            } else if ((b & 0xf0) == 0xe0) {		// 1110xxxx (yields 4 bits)
                sumb = b & 0x0f;
                more = 2;				// Expect 2 more bytes
            } else if ((b & 0xf8) == 0xf0) {		// 11110xxx (yields 3 bits)
                sumb = b & 0x07;
                more = 3;				// Expect 3 more bytes
            } else if ((b & 0xfc) == 0xf8) {		// 111110xx (yields 2 bits)
                sumb = b & 0x03;
                more = 4;				// Expect 4 more bytes
            } else /*if ((b & 0xfe) == 0xfc)*/ {	// 1111110x (yields 1 bit)
                sumb = b & 0x01;
                more = 5;				// Expect 5 more bytes
            }
            /* We don't test if the UTF-8 encoding is well-formed */
        }
        
        return sbuf.toString();
    }
}
