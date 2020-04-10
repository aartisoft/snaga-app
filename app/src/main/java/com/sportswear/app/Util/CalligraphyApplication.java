package com.sportswear.app.Util;

import android.app.Application;

public class CalligraphyApplication extends Application {



    private static CalligraphyApplication mInstance;

    public static synchronized CalligraphyApplication getInstance() {
        return mInstance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        /*CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/futura_medium_bt.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );*/

    }

}
