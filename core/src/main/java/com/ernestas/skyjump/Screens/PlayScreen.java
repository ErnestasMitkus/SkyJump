package com.ernestas.skyjump.Screens;

import com.badlogic.gdx.Gdx;
import static com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.ernestas.skyjump.Audio.MusicPlayer;
import com.ernestas.skyjump.Gameplay.Level;
import com.ernestas.skyjump.Gameplay.Player;
import com.ernestas.skyjump.Input.InputProcessor;
import com.ernestas.skyjump.Loaders.LevelProvider;
import com.ernestas.skyjump.MyGame;
import com.ernestas.skyjump.Settings.Settings;
import com.ernestas.skyjump.Util.Vectors.Vector2f;

public class PlayScreen implements Screen {

    private MyGame game;
    private InputProcessor input;
    private SpriteBatch batch;

    private OrthographicCamera camera;
    private float cameraMaximumSpeed = 60f;
    private float cameraSpeed = 0f;

    private Level level;

    private boolean debug = false;
    private boolean pause = false;

    private MusicPlayer mp;

    public PlayScreen(MyGame game) {
        this.game = game;
        input = game.getInput();
        batch = new SpriteBatch();
        camera = new OrthographicCamera(Settings.getWidth(), Settings.getHeight());
        mp = new MusicPlayer();
    }

    @Override
    public void show() {
        level = LevelProvider.generateLevel(Gdx.files.internal("Levels/testlevel.json").readString());
        level.init(camera, input, mp);
        reset();
    }

    private void reset() {
        level.reset();

        Vector2f vec = level.getPlayer().getPosition();

        camera.position.set(vec.x, vec.y, 0);
        camera.update();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        level.render(batch);
        batch.end();

        if (debug) {
            debug();
        }
    }

    public void debug() {
        ShapeRenderer renderer = new ShapeRenderer();
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setProjectionMatrix(camera.combined);

        level.getPlatforms().forEach((platform) -> renderWithColor(renderer, platform.getBounds(), Color.BLUE));
        level.getEvents().forEach((event) -> renderWithColor(renderer, event.getBounds(), Color.BLUE));

        renderWithColor(renderer, level.getPlayer().getBounds(), Color.GREEN);

        renderer.end();
    }

    private void renderWithColor(ShapeRenderer renderer, Rectangle rec, Color color) {
        renderer.setColor(color);
        renderer.rect(rec.x, rec.y, rec.width, rec.height);
    }

    public void update(float delta) {
        mp.update(delta);
        input.update(delta);

//        if (input.isPressedAdvanced(Keys.STAR)) {
//            debug = !debug;
//        }
//        if (input.isPressedAdvanced(Keys.ESCAPE)) {
//            pause = !pause;
//        }
//        if (input.isPressedAdvanced(Keys.BACKSPACE)) {
//            System.out.println("player's position: " + level.getPlayer().getPosition());
//        }
//        if (input.isPressedAdvanced(Keys.R)) {
//            reset();
//        }

        updateCamera(delta);

        if (pause) {
            return;
        }

        level.update(delta);

        if (level.goToMenu()) {
            game.setScreen(new MenuScreen(game));
        }
    }

    private void updateCamera(float delta) {
        boolean hardFocusCam = false;
        float dx, dy;
        Vector2f vec = level.getPlayer().getPosition();

        dx = vec.x - camera.position.x;
        if (Math.abs(vec.x - camera.position.x) < 0.5f || hardFocusCam) {
            dx = vec.x - camera.position.x;
        } else {
            dx *= cameraMaximumSpeed * Math.pow(delta, 1.8);
        }

        dy = vec.y - camera.position.y;
        if (Math.abs(vec.y - camera.position.y) < 0.5f || hardFocusCam) {
            dy = vec.y - camera.position.y;
        } else {
            dy *= cameraMaximumSpeed * Math.pow(delta, 1.8);
        }

        if (camera.position.y + dy < camera.viewportHeight / 2) {
            dy = camera.viewportHeight / 2 - camera.position.y;
        }
        if (!level.inSecret()) {
            float simpleEndOffset = 2382 * Settings.getScale();
            // simple game
            if (camera.position.x + dx < camera.viewportWidth / 2) {
                dx = camera.viewportWidth / 2 - camera.position.x;
            } else if (camera.position.x + dx > simpleEndOffset - camera.viewportWidth / 2) {
                dx = simpleEndOffset - camera.position.x - camera.viewportWidth / 2;
            }

        }

        camera.translate(dx, dy);
        camera.update();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
        pause = true;
    }

    @Override
    public void resume() {
        pause = false;
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {

    }
}
