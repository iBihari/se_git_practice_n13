package com.shubhamgupta16.simplewallpaper;

import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.shubhamgupta16.simplewallpaper.activities.SplashActivity;
import com.shubhamgupta16.simplewallpaper.data_source.DataService;
import com.shubhamgupta16.simplewallpaper.data_source.SQLDataServiceImpl;
import com.shubhamgupta16.simplewallpaper.data_source.InitSQL;
import com.shubhamgupta16.simplewallpaper.data_source.SQLWallpapers;
import com.shubhamgupta16.simplewallpaper.models.utils.SQLFav;
import com.shubhamgupta16.simplewallpaper.models.utils.Utils;


public class MainApplication extends android.app.Application
        implements ActivityLifecycleCallbacks, DefaultLifecycleObserver {

    private Activity currentActivity;

    private static final String TAG = "MyApplication";

    private int interstitialAfterClicks;
    private int cardClicks;
    private DataService dataService;

    public static DataService getDataService(Application application) {
        return ((MainApplication) application).dataService;
    }
    public void interstitialShown(){
        Log.d(TAG, "interstitialShown: called");
        cardClicks = 0;
    }
    public boolean canShowInterstitial(){
        cardClicks++;
        Log.d(TAG, "canShowInterstitial: " + cardClicks);
        return cardClicks >= interstitialAfterClicks;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.registerActivityLifecycleCallbacks(this);

        interstitialAfterClicks = getResources().getInteger(R.integer.interstitial_after_clicks);
        cardClicks = interstitialAfterClicks - 2;
        Utils.initTheme(this);

        SQLWallpapers sqlWallpapers = new SQLWallpapers(this);
        SQLFav sqlFav = new SQLFav(this);
        dataService = new SQLDataServiceImpl(sqlWallpapers, sqlFav);
        InitSQL.apply(this, sqlWallpapers, sqlFav);

        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
    }

    @Override
    public void onStart(@NonNull LifecycleOwner owner) {
    }

    private void tryShowSplashAd(){
        if (currentActivity instanceof SplashActivity) {
        }
    }

    /**
     * ActivityLifecycleCallback methods.
     */
    @Override
    public void onActivityStarted(@NonNull Activity activity) {
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
    }

}
