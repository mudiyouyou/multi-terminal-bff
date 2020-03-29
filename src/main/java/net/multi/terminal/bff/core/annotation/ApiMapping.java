package net.multi.terminal.bff.core.annotation;

import java.lang.annotation.*;

/**
 * Api方法注解，用于标识Api方法和方法名称及描述
 * 描述可以生成Api文档
 */
@Documented
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiMapping {
    String desc() default "";
    String name() default "";
}
