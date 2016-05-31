package com.danlvse.weebo.utils.weibo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.danlvse.weebo.ui.widget.ContentClickableSpan;
import com.danlvse.weebo.ui.widget.WeiboContentClickableSpan;
import com.danlvse.weebo.utils.OnWeiboContentListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zxy on 16/5/25.
 */
public class WeiboContentKeyUtil {

    private static final String AT = "@[\u4e00-\u9fa5\\w_-]{2,30}";// @人
    private static final String TOPIC = "#[\u4e00-\u9fa5\\w]+#";// ##话题
    private static final String URL = "http://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";// url
    private static final String ALL = "(@[\u4e00-\u9fa5\\w_-]{2,30})|(#[\u4e00-\u9fa5\\w]+#)|(http://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|])";

    //微博正文字符串解析
    public static SpannableString getWeiBoContent(final String source, final Context context, TextView textView, final OnWeiboContentListener listener) {
        SpannableString spannableString = new SpannableString(source);
        //设置正则
        Pattern pattern = Pattern.compile(ALL);
        Pattern patternAt = Pattern.compile(AT);
        Pattern patternTopic = Pattern.compile(TOPIC);

        Matcher matcher = pattern.matcher(spannableString);
        if (matcher.find()) {
            textView.setMovementMethod(LinkMovementMethod.getInstance());
            matcher.reset();
        }
        //记录上一个特殊字符串起始位置
        int lastStart = 0;
        /**
         *  如果匹配特殊字符串则进行对应处理，否则设置为默认格式这里
         *  这里如果不处理则非特殊的字符串点击事件会失效
         */
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            final CharSequence s = spannableString.subSequence(start, end);
            //‘@’开头的字符串
            if (patternAt.matcher(s).matches()) {
                WeiboContentClickableSpan myClickableSpan = new WeiboContentClickableSpan(context) {
                    @Override
                    public void onClick(View widget) {
                        Toast.makeText(context, "点击了用户：" + s, Toast.LENGTH_SHORT).show();
                    }
                };
                spannableString.setSpan(myClickableSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                //’##‘格式的字符串
            } else if (patternTopic.matcher(s).matches()) {
                WeiboContentClickableSpan myClickableSpan = new WeiboContentClickableSpan(context) {
                    @Override
                    public void onClick(View widget) {
                        Toast.makeText(context, "点击了话题：" + s, Toast.LENGTH_SHORT).show();
                    }
                };
                spannableString.setSpan(myClickableSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                //URL
            } else {
                WeiboContentClickableSpan myClickableSpan = new WeiboContentClickableSpan(context) {
                    @Override
                    public void onClick(View widget) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(s.toString()));
                        context.startActivity(browserIntent);
                    }
                };
                spannableString.setSpan(myClickableSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            //非特殊字符串
            spannableString.setSpan(new ContentClickableSpan(context) {
                @Override
                public void onClick(View widget) {
                    listener.onTextClick();
                }
            }, lastStart, start, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            lastStart = end;

        }
        //最后一串字符
        spannableString.setSpan(new ContentClickableSpan(context) {
            @Override
            public void onClick(View widget) {
                listener.onTextClick();
            }
        }, lastStart, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


        return spannableString;
    }

    //评论的内容,不需要普通字符串处理
    public static SpannableString getWeiBoContent(String source, final Context context, TextView editText) {
        SpannableString spannableString = new SpannableString(source);
        //设置正则
        Pattern pattern = Pattern.compile(ALL);
        Matcher matcher = pattern.matcher(spannableString);

        if (matcher.find()) {
            editText.setMovementMethod(LinkMovementMethod.getInstance());
            matcher.reset();
        }

        while (matcher.find()) {
            final String at = matcher.group(1);
            final String topic = matcher.group(2);
            final String url = matcher.group(3);

            //处理@用户
            if (at != null) {
                int start = matcher.start(1);
                int end = start + at.length();
                WeiboContentClickableSpan myClickableSpan = new WeiboContentClickableSpan(context) {
                    @Override
                    public void onClick(View widget) {
                        Toast.makeText(context, "点击了用户：" + at, Toast.LENGTH_SHORT).show();
                    }
                };
                spannableString.setSpan(myClickableSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            //处理##话题
            if (topic != null) {
                int start = matcher.start(2);
                int end = start + topic.length();
                WeiboContentClickableSpan clickableSpan = new WeiboContentClickableSpan(context) {

                    @Override
                    public void onClick(View widget) {
                        Toast.makeText(context, "点击了话题：" + topic, Toast.LENGTH_LONG).show();
                    }
                };
                spannableString.setSpan(clickableSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            // 处理url地址
            if (url != null) {
                int start = matcher.start(3);
                int end = start + url.length();
                WeiboContentClickableSpan clickableSpan = new WeiboContentClickableSpan(context) {

                    @Override
                    public void onClick(View widget) {
                        Toast.makeText(context, "点击了网址：" + url, Toast.LENGTH_LONG).show();
                    }
                };
                spannableString.setSpan(clickableSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

        }
        return spannableString;
    }
}

