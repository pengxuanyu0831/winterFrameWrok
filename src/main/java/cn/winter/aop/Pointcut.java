package cn.winter.aop;



/**
 * @author xuanyu peng
 * @description: 切入点接口，获取ClassFilter 和 MethodMatcher
 * @date 2023/3/19 21:38
 */
public interface Pointcut {
    ClassFilter getClassFilter();


    MethodMatcher getMethodMatcher();


}
