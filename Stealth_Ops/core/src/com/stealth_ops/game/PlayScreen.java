package com.stealth_ops.game;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
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
    protected TextureAtlas atlas;

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
/* 
    //Box2d variables
    protected World world;
    protected Box2DDebugRenderer b2dr;

    protected BodyDef bdef;
    protected PolygonShape shape;
    protected FixtureDef fdef;
    protected Body body;    
*/
    protected Music music;

    public PlayScreen(Stealth_Ops game_passed){
        // atlas = new TextureAtlas("Mario_and_Enemies.pack");
        this.game = game_passed;

        //create cam used to follow player through the map
        gamecam = new OrthographicCamera();
        //create a FitViewport to maintain virtual aspect ratio despite screen size
        gamePort = new FitViewport(Stealth_Ops.V_LAR, Stealth_Ops.V_ALT, gamecam);
        //creates the player
		player = new Player(850, 0, 100);
        //creates the enemies
		enemies = new ArrayList<Enemy>();
        enemies.add(new Enemy(900, 375, 25, 200, 'N', 90d));

		detected = 0;

        //Load our map and setup our map renderer
        maploader = new TmxMapLoader();
        map = maploader.load("fase_alpha.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        
        //create our game HUD 
        hud = new Hud(game.batch, this);
        //objects lists
        walls = new ArrayList<Rectangle>();
        doors = new ArrayList<Rectangle>();

/* 
        world = new World(new Vector2(0,0), true);
        b2dr = new Box2DDebugRenderer();
        bdef = new BodyDef(); 
        shape = new PolygonShape();
        fdef = new FixtureDef();

        //player
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2);
        body = world.createBody(bdef);
        shape.setAsBox(player.getWidth() / 2, player.getHeight() / 2);
        fdef.shape = shape;
        body.createFixture(fdef);
*/
        //walls
        for(MapObject object: map.getLayers().get(0).getObjects().getByType(RectangleMapObject.class)){
            Rectangle retangulo = ((RectangleMapObject) object).getRectangle();
            walls.add(retangulo);
/* 
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(retangulo.getX() + retangulo.getWidth()/2, retangulo.getY() + retangulo.getHeight() /2);
            body = world.createBody(bdef);
            shape.setAsBox(retangulo.getWidth() /2, retangulo.getHeight() / 2);
            fdef.shape = shape;
            body.createFixture(fdef);
*/
        }
        //door
        for(MapObject object: map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)){
            Rectangle retangulo = ((RectangleMapObject) object).getRectangle();
            doors.add(retangulo);
/* 
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(retangulo.getX() + retangulo.getWidth()/2, retangulo.getY() + retangulo.getHeight() /2);
            body = world.createBody(bdef);
            shape.setAsBox(retangulo.getWidth() /2, retangulo.getHeight() / 2);
            fdef.shape = shape;
            body.createFixture(fdef);
*/
        }
    
       // world.setContactListener(new WorldContactListener());
    }

    
    public void enemy_move_alpha(ArrayList<Enemy> enemies){
        Iterator<Enemy> iter = enemies.iterator();
        iter.next().move_alpha();
    }
    
    public void update(float dt){
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
        
        //render our BOX2DDebuglines
        //b2dr.render(world, gamecam.combined);
        
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
        hud.updateDetection(detected);
        
        //player, enemy and camera movement
        player.move(walls);
        enemy_move_alpha(enemies);
		GameUtils.camera_move(player, gamecam);
        
        //check detection for game-over
		if (GameUtils.check_detection(player, enemies)) {
            detected = 1;
		} else detected = 0;
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
    
    public TextureAtlas getAtlas(){
        return atlas;
    }

    public TiledMap getMap(){
        return map;
    }
    
    @Override
    public void dispose() {
        //dispose of all our opened resources
        map.dispose();
        renderer.dispose();
        hud.dispose();
        player.dispose();
        for (Enemy enemy : enemies) {
            enemy.dispose();
        }
    }
}