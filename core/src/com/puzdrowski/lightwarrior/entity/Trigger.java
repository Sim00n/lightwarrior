package com.puzdrowski.lightwarrior.entity;

import com.badlogic.gdx.math.Vector2;

public class Trigger extends Entity {

	public static final String NAME = "Door";
	
	public Trigger(Vector2 position, ACTION action) {
		super(NAME);
		this.position = position;
		this.action = action;
	}
}
