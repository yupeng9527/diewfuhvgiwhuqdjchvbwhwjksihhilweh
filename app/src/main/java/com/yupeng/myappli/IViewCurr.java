package com.yupeng.myappli;

public class IViewCurr {
    interface IView extends IBaseView{}

    abstract class doData extends BasePresenter {
        public doData(IBaseView iBaseView) {
            super(iBaseView);
        }
        public abstract void doGetCart();
    }
}
