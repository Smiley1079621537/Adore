package me.lemuel.adore.demo.image;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import me.lemuel.adore.R;
import me.lemuel.adore.demo.base.BaseActivity;
import me.lemuel.adore.transition.TransitionsHeleper;
import me.lemuel.adore.transition.bean.InfoBean;
import me.lemuel.adore.transition.method.ColorShowMethod;

/**
 * Created by Mr_immortalZ on 2016/10/28.
 * email : mr_immortalz@qq.com
 */

public class ImageDetailActivity extends BaseActivity {
    @BindView(R.id.iv_detail)
    SimpleDraweeView ivDetail;
    @BindView(R.id.tv)
    TextView tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TransitionsHeleper.getInstance()
                .setShowMethod(new ColorShowMethod(R.color.bg_teal_light,
                        R.color.bg_purple) {
                    @Override
                    public void loadCopyView(InfoBean bean, SimpleDraweeView copyView) {
                        copyView.setImageURI(bean.getImgUrl());
                    }

                    @Override
                    public void loadTargetView(InfoBean bean, SimpleDraweeView targetView) {
                        targetView.setImageURI(Uri.parse(bean.getImgUrl()));
                    }

                }).show(this, ivDetail);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_imagedetail;
    }
}
