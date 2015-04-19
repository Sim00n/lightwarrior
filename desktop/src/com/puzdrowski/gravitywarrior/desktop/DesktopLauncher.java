package com.puzdrowski.gravitywarrior.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.puzdrowski.lightwarrior.LightWarrior;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = LightWarrior.WIDTH;
		config.height = LightWarrior.HEIGHT;
		config.title = LightWarrior.TITLE;
		new LwjglApplication(new LightWarrior(), config);
	}
}
