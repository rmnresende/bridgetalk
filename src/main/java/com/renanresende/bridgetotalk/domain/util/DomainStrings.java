package com.renanresende.bridgetotalk.domain.util;

public final class DomainStrings {

    private DomainStrings() {}

    public static boolean isNotBlank(String value) {
        return value != null && !value.isBlank();
    }

    public static boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
