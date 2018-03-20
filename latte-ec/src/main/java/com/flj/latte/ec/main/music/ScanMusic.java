package com.flj.latte.ec.main.music;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by LB-john on 2018/3/18.
 */

public class ScanMusic {
    public ArrayList<Model> query(Context mContext) {
        //创建ArryList
        ArrayList<Model>arrayList;
        //实例化ArryList对象
        arrayList = new ArrayList<Model>();
        //创建一个扫描游标
        Log.d("getContentResolver", "mContext.getContentResolver() :  " + mContext.getContentResolver());
        Cursor c=mContext.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,null);
        boolean e = c == null? true:false;
        Log.d("getContentResolver", "c :" + e);
        if(c!=null)
        {

            //创建Model对象
            Model model;
            //循环读取
            //实例化Model对象
            Log.d("getContentResolver", "c not null");
            while(c.moveToNext()){

                model = new Model();
                //扫描本地文件，得到歌曲的相关信息
                String music_name=c.getString(c.getColumnIndex(MediaStore.Audio.Media.TITLE));
                String music_singer=c.getString(c.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                String path = c.getString(c.getColumnIndex(MediaStore.Audio.Media.DATA));
                Log.d("getContentResolver", "music_name : " + music_name);

                //设置值到Model的封装类中
                model.setText_song(music_name);
                model.setText_singer(music_singer);
                model.setPath(path);
                //将model值加入到数组中
                arrayList.add(model);

            }
            //打印出数组的长度
            System.out.println(arrayList.size());

        }
        //得到一个数组的返回值
        return arrayList;

    }




}
