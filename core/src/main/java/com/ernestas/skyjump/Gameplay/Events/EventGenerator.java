package com.ernestas.skyjump.Gameplay.Events;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.ernestas.skyjump.Loaders.ImageLoader;
import com.ernestas.skyjump.Resources.GameResources;
import com.ernestas.skyjump.Settings.Settings;
import com.ernestas.skyjump.Sprite.ScaledSprite;
import com.ernestas.skyjump.Util.Vectors.Vector2f;

public class EventGenerator {

    private EventGenerator() {}

    public static Event generateEvent(int id, Vector2f position) {
        Event event = null;
        position.x *= Settings.getScale();
        position.y *= Settings.getScale();

        switch(id) {
            case Event.EVENT_VICTORY:
                event = new EventVictory(id, position);
                break;
            case Event.EVENT_HAND:
                event = new EventHand(id, position);
            default:
                break;
        }


        return event;
    }


}
