package me.lemuel.adore.view.rowview.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import java.util.ArrayList;

import me.lemuel.adore.listener.OnRowViewClickListener;
import me.lemuel.adore.view.rowview.descripter.RowDescripter;

public class RowGroupView extends LinearLayout {

    private Context context;

    public RowGroupView(Context context) {
        super(context);
        initializeView(context);
    }

    public RowGroupView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initializeView(context);
    }

    public RowGroupView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeView(context);
    }

    private void initializeView(Context context) {
        this.context = context;
        setOrientation(VERTICAL);
    }

    public void initializeData(ArrayList<RowDescripter> descripters, OnRowViewClickListener listener) {
        if (descripters!=null && descripters.size() > 0){
            RowView rowView;
            for (RowDescripter descripter : descripters) {
                rowView = new RowView(context);
                rowView.initializeData(descripter,listener);
                addView(rowView);
            }
        }
    }
}
