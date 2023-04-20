package com.xcompany.nimble.biz.user.controller;

import com.xcompany.nimble.biz.user.model.User;
import com.xcompany.nimble.biz.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Duration;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    private RedisTemplate<String, String> redisTemplate;
    @Mock
    private ValueOperations<String, String> opsForValue;

    @Test
    void createUser() throws Exception {
        doReturn(opsForValue)
                .when(redisTemplate)
                .opsForValue();
        doReturn(true)
                .when(opsForValue)
                .setIfAbsent(anyString(), anyString(), any(Duration.class));
        doReturn(User.builder().id("1").name("abc").email("abc@xcompany.com").age(20).build())
                .when(userService)
                .createUser(any(User.class));
        mockMvc.perform(put("/user/abc"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\"code\":\"0\",\"data\":{\"id\":\"1\",\"name\":\"abc\",\"email\":\"abc@xcompany.com\",\"age\":20}}"));
    }

    @Test
    void createUserExist() throws Exception {
        doReturn(opsForValue)
                .when(redisTemplate)
                .opsForValue();
        doReturn(false)
                .when(opsForValue)
                .setIfAbsent(anyString(), anyString(), any(Duration.class));
        mockMvc.perform(put("/user/abc"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"code\":\"2001\"")));
    }

    @Test
    void findUser() throws Exception {
        doReturn(User.builder().id("1").name("abc").email("abc@xcompany.com").age(20).build())
                .when(userService)
                .findUserByName(anyString());
        mockMvc.perform(get("/user/abc"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\"code\":\"0\",\"data\":{\"id\":\"1\",\"name\":\"abc\",\"email\":\"abc@xcompany.com\",\"age\":20}}"));
    }

    @Test
    void updateProfile() throws Exception {
        doReturn(User.builder().id("1").name("abc").email("abc@xcompany.com").age(20).build())
                .when(userService)
                .findUserById(anyString());
        mockMvc.perform(
                        post("/user/updateProfile")
                                .contentType("application/json")
                                .content("{\"id\":\"1\",\"name\":\"abc\",\"email\":\"abcd@xcompany.com\",\"age\": 21}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\"code\":\"0\",\"data\":{\"id\":\"1\",\"name\":\"abc\",\"email\":\"abcd@xcompany.com\",\"age\": 21}}"));
    }
}