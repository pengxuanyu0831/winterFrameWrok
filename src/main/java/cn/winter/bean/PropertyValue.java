package cn.winter.bean;

/**
 * @program spring-core
 * @description:
 * @author: pengxuanyu
 * @create: 2022/06/11 21:44
 */
public class PropertyValue {
    private final String name;

    private final Object value;

    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }
}
