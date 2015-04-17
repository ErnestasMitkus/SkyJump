package com.ernestas.skyjump.Gameplay.Platforms;

import com.badlogic.gdx.math.Rectangle;

public class PlatformGenerator {

    public static final int SIMPLE_PLATFORM = 0;

    private PlatformGenerator() {}

    public static Platform generatePlatform(int id, Rectangle bounds) {
        Platform platform = null;

        switch(id) {
            case SIMPLE_PLATFORM:
                bounds.height = 32;
                platform = new SimplePlatform(SIMPLE_PLATFORM, bounds);
                break;
            default:
                break;
        }

        return platform;
    }


}
