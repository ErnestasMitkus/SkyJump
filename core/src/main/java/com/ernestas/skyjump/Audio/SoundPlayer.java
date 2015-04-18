package com.ernestas.skyjump.Audio;

import com.badlogic.gdx.audio.Sound;
import com.ernestas.skyjump.Loaders.SoundLoader;
import com.ernestas.skyjump.Resources.GameResources;

public class SoundPlayer {

    private SoundPlayer() {
    }

    public static void playSound(String path) {
        Sound sound = GameResources.getSoundLoader().getSound(path);
        long id = sound.play();
        sound.setLooping(id, false);
        sound.setVolume(id, 1f);

        if (path == SoundLoader.JUMP) {
            sound.setPitch(id, 0.5f);
        }
    }

}
