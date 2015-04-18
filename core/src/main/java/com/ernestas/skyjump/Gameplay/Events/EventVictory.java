package com.ernestas.skyjump.Gameplay.Events;

import com.ernestas.skyjump.Util.Vectors.Vector2f;

public class EventVictory extends Event {

    public EventVictory(int id, Vector2f position) {
        super(getDefaultSprite(id), position);
    }

    @Override
    public void event() {
        level.simpleVictory();
    }
}
