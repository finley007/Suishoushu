package com.changyi.fi.core.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by finley on 2/8/17.
 */
@Retention(RUNTIME)
@Target({TYPE, METHOD})
public @interface Validate {
    String validator() default "";
}
