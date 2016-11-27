package com.packt.square;

import com.badlogic.gdx.Gdx;
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
    private int squareX = 0, squareY = 0;

    @Override
    public void show() {
        squareMain = new Texture(Gdx.files.internal(("squaremain.png")));
        batch = new SpriteBatch();
    }

    private void checkForBounds() {
        if (squareX >= Gdx.graphics.getWidth()) {
            squareX = 0;
        }
    }

    @Override
    public void render(float delta) {
        timer -= delta;
        if (timer <= 0) {
            timer = MOVE_TIME;
            squareX += SQUARE_MOVEMENT;
        }
        checkForBounds();
        Gdx.gl.glClearColor(Color.BLACK.getRed(), Color.BLACK.getGreen(), Color.BLACK.getBlue(), Color.BLACK.getAlpha());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(squareMain, squareX, squareY);
        batch.end();
    }
}
