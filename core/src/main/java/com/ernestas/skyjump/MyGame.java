package com.ernestas.skyjump;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.ernestas.skyjump.Input.InputProcessor;
import com.ernestas.skyjump.Loaders.GlobalLoader;
import com.ernestas.skyjump.Screens.PlayScreen;

public class MyGame extends Game {
    public static String GAME_TITLE = "Sky Jump";

    // Input proccesing
    InputProcessor input;

	@Override
	public void create () {
        input = new InputProcessor();
        Gdx.input.setInputProcessor(input);

        loadResources();

        setScreen(new PlayScreen(this));
    }

    private void loadResources() {
        GlobalLoader.load();
    }

    public InputProcessor getInput() { return input; }
}
