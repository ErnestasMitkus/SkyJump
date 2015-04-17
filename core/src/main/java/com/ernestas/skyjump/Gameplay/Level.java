package com.ernestas.skyjump.Gameplay;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.ernestas.skyjump.Background.Background;
import com.ernestas.skyjump.Gameplay.Platforms.Platform;
import com.ernestas.skyjump.Gameplay.Platforms.SimplePlatform;
import com.ernestas.skyjump.Input.InputProcessor;
import com.ernestas.skyjump.Loaders.ImageLoader;
import com.ernestas.skyjump.Resources.GameResources;
import com.ernestas.skyjump.Settings.Settings;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

public class Level {
    private static final float REALITY_COEF = 1.2f;
    public static final float GRAVITY_VALUE = -9.80665f * REALITY_COEF * Settings.getScale(); // m/s^2

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

    Background background;

    // fields
    private List<Platform> platformList;

    private Player player;
    private boolean inSecret = false;

    private Level() {
        platformList = new ArrayList<>();
    }

    public void init(InputProcessor input) {
        background = new Background(new Rectangle(-2000, -100, 4000, 2100));
        player = new Player(this, input);
        reset();
    }

    public void reset() {
        player.restart();
        player.setPosition(300 * Settings.getScale(), 150 * Settings.getScale());
    }

    public void update(float delta) {
        player.update(delta);
        platformList.forEach((platform) -> platform.update(delta));
    }

    public void render(SpriteBatch batch) {
        background.render(batch);
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

    public float getClosestGround(Rectangle bounds) {
        float a = getClosestGround(bounds.x, bounds.y);
        float b = getClosestGround(bounds.x + bounds.width, bounds.y);

        return Math.min(a, b);
    }

    public float getClosestGround(float x, float y) {
        float checkRange = 200f;
        float min = checkRange + 1;
        boolean found = false;

        // create vertical 1px bar
        Rectangle bar = new Rectangle(x, y - checkRange, 1, checkRange);

        for (Platform platform : platformList) {
            if (platform.getBounds().overlaps(bar)) {
                for (int i = 0; i < checkRange; ++i) {
                    if (platform.getBounds().contains(x, y - i)) {
                        min = Math.min(min, i - 1);
                        found = true;
                        break;
                    }
                }
            }
        }

        if (!found) {
            return y + 100;
        }
        return min;
    }

    public boolean inSecret() {
        return inSecret;
    }

}
