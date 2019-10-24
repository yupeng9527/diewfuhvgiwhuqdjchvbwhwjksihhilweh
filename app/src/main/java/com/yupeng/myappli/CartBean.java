package com.yupeng.myappli;

import java.util.List;

public class CartBean {
    public String message;
    public String status;
    public List<ResultBean> result;
    public static class ResultBean {
        public String categoryName;
        public List<ShoppingCartListBean> shoppingCartList;
        public static class ShoppingCartListBean {
            public int commodityId;
            public String commodityName;
            public int count;
            public String pic;
            public int price;
            public boolean isChecked;
        }
    }
}
