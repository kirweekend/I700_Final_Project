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

    @Override
    public void show() {
        squareMain = new Texture(Gdx.files.internal(("squaremain.png")));
        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(Color.BLACK.getRed(), Color.BLACK.getGreen(), Color.BLACK.getBlue(), Color.BLACK.getAlpha());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(squareMain, 0, 0);
        batch.end();
    }
}
