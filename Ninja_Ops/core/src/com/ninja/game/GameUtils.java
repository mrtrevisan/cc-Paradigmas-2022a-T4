package com.ninja.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

public class GameUtils {
    public static boolean check_collision(Player player, Enemy enemy){
        if ((player.getX() < enemy.getX() + enemy.getWidth()) &&
            (player.getX() + player.getWidth() > enemy.getX()) &&
            (player.getY() < enemy.getY() + enemy.getHeight()) &&
            (player.getY() + player.getHeight() > enemy.getY())) {
            return true;
        } else return false;
    }

    public static void camera_move(Player player, OrthographicCamera camera){
        Vector3 position = camera.position;
        position.x = player.getX();
        position.y = player.getY();
        camera.position.set(position);
    }
}