package com.yupeng.myappli;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.time.Instant;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private Banner ban_ner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initData() {
        HttpUtil.getHttpUtil().getApi()
                .Imago()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ImageBean>() {
                    @Override
                    public void accept(ImageBean imageBean) throws Exception {
                        ban_ner.setImages(imageBean.result);
                        ban_ner.setImageLoader(new ImageLoader() {
                            @Override
                            public void displayImage(Context context, Object path, ImageView imageView) {
                                ImageBean.ResultBean resultBean=(ImageBean.ResultBean)path;
                                Glide.with(MainActivity.this)
                                        .load(resultBean.imageUrl)
                                        .into(imageView);
                            }
                        });
                        ban_ner.isAutoPlay(true);
                        ban_ner.setDelayTime(2000);
                        ban_ner.start();
                    }
                });
    }
    private String getMetaChannel() {
        try {
            ApplicationInfo appInfo = getPackageManager()
                    .getApplicationInfo(this.getPackageName(),
                            PackageManager.GET_META_DATA);
            String value = appInfo.metaData.getString("APP_CHANNEL_VALUE");
            return value;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }
    private void initView() {
        ban_ner = (Banner) findViewById(R.id.ban_ner);
    }
}
