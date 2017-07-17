package me.lemuel.adore.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;
import me.lemuel.adore.R;
import me.lemuel.adore.base.BaseFragment;
import me.lemuel.adore.bean.bible.Bible;
import me.lemuel.adore.bean.bible.Book;
import me.lemuel.adore.bean.bible.Section;
import me.lemuel.adore.provider.SectionViewProvider;

public class BibleFragment extends BaseFragment {

    private MultiTypeAdapter sectionAdapter;
    private static int bookid = 1;
    private static int chapterid = 1;
    private int id;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_bible;
    }

    @Override
    protected void initView(View root) {
        RecyclerView chapterList = (RecyclerView) root.findViewById(R.id.bible_list);
        chapterList.setLayoutManager(new LinearLayoutManager(getActivity()));
        sectionAdapter = new MultiTypeAdapter();
        sectionAdapter.register(Section.class, new SectionViewProvider());
        chapterList.setAdapter(sectionAdapter);
    }

    public static BibleFragment newInstance(int position) {
        BibleFragment bibleFragment = new BibleFragment();
        bibleFragment.id = position;
        return bibleFragment;
    }

    public void setChapterById() { //根据fragmentId找到bookId和chapterId
        bookid = Bible.getRelativeInfoById(id)[0];
        chapterid = Bible.getRelativeInfoById(id)[1];
    }

    public BibleFragment() {
        System.out.print("BibleFragment");
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {
        setChapterById();
        Book book = new Book(bookid, getContext());
        ArrayList<Section> sections = book.getContent().get(chapterid - 1).getContent();
        Items items = new Items();
        items.addAll(sections);
        sectionAdapter.setItems(items);
        sectionAdapter.notifyDataSetChanged();
    }
}
