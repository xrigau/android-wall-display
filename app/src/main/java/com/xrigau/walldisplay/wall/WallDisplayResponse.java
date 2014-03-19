package com.xrigau.walldisplay.wall;

import com.google.gson.annotations.SerializedName;

class WallDisplayResponse {

    @SerializedName("jobs")
    private final JobList jobs;

    WallDisplayResponse(JobList jobs) {
        this.jobs = jobs;
    }

    JobList getJobs() {
        return jobs;
    }

}
