package cn.winter.aop;

import java.lang.reflect.Method;

/**
 * @author xuanyu peng
 * @description:
 * @date 2023/3/19 21:41
 */
public interface MethodMatcher {

    // 方法是否匹配
    boolean matcher(Method method, Class<?> targetClass);
}
