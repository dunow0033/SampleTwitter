package com.twitter.challenge.data.repository;

import com.twitter.challenge.data.network.RetrofitInstance;
import com.twitter.challenge.model.WeatherConditions;

import io.reactivex.Single;

public class WeatherRepo {
    private static WeatherRepo INSTANCE = null;

    private WeatherRepo() {

    }

    public static WeatherRepo getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new WeatherRepo();
        }
        return INSTANCE;
    }

    public Single<WeatherConditions> getTodaysWeather() {
        return RetrofitInstance.getINSTANCE().getTodaysWeather();
    }
    public Single<WeatherConditions> getWeatherForFutureDay(int i) {
        return RetrofitInstance.getINSTANCE().getFutureWeatherForDay(i);
    }
}
