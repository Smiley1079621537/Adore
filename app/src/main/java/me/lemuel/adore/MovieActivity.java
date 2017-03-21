package me.lemuel.adore;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.umeng.socialize.UMShareAPI;

import me.lemuel.adore.view.DialogView;

public class MovieActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_movie);

        String imgUrl = getIntent().getStringExtra(getString(R.string.img_url));
        final DialogView dialogView = new DialogView();
        // dialogView.setImageUrl(imgUrl);
        //dialogView.show(getSupportFragmentManager(), "fragment_bottom_dialog");

        ImageView movieImage = (ImageView) findViewById(R.id.movie_img);
        Glide.with(this).load(imgUrl).asBitmap().into(movieImage);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                // Great! User has recorded and saved the audio file
            } else if (resultCode == RESULT_CANCELED) {
                // Oops! User has canceled the recording
            }
        }
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
