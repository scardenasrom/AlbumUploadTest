package testing.com.albumuploadtest.utils;

import android.view.View;

import com.getkeepsafe.taptargetview.TapTarget;

import testing.com.albumuploadtest.R;

public class TapTargetUtils {

    public static TapTarget getTapTarget(View anchorView, String title, String text) {
        return TapTarget.forView(anchorView, title, text)
                .outerCircleColor(R.color.primary_dark)
                .targetCircleColor(R.color.primary)
                .titleTextSize(24)
                .titleTextColor(R.color.white)
                .descriptionTextSize(18)
                .descriptionTextColor(R.color.white)
                .dimColor(R.color.black)
                .cancelable(false)
                .transparentTarget(true)
                .targetRadius(60);
    }

}
