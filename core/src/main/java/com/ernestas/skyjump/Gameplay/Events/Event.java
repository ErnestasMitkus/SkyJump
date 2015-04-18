package com.ernestas.skyjump.Gameplay.Events;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.ernestas.skyjump.Gameplay.Level;
import com.ernestas.skyjump.Loaders.ImageLoader;
import com.ernestas.skyjump.Resources.GameResources;
import com.ernestas.skyjump.Sprite.ScaledSprite;
import com.ernestas.skyjump.Util.Vectors.Vector2f;

public abstract class Event {
    public static final int EVENT_VICTORY = 0;
    public static final int EVENT_HAND = 1;

    protected ScaledSprite sprite;
    protected Level level;

    public Event(ScaledSprite sprite, Vector2f position) {
        this.sprite = sprite;
        this.sprite.setPosition(position.x, position.y);
    }

    public void init(Level level) { this.level = level; }

    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public static ScaledSprite getDefaultSprite(int id) {
        Sprite sprite = null;

        switch (id) {
            case EVENT_VICTORY:
                return GameResources.getImageLoader().getImage(ImageLoader.FLAG);
            case EVENT_HAND:
                return GameResources.getImageLoader().getImage(ImageLoader.HAND);
            default:
                break;
        }

        return new ScaledSprite(sprite);
    }

    public void update(float delta) {}

    public Rectangle getBounds() {
        return sprite.getBoundingRectangle();
    }
    public abstract void event();

    public void restart() {

    }
}
