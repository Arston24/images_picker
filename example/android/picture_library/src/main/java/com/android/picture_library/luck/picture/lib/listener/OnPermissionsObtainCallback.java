package com.android.picture_library.luck.picture.lib.listener;

import android.content.Context;

/**
 * @author：luck
 * @date：2021/5/13 11:33 AM
 * @describe：Permissions Callback
 */
public interface OnPermissionsObtainCallback {
    /**
     * Permissions Intercept show dialog
     *
     * @param context
     * @param isCamera             is use camera
     * @param tips                 permissions show tips
     * @param dialogOptionCallback
     */
    void onPermissionsIntercept(Context context, boolean isCamera, String[] permissions, String tips, OnPermissionDialogOptionCallback dialogOptionCallback);
}
