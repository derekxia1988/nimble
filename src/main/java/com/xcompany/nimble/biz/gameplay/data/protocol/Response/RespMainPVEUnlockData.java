package com.xcompany.nimble.biz.gameplay.data.protocol.Response;

import com.xcompany.nimble.biz.gameplay.data.mongo.Item;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class RespMainPVEUnlockData implements RespData {
    private final int nextStageId;
    private final int nextLevelId;
    private final List<Item> itemList;
}
