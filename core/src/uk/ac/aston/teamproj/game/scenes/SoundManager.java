package uk.ac.aston.teamproj.game.scenes;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundManager implements Sound {

	public static boolean soundsPlaying = true;
	public static Sound[] sounds; 
	
	
	
	public SoundManager() {
	
		 
		sounds = new Sound[10];
		sounds[1] = Gdx.audio.newSound(Gdx.files.internal("pop.mp3"));
		sounds[2] = Gdx.audio.newSound(Gdx.files.internal("menu_click.mp3"));
		sounds[3] = Gdx.audio.newSound(Gdx.files.internal("lightening.mp3"));
		sounds[4] = Gdx.audio.newSound(Gdx.files.internal("gameover.mp3"));
		sounds[5] = Gdx.audio.newSound(Gdx.files.internal("firstplace.wav"));
		sounds[6] = Gdx.audio.newSound(Gdx.files.internal("electric-transition-super-quick-wwww.mp3"));
		sounds[7] = Gdx.audio.newSound(Gdx.files.internal("coin.wav"));
		sounds[8] = Gdx.audio.newSound(Gdx.files.internal("bomb.wav"));
	}
	
	
	
	public static void playSound(Sound sound) {
		if (soundsPlaying != false) {
		sound.play(1F);

		}
		
	}
	
	public static void SoundsOn() {
soundsPlaying = true;
	}
		
public static void SoundsOff() {
	 soundsPlaying = false;
	}
		
	@Override
	public long play() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long play(float volume) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long play(float volume, float pitch, float pan) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long loop() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long loop(float volume) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long loop(float volume, float pitch, float pan) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void stop() {
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
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop(long soundId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause(long soundId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume(long soundId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLooping(long soundId, boolean looping) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPitch(long soundId, float pitch) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setVolume(long soundId, float volume) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPan(long soundId, float pan, float volume) {
		// TODO Auto-generated method stub
		
	}

}
