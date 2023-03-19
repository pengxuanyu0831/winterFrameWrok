package cn.winter.aop;



/**
 * @author xuanyu peng
 * @description:
 * @date 2023/3/19 21:38
 */
public interface Pointcut {
    ClassFilter getClassFilter();


    MethodMatcher getMethodMatcher();


}
