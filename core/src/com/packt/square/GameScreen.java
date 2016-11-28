package com.packt.square;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.awt.*;

/**
 * Created by kirweekend on 11/25/16.
 */
public class GameScreen extends ScreenAdapter {
    private SpriteBatch batch;
    private Texture squareMain;
    private static final float MOVE_TIME = 0.1F;
    private static final int SQUARE_MOVEMENT = 32;
    private float timer = MOVE_TIME;
    private static final int RIGHT = 0;
    private static final int LEFT = 1;
    private static final int UP = 2;
    private static final int DOWN = 3;
    private static final int START = 4;
    private int squareX = 320, squareY = 240;
    private int squareDirection = START;

    @Override
    public void show() {
        squareMain = new Texture(Gdx.files.internal(("squaremain.png")));
        batch = new SpriteBatch();
    }

    private void checkBounds() {
        if (squareX >= Gdx.graphics.getWidth()) {
            squareX = 0;
        }
        if (squareX < 0) {
            squareX = Gdx.graphics.getWidth() - SQUARE_MOVEMENT;
        }
        if (squareY >= Gdx.graphics.getHeight()) {
            squareY = 0;
        }
        if (squareY < 0) {
            squareY = Gdx.graphics.getHeight() - SQUARE_MOVEMENT;
        }
    }

    @Override
    public void render(float delta) {
        keyboardInput();
        timer -= delta;
        if (timer <= 0) {
            timer = MOVE_TIME;
            moveSquare();
        }
        checkBounds();
        Gdx.gl.glClearColor(Color.BLACK.getRed(), Color.BLACK.getGreen(), Color.BLACK.getBlue(), Color.BLACK.getAlpha());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(squareMain, squareX, squareY);
        batch.end();
    }

    private void moveSquare() {
        switch (squareDirection) {
            case RIGHT: {
                squareX += SQUARE_MOVEMENT;
                return;
            }
            case LEFT: {
                squareX -= SQUARE_MOVEMENT;
                return;
            }
            case UP: {
                squareY += SQUARE_MOVEMENT;
                return;
            }
        }
    }

    private void keyboardInput() {
        boolean lPressed = Gdx.input.isKeyPressed(Input.Keys.LEFT);
        boolean rPressed = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
        boolean uPressed = Gdx.input.isKeyPressed(Input.Keys.UP);
        boolean dPressed = Gdx.input.isKeyPressed(Input.Keys.DOWN);
        boolean isPressed = Gdx.input.isKeyPressed(Input.Keys.SPACE);
        if (lPressed) squareDirection = LEFT;
        if (rPressed) squareDirection = RIGHT;
        if (uPressed) squareDirection = UP;
        if (dPressed) squareDirection = DOWN;
        if (isPressed) squareDirection = START;
    }
}
