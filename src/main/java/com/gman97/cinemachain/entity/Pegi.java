package com.gman97.cinemachain.entity;

import lombok.Getter;

@Getter
public enum Pegi {

    THREE_PLUS("3+"),
    SEVEN_PLUS("7+"),
    TWELVE_PLUS("12+"),
    SIXTEEN_PLUS("16+"),
    EIGHTEEN_PLUS("18+");

    private final String value;

    Pegi(String value) {
        this.value = value;
    }
}
