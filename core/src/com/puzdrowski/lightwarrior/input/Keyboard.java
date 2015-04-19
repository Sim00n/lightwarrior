package com.puzdrowski.lightwarrior.input;

public class Keyboard {

	public static boolean[] keys;
	public static boolean[] pkeys;
	
	public static final int NUM_KEYS = 10;
	
	public static final int UP = 0;
	public static final int LEFT = 1;
	public static final int RIGHT = 2;
	
	// Deprecated for now
	public static final int GRAVITY_UP = 3;
	public static final int GRAVITY_DOWN = 4;
	public static final int GRAVITY_LEFT = 5;
	public static final int GRAVITY_RIGHT = 6;
	
	public static final int DROP_LIGHT = 7;
	
	public static final int CHEAT = 8;
	public static final int MENU = 9;
	
	static {
		keys = new boolean[NUM_KEYS];
		pkeys = new boolean[NUM_KEYS];
	}
	
	public static void setKey(int i, boolean b) {
		keys[i] = b;
	}
	
	public static void update() {
		for(int i = 0; i < keys.length; i++) {
			pkeys[i] = keys[i];
		}
	}
	
	public static boolean isDown(int i) {
		return keys[i];
	}
	
	public static boolean isPressed(int i) {
		return keys[i] && !pkeys[i];
	}
}
