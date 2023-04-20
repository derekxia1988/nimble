package com.xcompany.nimble.biz.user.service.impl;

import com.xcompany.nimble.biz.exception.BizErrorCode;
import com.xcompany.nimble.biz.user.model.User;
import com.xcompany.nimble.biz.user.repository.UserRepository;
import com.xcompany.nimble.biz.user.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findUserById(String id) {
        log.debug("findUser: id={}", id);
        var user = userRepository.findById(id);
        if (user == null) {
            throw BizErrorCode.USER_NOT_FOUND.error(id);
        }
        log.debug("findUser: user={}", user);
        return user;
    }

    @Override
    public User findUserByName(String name) {
        log.debug("findUser: name={}", name);
        var user = userRepository.findByName(name);
        if (user == null) {
            throw BizErrorCode.USER_NOT_FOUND.error(name);
        }
        log.debug("findUser: user={}", user);
        return user;
    }

    @Override
    public User createUser(User user) {
        // TODO: check if user data is valid
        log.debug("createUser: user={}", user);
        return userRepository.insert(user);
    }

    @Override
    public void updateUser(User user) {
        log.debug("updateUser: user={}", user);
        userRepository.save(user);
    }
}
