package com.epam.rd.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ProductField {
    String fieldNameEn();
    String fieldNameUa() default "???";
}
