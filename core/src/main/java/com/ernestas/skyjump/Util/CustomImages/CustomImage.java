package com.ernestas.skyjump.Util.CustomImages;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class CustomImage {

    private CustomImage() {
    }

    public static Sprite getEmptySprite() {
        return new Sprite(new Texture(new Pixmap(0, 0, Pixmap.Format.RGBA8888)));
    }

    public static Sprite getColorSprite(Color color) {
        return getColorSprite(color, 1, 1);
    }

    public static Sprite getColorSprite(Color color, int width, int height) {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.drawPixel(0, 0);

        Sprite sprite = new Sprite(new Texture(pixmap));
        sprite.setOrigin(0, 0);
        sprite.setScale(width, height);
        return sprite;
    }

}
