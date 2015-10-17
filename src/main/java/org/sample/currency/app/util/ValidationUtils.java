package org.sample.currency.app.util;


import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * Validation utility methods
 *
 * Created by Mohamed Mekkawy.
 */
public abstract class ValidationUtils {

    private ValidationUtils() {
        throw new NotImplementedException("Utility classes cannot be instantiated");
    }

    public static void assertNotBlank(String value, String message) {
        if (StringUtils.isBlank(value)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void assertMinimumLength(String value, int length, String message) {
        if (value.length() < length) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void assertMatches(String value, Pattern regex, String message) {
        if (!regex.matcher(value).matches()) {
            throw new IllegalArgumentException(message);
        }
    }
}
