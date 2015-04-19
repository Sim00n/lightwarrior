package com.puzdrowski.lightwarrior.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class AudioManager {

	public static Music bg_slow = Gdx.audio.newMusic(Gdx.files.internal("sound/slow.mp3"));
	public static Music bg_medium = Gdx.audio.newMusic(Gdx.files.internal("sound/medium.mp3"));
	public static Music bg_fast = Gdx.audio.newMusic(Gdx.files.internal("sound/fast.mp3"));
	public static Music bg_complex = Gdx.audio.newMusic(Gdx.files.internal("sound/complex.mp3"));

	public static Sound coin = Gdx.audio.newSound(Gdx.files.internal("sound/coin.wav"));
	public static Sound life = Gdx.audio.newSound(Gdx.files.internal("sound/life.wav"));
	//public static Sound explosion = Gdx.audio.newSound(Gdx.files.internal("sound/explosion.wav"));
	public static Sound explosion = Gdx.audio.newSound(Gdx.files.internal("sound/explosion2.wav"));
	public static Sound collision = Gdx.audio.newSound(Gdx.files.internal("sound/collision.wav"));
	public static Sound door = Gdx.audio.newSound(Gdx.files.internal("sound/door.wav"));
	public static Sound death = Gdx.audio.newSound(Gdx.files.internal("sound/death.wav"));
	
}