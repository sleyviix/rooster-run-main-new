package uk.ac.aston.teamproj.game.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import uk.ac.aston.teamproj.game.MainGame;
import uk.ac.aston.teamproj.game.screens.PlayScreen;

//public class PlayerProgressBar implements Disposable {
//	
//	private Stage stage;
//	private Viewport viewport;
//	private ProgressBar bar;
//	
//	public PlayerProgressBar(SpriteBatch sb) {
//		Skin skin = new Skin();
//		Pixmap pixmap = new Pixmap(10, 10, Format.RGBA8888);
//		pixmap.setColor(Color.WHITE);
//		pixmap.fill();
//		skin.add("white", new Texture(pixmap));
//
//		TextureRegionDrawable textureBar = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("new_graphics/mud.png"))));
//		textureBar.setMinSize(30, 30);
//		ProgressBarStyle barStyle = new ProgressBarStyle(skin.newDrawable("white", Color.LIGHT_GRAY), textureBar);
//		barStyle.knobBefore = barStyle.knob;
//		bar = new ProgressBar(1, 100, 0.1f, false, barStyle);
//		bar.setPosition(20, 360);
//		bar.setSize(400, bar.getPrefHeight());
//		bar.setAnimateDuration(2);
//		
//		viewport = new FitViewport(MainGame.V_WIDTH / 3, MainGame.V_HEIGHT / 3, new OrthographicCamera());
//		stage = new Stage(viewport, sb);
//
//		Table table = new Table();
//		table.top(); // put it at the top of our stage
//		table.setFillParent(true);
//		table.addActor(bar);
//
//		// Finally we need to add table to our stage
//		stage.addActor(table);
//	}
//
//	public void draw() {
//		stage.draw();
//		bar.setValue(bar.getValue() + 0.01f);
//	}
//	
//	@Override
//	public void dispose() {
//		stage.dispose();
//	}
//}

public class PlayerProgressBar implements Disposable {
	
	private Stage stage;
	private Viewport viewport;
	private ProgressBar bar;
	
	public PlayerProgressBar(SpriteBatch sb) {
		// background
		Pixmap pixmap = new Pixmap(100, 20, Format.RGBA8888);
		pixmap.setColor(Color.RED);
		pixmap.fill();
		
		TextureRegionDrawable bg = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
		pixmap.dispose();
		ProgressBarStyle progressBarStyle = new ProgressBarStyle();
		progressBarStyle.background = bg;
		
		// knob
		pixmap = new Pixmap(0, 20, Format.RGBA8888);
		pixmap.setColor(Color.GREEN);
		pixmap.fill();
		TextureRegionDrawable knob = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
		pixmap.dispose();		 
		progressBarStyle.knob = knob;
		 
		// knob before
		pixmap = new Pixmap(100, 20, Format.RGBA8888);
		pixmap.setColor(Color.GREEN);
		pixmap.fill();
		TextureRegionDrawable knobBefore = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
		pixmap.dispose();		 
		progressBarStyle.knobBefore = knobBefore;
		
		
		// progress bar
		bar = new ProgressBar(0.0f, 1.0f, 0.01f, false, progressBarStyle);
		bar.setValue(1.0f);
		bar.setAnimateDuration(0.25f);
		bar.setBounds(10, 10, 100, 20);
		
		// rest of code
		viewport = new FitViewport(MainGame.V_WIDTH / 3, MainGame.V_HEIGHT / 3, new OrthographicCamera());
		stage = new Stage(viewport, sb);

		Table table = new Table();
		table.top(); // put it at the top of our stage
		table.setFillParent(true);
		table.addActor(bar);

		// Finally we need to add table to our stage
		stage.addActor(table);
	}

	public void draw() {
		stage.draw();
		stage.act();
		//bar.setValue(bar.getValue() + 0.00000001f);
	}
	
	@Override
	public void dispose() {
		stage.dispose();
	}
}

