package com.twitter.challenge.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.squareup.moshi.Json;

import java.util.Objects;

public class Rain {
    @Json(name = "3h")
    private double h;

    public Rain(double h) {
        this.h = h;
    }

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rain rain = (Rain) o;
        return Double.compare(rain.h, h) == 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(h);
    }

    @Override
    public String toString() {
        return "Rain{" +
                "h=" + h +
                '}';
    }
}
