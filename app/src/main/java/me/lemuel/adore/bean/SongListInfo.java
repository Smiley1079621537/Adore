package me.lemuel.adore.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lemuel on 2017/05/08.
 */
public class SongListInfo implements Parcelable{

    /**
     * #主打榜单
     * 1.新歌榜
     * 2.热歌榜
     * #分类榜单
     * 20.华语金曲榜
     * 21.欧美金曲榜
     * 24.影视金曲榜
     * 23.情歌对唱榜
     * 25.网络歌曲榜
     * 22.经典老歌榜
     * 11.摇滚榜
     * #媒体榜单
     * 6.KTV热歌榜
     * 8.Billboard
     * 18.Hito中文榜
     * 7.叱咤歌曲榜
     */
    private String title;
    private String type;
    private String coverUrl;
    private String music1;
    private String music2;
    private String music3;

    public SongListInfo(){

    }

    protected SongListInfo(Parcel in) {
        title = in.readString();
        type = in.readString();
        coverUrl = in.readString();
        music1 = in.readString();
        music2 = in.readString();
        music3 = in.readString();
    }

    public static final Creator<SongListInfo> CREATOR = new Creator<SongListInfo>() {
        @Override
        public SongListInfo createFromParcel(Parcel in) {
            return new SongListInfo(in);
        }

        @Override
        public SongListInfo[] newArray(int size) {
            return new SongListInfo[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getMusic1() {
        return music1;
    }

    public void setMusic1(String music1) {
        this.music1 = music1;
    }

    public String getMusic2() {
        return music2;
    }

    public void setMusic2(String music2) {
        this.music2 = music2;
    }

    public String getMusic3() {
        return music3;
    }

    public void setMusic3(String music3) {
        this.music3 = music3;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(type);
        dest.writeString(coverUrl);
        dest.writeString(music1);
        dest.writeString(music2);
        dest.writeString(music3);
    }
}