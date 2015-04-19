package com.puzdrowski.lightwarrior.stage;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.puzdrowski.lightwarrior.LightWarrior;

public class HUDStage extends StageWrapper {
	
	private Label scoreLabel;
	private Label livesLabel;
	private Label hpLabel;
	private Label cooldownLabel;
	private Label hintLabel;
	public int score = 0;
	public int lives = 3;
	public int hp = 100;
	public int cooldown = 0;
	
	public HUDStage(LightWarrior game) {
		super(game);
	}
	
	@Override
	public void elements() {
		super.elements();
		
		Table internalTable = new Table();
		scoreLabel = new Label("Score: " + score, skin);
		scoreLabel.setColor(Color.WHITE);
		scoreLabel.setFontScale(1f);
		internalTable.add(scoreLabel).pad(2);		
		
		internalTable.row();
		
		livesLabel = new Label("Lives: " + lives, skin);
		livesLabel.setColor(Color.WHITE);
		livesLabel.setFontScale(1f);
		internalTable.add(livesLabel).pad(2);
		
		internalTable.row();
		
		hpLabel = new Label("Health: " + hp, skin);
		hpLabel.setColor(Color.WHITE);
		hpLabel.setFontScale(1f);
		internalTable.add(hpLabel).pad(2);
		
		internalTable.row();
		
		cooldownLabel = new Label("Cooldown: " + cooldown, skin);
		cooldownLabel.setColor(Color.WHITE);
		cooldownLabel.setFontScale(1f);
		internalTable.add(cooldownLabel).pad(2);
		
		internalTable.row();
		
		hintLabel = new Label("ESC for menu", skin);
		hintLabel.setColor(Color.GREEN);;
		hintLabel.setFontScale(0.9f);
		internalTable.add(hintLabel).pad(2);
		
		table.add(internalTable);
		table.setPosition(-LightWarrior.WIDTH/2 + 60f, LightWarrior.HEIGHT/2 - 80f);
		
	}
	
	@Override
	public void update(float dt) {
		super.update(dt);
		scoreLabel.setText("Score: " + score);
		livesLabel.setText("Lives: " + lives);
		hpLabel.setText("HP: " + hp);
		cooldownLabel.setText("Cooldown: " + cooldown);
	}
}

