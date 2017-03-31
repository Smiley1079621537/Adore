package me.lemuel.adore.demo.intent;

import android.content.Intent;
import android.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;
import me.lemuel.adore.R;
import me.lemuel.adore.demo.base.BaseActivity;
import me.lemuel.adore.transition.TransitionsHeleper;

/**
 * Created by Mr_immortalZ on 2016/11/1.
 * email : mr_immortalz@qq.com
 */

public class IntentActivity extends BaseActivity {
    @BindView(R.id.btn)
    Button btn;

    @Override
    public int getLayoutId() {
        return R.layout.activity_intent;
    }


    @OnClick(R.id.btn)
    public void onClick() {
        Intent intent = new Intent(this, IntentDetailActivity.class);
        intent.putExtra(IntentDetailActivity.TRANSITION_DATA, "This is immortalZ");
        TransitionsHeleper.startActivity(this, intent, btn);
    }
}
