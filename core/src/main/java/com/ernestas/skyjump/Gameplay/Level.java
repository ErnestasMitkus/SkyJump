package com.ernestas.skyjump.Gameplay;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.ernestas.skyjump.Background.Background;
import com.ernestas.skyjump.Fonts.FontFactory;
import com.ernestas.skyjump.Gameplay.Events.Event;
import com.ernestas.skyjump.Gameplay.Platforms.Platform;
import com.ernestas.skyjump.Gameplay.Platforms.SimplePlatform;
import com.ernestas.skyjump.Input.InputProcessor;
import com.ernestas.skyjump.Loaders.ImageLoader;
import com.ernestas.skyjump.Resources.GameResources;
import com.ernestas.skyjump.Settings.Settings;
import com.ernestas.skyjump.Sprite.Frame;
import com.ernestas.skyjump.Sprite.ScaledSprite;
import com.ernestas.skyjump.Util.Vectors.Vector2f;
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

        public Builder addEvent(Event event) {
            level.addEvent(event);
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
    private void addEvent(Event event) {
        eventList.add(event);
    }

    private Background background;
    private ScaledSprite redBackground;

    private ScaledSprite arrowSprite;

    private Frame frame;

    // fields
    private List<Platform> platformList;
    private List<Event> eventList;

    private Player player; //-830
    private Vector2f initialPosition = new Vector2f(150 * Settings.getScale(), 150 * Settings.getScale());
    private boolean inSecret = false;

    private boolean gameWon = false;

    private Level() {
        platformList = new ArrayList<>();
        eventList = new ArrayList<>();
    }

    public void init(OrthographicCamera camera, InputProcessor input) {
        background = new Background(new Rectangle(0, -100, 4000, 2100));
        redBackground = new ScaledSprite(GameResources.getImageLoader().getImage(ImageLoader.BACKGROUND_RED));
        redBackground.setPosition(-10000, 0);
        redBackground.setScale(5f, redBackground.getScaleY());

        arrowSprite = new ScaledSprite(GameResources.getImageLoader().getImage(ImageLoader.ARROW));
        arrowSprite.setPosition(133 * Settings.getScale(), 100 * Settings.getScale());

        player = new Player(this, initialPosition, input);
        eventList.forEach(event -> event.init(this));

        Vector2f size = new Vector2f(Frame.getDefaultSprite().getWidth(), Frame.getDefaultSprite().getHeight());
        frame = new Frame(camera, "", FontFactory.generateFont(FontFactory.FontName.openSans, 16, false, false),
                        new Vector2f(Settings.getWidth() - size.x, Settings.getHeight() - size.y));

        frame.setBottomText("Press Enter to return to menu");

        reset();
    }

    public void reset() {
        inSecret = false;
        gameWon = false;
        player.restart();
        platformList.forEach(platform -> platform.restart());
        eventList.forEach(event -> event.restart());
        player.setPosition(initialPosition.x, initialPosition.y);
    }

    public void update(float delta) {
        if (player.isDead() || gameWon) {
            return;
        }

        player.update(delta);
        if (player.getPosition().y + player.getBounds().height < 0) {
            playerFell();
            return;
        }
        if (player.getPosition().x < 0) {
            inSecret = true;
        } else if (player.getPosition().x - 100f > Settings.getWidth()) {
            inSecret = false;
        }
        platformList.forEach((platform) -> platform.update(delta));
        eventList.forEach((event) -> {
            event.update(delta);
            if (event.getBounds().overlaps(player.getBounds())) {
                event.event();
            }
        });
    }

    public void render(SpriteBatch batch) {
        redBackground.draw(batch);
        background.render(batch);
        arrowSprite.draw(batch);
        platformList.forEach((platform) -> platform.render(batch));
        eventList.forEach((event) -> event.render(batch));
        player.render(batch);

        if (player.isDead() || gameWon) {
            frame.render(batch);
        }
    }

    public List<Platform> getPlatforms() {
        return platformList;
    }
    public List<Event> getEvents() {
        return eventList;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean checkCollision(Rectangle rec) {
        for (Platform platform: platformList) {
            if (platform.isSolid() && platform.getBounds().overlaps(rec)) {
                platform.setVisible(true);
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
            if (!platform.isSolid()) {
                continue;
            }
            if (platform.getBounds().overlaps(bar)) {
                for (int i = 0; i < checkRange; ++i) {
                    if (platform.getBounds().contains(x, y - i)) {
                        min = Math.min(min, y - (platform.getBounds().getY() + platform.getBounds().height));
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

    public void playerFell() {
        player.die();
        frame.setText("YOU FELL.");
        // if in heaven or hell
        if (player.getPosition().x > 0) {
            //heaven fell
            frame.addText("Better luck next time :)");
        } else {
            //hell fell
            frame.addText("Next time try following the rules??");

        }
    }

    public void playerDied() {
        frame.setText("YOU DIED");
        frame.addText("Maybe you will not try to follow the rules?");
        player.die();
    }

    public void simpleVictory() {
        frame.setText("YOU WON");
        frame.addText("Congratulations :)");
        gameWon = true;
    }

}
