package me.lemuel.adore;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;
import me.lemuel.adore.demo.base.BaseActivity;
import me.lemuel.adore.demo.fab.FabActivity;
import me.lemuel.adore.demo.fragment.FActivity;
import me.lemuel.adore.demo.image.ImageActivity;
import me.lemuel.adore.demo.intent.IntentActivity;
import me.lemuel.adore.demo.recyclerview.RvActivity;

public class PictureActivity extends BaseActivity {
    @BindView(R.id.btn_image)
    Button btnImage;
    @BindView(R.id.btn_recycleview)
    Button btnRecycleview;
    @BindView(R.id.btn_fab)
    Button btnFab;
    @BindView(R.id.btn_fragment)
    Button btnFragment;
    @BindView(R.id.btn_intent)
    Button btnIntent;

    @Override
    public int getLayoutId() {
        return R.layout.activity_picture;
    }


    @OnClick({R.id.btn_image, R.id.btn_recycleview, R.id.btn_fab, R.id.btn_fragment, R.id.btn_intent})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_image:
                gotoNextActivity(ImageActivity.class);
                break;
            case R.id.btn_recycleview:
                gotoNextActivity(RvActivity.class);
                break;
            case R.id.btn_fab:
                gotoNextActivity(FabActivity.class);
                break;
            case R.id.btn_fragment:
                gotoNextActivity(FActivity.class);
                break;
            case R.id.btn_intent:
                gotoNextActivity(IntentActivity.class);
                break;
        }
    }

    private void gotoNextActivity(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);

    }

}
