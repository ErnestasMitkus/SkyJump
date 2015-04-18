package com.ernestas.skyjump.Loaders;

import com.ernestas.skyjump.Resources.GameResources;

public class GlobalLoader {

    private GlobalLoader() {}

    public static void load() {
        // ImageLoader
        ImageLoader imageLoader = new ImageLoader();
        imageLoader.load();
        GameResources.setImageLoader(imageLoader);

        // FontFactory

        // SoundFactory
        SoundLoader soundLoader = new SoundLoader();
        soundLoader.load();
        GameResources.setSoundLoader(soundLoader);

    }

}
