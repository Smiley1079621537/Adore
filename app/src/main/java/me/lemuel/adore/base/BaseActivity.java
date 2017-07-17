package me.lemuel.adore.base;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.imagepipeline.request.Postprocessor;

import butterknife.ButterKnife;
import jp.wasabeef.fresco.processors.BlurPostprocessor;
import me.lemuel.adore.bean.music.OnlineMusicList;
import me.lemuel.adore.util.CommentUtil;
import me.lemuel.adore.view.DialogView;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentLayout());
        CommentUtil.setTransparentStatusbar(this);
        ButterKnife.bind(this);
        initView();
        initData();
        initEvent();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    protected abstract int getContentLayout();
    protected abstract void initEvent();
    protected abstract void initView();
    protected abstract void initData();

    public static void setTranslucent(Activity activity) {
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
        rootView.setFitsSystemWindows(true);
        rootView.setClipToPadding(true);
    }

     //图片模糊处理
    public void blurHeaderView(SimpleDraweeView draweeView,OnlineMusicList.Billboard billboard) {
        Postprocessor postprocessor = new BlurPostprocessor(draweeView.getContext(), 50);
        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(billboard.getPic_s640()))
                .setPostprocessor(postprocessor)
                .build();
        PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                .setImageRequest(imageRequest)
                .setOldController(draweeView.getController())
                .build();
        draweeView.setController(controller);
    }

    private void showBottomDialog(String imgUrl) {
        DialogView dialogView = new DialogView();
        dialogView.setImageUrl(imgUrl);
        dialogView.show(getSupportFragmentManager(), "fragment_bottom_dialog");
    }
}
