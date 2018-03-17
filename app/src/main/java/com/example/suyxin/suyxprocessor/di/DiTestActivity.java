package com.example.suyxin.suyxprocessor.di;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.suyx_annotation_lib.annotation.dependency_inject.Inject;
import com.example.suyxin.suyxprocessor.R;

public class DiTestActivity extends AppCompatActivity {


    @Inject
   public ITestObject testObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_di_test);
        TextView tv1 = (TextView) findViewById(R.id.tv1);
//        tv1.setText(testObject.getText());

    }
}
