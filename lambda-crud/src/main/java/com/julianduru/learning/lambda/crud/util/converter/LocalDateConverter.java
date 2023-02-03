package com.julianduru.learning.lambda.crud.util.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.sql.Date;
import java.time.LocalDate;

/**
 * created by julian on 03/02/2023
 */
@Converter
public class LocalDateConverter implements AttributeConverter<LocalDate, Date> {


    @Override
    public Date convertToDatabaseColumn(LocalDate attr) {
        return attr == null ? null : Date.valueOf(attr);
    }


    @Override
    public LocalDate convertToEntityAttribute(Date data) {
        return data == null ? null : data.toLocalDate();
    }


}

