package com.packt.square;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

/**
 * Created by kirweekend on 11/30/16.
 */
public class Player {
    protected Texture squareMain;
    protected int squareX;
    protected int squareY;

    protected static final int RIGHT = 0;
    protected static final int LEFT = 1;
    protected static final int UP = 2;
    protected static final int DOWN = 3;
    protected static final int START = 4;
    protected Rectangle square;
    public int score = 0;

    protected int squareDirection = START;

    protected int playerSpawnPositionX;
    protected int playerSpawnPositionY;

    public static ArrayList<Player> players = new ArrayList<Player>();

    // Returns players collections
    public static ArrayList<Player> getPlayers() {
        return players;
    }

    public static void resetPlayers() {
        for (Player player : Player.getPlayers()) {
            player.squareDirection = START;
            player.square.x = player.playerSpawnPositionX;
            player.square.y = player.playerSpawnPositionY;
            player.score = 0;
        }
    }

    public Player() {
        // Adds this player to the static players collection
        players.add(this);
        setSpawnPosition();
        square = new Rectangle(playerSpawnPositionX, playerSpawnPositionY, 32, 32);
        setPicture();
    }

    public void setSpawnPosition() {
        playerSpawnPositionX = 224;
        playerSpawnPositionY = 288;
    }

    public void setPicture() {
        squareMain = new Texture(Gdx.files.internal(("squarv.png")));
    }

    public void checkBounds() {
        if (square.x >= Gdx.graphics.getWidth()) {
            GameScreen.state = GameScreen.STATE.GAME_OVER;
        }
        if (square.x < 0) {
            GameScreen.state = GameScreen.STATE.GAME_OVER;
        }
        if (square.y >= Gdx.graphics.getHeight()) {
            GameScreen.state = GameScreen.STATE.GAME_OVER;
        }
        if (square.y < 0) {
            GameScreen.state = GameScreen.STATE.GAME_OVER;
        }
    }

    public void draw(SpriteBatch batch) {
        batch.draw(squareMain, square.x, square.y);
    }

    public void keyboardInput() {
        boolean lPressed = Gdx.input.isKeyPressed(Input.Keys.A);
        boolean rPressed = Gdx.input.isKeyPressed(Input.Keys.D);
        boolean uPressed = Gdx.input.isKeyPressed(Input.Keys.W);
        boolean dPressed = Gdx.input.isKeyPressed(Input.Keys.S);
        boolean isPressed = Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT);
        if (lPressed) squareDirection = LEFT;
        if (rPressed) squareDirection = RIGHT;
        if (uPressed) squareDirection = UP;
        if (dPressed) squareDirection = DOWN;
        if (isPressed) squareDirection = START;
    }

    public void checkCollision() {
        if (GameScreen.coinX == square.x && GameScreen.coinY == square.y) {
            GameScreen.coinUsable = false;
            addToScore();
        }
    }

    private void addToScore() {
        score += GameScreen.POINTS_PER_COIN;
    }

    public void move() {
        switch (squareDirection) {
            case RIGHT: {
                square.x += GameScreen.SQUARE_MOVEMENT;
                return;
            }
            case LEFT: {
                square.x -= GameScreen.SQUARE_MOVEMENT;
                return;
            }
            case UP: {
                square.y += GameScreen.SQUARE_MOVEMENT;
                return;
            }
            case DOWN: {
                square.y -= GameScreen.SQUARE_MOVEMENT;
            }

        }
    }
}
