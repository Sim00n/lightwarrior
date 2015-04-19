package com.puzdrowski.lightwarrior.level;

import java.util.ArrayList;

import box2dLight.ConeLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.puzdrowski.lightwarrior.LightWarrior;
import com.puzdrowski.lightwarrior.entity.Enemy;
import com.puzdrowski.lightwarrior.entity.Entity;
import com.puzdrowski.lightwarrior.entity.Player;
import com.puzdrowski.lightwarrior.entity.Shooter;
import com.puzdrowski.lightwarrior.entity.Trigger;
import com.puzdrowski.lightwarrior.input.Keyboard;
import com.puzdrowski.lightwarrior.stage.GameOverStage;
import com.puzdrowski.lightwarrior.stage.HUDStage;
import com.puzdrowski.lightwarrior.stage.PauseStage;
import com.puzdrowski.lightwarrior.stage.WinnerStage;
import com.puzdrowski.lightwarrior.utils.AudioManager;
import com.puzdrowski.lightwarrior.utils.TempLight;
import com.puzdrowski.lightwarrior.utils.Utils;

public abstract class Level {

	private final float FLOOR_FRICTION = 0.4f;
	
	private Color ambientLevel;
	public Music currentMusic;
	
	private GameOverStage gameOverStage;
	private HUDStage hudStage;
	private WinnerStage winnerStage;
	public PauseStage pauseStage;
	
	private LightWarrior game;
	private Player player;

	private World world;
	private Box2DDebugRenderer b2dr;
	private RayHandler rayHandler;	
	
	private TiledMap tileMap;
	private String tileMapPath;
	private OrthographicCamera camera;
	private OrthogonalTiledMapRenderer tmr;
	private TiledMapTileLayer layer;

	private BodyDef bdef;
	private FixtureDef fdef;
	private PolygonShape shape;
	private MyContactListener cl;
	
	private ParticleEffectPool tntEffectPool;
	private ParticleEffectPool scepterEffectPool;
	private Array<PooledEffect> effects = new Array<PooledEffect>();
	
	private float scepterTickOffset = 0.0f; 
	
	public Body playerBody;
	private PointLight playerLight, scepterLight;
	private Texture playerFront, playerLeft, playerRight, playerFalling, playerFallingLeft, playerFallingRight;
	private Batch batch;
		
	private float gravityX, gravityY;
	
	float tileSize;
	public ArrayList<PointLight> staticLights;
	public ArrayList<ConeLight> rotatingLights;
	public ArrayList<ConeLight> harmonicLights; 
	public ArrayList<Entity> entities;
	public ArrayList<TempLight> temporaryLights;
	
	public ArrayList<Enemy> enemies;
	
	private float coneLightsDirections = 0f;
	private float rotatingLightsDirections = 0f;
	private float decreasingJump = 0f;
	
	public Level(LightWarrior game, Player player, String tileMapPath, float gravityX, float gravityY, Color ambientLevel) {
		this.game = game;
		this.player = player;
		this.tileMapPath = tileMapPath;
		this.gravityX = gravityX;
		this.gravityY = gravityY;
		this.ambientLevel = ambientLevel;
		
		staticLights = new ArrayList<PointLight>();
		rotatingLights = new ArrayList<ConeLight>();
		harmonicLights = new ArrayList<ConeLight>();
		entities = new ArrayList<Entity>();		
		enemies = new ArrayList<Enemy>();
		temporaryLights = new ArrayList<TempLight>();
		
		initialize();
	}
		
