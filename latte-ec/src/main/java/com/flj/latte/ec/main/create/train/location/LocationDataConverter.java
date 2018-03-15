package com.flj.latte.ec.main.create.train.location;

import com.alibaba.fastjson.JSONArray;
import com.flj.latte.ec.main.create.HistoryType;
import com.flj.latte.ui.recycler.DataConverter;
import com.flj.latte.ui.recycler.MultipleFields;
import com.flj.latte.ui.recycler.MultipleItemEntity;
import com.flj.latte.util.storage.LattePreference;

import java.util.ArrayList;

/**
 * Created by LB-john on 2018/3/13.
 */

public class LocationDataConverter extends DataConverter {

    public static final String TAG_LOCATION_HISTORY = "location_history";
    public static final String TAG_OPPONENT_HISTORY = "opponent_history";
    public static final String TAG_COMPETITION_HISTORY = "competition_history";
    public static final String TAG_THEME_HISTORY = "theme_history";

    private int mTagHistory = 0;


    public LocationDataConverter(int tag) {
        this.mTagHistory = tag;
    }

    @Override
    public ArrayList<MultipleItemEntity> convert() {
        String jsonStr = null;

        if (mTagHistory == HistoryType.OPPONENT){
            jsonStr = LattePreference.getCustomAppProfile(LocationDataConverter.TAG_OPPONENT_HISTORY);
        }else if(mTagHistory == HistoryType.LOCATION){
            jsonStr = LattePreference.getCustomAppProfile(LocationDataConverter.TAG_LOCATION_HISTORY);
        }else if (mTagHistory == HistoryType.COMPETITION){
            jsonStr = LattePreference.getCustomAppProfile(LocationDataConverter.TAG_COMPETITION_HISTORY);
        }else if (mTagHistory == HistoryType.THEME){
            jsonStr = LattePreference.getCustomAppProfile(LocationDataConverter.TAG_THEME_HISTORY);
        }


        if (!jsonStr.equals("")) {
            final JSONArray array = JSONArray.parseArray(jsonStr);
            int size = array.size();
            for (int i = 0; i < size; i++) {
                String historyItemText = array.getString(i);
                final MultipleItemEntity entity = MultipleItemEntity.builder()
                        .setItemType(LocationItemType.ITEM_LOCATION)
                        .setField(MultipleFields.TEXT, historyItemText)
                        .build();
                ENTITIES.add(entity);
            }
        }

        return ENTITIES;
    }
}
