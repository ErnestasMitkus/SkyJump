package com.ernestas.skyjump.Loaders;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class SpriteScaler {

    private SpriteScaler(){}

    public static Sprite scaleSprite(Sprite sprite, float scale) {
        Sprite result = new Sprite(sprite);

        result.setOrigin(0, 0);
        result.setScale(scale);

        return result;
    }

    public static Sprite lowerSpriteZAxis(Sprite sprite, float percentage) {
        Sprite result = new Sprite(sprite);

        result.setOrigin(0, 0);
        result.setScale(sprite.getScaleX() * percentage / 100, sprite.getScaleY() * percentage / 100);

        return result;
    }
}
