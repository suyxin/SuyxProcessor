package com.suyxin.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.suyx_annotation_lib.annotation.Hello;
import com.example.suyx_annotation_lib.annotation.ViewInject;
import com.example.suyx_api_lib.InjectManager;
import com.example.suyxin.suyxprocessor.R;


@Hello(name = "hello",text = "MainActivity1")
public class MainActivity1 extends AppCompatActivity {
    @ViewInject(R.id.name)
    TextView name1;

    @ViewInject(R.id.value)
    TextView value1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InjectManager.getInstance().inject(this);

        name1.setText("我成功注入了name");
        value1.setText("去成功注入了value");

    }
}
