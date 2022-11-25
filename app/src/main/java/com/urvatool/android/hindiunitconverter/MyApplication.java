package com.urvatool.android.hindiunitconverter;

import android.app.Application;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.urvatool.android.hindiunitconverter.util.Conversions;

public class MyApplication extends Application {


    public static boolean shouldShowAppOpenAd = true;

    @Override
    public void onCreate() {
        super.onCreate();

        MobileAds.initialize(
                this,
                new OnInitializationCompleteListener() {
                    @Override
                    public void onInitializationComplete(InitializationStatus initializationStatus) {}
                });

        new AppOpenManager(this);
        Conversions.getInstance().updateCurrencyConversions(this);
    }

}
