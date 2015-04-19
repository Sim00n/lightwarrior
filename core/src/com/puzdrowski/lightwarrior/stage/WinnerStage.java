package com.puzdrowski.lightwarrior.stage;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.puzdrowski.lightwarrior.LightWarrior;

public class WinnerStage extends StageWrapper {

	private TextButton restartButton;
	private Label label;
	private Label scoreLabel;
	private Window window;
	public int score = 0;
	
	public WinnerStage(LightWarrior game) {
		super(game);
	}

	@Override
	protected void initialize() {
		super.initialize();
		initRestart();
	}
	
	@Override
	public void elements() {
		super.elements();
		
		window = new Window("Winner Winner Chicken Dinner!", skin);
		window.align(Align.center);
		
		Table internalTable = new Table();
		label = new Label("    Thanks for playing GravityWarrior    ", skin);
		label.setColor(Color.WHITE);
		label.setFontScale(1f);
		internalTable.add(label).pad(20);
		
		internalTable.row();
		
		scoreLabel = new Label("Your score: " + score + " / " + LightWarrior.MAX_COINS, skin);
		scoreLabel.setColor(Color.WHITE);
		scoreLabel.setFontScale(1f);
		internalTable.add(scoreLabel).pad(20);
		
		internalTable.row();
		
		
		restartButton = new TextButton("Beat me again?", skin);
		internalTable.add(restartButton);
		
		window.add(internalTable).pad(20);
		table.add(window);
	}
	
	public void initRestart() {
		restartButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.restart();
			}
		});
	}
	
	@Override
	public void update(float dt) {
		super.update(dt);
		scoreLabel.setText("Score: " + score + " / " + LightWarrior.MAX_COINS);
	}
	
}
