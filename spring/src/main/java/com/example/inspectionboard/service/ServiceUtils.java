package com.example.inspectionboard.service;

public final class ServiceUtils {
    private static final int MAX_MARK = 12;
    private static final int MIN_MARK = 1;

    public static boolean isMarkValid(int mark) {
        return mark >= MIN_MARK && mark <= MAX_MARK;
    }
}
