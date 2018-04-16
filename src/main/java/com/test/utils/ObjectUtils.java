package com.test.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

public final class ObjectUtils {

    private static Logger log = Logger.getLogger(ObjectUtils.class.getName());

    private ObjectUtils() {
        super();
    }

    public static <T> T copy(Object origin, Class<T> clazz) {
        T destination = null;
        try {
            destination = clazz.newInstance();
            copy(origin, destination, true);
        } catch (InstantiationException e) {
            log.error("Unexpected error!!!", e);
        } catch (IllegalAccessException e) {
            log.error("Unexpected error!!!", e);
        }
        return destination;
    }

    public static void copy(Object orig, Object dest) {
        copy(orig, dest, false);
    }

    public static void copy(Object orig, Object dest, boolean ignoreNullValues) {
        PropertyDescriptor[] origDescriptors = PropertyUtils.getPropertyDescriptors(orig);
        for (int i = 0; i < origDescriptors.length; i++) {
            String name = origDescriptors[i].getName();
            if (PropertyUtils.isReadable(orig, name) && PropertyUtils.isWriteable(dest, name)) {
                try {
                    Object value = PropertyUtils.getSimpleProperty(orig, name);
                    if (!ignoreNullValues || value != null) {
                        PropertyUtils.setSimpleProperty(dest, name, value);
                    }
                } catch (IllegalAccessException e) {
                    log.error("Unexpected error!!!", e);
                } catch (InvocationTargetException e) {
                    log.error("Unexpected error!!!", e);
                } catch (NoSuchMethodException e) {
                    log.error("Unexpected error!!!", e);
                }
            }
        }
    }

    public static <T> List<T> copyToList(Iterable<?> list, Class<T> clazz) {
        List<T> result = new LinkedList<T>();

        for (Object origin : list) {
            T destination = copy(origin, clazz);
            result.add(destination);
        }

        return result;
    }

}
