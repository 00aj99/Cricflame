package com.cricflame.cricflame;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

public class AboutUsActivity extends AppCompatActivity {
    ImageView back;
    private ListView ltvAboutUs;
    String[] match = {
            "Help",
            "About Us",
            "Contact Us",
            "Term & Service",
            "Privacy Policy",
            "Refund and Cancelation Policy"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ltvAboutUs = findViewById(R.id.ltv_about_us);
        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), R.layout.help_list_item, match);
        ltvAboutUs.setAdapter(adapter);


        ltvAboutUs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent;
                switch (i){
                    case 0:
                        intent = new Intent(AboutUsActivity.this,AboutUsWebViewActivity.class);
                        intent.putExtra("item",i);
                        startActivity(intent);
                        break;

                    case 1:
                        intent = new Intent(AboutUsActivity.this,AboutUsWebViewActivity.class);
                        intent.putExtra("item",i);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(AboutUsActivity.this,AboutUsWebViewActivity.class);
                        intent.putExtra("item",i);
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(AboutUsActivity.this,AboutUsWebViewActivity.class);
                        intent.putExtra("item",i);
                        startActivity(intent);
                        break;
                    case 4:
                        intent = new Intent(AboutUsActivity.this,AboutUsWebViewActivity.class);
                        intent.putExtra("item",i);
                        startActivity(intent);
                        break;
                    case 5:
                        intent = new Intent(AboutUsActivity.this,AboutUsWebViewActivity.class);
                        intent.putExtra("item",i);
                        startActivity(intent);
                        break;
                }
            }
        });
    }
}
