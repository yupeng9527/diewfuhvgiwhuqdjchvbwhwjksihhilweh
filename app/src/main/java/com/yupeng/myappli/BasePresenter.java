package com.yupeng.myappli;

public class BasePresenter {
    protected IBaseView iBaseView;

    public BasePresenter(IBaseView iBaseView) {
        this.iBaseView = iBaseView;
    }
    public void onDestroy(){
        if (iBaseView!=null) {
            iBaseView=null;
        }
    }
}
