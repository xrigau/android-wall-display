package com.xrigau.walldisplay.auth;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.novoda.notils.caster.Views;
import com.xrigau.walldisplay.R;

public class AuthFragment extends Fragment {

    private static final String CI_URL = "http://ci.novoda.com/";

    private WebView webView;
    private Listener listener;

    private int count = 0;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (Listener) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_auth, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        if (!getActivity().getIntent().hasExtra(AuthActivity.EXTRA_FAILURE)) {
            String cookie = CookieManager.getInstance().getCookie(CI_URL);
            if (!TextUtils.isEmpty(cookie)) {
                notifyCookie(cookie);
                return;
            }
        }

        webView = Views.findById(view, R.id.auth_web_view);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                if (CI_URL.equals(url) && count > 0) {
                    notifyCookieOrError();
                }
                android.util.Log.e("Xavi", "HOOOOOOOHOOOO");
                count++;
            }
        });
        webView.loadUrl(CI_URL);
    }

    private void notifyCookieOrError() {
        String cookie = CookieManager.getInstance().getCookie(CI_URL);
        if (TextUtils.isEmpty(cookie)) {
            notifyError();
        } else {
            notifyCookie(cookie);
        }
    }

    private void notifyError() {
        if (listener != null) {
            listener.onError();
        }
    }

    private void notifyCookie(String cookie) {
        if (listener != null) {
            listener.onCookieRetrieved(cookie);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    interface Listener {
        void onCookieRetrieved(String cookie);

        void onError();
    }

}
