package com.ernestas.skyjump.Gameplay.Platforms;

import com.badlogic.gdx.math.Rectangle;
import com.ernestas.skyjump.Settings.Settings;

public class PlatformGenerator {

    public static final int GROUND_PLATFORM = 0;
    public static final int SIMPLE_PLATFORM = 1;
    public static final int WALL = 2;
    public static final int EXTENDING_PLATFORM = 3;
    public static final int CORRUPT_PLATFORM = 4;

    private PlatformGenerator() {}

    public static Platform generatePlatform(int id, Rectangle bounds, boolean solid, boolean visible) {
        Platform platform = null;
        float scale = Settings.getScale();

        switch(id) {
            case GROUND_PLATFORM:
            case EXTENDING_PLATFORM:
                bounds.height = 43 * scale;
                break;
            case SIMPLE_PLATFORM:
                bounds.height = 13 * scale;
                break;
            case WALL:
                bounds.height = bounds.width;
                bounds.width = 21 * scale;
                break;
            case CORRUPT_PLATFORM:
                bounds.height /= 2;
                break;
            default:
                break;
        }

        platform = new SimplePlatform(id, scaleRect(bounds, Settings.getScale()));
        platform.setSolid(solid);
        platform.setVisible(visible);

        return platform;
    }

    private static Rectangle scaleRect(Rectangle rec, float scale) {
        return new Rectangle(rec.x * scale, rec.y * scale, rec.width * scale, rec.height * scale);
    }

}
