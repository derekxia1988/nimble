package com.xcompany.nimble.biz.gameplay.service.manager;

import com.google.common.collect.Lists;
import com.xcompany.nimble.biz.gameplay.data.mongo.Item;
import com.xcompany.nimble.biz.gameplay.data.mongo.Player;
import com.xcompany.nimble.biz.gameplay.data.numeric.RewardNumeric;
import com.xcompany.nimble.biz.numeric.Numerics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RewardManager {
    @Autowired
    private ItemManager itemManager;

    public List<Item> reward(Player player, int[] rewardIds, float[] params){
        List<Item> itemList = Lists.newArrayList();
        for(int rewardId : rewardIds){
            RewardNumeric rewardNumeric = Numerics.get(RewardNumeric.class, rewardId).get();
            if(rewardNumeric.getType() == 1){
                int count = rewardNumeric.getCount() + (int)(rewardNumeric.getFactors()[0] * params[0]);
                itemManager.incr(player, String.valueOf(rewardNumeric.getItemId()), count);
                itemList.add(Item.builder().id(rewardNumeric.getItemId()).count(count).build());
            }
        }

        return itemList;
    }
}
