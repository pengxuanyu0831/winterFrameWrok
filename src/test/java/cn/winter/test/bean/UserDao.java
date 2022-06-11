package cn.winter.test.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * @program spring-core
 * @description:
 * @author: pengxuanyu
 * @create: 2022/06/11 22:28
 */
public class UserDao {
    private static Map<String, String> map = new HashMap<>();
    static {
        map.put("00001", "zhangsan");
        map.put("00002", "lisi");
        map.put("00003", "wangwu");
    }

    public String getName(String id) {
        return map.get(id);
    }
}
