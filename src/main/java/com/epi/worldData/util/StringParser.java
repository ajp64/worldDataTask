package com.epi.worldData.util;

import com.opencsv.bean.AbstractBeanField;

/*
 Parser to handle cases where an error value for an isoA2 is provided
*/

public class StringParser extends AbstractBeanField <String, String> {
    @Override
    protected String convert(String value) {
        if (value == null || value.trim().equals("#N/A")) {
            return null;
        }

        return value;
    }
}
