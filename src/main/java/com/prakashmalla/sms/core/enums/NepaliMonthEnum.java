package com.prakashmalla.sms.core.enums;

import lombok.Getter;

@Getter
public enum NepaliMonthEnum {
    BAISHAKH("Baishakh"),
    JESTHA("Jestha"),
    ASHADH("Ashadh"),
    SHRAWAN("Shrawan"),
    BHADRA("Bhadra"),
    ASHWIN("Ashwin"),
    KARTIK("Kartik"),
    MANGSIR("Mangsir"),
    POUSH("Poush"),
    MAGH("Magh"),
    FALGUN("Falgun"),
    CHAITRA("Chaitra");

    private final String month;

    NepaliMonthEnum(String month) {
        this.month = month;
    }
}

