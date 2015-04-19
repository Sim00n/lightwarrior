package com.puzdrowski.lightwarrior.entity;

import com.puzdrowski.lightwarrior.LightWarrior;

public class Player extends Entity {

	public static final String NAME = "Player";
	public static final float PLAYER_START_X = 40f;
	public static final float PLAYER_START_Y = 700f;
	public static final float DEFAULT_COOLDOWN = 10f;
	
	private LightWarrior game;
	private float cooldown;
	private float lightTime;
	private int score = 0;
	private int lives = 3;
	private int health = 100;
	
	public Player(float x, float y, LightWarrior game) {
		super(NAME, x, y);
		this.game = game;
		cooldown = 0f;
		health = 100;
	}

	public float getCooldown() {
		return cooldown;
	}

	public void setCooldown(float cooldown) {
		this.cooldown = cooldown;
	}
	
	public boolean isCooled() {
		return cooldown == 0f;
	}
	
	public boolean isOnCooldown() {
		return cooldown > 0.0f;
	}
	
	public float getLightTime() {
		return lightTime;
	}

	public void setLightTime(float lightTime) {
		this.lightTime = lightTime;
	}
	
	public void score(int i) {
		score += i;
	}
	
	public int getScore() {
		return score;
	}

	public int getLives() {
		return lives;
	}
	
	public void setLives(int lives) {
		this.lives = lives;
	}
	
	public void die() {
		lives--;
		if(lives < 0) {
			lives = 0;
		} else {
			health = 100;
		}
	}
	
	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
	
	public void addHP(int hp) {
		health += hp;
		if(health <= 0) {
			game.death();
		}
	}
	
	public void update(float dt) {
		if(cooldown > 0f) {
			cooldown -= dt;
		}
		if(cooldown < 0f) {
			cooldown = 0f;
		}
		
		if(lightTime > 0f) {
			lightTime -= dt;
		}
		if(lightTime < 0f) {
			lightTime = 0f;
		}
	}
}
