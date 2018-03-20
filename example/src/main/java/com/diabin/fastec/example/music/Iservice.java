package com.diabin.fastec.example.music;

import android.content.Context;

import java.util.List;

/**
 * Created by john on 2017/11/19.
 */

public interface Iservice {

    //把想暴露的方法都暴露在接口中
    public void callPlayMusic(Context context, String filename, int currentIndex, List<Music> musicList);
    public void callPauseMusic();
    public void callRePlayMusic();
    public void callSeekTo(int position);
    public void callNextMusic(Context context, String filename, int currentIndex, List<Music> musicList);

}
