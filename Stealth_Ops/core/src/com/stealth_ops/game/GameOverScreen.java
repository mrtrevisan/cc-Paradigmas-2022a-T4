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



public class GameOverScreen implements Screen {
    final Stealth_Ops game;
	static private int WIDTH = 1920;
	static private int HEIGHT = 1080;

	private Texture gameOver;
	private Music gameOver_music;
	private OrthographicCamera gameOver_camera;
	private Viewport gameOver_Port;

    public GameOverScreen(final Stealth_Ops passed_game) {
		game = passed_game;
		gameOver_camera = new OrthographicCamera();
		gameOver_camera.setToOrtho(false, WIDTH, HEIGHT);
		gameOver_Port = new FitViewport(WIDTH, HEIGHT, gameOver_camera);
	
		gameOver = new Texture(Gdx.files.internal("game_over.jpg"));

		gameOver_music = Gdx.audio.newMusic(Gdx.files.internal("mgs-game_over.mp3"));
		gameOver_music.setLooping(true);
        gameOver_music.setVolume(0.2f);
		gameOver_music.play();
	}

    @Override
	public void render(float delta){
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		gameOver_camera.update();
		game.batch.setProjectionMatrix(gameOver_camera.combined);
		game.batch.begin();

		game.batch.draw(gameOver,0,0);
		game.txt.draw(game.batch, "GAME OVER!", WIDTH/2, 400);
		game.txt.draw(game.batch, "Press enter to restart!", WIDTH/2, HEIGHT/2);
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
    @Override
    public void resize(int width, int height) {
        //updated our game viewport
        gameOver_Port.update(width, height);
    }

    @Override
    public void dispose() {
        gameOver_music.dispose();
        gameOver.dispose();
        game.batch.dispose();
        game.txt.dispose();
    }
}