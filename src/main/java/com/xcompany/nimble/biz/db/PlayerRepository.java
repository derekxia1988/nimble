package com.xcompany.nimble.biz.db;

import com.xcompany.nimble.biz.data.Player;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class PlayerRepository {
    private final MongoTemplate mongoTemplate;

    public PlayerRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public Player findByName(String name) {
        return mongoTemplate.findOne(Query.query(Criteria.where("name").is(name)), Player.class);
    }

    public boolean exist(String id) {
        return mongoTemplate.exists(Query.query(Criteria.where("id").is(id)), Player.class);
    }

    public Player insert(Player player) {
        return mongoTemplate.insert(player);
    }

    public Player findById(String id) {
        return mongoTemplate.findById(id, Player.class);
    }

    public void save(Player player) {
        mongoTemplate.save(player);
    }
}
