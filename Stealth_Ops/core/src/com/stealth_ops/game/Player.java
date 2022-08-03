package com.stealth_ops.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class Player extends Rectangle{
    protected Texture img_back, img_front, img_left, img_right, img_to_show;
    protected Float speed;

    public Player(int x, int y, float speed){
        super(x, y, 0, 0);
        this.speed = speed;
        this.img_back = new Texture("player_back.png");
        this.img_front = new Texture("player_front.png");
        this.img_right = new Texture("player_right.png");
        this.img_left = new Texture("player_left.png");
        this.img_to_show = img_back;
        super.setWidth(img_to_show.getWidth());
        super.setHeight(img_to_show.getHeight());
    } 

    public Texture getImg(){
        return img_to_show;
    }
    public float getImgW(){
        return img_to_show.getWidth(); 
    }
    public float getImgH(){
        return img_to_show.getHeight();
    }

    public void move(){
        if(Gdx.input.isKeyPressed(Keys.A)){ 
            super.x -= this.speed * Gdx.graphics.getDeltaTime();
            this.img_to_show = this.img_left;
        }
      	if(Gdx.input.isKeyPressed(Keys.D)){
            super.x += this.speed * Gdx.graphics.getDeltaTime();
            this.img_to_show = this.img_right;
        }
        if(Gdx.input.isKeyPressed(Keys.W)){
            super.y += this.speed * Gdx.graphics.getDeltaTime();
            this.img_to_show = this.img_back;
        }
		if(Gdx.input.isKeyPressed(Keys.S)){ 
            super.y -= this.speed * Gdx.graphics.getDeltaTime();
            this.img_to_show = this.img_front;
        }
        if(super.x < 0) super.x = 0;
    	if(super.x > 1920 - super.width) super.x = 1920 - super.width;
		if(super.y < 0) super.y = 0;
    	if(super.y > 1080 - super.height) super.y = 1080 - super.height;
	
    }

    public void dispose(){
        img_back.dispose();
        img_front.dispose();
        img_left.dispose();
        img_right.dispose();
    }
}