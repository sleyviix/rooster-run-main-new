package uk.ac.aston.teamproj.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import uk.ac.aston.teamproj.game.MainGame;

/** 
 * @author Parmo
 * @since 5.1.3
 * @date 13/12/2020
 */

public class MainMenuScreen implements Screen {
	
	private MainGame game;
	private Viewport viewport;
	private Stage stage;
	
	private TextureAtlas buttonsAtlas; //the sprite-sheet containing all buttons
	private Skin skin; //skin for buttons
	private ImageButton[] buttons;

	public MainMenuScreen(MainGame game) {
		this.game = game;
		viewport = new FitViewport(MainGame.V_WIDTH/6, MainGame.V_HEIGHT/6, new OrthographicCamera());
		stage = new Stage(viewport, ((MainGame) game).batch);
		
		buttonsAtlas = new TextureAtlas("buttons/buttons.pack");
		skin = new Skin(buttonsAtlas);
		buttons = new ImageButton[3];
		
		initializeButtons();		
		populateTable();		
	}
	
	private void initializeButtons() {		
		ImageButtonStyle style;
		
//		//Single player Button
//		style = new ImageButtonStyle();
//		style.up = skin.getDrawable("single_player_inactive");  //set default image
//		style.over = skin.getDrawable("single_player_active");  //set image for mouse over
//		
//		ImageButton singleBtn = new ImageButton(style);
//		singleBtn.addListener(new InputListener() {
//	            @Override
//	            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
//	                //do something
//	            	System.out.println("SINGLE");
//	            	MainMenuScreen.this.dispose();
//	            	game.setScreen(new PlayScreen(game, 0));
//	            	return true;
//	            }	       
//	    });
		
		
		//Multiplayer Button
		style = new ImageButtonStyle();
		style.up = skin.getDrawable("play_inactive");  //set default image
		style.over = skin.getDrawable("play_active");  //set image for mouse over
		
		ImageButton multiBtn = new ImageButton(style);
		multiBtn.addListener(new InputListener() {
	            @Override
	            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
	            	//do something
	            	//plays button sounds
	            	Sound sound = Gdx.audio.newSound(Gdx.files.internal("sound/pop.mp3"));
	                sound.play(1F);
	            	System.out.println("MULTI");
	            	MainMenuScreen.this.dispose();
	            	game.setScreen(new MultiplayerMenuScreen(game));
	            	return true;
	            }	       
	    });
		
		//Tutorial Button
		style = new ImageButtonStyle();
		style.up = skin.getDrawable("tutorial_inactive");  //set default image
		style.over = skin.getDrawable("tutorial_active");  //set image for mouse over
		

		ImageButton tutorialBtn = new ImageButton(style);
		tutorialBtn.addListener(new InputListener() {
	            @Override
	            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
	                //do something
	            	//plays button sounds
	            	Sound sound = Gdx.audio.newSound(Gdx.files.internal("sound/pop.mp3"));
	                sound.play(1F);
	            	System.out.println("TUTORIAL");
	            	Gdx.net.openURI("https://www.youtube.com/watch?v=wHyzCWOFR3A&ab_channel=RoosterRun");
	            	return true;
	            }	  
	            
	    });
		
		//Quit Button
		style = new ImageButtonStyle();
		style.up = skin.getDrawable("quit_inactive");  //set default image
		style.over = skin.getDrawable("quit_active");  //set image for mouse over
		
		ImageButton quitBtn = new ImageButton(style);
		quitBtn.addListener(new InputListener() {
	            @Override
	            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
	                //do something
	            	//plays button sounds
	            	Sound sound = Gdx.audio.newSound(Gdx.files.internal("sound/pop.mp3"));
	                sound.play(1F);
	            	System.out.println("QUIT");
	            	Gdx.app.exit();
	            	return true;
	            }	       
	    });
		
		//buttons[0] = singleBtn;
		buttons[0] = multiBtn;
		buttons[1] = tutorialBtn;
		buttons[2] = quitBtn;
	}
	
	private void populateTable() {
		Table table = new Table();		
		table.top();
		table.setFillParent(true);
		
		//draw the background
		Texture background = new Texture("buttons/main_menu_bg.jpg");
		table.background(new TextureRegionDrawable(new TextureRegion(background)));

		//draw all buttons
		ImageButton singleBtn = buttons[0];
		table.add(singleBtn).height(22f).width(120).pad(4).padLeft(200).padTop(60);
		table.row();
		for (int i = 1; i < buttons.length; i++) {
			ImageButton button = buttons[i];
			table.add(button).height(22f).width(120).pad(4).padLeft(200);
			table.row();
		} 
		
		stage.addActor(table);		
		Gdx.input.setInputProcessor(stage);
	}
	
	@Override
    public void show() {

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0f , 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.draw();
		stage.act(delta);	
	}


	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
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
		skin.dispose();
		buttonsAtlas.dispose();
	}
	
}