	public void initialize() {		
		// World
		world = new World(new Vector2(gravityX, gravityY), true);
		cl = new MyContactListener();
		world.setContactListener(cl);
		
		// Box2D Render
		b2dr = new Box2DDebugRenderer();
		b2dr.setDrawBodies(false);
		
		// Box2DLights
		rayHandler = new RayHandler(world);
		rayHandler.setShadows(true);
		rayHandler.setAmbientLight(ambientLevel);
		rayHandler.setBlurNum(2);
		
		// Box2D runtime structures
		bdef = new BodyDef();
		fdef = new FixtureDef();
		shape = new PolygonShape();
		
		// Camera(s)
		camera = new OrthographicCamera();
		camera.setToOrtho(false, LightWarrior.WIDTH, LightWarrior.HEIGHT);

		// Player's Textures
		playerFront = new Texture(Gdx.files.internal("textures/player_front.png"));
		playerLeft = new Texture(Gdx.files.internal("textures/player_left.png"));
		playerRight = new Texture(Gdx.files.internal("textures/player_right.png"));
		playerFalling = new Texture(Gdx.files.internal("textures/player_falling.png"));
		playerFallingLeft = new Texture(Gdx.files.internal("textures/player_falling_left.png"));
		playerFallingRight = new Texture(Gdx.files.internal("textures/player_falling_right.png"));
		batch = new SpriteBatch();
		
		// Player
		bdef.position.set(player.getPosition());
		bdef.type = BodyType.DynamicBody;
		bdef.allowSleep = false;
		playerBody = world.createBody(bdef);
		bdef.allowSleep = true;
		
		shape.setAsBox(14, 30);
		fdef.shape = shape;
		playerBody.createFixture(fdef).setUserData("player");
		
		// Foot sensor
		shape.setAsBox(8, 3, new Vector2(0, -32), 0);
		fdef.shape = shape;
		fdef.isSensor = true;
		playerBody.createFixture(fdef).setUserData("foot");
		
		// Player light
		playerLight = new PointLight(rayHandler, 30, new Color(0.2f, 0.4f, 0.55f, 1.f), 100, 0, 0);
		playerLight.attachToBody(playerBody);

		// Player particles
		ParticleEffect tntEffect = new ParticleEffect();
		tntEffect.load(Gdx.files.internal("particles/explosion.p"), Gdx.files.internal("particles"));
		tntEffectPool = new ParticleEffectPool(tntEffect, 1, 5);
		
		ParticleEffect scepterEffect = new ParticleEffect();
		scepterEffect.load(Gdx.files.internal("particles/scepter.p"), Gdx.files.internal("particles"));
		scepterEffectPool = new ParticleEffectPool(scepterEffect, 1, 5);
		
		// Tiled
		tileMap = new TmxMapLoader().load(tileMapPath);
		tmr = new OrthogonalTiledMapRenderer(tileMap);
		layer = (TiledMapTileLayer) tileMap.getLayers().get("second");
		tileSize = layer.getTileWidth();
		
		// Reading "second" level for box2d collisions
		for(int row = 0; row < layer.getHeight(); row++) {
			for(int col = 0; col < layer.getWidth(); col++) {
				Cell cell = layer.getCell(col, row);
				
				if(cell == null) continue;
				if(cell.getTile() == null) continue;
				
				bdef.type = BodyType.StaticBody;
				bdef.position.set((col + 0.5f) * tileSize, (row + 0.5f) * tileSize);
								
				ChainShape cs = new ChainShape();
				Vector2[] v = new  Vector2[5];
				v[0] = new Vector2(-tileSize / 2, -tileSize / 2);
				v[1] = new Vector2(-tileSize / 2, tileSize / 2);
				v[2] = new Vector2(tileSize / 2, tileSize / 2);
				v[3] = new Vector2(tileSize / 2, -tileSize / 2);
				v[4] = new Vector2(-tileSize / 2, -tileSize / 2);
				cs.createChain(v);
				
				fdef.friction = FLOOR_FRICTION;
				fdef.shape = cs;
				fdef.isSensor = false;
				world.createBody(bdef).createFixture(fdef).setUserData("floor");
			}
		}
		
		// Tiled
		layer = (TiledMapTileLayer) tileMap.getLayers().get("active");
		for(int row = 0; row < layer.getHeight(); row++) {
			for(int col = 0; col < layer.getWidth(); col++) {
				Cell cell = layer.getCell(col, row);
				
				if(cell == null) continue;
				if(cell.getTile() == null) continue;
				
				String actionProperty = (String) cell.getTile().getProperties().get("action");
				if(actionProperty != null) {
					if(actionProperty.equals("nextLevel")) {
						entities.add(new Trigger(new Vector2((col + 0.5f) * tileSize, (row + 0.5f) * tileSize), Entity.ACTION.NEXT_LEVEL));
					}
					if(actionProperty.equals("death")) {
						entities.add(new Trigger(new Vector2((col + 0.5f) * tileSize, (row + 0.5f) * tileSize), Entity.ACTION.DEATH));
					}
					if(actionProperty.equals("explode")) {
						entities.add(new Trigger(new Vector2((col + 0.5f) * tileSize, (row + 0.5f) * tileSize), Entity.ACTION.EXPLODE));
					}
					if(actionProperty.equals("coin")) {
						entities.add(new Trigger(new Vector2((col + 0.5f) * tileSize, (row + 0.5f) * tileSize), Entity.ACTION.COIN));
					}
					if(actionProperty.equals("life")) {
						entities.add(new Trigger(new Vector2((col + 0.5f) * tileSize, (row + 0.5f) * tileSize), Entity.ACTION.LIFE));
					}
				}
			}
		}
		
		layer = (TiledMapTileLayer) tileMap.getLayers().get("enemy");
		layer.setVisible(false);
		for(int row = 0; row < layer.getHeight(); row++) {
			for(int col = 0; col < layer.getWidth(); col++) {
				Cell cell = layer.getCell(col, row);
				
				if(cell == null) continue;
				if(cell.getTile() == null) continue;
				
				String actionProperty = (String) cell.getTile().getProperties().get("type");
				if(actionProperty != null) {
					if(actionProperty.equals("shooting")) {
						enemies.add(new Shooter(new Vector2((col + 0.5f) * tileSize, (row + 0.5f) * tileSize), world));
					}
				}
			}
		}
		
		levelLights(tileMap, rayHandler);
		
		// Stages
		gameOverStage = new GameOverStage(game);
		hudStage = new HUDStage(game);
		winnerStage = new WinnerStage(game);
		pauseStage = new PauseStage(game);
		
		currentMusic = AudioManager.bg_complex;
		currentMusic.setVolume(0.25f);
		currentMusic.setLooping(true);
		currentMusic.setVolume(LightWarrior.MUSIC_LEVEL);
		currentMusic.play();
	}
	
