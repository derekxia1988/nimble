package com.xcompany.nimble.biz.gameplay.data.numeric;

import java.util.*;
import java.util.List;

public class ConstNumeric {
    /**
     * 每消耗1体力玩家经验长1
     */
    public static int ENERGY_TO_ROLE_EXP;

    /**
     * 玩家最大体力上限
     */
    public static int ENERGY_MAX;

    /**
     * 蛋包饭恢复体力的值
     */
    public static int ENERGY_ITEM_RESTORE_VALUE;

    /**
     * 玩家购买体力消耗的钻石数
     */
    public static int[] ENERGY_DIAMOND_BUY_COST;

    /**
     * 玩家用钻石购买一次获得的体力数
     */
    public static int ENERGY_DIAMOND_BUY_AMOUNT;

    /**
     * 体力自然回复1点所需的时间（秒）
     */
    public static int ENERGY_RESTORE_TIME;

    /**
     * 可恢复体力的道具id
     */
    public static int ENERGY_ITEM_ID;

    /**
     * 每天刷新refresh id（如无特殊情况都用这个）
     */
    public static int DEFAULT_DAILY_REFRESH_ID;

    /**
     * 邮件最大上限数量
     */
    public static int MAILBOX_SIZE_LIMIT;

    /**
     * 跑图最大可编入队伍的人数
     */
    public static int PARTY_MAX_HERO;

    /**
     * 支线任务接取个数上限
     */
    public static int QUEST_SIDE_LIMIT;

    /**
     * 兑换1个召唤券所需的钻石数量
     */
    public static int GACHA_TICKET_VALUE;

}