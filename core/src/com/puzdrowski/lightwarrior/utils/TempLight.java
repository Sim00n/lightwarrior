package com.puzdrowski.lightwarrior.utils;

import box2dLight.PointLight;

public class TempLight {

	public float timer = 0;
	public PointLight pl;
	private boolean removed = false;
	
	public TempLight(float time, PointLight pl) {
		this.timer = time;
		this.pl = pl;
	}
	
	public void update(float dt) {
		if(timer < 0) {
			pl.remove();
			timer = 0;
			removed = true;
		} else {
			timer -= dt;
		}
	}	
	
	public boolean isRemoved() {
		return removed;
	}
}
