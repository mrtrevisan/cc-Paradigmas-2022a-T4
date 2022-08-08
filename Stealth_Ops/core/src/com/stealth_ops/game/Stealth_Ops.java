package com.stealth_ops.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Stealth_Ops extends Game {
	public static final int V_LAR = 400;
	public static final int V_ALT = 200;

	public SpriteBatch batch;
	public BitmapFont txt;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		txt = new BitmapFont();
		//change the current screen to menu screen
		setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		super.dispose();
		batch.dispose();
		txt.dispose();
	}
}
