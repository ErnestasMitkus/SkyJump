package com.ernestas.skyjump.Audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class MusicPlayer {

    public static final String HEAVEN_MUSIC = "Audio/Music/heaven.mp3";
    public static final String HELL_MUSIC = "Audio/Music/hell.mp3";

    private static Music music;
    private static boolean playingHeaven = false;

    private float fadeInDuration = 3f;
    private float timePassedSinceStart = 0f;
    private float maxVolume = 0.5f;

    public MusicPlayer() {

    }

    public void playHeaven() {
        if (!playingHeaven) {
            playMusic(HEAVEN_MUSIC);
            playingHeaven = true;
        }
    }

    public void playHell() {
        if (playingHeaven) {
            playMusic(HELL_MUSIC);
            playingHeaven = false;
        }
    }

    public void update(float delta) {
        if (music != null) {
            timePassedSinceStart += delta;
            float volume = Math.min(timePassedSinceStart / fadeInDuration, maxVolume);
            music.setVolume(volume);
        }
    }

    private int tried = 0;

    private void playMusic(String path) {
        try {
            if (music != null && music.isPlaying()) {
                music.stop();
                music.dispose();
            }
            music = Gdx.audio.newMusic(Gdx.files.internal(path));
            music.setLooping(true);
            music.setVolume(0f);
            music.play();
            timePassedSinceStart = 0f;
            tried = 0;
        } catch (Exception e) {
            e.printStackTrace();
            if (tried < 5) {
                tried++;
                playMusic(path);
            }
        }
    }
}
