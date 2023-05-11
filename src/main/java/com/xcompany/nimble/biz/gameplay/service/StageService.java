package com.xcompany.nimble.biz.gameplay.service;

import com.xcompany.nimble.base.net.ws.WSRespEvent;
import com.xcompany.nimble.biz.gameplay.data.mongo.Player;
import com.xcompany.nimble.biz.gameplay.data.mongo.stage.StageType;
import com.xcompany.nimble.biz.gameplay.data.numeric.LevelNumeric;
import com.xcompany.nimble.biz.gameplay.data.numeric.StageNumeric;
import com.xcompany.nimble.biz.gameplay.data.protocol.Request.ReqLoginData;
import com.xcompany.nimble.biz.gameplay.data.protocol.Request.ReqMainPVEUnlockData;
import com.xcompany.nimble.biz.gameplay.data.protocol.Response.RespBase;
import com.xcompany.nimble.biz.gameplay.data.protocol.Response.RespMainPVEUnlockData;
import com.xcompany.nimble.biz.gameplay.data.protocol.Response.RespOpCode;
import com.xcompany.nimble.biz.numeric.Numerics;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class StageService extends BaseService {

    @EventListener(ReqMainPVEUnlockData.class)
    public void unlockStage(ReqMainPVEUnlockData reqMainPVEUnlock) {
        Player player = reqMainPVEUnlock.getPlayer();
        int stageId = player.getStageProgressMap().get(String.valueOf(StageType.MAIN_PVE_STAGE.getTypeCode()));
        int levelId = stageId / 100 % 10000;
        LevelNumeric levelNumeric = Numerics.get(LevelNumeric.class, levelId).get();
        int[] stageIdList = levelNumeric.getStage();

        int nextStageId = -1;
        int nextLevelId = -1;

        for(int i = 0; i < stageIdList.length; i++){
            if(stageIdList[i] == stageId) {
                if (i == stageIdList.length - 1) {
                    nextLevelId = levelId + 1;
                    LevelNumeric nextLevelNumeric = Numerics.get(LevelNumeric.class, nextLevelId).get();
                    nextStageId = nextLevelNumeric.getStage()[0];
                } else {
                    nextStageId = stageIdList[i + 1];
                    nextLevelId = levelId;
                }

                break;
            }
        }

        player.getStageProgressMap().put(String.valueOf(StageType.MAIN_PVE_STAGE.getTypeCode()), nextStageId);
        this.rewardManager.reward(player, Numerics.get(StageNumeric.class, stageId).get().getRewards(), new float[]{levelId});

        RespMainPVEUnlockData respMainPVEUnlock = RespMainPVEUnlockData.builder().nextStageId(nextStageId).nextLevelId(nextLevelId).build();
        RespBase respBase = RespBase.builder().pid(player.getId()).opCode(RespOpCode.STAGE_UNLOCK.getOpCode()).respData(respMainPVEUnlock).build();

        this.publisher.publishEvent(new WSRespEvent(this, player.getId(), respBase));
    }
}
