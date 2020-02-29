package com.version.common.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

/**
 * @author 周希来
 */
@Retention(RetentionPolicy.RUNTIME)
@Component
@Target({ java.lang.annotation.ElementType.TYPE })
public @interface TCPController {
	String name();
}
