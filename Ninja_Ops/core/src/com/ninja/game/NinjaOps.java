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


public class NinjaOps extends ApplicationAdapter {
	SpriteBatch batch;
	OrthographicCamera camera;
	Player player;
	Enemy enemy;
	int detected;
	

	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
      	camera.setToOrtho(false, 720, 405);		
		player = new Player(0, 0, 100);
		enemy = new Enemy(100, 100, 50); 
		detected = 0;
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		camera.update();	
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(player.getImg(), player.getX(), player.getY());
		batch.draw(enemy.getImg(), enemy.getX(), enemy.getY());
		batch.end();

		player.move();
		GameUtils.camera_move(player, camera);
		if (GameUtils.check_collision(player, enemy)) {
			System.out.println("detected");
		}
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		player.dispose();
		enemy.dispose();
	}
}
