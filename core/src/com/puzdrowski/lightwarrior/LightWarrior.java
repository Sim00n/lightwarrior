package com.puzdrowski.lightwarrior;

import box2dLight.RayHandler;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.puzdrowski.lightwarrior.entity.Player;
import com.puzdrowski.lightwarrior.input.InputProcessor;
import com.puzdrowski.lightwarrior.input.Keyboard;
import com.puzdrowski.lightwarrior.level.GenericLevel;
import com.puzdrowski.lightwarrior.level.Level;
import com.puzdrowski.lightwarrior.utils.AudioManager;

public class LightWarrior extends ApplicationAdapter {
	
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 768;
	public static final String TITLE = "Light Warrior";
	public static final int MAX_COINS = 772;
		
	public static enum STATE { PLAY, GAMEOVER, WINNER, PAUSE };
	public static STATE state = STATE.PLAY;
	public static STATE prevState = STATE.PLAY;
	
	public static float MUSIC_LEVEL = 0.5f;
	public static float EFFECTS_LEVEL = 1.0f;

	private static final float STEP = 1 / 60f;
	private float accum;
	
	private Player player;
	private Level level;
	private int levelNumber = 0;
	
	@Override
	public void create () {
		Gdx.input.setInputProcessor(new InputProcessor());
		RayHandler.setGammaCorrection(true);
		RayHandler.useDiffuseLight(true);
		player = new Player(Player.PLAYER_START_X, Player.PLAYER_START_Y, this);
		nextLevel();
	}

	@Override
	public void render () {
		accum += Gdx.graphics.getDeltaTime();
		while(accum >= STEP) {
			accum -= STEP;
			update(STEP);
			draw();
		}
	}
	
	public void nextLevel() {
		levelNumber++;
		if(levelNumber == 1)
			level = new GenericLevel(this, player, "textures/tilemap.tmx", 0f, -500f, new Color(0.2f, 0.2f, 0.2f, 0.5f));
		if(levelNumber == 2)
			level = new GenericLevel(this, player, "textures/tilemap2.tmx", 0f, -500f, new Color(0.09f, 0.09f, 0.09f, 0.5f));
		if(levelNumber == 3)
			level = new GenericLevel(this, player, "textures/tilemap3.tmx", 0f, -500f, new Color(0.05f, 0.05f, 0.05f, 0.7f));
		if(levelNumber == 4)
			level = new GenericLevel(this, player, "textures/tilemap4.tmx", 0f, -500f, new Color(0.05f, 0.05f, 0.08f, 0.2f));
		if(levelNumber == 5)
			level = new GenericLevel(this, player, "textures/tilemap5.tmx", 0f, -500f, new Color(0.05f, 0.05f, 0.08f, 0.2f));
		if(levelNumber == 6)
			level = new GenericLevel(this, player, "textures/tilemap6.tmx", 0f, -500f, new Color(0.05f, 0.05f, 0.08f, 0.2f));
		if(levelNumber == 7)
			level = new GenericLevel(this, player, "textures/tilemap7.tmx", 0f, -500f, new Color(0.05f, 0.05f, 0.08f, 0.2f));
		if(levelNumber == 8)
			level = new GenericLevel(this, player, "textures/tilemap8.tmx", 0f, -500f, new Color(0.02f, 0.02f, 0.02f, 0.2f));
		if(levelNumber == 9)
			level = new GenericLevel(this, player, "textures/tilemap9.tmx", 0f, -500f, new Color(0.02f, 0.02f, 0.02f, 0.2f));
		if(levelNumber == 10)
			level = new GenericLevel(this, player, "textures/tilemap10.tmx", 0f, -500f, new Color(0.01f, 0.01f, 0.01f, 0.2f));
		if(levelNumber > 10)
			winnerWinnerChickenDinner();
	}
	
	public void death() {
		long id = AudioManager.death.play();
		AudioManager.death.setVolume(id, LightWarrior.EFFECTS_LEVEL);
		
		if(player.getLives() > 0){
			level.playerBody.setTransform(new Vector2(Player.PLAYER_START_X, Player.PLAYER_START_Y), 0);
			level.getPlayer().die();
		} else {
			level.currentMusic.stop();
			state = STATE.GAMEOVER;
			
			level.currentMusic.stop();
			level.currentMusic = AudioManager.bg_slow;
			level.currentMusic.setVolume(LightWarrior.MUSIC_LEVEL);
			level.currentMusic.play();
		}
	}
	
	public void winnerWinnerChickenDinner() {
		level.currentMusic.stop();
		level.currentMusic = AudioManager.bg_fast;
		level.currentMusic.setVolume(LightWarrior.MUSIC_LEVEL);
		level.currentMusic.play();
		state = STATE.WINNER;
	}
	
	public void restart() {
		level.currentMusic.stop();
		Gdx.input.setInputProcessor(new InputProcessor());
		state = STATE.PLAY;
		
		player = new Player(Player.PLAYER_START_X, Player.PLAYER_START_Y, this);
		levelNumber = 0;
		nextLevel();
		for(int i = 0; i < Keyboard.NUM_KEYS; i++) {
			Keyboard.keys[i] = false;
			Keyboard.pkeys[i] = false;
		}
	}
	
	private void update(float dt) {
		if(state == STATE.PLAY)
			level.update(dt);
		if(state == STATE.PAUSE)
			level.pauseStage.update(dt);
		player.update(dt);
	}
	
	private void draw() {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		level.render();
	}
	
	public Level getLevel() {
		return level;
	}
}
