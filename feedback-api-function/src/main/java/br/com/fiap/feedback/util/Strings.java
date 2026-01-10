package br.com.fiap.feedback.util;

public final class Strings {

    private Strings() {
    }

    public static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
