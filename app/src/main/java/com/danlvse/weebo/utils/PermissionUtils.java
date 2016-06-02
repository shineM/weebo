package com.danlvse.weebo.utils;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by zxy on 16/6/2.
 */
public class PermissionUtils {
    private static final int REQUEST_WRITE_PERMISSION_CODE = 1;
    private static final int REQUEST_LOCATION_PERMISSION_CODE = 2;
    public static final int[] REQUEST_CODE_LIST = {REQUEST_WRITE_PERMISSION_CODE, REQUEST_LOCATION_PERMISSION_CODE};

    public interface PermissionOperation {
        void doIfGranted(int requestCode);
    }

    public static void checkPermission(Activity context,String permissionType, int requestCode,PermissionOperation operation) {
        int storagePermission = ContextCompat.checkSelfPermission(context, permissionType);
        String[] reqPermissonList = {permissionType};
        if (storagePermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(context, reqPermissonList, requestCode);
        } else {
            operation.doIfGranted(requestCode);
        }
    }

    public static void onRequestResultAction(Activity activity,int requestCode, String[] permissions, int[] grantResults, int[] requestCodeList,PermissionOperation operation) {
        for (int i = 0; i < requestCodeList.length; i++) {
            if (requestCodeList[i] == requestCode) {
                for (int j = 0; j < permissions.length; i++) {
                    if (grantResults[j] == PackageManager.PERMISSION_GRANTED) {
                        operation.doIfGranted(requestCode);
                        return;
                    } else if (grantResults[j] == PackageManager.PERMISSION_DENIED) {
                        return;
                    }
                }
            }
        }
    }
}
