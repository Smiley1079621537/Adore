package me.lemuel.adore.view.rowview.descripter;

import java.util.ArrayList;

public class RowGroupDescripter {
    private ArrayList<BaseRowViewDescripter> rowDescripters;

    public RowGroupDescripter(ArrayList<BaseRowViewDescripter> rowDescripters) {
        this.rowDescripters = rowDescripters;
    }

    public ArrayList<BaseRowViewDescripter> getRowDescripters() {
        return rowDescripters;
    }
}
