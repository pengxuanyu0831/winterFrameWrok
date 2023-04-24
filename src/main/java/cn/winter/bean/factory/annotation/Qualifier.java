package cn.winter.bean.factory.annotation;

import java.lang.annotation.*;

/**
 * @author xuanyu peng
 * @description:
 * @date 2023/4/24 22:36
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Qualifier {


    String value() default "";
}
