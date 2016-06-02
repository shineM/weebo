package com.danlvse.weebo.utils;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Property;
import android.view.View;

/**
 * Created by zxy on 16/6/2.
 */
public class ViewUtils {
    public static final Property<View, Integer> BACKGROUND_COLOR
            = new AnimUtils.IntProperty<View>("backgroundColor") {

        @Override
        public void setValue(View view, int value) {
            view.setBackgroundColor(value);
        }

        @Override
        public Integer get(View view) {
            Drawable d = view.getBackground();
            if (d instanceof ColorDrawable) {
                return ((ColorDrawable) d).getColor();
            }
            return Color.TRANSPARENT;
        }
    };
}
