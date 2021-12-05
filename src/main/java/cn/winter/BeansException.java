package cn.winter;

/**
 * @program spring-core
 * @description:
 * @author: pengxuanyu
 * @create: 2021/12/05 10:28
 */
public abstract class BeansException extends RuntimeException{
    public BeansException(String msg) {
        super(msg);
    }

}
