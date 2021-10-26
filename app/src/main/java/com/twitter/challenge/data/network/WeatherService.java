package com.twitter.challenge.data.network;

import com.twitter.challenge.model.WeatherConditions;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface WeatherService {
    @GET("current.json")
    Single<WeatherConditions> getTodaysWeather();

    @GET("/future_{n}.json")
    Single<WeatherConditions> getFutureWeatherForDay(@Path("n") int day);
}
