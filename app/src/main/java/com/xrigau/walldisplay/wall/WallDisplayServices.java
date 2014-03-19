package com.xrigau.walldisplay.wall;

import retrofit.http.GET;

interface WallDisplayServices {

    @GET("/user/xrigau/my-views/view/Active/api/json")
    WallDisplayResponse wallDisplay();

}
