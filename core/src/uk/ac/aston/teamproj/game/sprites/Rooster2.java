package uk.ac.aston.teamproj.game.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import uk.ac.aston.teamproj.game.MainGame;
import uk.ac.aston.teamproj.game.screens.PlayScreen;


/** 
 *	 Created by Chan on 02.11.2020
 */

/**
 * 	 Edited by Parmo on 04.11.2020
 *   	Mods: state(s), rendering of appropriate texture region, animations, collision sensor, category and mask
 *   	New methods: update(), getFrame(), getState()
 */

public class Rooster2 extends Sprite {
	
	//enum variable storing different states
	public enum State {
		FALLING,
		JUMPING,
		STANDING,
		RUNNING
	}
	public State currentState;
	public State previousState;
	private float stateTimer; //keeps track of the amount of time we stay in a state.
							  //Decides what frame gets pulled from an animation  
	
	public World world; // The world chicken is going to live in.
	public Body b2body; // box2d body
	
	private TextureRegion roosterStand; //region containing the "idle" rooster
	private Animation roosterRun;
	private Animation roosterJump;
	private boolean runningRight;
	
	
	@SuppressWarnings("unchecked")
	public Rooster2(World world, PlayScreen screen) {
		super(screen.getAtlas().findRegion("chicken_colours")); //pass the required texture region to the superclass
		this.world = world;
		defineRooster();
		
		//set the rooster to be associated with this texture region
		roosterStand = new TextureRegion(getTexture(), 7*48, 2*48, 48, 48);
		setBounds(0, 0, 16/MainGame.PPM, 16/MainGame.PPM);
		setRegion(roosterStand);
		
		//initialize state
		currentState = State.STANDING;
		previousState = State.STANDING;
		stateTimer = 0;
		runningRight = true;
		
		//initialize run animation
		Array<TextureRegion> frames = new Array<>(); //store textures for this animation in an array
		for (int i = 6; i < 9; i++) {
			TextureRegion tr = new TextureRegion(getTexture(), i*48, 2*48, 48, 48);
			frames.add(tr);
		}
		roosterRun = new Animation(0.1f, frames); //0.1f = duration of each image frame
		frames.clear();
		
		//initialize jump animation
		{
			frames.add(new TextureRegion(getTexture(), 7*48, 2*48, 48, 48));
			//frames.add(new TextureRegion(getTexture(), 48*7, 6*48, 48, 48));
			//frames.add(new TextureRegion(getTexture(), 48*7, 2*48, 48, 48));
		}
		roosterJump = new Animation(0.1f, frames);
		frames.clear();
	}
	
	public void update (float dt) { //dt = delta time
		// The box2D body coordinate system starts at the center of the fixture. 
		// Need to move the texture region to the bottom left-hand corner.
		setPosition(b2body.getPosition().x - getWidth()/2, //move by half the width of the x axis
				b2body.getPosition().y - getHeight()/2); //move by half the height of the y axis 
		
		
		setRegion(getFrame(dt));
	}
	
	//returns the appropriate frame that we need to display as the sprite texture region
	public TextureRegion getFrame (float dt) {
		//to return the appropriate frame, check the current state
		currentState = getState();
		
		TextureRegion region;
		switch(currentState) {
			case JUMPING:
				region = (TextureRegion) roosterJump.getKeyFrame(stateTimer); 
				break;
			case RUNNING:
				region = (TextureRegion) roosterRun.getKeyFrame(stateTimer, true);
				break;
			case FALLING:
			case STANDING:
			default:
				region = roosterStand;
				break;
		}
		
		//if rooster is running left and the region is not facing the left, then FLIP it over.
		if ((b2body.getLinearVelocity().x<0 || !runningRight) && !region.isFlipX()) {
			region.flip(true, false);
			runningRight = false;
		} else if ((b2body.getLinearVelocity().x>0 || runningRight) && region.isFlipX()) {
			region.flip(true, false);
			runningRight = true;
		}
		
		// if current state does not equal the previous state, than we must have transitioned to a new
		// state, so we need to reset the state timer
		stateTimer = (currentState == previousState)? stateTimer + dt : 0;
		previousState = currentState;
		return region;
	}
	
	public State getState() {
		if (b2body.getLinearVelocity().y > 0 //rooster is going up
			|| (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING)) //rooster was jumping and is now in descent phase 
				return State.JUMPING;
		else if (b2body.getLinearVelocity().y < 0) //rooster is falling
			return State.FALLING;
		else if (b2body.getLinearVelocity().x != 0)
			return State.RUNNING;
		else
			return State.STANDING;
	}
	
	public void defineRooster() {
		BodyDef bdef = new BodyDef();
		bdef.position.set(100 / MainGame.PPM, 32 / MainGame.PPM);
		bdef.type = BodyDef.BodyType.DynamicBody;
		b2body = world.createBody(bdef);
				
		FixtureDef fdef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(6 / MainGame.PPM);
		
		//set category bit (what this fixture is) and mask bit (what this fixture can collide with)
		fdef.filter.categoryBits = MainGame.ROOSTER_BIT;
		fdef.filter.maskBits = MainGame.DEFAULT_BIT | MainGame.BRICK_BIT | MainGame.BOMB_BIT;
		
		fdef.shape = shape;
		b2body.createFixture(fdef);
		
		// create a sensor on rooster's beak to detect collision
		EdgeShape beak = new EdgeShape(); //edgeshape is basically a line between 2 points
		beak.set(new Vector2( 7 / MainGame.PPM,  3 / MainGame.PPM),
				 new Vector2( 7 / MainGame.PPM, -3 / MainGame.PPM));
		fdef.shape = beak;
		fdef.isSensor = true; //it's not a colliding object, but just a sensor
		
		b2body.createFixture(fdef).setUserData("beak"); //uniquely identifies this fixture as "beak"
	}
	
	public float getPositionX() {
		return b2body.getPosition().x;
	}
}
