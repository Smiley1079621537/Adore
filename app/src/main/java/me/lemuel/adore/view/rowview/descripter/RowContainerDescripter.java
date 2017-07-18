package me.lemuel.adore.view.rowview.descripter;

import java.util.ArrayList;

public class RowContainerDescripter {
    private ArrayList<RowGroupDescripter> groupDescripters;

    public RowContainerDescripter(ArrayList<RowGroupDescripter> groupDescripters) {
        this.groupDescripters = groupDescripters;
    }

    public ArrayList<RowGroupDescripter> getGroupDescripters() {
        return groupDescripters;
    }
}
