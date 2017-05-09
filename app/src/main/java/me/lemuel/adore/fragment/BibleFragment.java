package me.lemuel.adore.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;
import me.lemuel.adore.R;
import me.lemuel.adore.bean.bible.Bible;
import me.lemuel.adore.bean.bible.Book;
import me.lemuel.adore.bean.bible.Section;
import me.lemuel.adore.provider.SectionViewProvider;

/**
 * author : lemuel
 * time   : 2017/03/23
 */
public class BibleFragment extends Fragment {

    private MultiTypeAdapter sectionAdapter;
    private static int bookid = 1;
    private static int chapterid = 1;
    private int id;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_bible, container, false);
        RecyclerView chapterList = (RecyclerView) root.findViewById(R.id.bible_list);
        setChapterById();
        chapterList.setLayoutManager(new LinearLayoutManager(getContext()));
        sectionAdapter = new MultiTypeAdapter();
        sectionAdapter.register(Section.class, new SectionViewProvider());
        chapterList.setAdapter(sectionAdapter);
        return root;
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Book book = new Book(bookid, getContext());
        ArrayList<Section> sections = book.getContent().get(chapterid - 1).getContent();
        Items items = new Items();
        items.addAll(sections);
        sectionAdapter.setItems(items);
        sectionAdapter.notifyDataSetChanged();
    }
}
