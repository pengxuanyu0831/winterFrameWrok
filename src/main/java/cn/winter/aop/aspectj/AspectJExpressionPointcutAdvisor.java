package cn.winter.aop.aspectj;

import cn.winter.aop.Advisor;
import cn.winter.aop.Pointcut;
import cn.winter.aop.PointcutAdvisor;
import org.aopalliance.aop.Advice;

/**
 * @author xuanyu peng
 * @description:
 * @date 2023/3/29 23:06
 */
public class AspectJExpressionPointcutAdvisor implements PointcutAdvisor {
    // 具体的切面
    private AspectJExpressionPointCut pointCut;

    // 具体的拦截方法
    private Advice advice;

    // 表达式
    private String expression;

    public void setExpression(String expression) {
        this.expression = expression;
    }


    @Override
    public Advice getAdvice() {
        return advice;
    }

    @Override
    public Pointcut getPointcut() {
        if (null == pointCut) {
            pointCut = new AspectJExpressionPointCut(expression);
        }
        return pointCut;
    }

    public void setAdvice(Advice advice) {
        this.advice = advice;
    }
}
