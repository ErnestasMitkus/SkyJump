package com.ernestas.skyjump.Gameplay.Events;

import com.badlogic.gdx.math.Rectangle;
import com.ernestas.skyjump.Audio.SoundPlayer;
import com.ernestas.skyjump.Loaders.SoundLoader;
import com.ernestas.skyjump.Util.Vectors.Vector2f;

public class EventHand extends Event {

    private boolean grow;
    private Rectangle growBounds;

    private float leftToGrow = 0f;
    private float growSpeed = 2000f;
    private float expandGrowBoundsAmount = 70f;

    private Vector2f initialPosition;

    public EventHand(int id, Vector2f position) {
        super(getDefaultSprite(id), position);
        initialPosition = position;
        restart();
    }

    @Override
    public void update(float delta) {
        if (level.getPlayer().getBounds().overlaps(growBounds) && !grow) {
            grow = true;
            leftToGrow = sprite.getBoundingRectangle().getHeight();
            SoundPlayer.playSound(SoundLoader.BOO);
        }

        if (grow) {
            float growAmount = Math.min(leftToGrow, delta * growSpeed);
            leftToGrow -= growAmount;

            if (leftToGrow > 0) {
                sprite.setPosition(sprite.getX(), sprite.getY() + growAmount);
            }
        }

        if (level.getPlayer().getBounds().overlaps(getBounds())) {
            event();
        }
    }

    @Override
    public void restart() {
        grow = false;
        sprite.setPosition(sprite.getX(), initialPosition.y - sprite.getHeight());

        growBounds = new Rectangle(sprite.getBoundingRectangle());
        growBounds.x -= expandGrowBoundsAmount / 2;
        growBounds.width += expandGrowBoundsAmount;
        growBounds.height += 2000;
    }

    @Override
    public void event() {
        level.playerDied();
    }

    @Override
    public Rectangle getBounds() {
        Rectangle spriteRec = sprite.getBoundingRectangle();
        Rectangle rec = new Rectangle();

        rec.x = spriteRec.x;
        rec.y = spriteRec.y + spriteRec.height - spriteRec.width;
        rec.width = spriteRec.width;
        rec.height = spriteRec.width;

        return rec;
    }
}
