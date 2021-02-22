package uk.ac.aston.teamproj.game.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import uk.ac.aston.teamproj.game.MainGame;
import uk.ac.aston.teamproj.game.screens.PlayScreen;

/**
 * 
 * Created by Junaid and Marcus
 *    
 *
 */

public class Hud2 implements Disposable {
	
	/*
	 * When our game world moves we want our hud to stay the same.
	 * We're going to use a new camera and a new viewport specifically for the hud. (It stays locked there and only renders that part of the screen).
	 */
	public Stage stage;
	private Viewport viewport;
	
	private Label nameFixedLabel;
	
	private Integer score;
	private Label scoreFixedLabel;
	private Label scoreLabel;
		
	private Integer coins;
	private Label coinsFixedLabel;
	private Label coinsLabel;
	
	private Integer lives;
	private Label livesFixedLabel;
	private Label livesLabel;
	
	public Hud2(SpriteBatch sb) {
		
		coins = 0;
		score = 0;
		lives = 3;
		
		viewport = new FitViewport(MainGame.V_WIDTH/3, MainGame.V_HEIGHT/3, new OrthographicCamera());
		/*
		 * reuse the sb.
		 */
		stage = new Stage(viewport, sb);
		
		Table table = new Table();
		table.top();// put it at the top of our stage //CHANGED
		table.setFillParent(true); // table is the same of our stage
		
		scoreLabel = new Label(String.format("\t%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		coinsLabel = new Label(String.format("\t%06d", coins), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		livesLabel = new Label(String.format("%01d", lives), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		
		nameFixedLabel = new Label("\t Player 2:", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		scoreFixedLabel = new Label("\tSCORE", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		coinsFixedLabel = new Label("\tCOINS", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		livesFixedLabel = new Label("\tLIVES", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

		table.row().padTop(50);
		table.add(nameFixedLabel).expandX();
		//table.add(scoreFixedLabel).expandX().padTop(80);
		//table.add(coinsFixedLabel).expandX().padTop(80);
		//table.add(livesFixedLabel).expandX().padTop(80);
		//table.row(); // Everything beyond this will be in a new row
		table.add(scoreLabel).expandX();
		table.add(coinsLabel).expandX();
		table.add(livesLabel).expandX().padRight(2);
//		System.out.println(table.add(nameFixedLabel).getFillX());
//		System.out.println(table.add(nameFixedLabel).getFillY());
//		
//		System.out.println(table.add(scoreLabel).getFillX());
//		System.out.println(table.add(scoreLabel).getFillY());
//		
//		System.out.println(table.add(coinsLabel).getFillX());
//		System.out.println(table.add(coinsLabel).getFillY());
		
//		System.out.println(table.add(livesLabel).getFillX());
//		System.out.println(table.add(livesLabel).getFillY());
		// Finally we need to add table to our stage
		stage.addActor(table);
	}
	
	public void updateScore() {
		score++;
		scoreLabel.setText(String.format("%06d", score));;	
	}
	
	public void updateLives() {
		if (PlayScreen.player2.isDead()) {
			lives = 0;
		} else {
			lives = PlayScreen.player2.getLives();
		}
		livesLabel.setText(String.format("%01d", lives));
	}
	
	public void updateCoins(int value) {
		PlayScreen.player2.updateCoins(value);
		coins = PlayScreen.player2.getCoins();
		coinsLabel.setText(String.format("%06d", coins));
	}

	@Override
	public void dispose() {
		stage.dispose();
	}

	public int getScore() {
		return score;
	}
	
	public int getCoins() {
		return coins;
	}
	
	
}
