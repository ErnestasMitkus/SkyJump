package com.ernestas.skyjump.Settings;

public class Settings {
    public static final int DEFAULT_WIDTH = 600;
    public static final int DEFAULT_HEIGHT = 300;
    public static final int DEFAULT_FRAME_RATE = 60;

    /********************************************************/
    private static Settings instance = null;

    private Settings() {}

    public static Settings getInstance() {
        if (instance == null) {
            instance = new Settings();
        }
        return instance;
    }

    public static void reset() {
        instance = null;
    }
    /********************************************************/

    private int width = DEFAULT_WIDTH;
    private int height = DEFAULT_HEIGHT;
    private int frameRate = DEFAULT_FRAME_RATE;
    private float scale = 1f;

    public static void setFrameRate(int frameRate) { getInstance().frameRate = frameRate; }

    public static int getWidth() { return getInstance().width; }
    public static int getHeight() { return getInstance().height; }
    public static int getFrameRate() { return getInstance().frameRate; }

    public static void setScale(float scale) {
        getInstance().width = (int) Math.ceil(DEFAULT_WIDTH * scale);
        getInstance().height = (int) Math.ceil(DEFAULT_HEIGHT * scale);
        getInstance().scale = scale;
    }
    public static float getScale() { return getInstance().scale; }

}