	public void levelLights(TiledMap tileMap, RayHandler rayHandler) {
		TiledMapTileLayer lights = (TiledMapTileLayer) tileMap.getLayers().get("lights");
		lights.setVisible(false);
		for(int row = 0; row < lights.getHeight(); row++) {
			for(int col = 0; col < lights.getWidth(); col++) {
				Cell cell = lights.getCell(col, row);
				
				if(cell == null) continue;
				if(cell.getTile() == null) continue;
										
				String colorProperty = (String) cell.getTile().getProperties().get("color");
				
				if(colorProperty != null) {
					if(colorProperty.equals("yellow")) {
						PointLight l = new PointLight(rayHandler, 30, new Color(0.96f, 0.93f, 0.1f, 1f), 250, (col + 0.5f) * tileSize, (row + 0.5f) * tileSize);
						staticLights.add(l);
					}
					if(colorProperty.equals("toxic")) {
						PointLight l = new PointLight(rayHandler, 30, new Color(0.96f, 0.93f, 0.4f, 10f), 175, (col + 0.5f) * tileSize, (row + 0.5f) * tileSize);
						staticLights.add(l);
					}
					if(colorProperty.equals("pink")) {
						PointLight l = new PointLight(rayHandler, 30, new Color(0.96f, 0.33f, 0.9f, 10f), 250, (col + 0.5f) * tileSize, (row + 0.5f) * tileSize);
						staticLights.add(l);
					}
					if(colorProperty.equals("red")) {
						PointLight l = new PointLight(rayHandler, 30, new Color(0.96f, 0.93f, 0.3f, 1f), 175, (col + 0.5f) * tileSize, (row + 0.5f) * tileSize);
						staticLights.add(l);
					}
					if(colorProperty.equals("black")) {
						PointLight l = new PointLight(rayHandler, 30, new Color(0.15f, 0.15f, 0.3f, 1f), 250, (col + 0.5f) * tileSize, (row + 0.5f) * tileSize);
						staticLights.add(l);
					}
					if(colorProperty.equals("blue")) {
						ConeLight cl = new ConeLight(rayHandler, 30, new Color(0.0f, 0.0f, 0.75f, 1f), 450, (col + 0.5f) * tileSize, (row + 0.5f) * tileSize, 250.3f, 30f);
						harmonicLights.add(cl);
					}
					if(colorProperty.equals("blue-circle")) {
						ConeLight cl = new ConeLight(rayHandler, 30, new Color(0.0f, 0.0f, 0.75f, 1f), 450, (col + 0.5f) * tileSize, (row + 0.5f) * tileSize, 250.3f, 45f);
						rotatingLights.add(cl);
					}
				} else {
					System.err.println("W level 2 brakuje definicji kolorow w properties tilesow.");
				}
			}
		}
	}
	
