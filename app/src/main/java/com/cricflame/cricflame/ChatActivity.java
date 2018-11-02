package com.cricflame.cricflame;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.cricflame.cricflame.Model.LoadingDialog;

public class ChatActivity extends AppCompatActivity {
    private WebView browser;
    private LoadingDialog pd;
    ImageView back;
    private String Url = "http://royalcube.in/mychat/index.html";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.cricflame.cricflame.R.layout.activity_chat);
        browser = (WebView) findViewById(com.cricflame.cricflame.R.id.webview);
        back= (ImageView) findViewById(com.cricflame.cricflame.R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        pd=new LoadingDialog(ChatActivity.this);
        CookieManager.getInstance().setAcceptCookie(true);
        browser.getSettings().setDomStorageEnabled(true);

        browser.getSettings().setJavaScriptEnabled(true); // enable javascript
        browser.getSettings().setLoadWithOverviewMode(true);
        browser.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        browser.getSettings().setUseWideViewPort(true);
      //  browser.getSettings().setBuiltInZoomControls(true);


        browser.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                //Toast.makeText(activity, description, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon)
            {
                pd.show();

            }


            @Override
            public void onPageFinished(WebView view, String url) {
                pd.dismiss();

                String webUrl = browser.getUrl();

            }



        });

        browser.loadUrl(Url);

    }



    private class MyWebChromeClient extends WebChromeClient {
        Context context;

        public MyWebChromeClient(Context context) {
            super();
            this.context = context;
        }
    }
}
