package com.ernestas.skyjump.Sprite;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.ernestas.skyjump.Util.CustomImages.CustomImage;

public class Animation {

    private Spritesheet spritesheet;
    private int frequency;
    private float deltaBucket;
    private float framesPerSecond;

    private int currentAnimation;
    private boolean iterationDone;

    public Animation(Spritesheet spritesheet, int posX, int posY, int frequency, float framesPerSecond) {
        this.spritesheet = spritesheet;
        setPosition(posX, posY);
        this.frequency = frequency;
        this.framesPerSecond = framesPerSecond;
        restart();
    }

    public void restart() {
        deltaBucket = 0;
        currentAnimation = 0;
        iterationDone = false;
    }

    public void update(float delta) {
        deltaBucket += delta;
        while (deltaBucket >= frequency * framesPerSecond) {
            deltaBucket -= frequency * framesPerSecond;
            currentAnimation++;
            if (currentAnimation == spritesheet.getAmount()) {
                currentAnimation = 0;
                iterationDone = true;
            }
        }
    }

    public Sprite getSprite() {
        return spritesheet.getSprite(currentAnimation);
    }

    public void setPosition(int posX, int posY) {
        spritesheet.setPosition(posX, posY);
    }

    public boolean iterationDone() {
        return iterationDone;
    }

    public float percentageDone() {
        return (currentAnimation * 1f) / spritesheet.getAmount();
    }

    public static float frameRateToDeltaRate(int framesPerSecond) {
        return 1f / framesPerSecond;
    }

}
