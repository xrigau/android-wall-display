package com.xrigau.walldisplay.wall;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.novoda.notils.caster.Views;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;
import com.xrigau.walldisplay.R;

public class JobView extends FrameLayout {

    private final Shimmer shimmer = new Shimmer();

    private Job job;

    private ShimmerTextView name;

    public JobView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public JobView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.view_job, this);
    }

    @Override
    protected void onFinishInflate() {
        name = Views.findById(this, R.id.name);
        name.setShadowLayer(30, 0, 0, Color.WHITE);
    }

    void updateWith(Job job) {
        this.job = job;
        name.setText(job.getName());
        JobColor color = JobColor.from(job);
        color.into(this);

        if (color.inProgress()) {
            shimmer.start(name);
        } else {
            shimmer.cancel();
        }
    }

}
