package com.ernestas.skyjump.Sprite;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ernestas.skyjump.Loaders.ImageLoader;
import com.ernestas.skyjump.Resources.GameResources;
import com.ernestas.skyjump.Util.Vectors.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class Frame {

    private BitmapFont font;
    private List<String> texts = new ArrayList<>();

    private String bottomText = "";

    private ScaledSprite sprite;
    private Camera camera;

    public Frame(Camera camera, String text, BitmapFont font, Vector2f position) {
        this.camera = camera;
        texts.add(text);
        this.font = font;

        sprite = getDefaultSprite();
        sprite.setPosition(position.x, position.y);
    }

    public void setText(String text) {
        texts.clear();
        addText(text);
    }

    public void addText(String text) {
        texts.add(text);
    }

    public void setBottomText(String text) {
        bottomText = text;
    }

    public static ScaledSprite getDefaultSprite() {
        return new ScaledSprite(GameResources.getImageLoader().getImage(ImageLoader.FRAME));
    }

    public void render(SpriteBatch batch) {
        sprite.setPosition(camera.position.x - sprite.getWidth() / 2, camera.position.y - sprite.getHeight() / 2);
        sprite.draw(batch);

        // draw all the texts
        // TODO: Add a normal method to add all the strings


        float spacing = 20f;
        // draw first text
        String text = texts.get(0);
        BitmapFont.TextBounds bounds = font.getBounds(text);
        font.draw(batch, text, sprite.getX() + (sprite.getWidth() - bounds.width) / 2,
            sprite.getY() + (sprite.getHeight() - bounds.height) / 2 + spacing / 2);


        text = texts.get(1);
        bounds = font.getBounds(text);
        font.draw(batch, text, sprite.getX() + (sprite.getWidth() - bounds.width) / 2,
            sprite.getY() + (sprite.getHeight() - bounds.height) / 2 - spacing / 2);


        if (!bottomText.isEmpty()) {
            text = bottomText;
            bounds = font.getBounds(text);
            font.draw(batch, text, sprite.getX() + (sprite.getWidth() - bounds.width) / 2,
                sprite.getY() + spacing * 2);
        }
    }
}
