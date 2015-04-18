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
    private static final String PLAYER_LOCATION = SPRITE_LOCATION + "Player/";
    private static final String MISC_LOCATION = SPRITE_LOCATION + "Misc/";

    // everything here must be in the map
    public static final String BACKGROUND = SPRITE_LOCATION + "background.png";
    public static final String BACKGROUND_RED = SPRITE_LOCATION + "backgroundRed.png";
    public static final String MENU_BACKGROUND = SPRITE_LOCATION + "menuBackground.png";


    public static final String PLAYERSS = PLAYER_LOCATION + "playerSS.png";

    public static final String SIMPLE_PLATFORM = PLATFORM_LOCATION + "simple_platform.png";
    public static final String GROUND_PLATFORM = PLATFORM_LOCATION + "ground_platform.png";
    public static final String WALL = PLATFORM_LOCATION + "wall.png";
    public static final String CORRUPT = PLATFORM_LOCATION + "corrupt.png";

    public static final String FLAG = MISC_LOCATION + "flag.png";
    public static final String HAND = MISC_LOCATION + "hand.png";
    public static final String ARROW = MISC_LOCATION + "arrow.png";
    public static final String FRAME = MISC_LOCATION + "frame.png";

    List<String> spritePaths;
    HashMap<String, Sprite> spriteMap;
    boolean loaded = false;

    public void load() {
        fillList();
        populateMap();
    }

    private void fillList() {
        spritePaths = new ArrayList<>();

        spritePaths.add(BACKGROUND);
        spritePaths.add(BACKGROUND_RED);
        spritePaths.add(MENU_BACKGROUND);
        spritePaths.add(PLAYERSS);
        spritePaths.add(SIMPLE_PLATFORM);
        spritePaths.add(GROUND_PLATFORM);
        spritePaths.add(CORRUPT);
        spritePaths.add(WALL);
        spritePaths.add(FLAG);
        spritePaths.add(HAND);
        spritePaths.add(ARROW);
        spritePaths.add(FRAME);
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

        return new ScaledSprite(new Sprite(sprite));
    }

}
