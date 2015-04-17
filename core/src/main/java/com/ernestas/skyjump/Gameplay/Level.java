package com.ernestas.skyjump.Gameplay;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ernestas.skyjump.Gameplay.Platforms.Platform;

import java.util.ArrayList;
import java.util.List;

public class Level {

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

    private Level() {
        platformList = new ArrayList<>();
    }

    public void update(float delta) {
        platformList.forEach((platform) -> platform.update(delta));
    }

    public void render(SpriteBatch batch) {
        platformList.forEach((platform) -> platform.render(batch));
    }

}
