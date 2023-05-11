package com.xcompany.nimble.biz.gameplay.data.protocol.Response;

import com.xcompany.nimble.biz.gameplay.data.mongo.Item;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Builder
@Data
public class RespItemData implements RespData {
    private Map<String, Item> itemMap;

}
