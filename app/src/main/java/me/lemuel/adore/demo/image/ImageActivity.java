package me.lemuel.adore.demo.image;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.OnClick;
import me.lemuel.adore.R;
import me.lemuel.adore.demo.base.BaseActivity;
import me.lemuel.adore.transition.TransitionsHeleper;

/**
 * Created by Mr_immortalZ on 2016/10/27.
 * email : mr_immortalz@qq.com
 */

public class ImageActivity extends BaseActivity {


    @BindView(R.id.iv1)
    SimpleDraweeView iv1;

    String imgUrl = "http://imga.mumayi.com/android/wallpaper/2012/01/02/sl_600_2012010206150877826134.jpg";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iv1.setImageURI(imgUrl);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_image;
    }

    @OnClick(R.id.iv1)
    public void onClick() {
        TransitionsHeleper.startActivity(this, ImageDetailActivity.class, iv1, imgUrl);
    }
}
