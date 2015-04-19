package com.puzdrowski.lightwarrior.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Enemy extends Entity {

	public static final String NAME = "Enemy";
	protected Texture texture;
	protected BodyDef bdef;
	protected World world;
	protected Body enemyBody;
	protected PolygonShape shape;
	protected FixtureDef fdef;
	protected float tileSize;

	
	public Enemy(Vector2 pos, World world) {
		super(NAME, pos.x, pos.y);
		this.world = world;
		initialize();
	}
	
	public Enemy(float x, float y, World world) {
		super(NAME, x, y);
		this.world = world;
		initialize();
	}	
	
	private void initialize() {
		bdef = new BodyDef();
		fdef = new FixtureDef();
		shape = new PolygonShape();
		texture = new Texture(Gdx.files.internal("textures/enemies/blue_front.png"));
		tileSize = texture.getWidth();
		
		// Enemy
		bdef.position.set(this.getPosition());
		bdef.type = BodyType.DynamicBody;
		bdef.allowSleep = false;
		enemyBody = world.createBody(bdef);
		bdef.allowSleep = true;
		
		shape.setAsBox(14, 10);
		fdef.shape = shape;
		enemyBody.createFixture(fdef).setUserData("enemy");
		
		
	}
	
	public void render(Batch batch) {
		batch.draw(texture, enemyBody.getPosition().x - tileSize / 2, enemyBody.getPosition().y - tileSize / 2);
	}
		
	public void update(float dt, Body player) {
		float dx = player.getPosition().x - this.getPosition().x;
		float dy = player.getPosition().y - this.getPosition().y;
		
		enemyBody.setLinearVelocity(dx + world.getGravity().x, dy + world.getGravity().y);
	}
}
