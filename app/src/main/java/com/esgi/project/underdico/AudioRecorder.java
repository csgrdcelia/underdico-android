package com.esgi.project.underdico;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class AudioRecorder {

    Context context;
    String savePath;
    MediaRecorder mediaRecorder;
    MediaPlayer mediaPlayer;

    public AudioRecorder(Context context) {
        this.context = context;
        savePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +
                UUID.randomUUID().toString() + "AudioRecording.mp3";
    }

    public void record() throws IOException {
        if(mediaRecorder == null) {
            prepareMediaRecorder();
        }
        mediaRecorder.prepare();
        mediaRecorder.start();
    }

    public void stop() {
        mediaRecorder.stop();
    }

    public void play() throws IOException {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(savePath);
            mediaPlayer.prepare();
        }
        mediaPlayer.start();
    }

    public void deleteRecord() {
        File file = new File(savePath);
        file.delete();
    }

    private void prepareMediaRecorder() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mediaRecorder.setOutputFile(savePath);
    }

    public String getSavePath() {
        return savePath;
    }

}
