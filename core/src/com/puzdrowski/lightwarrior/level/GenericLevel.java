package com.puzdrowski.lightwarrior.level;

import com.badlogic.gdx.graphics.Color;
import com.puzdrowski.lightwarrior.LightWarrior;
import com.puzdrowski.lightwarrior.entity.Player;

public class GenericLevel extends Level {

	public GenericLevel(LightWarrior game, Player player, String tileMapPath, float gravityX, float gravityY, Color ambientLevel) {
		super(game, player, tileMapPath, gravityX, gravityY, ambientLevel);
	}
}
