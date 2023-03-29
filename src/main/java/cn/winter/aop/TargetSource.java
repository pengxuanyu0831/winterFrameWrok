package cn.winter.aop;

/**
 * @author xuanyu peng
 * @description:
 * @date 2023/3/19 21:59
 */
public class TargetSource {
    private final Object target;

    public TargetSource(Object obj) {
        this.target = obj;
    }


    public Class<?>[] getTargetClass() {
        return this.target.getClass().getInterfaces();
    }


    public Object getTarget() {
        return this.target;
    }
}
