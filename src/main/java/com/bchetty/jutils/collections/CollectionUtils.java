package com.bchetty.jutils.collections;

import com.google.common.base.Joiner;
import com.bchetty.jutils.misc.UtilConstants;
import java.util.Collection;
import java.util.Map;

/**
 * Collection Utilities.
 *
 * @author Babji, Chetty
 */
public class CollectionUtils {
    /**
     * Private Constructor - To make this class, a Non-instantiable class (Utility Class Pattern).
     */
    private CollectionUtils() {}

    /**
     *
     * @param col
     * @return
     */
    public static boolean isEmpty(Collection col) {
        return !isNotEmpty(col);
    }

    /**
     *
     * @param col
     * @return
     */
    public static boolean isNotEmpty(Collection col) {
        return (col != null && !col.isEmpty());
    }
    
    public static void printMap(Map map, String keyValSeparator, String separator) {
        if (map != null && !map.isEmpty()) {
            keyValSeparator = (keyValSeparator != null) ? keyValSeparator : UtilConstants.KEY_VALUE_DELIMITER;
            separator = (separator != null) ? separator : UtilConstants.LINE_SEPARATOR;
            
            System.out.println("\n[----------------------------------------------------------------]");
            System.out.println(Joiner.on(separator).withKeyValueSeparator(keyValSeparator).join(map));
            System.out.println("[----------------------------------------------------------------]");
        }        
    }
    
    /* TODO
    public static <ValueType> Collection<List<ValueType>> group(Iterable<ValueType> list, String property) {
        Map groups = new LinkedHashMap();
        
        try {
            for (Iterator i$ = list.iterator(); i$.hasNext();) {
                Object object = i$.next();
                Object key = PropertyUtils.getProperty(object, property);
                List group = (List) groups.get(key);
                if (group == null) {
                    group = new ArrayList();
                    groups.put(key, group);
                }
                group.add(object);
            }
        } catch (Exception exception) {            
            exception.printStackTrace();
        }
        
        return groups.values();
    }
    * 
    */
}
