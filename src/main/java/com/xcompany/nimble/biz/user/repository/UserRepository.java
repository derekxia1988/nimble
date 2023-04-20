package com.xcompany.nimble.biz.user.repository;

import com.xcompany.nimble.biz.user.model.User;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    private final MongoTemplate mongoTemplate;

    public UserRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public User findByName(String name) {
        return mongoTemplate.findOne(Query.query(Criteria.where("name").is(name)), User.class);
    }

    public User insert(User user) {
        return mongoTemplate.insert(user);
    }

    public User findById(String id) {
        return mongoTemplate.findById(id, User.class);
    }

    public void save(User user) {
        mongoTemplate.save(user);
    }
}
