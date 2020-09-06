package org.vasileva.simbirsofttask.service;

import java.time.format.DateTimeFormatter;

//определяем формат даты и времени
public class ParserForDateTimeUtil {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

}
