package me.lemuel.adore;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.geniusforapp.fancydialog.FancyAlertDialog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.bible_viewpager)
    ViewPager mBibleViewpager;
    private int sectionid = 1;
    private int chapterid = 1;
    private ArrayList<Chapter> content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        final BibleAdapter bibleAdapter = new BibleAdapter(getSupportFragmentManager());
        mBibleViewpager.setAdapter(bibleAdapter);
        mBibleViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                System.out.println("on page selected" + position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private void showAlert() {
        FancyAlertDialog.Builder alert = new FancyAlertDialog.Builder(MainActivity.this)
                .setImageRecourse(R.drawable.ic_adb_black_24dp)
                .setTextTitle("今日经文")
                .setTextSubTitle("林后5:17")
                .setBody("若有人在基督里，他就是新造的人，\n旧事已过，都变成新的了。")
                .setNegativeColor(R.color.colorAccent)
                .setNegativeButtonText("取消")
                .setOnNegativeClicked(new FancyAlertDialog.OnNegativeClicked() {
                    @Override
                    public void OnClick(View view, Dialog dialog) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButtonText("确定")
                .setPositiveColor(R.color.colorPrimary)
                .setOnPositiveClicked(new FancyAlertDialog.OnPositiveClicked() {
                    @Override
                    public void OnClick(View view, Dialog dialog) {
                        Toast.makeText(MainActivity.this, "Updating", Toast.LENGTH_SHORT).show();
                    }
                })
                .setButtonsGravity(FancyAlertDialog.PanelGravity.CENTER)
                //.setAutoHide(true)
                .build();
        alert.show();
    }
}