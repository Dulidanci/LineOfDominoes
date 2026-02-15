package io.github.dulidanci.lineofdominoes.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

public class MouseState {
    public int x;
    public int y;

    public int deltaX;
    public int deltaY;

    public float worldX;
    public float worldY;

    public float deltaWorldX;
    public float deltaWorldY;

    public boolean leftPressed;
    public boolean leftJustPressed;
    public boolean leftJustReleased;

    private boolean previousLeftPressed;

    public void update() {
        int newX = Gdx.input.getX();
        int newY = Gdx.input.getY();

        deltaX = newX - x;
        deltaY = newY - y;

        x = newX;
        y = newY;

        boolean current = Gdx.input.isButtonPressed(Input.Buttons.LEFT);

        leftJustPressed = current && !previousLeftPressed;
        leftJustReleased = !current && previousLeftPressed;
        leftPressed = current;

        previousLeftPressed = current;
    }

    public void projectedMouse(Vector2 mousePosition) {
        deltaWorldX = mousePosition.x - worldX;
        deltaWorldY = mousePosition.y - worldY;

        worldX = mousePosition.x;
        worldY = mousePosition.y;
    }
}
