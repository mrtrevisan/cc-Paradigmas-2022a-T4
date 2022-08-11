package com.stealth_ops.game;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class GameUtils {
    final static int player_ajuste = 25;
    public static boolean check_collision(Player player, Enemy enemy){
        if ((player.getX() < enemy.getX() + enemy.getWidth() - player_ajuste) &&
            (player.getX() + player.getWidth() - player_ajuste > enemy.getX()) &&
            (player.getY() < enemy.getY() + enemy.getHeight() - player_ajuste) &&
            (player.getY() + player.getHeight() - player_ajuste  > enemy.getY())) {
            return true;
        } else return false;
    }

    public static void camera_move(Player player, OrthographicCamera camera){
        Vector3 position = camera.position;
        position.x = player.getX() + (player.getWidth() / 2);
        position.y = player.getY() + (player.getHeight() / 2);
        camera.position.set(position);
    }

    public static double vector_angle(Vector2 pv, Vector2 ev){
        return Math.acos( (pv.dot(ev)) / (pv.len() * ev.len()) ); 
    }

    public static boolean isInside(OurPoint t, Rectangle wall){
        if ( (t.x >= wall.getX()) &&
             (t.x <= wall.getX() + wall.getWidth()) &&
             (t.y >= wall.getY()) &&
             (t.y <= wall.getY() + wall.getHeight())
           ) return true;
        else return false;
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

    public static boolean check_dist(Player player, Enemy enemy){
        if (
            Math.sqrt( 
                Math.pow((enemy.x - player.x), 2d) + Math.pow((enemy.y - player.y), 2d) 
            ) < enemy.getSight()
        ) return true;
        else return false;
    }

    public static boolean check_vision(Player player, Enemy enemy, ArrayList<Rectangle> walls){
        OurPoint p, e, t;
        p = new OurPoint(player.x + player.getWidth() / 2, player.y + player.getHeight() / 2);
        //p.x = player.x;
        //p.y = player.y;
        e = new OurPoint(enemy.x + enemy.getWidth() / 2, enemy.y + enemy.getHeight() / 2);
        //e.x = enemy.x;
        //e.y = enemy.y;
        t = new OurPoint();

        for (Rectangle wall : walls){
            for (float i = 0; i < 1; i += 0.01) {
                //r(i) = i*P + (1 - i)*E, 0 <= i <= 1
                t.setLocation(
                    i*p.getX() + (1-i)*e.getX(),
                    i*p.getY() + (1-i)*e.getY() 
                );
                if (isInside(t, wall)) return false;
            }  
        }
        return true;
    }

    public static boolean check_detection(Player player, ArrayList<Enemy> enemies, ArrayList<Rectangle> walls){
        for (Enemy enemy : enemies){
            if( 
                (check_collision(player, enemy)) || 
                (
                    check_fov(player, enemy) &&
                    check_dist(player, enemy) &&
                    check_vision(player, enemy, walls)
                )
            ) return true;
        }
        return false;
    }
    public static boolean check_wall_collision(Player player, float x, float y, ArrayList<Rectangle> walls){
        for (Rectangle wall : walls) {
            if ((x < wall.getX() + wall.getWidth() - 20) &&
                (x + player.getWidth() - player_ajuste > wall.getX()) &&
                (y < wall.getY() + wall.getHeight() - 10) &&
                (y + player.getHeight() - player_ajuste > wall.getY())) {
                return true;
            } 
        }
        return false;
    }

    public static boolean check_success(Player player, ArrayList<Rectangle> doors){
        for (Rectangle door : doors) {
            if ((player.x < door.getX() + door.getWidth()) &&
                (player.x + player.getWidth() - player_ajuste > door.getX()) &&
                (player.y < door.getY() + door.getHeight()) &&
                (player.y + player.getHeight() - player_ajuste  > door.getY())) {
                return true;
            }  
        }
        return false;
    }
}