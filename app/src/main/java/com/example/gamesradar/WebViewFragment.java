package com.example.gamesradar;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.progressindicator.LinearProgressIndicator;

public class WebViewFragment extends Fragment {

    private static final String ARG_URL = "webview_url";

    MaterialToolbar toolbar;
    WebView webView;
  LinearProgressIndicator loading;
    String url;

    public WebViewFragment() {
        // Required empty public constructor
    }

    public static WebViewFragment newInstance(String url) {
        WebViewFragment fragment = new WebViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_URL, url);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            url = getArguments().getString(ARG_URL);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

      View v= inflater.inflate(R.layout.fragment_web_view, container, false);
    toolbar = v.findViewById(R.id.topAppBar);
    webView = v.findViewById(R.id.web_view);
    loading = v.findViewById(R.id.loading_web_view);
    loading.setVisibility(View.VISIBLE);

    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getParentFragmentManager().popBackStack();
        }
    });
    Log.d("url",url);

    webView.setWebViewClient(new WebViewClient());
    webView.setWebChromeClient(new WebChromeClient(){
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            loading.setProgress(newProgress);
            if(newProgress==100){
                loading.setVisibility(View.GONE);
            }
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            toolbar.setTitle(title);
        }

        @Override
        public void onReceivedIcon(WebView view, Bitmap icon) {
            super.onReceivedIcon(view, icon);
        }
    });
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
    webView.loadUrl(url);



    return v;
    }
}