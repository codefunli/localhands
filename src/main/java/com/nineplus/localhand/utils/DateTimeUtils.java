package com.nineplus.localhand.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Locale;

@Component
public class DateTimeUtils {

    private static final String DATETIME_1 = "yyyy-MM-dd'T'HH:mm";
    private static final String DATE_1 = "yyyy-MM-dd";

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH 'giờ' mm 'phút,' 'ngày' dd 'tháng' MM 'năm' yyyy", new Locale("vi"));
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("'ngày' dd 'tháng' MM 'năm' yyyy", new Locale("vi"));

    public String convertLocalDateTimeToString2(String input) {
        if (StringUtils.isEmpty(input)){
            return input;
        }
        String stringDate = input.trim();
        LocalDateTime dateTime = null;
        String formattedDateTime = null;

        Date date = null;
        String formattedDate = null;

        try{
            if (isLocalDateTime(stringDate, DATETIME_1)){
                dateTime = LocalDateTime.parse(stringDate, DateTimeFormatter.ofPattern(DATETIME_1));
                formattedDateTime = dateTime.format(formatter);
                return formattedDateTime;
            } else if (isDate(stringDate, DATE_1)){
                date = new SimpleDateFormat(DATE_1).parse(stringDate);
                formattedDate = simpleDateFormat.format(date);
                return formattedDate;
            } else {
            return stringDate;
            }
        } catch (Exception e){
            return stringDate;
        }
    }

    public boolean isLocalDateTime(String datetimeStr, String format) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            LocalDateTime.parse(datetimeStr, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public boolean isDate(String date, String format){
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            dateFormat.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public Instant getNowInstant(){
        LocalDateTime localDateTime = LocalDateTime.now();
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
        Instant instant = zonedDateTime.toInstant();
        return instant;
    }

    public Instant getInstant(LocalDateTime localDateTime){
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
        Instant instant = zonedDateTime.toInstant();
        return instant;
    }
}


