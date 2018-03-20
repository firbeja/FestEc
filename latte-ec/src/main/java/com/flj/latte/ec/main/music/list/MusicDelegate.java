package com.flj.latte.ec.main.music.list;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.diabin.latte.ec.R;
import com.diabin.latte.ec.R2;
import com.flj.latte.app.Latte;
import com.flj.latte.delegates.LatteDelegate;
import com.flj.latte.delegates.bottom.BottomItemDelegate;
import com.flj.latte.ec.main.music.Iservice;
import com.flj.latte.ec.main.music.Model;
import com.flj.latte.ec.main.music.MusicService;
import com.flj.latte.ec.main.music.ScanMusic;
import com.flj.latte.ui.recycler.MultipleItemEntity;
import com.flj.latte.util.callback.CallbackManager;
import com.flj.latte.util.callback.CallbackType;
import com.flj.latte.util.callback.IGlobalCallback;
import com.flj.latte.util.log.LatteLogger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.content.Context.BIND_AUTO_CREATE;

/**
 * Created by LB-john on 2018/3/19.
 */

public class MusicDelegate extends BottomItemDelegate {


    private SongReceiver songReceiver;
    private String path;
    private int currentIndex;
    private int duration;
    private int currentPosition;

    @BindView(R2.id.rl_music_list)
    RecyclerView mRecyclerView = null;
    @BindView(R2.id.tv_music_song)
    TextView songText = null;
    @BindView(R2.id.seekBar)
    AppCompatSeekBar seekBar = null;

    @OnClick(R2.id.tv_music_play)
    void onClickPlay() {
        if (iservice != null) {
            LatteLogger.d("tagtag", "path: " + path
                    + "\ncurrentIndex" + currentIndex);
            iservice.callPlayMusic(path, currentIndex, musicList);
        }
    }

    @OnClick(R2.id.tv_music_pause)
    void onClickPause() {
        if (iservice != null) {
            iservice.callPauseMusic();
        }
    }

    @OnClick(R2.id.tv_music_replay)
    void onClickRePlay() {
        if (iservice != null) {
            iservice.callRePlayMusic();
        }
    }

    @OnClick(R2.id.tv_music_next)
    void onClickNext() {
        currentIndex++;
        String name = musicList.get(currentIndex).getText_song();
        songText.setText(musicList.get(currentIndex).getText_song());
        path = musicList.get(currentIndex).getPath();
        iservice.callNextMusic(path, currentIndex, musicList);
    }

    private List<Model> musicList;
    private static Iservice iservice = null;
    private Myconn conn;
    private IntentFilter intentFilter;
    private IntentFilter songIntentFilter;
    private MyReceiver myReceiver;

    @Override
    public Object setLayout() {
        return R.layout.delegate_music;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //混合方式开启服务
        Intent intent = new Intent(Latte.getApplicationContext(), MusicService.class);
        Latte.getApplicationContext().startService(intent);
        conn = new Myconn();
        Latte.getApplicationContext().bindService(intent, conn, BIND_AUTO_CREATE);

        intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.musicdemo.DATA_CHANGE");
        myReceiver = new MyReceiver();
        getActivity().registerReceiver(myReceiver, intentFilter);

        songIntentFilter = new IntentFilter();
        songIntentFilter.addAction("com.example.musicdemo.SONG_CHANGE");
        songReceiver = new SongReceiver();
        getActivity().registerReceiver(songReceiver, songIntentFilter);

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        ScanMusic scanMusic = new ScanMusic();
        musicList = scanMusic.query(getContext());
        initRecyclerView();
    }

    @Override
    public void onDestroy() {
        getActivity().unbindService(conn);
        super.onDestroy();
        getActivity().unregisterReceiver(myReceiver);
        getActivity().unregisterReceiver(songReceiver);
    }

    private void initRecyclerView() {
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        final List<MultipleItemEntity> data = new MusicDataconverter().setListData(musicList).convert();
        final MusicAdapter addressAdapter = new MusicAdapter(data, new MusicAdapter.MusicAdapterListener() {
            @Override
            public void playMusic(int position, String path) {
                songText.setText(musicList.get(position).getText_song());
                iservice.callPlayMusic(path, position, musicList);
                IGlobalCallback callback = CallbackManager.getInstance().getCallback(CallbackType.PLAY_MUSIC);
                if (callback!=null){
                    callback.executeCallback(path);
                }
            }
        });
        mRecyclerView.setAdapter(addressAdapter);

    }

    private class Myconn implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iservice = (Iservice) service;
            LatteLogger.d("MusicService", " onServiceConnected() :"
                    + "\niservice : " + iservice);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }

    class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            path = bundle.getString("path");
            currentIndex = bundle.getInt("currentIndex");
            duration = bundle.getInt("duration");
            currentPosition = bundle.getInt("currentPosition");

            //设置seekBar进度
            seekBar.setMax(duration);
            seekBar.setProgress(currentPosition);
            //给seekBar设置点击事件
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                //当进度改变
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                }

                //当开始拖动
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                //当拖动停止的时候调用
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    iservice.callSeekTo(seekBar.getProgress());
                }
            });
        }
    }

    class SongReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            int currentIndex = bundle.getInt("currentIndex");
            Model model = musicList.get(currentIndex);
            if (model != null) {
                songText.setText(model.getText_song());
            }
        }
    }

}
