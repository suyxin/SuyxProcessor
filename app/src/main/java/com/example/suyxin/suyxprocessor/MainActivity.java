package com.example.suyxin.suyxprocessor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.suyx_annotation_lib.annotation.Hello;
import com.example.suyx_annotation_lib.annotation.Path;
import com.example.suyx_annotation_lib.annotation.ViewInject;
import com.example.suyx_api_lib.inject.InjectManager;
import com.example.suyx_api_lib.router.Router;

@Path("/main/home")
@Hello(name = "hello",text = "suyxin")
public class MainActivity extends AppCompatActivity {
    @ViewInject(R.id.name)
    TextView name;

    @ViewInject(R.id.value)
    TextView value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InjectManager.getInstance().inject(this);

        name.setText("我成功注入了name");
        value.setText("去成功注入了value");

        Router.getInstance().toActivity(this, "/main/test1");
    }
}
