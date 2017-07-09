package com.bchetty.jutils.converters;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract Converter Class
 * 
 * @author Babji, Chetty
 */
public abstract class AbstractConverter<S,D> implements Converter<S,D> {
    /**
     * 
     * @param source
     * @return 
     */
    @Override
    public abstract D convert(S source);
    
    /**
     * 
     * @param sourceList
     * @return 
     */
    @Override
    public List<D> convertList(List<S> sourceList) {
        List<D> destinationList = null;
        if(sourceList != null) {
            destinationList = new ArrayList<D>();
            
            for(S source: sourceList) {
                destinationList.add(convert(source));
            }
        }
        
        return destinationList;
    }    
}
