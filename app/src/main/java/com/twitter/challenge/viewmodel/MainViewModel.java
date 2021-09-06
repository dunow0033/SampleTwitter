package com.twitter.challenge.viewmodel;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.twitter.challenge.model.WeatherConditions;
import com.twitter.challenge.repo.WeatherRepo;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends AndroidViewModel {

    private final MutableLiveData<WeatherConditions> currentWeather;
    private final MutableLiveData<String> standardDeviation;
    private final CompositeDisposable disposable = new CompositeDisposable();
    private final List<WeatherConditions> futureWeatherList;


    public MainViewModel(@NonNull Application application) {
        super(application);

        currentWeather = new MutableLiveData<>();
        standardDeviation = new MutableLiveData<>();
        futureWeatherList = new ArrayList<>();
    }

    {
        getFutureWeatherResponse();
        getCurrentWeatherResponse();
    }

    public LiveData<WeatherConditions> getCurrentWeather() {
        return currentWeather;
    }

    public LiveData<String> getStandardDeviation() {
        return standardDeviation;
    }

    private void getCurrentWeatherResponse() {
        disposable.add(WeatherRepo.getInstance()
                .getCurrentWeather()
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleCurrentWeatherResults, this::handleError));
    }

    private void getFutureWeatherResponse() {
        for (int i = 1; i <= 5; i++) {
            disposable.add(WeatherRepo.getInstance()
                    .getWeatherForFutureDay(i)
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleFutureWeatherResults, this::handleError));
        }
    }

    private void handleFutureWeatherResults(WeatherConditions weatherConditions) {
        if (weatherConditions != null) {
            futureWeatherList.add(weatherConditions);
        } else {
            Toast.makeText(this.getApplication().getApplicationContext(), "NO RESULTS FOUND", Toast.LENGTH_LONG).show();
        }
        if (futureWeatherList.size() == 5) {
            calculateStandardDeviation();
        }
    }

    private void handleCurrentWeatherResults(WeatherConditions weatherConditions) {
        if (weatherConditions != null) {
            currentWeather.postValue(weatherConditions);
        } else {
            Toast.makeText(this.getApplication().getApplicationContext(), "NO RESULTS FOUND", Toast.LENGTH_LONG).show();
        }
    }

    private void handleError(Throwable t) {
        Toast.makeText(this.getApplication().getApplicationContext(), "ERROR IN FETCHING API RESPONSE. Try again",
                Toast.LENGTH_LONG).show();
    }

    private void calculateStandardDeviation() {
        List<Float> futureWeather = getFutureTemps(futureWeatherList);

        Double deviation = getStandardDeviation(futureWeather);

        standardDeviation.postValue(String.valueOf(deviation));
    }

    private List<Float> getFutureTemps(List<WeatherConditions> weatherConditionsList) {
        List<Float> temps = new ArrayList<>();
        for (WeatherConditions weatherConditions : weatherConditionsList) {
            temps.add(weatherConditions.getWeather().getTemp());
        }
        return temps;
    }

    public double getStandardDeviation(List<Float> temps) {
        double n = temps.size(), sum = 0, mean;
        for (int i = 0; i < n; i++) {
            sum = sum + temps.get(i);
        }
        mean = sum / n;
        sum = 0;
        for (int i = 0; i < n; i++) {
            sum += Math.pow((temps.get(i) - mean), 2);
        }
        mean = sum / (n - 1);
        return Math.sqrt(mean);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }
}
