package me.lemuel.adore.view.rowview;

import java.util.ArrayList;

import me.lemuel.adore.R;
import me.lemuel.adore.base.RowAction;
import me.lemuel.adore.view.rowview.descripter.RowContainerDescripter;
import me.lemuel.adore.view.rowview.descripter.RowDescripter;
import me.lemuel.adore.view.rowview.descripter.RowGroupDescripter;

public class RowContainerViewSetting {

    public static RowContainerDescripter getRowContainerDescripter() {
        //第一组
        ArrayList<RowDescripter> rowDescripters = new ArrayList<>();
        RowDescripter descripter1 = new RowDescripter(R.drawable.splash1,"Immanuel1", RowAction.IMMANUEL1);
        RowDescripter descripter2 = new RowDescripter(R.drawable.splash2,"Immanuel2", RowAction.IMMANUEL2);
        RowDescripter descripter3 = new RowDescripter(R.drawable.splash3,"Immanuel3", RowAction.IMMANUEL3);
        rowDescripters.add(descripter1);
        rowDescripters.add(descripter2);
        rowDescripters.add(descripter3);
        RowGroupDescripter groupDescripter1 = new RowGroupDescripter(rowDescripters);
        //第二组
        ArrayList<RowDescripter> rowDescripters2 = new ArrayList<>();
        RowDescripter descripter4 = new RowDescripter(R.drawable.splash1,"Immanuel4", RowAction.IMMANUEL4);
        RowDescripter descripter5 = new RowDescripter(R.drawable.splash2,"Immanuel5", RowAction.IMMANUEL5);
        RowDescripter descripter6 = new RowDescripter(R.drawable.splash3,"Immanuel6", RowAction.IMMANUEL6);
        RowDescripter descripter7 = new RowDescripter(R.drawable.splash1,"Immanuel7", RowAction.IMMANUEL7);
        rowDescripters2.add(descripter4);
        rowDescripters2.add(descripter5);
        rowDescripters2.add(descripter6);
        rowDescripters2.add(descripter7);
        RowGroupDescripter groupDescripter2 = new RowGroupDescripter(rowDescripters2);
        //合并
        ArrayList<RowGroupDescripter> groupDescripters = new ArrayList<>();
        groupDescripters.add(groupDescripter1);
        groupDescripters.add(groupDescripter2);

        return new RowContainerDescripter(groupDescripters);
    }
}
