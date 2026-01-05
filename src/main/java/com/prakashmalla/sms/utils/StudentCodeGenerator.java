package com.prakashmalla.sms.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class StudentCodeGenerator {

    private static final String PREFIX = "STU";
    private static final DateTimeFormatter YEAR_FORMATTER = DateTimeFormatter.ofPattern("yy");

    public static String generateStudentCode(Long studentId) {
        String year = LocalDate.now().format(YEAR_FORMATTER);
        String paddedId = String.format("%06d", studentId);
        return PREFIX + year + paddedId;
    }
}