	public void render() {	
		tmr.setView(camera);
		tmr.render();
		Vector2 playerPos = playerBody.getPosition();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
			
		for(int i = effects.size - 1; i >= 0; i--) {
			PooledEffect effect = effects.get(i);
			effect.draw(batch, 1/45f);
		}
		
		for(int i = 0; i < enemies.size(); i++) {
			Entity e = enemies.get(i);
			e.render(batch);
		}
				
		if(playerBody.getLinearVelocity().y - world.getGravity().y != 0f) {
			if(playerBody.getLinearVelocity().x > 0)
				batch.draw(playerFallingRight, playerPos.x - tileSize/2, playerPos.y - tileSize);
			else if(playerBody.getLinearVelocity().x < 0)
				batch.draw(playerFallingLeft, playerPos.x - tileSize/2, playerPos.y - tileSize);
			else
				batch.draw(playerFalling, playerPos.x - tileSize/2, playerPos.y - tileSize);
		} else { 
			if(playerBody.getLinearVelocity().x > 0)
				batch.draw(playerRight, playerPos.x - tileSize/2, playerPos.y - tileSize);
			else if(playerBody.getLinearVelocity().x < 0)
				batch.draw(playerLeft, playerPos.x - tileSize/2, playerPos.y - tileSize);
			else
				batch.draw(playerFront, playerPos.x - tileSize/2, playerPos.y - tileSize);
		}
		
		batch.end();

		b2dr.render(world, camera.combined);
		rayHandler.setCombinedMatrix(camera.combined);
		rayHandler.updateAndRender();
		
		if(LightWarrior.state == LightWarrior.STATE.GAMEOVER) {
			gameOverStage.render();
		} else if(LightWarrior.state == LightWarrior.STATE.WINNER) {
			winnerStage.render();
		} else if(LightWarrior.state == LightWarrior.STATE.PLAY) {
			hudStage.render();
		} else if(LightWarrior.state == LightWarrior.STATE.PAUSE) {
			pauseStage.render();
		}
		
	}
	
