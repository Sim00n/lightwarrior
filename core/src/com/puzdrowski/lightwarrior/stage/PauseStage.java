package com.puzdrowski.lightwarrior.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.puzdrowski.lightwarrior.LightWarrior;
import com.puzdrowski.lightwarrior.input.InputProcessor;

/*
 * 62
 * 59
 * 97
 * 77
 * 82
 * 89
 * 139
 * 56
 * 82
 * 29
 * ==
 * 772
 */

public class PauseStage extends StageWrapper {

	private Label intro1, intro2, intro3, intro4, intro5, intro6;
	private TextButton backButton;
	private Slider musicSlider, effectsSlider;
	private Label musicLabel, effectsLabel;
	
	public PauseStage(LightWarrior game) {
		super(game);
		initBack();
	}
		
	@Override
	public void elements() {
		super.elements();
		
		Window window = new Window("Pause", skin);
		window.align(Align.left);
		
		Table internalTable = new Table();
		internalTable.align(Align.left);
		
		Label emptyLabel = new Label(" ", skin);
		Label emptyLabel2 = new Label(" ", skin);
		Label emptyLabel3 = new Label(" ", skin);
		Label emptyLabel4 = new Label(" ", skin);
		Label emptyLabel5 = new Label(" ", skin);
		Label emptyLabel6 = new Label(" ", skin);
		Label emptyLabel7 = new Label(" ", skin);
		
		intro1 = new Label("Welcome to Light Warrior.", skin);
		internalTable.add(intro1);
		internalTable.row();
		internalTable.add(emptyLabel);
		internalTable.row();
		intro2 = new Label("Use WSAD to move.", skin);
		intro2.setColor(Color.CYAN);
		internalTable.add(intro2);
		internalTable.row();
		intro3 = new Label("Use SPACE to active your light scepter for 5 seconds. It has a cooldown of 10 seconds.", skin);
		intro3.setColor(Color.CYAN);
		internalTable.add(intro3);
		internalTable.row();
		internalTable.add(emptyLabel2);
		internalTable.row();
		intro4 = new Label("You can climb walls by jumping towards the wall while standing next to it.", skin);
		internalTable.add(intro4);
		internalTable.row();
		intro5 = new Label("Avoid barrels, spikes, lava, and toxins. Collect potatoe-lifes,", skin);
		internalTable.add(intro5);
		internalTable.row();
		intro6 = new Label("Collect coins and share your high score on the Ludum Dare website!", skin);
		internalTable.add(intro6);
		
		internalTable.row();
		internalTable.add(emptyLabel3);
		internalTable.row();
		
		musicLabel = new Label("Music", skin);
		internalTable.add(musicLabel);
		internalTable.row();
		musicSlider = new Slider(0, 1, 0.01f, false, skin);
		musicSlider.setAnimateDuration(0.3f);
		musicSlider.setValue(LightWarrior.MUSIC_LEVEL);
		musicSlider.setDisabled(false);
		internalTable.add(musicSlider);
		
		internalTable.row();
		internalTable.add(emptyLabel4);
		internalTable.row();
		
		effectsLabel = new Label("Effects", skin);
		internalTable.add(effectsLabel);
		internalTable.row();
		effectsSlider = new Slider(0, 1, 0.01f, false, skin);
		effectsSlider.setAnimateDuration(0.3f);
		effectsSlider.setValue(LightWarrior.EFFECTS_LEVEL);
		effectsSlider.setDisabled(false);
		internalTable.add(effectsSlider);
		
		internalTable.row();
		internalTable.add(emptyLabel5);
		internalTable.row();
		
		backButton = new TextButton("Back", skin);
		internalTable.add(backButton);
		
		internalTable.row();
		internalTable.add(emptyLabel6);
		internalTable.row();
		
		Label author = new Label("Author: Szymon Puzdrowski - tinyurl.com/puzdrowski", skin);
		internalTable.add(author);
		
		internalTable.row();
		internalTable.add(emptyLabel7);
		internalTable.row();
		
		
		
		window.add(internalTable.left());
		table.add(window);
		//table.setPosition(-GravityWarrior.WIDTH/2 + 60f, GravityWarrior.HEIGHT/2 - 60f);
	}
	
	public void initBack() {
		backButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				LightWarrior.state = LightWarrior.prevState;
				LightWarrior.prevState = LightWarrior.STATE.PAUSE;
				game.getLevel().currentMusic.pause();
				game.getLevel().currentMusic.setVolume(LightWarrior.MUSIC_LEVEL);
				game.getLevel().currentMusic.play();
				Gdx.input.setInputProcessor(new InputProcessor());
			}
		});
		
		musicSlider.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				LightWarrior.MUSIC_LEVEL = musicSlider.getValue();
			}
		});
		
		effectsSlider.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				LightWarrior.EFFECTS_LEVEL = effectsSlider.getValue();
			}
		});
		
	}
	
	@Override
	public void update(float dt) {
		super.update(dt);
		musicSlider.act(dt);
		effectsSlider.act(dt);
	}
}
