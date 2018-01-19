package com.luxoft.carsapp.presentation.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.luxoft.carsapp.R;

import butterknife.Bind;

public class BrowserActivity extends BaseActivity {
    public static final String URL_KEY = "url_key";
    private String wikiUrl = "";
//    @Bind(R.id.webView)
    WebView webView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);

        wikiUrl = getIntent().getStringExtra(URL_KEY);
        webView = findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl(wikiUrl);
    }
}
