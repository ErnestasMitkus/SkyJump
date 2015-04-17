package com.ernestas.skyjump.Screens;

import com.badlogic.gdx.Gdx;
import static com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ernestas.skyjump.Gameplay.Level;
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

    public PlayScreen(MyGame game) {
        this.game = game;
        input = game.getInput();
        batch = new SpriteBatch();
        camera = new OrthographicCamera(Settings.getWidth(), Settings.getHeight());
        camera.position.set(500f - Settings.getWidth() / 2f, Settings.getHeight() / 2f, 0);
    }

    @Override
    public void show() {
        level = LevelProvider.generateLevel("Levels/testlevel.json");
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
    }

    public void update(float delta) {
        float cameraSpeed = 200f;
        float vecX = 0f;
        float vecY = 0f;

        if (input.isPressed(Keys.A)) {
            --vecX;
        }
        if (input.isPressed(Keys.D)) {
            ++vecX;
        }
        if (input.isPressed(Keys.S)) {
            --vecY;
        }
        if (input.isPressed(Keys.W)) {
            ++vecY;
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

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
