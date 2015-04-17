package com.ernestas.skyjump.Loaders;

import com.badlogic.gdx.math.Rectangle;
import com.ernestas.skyjump.Gameplay.Level;
import com.ernestas.skyjump.Gameplay.Platforms.Platform;
import com.ernestas.skyjump.Gameplay.Platforms.PlatformGenerator;
import com.ernestas.skyjump.Util.FileStringifier;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LevelProvider {
    private LevelProvider() {}

    public static Level generateLevel(String path) {
        Level.Builder builder = new Level.Builder();
        JSONObject obj;

        try {
            obj = new JSONObject(FileStringifier.fileToString(path));

            getPlatforms(obj).forEach((platform) -> builder.addPlatform(platform));


        } catch (IOException e) {
            System.err.println("FAILED TO PARSE JSON FILE TO STRING");
            e.printStackTrace();
            return null;
        } catch (JSONException e) {
            System.err.println("FAILED TO PARSE JSON OBJECT FROM STRING");
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
            int width = platformArray.getInt(3);

            platforms.add(PlatformGenerator.generatePlatform(id, new Rectangle(x, y, width, 32)));
        }

        return platforms;
    }

}
