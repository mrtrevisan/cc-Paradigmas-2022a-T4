package com.stealth_ops.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Player extends Rectangle{
    protected Texture img_back, img_front, img_left, img_right, img_to_show;
    protected float speed;
    protected float desloc;

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

    public void move(ArrayList<Rectangle> walls){
        desloc = this.speed * Gdx.graphics.getDeltaTime();

        if(Gdx.input.isKeyPressed(Keys.A)){
            this.img_to_show = this.img_left;
            if (!GameUtils.check_wall_collision(this, super.x - desloc, super.y, walls))
                super.x -= desloc;
        }
      	if(Gdx.input.isKeyPressed(Keys.D)){
            this.img_to_show = this.img_right;
            if (!GameUtils.check_wall_collision(this, super.x + desloc, super.y, walls))
                super.x += desloc;
        }
        if(Gdx.input.isKeyPressed(Keys.W)){
            this.img_to_show = this.img_back;
            if (!GameUtils.check_wall_collision(this, super.x, super.y + desloc, walls))
                super.y += desloc;
        }
		if(Gdx.input.isKeyPressed(Keys.S)){ 
            this.img_to_show = this.img_front;
            if (!GameUtils.check_wall_collision(this, super.x, super.y - desloc, walls))
                super.y -= desloc;
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