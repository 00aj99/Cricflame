package com.cricflame.cricflame.DummyBooks;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cricflame.cricflame.Global;
import com.cricflame.cricflame.R;

public class MakeMatchActivity extends AppCompatActivity {
    private ListView match_list;
    String[] match = {
            "Make Match Book One",
            "Make Match Book Two",
            "Make Match Book Three"
    };
    public static String dummy_session_url;
    public static String dummy_book_url;
    private ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_match);
        back= (ImageView) findViewById(com.cricflame.cricflame.R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        match_list = (ListView) findViewById(R.id.dummy_match_list);
        MyAdapter adapter = new MyAdapter(this,match);
        match_list.setAdapter(adapter);

        match_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0){
                    Intent intent = new Intent(MakeMatchActivity.this,DummyBook.class);
                    dummy_session_url = Global.DUMMY1_SESSION_URL;
                    dummy_book_url = Global.DUMMY1_BOOK_URL;
                    startActivity(intent);
                }

                if(i==1){
                    Intent intent = new Intent(MakeMatchActivity.this,DummyBook.class);
                    dummy_session_url = Global.DUMMY2_SESSION_URL;
                    dummy_book_url = Global.DUMMY2_BOOK_URL;
                    startActivity(intent);
                }

                if(i==2){
                    Intent intent = new Intent(MakeMatchActivity.this,DummyBook.class);
                    dummy_session_url = Global.DUMMY3_SESSION_URL;
                    dummy_book_url = Global.DUMMY3_BOOK_URL;
                    startActivity(intent);
                }
            }

        });
    }
}

class MyAdapter extends ArrayAdapter{
    String[] booksArray;

    public MyAdapter(@NonNull Context context, String[] books1) {
        super(context,R.layout.list_item,R.id.txt_title,books1);
        this.booksArray = books1;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_item,parent,false);

        TextView txtTittle = (TextView) view.findViewById(R.id.txt_title);

        txtTittle.setText(booksArray[position]);
        return view;
    }
}