	public void update(float dt) {
		world.step(1/45f, 6, 2);
		handleMovement();
		handleGravityChange();
		handleEntities();
		handleParticles();
		handlePlayerScepter(dt);
		handleLights();
		
		if(LightWarrior.state == LightWarrior.STATE.GAMEOVER) {
			Gdx.input.setInputProcessor(gameOverStage.getStage());
			gameOverStage.score = player.getScore();
			gameOverStage.update(dt);
		} else if(LightWarrior.state == LightWarrior.STATE.WINNER) {
			Gdx.input.setInputProcessor(winnerStage.getStage());
			winnerStage.score = player.getScore();
			winnerStage.update(dt);
		} else if(LightWarrior.state == LightWarrior.STATE.PAUSE) {
			Gdx.input.setInputProcessor(pauseStage.getStage());
			pauseStage.update(dt);
		}
		
		for(int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			e.update(dt, playerBody);
		}
		
		for(int i = 0; i < temporaryLights.size(); i++) {
			TempLight tl = temporaryLights.get(i);
			tl.update(dt);
			if(tl.isRemoved()) 
				temporaryLights.remove(tl);
		}
		
		hudStage.score = player.getScore();
		hudStage.lives = player.getLives();
		hudStage.hp = player.getHealth();
		hudStage.cooldown = (int) player.getCooldown();
		hudStage.update(dt);
	}
	
	public void handleLights() {
		// Harmonic Lights
		coneLightsDirections += 0.025f;
		if(coneLightsDirections > 360f)
			coneLightsDirections = 0;
		
		for(ConeLight cl : harmonicLights) {
			cl.setDirection((float) (Math.sin(coneLightsDirections) * 100) - 90f);
		}
		
		// Rotating Lights
		rotatingLightsDirections++;
		if(rotatingLightsDirections > 360f)
			rotatingLightsDirections = 0;
		for(ConeLight cl : rotatingLights) {
			cl.setDirection(rotatingLightsDirections);
		}
		
		if(player.getLightTime() == 0f) {
			if(scepterLight != null) {
				scepterLight.remove();
				scepterLight = null;
			}
		}
	}
	
	public void handlePlayerScepter(float dt) {
		if(!player.isOnCooldown()) {
			scepterTickOffset += dt;
			if(scepterTickOffset > 0.3f) {
				PooledEffect effect = scepterEffectPool.obtain();
				effect.setPosition(playerBody.getPosition().x + 16f, playerBody.getPosition().y + 16f);
				effect.start();
				effects.add(effect);
				scepterTickOffset = 0.0f;
			}
		}
	}
	
	public void handleParticles() {
		for(int i = effects.size - 1; i >= 0; i--) {
			PooledEffect effect = effects.get(i);
			if(effect.isComplete()) {
				effect.free();
				effects.removeIndex(i);
			}
		}
	}
	
	public void handleEntities() {
		for(int i = 0; i <  entities.size(); i++) {
			Entity e = entities.get(i);
			Vector2 pp = playerBody.getPosition();
			if(Utils.isPointInRangeOfPoint(pp, e.getPosition(), 30.0f)) {
				if(e.getAction() == Entity.ACTION.EXPLODE) {
					PooledEffect effect = tntEffectPool.obtain();
					effect.setPosition(e.getPosition().x, e.getPosition().y);
					effect.start();
					effects.add(effect);
										
					layer = (TiledMapTileLayer) tileMap.getLayers().get("active");
					Cell cell = layer.getCell((int) (e.getPosition().x / tileSize), (int) (e.getPosition().y / tileSize));
					if(cell == null) continue;
					if(cell.getTile() == null) continue;
					cell.setTile(null);
					entities.remove(i);
					
					player.addHP(-25);
					
					temporaryLights.add(new TempLight(3f, new PointLight(rayHandler, 10, new Color(1.0f, 1.0f, 1.0f, 1f), 300, e.getPosition().x, e.getPosition().y)));
					
					AudioManager.explosion.play(LightWarrior.EFFECTS_LEVEL);
				}
				if(e.getAction() == Entity.ACTION.COIN) {
					layer = (TiledMapTileLayer) tileMap.getLayers().get("active");
					Cell cell = layer.getCell((int) (e.getPosition().x / tileSize), (int) (e.getPosition().y / tileSize));
					if(cell == null) continue;
					if(cell.getTile() == null) continue;
					cell.setTile(null);
					entities.remove(i);
					player.score(1);
					
					AudioManager.coin.play(LightWarrior.EFFECTS_LEVEL);
				}
				if(e.getAction() == Entity.ACTION.NEXT_LEVEL) { 
					game.nextLevel();
					AudioManager.door.play(LightWarrior.EFFECTS_LEVEL);
				}
				if(e.getAction() == Entity.ACTION.LIFE) {
					layer = (TiledMapTileLayer) tileMap.getLayers().get("active");
					Cell cell = layer.getCell((int) (e.getPosition().x / tileSize), (int) (e.getPosition().y / tileSize));
					if(cell == null) continue;
					if(cell.getTile() == null) continue;
					cell.setTile(null);
					entities.remove(i);
					player.setLives(player.getLives()+1);
					
					AudioManager.life.play(LightWarrior.EFFECTS_LEVEL);
				}
			}
			if(Utils.isPointInRangeOfPoint(pp, e.getPosition(), 20.0f)) {
				if(e.getAction() == Entity.ACTION.DEATH)
					game.death();
			}
		}
	}
	
