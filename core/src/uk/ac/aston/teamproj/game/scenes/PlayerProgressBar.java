package uk.ac.aston.teamproj.game.scenes;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import uk.ac.aston.teamproj.game.MainGame;

public class PlayerProgressBar implements Disposable {
	
	private static final float MAP_SIZE = 500;
	private static final float BAR_WIDTH = 400;
	private static final float BAR_HEIGHT = 32;
	private static final float PLAYER_RADIUS = 30;
	
	private Stage stage;
	private Viewport viewport;
	
	private Image background;
	private Image player;
	
	private float playerProgressPos;
	
	public PlayerProgressBar(SpriteBatch sb) {
		viewport = new FitViewport(MainGame.V_WIDTH / 3, MainGame.V_HEIGHT / 3, new OrthographicCamera());
		stage = new Stage(viewport, sb);
		
		background = new Image(new Texture("progress_bar/grey_bar.png"));
		player = new Image(new Texture("progress_bar/rooster_new.png"));
		
		background.setColor(1f, 1f, 1f, 0.8f);
		background.setBounds(10, 370, BAR_WIDTH, BAR_HEIGHT);
		player.setColor(1f, 1f, 1f, 0.7f);		
	}

	public void draw() {
		player.setBounds(12 + playerProgressPos, 371f, PLAYER_RADIUS, PLAYER_RADIUS);
		Group group = new Group();
		group.addActor(background);
		group.addActor(player);
		stage.addActor(group);

		stage.draw();
		stage.act();
	}
	
	@Override
	public void dispose() {
		stage.dispose();
	}
	
	public void updateProgress(float position) {
		float actualPosition = (position * MainGame.PPM) / 100;
		float progress = (actualPosition * 100) / MAP_SIZE;
		// TODO fix
		
		this.playerProgressPos = (progress * (BAR_WIDTH - PLAYER_RADIUS/2)) / 100;
	}
	
}