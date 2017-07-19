package me.lemuel.adore.view.rowview.descripter;

import me.lemuel.adore.base.RowAction;

public class RowProfileViewDescripter {
    private int profileResId;
    private String profileTitle;
    private String profileSubTitle;
    private RowAction action;

    public RowProfileViewDescripter(
            int profileResId, String profileTitle, String profileSubTitle, RowAction action) {
        this.profileResId = profileResId;
        this.profileTitle = profileTitle;
        this.profileSubTitle = profileSubTitle;
        this.action = action;
    }

    public int getProfileResId() {
        return profileResId;
    }

    public String getProfileTitle() {
        return profileTitle;
    }

    public String getProfileSubTitle() {
        return profileSubTitle;
    }

    public RowAction getAction() {
        return action;
    }
}
