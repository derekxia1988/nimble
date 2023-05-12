package com.xcompany.nimble.biz.db.cache;

import com.xcompany.nimble.biz.db.repository.PlayerRepository;
import com.xcompany.nimble.biz.gameplay.data.mongo.Player;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
@Log4j2
public class PlayerCache {
    private final ConcurrentHashMap<String, Player> playerMap = new ConcurrentHashMap<>();
    private final PlayerRepository playerRepository;
    @Autowired
    private ThreadPoolTaskScheduler scheduler;

    public PlayerCache(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @PostConstruct
    public void scheuleSave(){
        scheduler.scheduleAtFixedRate(
                ()->this.saveAll(),
                Duration.ofSeconds(60)
        );
    }

    @PreDestroy
    public void saveAll(){
        log.info("[Mongo] Start to save {} players.", this.playerMap.size());
        for (Player player : playerMap.values()) {
            try {
                this.playerRepository.save(player);
                log.info("[Mongo] Save player {} success.", player.getId());
            } catch (Exception e) {
                log.error("[Mongo] Save player {} fail.");
                e.printStackTrace();
            }
        }
    }

    public Player get(String id) {
        if (this.playerMap.getOrDefault(id, null) == null) {
            Player player = this.playerRepository.findById(id);

            if (player != null) {
                this.playerMap.put(id, player);
            }
        }

        return this.playerMap.get(id);
    }

    public boolean exist(String id) {
        return this.playerMap.containsKey(id) || this.playerRepository.exist(id);
    }

    public void create(String pid) {
        Player player = new Player(pid);
        player.init();
        this.playerRepository.insert(player);
    }
}
