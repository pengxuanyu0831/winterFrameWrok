package cn.winter.test.bean;

/**
 * @program spring-core
 * @description:
 * @author: pengxuanyu
 * @create: 2021/11/20 22:27
 */
public class UserService {
    private String id;

    private UserDao userDao;

    public void queryUserInfo() {
        System.out.println("查询用户信息--->" + userDao.getName(id));
    }

    public String getId() {
        return id;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }


}
