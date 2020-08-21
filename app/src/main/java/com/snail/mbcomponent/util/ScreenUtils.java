package com.snail.mbcomponent.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.ViewGroup;

import androidx.core.widget.NestedScrollView;

/**
 * 用于获取屏幕的大小
 * Created by Administrator on 2016/3/29.
 */
public class ScreenUtils {

    private static int screen_w = 0;
    private static int screen_h = 0;
    private static float density = 0.0f;


    /**
     * 获取屏幕的宽度
     */
    public static int getScreen_w(Context context) {
        if (ScreenUtils.screen_w <= 0) {
            ScreenUtils.getScreenByContext(context);
        }

        return ScreenUtils.screen_w;
    }

    /**
     * 获取屏幕的宽度
     */
    public static int getScreen_h(Context context) {
        if (ScreenUtils.screen_h <= 0) {
            ScreenUtils.getScreenByContext(context);
        }

        return ScreenUtils.screen_h;
    }


    /**
     * 获取屏幕的宽度
     */
    private static void getScreenByContext(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        ScreenUtils.screen_w = metrics.widthPixels;
        ScreenUtils.screen_h = metrics.heightPixels;
        ScreenUtils.density = metrics.density;
    }

    /**
     * 根据输入的比例将手机的高度除以ratio,
     * 如果ratio <= 0 就直接的返回全手机的高度。
     */
    public static int getHeightSizeByRatio(Context context, float ratio) {
        int w = ScreenUtils.getScreen_h(context);
        if (ratio <= 0) {
            return w;
        }

        return (int) (w / ratio);
    }


    /**
     * 返回手机的比率
     */
    public static float getDensity(Context context) {
        if (ScreenUtils.density <= 0.0f) {
            ScreenUtils.getScreenByContext(context);
        }

        return ScreenUtils.density;
    }


    /**
     * 获取ScrollView屏幕截图
     */
    public static Bitmap shotScrollView(NestedScrollView scrollView) {
        int h = 0;
        Bitmap bitmap;
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
            scrollView.getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff"));
        }
        bitmap = Bitmap.createBitmap(scrollView.getWidth(), h, Bitmap.Config.RGB_565);
        final Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);
        return bitmap;
    }

    /**
     * 获取ScrollView屏幕截图
     */
    public static Bitmap shotScrollView(NestedScrollView scrollView, int color) {
        int h = 0;
        Bitmap bitmap;
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
            scrollView.getChildAt(i).setBackgroundColor(color);
        }
        bitmap = Bitmap.createBitmap(scrollView.getWidth(), h, Bitmap.Config.RGB_565);
        final Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);
        return bitmap;
    }

    /**
     * 获取ViewGroup屏幕截图
     */
    public static Bitmap shotGroupView(ViewGroup group) {
        int h = 0;
        Bitmap bitmap;
        for (int i = 0; i < group.getChildCount(); i++) {
            h += group.getChildAt(i).getHeight();
            group.getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff"));
        }
        bitmap = Bitmap.createBitmap(group.getWidth(), h, Bitmap.Config.RGB_565);
        final Canvas canvas = new Canvas(bitmap);
        group.draw(canvas);
        return bitmap;
    }

    /**
     * 获取ViewGroup屏幕截图
     */
    public static Bitmap shotGroupView(ViewGroup group, int color) {
        int h = 0;
        Bitmap bitmap;
        for (int i = 0; i < group.getChildCount(); i++) {
            h += group.getChildAt(i).getHeight();
            group.getChildAt(i).setBackgroundColor(color);
        }
        bitmap = Bitmap.createBitmap(group.getWidth(), h, Bitmap.Config.RGB_565);
        final Canvas canvas = new Canvas(bitmap);
        group.draw(canvas);
        return bitmap;
    }

    /**
     * 获取ViewGroup屏幕截图
     */
    public static Bitmap shotProductLabel(ViewGroup group) {
        int h = 0;
        Bitmap bitmap;
        for (int i = 0; i < group.getChildCount(); i++) {
            h += group.getChildAt(i).getHeight();
        }
        bitmap = Bitmap.createBitmap(group.getWidth(), h, Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        group.draw(canvas);
        return bitmap;
    }
}
