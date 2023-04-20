package com.xcompany.nimble.biz.user.service;

import com.xcompany.nimble.biz.user.model.User;

public interface UserService {
    User findUserById(String id);
    User findUserByName(String name);
    User createUser(User user);
    void updateUser(User user);
}
