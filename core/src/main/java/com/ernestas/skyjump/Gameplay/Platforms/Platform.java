package com.ernestas.skyjump.Gameplay.Platforms;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.ernestas.skyjump.Settings.Settings;
import com.ernestas.skyjump.Sprite.ScaledSprite;

public abstract class Platform {

    protected ScaledSprite sprite;
    protected Rectangle bounds;
    protected boolean solid = true;
    protected boolean visible = true;

    private boolean initialVisibility = true;
    private boolean initialSet = false;

    public Platform(ScaledSprite sprite, Rectangle bounds) {
        this.sprite = sprite;
        this.bounds = bounds;

        sprite.setPosition(bounds.x, bounds.y);
        sprite.setScale(((bounds.width * Settings.getScale()) / sprite.getWidth()), (bounds.height * Settings.getScale()) / sprite.getHeight());
    }

    public ScaledSprite getSprite() { return sprite; }
    public Rectangle getBounds() { return bounds; }

    public void restart() {
        this.visible = initialVisibility;
    }

    public void update(float delta) {}
    public void render(SpriteBatch batch) {
        if (isVisible()) {
            sprite.draw(batch);
        }
    }

    public void setSolid(boolean solid) { this.solid = solid; }
    public boolean isSolid() { return solid; }
    public void setVisible(boolean visible) {
        if (!initialSet) {
            this .initialVisibility = visible;
            initialSet = true;
        }
        this.visible = visible;
    }
    public boolean isVisible() { return visible; }
}
