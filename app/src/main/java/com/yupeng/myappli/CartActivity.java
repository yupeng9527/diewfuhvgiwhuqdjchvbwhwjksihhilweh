package com.yupeng.myappli;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CartActivity extends AppCompatActivity {

    @BindView(R.id.ex_list)
    ExpandableListView mExList;
    @BindView(R.id.che_box)
    CheckBox mCheBox;
    @BindView(R.id.text_hj)
    TextView mTextHj;
    @BindView(R.id.but_juis)
    Button mButJuis;
    List<CartBean.ResultBean> result = new ArrayList<>();
    private CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ButterKnife.bind(this);
        cartAdapter = new CartAdapter(result);
        cartAdapter.setCheckedIS(new CartAdapter.CheckedIS() {
            @Override
            public void onisCheckChange(int groupPosition, int childPosition) {
                initDataChage();
            }

            @Override
            public void onCheckNum(int groupPosition, int childPosition) {
                initDataChage();
            }

            @Override
            public void onGroupChange(int groupPosition) {
                initDataChage();
            }
        });
        mExList.setAdapter(cartAdapter);
        Map<String,String> lmap = new HashMap<>();
        lmap.put("phone","15501186660");
        lmap.put("pwd","123456");
        HttpUtil.getHttpUtil()
                .getApi()
                .login(lmap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<LoginBean>() {
                    @Override
                    public void accept(LoginBean loginBean) throws Exception {
                        if ("0000".equals(loginBean.status)){
                            LoginBean.ResultBean result = loginBean.result;
                            Map<String,String> map = new HashMap<>();
                            map.put("userId",String.valueOf(result.userId));
                            map.put("sessionId",result.sessionId);
                            initData(map);
                        }else {

                        }
                    }


                });
    }
    private void initData(Map<String,String> map) {
        HttpUtil.getHttpUtil()
                .getApi()
                .Cart(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CartBean>() {
                    @Override
                    public void accept(CartBean cartBean) throws Exception {
                        if (cartBean != null && "0000".equals(cartBean.status)) {
                            List<CartBean.ResultBean> result = cartBean.result;
                            cartAdapter.setResult(result);
                            for (int i = 0; i < result.size(); i++) {
                                mExList.expandGroup(i);
                            }
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }
    private boolean isAllChild = false;
    private double isPrice = 0.0;
    private int mnum = 0;
    private void initDataChage() {
        isAllChild = cartAdapter.isAllcheck();
        mCheBox.setChecked(isAllChild);
        isPrice= cartAdapter.isAllcheckPrice();
        mTextHj.setText("合计￥：￥"+isPrice);
        mnum = cartAdapter.isAllchecknum();
        mButJuis.setText("去结算（"+mnum+"）");
    }

    @OnClick({R.id.che_box,R.id.but_juis})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.che_box:
                boolean newTsCheck = !isAllChild;
                isAllChild=newTsCheck;
                cartAdapter.changeischeck(isAllChild);
                initDataChage();
                break;
            case R.id.but_juis:
                Toast.makeText(this, "好使", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
