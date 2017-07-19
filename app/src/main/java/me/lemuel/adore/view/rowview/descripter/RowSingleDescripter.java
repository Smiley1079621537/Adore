package me.lemuel.adore.view.rowview.descripter;

import me.lemuel.adore.base.RowAction;

public class RowSingleDescripter extends BaseRowViewDescripter {

    private int iconResId;
    private String title;
    private RowAction action;

    public RowSingleDescripter(int iconResId, String title, RowAction action) {
        this.iconResId = iconResId;
        this.title = title;
        this.action = action;
    }

    public int getIconResId() {
        return iconResId;
    }

    public String getTitle() {
        return title;
    }

    public RowAction getAction() {
        return action;
    }
}
