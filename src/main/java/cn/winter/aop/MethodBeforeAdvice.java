package cn.winter.aop;

import java.lang.reflect.Method;
/**
 * @author xuanyu peng
 * @description:
 * @date 2023/3/29 23:02
 */
public interface MethodBeforeAdvice extends BeforeAdvice {
    void before(Method method, Object[] args, Object target) throws Throwable;
}
