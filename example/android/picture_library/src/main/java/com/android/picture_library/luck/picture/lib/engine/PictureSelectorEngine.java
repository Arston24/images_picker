package com.android.picture_library.luck.picture.lib.engine;


import com.android.picture_library.luck.picture.lib.entity.LocalMedia;
import com.android.picture_library.luck.picture.lib.listener.OnResultCallbackListener;

/**
 * @author：luck
 * @date：2020/4/22 11:36 AM
 * @describe：PictureSelectorEngine
 */
public interface PictureSelectorEngine {

    /**
     * Create ImageLoad Engine
     *
     * @return
     */
    ImageEngine createEngine();

    /**
     * Create Result Listener
     *
     * @return
     */
    OnResultCallbackListener<LocalMedia> getResultCallbackListener();
}
