package com.rimproject.andsensor;

import android.content.Context;

public class AndSensor extends android.app.Application {

    private static AndSensor instance;

    public AndSensor() {
        instance = this;
    }

    public static Context getContext() {
        return instance;
    }

}
