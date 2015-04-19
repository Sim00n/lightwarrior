package com.puzdrowski.lightwarrior.entity;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

public class Entity {

	public String name;
	protected Vector2 position;
	public static enum ACTION { NEXT_LEVEL, DEATH, EXPLODE, COIN, LIFE };
	protected ACTION action;
	
	public Vector2 getPosition() {
		return position;
	}

	public void render(Batch batch) {
		
	}
	
	public void update(float dt) {
		
	}
	
	public void setPosition(Vector2 position) {
		this.position = position;
	}
	
	public ACTION getAction() {
		return action;
	}

	public void setAction(ACTION action) {
		this.action = action;
	}

	public Entity(String name) {
		this.name = name;
		this.position = new Vector2(0f, 0f);
	}
	
	public Entity(String name, float x, float y) {
		this.name = name;
		this.position = new Vector2(x, y);
	}	
}
