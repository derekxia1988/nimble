package com.xcompany.nimble.biz.user.service.impl;

import com.xcompany.nimble.biz.exception.BizErrorCode;
import com.xcompany.nimble.biz.user.model.User;
import com.xcompany.nimble.biz.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static com.xcompany.nimble.test.util.TestUtils.assertBizError;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void findUserById() {
        doAnswer(invocation -> {
            String id = invocation.getArgument(0);
            return User.builder().id(id).name("abc").build();
        }).when(userRepository).findById(anyString());
        var user = userService.findUserById("1");
        assertEquals("1", user.getId());
        assertEquals("abc", user.getName());
    }

    @Test
    void findUserByIdNotExist() {
        doReturn(null).when(userRepository).findById(anyString());
        assertBizError(BizErrorCode.USER_NOT_FOUND, () -> userService.findUserById("1"));
    }

    @Test
    void findUserByName() {
        doAnswer(invocation -> {
            String name = invocation.getArgument(0);
            return User.builder().id("1").name(name).build();
        }).when(userRepository).findByName(anyString());
        var user = userService.findUserByName("abc");
        assertEquals("1", user.getId());
        assertEquals("abc", user.getName());
    }

    @Test
    void findUserByNameNotExist() {
        doReturn(null).when(userRepository).findByName(anyString());
        assertBizError(BizErrorCode.USER_NOT_FOUND, () -> userService.findUserByName("abc"));
    }

    @Test
    void createUser() {
        doAnswer(invocation -> {
            User user = invocation.getArgument(0);
            return User.builder().id("1").name(user.getName()).build();
        }).when(userRepository).insert(any());
        var user = userService.createUser(User.builder().name("abc").build());
        assertEquals("1", user.getId());
        assertEquals("abc", user.getName());
    }

    @Test
    void updateUser() {
        doNothing().when(userRepository).save(any());
        assertDoesNotThrow(() -> userService.updateUser(User.builder().id("1").name("abc").build()));
    }
}