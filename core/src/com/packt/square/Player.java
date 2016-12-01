package com.packt.square;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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
            player.squareX = player.playerSpawnPositionX;
            player.squareY = player.playerSpawnPositionY;
            player.score = 0;
        }
    }


    public Player() {
        // Adds this player to the static players collection
        players.add(this);
        squareX = playerSpawnPositionX;
        squareY = playerSpawnPositionY;
        setSpawnPosition();
        setPicture();
    }

    public void setSpawnPosition() {
        playerSpawnPositionX = 0;
        playerSpawnPositionY = 0;
    }

    public void setPicture() {
        squareMain = new Texture(Gdx.files.internal(("squarv.png")));
    }

    public void checkBounds() {
        if (squareX >= Gdx.graphics.getWidth()) {
            GameScreen.state = GameScreen.STATE.GAME_OVER;
        }
        if (squareX < 0) {
            GameScreen.state = GameScreen.STATE.GAME_OVER;
        }
        if (squareY >= Gdx.graphics.getHeight()) {
            GameScreen.state = GameScreen.STATE.GAME_OVER;
        }
        if (squareY < 0) {
            GameScreen.state = GameScreen.STATE.GAME_OVER;
        }
    }

    public void draw(SpriteBatch batch) {
        batch.draw(squareMain, squareX, squareY);
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
        if (GameScreen.coinX == squareX && GameScreen.coinY == squareY) {
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
                squareX += GameScreen.SQUARE_MOVEMENT;
                return;
            }
            case LEFT: {
                squareX -= GameScreen.SQUARE_MOVEMENT;
                return;
            }
            case UP: {
                squareY += GameScreen.SQUARE_MOVEMENT;
                return;
            }
            case DOWN: {
                squareY -= GameScreen.SQUARE_MOVEMENT;
            }

        }
    }
}
