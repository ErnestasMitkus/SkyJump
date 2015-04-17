package com.ernestas.skyjump.Resources;

import com.ernestas.skyjump.Loaders.ImageLoader;

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

    public static void setImageLoader(ImageLoader imageLoader) { getInstance().imageLoader = imageLoader; }
    public static ImageLoader getImageLoader() { return getInstance().imageLoader; }

}
