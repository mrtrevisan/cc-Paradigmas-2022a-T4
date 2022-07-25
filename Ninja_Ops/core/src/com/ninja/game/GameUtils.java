package com.ninja.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Vector2;
import java.lang.Math;

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

    public static double vector_angle(Vector2 pv, Vector2 ev){
        return Math.acos( (pv.dot(ev)) / (pv.len() * ev.len()) ); 
    }

    public static boolean check_fov(Player player, Enemy enemy){
        Vector2 player_v = new Vector2(0f, 0f);
        Vector2 enemy_v = new Vector2(0f, 0f);
        player_v.x = player.getX() - enemy.getX();
        player_v.y = player.getY() - enemy.getY();
        enemy_v.x = enemy.getFOV_X() - enemy.getX();
        enemy_v.y = enemy.getFOV_Y() - enemy.getY();
        if (( Math.toDegrees( vector_angle(player_v, enemy_v) ) < enemy.fov_angle / 2 ) && 
            ( Math.toDegrees( vector_angle(player_v, enemy_v) ) > -1 * enemy.fov_angle / 2 )) {
                return true;
        } else {
            return false;
        }

    }
}