package com.xcompany.nimble.biz.numeric;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("numeric")
@Data
public class NumericProperties {
    private String path;
    private String packagePath;
}
