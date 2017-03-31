package me.lemuel.adore.demo.intent;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import butterknife.BindView;
import me.lemuel.adore.R;
import me.lemuel.adore.demo.base.BaseActivity;
import me.lemuel.adore.transition.TransitionsHeleper;

/**
 * Created by Mr_immortalZ on 2016/11/1.
 * email : mr_immortalz@qq.com
 */

public class IntentDetailActivity extends BaseActivity {

    protected static final String TRANSITION_DATA = "data";
    @BindView(R.id.tv)
    TextView tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent != null) {
            tv.setText(intent.getStringExtra(TRANSITION_DATA));
        }
        TransitionsHeleper.getInstance()
                .show(this, null);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_intent_detail;
    }
}
