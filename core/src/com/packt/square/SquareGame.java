package com.packt.square;

import com.badlogic.gdx.Game;

public class SquareGame extends Game {

    @Override
    public void create() {
        setScreen(new GameScreen());
    }
}
