package me.lemuel.adore.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lemuel on 2017/05/08.
 */
public class OnlineMusic implements Parcelable{

    @SerializedName("pic_big")
    private String pic_big;
    @SerializedName("pic_small")
    private String pic_small;
    @SerializedName("lrclink")
    private String lrclink;
    @SerializedName("song_id")
    private String song_id;
    @SerializedName("title")
    private String title;
    @SerializedName("ting_uid")
    private String ting_uid;
    @SerializedName("album_title")
    private String album_title;
    @SerializedName("artist_name")
    private String artist_name;

    public OnlineMusic() {
        super();
    }

    protected OnlineMusic(Parcel in) {
        pic_big = in.readString();
        pic_small = in.readString();
        lrclink = in.readString();
        song_id = in.readString();
        title = in.readString();
        ting_uid = in.readString();
        album_title = in.readString();
        artist_name = in.readString();
    }

    public static final Creator<OnlineMusic> CREATOR = new Creator<OnlineMusic>() {
        @Override
        public OnlineMusic createFromParcel(Parcel in) {
            return new OnlineMusic(in);
        }

        @Override
        public OnlineMusic[] newArray(int size) {
            return new OnlineMusic[size];
        }
    };

    public String getPic_big() {
        return pic_big;
    }

    public void setPic_big(String pic_big) {
        this.pic_big = pic_big;
    }

    public String getPic_small() {
        return pic_small;
    }

    public void setPic_small(String pic_small) {
        this.pic_small = pic_small;
    }

    public String getLrclink() {
        return lrclink;
    }

    public void setLrclink(String lrclink) {
        this.lrclink = lrclink;
    }

    public String getSong_id() {
        return song_id;
    }

    public void setSong_id(String song_id) {
        this.song_id = song_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTing_uid() {
        return ting_uid;
    }

    public void setTing_uid(String ting_uid) {
        this.ting_uid = ting_uid;
    }

    public String getAlbum_title() {
        return album_title;
    }

    public void setAlbum_title(String album_title) {
        this.album_title = album_title;
    }

    public String getArtist_name() {
        return artist_name;
    }

    public void setArtist_name(String artist_name) {
        this.artist_name = artist_name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(pic_big);
        dest.writeString(pic_small);
        dest.writeString(lrclink);
        dest.writeString(song_id);
        dest.writeString(title);
        dest.writeString(ting_uid);
        dest.writeString(album_title);
        dest.writeString(artist_name);
    }
}

