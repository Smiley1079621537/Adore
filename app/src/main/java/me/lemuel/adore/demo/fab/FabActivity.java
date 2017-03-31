package me.lemuel.adore.demo.fab;

import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;
import me.lemuel.adore.R;
import me.lemuel.adore.demo.base.BaseActivity;
import me.lemuel.adore.transition.TransitionsHeleper;

/**
 * Created by Mr_immortalZ on 2016/10/29.
 * email : mr_immortalz@qq.com
 */

public class FabActivity extends BaseActivity {
    @BindView(R.id.btn_circle)
    FloatingActionButton btnCircle;
    @BindView(R.id.btn_no)
    FloatingActionButton btnNo;
    @BindView(R.id.btn)
    Button btn;

    @Override
    public int getLayoutId() {
        return R.layout.activity_fab;
    }


    @OnClick({R.id.btn_no, R.id.btn_circle,R.id.btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_no:
                TransitionsHeleper.startActivity(FabActivity.this, FabNoCircleActivity.class, btnNo);
                break;
            case R.id.btn_circle:
                TransitionsHeleper.startActivity(FabActivity.this, FabCircleActivity.class, btnCircle);
                break;
            case R.id.btn:
                TransitionsHeleper.startActivity(FabActivity.this, ButtonActivity.class, btn);
                break;
        }
    }

}
