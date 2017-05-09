package me.lemuel.adore.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.geniusforapp.fancydialog.FancyAlertDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.lemuel.adore.R;
import me.lemuel.adore.adapter.BibleAdapter;
import me.lemuel.adore.bean.bible.Bible;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.bible_viewpager)
    ViewPager mBibleViewpager;
    public Menu mainMenu;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        BibleAdapter bibleAdapter = new BibleAdapter(getSupportFragmentManager());
        mBibleViewpager.setAdapter(bibleAdapter);
        mBibleViewpager.addOnPageChangeListener(this);
    }

    private void updateMenu(int position) {
        int[] ids = Bible.getRelativeInfoById(position);
        mainMenu.findItem(R.id.action_book).setTitle(Bible.getBookName(ids[0]));
        mainMenu.findItem(R.id.action_chapter).setTitle(String.valueOf(ids[1]));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        this.mainMenu = menu;
        updateMenu(mBibleViewpager.getCurrentItem());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_book:

                break;
            case R.id.action_chapter:

                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
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

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        updateMenu(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

    }
}