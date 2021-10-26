package com.twitter.challenge.view;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    /**
     * Checks for network connection.
     */
    boolean isNetworkAvailable() {
        final ConnectivityManager connectivityManager =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    void showToast(@StringRes int message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
