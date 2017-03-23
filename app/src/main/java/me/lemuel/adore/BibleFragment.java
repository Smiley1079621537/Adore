package me.lemuel.adore;

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

/**
 * author : lemuel
 * time   : 2017/03/23
 */
public class BibleFragment extends Fragment {

    private MultiTypeAdapter sectionAdapter;
    private static int bookid = 1;
    private static int chapterid = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_bible, container, false);
        RecyclerView mBibleList = (RecyclerView) root.findViewById(R.id.bible_list);
        mBibleList.setLayoutManager(new LinearLayoutManager(getContext()));
        sectionAdapter = new MultiTypeAdapter();
        sectionAdapter.register(Section.class, new SectionViewProvider());
        mBibleList.setAdapter(sectionAdapter);
        return root;
    }

    public static BibleFragment newInstance(int position) {
        BibleFragment bibleFragment = new BibleFragment();
        for (int i = 1, sum = 0; i <= Bible.BOOK_NUM; i++) {
            sum = sum + Bible.getChapterNum(i);
            if (position + 1 <= sum) {
                bookid = i;
                chapterid = i == 1 ? position + 1 : sum - position;
                break;
            }
        }
        return bibleFragment;
    }

    public BibleFragment() {
        System.out.print("BibleFragment");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Book book = new Book(bookid, getContext());
        ArrayList<Section> sections = book.getContent().get(chapterid-1).getContent();
        Items items = new Items();
        items.addAll(sections);
        sectionAdapter.setItems(items);
        sectionAdapter.notifyDataSetChanged();
    }
}
