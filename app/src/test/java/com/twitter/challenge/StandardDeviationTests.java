package com.twitter.challenge;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.within;

import com.twitter.challenge.viewmodel.MainViewModel;
import com.twitter.challenge.utils.TemperatureConverter;

import org.assertj.core.data.Offset;
import org.junit.Test;
import org.robolectric.RuntimeEnvironment;

import java.util.Arrays;
import java.util.List;

public class StandardDeviationTests {
    @Test
    public void testGetStandardDeviation() {
        final Offset<Double> precision = within(0.001);
        List<Float> tempsTest1 = Arrays.asList(23f,50f,40f,29f,30f);
        List<Float> tempsTest2 = Arrays.asList(-1f,-5f,-2f,-5f,-3f);
        List<Float> tempsTest3 = Arrays.asList(1f,5f,2f,5f,3f);
        List<Float> tempsTest4 = Arrays.asList(-1f,5f,-2f,-5f,3f);
        List<Float> tempsTest5 = Arrays.asList(-5f,5f,10f,15f,20f);
        List<Float> tempsTest6 = Arrays.asList(10f,8f,10f,8f,10f);
        MainViewModel mainViewModel = new MainViewModel(RuntimeEnvironment.getApplication());
        assertThat(mainViewModel.getStandardDeviation(tempsTest1)).isEqualTo(10.644, precision);
        assertThat(mainViewModel.getStandardDeviation(tempsTest2)).isEqualTo(1.789, precision);
        assertThat(mainViewModel.getStandardDeviation(tempsTest3)).isEqualTo(1.789, precision);
        assertThat(mainViewModel.getStandardDeviation(tempsTest4)).isEqualTo(4, precision);
        assertThat(mainViewModel.getStandardDeviation(tempsTest5)).isEqualTo(9.618, precision);
        assertThat(mainViewModel.getStandardDeviation(tempsTest6)).isEqualTo(1.095, precision);
    }
    @Test
    public void testCelsiusToFahrenheitConversion() {
        final Offset<Float> precision = within(0.01f);

        assertThat(TemperatureConverter.celsiusToFahrenheit(-50)).isEqualTo(-58, precision);
        assertThat(TemperatureConverter.celsiusToFahrenheit(0)).isEqualTo(32, precision);
        assertThat(TemperatureConverter.celsiusToFahrenheit(10)).isEqualTo(50, precision);
        assertThat(TemperatureConverter.celsiusToFahrenheit(21.11f)).isEqualTo(70, precision);
        assertThat(TemperatureConverter.celsiusToFahrenheit(37.78f)).isEqualTo(100, precision);
        assertThat(TemperatureConverter.celsiusToFahrenheit(100)).isEqualTo(212, precision);
        assertThat(TemperatureConverter.celsiusToFahrenheit(1000)).isEqualTo(1832, precision);
    }
}
