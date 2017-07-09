package com.bchetty.jutils.time;

import com.bchetty.jutils.enums.Duration;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;

/**
 * Class with utility methods for manipulating Date and Time.
 *
 * @author Babji, Chetty
 */
public class DateTimeUtil {
    /**
     * Private Constructor - To make this class, a Non-instantiable class
     * (Utility Class Pattern).
     */
    private DateTimeUtil() {}

    /**
     *
     * @param inputDate
     * @param duration
     * @param durationInt
     * @return
     */
    public static Date add(Date inputDate, Duration duration, int durationInt) {
        Date outputDate = null;

        switch (duration) {
            case MILLIS:
                outputDate = new DateTime(inputDate).plusMillis(durationInt).toDate();
                break;
            case SECONDS:
                outputDate = new DateTime(inputDate).plusSeconds(durationInt).toDate();
                break;
            case MINUTES:
                outputDate = new DateTime(inputDate).plusMinutes(durationInt).toDate();
                break;
            case HOURS:
                outputDate = new DateTime(inputDate).plusHours(durationInt).toDate();
                break;
            case DAYS:
                outputDate = new DateTime(inputDate).plusDays(durationInt).toDate();
                break;
            case WEEKS:
                outputDate = new DateTime(inputDate).plusWeeks(durationInt).toDate();
                break;
            case MONTHS:
                outputDate = new DateTime(inputDate).plusMonths(durationInt).toDate();
                break;
            case YEARS:
                outputDate = new DateTime(inputDate).plusYears(durationInt).toDate();
                break;
            default:
                outputDate = new DateTime(inputDate).plus(durationInt).toDate();
                break;
        }

        return outputDate;
    }

    /**
     *
     * @return
     */
    public static Date now() {
        return new DateTime().toDate();
    }

    /**
     *
     * @return
     */
    public static Date getToday() {
        DateTime now = new DateTime();
        LocalDate today = now.toLocalDate();
        DateTime startOfToday = today.toDateTimeAtStartOfDay(now.getZone());

        return startOfToday.toDate();
    }

    /**
     *
     * @return
     */
    public static Date getTomorrow() {
        DateTime now = new DateTime();
        LocalDate today = now.toLocalDate();
        LocalDate tomorrow = today.plusDays(1);
        DateTime startOfTomorrow = tomorrow.toDateTimeAtStartOfDay(now.getZone());

        return startOfTomorrow.toDate();
    }

    /**
     *
     * @param inputDate
     * @return
     */
    public static Date getMonday(Date inputDate) {
        DateTime dateTime = new DateTime(inputDate);
        LocalDate localDate = dateTime.toLocalDate().withDayOfWeek(DateTimeConstants.MONDAY);
        DateTime dateWithJustDayInfo = localDate.toDateTimeAtStartOfDay(dateTime.getZone());

        return dateWithJustDayInfo.toDate();
    }

    /**
     *
     * @param inputDate
     * @return
     */
    public static Date getStartOfDay(Date inputDate) {
        DateTime dateTime = new DateTime(inputDate);
        LocalDate localDate = dateTime.toLocalDate();
        DateTime dateWithJustDayInfo = localDate.toDateTimeAtStartOfDay(dateTime.getZone());

        return dateWithJustDayInfo.toDate();
    }

    /**
     *
     * @param inputDate
     * @param locale
     * @return
     */
    public static Calendar getCalendar(Date inputDate, Locale locale) {
        return new DateTime(inputDate).toCalendar(locale);
    }

    /**
     * Merge Date-Object wth only date information, with a Date-Object with only
     * time information.
     *
     * @param date
     * @param time
     * @return
     */
    public static Date mergeDateTime(Date date, Date time) {
        Calendar timeCal = Calendar.getInstance();
        timeCal.setTime(time);

        Calendar dateCal = Calendar.getInstance();
        dateCal.setTime(date);

        copyTime(timeCal, dateCal);
        return dateCal.getTime();
    }

    /**
     * Copy Time (HH:mm:ss:ms) from 'fromCal' to 'toCal'.
     *
     * @param fromCal
     * @param toCal
     */
    public static void copyTime(Calendar fromCal, Calendar toCal) {
        toCal.set(Calendar.HOUR_OF_DAY, fromCal.get(Calendar.HOUR_OF_DAY));
        toCal.set(Calendar.MINUTE, fromCal.get(Calendar.MINUTE));
        toCal.set(Calendar.SECOND, fromCal.get(Calendar.SECOND));
        toCal.set(Calendar.MILLISECOND, fromCal.get(Calendar.MILLISECOND));
    }
    
    /**
     * Returns a string, representing time in 24 hour format.
     * 
     * @param date
     * @return 
     */
    public static String toTime(Date date) {
        if (date == null) {
            return null;
        }
        return new SimpleDateFormat("HH:mm").format(date);
    }
    
    /**
     * Date with seconds and milliseconds reset
     * 
     * @return 
     */
    public static Date getActualDateWithoutSeconds() {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.setTime(new Date());
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        
        return cal.getTime();
    }
    
    /**
     * 
     * @param dateString
     * @param dateFormat
     * @return 
     */
    public static int compareToToday(String dateString, String dateFormat) {
        if(dateString != null && !dateString.isEmpty() && dateFormat != null && !dateFormat.isEmpty()) {
            SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(dateFormat);
            Date date = null;
            try {
                date = DATE_FORMAT.parse(dateString);
            } catch(Exception ex) {
                ex.printStackTrace();
            }
            
            return (date != null) ? date.compareTo(DateTimeUtil.getToday()) : 0;
        }
        
        return 0;
    }
    
    /**
     * Compare 2 dates, considering only the 'Date' part (without time).
     * 
     * @param firstDate
     * @param secondDate
     * @return 
     */
    public static int compareDatesWithoutTime(Date firstDate, Date secondDate) {
        if(firstDate == null && secondDate == null) {
            return 0;            
        } else if(firstDate == null && secondDate != null) {
            return -1;
        } else if(firstDate != null && secondDate == null) {
            return 1;            
        }
        
        return DateTimeComparator.getDateOnlyInstance().compare(firstDate, secondDate);
    }
    
    /**
     * Compare 2 dates, considering only the 'Time' part (without date - only HH:MM:SS).
     * 
     * @param firstDate
     * @param secondDate
     * @return 
     */
    public static int compareDatesOnlyWithTime(Date firstDate, Date secondDate) {
        if(firstDate == null && secondDate == null) {
            return 0;            
        } else if(firstDate == null && secondDate != null) {
            return -1;
        } else if(firstDate != null && secondDate == null) {
            return 1;            
        }
        
        return DateTimeComparator.getTimeOnlyInstance().compare(firstDate, secondDate);
    }
}