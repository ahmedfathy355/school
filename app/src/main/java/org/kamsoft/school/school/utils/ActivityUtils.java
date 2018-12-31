package org.kamsoft.school.school.utils;

import android.app.Activity;
import android.content.Intent;


/**
 * Created by ahmed fathy on 9-3-2018.
 */
public class ActivityUtils {

    private static ActivityUtils sActivityUtils = null;

    public static ActivityUtils getInstance() {
        if (sActivityUtils == null) {
            sActivityUtils = new ActivityUtils();
        }
        return sActivityUtils;
    }

    public void invokeActivity(Activity activity, Class<?> tClass, boolean shouldFinish) {
        Intent intent = new Intent(activity, tClass);
        activity.startActivity(intent);
        if (shouldFinish) {
            activity.finish();
        }
    }


}
