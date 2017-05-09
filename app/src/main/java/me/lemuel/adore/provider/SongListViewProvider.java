package me.lemuel.adore.provider;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import me.drakeet.multitype.ItemViewProvider;
import me.lemuel.adore.R;
import me.lemuel.adore.bean.SongList;

/**
 * Created by lemuel on 2017/05/08.
 */
public class SongListViewProvider
        extends ItemViewProvider<SongList, SongListViewProvider.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_song_list, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull SongList songList) {
       // holder.mIvCover.setImageURI(songList.getCoverUrl());
        holder.mTvMusic1.setText(songList.getTitle());
        holder.mTvMusic2.setText(songList.getType());
        holder.mTvMusic3.setText(songList.getMusic3());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView mIvCover;
        TextView mTvMusic1;
        TextView mTvMusic2;
        TextView mTvMusic3;
        ViewHolder(View itemView) {
            super(itemView);
            mIvCover = (SimpleDraweeView) itemView.findViewById(R.id.iv_cover);
            mTvMusic1 = (TextView) itemView.findViewById(R.id.tv_music_1);
            mTvMusic2 = (TextView) itemView.findViewById(R.id.tv_music_2);
            mTvMusic3 = (TextView) itemView.findViewById(R.id.tv_music_3);

        }
    }
}