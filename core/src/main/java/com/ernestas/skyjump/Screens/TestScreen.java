package com.ernestas.skyjump.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.ernestas.skyjump.Fonts.FontFactory;
import com.ernestas.skyjump.Input.InputProcessor;
import com.ernestas.skyjump.MyGame;
import com.ernestas.skyjump.Sprite.ScaledSprite;
import com.ernestas.skyjump.Util.CustomImages.CustomImage;

import java.util.Random;

public class TestScreen implements Screen {

    private MyGame game;
    private InputProcessor input;

    // Image handling
    private SpriteBatch batch;
    private Sprite sprite;

    private ScaledSprite colorChangingSprite;
    private float changeTime = 1/3f;
    private float changeTimeLeft = 0;

    private BitmapFont font[] = new BitmapFont[3];

    public TestScreen(MyGame game) {
        this.game = game;
        this.input = game.getInput();
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        sprite = new Sprite(new Texture("badlogic.jpg"));
        colorChangingSprite = new ScaledSprite(CustomImage.getEmptySprite());

        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.size = 20;

        font[0] = FontFactory.generateFont(FontFactory.FontName.aller, param, false, false);

        param.size = 40;
        param.borderColor = Color.BLACK;
        param.borderWidth = 3;
        font[1] = FontFactory.generateFont(FontFactory.FontName.openSans, param, true, false);

        param.shadowColor = Color.GRAY;
        param.shadowOffsetX = 5;
        param.shadowOffsetY = 3;
        font[2] = FontFactory.generateFont(FontFactory.FontName.roboto, param, false, true);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        batch.begin();
//		batch.draw(sprite, 0, 0, Settings.getWidth(), Settings.getHeight());

        for (int i = 0; i < font.length; ++i) {
            font[i].draw(batch, "Test String", 100, (i + 1) * 50);
        }

        if (input.mouseButtonPressed(Input.Buttons.LEFT)) {
            colorChangingSprite.draw(batch);
        }

        batch.end();
    }

    public void update(float delta) {
        changeTimeLeft -= delta;
        if (changeTimeLeft <= 0) {
            changeTimeLeft = changeTime;
            colorChangingSprite = new ScaledSprite(CustomImage.getColorSprite(randomColor(), 15, 15));
        }

        if (input.mouseButtonPressed(Input.Buttons.LEFT)) {
            colorChangingSprite.setPosition(
                input.getMousePos().x - (colorChangingSprite.getWidth()) / 2,
                input.getMousePos().y - (colorChangingSprite.getHeight()) / 2);

        }
    }

    private Color randomColor() {
        Random rand = new Random();
        return new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), 1f);
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
