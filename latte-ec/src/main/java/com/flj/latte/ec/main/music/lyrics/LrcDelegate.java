package com.flj.latte.ec.main.music.lyrics;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.diabin.latte.ec.R;
import com.diabin.latte.ec.R2;
import com.flj.latte.delegates.LatteDelegate;
import com.flj.latte.util.callback.CallbackManager;
import com.flj.latte.util.callback.CallbackType;
import com.flj.latte.util.callback.IGlobalCallback;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import me.wcy.lrcview.LrcView;

/**
 * Created by LB-john on 2018/3/20.
 */

public class LrcDelegate extends LatteDelegate {

    @BindView(R2.id.lrc_view)
    LrcView lrcView = null;
//    @BindView(R2.id.img_view)
//    ImageView imgView = null;

    private IntentFilter intentFilter;
    private MyReceiver myReceiver;
    private String path;


    @Override
    public Object setLayout() {
        return R.layout.delegate_lrc;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        CallbackManager.getInstance().addCallback(CallbackType.PLAY_MUSIC, new IGlobalCallback() {
            @Override
            public void executeCallback(@Nullable Object args) {
                String path = (String) args;
                int lastIndexOfRightBracket = path.lastIndexOf("/");
                String filename = path.substring(lastIndexOfRightBracket + 1, path.length());
                final String replace = filename.replace(".mp3", ".lrc");
                //歌词
                lrcView.loadLrc(getLrcText(replace));
            }
        });
    }


    @SuppressLint("SdCardPath")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.musicdemo.DATA_CHANGE");
        myReceiver = new MyReceiver();
        getActivity().registerReceiver(myReceiver, intentFilter);

//        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.img_animation);
//        LinearInterpolator lin = new LinearInterpolator();
//        animation.setInterpolator(lin);
//        imgView.startAnimation(animation);
    }

    private String getLrcText(String fileName) {
        String lrcText = null;
        try {
            File file = new File(Environment.getExternalStorageDirectory(), fileName);

//            InputStream is = getActivity().getAssets().open(fileName);
            InputStream is = new FileInputStream(file);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            lrcText = new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lrcText;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(myReceiver);
    }

    class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            path = bundle.getString("path");
            int duration = bundle.getInt("duration");
            int currentPosition = bundle.getInt("currentPosition");
            Log.d("tagtag", "onReceive: duration :" + duration);
            //歌词
            lrcView.updateTime(currentPosition);
        }
    }

}


