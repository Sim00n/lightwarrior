package com.puzdrowski.lightwarrior.level;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class MyContactListener implements ContactListener {

	private boolean playerOnGround;
	private boolean footOnGround;
	
	// called when two fixtures start to collide
	public void beginContact(Contact c) {
		
		Fixture fa = c.getFixtureA();
		Fixture fb = c.getFixtureB();
				
		if(fa.getUserData() != null && fa.getUserData().equals("foot")) {
			if(fb.getUserData() != null && fb.getUserData().equals("floor")) {
				footOnGround = true;
			}
		}
		if(fb.getUserData() != null && fb.getUserData().equals("foot")) {
			if(fa.getUserData() != null && fa.getUserData().equals("floor")) {
				footOnGround = true;
			}
		}
		
		if(fa.getUserData() != null && fa.getUserData().equals("player")) {
			if(fb.getUserData() != null && fb.getUserData().equals("floor")) {
				playerOnGround = true;
			}
		}
		if(fb.getUserData() != null && fb.getUserData().equals("player")) {
			if(fa.getUserData() != null && fa.getUserData().equals("floor")) {
				playerOnGround = true;
			}
		}
		
		/*if(footOnGround) {
			AudioManager.collision.play();
		}*/
		
	}
	
	// called when two fixtures no longer collide
	public void endContact(Contact c) {
		
		Fixture fa = c.getFixtureA();
		Fixture fb = c.getFixtureB();
		
		if(fa.getUserData() != null && fa.getUserData().equals("foot")) {
			if(fb.getUserData() != null && fb.getUserData().equals("floor")) {
				footOnGround = false;
			}
		}
		if(fb.getUserData() != null && fb.getUserData().equals("foot")) {
			if(fa.getUserData() != null && fa.getUserData().equals("floor")) {
				footOnGround = false;
			}
		}
		
		if(fa.getUserData() != null && fa.getUserData().equals("player")) {
			if(fb.getUserData() != null && fb.getUserData().equals("floor")) {
				playerOnGround = false;
			}
		}
		if(fb.getUserData() != null && fb.getUserData().equals("player")) {
			if(fa.getUserData() != null && fa.getUserData().equals("floor")) {
				playerOnGround = false;
			}
		}
	}
	
	public boolean isPlayerOnGround() { return playerOnGround || footOnGround; }
	public void preSolve(Contact c, Manifold m) {}
	public void postSolve(Contact c, ContactImpulse ci) {}
}
