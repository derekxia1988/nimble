package com.xcompany.nimble.biz.gameplay;

import com.xcompany.nimble.biz.gameplay.service.PlayerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PlayerServiceTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PlayerService playerService;

    @Test
    void login(){
//        Player player = playerService.login("234");
//        Assert.assertNotNull(player);
    }
}
