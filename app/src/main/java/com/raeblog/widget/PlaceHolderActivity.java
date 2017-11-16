package com.raeblog.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.rae.widget.RaePlaceHolderLayout;

/**
 * Created by ChenRui on 2017/4/18 0018 16:10.
 */
public class PlaceHolderActivity extends AppCompatActivity {
    private ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_holder);
        final RaePlaceHolderLayout layout = findViewById(R.id.layout_content);
        listView = findViewById(R.id.lv_demo);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.widget_dialog_item_filter, R.id.tv_name);
        adapter.add("123");
        adapter.add("123");
        adapter.add("123");
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        layout.subscribe(listView.getAdapter());
        layout.setRetryClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "重试按钮点击", Toast.LENGTH_SHORT).show();
            }
        });

//        listView.postDelayed(new Runnable() {
//            @Override
//            public void run() {
////                layout.onNetWorkError();
//            }
//        }, 1000);
    }

    public void onClick(View view) {
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) listView.getAdapter();
        adapter.clear();
        adapter.notifyDataSetChanged();
    }
}
