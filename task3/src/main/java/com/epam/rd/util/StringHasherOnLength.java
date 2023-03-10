package com.epam.rd.util;

/**
 * This class wraps String object and provides a custom hashCode generation basing on it
 * This class' logic of hashing: it returns length of a particular String value.
 */
public class StringHasherOnLength {
    private String value;

    public StringHasherOnLength(String value) {
        this.value = value;
    }

    /**
     * Provides custom hashCode generation
     * @return
     */
    @Override
    public int hashCode() {
        return value.length();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StringHasherOnLength)) {
            return false;
        }
        StringHasherOnLength that = (StringHasherOnLength) o;
        return value.equals(that.value);
    }
}