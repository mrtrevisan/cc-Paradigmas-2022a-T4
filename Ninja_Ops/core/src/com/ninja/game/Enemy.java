package com.ninja.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Enemy extends Rectangle {
    protected Texture img;
    protected float speed;
    protected char face_direction;
    protected double fov_angle;

    public Enemy(float x, float y, float speed, char fd, double fa){
        super(x, y, 0, 0);
        this.speed = speed;
        this.face_direction = fd;
        this.fov_angle = fa;
        img = new Texture("enemy_back.png");
        super.setWidth(img.getWidth());
        super.setHeight(img.getHeight());
    } 
    public void setDirection(char dir){
        this.face_direction = dir;
    }

    public Texture getImg(){
        return img;
    }

    public float getSpeed(){
        return this.speed;
    }

    public float getFOV_X(){
        switch(face_direction){
            case 'N':
            case 'S':
                return super.x;
            case 'L':
                return super.x + 5f;
            case 'O':
                return super.x - 5f;
            default:
                return 0f;
        }
    }
    public float getFOV_Y(){
        switch(face_direction){
            case 'L':
            case 'O':
                return super.y;
            case 'N':
                return super.y + 5f;
            case 'S': 
                return super.y -5f;
            default:
                return 0f;
        }
    }

    public void dispose(){
        img.dispose();
    }
}