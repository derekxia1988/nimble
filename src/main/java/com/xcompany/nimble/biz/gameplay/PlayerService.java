package com.xcompany.nimble.biz.gameplay;

import com.xcompany.nimble.biz.data.Player;
import com.xcompany.nimble.biz.db.PlayerRepository;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Player login(String id){
        if(!this.playerRepository.exist(id)) {
            this.createPlayer(id);
        }

        return this.playerRepository.findById(id);
    }

    private void createPlayer(String name){
        Player player = Player.builder().id(name).build();
        player.init();
        this.playerRepository.insert(player);
    }
}
