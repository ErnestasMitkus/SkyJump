package com.ernestas.skyjump.Gameplay.Platforms;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.ernestas.skyjump.Loaders.ImageLoader;
import com.ernestas.skyjump.Resources.GameResources;
import com.ernestas.skyjump.Sprite.ScaledSprite;
import com.ernestas.skyjump.Util.Vectors.Vector2f;

public class SimplePlatform extends Platform {

    private int id;

    public SimplePlatform(int id, Rectangle bounds) {
        super(new ScaledSprite(getDefaultSprite()), bounds);

        this.id = id;
    }

    public SimplePlatform(int id, Vector2f position) {
        this(id, new Rectangle(position.x, position.y, 128, 32));
    }

    private static Sprite getDefaultSprite() {
        return GameResources.getImageLoader().getImage(ImageLoader.SIMPLE_PLATFORM);
    }
}
