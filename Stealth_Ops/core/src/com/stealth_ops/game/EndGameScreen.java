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

public class EndGameScreen implements Screen{
    final Stealth_Ops game;
	static private int WIDTH = 720;
	static private int HEIGHT = 405;

	private Texture endGame;
	private Music endGame_music;
	private OrthographicCamera endGame_camera;
	private Viewport endGame_Port;

    public EndGameScreen(final Stealth_Ops passed_game) {
		game = passed_game;
		endGame_camera = new OrthographicCamera();
		endGame_camera.setToOrtho(false, WIDTH, HEIGHT);
		endGame_Port = new FitViewport(WIDTH, HEIGHT, endGame_camera);
	
		endGame = new Texture(Gdx.files.internal("end_game.jpg"));

		endGame_music = Gdx.audio.newMusic(Gdx.files.internal("mgs-success.mp3"));
		endGame_music.setLooping(false);
        endGame_music.setVolume(0.4f);
		endGame_music.play();
	}

    @Override
	public void render(float delta){
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		endGame_camera.update();
		game.batch.setProjectionMatrix(endGame_camera.combined);
		game.batch.begin();

		game.batch.draw(endGame,0,0, WIDTH, HEIGHT);
		game.txt.draw(game.batch, "You win!", WIDTH/2 - 35, HEIGHT - 50);
		game.txt.draw(game.batch, "Press enter to restart!", WIDTH/2 - 65, 50);
		game.txt.draw(game.batch, "Esc to finish the game", 10, 400);
		game.batch.end();
		
		// If player activates the game, dispose of this menu.
		if(Gdx.input.isKeyPressed(Keys.ENTER)){ 
			game.setScreen(new PlayScreen(game));
			dispose();
		}
		else if(Gdx.input.isKeyPressed(Keys.ESCAPE)){
			dispose();
			game.batch.dispose();
        	game.txt.dispose();
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
        endGame_Port.update(width, height);
    }

    @Override
    public void dispose() {
        endGame_music.dispose();
        endGame.dispose();
    }
}