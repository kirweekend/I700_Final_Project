package com.packt.square;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
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
    private Texture background;
    private static final float MOVE_TIME = 0.1F;
    public static final int SQUARE_MOVEMENT = 32;
    private float timer = MOVE_TIME;
    public static boolean coinUsable = false;
    public static int coinX, coinY;
    public static STATE state = STATE.PLAYING;
    private BitmapFont bitmapFont;
    public static final int POINTS_PER_COIN = 10;
    private Music song;
    Player player1 = new Player();
    PlayerTwo player2 = new PlayerTwo();
    PauseMenu pauseMenu = new PauseMenu();

    public enum STATE {
        PLAYING,
        GAME_OVER,
        PAUSE,
        RESUME
    }

    private void checkForRestart() {
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) doRestart();
    }

    public void music() {
        song = Gdx.audio.newMusic(Gdx.files.internal("mymusic.mp3"));
        song.play();
    }

    private void doRestart() {
        state = STATE.PLAYING;
        timer = MOVE_TIME;
        Player.resetPlayers();
        coinUsable = false;
        song.play();
    }

    private void placeCoin() {
        if (!coinUsable && (player2.squareDirection != 4 || player1.squareDirection != 4)) {
            coinX = MathUtils.random(Gdx.graphics.getWidth() / SQUARE_MOVEMENT - 1) * SQUARE_MOVEMENT;
            coinY = MathUtils.random((Gdx.graphics.getHeight() - 64) / SQUARE_MOVEMENT - 1) * SQUARE_MOVEMENT;
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

    public void checkPlayerCollision() {
        if (player1.square.x < player2.square.x + 32 &&
                player1.square.x + 32 > player2.square.x &&
                player1.square.y < player2.square.y + 32 &&
                32 + player1.square.y > player2.square.y) {
            state = STATE.GAME_OVER;
            song.stop();
        }
    }

    private void mainLogic() {
        if (player1.checkBounds() == true) {
            bitmapFont.draw(batch, "Player Two Won", 120, 200);
        } else if (player2.checkBounds() == true) {
            bitmapFont.draw(batch, "Player One Won", 120, 200);
        } else if (player1.score > player2.score) {
            bitmapFont.draw(batch, "Player One Won", 120, 200);
        } else if (player1.score < player2.score) {
            bitmapFont.draw(batch, "Player Two Won", 120, 200);
        } else {
            bitmapFont.draw(batch, "Draw", 260, 210);
        }
    }

    private void draw() {
        batch.begin();
        batch.draw(background, 0, 0);
        if (state == STATE.PLAYING) {
            int playerIndex = 0;
            for (Player player : Player.getPlayers()) {
                String scoreAsString = Integer.toString(player.score);
                bitmapFont.draw(batch, scoreAsString, 220 + 192 * (playerIndex), 440);
                player.draw(batch);
                playerIndex++;
            }
        }
        if (coinUsable && state == STATE.PLAYING) {
            batch.draw(coin, coinX, coinY);
        }
        if (state == STATE.PAUSE) {
            bitmapFont.draw(batch, "Pause ", 240, 300);
        }
        if (state == GameScreen.STATE.GAME_OVER) {
            bitmapFont.draw(batch, "Game Over ", 180, 270);
            mainLogic();
            song.stop();
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

    private void checkPause() {
        if (Gdx.input.isKeyPressed(Input.Keys.P)) {
            state = STATE.PAUSE;
        }
    }

    @Override
    public void render(float delta) {
        switch (state) {
            case PLAYING: {
                checkPause();
                keyboardInput();
                placeCoin();
                if (player1.score + player2.score == 100) {
                    state = STATE.GAME_OVER;
                }
                checkCollision();
                checkPlayerCollision();
            }
            timer -= delta;
            if (timer <= 0) {
                timer = MOVE_TIME;
                moveEntities();
                checkBounds();
            }
            break;
            case GAME_OVER: {
                checkForRestart();
            }
            break;
            case PAUSE: {
                pauseMenu.render(delta);
            }
            break;
            case RESUME: {
                state = STATE.PLAYING;
            }
        }

            clearScreen();
            draw();

    }
    @Override
    public void pause() {
        if (state != STATE.GAME_OVER) {
            state = STATE.PAUSE;
        }
    }

        @Override
        public void show () {
            music();
            background = new Texture(Gdx.files.internal("background.jpg"));
            placeCoin();
            bitmapFont = new BitmapFont(Gdx.files.internal("fonts/font.fnt"),
                    Gdx.files.internal("fonts/font.png"), false);
            coin = new Texture(Gdx.files.internal("coin.png"));
            batch = new SpriteBatch();
        }

}
