package me.lemuel.adore.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

import java.util.Date;

@Entity
public class Note {
    @Id
    private Long id;
    @NotNull
    private String text;
    private String comment;
    private Date date;
    @Generated(hash = 1961626152)
    public Note(Long id, @NotNull String text, String comment, Date date) {
        this.id = id;
        this.text = text;
        this.comment = comment;
        this.date = date;
    }
    @Generated(hash = 1272611929)
    public Note() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getText() {
        return this.text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public String getComment() {
        return this.comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public Date getDate() {
        return this.date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

}
