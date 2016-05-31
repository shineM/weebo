package com.danlvse.weebo.ui.widget;


import android.content.Context;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import com.danlvse.weebo.R;

/**
 * Created by zxy on 16/5/25.
 */
public class WeiboContentClickableSpan extends ClickableSpan {

    private Context mContext;

    public WeiboContentClickableSpan(Context context) {
        mContext = context;
    }

    @Override
    public void onClick(View widget) {
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        ds.setColor(mContext.getResources().getColor(R.color.colorPrimary));
        ds.setUnderlineText(false);
    }


}