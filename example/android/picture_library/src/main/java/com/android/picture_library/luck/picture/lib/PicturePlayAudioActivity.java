package com.android.picture_library.luck.picture.lib;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;

import com.luck.picture.lib.R;
import com.android.picture_library.luck.picture.lib.config.*;
import com.android.picture_library.luck.picture.lib.tools.DateUtils;
import com.android.picture_library.luck.picture.lib.tools.SdkVersionUtils;


/**
 * # No longer maintain audio related functions,
 * but can continue to use but there will be phone compatibility issues.
 * <p>
 * 不再维护音频相关功能，但可以继续使用，可能会有机型兼容性问题
 */
@Deprecated
public class PicturePlayAudioActivity extends PictureBaseActivity implements View.OnClickListener {
    private String audio_path;
    private MediaPlayer mediaPlayer;
    private SeekBar musicSeekBar;
    private boolean isPlayAudio = false;
    private TextView tv_PlayPause;
    private TextView tv_musicStatus;
    private TextView tv_musicTotal;
    private TextView tv_musicTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                , WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getResourceId() {
        return R.layout.picture_play_audio;
    }

    @Override
    protected void initWidgets() {
        super.initWidgets();
        audio_path = getIntent().getStringExtra(PictureConfig.EXTRA_AUDIO_PATH);
        tv_musicStatus = findViewById(R.id.tv_musicStatus);
        tv_musicTime = findViewById(R.id.tv_musicTime);
        musicSeekBar = findViewById(R.id.musicSeekBar);
        tv_musicTotal = findViewById(R.id.tv_musicTotal);
        tv_PlayPause = findViewById(R.id.tv_PlayPause);
        TextView tv_Stop = findViewById(R.id.tv_Stop);
        TextView tv_Quit = findViewById(R.id.tv_Quit);
        mHandler.postDelayed(() -> initPlayer(audio_path), 30);
        tv_PlayPause.setOnClickListener(this);
        tv_Stop.setOnClickListener(this);
        tv_Quit.setOnClickListener(this);
        musicSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                if (mediaPlayer != null) {
                    tv_musicTime.setText(DateUtils.formatDurationTime(mediaPlayer.getCurrentPosition()));
                    musicSeekBar.setProgress(mediaPlayer.getCurrentPosition());
                    musicSeekBar.setMax(mediaPlayer.getDuration());
                    tv_musicTotal.setText(DateUtils.formatDurationTime(mediaPlayer.getDuration()));
                    mHandler.postDelayed(runnable, 200);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    /**
     * 初始化音频播放组件
     *
     * @param path
     */
    private void initPlayer(String path) {
        mediaPlayer = new MediaPlayer();
        try {
            if (PictureMimeType.isContent(path)) {
                mediaPlayer.setDataSource(getContext(), Uri.parse(path));
            } else {
                mediaPlayer.setDataSource(path);
            }
            mediaPlayer.prepare();
            mediaPlayer.setLooping(true);
            playAudio();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_PlayPause) {
            playAudio();

        }
        if (i == R.id.tv_Stop) {
            tv_musicStatus.setText(getString(R.string.picture_stop_audio));
            tv_PlayPause.setText(getString(R.string.picture_play_audio));
            stop(audio_path);

        }
        if (i == R.id.tv_Quit) {
            mHandler.removeCallbacks(runnable);
            mHandler.postDelayed(() -> stop(audio_path), 30);
            try {
                exit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 播放音频
     */
    private void playAudio() {
        if (mediaPlayer != null) {
            musicSeekBar.setProgress(mediaPlayer.getCurrentPosition());
            musicSeekBar.setMax(mediaPlayer.getDuration());
        }
        String ppStr = tv_PlayPause.getText().toString();
        if (ppStr.equals(getString(R.string.picture_play_audio))) {
            tv_PlayPause.setText(getString(R.string.picture_pause_audio));
            tv_musicStatus.setText(getString(R.string.picture_play_audio));
        } else {
            tv_PlayPause.setText(getString(R.string.picture_play_audio));
            tv_musicStatus.setText(getString(R.string.picture_pause_audio));
        }
        playOrPause();
        if (!isPlayAudio) {
            mHandler.post(runnable);
            isPlayAudio = true;
        }
    }

    /**
     * 停止播放
     *
     * @param path
     */
    public void stop(String path) {
        if (mediaPlayer != null) {
            try {
                mediaPlayer.stop();
                mediaPlayer.reset();
                if (PictureMimeType.isContent(path)){
                    mediaPlayer.setDataSource(getContext(),Uri.parse(path));
                } else {
                    mediaPlayer.setDataSource(path);
                }
                mediaPlayer.prepare();
                mediaPlayer.seekTo(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 暂停播放
     */
    public void playOrPause() {
        try {
            if (mediaPlayer != null) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                } else {
                    mediaPlayer.start();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        if (SdkVersionUtils.checkedAndroid_Q()) {
            finishAfterTransition();
        } else {
            super.onBackPressed();
        }
        exit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mHandler.removeCallbacks(runnable);
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
