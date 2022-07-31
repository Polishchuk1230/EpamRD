package com.epam.rd.util;

/**
 * This class wraps String object and provides a custom hashCode generation basing on it
 * This class' logic of hashing: it sums 4 first character values of the value.
 */
public class StringHasherOnFirst4 {
    private String value;

    public StringHasherOnFirst4(String value) {
        this.value = value;
    }

    /**
     * Provides custom hashCode generation
     * @return
     */
    @Override
    public int hashCode() {
        return value.chars().limit(4).sum();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StringHasherOnFirst4)) {
            return false;
        }
        StringHasherOnFirst4 that = (StringHasherOnFirst4) o;
        return value.equals(that.value);
    }
}
