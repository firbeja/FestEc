package com.flj.latte.ui.recycler;


import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;



public abstract class DataConverter {

    protected final ArrayList<MultipleItemEntity> ENTITIES = new ArrayList<>();
    private String mJsonData = null;
    private JSONArray mJsonArrayData = null;
    private List mList = null;
    private Map mMap = null;

    public abstract ArrayList<MultipleItemEntity> convert();

    public DataConverter setJsonData(String json) {
        this.mJsonData = json;
        return this;
    }

    public DataConverter setJsonArrayData(JSONArray json) {
        this.mJsonArrayData = json;
        return this;
    }

    public DataConverter setListData(List data) {
        this.mList = data;
        return this;
    }

    public DataConverter setMapData(Map data) {
        this.mMap = data;
        return this;
    }

    protected String getJsonData() {
        if (mJsonData == null || mJsonData.isEmpty()) {
            throw new NullPointerException("DATA IS NULL!");
        }
        return mJsonData;
    }

    protected JSONArray getJsonArrayData() {
        if (mJsonArrayData == null) {
            throw new NullPointerException("DATA IS NULL!");
        }
        return mJsonArrayData;
    }

    protected List getListData() {
        if (mList == null || mList.isEmpty()){
            throw new NullPointerException("DATA IS NULL!");
        }
        return mList;
    }

    protected Map getMapData() {
        if (mMap == null || mMap.isEmpty()){
            throw new NullPointerException("DATA IS NULL!");
        }
        return mMap;
    }

    public void clearData() {
        ENTITIES.clear();
    }
}
