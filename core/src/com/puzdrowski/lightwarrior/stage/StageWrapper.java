package com.puzdrowski.lightwarrior.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.puzdrowski.lightwarrior.LightWarrior;

public class StageWrapper {

	LightWarrior game;
	Stage stage;
	Skin skin;
	Pixmap pixmap;
	Table table;
	
	public StageWrapper(LightWarrior game) {
		this.game = game;
		initialize();
	}
	
	protected void initialize() {
		skin = new Skin(Gdx.files.internal("libgdx/uiskin.json"));
		stage = new Stage();
		skin.add("default", new BitmapFont());
		
		table = new Table();
		table.setFillParent(true);
		stage.addActor(table);
		
		elements();
	}
	
	public void elements() {}
	
	public void render() {
		stage.draw();
	}
	
	public void update(float dt) {
		stage.act(dt);
	}
	
	public Stage getStage() {
		return stage;
	}
}
