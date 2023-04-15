package cn.winter.context.annotation;

import java.lang.annotation.*;

/**
 * @author xuanyu peng
 * @description: bean 的作用域 默认单例
 * @date 2023/4/15 16:23
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Scope {

    String value() default "singleton";

}
