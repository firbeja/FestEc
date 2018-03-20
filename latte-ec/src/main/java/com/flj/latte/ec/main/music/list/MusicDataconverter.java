package com.flj.latte.ec.main.music.list;

import android.util.Log;

import com.flj.latte.ec.main.music.Model;
import com.flj.latte.ui.recycler.DataConverter;
import com.flj.latte.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LB-john on 2018/3/19.
 */

public class MusicDataconverter extends DataConverter {
    @Override
    public ArrayList<MultipleItemEntity> convert() {
        List<Model> musicList = getListData();
        int size = musicList.size();
        for (int i = 0; i < size; i++) {
            Model model = musicList.get(i);
            String textSong = model.getText_song();
            String textSinger = model.getText_singer();
            String path = model.getPath();

            Log.d("tagsongName", "text_song : " + textSong
                    + "    path : " + path
                    + "  -  singer : " + textSinger);

            MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setItemType(MusicItemType.ITEM_MUSIC)
                    .setField(MusicItemFields.song,textSong)
                    .setField(MusicItemFields.singer,textSinger)
                    .setField(MusicItemFields.path,path)
                    .build();
            ENTITIES.add(entity);
        }
        return ENTITIES;

    }
}
