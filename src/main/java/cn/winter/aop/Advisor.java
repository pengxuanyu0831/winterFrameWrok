package cn.winter.aop;

import org.aopalliance.aop.Advice;

/**
 * @author xuanyu peng
 * @description:
 * @date 2023/3/29 23:03
 */
public interface Advisor {
    Advice getAdvice();
}
