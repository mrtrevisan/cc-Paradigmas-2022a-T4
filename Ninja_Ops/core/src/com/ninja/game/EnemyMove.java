package com.ninja.game;

import com.badlogic.gdx.Gdx;

public class EnemyMove implements Runnable {
    protected int phaseToRun;
    protected Enemy enemy;

    public EnemyMove(Enemy enemy, int phase){
        this.phaseToRun = phase;
        this.enemy = enemy;
    }

    public void Move_phase1(){
        while (true) {
            enemy.setDirection('L');
            for (int i = 0; i < 50; i++){
                //right
                enemy.x = enemy.x += enemy.getSpeed() * Gdx.graphics.getDeltaTime();
            }
            enemy.setDirection('O');
            for (int i = 0; i < 50; i++){
                //left
                enemy.x = enemy.x -= enemy.getSpeed() * Gdx.graphics.getDeltaTime();
            }       
        }
    }

    public void run(){
        if(phaseToRun == 1) Move_phase1();
    }
}