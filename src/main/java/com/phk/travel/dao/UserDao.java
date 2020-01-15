package com.phk.travel.dao;

import com.phk.travel.domain.User;

public interface UserDao {
    /**
     * 根据用户名查询用户信息
     * @param name
     * @return
     */
    User findUserByName(String name);

    /**
     * 保存用户信息
     * @param user
     * @return
     */
    void save(User user);

    /**
     * 根据激活码查询用户对象
     * @param code
     * @return
     */
    User findUserByCode(String code);

    /**
     * 修改指定用户激活状态为Y
     * @param user
     */
    void updateStatus(User user);

    /**
     * 根据用户名和密码查询用户
     * @param username
     * @param password
     * @return
     */
    User findUserByUsernameAndPassword(String username, String password);
}
