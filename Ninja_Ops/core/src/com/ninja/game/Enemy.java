package com.ninja.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Enemy extends Rectangle {
    protected Texture img;
    protected float speed;
    protected float x, y;

    public Enemy(float x, float y, float speed){
        this.x = x;
        this.y = y;
        this.speed = speed;
        img = new Texture("badlogic.jpg");
    } 
    public float getX(){
        return x;
    }
    public float getY(){
        return y;
    }
    public Texture getImg(){
        return img;
    }

    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    } 

    public void dispose(){
        img.dispose();
    }
}