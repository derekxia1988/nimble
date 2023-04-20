package com.xcompany.nimble.biz.user.controller;

import com.xcompany.nimble.biz.common.BizResponse;
import com.xcompany.nimble.biz.exception.BizErrorCode;
import com.xcompany.nimble.biz.user.model.User;
import com.xcompany.nimble.biz.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.xcompany.nimble.biz.common.RedisKey.USER_NAME;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final RedisTemplate<String, String> redisTemplate;

    @PutMapping("/{name}")
    public ResponseEntity<BizResponse<User>> createUser(@PathVariable String name) {
        var result = redisTemplate.opsForValue().setIfAbsent(
                USER_NAME.getKey(name), "1", USER_NAME.getExpire());
        if (!Boolean.TRUE.equals(result)) {
            throw BizErrorCode.USER_ALREADY_EXISTS.error(name);
        }
        var user = userService.createUser(User.builder().name(name).build());
        return ResponseEntity.ok(BizResponse.success(user));
    }

    @GetMapping("/{name}")
    public ResponseEntity<BizResponse<User>> findUser(@PathVariable String name) {
        var user = userService.findUserByName(name);
        return ResponseEntity.ok(BizResponse.success(user));
    }

    @PostMapping("/updateProfile")
    public ResponseEntity<BizResponse<User>> updateProfile(@RequestBody User user) {
        var result = userService.findUserById(user.getId());
        result.setEmail(user.getEmail());
        result.setAge(user.getAge());
        userService.updateUser(result);
        return ResponseEntity.ok(BizResponse.success(result));
    }
}
