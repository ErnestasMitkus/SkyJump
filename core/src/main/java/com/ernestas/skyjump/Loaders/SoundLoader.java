package com.ernestas.skyjump.Loaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SoundLoader {

    public static final String AUDIO_PATH = "Audio/";
    public static final String SOUND_PATH = AUDIO_PATH + "Sounds/";

    public static final String JUMP = SOUND_PATH + "jump.mp3";
    public static final String BOO = SOUND_PATH + "boo.mp3";



    private List<String> soundPaths;
    private HashMap<String, Sound> sounds;

    private boolean loaded = false;

    public void load() {
        fillList();
        populateMap();
    }

    private void fillList() {
        soundPaths = new ArrayList<>();

        soundPaths.add(JUMP);
        soundPaths.add(BOO);
    }

    private void populateMap() {
        sounds = new HashMap<>();

        for (String path: soundPaths) {
            sounds.put(path, loadSound(path));
        }

        loaded = true;
    }

    private Sound loadSound(String path) {
        return Gdx.audio.newSound(Gdx.files.internal(path));
    }

    public Sound getSound(String path) {
        Sound sound = sounds.get(path);

        if (sound == null) {
            System.err.println("Sound not found in sound map. Trying to load from path:\"" + path + "\"");
            sound = loadSound(path);
            if (sound != null) {
                sounds.put(path, sound);
            }
        }

        return sound;
    }


}
