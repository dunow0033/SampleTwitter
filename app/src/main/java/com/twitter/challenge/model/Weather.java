package com.twitter.challenge.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Objects;

public class Weather {
    private float temp;
    private double pressure;
    private double humidity;

    public Weather(float temp, double pressure, double humidity) {
        this.temp = temp;
        this.pressure = pressure;
        this.humidity = humidity;
    }

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Weather weather = (Weather) o;
        return Float.compare(weather.temp, temp) == 0 && Double.compare(weather.pressure, pressure) == 0 && Double.compare(weather.humidity, humidity) == 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(temp, pressure, humidity);
    }

    @Override
    public String toString() {
        return "Weather{" +
                "temp=" + temp +
                ", pressure=" + pressure +
                ", humidity=" + humidity +
                '}';
    }
}
