package com.ernestas.skyjump.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ernestas.skyjump.MyGame;
import com.ernestas.skyjump.Settings.Settings;

public class DesktopLauncher {
    private static final float SCALE = 1.5f;
    private static final int FRAMES_PER_SECOND = 60;

    public static void setUpSettings() {
        Settings.setScale(SCALE);
        Settings.setFrameRate(FRAMES_PER_SECOND);
    }

	public static void main (String[] arg) {
        setUpSettings();
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        config.title = MyGame.GAME_TITLE;

        config.width = Settings.getWidth();
        config.height = Settings.getHeight();

        config.foregroundFPS = Settings.getFrameRate();
        config.backgroundFPS = Settings.getFrameRate();

        config.vSyncEnabled = true;
        config.resizable = false;
        config.useGL30 = false;

		new LwjglApplication(new MyGame(), config);
	}
}
