package com.twitter.challenge.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.twitter.challenge.model.WeatherConditions;
import com.twitter.challenge.data.repository.WeatherRepo;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class WeatherViewModel extends ViewModel {
    public static final String TAG = WeatherViewModel.class.getName();

    private final MutableLiveData<WeatherConditions> todaysWeather;
    private final MutableLiveData<String> futureWeatherStandardDeviation;
    private final List<WeatherConditions> futureWeatherList;

    private final CompositeDisposable disposable = new CompositeDisposable();

    public WeatherViewModel() {
        super();
        todaysWeather = new MutableLiveData<>();
        futureWeatherStandardDeviation = new MutableLiveData<>();
        futureWeatherList = new ArrayList<>();
    }

    {
        fetchTodaysWeather();
    }

    @NonNull
    public LiveData<WeatherConditions> getTodaysWeather() {
        return todaysWeather;
    }

    @NonNull
    public LiveData<String> getFutureWeatherStandardDeviation() {
        return futureWeatherStandardDeviation;
    }

    private void fetchTodaysWeather() {
        disposable.add(WeatherRepo.getInstance()
                .getTodaysWeather()
                .subscribeOn(Schedulers.io())
                .subscribe(this::onTodaysWeatherSuccess, this::onError));
    }

    public void fetchFutureWeather() {
        for (int i = 1; i <= 5; i++) {
            disposable.add(WeatherRepo.getInstance()
                    .getWeatherForFutureDay(i)
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::onFutureWeatherSuccess, this::onError));
        }
    }

    private void onFutureWeatherSuccess(@Nullable WeatherConditions weatherConditions) {
        if (weatherConditions != null) {
            futureWeatherList.add(weatherConditions);
        } else {
            Log.d(TAG, "Empty search results for future weather.");
        }
        if (futureWeatherList.size() == 5) {
            updateStandardDeviationLd();
        }
    }

    private void onTodaysWeatherSuccess(@Nullable WeatherConditions weatherConditions) {
        if (weatherConditions != null) {
            todaysWeather.postValue(weatherConditions);
        } else {
            Log.d(TAG, "Empty search result for current weather.");
        }
    }

    private void onError(@NonNull Throwable throwable) {
        Log.d(TAG, "Error occurred: %s", throwable);
    }

    /**
     * Updates {@link WeatherViewModel#futureWeatherStandardDeviation} with calculated standard deviation of 5 future
     * weather dates responses.
     */
    private void updateStandardDeviationLd() {
        List<Float> futureWeather = createFutureWeatherList(futureWeatherList);

        Double deviation = calculateStandardDeviation(futureWeather);

        futureWeatherStandardDeviation.postValue(String.valueOf(deviation));
    }

    private List<Float> createFutureWeatherList(List<WeatherConditions> weatherConditionsList) {
        List<Float> temperatures = new ArrayList<>();
        for (WeatherConditions weatherConditions : weatherConditionsList) {
            temperatures.add(weatherConditions.getWeather().getTemp());
        }
        return temperatures;
    }

    /**
     * Standard deviation formula.
     */
    @NonNull
    public double calculateStandardDeviation(@NonNull List<Float> temps) {
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
