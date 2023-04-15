package cn.winter.stereotype;

import java.lang.annotation.*;

/**
 * @author xuanyu peng
 * @description:
 * @date 2023/4/15 16:25
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Component {
    String value() default "";
}
