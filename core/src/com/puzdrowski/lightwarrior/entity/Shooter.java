package com.puzdrowski.lightwarrior.entity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class Shooter extends Enemy {
	
	public Shooter(Vector2 pos, World world) {
		super(pos, world);
	}
	
	@Override
	public void update(float dt, Body player) {
		super.update(dt, player);
	}
}
