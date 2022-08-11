package com.stealth_ops.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Enemy extends Rectangle {
    private Texture img_back, img_front, img_left, img_right, img_to_show;
    private float speed, sight;
    private char face_direction;
    protected double fov_angle;

    public Enemy(float x, float y, float speed, float sight, char fd, double fa){
        super(x, y, 0, 0);

        this.speed = speed;
        this.sight = sight;
        this.face_direction = fd;
        this.fov_angle = fa;

        this.img_back = new Texture("enemy_back.png");
        this.img_front = new Texture("enemy_front.png");
        this.img_left = new Texture("enemy_left.png");
        this.img_right = new Texture("enemy_right.png");
        update_image();

        super.setWidth(img_to_show.getWidth());
        super.setHeight(img_to_show.getHeight());
    } 

    public void update_image(){
        switch(this.face_direction){
            case 'N':
                this.img_to_show = img_back;
                break;
            case 'S':
                this.img_to_show = img_front;
                break;
            case 'L':
                this.img_to_show = img_right;
                break;
            case 'O':
                this.img_to_show = img_left;
                break;
            default:
                return;
        }
    }
    public void setDirection(char dir){
        this.face_direction = dir;
        update_image();
    }

    public Texture getImg(){
        return this.img_to_show;
    }

    public float getSpeed(){
        return this.speed;
    }

    public float getSight(){
        return this.sight;
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

    public void dispose(){
        img_back.dispose();
        img_front.dispose();
        img_left.dispose();
        img_right.dispose();
    }
}