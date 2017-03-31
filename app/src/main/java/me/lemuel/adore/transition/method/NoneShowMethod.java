package me.lemuel.adore.transition.method;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.facebook.drawee.view.SimpleDraweeView;

import me.lemuel.adore.R;
import me.lemuel.adore.transition.bean.InfoBean;
import me.lemuel.adore.transition.view.RenderView;


/**
 * Created by Mr_immortalZ on 2016/10/24.
 * email : mr_immortalz@qq.com
 */

public  class NoneShowMethod extends ShowMethod {

    protected int startColor;
    protected int endColor;

    @Override
    public void translate(InfoBean bean, RenderView parent, View child) {

        startColor = parent.getResources().getColor(R.color.showmethod_start_color);
        endColor = parent.getResources().getColor(R.color.showmethod_end_color);

        set.setInterpolator(new AccelerateInterpolator());
        set.setDuration(duration).start();
    }

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


}
