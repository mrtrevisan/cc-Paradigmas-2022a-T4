package com.ninja.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Enemy extends Rectangle {
    protected Texture img;
    protected float speed;

    public Enemy(float x, float y, float speed){
        super(x, y, 0, 0);
        this.speed = speed;
        img = new Texture("enemy_back.png");
        super.setWidth(img.getWidth());
        super.setHeight(img.getHeight());
    } 
    public Texture getImg(){
        return img;
    }

    public void dispose(){
        img.dispose();
    }
}