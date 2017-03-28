package me.lemuel.adore.items.bible;

/**
 * author : lemuel
 * time   : 2017/03/23
 */
public class Section {
    private int bookId;
    private int chapterId;
    private int id;
    private String content;

    public Section(int id, String content, int chapterId, int bookId) {
        this.id = id;
        this.content = content;
        this.chapterId = chapterId;
        this.bookId = bookId;
    }


    public String getContent() {
        return this.content;
    }

    public int getId() {
        return id;
    }

    public int getBookId() {
        return bookId;
    }

    public int getChapterId() {
        return chapterId;
    }

    public String toMessageString() {
        return getContent() + "(" + Bible.getBookName(getBookId())
                + getChapterId() + ":" + id + ")";
    }

    public String toString() {
        return content;
    }

    public String getLocationString() {
        return Bible.getBookSimpleName(getBookId()) + " " + getChapterId() + ":" + id;
    }
}
