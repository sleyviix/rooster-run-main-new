package uk.ac.aston.teamproj.game.screens;

import java.io.IOException;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import uk.ac.aston.teamproj.game.MainGame;
import uk.ac.aston.teamproj.game.net.MPClient;
import uk.ac.aston.teamproj.game.net.MPServer;
import uk.ac.aston.teamproj.game.sprites.Rooster;
import uk.ac.aston.teamproj.game.sprites.Rooster2;

/**
 * 
 * Created by Parmo on 8/11/2020
 *
 */

public class GameOverScreen implements Screen {

	private Viewport viewport;
	private Stage stage;
	
	@SuppressWarnings("unused")
	private Game game;
	
	//Rooster
	Rooster rooster;
	Rooster2 rooster2;
	
	
	public GameOverScreen(Game game) {
		Sound sound = Gdx.audio.newSound(Gdx.files.internal("sound/gameover.mp3"));
        sound.play(1F);
		
		this.game = game;
		viewport = new FitViewport(MainGame.V_WIDTH/6, MainGame.V_HEIGHT/6, new OrthographicCamera());
		stage = new Stage(viewport, ((MainGame) game).batch);
		
		Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
		
		Table table = new Table();
		table.center();
		table.setFillParent(true);
		
		Label gameOverLabel = new Label ("GAME OVER", font);
		Label playAgainLabel = new Label ("Click Screen to Play Again", font);
		Label showScore = new Label (showScore(), font);
		table.add(gameOverLabel).expandX();
		table.row();
		table.add(playAgainLabel).expandX().padTop(10f);
		table.row();
		table.add(showScore).expandX();
		stage.addActor(table);
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}
	
	public String showScore() {
		return "Your score is: " + PlayScreen.score;
	}

	@Override
	public void render(float delta) {
		
		if(Gdx.input.justTouched()) {
			
			MPServer.server.stop();
			game.setScreen(new MultiplayerMenuScreen(( MainGame )game));

		
			try {
				new MPServer();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			dispose();
		}
		
		Gdx.gl.glClearColor(0,  0,  0 , 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.draw();
	}
	

	@Override
	public void resize(int width, int height) {
			
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
		stage.dispose();
	}

}
