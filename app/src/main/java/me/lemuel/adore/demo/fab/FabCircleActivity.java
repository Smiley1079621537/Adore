package me.lemuel.adore.demo.fab;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.animation.AccelerateInterpolator;

import com.facebook.drawee.view.SimpleDraweeView;

import me.lemuel.adore.R;
import me.lemuel.adore.demo.base.BaseActivity;
import me.lemuel.adore.transition.TransitionsHeleper;
import me.lemuel.adore.transition.bean.InfoBean;
import me.lemuel.adore.transition.method.ColorShowMethod;

/**
 * Created by Mr_immortalZ on 2016/10/29.
 * email : mr_immortalz@qq.com
 */

public class FabCircleActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TransitionsHeleper.getInstance()
                .setShowMethod(new ColorShowMethod(R.color.bg_purple, R.color.bg_teal) {
                    @Override
                    public void loadCopyView(InfoBean bean, SimpleDraweeView copyView) {
                        AnimatorSet set = new AnimatorSet();
                        set.playTogether(
                                ObjectAnimator.ofFloat(copyView,"rotation",0,180),
                                ObjectAnimator.ofFloat(copyView, "scaleX", 1, 0),
                                ObjectAnimator.ofFloat(copyView, "scaleY", 1, 0)
                        );
                        set.setInterpolator(new AccelerateInterpolator());
                        set.setDuration(duration / 4 * 5).start();
                    }

                    @Override
                    public void loadTargetView(InfoBean bean, SimpleDraweeView targetView) {

                    }


                })
                .show(this,null);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_fab_detail;
    }

}
