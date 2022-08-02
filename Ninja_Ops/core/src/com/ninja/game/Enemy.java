package com.ninja.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.Gdx;

public class Enemy extends Rectangle {
    protected Texture img_back, img_front, img_left, img_right, img_to_show;
    protected float speed;
    protected char face_direction;
    protected double fov_angle;
    protected int control;

    public Enemy(float x, float y, float speed, char fd, double fa){
        super(x, y, 0, 0);
        this.speed = speed;
        this.face_direction = fd;
        this.fov_angle = fa;
        this.control = 0;
        img_back = new Texture("enemy_back.png");
        img_front = new Texture("enemy_front.png");
        img_left = new Texture("enemy_left.png");
        img_right = new Texture("enemy_right.png");
        img_to_show = img_back;
        super.setWidth(img_to_show.getWidth());
        super.setHeight(img_to_show.getHeight());

    } 
    public void setDirection(char dir){
        this.face_direction = dir;
    }

    public Texture getImg(){
        return img_to_show;
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

    public void move(char dir){
        switch (dir){
            case 'N':
                this.img_to_show = img_back;
                this.face_direction = 'N';
                this.y += this.speed * Gdx.graphics.getDeltaTime();
                break;
            case 'S':
                this.img_to_show = img_front;
                this.face_direction = 'S';
                this.y -= this.speed * Gdx.graphics.getDeltaTime();
                break;
            case 'O':
                this.img_to_show = img_left;
                this.face_direction = 'O';
                this.x -= this.speed * Gdx.graphics.getDeltaTime();
                break;
            case 'L':
                this.img_to_show = img_right;
                this.face_direction = 'L';
                this.x += this.speed * Gdx.graphics.getDeltaTime();
                break;
        }
    }

    public void move_alpha(){
        if (control / 500 == 0) {
            this.move('L');
            control++;
        } else {
            this.move('O');
            control++;
        }
        if(control == 999) control = 0;
    }

    public void dispose(){
        img_back.dispose();
        img_front.dispose();
        img_left.dispose();
        img_right.dispose();
    }
}