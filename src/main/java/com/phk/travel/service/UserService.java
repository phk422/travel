package com.phk.travel.service;

import com.phk.travel.domain.User;

public interface UserService {
    /**
     * 注册用户
     * @param user
     * @return
     */
    boolean register(User user);

    boolean active(String code);

    User login(User u);
}
