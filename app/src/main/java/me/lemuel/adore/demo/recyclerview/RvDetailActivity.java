package me.lemuel.adore.demo.recyclerview;

import android.net.Uri;
import android.os.Bundle;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import me.lemuel.adore.R;
import me.lemuel.adore.demo.base.BaseActivity;
import me.lemuel.adore.transition.TransitionsHeleper;
import me.lemuel.adore.transition.bean.InfoBean;
import me.lemuel.adore.transition.method.InflateShowMethod;

/**
 * Created by Mr_immortalZ on 2016/10/29.
 * email : mr_immortalz@qq.com
 */

public class RvDetailActivity extends BaseActivity {


    @BindView(R.id.iv_detail)
    SimpleDraweeView ivDetail;

    @Override
    public int getLayoutId() {
        return R.layout.activity_recyclerview_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TransitionsHeleper.getInstance()
                .setShowMethod(new InflateShowMethod(this, R.layout.activity_rv_inflate) {
                    @Override
                    public void loadCopyView(InfoBean bean, final SimpleDraweeView copyView) {
                        copyView.setImageURI(Uri.parse(bean.getImgUrl()));

                    }

                    @Override
                    public void loadTargetView(InfoBean bean, SimpleDraweeView targetView) {
                        targetView.setImageURI(Uri.parse(bean.getImgUrl()));
                    }
                })
                .show(this, ivDetail);
    }
}
