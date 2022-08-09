package com.stealth_ops.game;

public class OurPoint {
    
    protected float x;
    protected float y;

    public OurPoint(float x, float y){
        this.x = x;
        this.y = y;
    }
    public OurPoint() {
        this.x = 0;
        this.y = 0;
    }
    public float getX(){
        return x;
    }
    public float getY(){
        return y;
    }
    public void setLocation(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
