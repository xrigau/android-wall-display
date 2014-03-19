package com.xrigau.walldisplay.auth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.xrigau.walldisplay.R;

public class AuthActivity extends Activity implements AuthFragment.Listener {

    public static final String EXTRA_COOKIE = "Cookie";
    public static final String EXTRA_FAILURE = "Failure";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResult(RESULT_CANCELED);
        setContentView(R.layout.activity_auth);
    }

    @Override
    public void onCookieRetrieved(String cookie) {
        setResult(RESULT_OK, new Intent().putExtra(EXTRA_COOKIE, cookie));
        finish();
    }

    @Override
    public void onError() {
        finish();
    }

}
