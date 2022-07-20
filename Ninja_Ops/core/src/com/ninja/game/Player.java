package com.ninja.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class Player extends Rectangle{
    protected Texture img;
    protected Float speed;
    //protected int x, y, width, height;

    public Player(int x, int y, float speed){
        super(x, y, 0, 0);
        this.speed = speed;
        img = new Texture("badlogic.jpg");
        super.setWidth(img.getWidth());
        super.setHeight(img.getHeight());
    } 
/*
    public int getX(){
        return super.x;
    }
    public int getY(){
        return y;
    } */
    public Texture getImg(){
        return img;
    }

 /*   public void setX(int x){
        super.x = x;
    }
    public void setY(int y){
        super.y = y;
    } 
*/
    public void move(){
        if(Gdx.input.isKeyPressed(Keys.LEFT)) super.x -= this.speed * Gdx.graphics.getDeltaTime();
      	if(Gdx.input.isKeyPressed(Keys.RIGHT)) super.x += this.speed * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Keys.UP)) super.y += this.speed * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Keys.DOWN)) super.y -= this.speed * Gdx.graphics.getDeltaTime();

        if(super.x < 0) super.x = 0;
    	if(super.x > 1920 - super.width) super.x = 1920 - super.width;
		if(super.y < 0) super.y = 0;
    	if(super.y > 1080 - super.height) super.y = 1080 - super.height;
	
    }

    public void dispose(){
        img.dispose();
    }
}