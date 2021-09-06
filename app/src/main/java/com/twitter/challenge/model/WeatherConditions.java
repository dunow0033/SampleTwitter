package com.twitter.challenge.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Objects;

public class WeatherConditions {
    private Coord cord;
    private Weather weather;
    private Wind wind;
    private Rain rain;
    private Clouds clouds;
    private String name;

    public WeatherConditions(Coord cord, Weather weather, Wind wind, Rain rain, Clouds clouds, String name) {
        this.cord = cord;
        this.weather = weather;
        this.wind = wind;
        this.rain = rain;
        this.clouds = clouds;
        this.name = name;
    }

    public Coord getCord() {
        return cord;
    }

    public void setCord(Coord cord) {
        this.cord = cord;
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Rain getRain() {
        return rain;
    }

    public void setRain(Rain rain) {
        this.rain = rain;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeatherConditions that = (WeatherConditions) o;
        return Objects.equals(cord, that.cord) &&
                Objects.equals(weather, that.weather) &&
                Objects.equals(wind, that.wind) &&
                Objects.equals(rain, that.rain) &&
                Objects.equals(clouds, that.clouds) &&
                Objects.equals(name, that.name);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(cord, weather, wind, rain, clouds, name);
    }

    @Override
    public String toString() {
        return "WeatherConditions{" +
                "cord=" + cord +
                ", weather=" + weather +
                ", wind=" + wind +
                ", rain=" + rain +
                ", clouds=" + clouds +
                ", name='" + name + '\'' +
                '}';
    }
}
