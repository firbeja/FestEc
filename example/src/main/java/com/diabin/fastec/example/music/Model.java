package com.diabin.fastec.example.music;

/**
 * Created by LB-john on 2018/3/18.
 */

public class Model {
    String text_song;
    public String getText_song() {
        return text_song;
    }
    public void setText_song(String text_song) {
        this.text_song = text_song;
    }
    public String getText_singer() {
        return text_singer;
    }
    public void setText_singer(String text_singer) {
        this.text_singer = text_singer;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    String text_singer;
    String path;

}
