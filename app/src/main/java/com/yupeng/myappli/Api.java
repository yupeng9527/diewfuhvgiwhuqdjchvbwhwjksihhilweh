package com.yupeng.myappli;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

public interface Api {
    String DATA_WL="http://172.17.8.100/";

    @GET("small/commodity/v1/bannerShow")
    Observable<ImageBean> Imago();

    @GET("small/order/verify/v1/findShoppingCart")
    Observable<CartBean> Cart(@HeaderMap Map<String,String> map);

    @FormUrlEncoded
    @POST("small/user/v1/login")
    Observable<LoginBean> login(@FieldMap Map<String,String> lmap);
}
















