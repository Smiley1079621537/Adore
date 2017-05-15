package me.lemuel.adore.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.lemuel.adore.App;
import me.lemuel.adore.R;
import me.lemuel.adore.activity.OnlineMusicActivity;
import me.lemuel.adore.base.Extras;
import me.lemuel.adore.bean.music.OnlineMusic;
import me.lemuel.adore.bean.music.OnlineMusicList;
import me.lemuel.adore.bean.music.SongListInfo;

public class SongListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_PROFILE = 0;
    private static final int TYPE_MUSIC_LIST = 1;
    private ArrayList<SongListInfo> mData;
    private Context mContext;

    public SongListAdapter(ArrayList<SongListInfo> data) {
        mData = data;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder holder = null;
        mContext = parent.getContext();
        switch (viewType) {
            case TYPE_PROFILE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lable, parent, false);
                holder = new ViewHolderProfile(view);
                break;
            case TYPE_MUSIC_LIST:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song_list, parent, false);
                holder = new ViewHolderMusicList(view);
                break;
        }
        return holder;
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolderProfile) {
            ((ViewHolderProfile) holder).tvProfile.setText(mData.get(position).getTitle());
        } else if (holder instanceof ViewHolderMusicList) {
            getMusicListInfo(mData.get(position), (ViewHolderMusicList) holder);
            holder.itemView.setOnClickListener(v -> {
                SongListInfo songListInfo = mData.get(holder.getAdapterPosition());
                Intent intent = new Intent(mContext, OnlineMusicActivity.class);
                intent.putExtra(Extras.MUSIC_LIST_TYPE, songListInfo);
                mContext.startActivity(intent);
            });
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (mData.get(position).getType().equals("#")) {
            return TYPE_PROFILE;
        } else {
            return TYPE_MUSIC_LIST;
        }
    }

    private void getMusicListInfo(final SongListInfo songListInfoInfo, final ViewHolderMusicList holderMusicList) {
        if (songListInfoInfo.getCoverUrl() == null) {
            holderMusicList.ivCover.setTag(songListInfoInfo.getTitle());
            holderMusicList.ivCover.setImageURI(String.valueOf(R.drawable.default_cover));
            holderMusicList.tvMusic1.setText("加载中…");
            holderMusicList.tvMusic2.setText("加载中…");
            holderMusicList.tvMusic3.setText("加载中…");
            getOnlineMusic(songListInfoInfo, holderMusicList);
        } else {
            holderMusicList.ivCover.setTag(null);
            setData(songListInfoInfo, holderMusicList);
        }
    }

    private void getOnlineMusic(final SongListInfo songListInfoInfo, final ViewHolderMusicList holderMusicList) {
        App.getAppComponent().getOnlineMusicService().getSongList(songListInfoInfo.getType(), "3", "0")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<OnlineMusicList>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(OnlineMusicList onlineMusicList) {
                        songListInfoInfo.setCoverUrl(onlineMusicList.getBillboard().getPic_s640());
                        updateSongListInfo(onlineMusicList, songListInfoInfo);
                        setData(songListInfoInfo, holderMusicList);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.d("imm", t.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void updateSongListInfo(OnlineMusicList onlineMusicList, SongListInfo songListInfoInfo) {
        ArrayList<OnlineMusic> songList = onlineMusicList.getSong_list();
        if (songList.size() >= 1) {
            songListInfoInfo.setMusic1("1."+songList.get(0).getTitle() + "--" + songList.get(0).getArtist_name());
        } else {
            songListInfoInfo.setMusic1("");
        }
        if (songList.size() >= 2) {
            songListInfoInfo.setMusic2("2."+songList.get(1).getTitle() + "--" + songList.get(1).getArtist_name());
        } else {
            songListInfoInfo.setMusic2("");
        }
        if (songList.size() >= 3) {
            songListInfoInfo.setMusic3("3."+songList.get(2).getTitle() + "--" + songList.get(2).getArtist_name());
        } else {
            songListInfoInfo.setMusic3("");
        }
    }

    private void setData(final SongListInfo songListInfoInfo, ViewHolderMusicList holderMusicList) {
        holderMusicList.ivCover.setImageURI(songListInfoInfo.getCoverUrl());
        holderMusicList.tvMusic1.setText(songListInfoInfo.getMusic1());
        holderMusicList.tvMusic2.setText(songListInfoInfo.getMusic2());
        holderMusicList.tvMusic3.setText(songListInfoInfo.getMusic3());
    }

    private static class ViewHolderProfile extends RecyclerView.ViewHolder {
        TextView tvProfile;

        public ViewHolderProfile(View itemView) {
            super(itemView);
            tvProfile = (TextView) itemView.findViewById(R.id.tv_profile);
        }
    }

    private static class ViewHolderMusicList extends RecyclerView.ViewHolder {
        SimpleDraweeView ivCover;
        TextView tvMusic1;
        TextView tvMusic2;
        TextView tvMusic3;

        public ViewHolderMusicList(View itemView) {
            super(itemView);
            ivCover = (SimpleDraweeView) itemView.findViewById(R.id.iv_cover);
            tvMusic1 = (TextView) itemView.findViewById(R.id.tv_music_1);
            tvMusic2 = (TextView) itemView.findViewById(R.id.tv_music_2);
            tvMusic3 = (TextView) itemView.findViewById(R.id.tv_music_3);
        }
    }
}
