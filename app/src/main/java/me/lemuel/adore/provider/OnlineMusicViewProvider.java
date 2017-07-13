package me.lemuel.adore.provider;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewProvider;
import me.lemuel.adore.R;
import me.lemuel.adore.bean.music.OnlineMusic;

/**
 * Created by Immanuel on 2017/05/09.
 */
public class OnlineMusicViewProvider
        extends ItemViewProvider<OnlineMusic, OnlineMusicViewProvider.ViewHolder> {

    private OnMusicClick mOnMusicClick;

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_online_music, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, @NonNull final OnlineMusic onlineMusic) {
        holder.mIvCover.setImageURI(Uri.parse(onlineMusic.getPic_big()));
        holder.mTvTitle.setText(onlineMusic.getTitle());
        holder.mTvArtist.setText(onlineMusic.getArtist_name()+" - "+onlineMusic.getAlbum_title());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnMusicClick!=null){
                    mOnMusicClick.onItemMusicClick(onlineMusic);
                }
            }
        });
    }

    public interface OnMusicClick{
        void onItemMusicClick(OnlineMusic onlineMusic);
    }

    public void setOnMusicClick(OnMusicClick onMusicClick){
        mOnMusicClick = onMusicClick;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.v_playing)
        View mVPlaying;
        @BindView(R.id.iv_cover)
        SimpleDraweeView mIvCover;
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_artist)
        TextView mTvArtist;
        @BindView(R.id.iv_more)
        ImageView mIvMore;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}