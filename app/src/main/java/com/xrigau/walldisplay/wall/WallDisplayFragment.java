package com.xrigau.walldisplay.wall;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.novoda.notils.caster.Views;
import com.novoda.notils.logger.simple.Log;
import com.xrigau.walldisplay.R;
import com.xrigau.walldisplay.auth.AuthActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.xrigau.walldisplay.wall.WallDisplayObservables.repeatingGetJobsObservable;

public class WallDisplayFragment extends Fragment {

    private static final int REQUEST_CODE = 123;
    private static final int REFRESH_DELAY_SECONDS = 30;

    private View loading;
    private JobsLayout jobsLayout;
    private TextView timestamp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wall_display, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        loading = Views.findById(view, R.id.loading);
        jobsLayout = Views.findById(view, R.id.jobs);
        timestamp = Views.findById(view, R.id.timestamp);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        retrieveSessionCookie();
    }

    private void retrieveSessionCookie() {
        startActivityForResult(new Intent(getActivity(), AuthActivity.class), REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        getJobs(data.getStringExtra(AuthActivity.EXTRA_COOKIE));
    }

    private void getJobs(String cookie) {
        showLoading();
        repeatingGetJobsObservable(cookie, REFRESH_DELAY_SECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JobList>() {
                    @Override
                    public void onNext(JobList jobs) {
                        hideLoading();
                        WallDisplayAdapter adapter = new WallDisplayAdapter(getActivity(), jobs);
                        jobsLayout.updateWith(adapter);
                        timestamp.setText("Updated: " + new SimpleDateFormat().format(new Date()));
                        timestamp.setTextColor(Color.BLACK);
                    }

                    @Override
                    public void onError(Throwable e) {
                        log("Error downloading news list", e);
                        hideLoading();
                        toast(R.string.generic_error_oops);
                        retrieveSessionCookieAfterFailure();
                        timestamp.setText("ERROR!" + new SimpleDateFormat().format(new Date()));
                        timestamp.setTextColor(Color.RED);
                    }

                    @Override
                    public void onCompleted() {

                    }
                });
    }

    private void retrieveSessionCookieAfterFailure() {
        startActivityForResult(new Intent(getActivity(), AuthActivity.class).putExtra(AuthActivity.EXTRA_FAILURE, true), REQUEST_CODE);
    }

    private void showLoading() {
        jobsLayout.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        jobsLayout.setVisibility(View.VISIBLE);
        loading.setVisibility(View.GONE);
    }

    private void toast(int resourceId) {
        Toast.makeText(getActivity(), resourceId, Toast.LENGTH_SHORT).show();
    }

    private void log(String message, Throwable error) {
        Log.e(message, error);
    }

}
