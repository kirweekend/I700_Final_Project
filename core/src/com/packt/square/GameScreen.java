package com.packt.square;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

import java.awt.*;

/**
 * Created by kirweekend on 11/25/16.
 */
public class GameScreen extends ScreenAdapter {
    private SpriteBatch batch;
    private Texture coin;
    private static final float MOVE_TIME = 0.1F;
    public static final int SQUARE_MOVEMENT = 32;
    private float timer = MOVE_TIME;
    public static boolean coinUsable = false;
    public static int coinX, coinY;
    public static STATE state = STATE.PLAYING;
    private BitmapFont bitmapFont;
    public static final int POINTS_PER_COIN = 10;

    public enum STATE {
        PLAYING, GAME_OVER
    }

    private void checkForRestart() {
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) doRestart();
    }

    private void doRestart() {
        state = STATE.PLAYING;
        timer = MOVE_TIME;
        Player.resetPlayers();
        coinUsable = false;
    }

    private void placeCoin() {
        if (!coinUsable) {
            coinX = MathUtils.random(Gdx.graphics.getWidth() / SQUARE_MOVEMENT - 1) * SQUARE_MOVEMENT;
            coinY = MathUtils.random((Gdx.graphics.getHeight() - 40) / SQUARE_MOVEMENT - 1) * SQUARE_MOVEMENT;
            coinUsable = true;
        }
    }

    private void checkCollision() {
        for (Player player : Player.getPlayers()) {
            player.checkCollision();
        }
    }

    private void keyboardInput() {
        for (Player player : Player.getPlayers()) {
            player.keyboardInput();
        }
    }

    private void draw() {
        batch.begin();
        if (state == STATE.PLAYING) {
            int playerIndex = 0;
            for (Player player : Player.getPlayers()) {
                String scoreAsString = Integer.toString(player.score);
                bitmapFont.draw(batch, scoreAsString, 60 + 100 * (playerIndex), 440);
                player.draw(batch);
                playerIndex++;
            }
        }
        if (coinUsable && state != GameScreen.STATE.GAME_OVER) {
            batch.draw(coin, coinX, coinY);
        }
        if (state == GameScreen.STATE.GAME_OVER) {
            bitmapFont.draw(batch, "You died!", Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        }
        batch.end();
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(Color.BLACK.getRed(), Color.BLACK.getGreen(), Color.BLACK.getBlue(), Color.BLACK.getAlpha());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void checkBounds() {
        for (Player player : Player.getPlayers()) {
            player.checkBounds();
        }
    }

    private void moveEntities() {
        for (Player player : Player.getPlayers()) {
            player.move();
        }
    }

    @Override
    public void render(float delta) {
        switch (state) {
            case PLAYING: {
                keyboardInput();
                checkCollision();
                placeCoin();
                timer -= delta;
                if (timer <= 0) {
                    timer = MOVE_TIME;
                    moveEntities();
                    checkBounds();
                }
            }
            break;
            case GAME_OVER: {
                checkForRestart();
            }
            break;
        }
        clearScreen();
        draw();
    }

    @Override
    public void show() {
        placeCoin();
        Player player = new Player();
        bitmapFont = new BitmapFont();
        coin = new Texture(Gdx.files.internal("ls.png"));
        batch = new SpriteBatch();
    }
}
