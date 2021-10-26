package com.twitter.challenge.data.network;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.twitter.challenge.utils.Constants;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class RetrofitInstance {
    private static WeatherService INSTANCE = null;

    private RetrofitInstance() {

    }

    public static WeatherService getINSTANCE() {
        if (INSTANCE == null)
            INSTANCE = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(MoshiConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(getClient())
                    .build()
                    .create(WeatherService.class);

        return INSTANCE;
    }

    private static OkHttpClient getClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();
    }
}
