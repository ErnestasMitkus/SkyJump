package com.ernestas.skyjump.Settings;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SettingsTest {

    @Before
    public void setUp() {
        Settings.reset();
    }

    @After
    public void tearDown() {
        Settings.reset();
    }

    @Test
    public void testDefaults() {
        Settings.reset();
        assertThat(Settings.getScale(), is(1f));
        assertThat(Settings.getFrameRate(), is(Settings.DEFAULT_FRAME_RATE));
    }

    @Test
    public void testSettings() {
        float scale = 1.5f;
        int fps = 60;

        Settings.setFrameRate(fps);
        Settings.setScale(scale);

        assertThat(Settings.getScale(), is(scale));
        assertThat(Settings.getWidth(), is((int) Math.ceil(Settings.DEFAULT_WIDTH * scale)));
        assertThat(Settings.getHeight(), is((int) Math.ceil(Settings.DEFAULT_HEIGHT * scale)));
        assertThat(Settings.getFrameRate(), is(fps));
    }

}