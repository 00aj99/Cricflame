package com.cricflame.cricflame;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

public class AboutUsWebViewActivity extends AppCompatActivity {

    private WebView wvAboutUs;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us_web_view);

        int item = getIntent().getExtras().getInt("item");

        back= (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        wvAboutUs = (WebView) findViewById(R.id.wv_about_us);
        wvAboutUs.setWebViewClient(new WebViewClient());

        switch (item){
            case 0:
                wvAboutUs.loadUrl("http://35.197.194.223:8090/admin/help.php");
                break;
            case 1:
                wvAboutUs.loadUrl("http://35.197.194.223:8090/admin/about.php");
                break;
            case 2:
                wvAboutUs.loadUrl("http://35.197.194.223:8090/admin/contact.php");
                break;
            case 3:
                wvAboutUs.loadUrl("http://35.197.194.223:8090/admin/general_terms.php");
                break;
            case 4:
                wvAboutUs.loadUrl("http://35.197.194.223:8090/admin/privacy_policy.php");
                break;
            case 5:
                wvAboutUs.loadUrl("http://35.197.194.223:8090/admin/refund.php");
                break;
        }
    }

}
