package uk.ac.aston.teamproj.game.screens;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import uk.ac.aston.teamproj.game.MainGame;
import uk.ac.aston.teamproj.game.net.MPClient;
import uk.ac.aston.teamproj.game.net.MPServer;

public class MenuScreen implements Screen {
	private Viewport viewport;
	private Stage stage;
	
	@SuppressWarnings("unused")
	private MainGame game;
	
	private Label lbl_ip;
	private Label lbl_name;
	
	private LabelStyle lbl_style;
	
	private Skin txt_skin;
	
	private TextButtonStyle btn_style;
	
	private TextField txt_ip;
	public static TextField txt_name;
	
	private Button btn_confirm;
	
	public static String ip = "localhost"; // change with user input
	public static String name = "Player 1";

	
	public MenuScreen(MainGame game) {
		this.game = game;
		viewport = new FitViewport(MainGame.V_WIDTH/6, MainGame.V_HEIGHT/6, new OrthographicCamera());
		stage = new Stage(viewport, ((MainGame) game).batch);
		
		lbl_style = new Label.LabelStyle();
		lbl_style.font = new BitmapFont();
		
		txt_skin = new Skin(Gdx.files.internal("uiskin.json"));
		
		btn_style = new TextButton.TextButtonStyle();
		btn_style.font = new BitmapFont();
		
		Table table = new Table();
		table.top();
		table.setFillParent(true);
		
		lbl_ip = new Label("please enter an IP address:" , lbl_style);
		lbl_name = new Label("enter your name: " , lbl_style);
		
		txt_ip = new TextField("localhost", txt_skin);
		txt_name = new TextField(name, txt_skin);
		
		btn_confirm = new TextButton("confirm", btn_style);
		
		table.add(lbl_ip).expandX();
		table.add(txt_ip).width(200);
		table.row();
		table.add(lbl_name).expandX();
		table.add(txt_name).width(200);
		table.row();
		table.add(btn_confirm);
		table.row();
		
		stage.addActor(table);
		
		Gdx.input.setInputProcessor(stage);

	}
	
	private void buttonHandler() {
		if(Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
			/*
			game.setScreen(new PlayScreen(game));
			*/
			txt_ip.setTextFieldListener(new TextField.TextFieldListener() {
				
				@Override
				public void keyTyped(TextField textField, char c) {
					ip = textField.getText();
				}
			});
			txt_name.setTextFieldListener(new TextField.TextFieldListener() {
				
				@Override
				public void keyTyped(TextField textField, char c) {
					name = textField.getText();
				}
			});

			new MPClient(txt_ip.getText(), txt_name.getText(), game);
			dispose();
			/*
			 * Right now port and IP are predefined !!!!, ideally we want input from the users.
			 * This can be implemented later, for now I want to focus on the logic
			 */
		}
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0,  0,  0 , 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.draw();
		stage.act(delta);
		
		buttonHandler();
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
		stage.dispose();
	}

}
