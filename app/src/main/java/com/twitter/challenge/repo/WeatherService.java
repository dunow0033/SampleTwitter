package com.twitter.challenge.repo;

import com.twitter.challenge.model.WeatherConditions;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

interface WeatherService {
    @GET("current.json")
    Single<WeatherConditions> getCurrentWeather();

    @GET("/future_{n}.json")
    Single<WeatherConditions> getWeatherForDay(@Path("n") int day);
}
