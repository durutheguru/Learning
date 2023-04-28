package com.julianduru.learning.lambda.crud.util;


import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

/**
 * created by julian
 */
@Slf4j
public class TimeUtil {


    private final static String[] DATE_FORMATS = new String[]{
        "yyyy-MM-dd"
    };


    private final static String[] DATE_TIME_FORMATS = new String[] {
        "yyyy-MM-dd hh:mma"
    };


    private final static String[] ZONE_DATE_TIME_FORMATS = new String[] {
        "yyyy-MM-dd HH:mm:ss.SSSS Z",
        "yyyy-MM-dd HH:mm Z",
        "yyyy-MM-dd hh:mma Z",
        "yyyy-MM-dd HH:mmZ",
        "yyyy-MM-dd HH:mmX",
        "yyyy-MM-dd HH:mm",
        "yyyy-MM-dd HH:mm:ss",
        "yyyy-MM-dd HH:mm:ss.SSSSSS"
    };


    public final static String DEFAULT_DATE_FORMAT = DATE_FORMATS[0];


    public final static String DEFAULT_DATE_TIME_FORMAT = DATE_TIME_FORMATS[0];


    public final static String DEFAULT_ZONE_DATE_TIME_FORMAT = ZONE_DATE_TIME_FORMATS[0];


    public final static DateTimeFormatter DEFAULT_DATE_FORMATTER = DateTimeFormatter.ofPattern(
        DEFAULT_DATE_FORMAT
    );


    public final static DateTimeFormatter DEFAULT_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(
        DEFAULT_DATE_TIME_FORMAT
    );


    public final static DateTimeFormatter DEFAULT_ZONE_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(
        DEFAULT_ZONE_DATE_TIME_FORMAT
    );


    public static Date ldtToDate(LocalDateTime ldt) {
        assert ldt != null;
        return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
    }


    public static ZonedDateTime zdtFromTimeStamp(Long timeStamp) {
        assert timeStamp != null;
        return ZonedDateTime.ofInstant(Instant.ofEpochMilli(timeStamp), ZoneId.systemDefault());
    }


    public static ZonedDateTime parseZonedDateTime(String str) {
        for (String format : ZONE_DATE_TIME_FORMATS) {
            try {
                return ZonedDateTime.parse(
                    str, DateTimeFormatter.ofPattern(format)
                );
            }
            catch (DateTimeParseException e) {
                log.warn(
                    String.format("Unable to parse %s with format %s", str, format)
                );
            }
        }

        log.warn(
            String.format("Unable to parse ZoneDateTime string %s", str)
        );

        return ZonedDateTime.of(
            parseLocalDateTime(str), ZoneId.systemDefault()
        );
    }


    public static LocalDateTime parseLocalDateTime(String str) {
        for (String format : ZONE_DATE_TIME_FORMATS) {
            try {
                return LocalDateTime.parse(
                    str, DateTimeFormatter.ofPattern(format)
                );
            }
            catch (DateTimeParseException e) {
                log.warn(
                    String.format("Unable to parse %s with format %s", str, format)
                );
            }
        }

        throw new IllegalArgumentException(
            String.format("Unable to parse date time string %s", str)
        );
    }


    public static String formatDateTime(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DEFAULT_DATE_TIME_FORMATTER);
    }


}


