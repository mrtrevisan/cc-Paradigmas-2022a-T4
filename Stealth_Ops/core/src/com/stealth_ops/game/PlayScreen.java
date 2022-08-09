package com.stealth_ops.game;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PlayScreen implements Screen{
    //Reference to our Game, used to set Screens
    final Stealth_Ops game;

    //basic playscreen variables
    protected OrthographicCamera gamecam;
    protected Viewport gamePort;
    protected Hud hud;

    //Tiled map variables
    protected TmxMapLoader maploader;
    protected TiledMap map;
    protected OrthogonalTiledMapRenderer renderer;

	protected Player player;
	protected ArrayList<Enemy> enemies;
    protected ArrayList<Rectangle> walls;
    protected ArrayList<Rectangle> doors;

	protected int detected;
    protected int success;
    protected int control;
    protected int sound_control;

    protected Music music;
    protected Sound rapaais, uui;

    public PlayScreen(Stealth_Ops game_passed){
        this.game = game_passed;

        //create cam used to follow player through the map
        gamecam = new OrthographicCamera();
        //create a FitViewport to maintain virtual aspect ratio despite screen size
        gamePort = new FitViewport(Stealth_Ops.V_LAR, Stealth_Ops.V_ALT, gamecam);
        //creates the player
		player = new Player(850, 0, 100);
        //creates the enemies
		enemies = new ArrayList<Enemy>();
        enemies.add(new Enemy(900, 375, 25, 200, 'S', 120d));

		detected = 0;
        success = 0;
        control = 0;
        sound_control = 0;

        //Load our map and setup our map renderer
        maploader = new TmxMapLoader();
        map = maploader.load("fase_alpha.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        
        //create our game HUD 
        hud = new Hud(game.batch, this);
        //music
        music = Gdx.audio.newMusic(Gdx.files.internal("mgs-vr.mp3"));
		music.setLooping(true);
		music.play();
        music.setVolume(0.2f);
        rapaais = Gdx.audio.newSound(Gdx.files.internal("rapaais.mp3"));
        uui = Gdx.audio.newSound(Gdx.files.internal("uui.mp3"));

        //objects lists
        walls = new ArrayList<Rectangle>();
        doors = new ArrayList<Rectangle>();

        //walls
        for(MapObject object: map.getLayers().get(0).getObjects().getByType(RectangleMapObject.class)){
            Rectangle retangulo = ((RectangleMapObject) object).getRectangle();
            walls.add(retangulo);
        }
        //doors
        for(MapObject object: map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)){
            Rectangle retangulo = ((RectangleMapObject) object).getRectangle();
            doors.add(retangulo);
        }

        Gdx.graphics.setContinuousRendering(false);
    }

    public void move_alpha(Enemy enemy){
        if (control < 650) {
            enemy.move('L');
            control++;
        } 
        else if (control == 650) {
            enemy.setDirection('N');
            control++;
        }
        else if (control < 750) {
            control++;
        }
        else if (control < 1500) {
            enemy.move('O');
            control++;
        }
        else if (control == 1500) {
            enemy.setDirection('S');
            control++;
        }
        else if(control < 1600 ) {
            control++;
        }
        else if (control < 1700) {
            enemy.move('L');
            control++;
        } 
        if (control == 1700) control = 0;
    }

    public void enemy_move_alpha(ArrayList<Enemy> enemies){
        Iterator<Enemy> iter = enemies.iterator();
        move_alpha(iter.next());
    }
    
    public void update(float dt){
        //update our gamecam with correct coordinates after changes
        gamecam.update();
        //tell our renderer to draw only what our camera can see in our game world.
        renderer.setView(gamecam);
        hud.update(dt, detected, success);
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
        
        //sprite batch
        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        //draw player
        game.batch.draw(player.getImg(), player.getX(), player.getY());
        //draw enemies
		for(Enemy enemy : enemies){
            game.batch.draw(enemy.getImg(), enemy.getX(), enemy.getY());
        }
        game.batch.end();
        
        //hud
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
        
        //player, enemy and camera movement
        if ((detected == 0) && (success == 0)){
            player.move(walls);
            enemy_move_alpha(enemies);
		    GameUtils.camera_move(player, gamecam);
        }
        //check detection for game-over
		if (GameUtils.check_detection(player, enemies, walls)) {
            detected = 1;
		}

        if (GameUtils.check_success(player, doors)) {
            success = 1;
        }

        if (success == 1) end_game();
        if (detected == 1) game_over();
        if ((success == 0) && (detected == 0)) Gdx.graphics.requestRendering();
    }

    public void game_over(){
        music.stop();
        if (sound_control == 0){
            rapaais.play();
            sound_control = 1;
        }
        game.setScreen(new GameOverScreen(game));
        dispose();
    }

    public void end_game(){
        music.stop();
        if (sound_control == 0){
            uui.play();
            sound_control = 1;
        }
        dispose();
    //    game.setScreen(new EndGameScreen(game));
    }

    @Override
    public void hide(){
        
    }
    
    @Override
    public void resume(){   
        
    }
    
    @Override
    public void pause(){
        
    }
    
    @Override
    public void show(){
        
    }
    
    @Override
    public void resize(int width, int height) {
        //updated our game viewport
        gamePort.update(width, height);
        
    }
    
    public TiledMap getMap(){
        return map;
    }
    
    @Override
    public void dispose() {
        //dispose of all our opened resources
        map.dispose();
        renderer.dispose();
        music.dispose();
        hud.dispose();
        player.dispose();
        rapaais.dispose();
        uui.dispose();
        for (Enemy enemy : enemies) {
            enemy.dispose();
        }
    }
}