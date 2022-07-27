package com.ninja.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;


public class NinjaOps extends ApplicationAdapter {
	SpriteBatch batch;
	OrthographicCamera camera;
	Player player;
	Enemy enemy;
	int detected;
	BitmapFont txt;
	Thread t1;

	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
      	camera.setToOrtho(false, 720, 405);		
		player = new Player(0, 0, 100);
		enemy = new Enemy(100, 100, 50, 'N', 120d); 
		txt = new BitmapFont();
		detected = 0;
		t1 = new Thread(new EnemyMove(enemy, 1));
		t1.start();
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		camera.update();	
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(player.getImg(), player.getX(), player.getY());
		batch.draw(enemy.getImg(), enemy.getX(), enemy.getY());
		txt.draw(batch, "detected: " + detected, 20, 20);
		batch.end();


		player.move();
		GameUtils.camera_move(player, camera);
		if (GameUtils.check_collision(player, enemy) || GameUtils.check_fov(player, enemy)) {
			detected = 1;
		} else detected = 0;
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		player.dispose();
		enemy.dispose();

		txt.dispose();
	}
}
