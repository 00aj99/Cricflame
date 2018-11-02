package com.cricflame.cricflame;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.cricflame.cricflame.Model.LoadingDialog;
import com.cricflame.cricflame.TipsTricks.GameListActivity;

public class GameActivity extends AppCompatActivity {
    private WebView browser;
    private LoadingDialog pd;
    ImageView back;
    private String Url = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.cricflame.cricflame.R.layout.activity_game);
        browser = (WebView) findViewById(com.cricflame.cricflame.R.id.webview);
        back= (ImageView) findViewById(com.cricflame.cricflame.R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        pd=new LoadingDialog(GameActivity.this);
        browser.getSettings().setJavaScriptEnabled(true); // enable javascript
        browser.getSettings().setLoadWithOverviewMode(true);
        browser.clearCache(true);
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

        browser.loadUrl(GameListActivity.GAME_ID);

    }



    private class MyWebChromeClient extends WebChromeClient {
        Context context;

        public MyWebChromeClient(Context context) {
            super();
            this.context = context;
        }
    }
}
