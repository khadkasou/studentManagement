package com.prakashmalla.sms.core.enums;

import lombok.Getter;

@Getter
public enum WeekDayEnum {
    SUNDAY("Sunday"),
    MONDAY("Monday"),
    TUESDAY("Tuesday"),
    WEDNESDAY("Wednesday"),
    THURSDAY("Thursday"),
    FRIDAY("Friday"),
    SATURDAY("Saturday"),
    ;

    private final String weekDay;

    WeekDayEnum(String weekDay) {
        this.weekDay = weekDay;
    }
}
