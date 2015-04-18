package com.ernestas.skyjump.Loaders;

import com.badlogic.gdx.math.Rectangle;
import com.ernestas.skyjump.Gameplay.Events.Event;
import com.ernestas.skyjump.Gameplay.Events.EventGenerator;
import com.ernestas.skyjump.Gameplay.Level;
import com.ernestas.skyjump.Gameplay.Platforms.Platform;
import com.ernestas.skyjump.Gameplay.Platforms.PlatformGenerator;
import com.ernestas.skyjump.Util.FileStringifier;
import com.ernestas.skyjump.Util.Vectors.Vector2f;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LevelProvider {
    private LevelProvider() {}

    public static Level generateLevel(String lines) {
        Level.Builder builder = new Level.Builder();
        JSONObject obj;

        try {
            obj = new JSONObject(lines);

            getPlatforms(obj).forEach((platform) -> builder.addPlatform(platform));
            getEvents(obj).forEach((event) -> builder.addEvent(event));


        } catch (Exception e) {
            System.err.println("FAILED TO PARSE JSON FILE TO STRING");
            e.printStackTrace();
            return null;
        }

        return builder.build();
    }

    private static List<Platform> getPlatforms(JSONObject obj) throws JSONException {
        List<Platform> platforms = new ArrayList<>();

        JSONArray array = obj.getJSONArray("platforms");

        for (int i = 0; i < array.length(); ++i) {
            JSONArray platformArray = array.getJSONArray(i);

            int id = platformArray.getInt(0);
            int x = platformArray.getInt(1);
            int y = platformArray.getInt(2);
            int length = platformArray.getInt(3);

            boolean solid = true;
            boolean visible = true;

            if (id == PlatformGenerator.WALL) {
                solid = platformArray.getBoolean(4);
                visible = platformArray.getBoolean(5);
            }

            platforms.add(PlatformGenerator.generatePlatform(id, new Rectangle(x, y, length, length), solid, visible));
        }

        return platforms;
    }

    private static List<Event> getEvents(JSONObject obj) throws JSONException {
        List<Event> events = new ArrayList<>();

        JSONArray array = obj.getJSONArray("events");

        for (int i = 0; i < array.length(); ++i) {
            JSONArray eventArray = array.getJSONArray(i);

            int id = eventArray.getInt(0);
            int x = eventArray.getInt(1);
            int y = eventArray.getInt(2);

            events.add(EventGenerator.generateEvent(id, new Vector2f(x, y)));
        }

        return events;
    }

}
