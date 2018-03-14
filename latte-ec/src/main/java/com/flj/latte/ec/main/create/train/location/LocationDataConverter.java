package com.flj.latte.ec.main.create.train.location;

import com.alibaba.fastjson.JSONArray;
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

    @Override
    public ArrayList<MultipleItemEntity> convert() {

        String jsonStr = LattePreference.getCustomAppProfile(TAG_LOCATION_HISTORY);
        if (!jsonStr.equals("")){
            final JSONArray array = JSONArray.parseArray(jsonStr);
            int size = array.size();
            for (int i = 0; i < size; i++) {
                String historyItemText = array.getString(i);
                final MultipleItemEntity entity = MultipleItemEntity.builder()
                        .setItemType(LocationItemType.ITEM_LOCATION)
                        .setField(MultipleFields.TEXT,historyItemText)
                        .build();
                ENTITIES.add(entity);
            }
        }

        return ENTITIES;
    }
}
