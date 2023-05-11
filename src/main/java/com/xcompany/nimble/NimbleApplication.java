package com.xcompany.nimble;

import com.xcompany.nimble.biz.gameplay.data.numeric.ConstNumeric;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NimbleApplication {

    public static void main(String[] args) {
        SpringApplication.run(NimbleApplication.class, args);
        System.out.println(ConstNumeric.ENERGY_DIAMOND_BUY_AMOUNT);
    }

}
