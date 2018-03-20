package com.flj.latte.ec.main.music;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.Message;
import android.widget.Toast;

import java.io.File;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MusicService extends Service {

    private int c = 0;
    private MediaPlayer mediaPlayer;
    private String mPath;
    private List<Model> mMusicList;

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    @Override
    public void onCreate() {
        mediaPlayer = new MediaPlayer();
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void playMusic(String path, final int currentIndex, final List<Model> musicList) {
        this.mMusicList = musicList;
        this.mPath = path;
        c = currentIndex;
        c++;
        try {
//            mediaPlayer.setDataSource("/mnt/sdcard/gg.mp3");
            File audio = new File(path);
            if (audio.exists()) {
                mediaPlayer.reset();
                path = audio.getAbsolutePath();
                mediaPlayer.setDataSource(path);
            } else {
                path = null;
                Toast.makeText(getApplicationContext(), path + ".mp3没有发现", Toast.LENGTH_SHORT).show();
            }
            mediaPlayer.prepare();
            mediaPlayer.start();

            //更新进度条
            updataSeekBar();


            final int finalC = c;
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
//                    Message msg1 = Message.obtain();
//                    Bundle bundle1 = new Bundle();//map
//                    bundle1.putInt("currentIndex", c);
//                    msg1.setData(bundle1);
//                    //发送一条消息 MainActivity里的handlemessage方法就会执行
//                    MainActivity.handler1.sendMessage(msg1);
//                    String nameEnglish = musicList.get(finalC).getNameEnglish() + ".mp3";
//                    playMusic(context, nameEnglish, finalC, musicList);

//                    Message msg = Message.obtain();
//                    msg.what = 1;
//                    Bundle bundle1 = new Bundle();//map
//                    bundle1.putInt("currentIndex", c);
//                    msg.setData(bundle1);
//                    //发送一条消息 MainActivity里的handlemessage方法就会执行
//                    MusicDelegate.handler.sendMessage(msg);
//                    String nameEnglish = musicList.get(finalC).getNameEnglish() + ".mp3";
//                    playMusic(context, nameEnglish, finalC, musicList);

                    Intent intent = new Intent("com.example.musicdemo.SONG_CHANGE");
                    Bundle bundle1 = new Bundle();//map
                    bundle1.putInt("currentIndex", c);
                    intent.putExtras(bundle1);
                    sendBroadcast(intent);
                    String path = musicList.get(finalC).getPath();
                    playMusic(path, finalC, musicList);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pauseMusic() {
        mediaPlayer.pause();
    }

    public void rePlayMusic() {
        mediaPlayer.start();
    }

    public void seekTo(int position) {
        mediaPlayer.seekTo(position);
    }

    //更新进度条的方法
    private void updataSeekBar() {
        //[1]获取到当前播放的总长度
        //[2]使用Timer 定时器去定时获取当前进度
        final Timer timer = new Timer();
        final TimerTask task = new TimerTask() {
            @Override
            public void run() {
                //一秒钟获取一次当前进度
                int duration = mediaPlayer.getDuration();
                int currentPosition = mediaPlayer.getCurrentPosition();
                //拿着我们在MainActivity 创建的handle 发消息 消息就可以携带数据
                Message msg = Message.obtain();
                msg.what = 2;
                Bundle bundle = new Bundle();//map
                int a= c-1;
                bundle.putString("path",mPath);
                bundle.putInt("currentIndex",a);
                bundle.putInt("duration", duration);
                bundle.putInt("currentPosition", currentPosition);
                msg.setData(bundle);
                //发送一条消息 MainActivity里的handlemessage方法就会执行
//                MusicDelegate.handler.sendMessage(msg);
                Intent intent = new Intent("com.example.musicdemo.DATA_CHANGE");
                intent.putExtras(bundle);
                sendBroadcast(intent);
            }
        };
        //100毫秒后 每隔一秒执行一次run方法
        timer.schedule(task, 100, 300);
        //当歌曲播放完毕后 把timer 和timertask取消
        //设置播放完成的监听
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                //把timer 和 timertask取消
                timer.cancel();
                task.cancel();
            }
        });
    }



    //在服务的内部定义一个中间人对象（Iinder）
    private class MyBinder extends Binder implements Iservice {
        @Override
        public void callPlayMusic(String path, int currentIndex, List<Model> musicList) {
            playMusic(path, currentIndex, musicList);
        }

        @Override
        public void callPauseMusic() {
            pauseMusic();
        }

        @Override
        public void callRePlayMusic() {
            rePlayMusic();
        }

        @Override
        public void callSeekTo(int position) {
            seekTo(position);
        }

        @Override
        public void callNextMusic(String path, int currentIndex, List<Model> musicList) {
            playMusic(path, currentIndex, musicList);
        }
    }

}
