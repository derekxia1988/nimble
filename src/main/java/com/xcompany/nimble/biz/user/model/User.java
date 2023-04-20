package com.xcompany.nimble.biz.user.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("user")
@Data
@Builder
public class User {
    @Id
    private String id;
    @Indexed
    private String name;
    private String email;
    private int age;
}
