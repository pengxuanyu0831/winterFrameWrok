package cn.winter.core;

import java.util.ArrayList;
import java.util.List;

/**
 * @program spring-core
 * @description:
 * @author: pengxuanyu
 * @create: 2022/06/11 21:46
 */
public class PropertyValues {
    private final List<PropertyValue> propertyValues = new ArrayList<>();

    public void addPropertyValues(PropertyValue value) {
        this.propertyValues.add(value);
    }

    public PropertyValue[] getPropertyValues() {
        return this.propertyValues.toArray(new PropertyValue[0]);
    }

    public PropertyValue getPropertyValue(String propertyName) {
        for (PropertyValue p : this.propertyValues) {
            if (p.getName().equals(propertyName)) {
                return (PropertyValue) p.getValue();
            }
        }
        return null;
    }
}
