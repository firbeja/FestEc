package com.diabin.fastec.example.music;

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

    private int c;
    private MediaPlayer mediaPlayer;
    private String path;

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

    public void seekTo(int position) {
        mediaPlayer.seekTo(position);
    }

    public void playMusic(final Context context, String filenameMusic, final int currentIndex, final List<Music> musicList) {
        c = currentIndex;
        c++;
        try {
//            mediaPlayer.setDataSource("/mnt/sdcard/gg.mp3");
            File audio = new File(Environment.getExternalStorageDirectory(), filenameMusic);
            if (audio.exists()) {
                mediaPlayer.reset();
                path = audio.getAbsolutePath();
                mediaPlayer.setDataSource(path);
            } else {
                path = null;
                Toast.makeText(getApplicationContext(), filenameMusic + ".mp3没有发现", Toast.LENGTH_SHORT).show();
            }
            mediaPlayer.prepare();
            mediaPlayer.start();

            //更新进度条
            updataSeekBar();


            final int finalC = c;
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    Message msg1 = Message.obtain();
                    Bundle bundle1 = new Bundle();//map
                    bundle1.putInt("currentIndex", c);
                    msg1.setData(bundle1);
                    //发送一条消息 MainActivity里的handlemessage方法就会执行
                    MainActivity.handler1.sendMessage(msg1);
                    String nameEnglish = musicList.get(finalC).getNameEnglish() + ".mp3";
                    playMusic(context, nameEnglish, finalC, musicList);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                Bundle bundle = new Bundle();//map
                bundle.putInt("duration", duration);
                bundle.putInt("currentPosition", currentPosition);
                msg.setData(bundle);
                //发送一条消息 MainActivity里的handlemessage方法就会执行
                MainActivity.handler.sendMessage(msg);
            }
        };
        //100毫秒后 每隔一秒执行一次run方法
        timer.schedule(task, 100, 1000);
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

    public void pauseMusic() {
        mediaPlayer.pause();
    }

    public void rePlayMusic() {
        mediaPlayer.start();
    }

    //在服务的内部定义一个中间人对象（Iinder）
    private class MyBinder extends Binder implements Iservice {
        @Override
        public void callPlayMusic(Context context, String filename, int currentIndex, List<Music> musicList) {
            playMusic(context, filename, currentIndex, musicList);
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
        public void callNextMusic(Context context, String filename, int currentIndex, List<Music> musicList) {
            playMusic(context, filename, currentIndex, musicList);
        }
    }

}
