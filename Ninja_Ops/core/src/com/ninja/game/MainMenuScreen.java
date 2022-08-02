package com.ninja.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.Input.Keys;
//import com.badlogic.gdx.audio.sound;



public class MainMenuScreen implements Screen {
	final NinjaOps game;
	static private int WIDTH = 720;
	static private int HEIGHT = 405;

	Texture menu;
	Music menu_music;
	
	OrthographicCamera camera;
	
	public MainMenuScreen(final NinjaOps passed_game) {
		game = passed_game;
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, WIDTH, HEIGHT);

		menu = new Texture(Gdx.files.internal("menu.jpg"));

		menu_music = Gdx.audio.newMusic(Gdx.files.internal("Silence.mp3"));

		menu_music.setLooping(true);
		menu_music.play();
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);
		
		game.batch.begin();
// ver se é assim que coloca fundo:
		game.batch.draw(menu,0,0);

		game.txt.draw(game.batch, "Welcome to NinjaOps!!", 100, 150);
		game.txt.draw(game.batch, "Press enter to start the game!", 100, 100);
		game.txt.draw(game.batch, "Esc to finish the game", 10, 400);
		game.batch.end();
		
		// If player activates the game, dispose of this menu.
		if(Gdx.input.isKeyPressed(Keys.ENTER)){ 
			game.setScreen(new PlayScreen(game));
			dispose();
		}
		else if(Gdx.input.isKeyPressed(Keys.ESCAPE)){
			dispose();
		}
	}


	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		menu_music.play();
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
	//	menu_music.dispose();
	//	menu.dispose();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}
}