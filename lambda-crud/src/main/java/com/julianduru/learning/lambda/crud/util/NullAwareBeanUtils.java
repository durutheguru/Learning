package com.julianduru.learning.lambda.crud.util;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtilsBean;

import java.lang.reflect.InvocationTargetException;

/**
 * created by julian
 */
@Slf4j
public class NullAwareBeanUtils extends BeanUtilsBean {


    @Override
    public void copyProperty(Object dest, String name, Object value)
        throws IllegalAccessException, InvocationTargetException {
        if (value == null){
            return;
        }

        super.copyProperty(dest, name, value);
    }


    public static void copy(Object source, Object destination) {
        try {
            NullAwareBeanUtils utilsBean = new NullAwareBeanUtils();
            utilsBean.copyProperties(destination, source);
        }
        catch (IllegalAccessException | InvocationTargetException e) {
            log.error(e.getMessage(), e);
            throw new IllegalStateException(e);
        }
    }


}


