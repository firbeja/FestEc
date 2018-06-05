package com.flj.latte.ec.main.ranking;

import com.flj.latte.ui.recycler.DataConverter;
import com.flj.latte.ui.recycler.MultipleFields;
import com.flj.latte.ui.recycler.MultipleItemEntity;
import com.flj.latte.util.log.LatteLogger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by LB-john on 2018/4/12.
 */

public class RankingDataConverter extends DataConverter {

    @Override
    public ArrayList<MultipleItemEntity> convert() {

        Map map = getMapData();
        JSONArray enter = (JSONArray) map.get("enter");
        final MultipleItemEntity enterEntity = MultipleItemEntity.builder()
                .setItemType(RankingItemType.Ranking)
                .setField(RankingItemFields.ENTERLIST,enter)
                .setField(RankingItemFields.TYPE,"报名排行")
                .build();

        JSONArray leave = (JSONArray) map.get("leave");
        final MultipleItemEntity leaveEntity = MultipleItemEntity.builder()
                .setItemType(RankingItemType.Ranking)
                .setField(RankingItemFields.ENTERLIST,leave)
                .setField(RankingItemFields.TYPE,"请假排行")
                .build();

        JSONArray pending = (JSONArray) map.get("pending");
        final MultipleItemEntity pendingEntity = MultipleItemEntity.builder()
                .setItemType(RankingItemType.Ranking)
                .setField(RankingItemFields.ENTERLIST,pending)
                .setField(RankingItemFields.TYPE,"待定排行")
                .build();

        ENTITIES.add(enterEntity);
        ENTITIES.add(leaveEntity);
        ENTITIES.add(pendingEntity);
        return ENTITIES;
    }
}
