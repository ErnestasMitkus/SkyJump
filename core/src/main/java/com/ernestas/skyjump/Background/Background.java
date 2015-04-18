package com.ernestas.skyjump.Background;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.ernestas.skyjump.Loaders.ImageLoader;
import com.ernestas.skyjump.Resources.GameResources;
import com.ernestas.skyjump.Sprite.ScaledSprite;

import java.util.ArrayList;
import java.util.List;

public class Background {

    List<ScaledSprite> bgLoopList = new ArrayList<>();

    public Background(Rectangle bounds) {
        this(bounds, GameResources.getImageLoader().getImage(ImageLoader.BACKGROUND));
    }

    public Background(Rectangle bounds, Sprite sprite) {
        ScaledSprite bg = new ScaledSprite(sprite);

        fillRectangleWithBg(bg, bounds);
    }

    private void fillRectangleWithBg(ScaledSprite bg, Rectangle bounds) {
        int xAmount = (int) Math.ceil(bounds.width / bg.getWidth());
        int yAmount = (int) Math.ceil(bounds.height / bg.getHeight());

        for (int x = 0; x < xAmount; ++x) {
            for (int y = 0; y < yAmount; ++y) {
                ScaledSprite sprite = new ScaledSprite(new Sprite(bg));
                sprite.setPosition(x * sprite.getWidth() + bounds.x, y * sprite.getHeight() + bounds.y);
                bgLoopList.add(sprite);
            }
        }
    }

    public void render(SpriteBatch batch) {
        bgLoopList.forEach((sprite) -> sprite.draw(batch));
    }
}
