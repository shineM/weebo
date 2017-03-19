package com.danlvse.weebo.data;

/**
 * Created by zxy on 17/3/19.
 */

public interface DataCallBack<T> {
    void onSuccess(T data);

    void onException(Exception e);

    void onEmpty();
}
