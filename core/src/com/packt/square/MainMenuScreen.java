package com.packt.square;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Created by kirweekend on 12/4/16.
 */

public class MainMenuScreen extends ScreenAdapter {
    private static final float WORLD_WIDTH = 640;
    private static final float WORLD_HEIGHT = 480;
    private Stage stage;
    private Texture backgroundTexture;
    private Table table;
    private Skin skin;
    private SquareGame game;

    public MainMenuScreen(SquareGame squareGame) {
        game = squareGame;
    }

    public void show() {

        stage = new Stage(new FitViewport(WORLD_WIDTH, WORLD_HEIGHT));
        skin = new Skin();
        table = new Table();
        Gdx.input.setInputProcessor(stage);
        backgroundTexture = new Texture(Gdx.files.internal("background.jpg"));

        skin.add("background", backgroundTexture);
        skin.add("Font", new BitmapFont(Gdx.files.internal("fonts/font.fnt"),
                Gdx.files.internal("fonts/font.png"), false));
        table.setFillParent(true);
        table.align(Align.center | Align.top);
        table.setBackground(skin.getDrawable("background"));

        Label.LabelStyle LabelStyle = new Label.LabelStyle();
        LabelStyle.font = skin.getFont("Font");

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = skin.getFont("Font");
        textButtonStyle.fontColor = com.badlogic.gdx.graphics.Color.BLACK;
        textButtonStyle.overFontColor = com.badlogic.gdx.graphics.Color.WHITE;

        skin.add("default", LabelStyle);
        skin.add("default", textButtonStyle);

        Label gameInfo = new Label("", skin);
        gameInfo.setText("Trunov Kirill C11");

        TextButton startGameButton = new TextButton("Start game", skin);
        startGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen());
                dispose();
            }
        });

        TextButton exitGameButton = new TextButton("Exit", skin);
        exitGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {Gdx.app.exit();}
        });

        table.add(gameInfo).padBottom(80).padTop(15);
        table.row();
        table.add(startGameButton).padBottom(20).padTop(10);
        table.row();
        table.add(exitGameButton);
        table.row();

        stage.addActor(table);
    }

    public void dispose() {
        skin.dispose();
        stage.dispose();
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }
}