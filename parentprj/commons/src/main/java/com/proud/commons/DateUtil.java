package com.proud.commons;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 *
 * @author Fabrizio Faustinoni
 */
public class DateUtil {

    private static final String FORMAT = "yyyy-MM-dd hh:mm:ss";

    public String getNow() {
        LocalDateTime datetime = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
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
