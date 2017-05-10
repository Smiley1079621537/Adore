package me.lemuel.adore.bean;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewProvider;
import me.lemuel.adore.R;


/**
 * Created by Immanuel on 2017/05/09.
 */
public class BillboardViewProvider
        extends ItemViewProvider<OnlineMusicList.Billboard, BillboardViewProvider.ViewHolder> {

    private Context mContext;

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.layout_list_header, parent, false);
        mContext = parent.getContext();
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull OnlineMusicList.Billboard billboard) {
        holder.mIvCover.setImageURI(Uri.parse(billboard.getPic_s444()));
        holder.mIvHeaderBg.setImageURI(Uri.parse(billboard.getPic_s640()));
        holder.mTvTitle.setText(billboard.getName());
        holder.mTvUpdateDate.setText(billboard.getUpdate_date());
        holder.mTvComment.setText(billboard.getComment());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_header_bg)
        SimpleDraweeView mIvHeaderBg;
        @BindView(R.id.iv_cover)
        SimpleDraweeView mIvCover;
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_update_date)
        TextView mTvUpdateDate;
        @BindView(R.id.tv_comment)
        TextView mTvComment;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}