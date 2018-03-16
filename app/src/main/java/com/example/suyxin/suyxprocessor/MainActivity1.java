package com.example.suyxin.suyxprocessor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.suyx_annotation_lib.annotation.Hello;
import com.example.suyx_annotation_lib.annotation.router.Path;
import com.example.suyx_annotation_lib.annotation.viewinject.ViewInject;
import com.example.suyx_api_lib.inject.InjectManager;

@Path("/main/test1")
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

        name1.setText("我成功注入了name11111111111");
        value1.setText("去成功注入了value111111111111");

    }
}
