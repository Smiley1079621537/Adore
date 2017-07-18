package me.lemuel.adore.view.rowview.descripter;

import java.util.ArrayList;

public class RowGroupDescripter {
    private ArrayList<RowDescripter> rowDescripters;

    public RowGroupDescripter(ArrayList<RowDescripter> rowDescripters) {
        this.rowDescripters = rowDescripters;
    }

    public ArrayList<RowDescripter> getRowDescripters() {
        return rowDescripters;
    }
}
