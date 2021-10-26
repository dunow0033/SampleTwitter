package com.twitter.challenge;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.within;

import com.twitter.challenge.viewmodel.WeatherViewModel;
import com.twitter.challenge.utils.TemperatureConverter;

import org.assertj.core.data.Offset;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StandardDeviationTests {

    @Test
    public void testGetStandardDeviation() {
        final Offset<Double> precision = within(0.001);
        List<Float> temps1 = new ArrayList<Float>() {{
            add(1f);
            add(2f);
            add(7f);
            add(87f);
            add(50f);
        }};
        List<Float> temps2 = new ArrayList<Float>() {{
            add(-21f);
            add(45f);
            add(23f);
            add(6f);
            add(-5f);
        }};
        List<Float> temps3 = new ArrayList<Float>() {{
            add(-3f);
            add(-2f);
            add(-7f);
            add(-87f);
            add(-50f);
        }};
        List<Float> temps4 = new ArrayList<Float>() {{
            add(75f);
            add(22f);
            add(17f);
            add(7f);
            add(23f);
        }};
        List<Float> temps5 = new ArrayList<Float>() {{
            add(-9f);
            add(-6f);
            add(7f);
            add(7f);
            add(-5f);
        }};

        WeatherViewModel weatherViewModel = new WeatherViewModel();

        assertThat(weatherViewModel.calculateStandardDeviation(temps1)).isEqualTo(38.082, precision);
        assertThat(weatherViewModel.calculateStandardDeviation(temps2)).isEqualTo(25.471, precision);
        assertThat(weatherViewModel.calculateStandardDeviation(temps3)).isEqualTo(37.718, precision);
        assertThat(weatherViewModel.calculateStandardDeviation(temps4)).isEqualTo(26.593, precision);
        assertThat(weatherViewModel.calculateStandardDeviation(temps5)).isEqualTo(7.628, precision);
    }
}
