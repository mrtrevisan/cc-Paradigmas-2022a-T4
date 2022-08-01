package com.ninja.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
//import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
//import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


import java.util.PriorityQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class PlayScreen implements Screen{
    //Reference to our Game, used to set Screens
    final NinjaOps game;
    private TextureAtlas atlas;
    public static boolean alreadyDestroyed = false;

    //basic playscreen variables
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Hud hud;

    //Tiled map variables
    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    OrthographicCamera camera;
	Player player;
	Enemy enemy;
	int detected;

/*

    //Box2d variables
    private World world;
    private Box2DDebugRenderer b2dr;
    private B2WorldCreator creator;

*/

    private Music music;




    public PlayScreen(NinjaOps game_passed){
        // atlas = new TextureAtlas("Mario_and_Enemies.pack");
        this.game = game_passed;
        //create cam used to follow ninja through cam world
        gamecam = new OrthographicCamera();
        camera = new OrthographicCamera();
        //create a FitViewport to maintain virtual aspect ratio despite screen size
        gamePort = new FitViewport(NinjaOps.V_LAR, NinjaOps.V_ALT, gamecam);
        
        camera = new OrthographicCamera();
      	camera.setToOrtho(false, 720, 405); 		
		player = new Player(0, 0, 100);
		enemy = new Enemy(100, 100, 50, 'N', 120d, 0);
		detected = 0;
        //Load our map and setup our map renderer

        
        maploader = new TmxMapLoader();
        map = maploader.load("maptest.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        
        //create our game HUD for scores/timers/level info
        hud = new Hud(game.batch, this);


        //initially set our gamcam to be centered correctly at the start of of map
        // gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

    }

    public TextureAtlas getAtlas(){
        return atlas;
    }

    @Override
    public void show() {


    }

    public void handleInput(float dt){
        
        if(Gdx.input.isTouched()){
            gamecam.position.x += 100 * dt;
        }

    }

    public void update(float dt){
        //handle user input first
        handleInput(dt);
        //update our gamecam with correct coordinates after changes
        gamecam.update();
        //tell our renderer to draw only what our camera can see in our game world.
        renderer.setView(gamecam);
        hud.update(dt, detected);
    }


    @Override
    public void render(float delta) {
        //separate our update logic from render
        update(delta);

        //Clear the game screen with Black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //render our game map
        renderer.render();

        

        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();

        game.batch.draw(player.getImg(), player.getX(), player.getY());
		game.batch.draw(enemy.getImg(), enemy.getX(), enemy.getY());
		//game.txt.draw(game.batch, "detected: " + detected, 20, 20);

        game.batch.end();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
        hud.updateDetection(detected);


        player.move();
		GameUtils.camera_move(player, gamecam);
		if (GameUtils.check_collision(player, enemy) || GameUtils.check_fov(player, enemy)) {
			detected = 1;
		} else detected = 0;
        
        //ScreenUtils.clear(1, 0, 0, 1);
    }


    @Override
    public void resize(int width, int height) {
        //updated our game viewport
        gamePort.update(width,height);

    }

    public TiledMap getMap(){
        return map;
    }


    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        //dispose of all our opened resources
        map.dispose();
        renderer.dispose();

    }

}