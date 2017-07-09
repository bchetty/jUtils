package com.bchetty.jutils.reflection;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Reflection Utilities
 * 
 * @author Babji, Chetty
 */
public class ReflectionUtils {
    public static <SourceType, ResultType> Class<ResultType> getTypeParameter(SourceType object, int index) {
        Type type = object.getClass().getGenericSuperclass();
        if (!(type instanceof ParameterizedType)) {
            throw new IllegalArgumentException();
        }
        return (Class) ((ParameterizedType) type).getActualTypeArguments()[index];
    }
}
