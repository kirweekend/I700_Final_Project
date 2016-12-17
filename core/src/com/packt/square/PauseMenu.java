package com.packt.square;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.packt.square.GameScreen;



public class PauseMenu {
    private OrthographicCamera camera;
    private Stage stage;
    private Skin skin;

    /**
     * Instantiation method, when it is called all assets are allocated in memory.
     */
    public PauseMenu() {
        camera = new OrthographicCamera();
        createUi();
    }

    /**
     * Renders all the assets that are created by {@link PauseMenu#createUi} method.
     * @param delta frame delta time, should contain main renderer delta
     */
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

    /**
     * Loads all the assets for the menu and sets their position in the game world.
     */
    private void createUi(){
        Table table;

        skin = new Skin();
        table = new Table();
        stage = new Stage(new FitViewport(640, 480, camera));
        Gdx.input.setInputProcessor(stage);

        skin.add("Font64", new BitmapFont(Gdx.files.internal("fonts/font.fnt"),
                Gdx.files.internal("fonts/font.png"), false)
        );

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = skin.getFont("Font64");
        textButtonStyle.fontColor = Color.LIGHT_GRAY;
        textButtonStyle.overFontColor = Color.WHITE;

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

    /**
     * Disposes of all the assets that are not taken care of by garbage collector.
     */
    public void dispose(){
        stage.dispose();
        skin.dispose();
    }
}