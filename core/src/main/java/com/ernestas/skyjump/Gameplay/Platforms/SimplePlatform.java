package com.ernestas.skyjump.Gameplay.Platforms;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.ernestas.skyjump.Loaders.ImageLoader;
import com.ernestas.skyjump.Resources.GameResources;
import com.ernestas.skyjump.Sprite.ScaledSprite;

public class SimplePlatform extends Platform {

    private int id;

    public SimplePlatform(int id, Rectangle bounds) {
        super(new ScaledSprite(getDefaultSprite(id)), bounds);
        this.id = id;
    }

    private static Sprite getDefaultSprite(int id) {
        switch(id) {
            case PlatformGenerator.GROUND_PLATFORM:
                return GameResources.getImageLoader().getImage(ImageLoader.GROUND_PLATFORM);
            case PlatformGenerator.SIMPLE_PLATFORM:
                return GameResources.getImageLoader().getImage(ImageLoader.SIMPLE_PLATFORM);
            case PlatformGenerator.WALL:
                return GameResources.getImageLoader().getImage(ImageLoader.WALL);
            default:
                return null;
        }
    }
}
