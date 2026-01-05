package com.prakashmalla.sms.core.enums;

import lombok.Getter;

@Getter
public enum FiscalYearTypeEnum {

    CALENDAR_YEAR("January – December"),
    JULY_YEAR("July – June"),
    APRIL_YEAR("April – March"),
    OCTOBER_YEAR("October – September"),
    BIKRAM_SAMBAT("Baisakh – Chaitra"),
    NEPALI_YEAR("Shrawan – Asar");

    private final String displayName;

    FiscalYearTypeEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
    
}

