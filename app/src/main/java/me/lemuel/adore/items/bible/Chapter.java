package me.lemuel.adore.items.bible;

import java.util.ArrayList;

/**
 * author : lemuel
 * time   : 2017/03/23
 */
public class Chapter {
    private final int bookId;
    private final int id;
    //    public static int autoId = 0;
    private ArrayList<Section> content;

    public Chapter(int id, int bookId) {
        this.bookId = bookId;
        this.id = id;
        this.content = new ArrayList();
    }

    public void addSection(Section section) {
        this.content.add(section);
    }

    public int getId() {
        return id;
    }

    public int getBookId() {
        return bookId;
    }

    public ArrayList<Section> getContent() {
        return this.content;
    }

    public Section getSection(int id) {
        return this.content.get(id);
    }

    public int getSectionNum() {
        return this.content.size();
    }

}

