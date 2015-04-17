package com.ernestas.skyjump.Loaders;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.ernestas.skyjump.Settings.Settings;
import com.ernestas.skyjump.Sprite.ScaledSprite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ImageLoader {
    private static final String SPRITE_LOCATION = "Sprites/";
    private static final String PLATFORM_LOCATION = SPRITE_LOCATION + "Platforms/";

    public static final String SPLASH = "badlogic.jpg";
    public static final String SIMPLE_PLATFORM = PLATFORM_LOCATION + "simple_platform.png";

    List<String> spritePaths;
    HashMap<String, Sprite> spriteMap;
    boolean loaded = false;

    public void load() {
        fillList();
        populateMap();
    }

    private void fillList() {
        spritePaths = new ArrayList<>();

        spritePaths.add(SPLASH);
        spritePaths.add(SIMPLE_PLATFORM);
    }

    private void populateMap() {
        spriteMap = new HashMap<>();

        for (String path: spritePaths) {
            spriteMap.put(path, loadImage(path));
        }

        loaded = true;
    }

    private Sprite loadImage(String path) {
        return loadImage(path, true);
    }

    private Sprite loadImage(String path, boolean scale) {
        Texture texture = new Texture(path);
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Sprite sprite = new Sprite(texture);
        if (scale) {
            sprite = SpriteScaler.scaleSprite(sprite, Settings.getScale());
        }

        return sprite;
    }

    public ScaledSprite getImage(String path) {
        Sprite sprite = spriteMap.get(path);

        if (sprite == null) {
            System.err.println("Sprite not found in sprite map. Trying to load from path:\"" + path + "\"");
            sprite = loadImage(path);
            if (sprite != null) {
                spriteMap.put(path, sprite);
            }
        }

        return new ScaledSprite(sprite);
    }

}
