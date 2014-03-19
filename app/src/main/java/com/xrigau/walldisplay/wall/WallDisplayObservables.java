package com.xrigau.walldisplay.wall;

import java.util.concurrent.TimeUnit;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

import static retrofit.RestAdapter.LogLevel.FULL;

class WallDisplayObservables {

    private static final String API_URL = "http://ci.novoda.com";

    static Observable<JobList> repeatingGetJobsObservable(final String cookie, int periodSeconds) {
        return Observable.timer(periodSeconds, periodSeconds, TimeUnit.SECONDS).flatMap(new Func1<Long, Observable<JobList>>() {
            @Override
            public Observable<JobList> call(Long aLong) {
                return enabledJobsObservable(cookie);
            }
        });
    }

    static Observable<JobList> enabledJobsObservable(String cookie) {
        return wallDisplayObservable(cookie).map(new Func1<JobList, JobList>() {
            @Override
            public JobList call(JobList jobs) {
                JobList enabled = new JobList();
                for (Job job : jobs) {
                    if (job.isEnabled()) {
                        enabled.add(job);
                    }
                }
                return enabled;
            }
        });
    }

    static Observable<JobList> wallDisplayObservable(final String cookie) {
        return Observable.create(new Observable.OnSubscribe<WallDisplayResponse>() {
            @Override
            public void call(Subscriber<? super WallDisplayResponse> subscriber) {
                RestAdapter restAdapter = new RestAdapter.Builder()
                        .setServer(API_URL)
                        .setLogLevel(FULL)
                        .setRequestInterceptor(new RequestInterceptor() {
                            @Override
                            public void intercept(RequestFacade request) {
                                request.addHeader("Cookie", cookie);
                            }
                        })
                        .build();
                try {
                    subscriber.onNext(restAdapter.create(WallDisplayServices.class).wallDisplay());
                    subscriber.onCompleted();
                } catch (Throwable e) {
                    subscriber.onError(e);
                }

            }
        }).map(new Func1<WallDisplayResponse, JobList>() {
                   @Override
                   public JobList call(WallDisplayResponse wallDisplayResponse) {
                       return wallDisplayResponse.getJobs();
                   }
               }
        );
    }

}
