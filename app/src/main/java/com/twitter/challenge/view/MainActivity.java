package com.twitter.challenge.view;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.twitter.challenge.R;
import com.twitter.challenge.databinding.ActivityMainBinding;
import com.twitter.challenge.model.WeatherConditions;
import com.twitter.challenge.utils.Constants;
import com.twitter.challenge.viewmodel.MainViewModel;
import com.twitter.challenge.utils.TemperatureConverter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MainViewModel viewModel;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        setUpViews();
        initObservers();
    }

    private void setUpViews() {
        binding.btnDeviation.setOnClickListener(v -> {
            if (isNetworkAvailable()) {
                viewModel.getStandardDeviation().observe(this, deviation -> {
                    if (deviation == null) {
                        binding.tvDeviation.setText(getString(R.string.deviation_error));
                    } else {
                        binding.tvDeviation.setText(deviation);
                    }
                });
            } else {
                Toast.makeText(this, R.string.no_network_available, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initObservers() {
        viewModel.getCurrentWeather().observe(this, weatherConditions -> {
            binding.tvTemperature.setText(getString(R.string.temperature,
                    weatherConditions.getWeather().getTemp(),
                    TemperatureConverter.celsiusToFahrenheit(
                            weatherConditions.getWeather().getTemp())));

            binding.tvWindSpeed.setText(String.format(getString(R.string.wind_speed),
                    weatherConditions.getWind().getSpeed()));

            if (weatherConditions.getClouds().getCloudiness() > 50) {
                binding.ivCloud.setVisibility(View.VISIBLE);
            } else {
                binding.ivCloud.setVisibility(View.GONE);
            }
        });
    }

    private boolean isNetworkAvailable() {
        final ConnectivityManager connectivityManager =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    @Override
    protected void onSaveInstanceState(@NonNull @NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Constants.DEVIATION, binding.tvDeviation.getText().toString());
        outState.putString(Constants.TEMPERATURE, binding.tvTemperature.getText().toString());
        outState.putString(Constants.WIND_SPEED, binding.tvWindSpeed.getText().toString());
        outState.putInt(Constants.CLOUD_VISIBILITY, binding.ivCloud.getVisibility());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String deviation = savedInstanceState.getString(Constants.DEVIATION);
        if (deviation != null) {
            binding.tvDeviation.setText(deviation);
        }

        String temperature = savedInstanceState.getString(Constants.TEMPERATURE);
        if (temperature != null) {
            binding.tvTemperature.setText(temperature);
        }

        String windSpeed = savedInstanceState.getString(Constants.WIND_SPEED);
        if (windSpeed != null) {
            binding.tvWindSpeed.setText(windSpeed);
        }

        int cloudVisibility = savedInstanceState.getInt(Constants.CLOUD_VISIBILITY);
        if (cloudVisibility != 0) {
            binding.ivCloud.setVisibility(cloudVisibility);
        }
    }
}
