package com.puzdrowski.lightwarrior.input;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;

public class InputProcessor extends InputAdapter {

	public boolean keyDown(int k) {
		// Movement
		if(k == Keys.W)
			Keyboard.setKey(Keyboard.UP, true);
		if(k == Keys.A)
			Keyboard.setKey(Keyboard.LEFT, true);
		if(k == Keys.D)
			Keyboard.setKey(Keyboard.RIGHT, true);
		
		// Gravity manipulation
		if(k == Keys.UP)
			Keyboard.setKey(Keyboard.GRAVITY_UP, true);
		if(k == Keys.DOWN)
			Keyboard.setKey(Keyboard.GRAVITY_DOWN, true);
		if(k == Keys.LEFT)
			Keyboard.setKey(Keyboard.GRAVITY_LEFT, true);
		if(k == Keys.RIGHT)
			Keyboard.setKey(Keyboard.GRAVITY_RIGHT, true);
		
		if(k == Keys.SPACE)
			Keyboard.setKey(Keyboard.DROP_LIGHT, true);
		
		if(k == Keys.GRAVE)
			Keyboard.setKey(Keyboard.CHEAT, true);
		if(k == Keys.ESCAPE)
			Keyboard.setKey(Keyboard.MENU, true);
		
		return true;
	}
	
	public boolean keyUp(int k) {
		// Movement
		if(k == Keys.W)
			Keyboard.setKey(Keyboard.UP, false);
		if(k == Keys.A) 
			Keyboard.setKey(Keyboard.LEFT, false);
		if(k == Keys.D) 
			Keyboard.setKey(Keyboard.RIGHT, false);
		
		// Gravity manipulation
		if(k == Keys.UP)
			Keyboard.setKey(Keyboard.GRAVITY_UP, false);
		if(k == Keys.DOWN)
			Keyboard.setKey(Keyboard.GRAVITY_DOWN, false);
		if(k == Keys.LEFT)
			Keyboard.setKey(Keyboard.GRAVITY_LEFT, false);
		if(k == Keys.RIGHT)
			Keyboard.setKey(Keyboard.GRAVITY_RIGHT, false);
		
		if(k == Keys.SPACE)
			Keyboard.setKey(Keyboard.DROP_LIGHT, false);
		
		if(k == Keys.GRAVE)
			Keyboard.setKey(Keyboard.CHEAT, false);
		if(k == Keys.ESCAPE)
			Keyboard.setKey(Keyboard.MENU, false);
		
		return true;
	}	
	
}
