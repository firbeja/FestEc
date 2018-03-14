package com.flj.latte.ui.recycler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 傅令杰
 */

public abstract class DataConverter {

    protected final ArrayList<MultipleItemEntity> ENTITIES = new ArrayList<>();
    private String mJsonData = null;
    private List mList = null;

    public abstract ArrayList<MultipleItemEntity> convert();

    public DataConverter setJsonData(String json) {
        this.mJsonData = json;
        return this;
    }

    public DataConverter setListData(List data) {
        this.mList = data;
        return this;
    }

    protected String getJsonData() {
        if (mJsonData == null || mJsonData.isEmpty()) {
            throw new NullPointerException("DATA IS NULL!");
        }
        return mJsonData;
    }

    protected List getListData() {
        if (mList == null || mList.isEmpty()){
            throw new NullPointerException("DATA IS NULL!");
        }
        return mList;
    }

    public void clearData() {
        ENTITIES.clear();
    }
}
