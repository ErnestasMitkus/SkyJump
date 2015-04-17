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

    private float playerSpeed = 200f * Settings.getScale();
    private float jumpStrength = 300f * Settings.getScale();

    private boolean isJumping = false;
    private boolean isFalling = true;
    private boolean touchedGround = false;

    private Vector2f mov;
    private boolean facingLeft = false;

    public Player(Level level, InputProcessor input) {
        this.level = level;
        this.input = input;
        sprite = new ScaledSprite(new Sprite(GameResources.getImageLoader().getImage(ImageLoader.PLAYER)));
        restart();
    }

    public void restart() {
        mov = new Vector2f();
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

//        if (input.isPressedWithDelay(Keys.SPACE, 0.1f)) {
        if (input.isPressed(Keys.SPACE)) {
            if (touchedGround) {
//                input.consume(Keys.SPACE);
                isJumping = true;
                touchedGround = false;
                mov.y += jumpStrength;
            }
        }

        mov.y += Level.GRAVITY_VALUE;

        move(mov.x * playerSpeed * delta, mov.y * delta);
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
                    isJumping = false;
                    isFalling = true;
                }
            }
        } else {
            if (vecY > 0) {
                // hit something above
                isJumping = false;
                isFalling = true;
                mov.y = 0f;
            } else if (vecY < 0) {
                // hit something below
                isJumping = false;
                isFalling = false;
                touchedGround = true;
                mov.y = 0;
                sprite.translate(0, -level.getClosestGround(getBounds()));
            }
        }
    }

    public void setPosition(float x, float y) {
        sprite.setPosition(x, y);
    }
}
