package com.yupeng.myappli;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyViewBuj extends LinearLayout {
    @BindView(R.id.text_qian)
    TextView mTextQian;
    @BindView(R.id.text_num)
    TextView mTextNum;
    @BindView(R.id.text_jian)
    TextView mTextJian;
    public int num = 1;

    public MyViewBuj(Context context) {
        super(context,null);
    }

    public MyViewBuj(Context context, AttributeSet attrs) {
        super(context, attrs);
        View inflate = LayoutInflater.from(context)
                .inflate(R.layout.layout_view, this);
        ButterKnife.bind(inflate);
    }


    @OnClick({R.id.text_qian, R.id.text_jian})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.text_qian:
                if (num>1){
                    num--;
                    mTextNum.setText(num+"");
                    if (ivIewChange!=null){
                        ivIewChange.onCurress(num);
                    }
                }else {
                    Toast.makeText(getContext(), "最小是1", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.text_jian:
                num++;
                mTextNum.setText(num+"");
                if (ivIewChange!=null){
                    ivIewChange.onCurress(num);
                }
                break;
        }
    }

    public void setIvIewChange(IvIewChange ivIewChange) {
        this.ivIewChange = ivIewChange;
    }

    public IvIewChange ivIewChange;
    public interface IvIewChange{
        void onCurress(int num);
    }
}
