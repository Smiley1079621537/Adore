package me.lemuel.adore.demo.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.lemuel.adore.R;
import me.lemuel.adore.demo.base.BaseActivity;
import me.lemuel.adore.transition.TransitionsHeleper;

/**
 * Created by Mr_immortalZ on 2016/10/29.
 * email : mr_immortalz@qq.com
 */

public class FActivity extends BaseActivity {

    @BindView(R.id.btn_circle)
    FloatingActionButton btnCommit;

    @Override
    public int getLayoutId() {
        return R.layout.activity_fragment;
    }

    @OnClick(R.id.btn_circle)
    public void onClick() {
        TransitionsHeleper.startActivity(this, FDetailActivity.class, btnCommit);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
}
