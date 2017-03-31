package me.lemuel.adore.demo.recyclerview;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import me.lemuel.adore.R;
import me.lemuel.adore.transition.TransitionsHeleper;

/**
 * Created by Mr_immortalZ on 2016/10/29.
 * email : mr_immortalz@qq.com
 */

public class RvAdapter extends CommonAdapter<String> {
    public RvAdapter(Context context, int layoutId, List<String> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(final ViewHolder holder, final String str, int position) {
        SimpleDraweeView imageView = (SimpleDraweeView) holder.itemView.findViewById(R.id.iv1);
        imageView.setImageURI(str);
        holder.setOnClickListener(R.id.iv1, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionsHeleper.startActivity((Activity) mContext, RvDetailActivity.class,
                        holder.itemView.findViewById(R.id.iv1),
                        str);
            }
        });
    }
}
