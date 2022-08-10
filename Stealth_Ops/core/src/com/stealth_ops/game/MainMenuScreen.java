package com.stealth_ops.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MainMenuScreen implements Screen {
	final Stealth_Ops game;
	static private int WIDTH = 720;
	static private int HEIGHT = 405;

	private Texture menu, manual;
	private Music menu_music;
	private OrthographicCamera camera;
	private Viewport menuPort;
	private boolean manual_menu;
	
	public MainMenuScreen(final Stealth_Ops passed_game) {
		game = passed_game;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, WIDTH, HEIGHT);
		menuPort = new FitViewport(WIDTH, HEIGHT, camera);
	
		menu = new Texture(Gdx.files.internal("main_menu.jpg"));
		manual = new Texture(Gdx.files.internal("manual.png"));

		menu_music = Gdx.audio.newMusic(Gdx.files.internal("Silence.mp3"));
		menu_music.setLooping(true);
		menu_music.play();

		manual_menu = false;
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		
		if (!manual_menu){
			game.batch.draw(menu,0,0, WIDTH, HEIGHT);
			game.txt.draw(game.batch, "Welcome to Stealth_Ops!!", 500, 400);
			game.txt.draw(game.batch, "Press enter to start the game!", 420, 80);
			game.txt.draw(game.batch, "Press H to show controls menu", 420, 110);
			game.txt.draw(game.batch, "Press ESC at any time to finish the game", 10, 400);
			game.batch.end();
		
			// If player activates the game, dispose of this menu.
			if(Gdx.input.isKeyJustPressed(Keys.ENTER)){ 
				game.setScreen(new PlayScreen(game));
				dispose();
			}
			if (Gdx.input.isKeyPressed(Keys.H)){
				manual_menu = true;
			}
		} else {
			game.batch.draw(manual ,0,0, WIDTH, HEIGHT);
			game.txt.draw(game.batch, "Press ESC at any time to finish the game", 10, 400);
			game.txt.draw(game.batch, "Press enter to go back to title menu", WIDTH / 2 - 60, 20);
			game.batch.end();

			if(Gdx.input.isKeyJustPressed(Keys.ENTER)){ 
				manual_menu = false;
			}
		}
		
		if(Gdx.input.isKeyPressed(Keys.ESCAPE)){
			dispose();
			game.txt.dispose();
			game.batch.dispose();
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
	@Override
    public void resize(int width, int height) {
        //updated our game viewport
        menuPort.update(width, height);
    }

	@Override
	public void dispose() {
		menu_music.dispose();
		menu.dispose();
		manual.dispose();
	}
}