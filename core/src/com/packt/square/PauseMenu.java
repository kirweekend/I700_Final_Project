package com.packt.square;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Created by kirweekend on 11/30/16.
 */
public class PauseMenu {
    private OrthographicCamera camera;
    private Stage stage;
    private Skin skin;

    public PauseMenu() {
        camera = new OrthographicCamera();
        createUi();
    }

    public void render(float delta) {
        camera.update();
        stage.act(delta);
        stage.draw();

        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)){
            GameScreen.state = GameScreen.STATE.PLAYING;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.Q)){
            Gdx.app.exit();
        }
    }

    private void createUi(){
        Table table;
        Texture backgroundTexture;
        stage = new Stage(new FitViewport(640, 480));
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

        skin.add("default", textButtonStyle);

        table.setFillParent(true);
        table.align(Align.center|Align.bottom);

        final TextButton newGameButton = new TextButton("Continue", skin);
        final TextButton exitButton = new TextButton("Quit", skin);

        newGameButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor){
                GameScreen.state = GameScreen.STATE.PLAYING;
            }
        });

        exitButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor){
                Gdx.app.exit();
            }
        });

        table.add(newGameButton);
        table.row();
        table.add(exitButton).padTop(10).padBottom(320);
        stage.addActor(table);
    }
}