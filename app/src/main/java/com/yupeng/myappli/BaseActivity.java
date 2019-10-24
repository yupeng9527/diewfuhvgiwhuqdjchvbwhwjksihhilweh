package com.yupeng.myappli;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity  extends AppCompatActivity {
    protected BasePresenter basePersenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (initLayout()!=0) {
            setContentView(initLayout());
            basePersenter=initPersenter();
        }else{
            finish();
        }

    }

    protected abstract int initLayout();

    protected abstract BasePresenter initPersenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        basePersenter.onDestroy();
    }
}
























