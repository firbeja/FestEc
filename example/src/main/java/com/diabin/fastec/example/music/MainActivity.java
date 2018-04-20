package com.diabin.fastec.example.music;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.diabin.fastec.example.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static String listviewname = null;
    private static int currentIndex = 0;
    private static List<Music> musicList = new ArrayList<>();
    private static EditText nameText;
    private static Iservice iservice;
    private Myconn conn;
    private static SeekBar sbar;
    private TextView text;
    private Object[] objectses = {"a", "b", "c", "d", "e", "f", "gg", "s", "q", "s", "q", "s", "q", "s", "q", "s", "q", "s", "q", "s", "q"};


    public static Handler handler1 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            currentIndex = data.getInt("currentIndex");
            nameText.setText(musicList.get(currentIndex).getNameChinese() + ".mp3");
        }
    };

    public static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            Bundle data = msg.getData();
            //获取歌曲的总时长和当前进度
            int duration = data.getInt("duration");
            int currentPosition = data.getInt("currentPosition");

            //设置seekBar进度
            sbar.setMax(duration);
            sbar.setProgress(currentPosition);

            //给seekBar设置点击事件
            sbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //混合方式开启服务
        Intent intent = new Intent(this, MusicService.class);
        startService(intent);
        conn = new Myconn();
        bindService(intent, conn, BIND_AUTO_CREATE);

        sbar = (SeekBar) findViewById(R.id.seekBar1);
        nameText = (EditText) findViewById(R.id.filename);

        //----------listview
        initMusics();
        listviewname = musicList.get(0).getNameEnglish() + ".mp3";
        nameText.setText(musicList.get(currentIndex).getNameChinese());
        MusicAdapter adapter = new MusicAdapter(MainActivity.this, R.layout.music_item, musicList);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String listviewnameChinese = musicList.get(position).getNameChinese();
                listviewname = musicList.get(position).getNameEnglish() + ".mp3";
                nameText.setText(listviewnameChinese);
                currentIndex = position;
            }
        });

        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        telephonyManager.listen(new MyPhoneListener(),PhoneStateListener.LISTEN_CALL_STATE);

        ScanMusic scanMusic = new ScanMusic();
        ArrayList<Model> query = scanMusic.query(this);
        Log.d("tagsongName", "songName : " + query.size());
        for (int i = 0; i < query.size(); i++) {
            Model model = query.get(i);
            String text_song = model.getText_song();
            String path = model.getPath();
            Log.d("tagsongName", "text_song : " + text_song
            +"    path : " + path);
        }
    }


    private final class MyPhoneListener extends PhoneStateListener{
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            switch (state){
                case TelephonyManager.CALL_STATE_RINGING:
                    if (iservice!=null){
                    iservice.callPauseMusic();}
                    break;
                case TelephonyManager.CALL_STATE_IDLE:
                    if (iservice!=null){
                    iservice.callRePlayMusic();}
                    break;
                default:
                    break;
            }
        }
    }

    private void initMusics() {
        Music a = new Music("a", "张小英 - 万年红");
        musicList.add(a);
        Music b = new Music("b", "韩宝仪 - 恭喜大家好");
        musicList.add(b);
        Music c = new Music("c", "刘德华 - 恭喜发财");
        musicList.add(c);
        Music d = new Music("d", "宋祖英 - 好日子");
        musicList.add(d);
        Music e = new Music("e", "张小英 - 恭喜发财");
        musicList.add(e);
        Music f = new Music("f", "张小英 - 新年好");
        musicList.add(f);
        Music g = new Music("g", "祖海 - 好运来");
        musicList.add(g);
        Music h = new Music("h", "其它");
        musicList.add(h);
        Music i = new Music("i", "其它");
        musicList.add(i);
        Music h1 = new Music("h", "其它");
        musicList.add(h1);
        Music i1 = new Music("i", "其它");
        musicList.add(i1);
        Music h2 = new Music("h", "其它");
        musicList.add(h2);
        Music i2 = new Music("i", "其它");
        musicList.add(i2);
        Music h3 = new Music("h", "其它");
        musicList.add(h3);
        Music i3 = new Music("i", "其它");
        musicList.add(i3);

    }



    @Override
    protected void onDestroy() {
        unbindService(conn);
        super.onDestroy();
    }

    public void click1(View v) {
        iservice.callPlayMusic(this, listviewname, currentIndex, musicList);
    }

    public void click2(View v) {
        iservice.callPauseMusic();
    }

    public void click3(View v) {
        iservice.callRePlayMusic();
    }

    public void click4(View v) {
        currentIndex++;
        String nameEnglish = musicList.get(currentIndex).getNameEnglish() + ".mp3";
        nameText.setText(musicList.get(currentIndex).getNameChinese());
        iservice.callNextMusic(this, nameEnglish, currentIndex, musicList);
    }

    private class Myconn implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iservice = (Iservice) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }

    public class MusicAdapter extends ArrayAdapter<Music> {

        private int resourceId;

        public MusicAdapter(Context context, int textViewResourceId, List<Music> objects) {
            super(context, textViewResourceId, objects);
            resourceId = textViewResourceId;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Music music = getItem(position);
            View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            TextView musicChinese = (TextView) view.findViewById(R.id.misic_chinese);
            TextView musicEnglish = (TextView) view.findViewById(R.id.music_english);
            musicChinese.setText(music.getNameChinese());
            musicEnglish.setText(music.getNameEnglish());

            return view;
        }
    }

}
