package cn.winter.aop;

/**
 * @author xuanyu peng
 * @description:
 * @date 2023/3/29 23:04
 */
public interface PointcutAdvisor extends Advisor{
    Pointcut getPointcut();
}
