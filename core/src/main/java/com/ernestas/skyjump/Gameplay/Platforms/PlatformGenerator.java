package com.ernestas.skyjump.Gameplay.Platforms;

import com.badlogic.gdx.math.Rectangle;
import com.ernestas.skyjump.Settings.Settings;

public class PlatformGenerator {

    public static final int GROUND_PLATFORM = 0;
    public static final int SIMPLE_PLATFORM = 1;
    public static final int WALL = 2;

    private PlatformGenerator() {}

    public static Platform generatePlatform(int id, Rectangle bounds) {
        Platform platform = null;

        switch(id) {
            case GROUND_PLATFORM:
                bounds.height = 32;
                platform = new SimplePlatform(GROUND_PLATFORM, scaleRect(bounds, Settings.getScale()));
                break;
            case SIMPLE_PLATFORM:
                bounds.height = 20;
                platform = new SimplePlatform(SIMPLE_PLATFORM, scaleRect(bounds, Settings.getScale()));
                break;
            case WALL:
                bounds.height = bounds.width;
                bounds.width = 32;
                platform = new SimplePlatform(WALL, scaleRect(bounds, Settings.getScale()));
                break;
            default:
                break;
        }

        return platform;
    }

    private static Rectangle scaleRect(Rectangle rec, float scale) {
        return new Rectangle(rec.x * scale, rec.y * scale, rec.width * scale, rec.height * scale);
    }

}
