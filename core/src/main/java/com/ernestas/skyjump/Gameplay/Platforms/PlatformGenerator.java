package com.ernestas.skyjump.Gameplay.Platforms;

import com.badlogic.gdx.math.Rectangle;
import com.ernestas.skyjump.Settings.Settings;

public class PlatformGenerator {

    public static final int SIMPLE_PLATFORM = 0;

    private PlatformGenerator() {}

    public static Platform generatePlatform(int id, Rectangle bounds) {
        Platform platform = null;

        switch(id) {
            case SIMPLE_PLATFORM:
                bounds.height = 32;
                platform = new SimplePlatform(SIMPLE_PLATFORM, scaleRect(bounds, Settings.getScale()));
                break;
            default:
                break;
        }

        return platform;
    }

    private static Rectangle scaleRect(Rectangle rec, float scale) {
        return new Rectangle(rec.x, rec.y, rec.width * scale, rec.height * scale);
    }

}