	public void handleMovement() {
		float dx = playerBody.getLinearVelocity().x + world.getGravity().x;
		float dy = playerBody.getLinearVelocity().y + world.getGravity().y;
		
		if(Keyboard.isPressed(Keyboard.LEFT)) {
			//if(cl.isPlayerOnGround()) {
				dx -= 1000f;
			//}
		}
		if(Keyboard.isPressed(Keyboard.RIGHT)) {
			//if(cl.isPlayerOnGround()) {
				dx += 1000f;
			//}
		}
		if(Keyboard.isPressed(Keyboard.UP)) {
			//if(cl.isPlayerOnGround()) {
				//dy += 1000f;
				//playerBody.applyForceToCenter(new Vector2(0, 1000f), true);
			//}
			if(playerBody.getLinearVelocity().y == 0) {
				decreasingJump = 400f;
			}
		}
		
		if(decreasingJump > 0f){ 
			dy = decreasingJump -= 8f;
			
		} else if(decreasingJump < 0f) {
			decreasingJump = 0f + world.getGravity().y;
		}
		
		playerBody.setLinearVelocity(new Vector2(dx, dy));
		
		if(Keyboard.isPressed(Keyboard.DROP_LIGHT)) {
			if(player.isCooled()) {
				//temporaryLights.add(new TempLight(5f, new PointLight(rayHandler, 100, new Color(0.5f, 0.9f, 0.5f, 0.7f), 200, playerBody.getPosition().x, playerBody.getPosition().y)));
				scepterLight = new PointLight(rayHandler, 30, new Color(0.6f, 0.6f, 0.6f, 3.f), 400, 0, 0);
				scepterLight.attachToBody(playerBody);
				player.setCooldown(Player.DEFAULT_COOLDOWN);;
				player.setLightTime(5f);
			}
		}
		
		if(Keyboard.isPressed(Keyboard.CHEAT)) {
			Keyboard.keys[Keyboard.CHEAT] = false;
			game.nextLevel();
		}
		
		if(Keyboard.isPressed(Keyboard.MENU)) {
			Keyboard.keys[Keyboard.MENU] = false;
			LightWarrior.prevState = LightWarrior.state;
			LightWarrior.state = LightWarrior.STATE.PAUSE;
		}
	}
	
	public void handleGravityChange() {
		/*if(Keyboard.isPressed(Keyboard.GRAVITY_UP))
			world.setGravity(new Vector2(world.getGravity().x, world.getGravity().y + 10f));
		if(Keyboard.isPressed(Keyboard.GRAVITY_DOWN))
			world.setGravity(new Vector2(world.getGravity().x, world.getGravity().y - 10f));
		if(Keyboard.isPressed(Keyboard.GRAVITY_LEFT))
			world.setGravity(new Vector2(world.getGravity().x - 10f, world.getGravity().y));
		if(Keyboard.isPressed(Keyboard.GRAVITY_RIGHT))
			world.setGravity(new Vector2(world.getGravity().x + 10f, world.getGravity().y));*/
	}
	
	
	public Player getPlayer() {
		return player;
	}
}
