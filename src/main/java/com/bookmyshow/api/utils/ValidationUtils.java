package com.bookmyshow.api.utils;

import org.springframework.validation.ObjectError;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class ValidationUtils {
    private ValidationUtils() {
    }

    /**
     * Primitive data type validators
     */
    public static boolean isPositiveNumber(Integer value) {
        return null != value && 0 < value;
    }

    public static boolean isZero(Integer value) {
        return !isPositiveNumber(value);
    }

    public static boolean isPositiveNumber(Float value) {
        return null != value && 0.0f < value;
    }

    public static boolean isPositiveNumber(Double value) {
        return null != value && 0.0 < value;
    }

    public static boolean isNotNullOrBlank(String value) {
        return null != value && !value.isBlank();
    }

    public static boolean isNullOrBlank(String value) {
        return !isNotNullOrBlank(value);
    }

    /**
     * Custom obect validators
     */
    public static boolean isNull(Object value) {
        return null == value;
    }

    public static boolean isNotNull(Object value) {
        return !isNull(value);
    }

    public static boolean isNullOrBlank(UUID value) {
        return null == value || isNullOrBlank(value.toString());
    }

    public static boolean isNullOrEmpty(Object o) {
        if (!isNotNull(o)) {
            return true;
        }
        if (o instanceof Collection<?>) {
            return ((Collection<?>) o).isEmpty();
        }
        if (o instanceof Map<?, ?>) {
            return ((Map<?, ?>) o).isEmpty();
        }
        return false;
    }

    public static boolean isNotNullOrEmpty(Object value) {
        return !isNullOrEmpty(value);
    }

    public static boolean isNotAllNull(final Object... values) {
        if (null == values) {
            return false;
        }
        for (final Object val : values) {
            if (null == val) {
                return false;
            }
        }
        return true;
    }

    public static boolean isInRange(LocalDate start, LocalDate end, LocalDate value) {
        return null != start && null != end && null != value && !value.isBefore(start) && !value.isAfter(end);
    }

    /**
     * Error data providers
     */
    public static Throwable getCause(List<ObjectError> errors) {
        if (errors.isEmpty()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();

        //Construct the error log with key and error message
        for (int i = 0; i < errors.size(); i++) {
            ObjectError error = errors.get(i);
            sb.append(String.join(": ", error.getCode(), error.getDefaultMessage()));

            //If not the last item
            if (i != errors.size() - 1) {
                sb.append(". ");
            }
        }
        return new Throwable(sb.toString());
    }
}
