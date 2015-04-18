package com.ernestas.skyjump.Resources;

import com.ernestas.skyjump.Loaders.ImageLoader;
import com.ernestas.skyjump.Loaders.SoundLoader;

public class GameResources {
    /********************************************************/
    private static GameResources instance = null;

    private GameResources() {}

    public static GameResources getInstance() {
        if (instance == null) {
            instance = new GameResources();
        }
        return instance;
    }

    public static void reset() {
        instance = null;
    }
    /********************************************************/

    private ImageLoader imageLoader;
    private SoundLoader soundLoader;

    public static void setImageLoader(ImageLoader imageLoader) { getInstance().imageLoader = imageLoader; }
    public static ImageLoader getImageLoader() { return getInstance().imageLoader; }

    public static void setSoundLoader(SoundLoader soundLoader) { getInstance().soundLoader = soundLoader; }
    public static SoundLoader getSoundLoader() { return getInstance().soundLoader; }
}
