package com.bchetty.jutils.misc;

/**
 * Constants for Utility Library.
 * 
 * @author Babji, Chetty
 */
public class UtilConstants {
    private UtilConstants(){
        throw new AssertionError();
    }
    
    public static final String EMPTY_STRING = "";
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    public static final String KEY_VALUE_DELIMITER = " -> ";
}