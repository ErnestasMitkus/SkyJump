package com.ernestas.skyjump.Gameplay;

import static com.badlogic.gdx.Input.Keys;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.ernestas.skyjump.Input.InputProcessor;
import com.ernestas.skyjump.Loaders.ImageLoader;
import com.ernestas.skyjump.Resources.GameResources;
import com.ernestas.skyjump.Settings.Settings;
import com.ernestas.skyjump.Sprite.ScaledSprite;
import com.ernestas.skyjump.Util.Vectors.Vector2f;

public class Player {

    private ScaledSprite sprite;
    private Level level;
    private InputProcessor input;

    private float playerSpeed = 200f;
    private float jumpStrength = 450f;

    private boolean isJumping = false;
    private boolean isFalling = false;

    private Vector2f mov = new Vector2f();
    private boolean facingLeft = true;

    public Player(Level level, InputProcessor input) {
        this.level = level;
        this.input = input;
        sprite = new ScaledSprite(new Sprite(GameResources.getImageLoader().getImage(ImageLoader.PLAYER)));
    }

    public void render(SpriteBatch batch) {
        sprite.flip(sprite.isFlipX() ^ facingLeft, false);
        sprite.draw(batch);
    }

    public void update(float delta) {
        mov.x = 0f;

        if (input.isPressed(Keys.A)) {
            facingLeft = true;
            --mov.x;
        }
        if (input.isPressed(Keys.D)) {
            facingLeft = false;
            ++mov.x;
        }

        if (input.isPressedWithDelay(Keys.SPACE, 0.5f)) {
            if (!isJumping && !isFalling) {
                input.isPressedAdvanced(Keys.SPACE);
                isJumping = true;
                mov.y += jumpStrength;
            }
        }

        mov.y += Level.GRAVITY_VALUE;
        move(mov.x * playerSpeed * delta, mov.y * delta);

        if (mov.y < 0) {
            isFalling = true;
        }
    }

    public Rectangle getBounds() {
        Rectangle rec = sprite.getBoundingRectangle();

        rec.x += 5;
        rec.width = 25 * Settings.getScale();
        rec.height = 58 * Settings.getScale();

        return rec;
    }

    private void move(float vecX, float vecY) {
        // Diagonal movement
        if (vecX != 0 && vecY != 0) {
            move(vecX, 0);
            move(0, vecY);
            return;
        }

        // No movement
        if (vecX + vecY == 0) {
            return;
        }

        Rectangle rec = getBounds();
        rec.x += vecX;
        rec.y += vecY;

        boolean collides = level.checkCollision(rec);
        if (!collides) {
            sprite.translate(vecX, vecY);
            if (vecY > 0) {
                if (!isJumping) {
                    isJumping = true;
                    isFalling = false;
                }
            } else if (vecY < 0) {
                if (!isFalling) {
                    isFalling = true;
                    isJumping = false;
                }
            }
        } else {
            if (vecY > 0) {
                // hit something above
                if (isJumping && !isFalling) {
                    isJumping = false;
                    isFalling = true;
                    mov.y = 0f;
                }
            } else if (vecY < 0) {
                // hit something below
                isJumping = false;
                isFalling = false;
                mov.y = 0f;
            }
        }
    }

    public void setPosition(float x, float y) {
        sprite.setPosition(x, y);
    }
}
