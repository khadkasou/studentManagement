package com.prakashmalla.sms.core.enums;

import lombok.Getter;

@Getter
public enum MonthTypeEnum {
    NEPALI("Nepali"),
    ENGLISH("English");

    private final String monthType;

    MonthTypeEnum(String monthType) {
        this.monthType = monthType;
    }
}

