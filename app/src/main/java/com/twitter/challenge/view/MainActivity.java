package com.twitter.challenge.view;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.twitter.challenge.R;
import com.twitter.challenge.databinding.ActivityMainBinding;
import com.twitter.challenge.utils.Constants;
import com.twitter.challenge.viewmodel.WeatherViewModel;
import com.twitter.challenge.utils.TemperatureConverter;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends BaseActivity {

    private WeatherViewModel viewModel;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(WeatherViewModel.class);
        initViews();
        initTodaysWeatherObserver();
    }

    private void initViews() {
        binding.deviationBtn.setOnClickListener(v -> {
            if (isNetworkAvailable()) {
                viewModel.fetchFutureWeather();
                viewModel.getFutureWeatherStandardDeviation().observe(this, deviation -> {
                    binding.deviationTv.setText(deviation);
                });
            } else {
                showToast(R.string.network_unavailable);
            }
        });
    }

    private void initTodaysWeatherObserver() {
        viewModel.getTodaysWeather().observe(this, weatherConditions -> {
            binding.tempTv.setText(getString(R.string.temperature,
                    weatherConditions.getWeather().getTemp(),
                    TemperatureConverter.celsiusToFahrenheit(
                            weatherConditions.getWeather().getTemp())));

            binding.windSpeedTv.setText(String.format(getString(R.string.wind_speed),
                    weatherConditions.getWind().getSpeed()));

            if (weatherConditions.getClouds().getCloudiness() > 50) {
                binding.cloudIv.setVisibility(View.VISIBLE);
            } else {
                binding.cloudIv.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull @NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Constants.STANDARD_DEVIATION, binding.deviationTv.getText().toString());
        outState.putString(Constants.TEMPERATURE, binding.tempTv.getText().toString());
        outState.putString(Constants.WIND_SPEED, binding.windSpeedTv.getText().toString());
        outState.putInt(Constants.CLOUD_VISIBILITY, binding.cloudIv.getVisibility());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String deviation = savedInstanceState.getString(Constants.STANDARD_DEVIATION);
        if (deviation != null) {
            binding.deviationTv.setText(deviation);
        }

        String temperature = savedInstanceState.getString(Constants.TEMPERATURE);
        if (temperature != null) {
            binding.tempTv.setText(temperature);
        }

        String windSpeed = savedInstanceState.getString(Constants.WIND_SPEED);
        if (windSpeed != null) {
            binding.windSpeedTv.setText(windSpeed);
        }

        int cloudVisibility = savedInstanceState.getInt(Constants.CLOUD_VISIBILITY);
        if (cloudVisibility != 0) {
            binding.cloudIv.setVisibility(cloudVisibility);
        }
    }
}
