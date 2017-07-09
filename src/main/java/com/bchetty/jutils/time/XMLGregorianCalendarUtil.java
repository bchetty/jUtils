package com.bchetty.jutils.time;

import java.util.Date;
import java.util.GregorianCalendar;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * Utility to handle XMLGregorianCalendar objects (usually used in webservices).
 * 
 * @author Babji, Chetty
 */
public class XMLGregorianCalendarUtil {
    private static DatatypeFactory xmlDataTypeFactory = null;
    
    static {
        try {
            xmlDataTypeFactory = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException dce) {
            throw new IllegalStateException("Exception while obtaining DatatypeFactory instance", dce);
        }
    }
    
    /**
     * Private Constructor - To make this class, a Non-instantiable class (Utility Class Pattern).
     */    
    private XMLGregorianCalendarUtil() {}
    
    /**
     * Convert Date to XMLGregorianCalendar.
     * 
     * @param date - java.util.Date
     * @return XMLGregorianCalendar
     */
    public static XMLGregorianCalendar asXMLGregorianCalendar(Date date) {
        XMLGregorianCalendar xmlGregCal = null;
        if (date != null) {
            GregorianCalendar gregCal = new GregorianCalendar();
            gregCal.setTimeInMillis(date.getTime());
            xmlGregCal = xmlDataTypeFactory.newXMLGregorianCalendar(gregCal);
        }
        
        return xmlGregCal;
    }

    /**
     * Converts XMLGregorianCalendar to java.util.Date
     *
     * @param xmlGregCal - XMLGregorianCalendar
     * @return Date - java.util.Date
     */
    public static Date asDate(XMLGregorianCalendar xmlGregCal) {
         return (xmlGregCal != null) ? xmlGregCal.toGregorianCalendar().getTime() : null;
    }
}
