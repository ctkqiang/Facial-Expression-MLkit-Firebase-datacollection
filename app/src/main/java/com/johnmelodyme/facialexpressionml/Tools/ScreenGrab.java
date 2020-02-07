package com.johnmelodyme.facialexpressionml.Tools;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import android.view.View.MeasureSpec;

/**
 * @Author : John Melody Melissa
 * @Copyright: John Melody Melissa  © Copyright 2020
 * @INPIREDBYGF : Sin Dee <3
 *
 * @CLASS: ScreenGrab
 */

public class ScreenGrab {
    private static ScreenGrab mInstance;

    private ScreenGrab() {
    }

    public static ScreenGrab getInstance() {
        if (mInstance == null) {
            synchronized (ScreenGrab.class) {
                if (mInstance == null) {
                    mInstance = new ScreenGrab();
                }
            }
        }
        return mInstance;
    }
    /**
     * Measures and takes a screenshot of the provided {@link View}.
     *
     * @param view The view of which the screenshot is taken
     * @return A {@link Bitmap} for the taken screenshot.
     */
    public Bitmap takeScreenshotForView(View view) {
        view.measure(MeasureSpec.makeMeasureSpec(view.getWidth(), MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(view.getHeight(), MeasureSpec.EXACTLY));
        view.layout((int) view.getX(), (int) view.getY(), (int) view.getX() +
                        view.getMeasuredWidth(), (int) view.getY() + view.getMeasuredHeight());
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache(true);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);

        return bitmap;
    }

    public Bitmap takeScreenshotForScreen(Activity activity) {
        return takeScreenshotForView(activity.getWindow().getDecorView().getRootView());
    }
}