package com.ernestas.skyjump.Sprite;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class ScaledSprite extends Sprite {

    public ScaledSprite(Sprite sprite) {
        super(sprite);
    }

    @Override
    public float getWidth() {
        return super.getWidth() * super.getScaleX();
    }

    @Override
    public float getHeight() {
        return super.getHeight() * super.getScaleY();
    }

}
