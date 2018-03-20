package com.flj.latte.ec.main.music.list;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.diabin.latte.ec.R;
import com.flj.latte.ec.main.music.Iservice;
import com.flj.latte.ui.recycler.MultipleItemEntity;
import com.flj.latte.ui.recycler.MultipleRecyclerAdapter;
import com.flj.latte.ui.recycler.MultipleViewHolder;
import com.flj.latte.util.callback.CallbackManager;
import com.flj.latte.util.callback.CallbackType;
import com.flj.latte.util.callback.IGlobalCallback;
import com.flj.latte.util.log.LatteLogger;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by LB-john on 2018/3/19.
 */

public class MusicAdapter extends MultipleRecyclerAdapter {

    private MusicAdapterListener mListener;

    interface MusicAdapterListener {
        void playMusic(int position,String path);
    }

    protected MusicAdapter(List<MultipleItemEntity> data,MusicAdapterListener listener) {
        super(data);
        addItemType(MusicItemType.ITEM_MUSIC, R.layout.item_music);
        this.mListener = listener;
    }

    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {
        super.convert(holder, entity);
        switch (holder.getItemViewType()) {
            case MusicItemType.ITEM_MUSIC:
                String song = entity.getField(MusicItemFields.song);
                String singer = entity.getField(MusicItemFields.singer);
                final String path = entity.getField(MusicItemFields.path);

                TextView songText = holder.getView(R.id.tv_music_song);
                TextView singerText = holder.getView(R.id.tv_music_singer);
                LinearLayout musicItemView = holder.getView(R.id.ll_music_list);
                final int adapterPosition = holder.getAdapterPosition();

                songText.setText(song);
                singerText.setText(singer);
                musicItemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        MusicDelegate delegate = new MusicDelegate();
//                        delegate.playMusic(adapterPosition,path);
                        LatteLogger.d("MusicAdapterplayMusic","mListener : " + mListener);
                        if (mListener != null){
                            mListener.playMusic(adapterPosition,path);
                        }
                    }
                });
                break;
            default:
                break;
        }
    }
}
