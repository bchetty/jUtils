package com.bchetty.jutils.converters;

import java.util.List;

/**
 *
 * @author Babji, Chetty
 */
public interface Converter<S, D> {
    public D convert (S source);
    public List<D> convertList(List<S> sourceList);
}
