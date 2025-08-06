package io.github.tcq1007.ddd.annotation;

public @interface DelegateMethod {

    String jpaMethodName() default "";
}
