package com.ernestas.skyjump.Gameplay;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.ernestas.skyjump.Gameplay.Platforms.Platform;
import com.ernestas.skyjump.Input.InputProcessor;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

public class Level {
    private static final float REALITY_COEF = 1.5f;
    public static final float GRAVITY_VALUE = -9.80665f * REALITY_COEF; // m/s^2

    public static class Builder {
        private Level level;

        public Builder() {
            level = new Level();
        }

        public Builder addPlatform(Platform platform) {
            level.addPlatform(platform);
            return this;
        }

        public Level build() {
            return level;
        }
    }

    // methods for the builder
    private void addPlatform(Platform platform) {
        platformList.add(platform);
    }

    // fields
    private List<Platform> platformList;

    private Player player;

    private Level() {
        platformList = new ArrayList<>();
    }

    public void init(InputProcessor input) {
        player = new Player(this, input);
        reset();
    }

    public void reset() {
        player.setPosition(250, 150);
    }

    public void update(float delta) {
        player.update(delta);
        platformList.forEach((platform) -> platform.update(delta));
    }

    public void render(SpriteBatch batch) {
        player.render(batch);
        platformList.forEach((platform) -> platform.render(batch));
    }

    public List<Platform> getPlatforms() {
        return platformList;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean checkCollision(Rectangle rec) {
        for (Platform platform: platformList) {
            if (platform.getBounds().overlaps(rec)) {
                return true;
            }
        }

        return false;
    }

}
