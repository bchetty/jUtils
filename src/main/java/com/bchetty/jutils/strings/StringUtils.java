package com.bchetty.jutils.strings;

import com.google.common.base.Joiner;
import com.bchetty.jutils.misc.UtilConstants;
import org.apache.commons.lang.WordUtils;

/**
 * Class with String utility methods.
 * 
 * @author Babji, Chetty
 */
public class StringUtils {    
    /**
     * Private Constructor - To make this class, a Non-instantiable class (Utility Class Pattern).
     */    
    private StringUtils() {}
    
    /**
     * This method delimits the input strings with the supplied delimiter, joins them and returns a single string.
     * Note that the method has a 'Variable Arguments' parameter.
     * 
     * @param delimiter - Delimiter
     * @param strings - Variable Arguments parameter
     * @return 
     */
     public static String join(String delimiter, String... strings) {
         return nativeJoin(delimiter, strings);         
     }
     
     /**
      * Joining strings using native code logic.
      * 
      * @param delimiter
      * @param strings
      * @return 
      */
     public static String nativeJoin(String delimiter, String... strings) {
         if(strings != null) {
             StringBuilder sbResult = new StringBuilder("");             
             int len = strings.length;
             
             if(len > 0) {
                 String newDelimiter = (delimiter != null) ? delimiter : "";
                 int index = 0;
                 
                 for(String str: strings) {
                    if(str != null && !str.isEmpty()) {
                        if(index != 0) {
                            sbResult.append(newDelimiter);                            
                        }
                        sbResult.append(str);
                        index++;
                    }
                }
             }
             
             return sbResult.toString();
         } else {
             return "";             
         }     
     }
     
     /**
      * Joining strings using Google Guava's Joiner.
      * 
      * @param delimiter
      * @param strings
      * @return 
      */
     public static String guavaJoin(String delimiter, String... strings) {         
         return (strings != null) ? Joiner.on(((delimiter != null) ? delimiter : "")).skipNulls().join(strings) : "";       
     }
     
     /**
     * This method turns a string with 'Wild Card Characters' into a 'Regular Expression (Regex)', which is further used for pattern matching.
     *
     * @param wildCardString - String which may contain 'Wild Card' characters.
     * @return String - Regular Expression, which can be used for pattern matching, using Java's built-in regex module.
     */
    public static String wildcardToRegex(String stringWithWildCardChars) {
        if(stringWithWildCardChars == null || stringWithWildCardChars.isEmpty()) {
            return "";
        }

        StringBuilder sbRegex = new StringBuilder();
        char[] chars = stringWithWildCardChars.toCharArray();

        for(int i = 0; i < chars.length; ++i) {
            if(chars[i] == '*' || chars[i] == '%') {
                sbRegex.append(".*");
            } else if (chars[i] == '?' || chars[i] == '_') {
                sbRegex.append(".");
            } else if ("+()^$.{}[]|\\".indexOf(chars[i]) >= 0) {
                sbRegex.append('\\').append(chars[i]); // prefix all metacharacters with backslash
            } else {
                sbRegex.append(chars[i]);
            }
        }

        return sbRegex.toString().toLowerCase();
    }
    
    /**
     * 
     * @param string
     * @return 
     */
    public static boolean isEmpty(String string) {
        return !isNotEmpty(string);
    }
    
    /**
     * 
     * @param string
     * @return 
     */
    public static boolean isNotEmpty(String string) {
        return (string != null && !string.isEmpty());
    }
    
    /**
     * Code Ref: org.apache.commons.lang.StringUtils.substringBeforeLast
     * 
     * @param input
     * @param separator
     * @return 
     */
    public static String prefixWithLast(String input, String separator) {
        if (isNotEmpty(input) && isNotEmpty(separator)) {
            int index = input.lastIndexOf(separator);            
            return (index < 0) ? input : input.substring(0, index);
        } else {
            return input;            
        }
    }
    
    /**
     * Code Ref: org.apache.commons.lang.StringUtils.substringAfterLast
     * 
     * @param input
     * @param separator
     * @return 
     */
    public static String suffixWithLast(String input, String separator) {
        if (isEmpty(input)) {
            return input;
        }
        if (isEmpty(separator)) {
            return UtilConstants.EMPTY_STRING;
        }
        
        int index = input.lastIndexOf(separator);
        return (index == -1 || index == (input.length() - separator.length())) ? input : input.substring(index + separator.length());
    }
    
    /**
     * 
     * @param input
     * @return 
     */
    public static String capitalize(String input) {
        return (input != null) ? WordUtils.capitalize(input.toLowerCase()) : null;
    }
}
