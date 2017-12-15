package com.anna.pdd;

import android.app.Application;

import com.anna.pdd.Retrofit.PddApi;

import io.realm.Realm;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.google.android.gms.ads.MobileAds;
/**
 * Created by anna on 11/16/17.
 */

public class PddApplication extends Application {
    private static PddApi sPddApi;
    private Retrofit mRetrofit;
    @Override
    public void onCreate() {
        super.onCreate();
        mRetrofit = new Retrofit.Builder()
                .baseUrl("http://138.68.157.14/") //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                .build();
        MobileAds.initialize(this, "ca-app-pub-9424115462607298~5289866307");
        Realm.init(this);
        sPddApi = mRetrofit.create(PddApi.class);
    }

    public static PddApi getPddApi() {
        return sPddApi;
    }
}
