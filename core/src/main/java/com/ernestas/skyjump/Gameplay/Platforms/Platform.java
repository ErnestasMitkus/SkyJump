package com.ernestas.skyjump.Gameplay.Platforms;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.ernestas.skyjump.Settings.Settings;
import com.ernestas.skyjump.Sprite.ScaledSprite;

public abstract class Platform {

    protected ScaledSprite sprite;
    protected Rectangle bounds;

    public Platform(ScaledSprite sprite, Rectangle bounds) {
        this.sprite = sprite;
        this.bounds = bounds;

        sprite.setPosition(bounds.x, bounds.y);
        sprite.setScale((bounds.width * Settings.getScale() / sprite.getWidth()), (bounds.height * Settings.getScale()) / sprite.getHeight());
    }

    public ScaledSprite getSprite() { return sprite; }
    public Rectangle getBounds() { return bounds; }

    public void update(float delta) {}

    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }
}
