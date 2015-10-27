package com.example.thj.videodemo;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.RadioButton;
import android.widget.VideoView;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */

    Button playButton ;
    Button pauseBtn;
    Button stopBtn;
    VideoView videoView ;
    EditText rtspUrl ;
    RadioButton radioStream;
    RadioButton radioFile;
    MediaController mediaController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rtspUrl = (EditText)this.findViewById(R.id.url);
        playButton = (Button)this.findViewById(R.id.start_play);
        pauseBtn = (Button)this.findViewById(R.id.start_pause);
        stopBtn = (Button)this.findViewById(R.id.start_stop);
        radioStream = (RadioButton)this.findViewById(R.id.radioButtonStream);
        radioFile = (RadioButton)this.findViewById(R.id.radioButtonFile);
        mediaController = new MediaController(this);
        playButton.setOnClickListener(new Button.OnClickListener() {//播放
            public void onClick(View v) {
                if (radioStream.isChecked()) {
                    PlayRtspStream(rtspUrl.getEditableText().toString());
                } else if (radioFile.isChecked()) {
                    PlayLocalFile(rtspUrl.getEditableText().toString());
                } else {
//                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//设置全屏
//                    videoView.setVideoURI(Uri.parse("http://101.95.49.76:8589/ISMPGroupManager/js/movie.mp4"));
//                    mediaController.setMediaPlayer(videoView);
//                    videoView.setMediaController(mediaController);
                    videoView.setVideoURI(Uri.parse("rtsp://180.153.43.1:8743/realplay?devid=81155400071&channelno=0&streamtype=1&hashtoken=F46A3F94581608D2F1A53EAC4F3A74DA"));
                    videoView.requestFocus();
                    videoView.start();
                }
            }
        });

        pauseBtn.setOnClickListener(new Button.OnClickListener(){//暂停
            public  void  onClick(View v){
                if (pauseBtn.getText().toString().trim().equals("继续")) {
                    pauseBtn.setText("暂停");
                    videoView.start();
                     return;
                }
                if (videoView != null && videoView.isPlaying()){
                    videoView.pause();
                    pauseBtn.setText("继续");
                }
            }
        });

        stopBtn.setOnClickListener(new Button.OnClickListener(){//停止
            public  void  onClick(View v){
                if (videoView != null && videoView.isPlaying()){
                    videoView.stopPlayback();
                    playButton.setEnabled(true);
                }
            }
        });

        videoView = (VideoView)this.findViewById(R.id.rtsp_player);

    }

    //play rtsp stream
    private void PlayRtspStream(String rtspUrl){
        if (videoView != null && videoView.isPlaying()){
            videoView.seekTo(0);
        }
        videoView.setVideoURI(Uri.parse(rtspUrl));
        mediaController.setMediaPlayer(videoView);
        videoView.setMediaController(mediaController);
        videoView.requestFocus();
        videoView.start();
    }

    //play local file
    private void PlayLocalFile(String filePath){
        videoView.setVideoPath(Environment.getExternalStorageDirectory() + "/" + filePath);
        videoView.requestFocus();
        videoView.start();
    }
}
