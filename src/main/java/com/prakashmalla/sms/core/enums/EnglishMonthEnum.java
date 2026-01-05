package com.prakashmalla.sms.core.enums;

import lombok.Getter;

@Getter
public enum EnglishMonthEnum {
    JANUARY("January"),
    FEBRUARY("February"),
    MARCH("March"),
    APRIL("April"),
    MAY("May"),
    JUNE("June"),
    JULY("July"),
    AUGUST("August"),
    SEPTEMBER("September"),
    OCTOBER("October"),
    NOVEMBER("November"),
    DECEMBER("December");

    private final String month;

    EnglishMonthEnum(String month) {
        this.month = month;
    }
}

