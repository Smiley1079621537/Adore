package me.lemuel.adore.activity;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.BindView;
import me.lemuel.adore.R;
import me.lemuel.adore.adapter.BibleAdapter;
import me.lemuel.adore.base.BaseActivity;
import me.lemuel.adore.bean.bible.Bible;

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.bible_viewpager)
    ViewPager mBibleViewpager;
    public Menu mainMenu;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initEvent() {
        mBibleViewpager.addOnPageChangeListener(this);
    }

    @Override
    protected void initView() {
        setSupportActionBar(mToolbar);
    }

    @Override
    protected void initData() {
        BibleAdapter bibleAdapter = new BibleAdapter(getSupportFragmentManager());
        mBibleViewpager.setAdapter(bibleAdapter);
    }

    private void updateMenu(int position) {
        int[] ids = Bible.getRelativeInfoById(position);
        mainMenu.findItem(R.id.action_book).setTitle(Bible.getBookName(ids[0]));
        mainMenu.findItem(R.id.action_chapter).setTitle(String.valueOf(ids[1]));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        mainMenu = menu;
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
        }
        return super.onOptionsItemSelected(item);
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