package com.stealth_ops.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

public class MainMenuScreen implements Screen {
	final Stealth_Ops game;
	static private int WIDTH = 720;
	static private int HEIGHT = 405;

	private Texture menu;
	private Music menu_music;
	private OrthographicCamera camera;
	
	public MainMenuScreen(final Stealth_Ops passed_game) {
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

		game.batch.draw(menu,0,0);
		game.txt.draw(game.batch, "Welcome to Stealth_Ops!!", 100, 150);
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

	public void hide(){
        
    }
	public void resume(){

	}
	public void pause(){
		
	}
	public void show(){
		
	}
	public void resize(int l, int w){
		
	}

	@Override
	public void dispose() {
		menu_music.dispose();
		menu.dispose();
	}
}