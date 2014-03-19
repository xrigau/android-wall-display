package com.xrigau.walldisplay.wall;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.xrigau.walldisplay.R;

import java.util.List;

class WallDisplayAdapter extends ArrayAdapter<Job> {

    WallDisplayAdapter(Context context, List<Job> jobs) {
        super(context, 0, jobs);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_job, parent, false);
        }
        ((JobView) convertView).updateWith(getItem(position));
        return convertView;
    }

}
