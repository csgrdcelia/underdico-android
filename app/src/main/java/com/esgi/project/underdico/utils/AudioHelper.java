package com.esgi.project.underdico.utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AudioHelper {

    Context context;
    String savePath;
    MediaRecorder mediaRecorder;
    MediaPlayer mediaPlayer;

    boolean isRecording;

    public AudioHelper(Context context) {
        this.context = context;
        savePath = FileUtils.createRandomCacheFile(context, ".mp3");
    }

    public void record() throws IOException {
        if(mediaRecorder == null) {
            prepareMediaRecorder();
        }
        mediaRecorder.prepare();
        mediaRecorder.start();
        isRecording = true;
    }

    public void stop() {
        if(isRecording) {
            mediaRecorder.stop();
            isRecording = false;
        }
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
        if (isRecording)
            stop();

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

    public static void play(String fileName) throws IOException{
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource(fileName);
        mediaPlayer.prepare();
        mediaPlayer.start();
    }

}
