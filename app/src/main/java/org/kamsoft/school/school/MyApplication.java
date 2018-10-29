package org.kamsoft.school.school;

/**
 * Created by admin on 8/24/2017.
 */

import android.app.Application;

import org.kamsoft.school.school.Database.ConnectivityReceiver;

public class MyApplication extends Application {

    private static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }
}