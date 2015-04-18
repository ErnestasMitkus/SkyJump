package com.ernestas.skyjump.Gameplay.Platforms;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.ernestas.skyjump.Loaders.ImageLoader;
import com.ernestas.skyjump.Resources.GameResources;
import com.ernestas.skyjump.Settings.Settings;
import com.ernestas.skyjump.Sprite.ScaledSprite;
import com.ernestas.skyjump.Util.CustomImages.CustomImage;

public class SimplePlatform extends Platform {

    private int id;

    public SimplePlatform(int id, Rectangle bounds) {
        super(new ScaledSprite(getDefaultSprite(id)), bounds);
        this.id = id;
        fixPlatform();
    }

    private static Sprite getDefaultSprite(int id) {
        switch(id) {
            case PlatformGenerator.GROUND_PLATFORM:
            case PlatformGenerator.EXTENDING_PLATFORM:
                return GameResources.getImageLoader().getImage(ImageLoader.GROUND_PLATFORM);
            case PlatformGenerator.SIMPLE_PLATFORM:
                return GameResources.getImageLoader().getImage(ImageLoader.SIMPLE_PLATFORM);
            case PlatformGenerator.WALL:
                return GameResources.getImageLoader().getImage(ImageLoader.WALL);
            case PlatformGenerator.CORRUPT_PLATFORM:
                return GameResources.getImageLoader().getImage(ImageLoader.CORRUPT);
            default:
                return null;
        }
    }

    private void fixPlatform() {
        float scale = Settings.getScale();

        switch(id) {
            case PlatformGenerator.GROUND_PLATFORM:
                getBounds().height -= 14 * scale;
                getBounds().x += 30f * scale;
                break;
            case PlatformGenerator.EXTENDING_PLATFORM:
                getBounds().height -= 14 * scale;
                getBounds().x *= 0.92f;
                getBounds().width *= 0.92f;
                break;
            case PlatformGenerator.SIMPLE_PLATFORM:
                getBounds().height -= 4 * scale;
                break;
            case PlatformGenerator.CORRUPT_PLATFORM:
                getBounds().height -= 6 * scale;
            default:
                break;
        }
    }


}
