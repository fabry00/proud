package com.proud.commons;

import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

/**
 *
 * @author Fabrizio Faustinoni
 */
public class DateUtils {

    private static final String FORMAT = "yyyy-MM-dd hh:mm:ss";
    private final Logger logger = Logger.getLogger(DateUtils.class);

    public String getNow() {
        // Time zone set on application start
        LocalDateTime datetime = LocalDateTime.ofInstant(Instant.now(),java.time.ZoneId.systemDefault());
        return DateTimeFormatter.ofPattern(FORMAT).format(datetime);
    }

    public String formatDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(FORMAT);
        return formatter.format(date);
    }

    public Date getNowDate() {
        return new Date();
    }
}
