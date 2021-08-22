package com.android.picture_library.luck.picture.lib.engine;

import android.content.Context;

import com.android.picture_library.luck.picture.lib.entity.LocalMedia;
import com.android.picture_library.luck.picture.lib.listener.OnCallbackListener;

import java.util.List;

/**
 * @author：luck
 * @date：2021/5/19 9:36 AM
 * @describe：CompressEngine
 */
public interface CompressEngine {
    /**
     * compress
     * ## The isCompressed in the localMedia object needs to be changed to true and setCompressPath is the compression path}
     *
     * @param context
     * @param compressData
     * @param listener
     */
    void onCompress(Context context, List<LocalMedia> compressData, OnCallbackListener<List<LocalMedia>> listener);
}
