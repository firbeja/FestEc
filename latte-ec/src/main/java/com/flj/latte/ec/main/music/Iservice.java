package com.flj.latte.ec.main.music;

import android.content.Context;

import java.util.List;

/**
 * Created by john on 2017/11/19.
 */

public interface Iservice {

    //把想暴露的方法都暴露在接口中
    public void callPlayMusic(String path, int currentIndex, List<Model> musicList);
    public void callPauseMusic();
    public void callRePlayMusic();
    public void callSeekTo(int position);
    public void callNextMusic(String path, int currentIndex, List<Model> musicList);

}
