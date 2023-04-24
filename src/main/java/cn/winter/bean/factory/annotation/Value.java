package cn.winter.bean.factory.annotation;

import java.lang.annotation.*;

/**
 * @author xuanyu peng
 * @description:
 * @date 2023/4/24 22:35
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Value {
    /**
     * The actual value expression: e.g. "#{systemProperties.myProp}".
     */
    String value();

}
