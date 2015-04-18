package com.ernestas.skyjump.Sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.ernestas.skyjump.Loaders.SpriteScaler;
import com.ernestas.skyjump.Settings.Settings;

import java.util.ArrayList;
import java.util.List;

public class Spritesheet {

    private List<Sprite> spriteList = new ArrayList<Sprite>();

    public Spritesheet(Texture texture, int srcHeight, int width, int height, int amount) {
        for (int x = 0; x < amount; ++x) {
            if (spriteList.size() >= amount) {
                break;
            } else {
                Sprite sprite = new Sprite(texture, width * x, srcHeight, width, height);
                sprite = SpriteScaler.scaleSprite(sprite, Settings.getScale());
                spriteList.add(sprite);
            }
        }
    }

    public void setPosition(int x, int y) {
        for (Sprite sprite: spriteList) {
            sprite.setPosition(x, y);
        }
    }

    public int getAmount() {
        return spriteList.size();
    }

    public Sprite getSprite(int index) {
        if (index < 0 || index >= spriteList.size()) {
            return null;
        } else {
            return spriteList.get(index);
        }
    }

}
