package com.cricflame.cricflame.DummyBooks.Fragment;

/**
 * Created by Deepak Sharma on 25/11/2017.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cricflame.cricflame.DummyBooks.MakeMatchActivity;
import com.cricflame.cricflame.Model.LoadingDialog;
import com.cricflame.cricflame.R;

public class Session_Tab extends Fragment {
    WebView browser;
    LoadingDialog pd;
    private String Url;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.dummy_book_layout,container,false);

        Url = MakeMatchActivity.dummy_session_url;
        browser = (WebView) view.findViewById(R.id.webview);

        //  records= (Button) findViewById(R.id.records);

//        records.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(DummyBook.this,WebActivityData.class));
//            }
//        });
        CookieManager.getInstance().setAcceptCookie(true);
        browser.getSettings().setDomStorageEnabled(true);
        pd=new LoadingDialog(getActivity());
        browser.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        browser.getSettings().setJavaScriptEnabled(true); // enable javascript
        browser.getSettings().setLoadWithOverviewMode(true);
        //  browser.clearCache(true);
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
        return view;

    }



    private class MyWebChromeClient extends WebChromeClient {
        Context context;

        public MyWebChromeClient(Context context) {
            super();
            this.context = context;
        }

    }

}