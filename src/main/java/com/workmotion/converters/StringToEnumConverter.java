package com.workmotion.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.workmotion.model.EmployeeStateChangeEvent;
@Component
public class StringToEnumConverter implements Converter<String, EmployeeStateChangeEvent> {
    @Override
    public EmployeeStateChangeEvent convert(String source) {
        return EmployeeStateChangeEvent.valueOf(source.toUpperCase());
    }
}
