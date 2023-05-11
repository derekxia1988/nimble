package com.xcompany.nimble.biz.db;

import com.xcompany.nimble.biz.gameplay.data.mongo.Player;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;

@Repository
public class PlayerRepository {
    private final MongoTemplate mongoTemplate;

    private final ConcurrentHashMap<String, Player> playerMap = new ConcurrentHashMap<>();

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
        if(this.playerMap.getOrDefault(id, null) == null){
            Player player = this.mongoTemplate.findById(id, Player.class);

            if(player != null){
                this.playerMap.put(id, player);
            }
        }

        return this.playerMap.get(id);
    }

    public void save(Player player) {
        mongoTemplate.save(player);
    }
}
