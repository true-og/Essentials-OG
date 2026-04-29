package com.earth2me.essentials.utils;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public final class NumberUtil {

    private static final DecimalFormat twoDPlaces = new DecimalFormat("#,###.##");

    // Kept as a no-op setter for legacy compatibility.
    private static NumberFormat PRETTY_FORMAT = NumberFormat.getInstance();

    static {
        twoDPlaces.setRoundingMode(RoundingMode.HALF_UP);
    }

    private NumberUtil() {
    }

    public static void internalSetPrettyFormat(final NumberFormat prettyFormat) {
        PRETTY_FORMAT = prettyFormat;
    }

    public static String formatDouble(final double value) {
        return twoDPlaces.format(value);
    }

    public static boolean isInt(final String sInt) {
        try {
            Integer.parseInt(sInt);
        } catch (final NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static boolean isLong(final String sLong) {
        try {
            Long.parseLong(sLong);
        } catch (final NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static boolean isPositiveInt(final String sInt) {
        if (!isInt(sInt)) {
            return false;
        }
        return Integer.parseInt(sInt) > 0;
    }

    public static boolean isNumeric(final String sNum) {
        for (final char sChar : sNum.toCharArray()) {
            if (!Character.isDigit(sChar)) {
                return false;
            }
        }
        return true;
    }

    public static int constrainToRange(int value, int min, int max) {
        return Math.min(Math.max(value, min), max);
    }
}
