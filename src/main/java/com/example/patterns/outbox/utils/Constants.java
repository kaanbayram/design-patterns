package com.example.patterns.outbox.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {

    @UtilityClass
    public class Checkout {
        public final String OUTBOX_AGGREGATE_TYPE = "Checkout";
    }
}
