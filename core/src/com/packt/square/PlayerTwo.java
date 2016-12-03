package com.packt.square;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by kirweekend on 11/30/16.
 */
public class PlayerTwo extends Player{

    public void keyboardInput(){
        boolean lPressed = Gdx.input.isKeyPressed(Input.Keys.LEFT);
        boolean rPressed = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
        boolean uPressed = Gdx.input.isKeyPressed(Input.Keys.UP);
        boolean dPressed = Gdx.input.isKeyPressed(Input.Keys.DOWN);
        boolean isPressed = Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT);
        if (lPressed) squareDirection = LEFT;
        if (rPressed) squareDirection = RIGHT;
        if (uPressed) squareDirection = UP;
        if (dPressed) squareDirection = DOWN;
        if (isPressed) squareDirection = START;
    }

    public void setSpawnPosition() {
        playerSpawnPositionX = 384;
        playerSpawnPositionY = 288;
    }

    public void setPicture() {
        squareMain = new Texture(Gdx.files.internal(("squarr.png")));
    }
}


