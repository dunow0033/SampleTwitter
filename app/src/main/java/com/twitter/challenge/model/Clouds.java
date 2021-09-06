package com.twitter.challenge.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Objects;

public class Clouds {
    private double cloudiness;

    public Clouds(double cloudiness) {
        this.cloudiness = cloudiness;
    }

    public double getCloudiness() {
        return cloudiness;
    }

    public void setCloudiness(double cloudiness) {
        this.cloudiness = cloudiness;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clouds clouds = (Clouds) o;
        return Double.compare(clouds.cloudiness, cloudiness) == 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(cloudiness);
    }

    @Override
    public String toString() {
        return "Clouds{" +
                "cloudiness=" + cloudiness +
                '}';
    }
}
