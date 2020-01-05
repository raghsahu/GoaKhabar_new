package dev.news.goakhabarr.Utils;

import android.app.Application;

import com.google.android.gms.ads.MobileAds;

import dev.news.goakhabarr.R;

/**
 * Created by Raghvendra Sahu on 18-Dec-19.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // initialize the AdMob app
        MobileAds.initialize(this, getString(R.string.admob_app_id));


    }
}
