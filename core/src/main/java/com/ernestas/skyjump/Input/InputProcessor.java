package com.ernestas.skyjump.Input;

import com.ernestas.skyjump.Settings.Settings;
import com.ernestas.skyjump.Util.Vectors.Vector2f;

public class InputProcessor implements com.badlogic.gdx.InputProcessor {

    public static final int KEY_UP = 0;
    public static final int KEY_PRESSED = 1;
    public static final int KEY_HOLD = 2;

    private int keys[] = new int[256];
    private boolean buttons[] = new boolean[5];

    private boolean mouseDown;
    private Vector2f mousePos = new Vector2f();

    public InputProcessor() {
        for (int i = 0; i < keys.length; ++i) {
            keys[i] = KEY_UP;
        }
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = false;
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        keys[keycode] = KEY_PRESSED;
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        keys[keycode] = KEY_UP;
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        mouseDown = true;
        mousePos.x = screenX;
        mousePos.y = Settings.getHeight() - screenY;
        buttons[button] = true;
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        mouseDown = false;
        buttons[button] = false;
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        mouseDown = true;
        mousePos.x = screenX;
        mousePos.y = Settings.getHeight() - screenY;
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        mousePos.x = screenX;
        mousePos.y = Settings.getHeight() - screenY;
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public int getKeyStatus(int keycode) {
        return keys[keycode];
    }

    public boolean isPressed(int keycode) {
        return keys[keycode] == KEY_PRESSED;
    }

    public boolean isHold(int keycode) {
        return keys[keycode] == KEY_HOLD;
    }

    public boolean isPressedAdvanced(int keycode) {
        boolean result = isPressed(keycode);
        keys[keycode] = KEY_HOLD;
        return result;
    }

    public boolean mouseButtonPressed(int button) {
        if (button < 0 || button >= buttons.length) {
            System.err.println("MOUSE BUTTON INDEX OUT OF BOUNDS. int[" + button + "]");
            return false;
        }
        return buttons[button];
    }

    public boolean mouseDown() { return mouseDown; }
    public Vector2f getMousePos() { return mousePos; }
}
