package uk.ac.aston.teamproj.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.TimeUtils;

import uk.ac.aston.teamproj.game.MainGame;

public class LoadingScreen implements Screen {
    private MainGame mGame;
    private BitmapFont bf_loadProgress;
    private float progress = 0;
    private float startTime = 0;
    private ShapeRenderer mShapeRenderer;
    private OrthographicCamera camera;
    private final int screenWidth = 800, screenHeight = 480;

    private int clientID;
    private String mapPath;
    
    public LoadingScreen(Game game, int clientID, String mapPath) {
        mGame = (MainGame) game;
        bf_loadProgress = new BitmapFont();
        //bf_loadProgress.setScale(2, 1);
        mShapeRenderer = new ShapeRenderer();
        startTime = TimeUtils.nanoTime();
        initCamera();
        
        this.clientID = clientID;
        this.mapPath = mapPath;
    }

    private void initCamera() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, screenWidth, screenHeight);
        camera.update();
    }

    @Override
    public void show() {
        // TODO Auto-generated method stub

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        showLoadProgress();
    }

    /**
     * Show progress that updates after every half second "0.5 sec"
     */
    private void showLoadProgress() {

        long currentTimeStamp = TimeUtils.nanoTime();
        if (currentTimeStamp - startTime > TimeUtils.millisToNanos(2)) {
            startTime = currentTimeStamp;
            progress = progress + 0.18f;
        }
        // Width of progress bar on screen relevant to Screen width
        float progressBarWidth = (screenWidth / 100) * progress;

        mGame.batch.setProjectionMatrix(camera.combined);
        mGame.batch.begin();
        bf_loadProgress.draw(mGame.batch, "Loading " + Math.round(progress) + " / " + 100, 10, 40);
        mGame.batch.end();

        mShapeRenderer.setProjectionMatrix(camera.combined);
        mShapeRenderer.begin(ShapeType.Filled);
        mShapeRenderer.setColor(Color.YELLOW);
        mShapeRenderer.rect(0, 10, progressBarWidth, 10);
        mShapeRenderer.end();
        if (progress >= 100)
            moveToMenuScreen();

    }

    /**
     * Move to menu screen after progress reaches 100%
     */
    private void moveToMenuScreen() {
        mGame.setScreen(new PlayScreen(mGame, clientID, mapPath));
        dispose();
    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub

    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
        bf_loadProgress.dispose();
        mShapeRenderer.dispose();
    }

}