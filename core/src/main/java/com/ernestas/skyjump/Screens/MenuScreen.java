package com.ernestas.skyjump.Screens;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ernestas.skyjump.Input.InputProcessor;
import com.ernestas.skyjump.Loaders.ImageLoader;
import com.ernestas.skyjump.Loaders.SoundLoader;
import com.ernestas.skyjump.MyGame;
import com.ernestas.skyjump.Resources.GameResources;
import com.ernestas.skyjump.Sprite.ScaledSprite;

public class MenuScreen implements Screen {

    private MyGame game;
    private ScaledSprite bg;

    private SpriteBatch batch;

    public MenuScreen(MyGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        bg = GameResources.getImageLoader().getImage(ImageLoader.MENU_BACKGROUND);
        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        update(delta);

        batch.begin();
        bg.draw(batch);
        batch.end();
    }

    public void update(float delta) {
        if (game.getInput().isPressedAdvanced(Input.Keys.ENTER)) {
           game.setScreen(new PlayScreen(game));
        }
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
