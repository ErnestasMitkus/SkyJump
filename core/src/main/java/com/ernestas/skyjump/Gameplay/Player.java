package com.ernestas.skyjump.Gameplay;

import static com.badlogic.gdx.Input.Keys;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.ernestas.skyjump.Input.InputProcessor;
import com.ernestas.skyjump.Loaders.ImageLoader;
import com.ernestas.skyjump.Resources.GameResources;
import com.ernestas.skyjump.Settings.Settings;
import com.ernestas.skyjump.Sprite.Animation;
import com.ernestas.skyjump.Sprite.ScaledSprite;
import com.ernestas.skyjump.Sprite.Spritesheet;
import com.ernestas.skyjump.Util.Vectors.Vector2f;

public class Player {
    public static int IDLE = 0;
    public static int DUCK = 1;
    public static int DUCK_WALK = 2;
    public static int WALK = 3;
    public static int JUMP = 4;
    public static int FALL = 5;

    private Level level;
    private InputProcessor input;

    private float playerSpeed = 200f * Settings.getScale();
    private float jumpStrength = 300f * Settings.getScale();

    private boolean isJumping = false;
    private boolean isFalling = true;
    private boolean touchedGround = false;

    private Vector2f position;
    private Vector2f mov;
    private boolean facingLeft = false;

    private Animation animations[] = new Animation[FALL+1];
    private int state = IDLE;
    private boolean ducking = false;

    private boolean godMode = false;
    private boolean dead = false;

    public Player(Level level, Vector2f position, InputProcessor input) {
        this.level = level;
        this.position = position;
        this.input = input;
//        sprite = new ScaledSprite(new Sprite(GameResources.getImageLoader().getImage(ImageLoader.PLAYER)));
        initAnimations();
        restart();
    }

    private void initAnimations() {
        float fps = Animation.frameRateToDeltaRate(Settings.getFrameRate());
        Texture t = GameResources.getImageLoader().getImage(ImageLoader.PLAYERSS).getTexture();
        Spritesheet s;

        // IDLE
        s = new Spritesheet(t, 0, 32, 64, 2);
        animations[IDLE] = new Animation(s, (int) position.x, (int) position.y, 30, fps);

        // DUCK
        s = new Spritesheet(t, 64, 32, 64, 1);
        animations[DUCK] = new Animation(s, (int) position.x, (int) position.y, 20, fps);

        // DUCK WALK
        s = new Spritesheet(t, 64, 32, 64, 3);
        animations[DUCK_WALK] = new Animation(s, (int) position.x, (int) position.y, 10, fps);

        // WALK
        s = new Spritesheet(t, 128, 32, 64, 3);
        animations[WALK] = new Animation(s, (int) position.x, (int) position.y, 10, fps);

        // JUMP
        s = new Spritesheet(t, 192, 32, 64, 2);
        animations[JUMP] = new Animation(s, (int) position.x, (int) position.y, 40, fps);

        // FALL
        s = new Spritesheet(t, 256, 32, 64, 2);
        animations[FALL] = new Animation(s, (int) position.x, (int) position.y, 40, fps);
    }

    public void restart() {
        dead = false;
        mov = new Vector2f();
    }

    public void render(SpriteBatch batch) {
        if (dead) {
            return;
        }

        // get correct sprite
        Sprite sprite = animations[state].getSprite();

        sprite.flip(sprite.isFlipX() ^ facingLeft, false);
        sprite.draw(batch);
    }

    public void update(float delta) {
        if (dead) {
            return;
        }
        mov.x = 0f;

        if (input.isPressed(Keys.A)) {
            facingLeft = true;
            --mov.x;
        }
        if (input.isPressed(Keys.D)) {
            facingLeft = false;
            ++mov.x;
        }

        if (input.isPressed(Keys.S)) {
            ducking = true;
        } else {
            if (ducking) {
                // try to stand up
                int currState = state;
                state = IDLE;
                if (!level.checkCollision(getBounds())) {
                    ducking = false;
                }
                state = currState;
            }
        }
        if (input.isPressedAdvanced(Keys.O)) {
            godMode = !godMode;
            if (godMode) {
                playerSpeed = 3 * playerSpeed;
            } else {
                playerSpeed = playerSpeed / 3;
            }
        }

        float inSecretCoef = level.inSecret() ? 1.5f : 1f;

//        if (input.isPressedWithDelay(Keys.SPACE, 0.1f)) {
        if (input.isPressed(Keys.SPACE)) {
            if (touchedGround || godMode) {
//                input.consume(Keys.SPACE);
                input.isPressedAdvanced(Keys.SPACE);
                isJumping = true;
                touchedGround = false;
                mov.y += jumpStrength * Math.max(1f, (inSecretCoef * 0.8f));
            }
        }

        mov.y += Level.GRAVITY_VALUE;

        float duckingCoef = (state == DUCK_WALK && !isJumping && !isFalling) ? 0.5f : 1f;

        move(mov.x * playerSpeed * delta * duckingCoef * inSecretCoef, mov.y * delta);

        animations[state].update(delta);
        int oldState = state;
        // set state
        if (ducking) {
            state = DUCK;
        } else if (isFalling) {
            state = FALL;
        } else if (isJumping) {
            state = JUMP;
        } else {
            state = IDLE;
        }

        if (mov.x != 0) {
            if (state == DUCK) {
                state = DUCK_WALK;
            } else if (state == IDLE) {
                state = WALK;
            }
        }

        if (oldState != state) {
            changeAnimationState();
        }
        animations[state].getSprite().setPosition(position.x, position.y);
    }

    private void changeAnimationState() {
        animations[state].restart();
    }

    public Rectangle getBounds() {
        Rectangle rec = animations[state].getSprite().getBoundingRectangle();

        rec.x = getPosition().x + 5;
        rec.y = getPosition().y;
        rec.width = 25 * Settings.getScale();
        rec.height = 58 * Settings.getScale();

        if (state == DUCK || state == DUCK_WALK) {
            rec.height = 36 * Settings.getScale();
        }

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
            position.x += vecX;
            position.y += vecY;
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
                mov.y = 0;
                touchedGround = true;
                position.y -= level.getClosestGround(getBounds());
            }
        }
    }

    public void setPosition(float x, float y) {
        position = new Vector2f(x, y);
    }

    public Vector2f getPosition() {
        return position;
    }

    public void die() {
        dead = true;
    }
    public boolean isDead() { return dead; }
}
