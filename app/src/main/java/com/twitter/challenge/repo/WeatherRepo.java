package com.twitter.challenge.repo;

import com.twitter.challenge.model.WeatherConditions;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

public class WeatherRepo {
    private static WeatherRepo INSTANCE = null;

    private WeatherRepo() {

    }

    public Single<WeatherConditions> getCurrentWeather() {
        return RetrofitInstance.getINSTANCE().getCurrentWeather();
    }
    public Single<WeatherConditions> getWeatherForFutureDay(int i) {
        return RetrofitInstance.getINSTANCE().getWeatherForDay(i);
    }

    public static WeatherRepo getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new WeatherRepo();
        }
        return INSTANCE;
    }
}
