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
import com.ernestas.skyjump.Gameplay.Level;
import com.ernestas.skyjump.Gameplay.Player;
import com.ernestas.skyjump.Input.InputProcessor;
import com.ernestas.skyjump.Loaders.LevelProvider;
import com.ernestas.skyjump.MyGame;
import com.ernestas.skyjump.Settings.Settings;

public class PlayScreen implements Screen {

    private MyGame game;
    private InputProcessor input;
    private SpriteBatch batch;

    private OrthographicCamera camera;

    private Level level;

    private boolean debug = false;
    private boolean pause = false;

    public PlayScreen(MyGame game) {
        this.game = game;
        input = game.getInput();
        batch = new SpriteBatch();
        camera = new OrthographicCamera(Settings.getWidth(), Settings.getHeight());
        camera.position.set(600f - Settings.getWidth() / 2f, Settings.getHeight() / 2f, 0);
    }

    @Override
    public void show() {
        level = LevelProvider.generateLevel("Levels/testlevel.json");
        level.init(input);
    }

    private void reset() {
        level.reset();
        // update camera to player
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

        renderWithColor(renderer, level.getPlayer().getBounds(), Color.GREEN);

        renderer.end();
    }

    private void renderWithColor(ShapeRenderer renderer, Rectangle rec, Color color) {
        renderer.setColor(color);
        renderer.rect(rec.x, rec.y, rec.width, rec.height);
    }

    public void update(float delta) {
        input.update(delta);

        float cameraSpeed = 200f;
        float vecX = 0f;
        float vecY = 0f;

        if (input.isPressedAdvanced(Keys.STAR)) {
            debug = !debug;
        }
        if (input.isPressedAdvanced(Keys.ESCAPE)) {
            pause = !pause;
        }
        if (input.isPressedAdvanced(Keys.R)) {
            reset();
        }

        if (input.isPressed(Keys.LEFT)) {
            --vecX;
        }
        if (input.isPressed(Keys.RIGHT)) {
            ++vecX;
        }
        if (input.isPressed(Keys.UP)) {
            ++vecY;
        }
        if (input.isPressed(Keys.DOWN)) {
            --vecY;
        }

        if (vecX != 0 || vecY != 0) {
            camera.translate(cameraSpeed * vecX * delta, cameraSpeed * vecY * delta);
            camera.update();
        }

        level.update(delta);
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
