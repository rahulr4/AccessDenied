package com.kairos.android.example;

import android.app.Application;

import com.kairos.Kairos;
import com.rahul.media.model.Define;

/**
 * Created by bkhera on 2/22/2018.
 */

public class AppController extends Application {

    private Kairos kairos = new Kairos();

    @Override
    public void onCreate() {
        super.onCreate();
        initializeSdk();
    }

    /*initialized sdk*/
    private void initializeSdk() {
        Define.MEDIA_PROVIDER = getString(R.string.image_provider);
        String app_id = "c7d15241";
        String api_key = "fd3287889f836397be1857dd4d0adb11";
        kairos.setAuthentication(this, app_id, api_key);
    }
}
