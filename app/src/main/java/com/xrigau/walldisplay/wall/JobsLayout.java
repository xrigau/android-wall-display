package com.xrigau.walldisplay.wall;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

public class JobsLayout extends GridLayout {

    private WallDisplayAdapter adapter;

    public JobsLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public JobsLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void updateWith(WallDisplayAdapter adapter) {
        this.adapter = adapter;
        if (hasChildViews()) {
            updateExistingViews();
        } else {
            addNewViews();
        }
    }

    private boolean hasChildViews() {
        return getChildCount() > 0;
    }

    private void updateExistingViews() {
        for (int i = 0; i < adapter.getCount(); i++) {
            boolean mustBeAdded = canBeRecycled(i);
            View convertView = mustBeAdded ? null : getChildAt(i);
            View updatedView = adapter.getView(i, convertView, this);
            if (mustBeAdded) {
                add(updatedView);
            }
        }
    }

    private void add(View view) {
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(getWidth() / 2 - 20, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(10, 10, 10, 10);
        addView(view, new LayoutParams(lp));
    }

    private void addNewViews() {
        for (int i = 0; i < adapter.getCount(); i++) {
            View view = adapter.getView(i, null, this);
            add(view);
        }
    }

    private boolean canBeRecycled(int i) {
        return getChildCount() < i;
    }

}
