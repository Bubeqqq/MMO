package com.example.mmo.MMO.Entity.Creatures;

import android.app.VoiceInteractor;
import android.graphics.Bitmap;

import com.example.mmo.MMO.Assets.Animation;
import com.example.mmo.MMO.Entity.Entity;
import com.example.mmo.MMO.GUI.HealthBar;
import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.World.Tiles.Tile;

public abstract class Creature extends Entity {

    public static final int DEFAULTWIDTH  = Tile.TILEWIDTH, DEFAULTHEIGHT = Tile.TILEWIDTH;

    protected float speed = 0.02f;

    protected Bitmap[] textures;

    protected Animation down, left, right, up;

    protected float moveX, moveY;

    protected final int animationSpeed = 60;

    protected HealthBar healthBar;

    public Creature(Handler handler, int width, int height, int x, int y, Bitmap[] textures) {
        super(handler, width, height, x, y);

        this.textures = textures;

        Bitmap[] d = new Bitmap[3];
        d[0] = textures[0];
        d[1] = textures[1];
        d[2] = textures[2];
        down = new Animation(d, animationSpeed);

        Bitmap[] l = new Bitmap[3];
        l[0] = textures[3];
        l[1] = textures[4];
        l[2] = textures[5];
        left = new Animation(l, animationSpeed);

        Bitmap[] r = new Bitmap[3];
        r[0] = textures[6];
        r[1] = textures[7];
        r[2] = textures[8];
        right = new Animation(r, animationSpeed);

        Bitmap[] u = new Bitmap[3];
        u[0] = textures[9];
        u[1] = textures[10];
        u[2] = textures[11];
        up = new Animation(u, animationSpeed);
    }

    public void Move(float x, float y){
        MoveX(x * speed);
        MoveY(y * speed);
    }

    protected void MoveX(float xMove){
        float newX = x + xMove;
        this.moveX = xMove;

        if(xMove > 0){ //Right
            move:
            while(x < newX){
                //Up

                //check for collision with mobs
                for(Entity e : handler.getEntityManager().getEntities()){
                    if(e == this || !e.haveCollisions())
                        continue;

                    if(e.getBounds().contains(bounds.right + 3, bounds.top))
                        break move;
                }

                int tileX = (int) (getBounds().right + 3) / Tile.TILEWIDTH;
                int tileY = (int) getBounds().top / Tile.TILEWIDTH;

                if(tileX >= handler.getWorld().getWidth())
                    break;

                boolean first = true, second = true; //true = can walk on layer

                if(handler.getTileManager().getTiles()[handler.getWorld().getWorld()[tileX][tileY]].isSolid()){
                    if(handler.getTileManager().getTiles()[handler.getWorld().getWorld()[tileX][tileY]].getBounds(tileX * Tile.TILEWIDTH, tileY * Tile.TILEWIDTH).contains(getBounds().right + 3, getBounds().top)){
                        if(!handler.getTileManager().getTiles()[handler.getWorld().getWorld()[tileX][tileY]].isAntyBounds()){
                            first = false;
                        }
                    }else {
                        if(handler.getTileManager().getTiles()[handler.getWorld().getWorld()[tileX][tileY]].isAntyBounds())
                            first = false;
                    }
                }

                if(handler.getWorld().getSecondLayer()[tileX][tileY] != -1 && handler.getTileManager().getSecondLayer()[handler.getWorld().getSecondLayer()[tileX][tileY]].isSolid()){
                    if(handler.getTileManager().getSecondLayer()[handler.getWorld().getSecondLayer()[tileX][tileY]].getBounds(tileX * Tile.TILEWIDTH, tileY * Tile.TILEWIDTH).contains(getBounds().right + 3, getBounds().top)){
                        second = false;
                    }
                }

                if(!first && !second)
                    break;

                if(!second && first)
                    break;

                if(!first && handler.getWorld().getSecondLayer()[tileX][tileY] == -1)
                    break;

                //Down

                //check for collision with mobs
                for(Entity e : handler.getEntityManager().getEntities()){
                    if(e == this || !e.haveCollisions())
                        continue;

                    if(e.getBounds().contains(bounds.right + 3, bounds.bottom))
                        break move;
                }

                first = true;
                second = true;

                tileX = (int) (getBounds().right + 3) / Tile.TILEWIDTH;
                tileY = (int) getBounds().bottom / Tile.TILEWIDTH;

                if(handler.getTileManager().getTiles()[handler.getWorld().getWorld()[tileX][tileY]].isSolid()){
                    if(handler.getTileManager().getTiles()[handler.getWorld().getWorld()[tileX][tileY]].getBounds(tileX * Tile.TILEWIDTH, tileY * Tile.TILEWIDTH).contains(getBounds().right + 3, getBounds().bottom)){
                        if(!handler.getTileManager().getTiles()[handler.getWorld().getWorld()[tileX][tileY]].isAntyBounds()){
                            first = false;
                        }
                    }else {
                        if(handler.getTileManager().getTiles()[handler.getWorld().getWorld()[tileX][tileY]].isAntyBounds())
                            first = false;
                    }
                }

                if(handler.getWorld().getSecondLayer()[tileX][tileY] != -1 && handler.getTileManager().getSecondLayer()[handler.getWorld().getSecondLayer()[tileX][tileY]].isSolid()){
                    if(handler.getTileManager().getSecondLayer()[handler.getWorld().getSecondLayer()[tileX][tileY]].getBounds(tileX * Tile.TILEWIDTH, tileY * Tile.TILEWIDTH).contains(getBounds().right + 3, getBounds().bottom)){
                        second = false;
                    }
                }

                if(!first && !second)
                    break;

                if(!second && first)
                    break;

                if(!first && handler.getWorld().getSecondLayer()[tileX][tileY] == -1)
                    break;

                x += 2;
            }
        }

        if(xMove < 0){ //Left
            move:
            while(x > newX){
                //Up

                //check for collision with mobs
                for(Entity e : handler.getEntityManager().getEntities()){
                    if(e == this || !e.haveCollisions())
                        continue;

                    if(e.getBounds().contains(bounds.left - 3, bounds.top))
                        break move;
                }

                int tileX = (int) (getBounds().left - 3) / Tile.TILEWIDTH;
                int tileY = (int) getBounds().top / Tile.TILEWIDTH;

                if(tileX <= 0)
                    break;

                boolean first = true, second = true; //true = can walk on layer

                if(handler.getTileManager().getTiles()[handler.getWorld().getWorld()[tileX][tileY]].isSolid()){
                    if(handler.getTileManager().getTiles()[handler.getWorld().getWorld()[tileX][tileY]].getBounds(tileX * Tile.TILEWIDTH, tileY * Tile.TILEWIDTH).contains(getBounds().left - 3, getBounds().top)){
                        if(!handler.getTileManager().getTiles()[handler.getWorld().getWorld()[tileX][tileY]].isAntyBounds()){
                            first = false;
                        }
                    }else {
                        if(handler.getTileManager().getTiles()[handler.getWorld().getWorld()[tileX][tileY]].isAntyBounds())
                            first = false;
                    }
                }

                if(handler.getWorld().getSecondLayer()[tileX][tileY] != -1 && handler.getTileManager().getSecondLayer()[handler.getWorld().getSecondLayer()[tileX][tileY]].isSolid()){
                    if(handler.getTileManager().getSecondLayer()[handler.getWorld().getSecondLayer()[tileX][tileY]].getBounds(tileX * Tile.TILEWIDTH, tileY * Tile.TILEWIDTH).contains(getBounds().left - 3, getBounds().top)){
                        second = false;
                    }
                }

                if(!first && !second)
                    break;

                if(!second && first)
                    break;

                if(!first && handler.getWorld().getSecondLayer()[tileX][tileY] == -1)
                    break;

                //Down

                //check for collision with mobs
                for(Entity e : handler.getEntityManager().getEntities()){
                    if(e == this || !e.haveCollisions())
                        continue;

                    if(e.getBounds().contains(bounds.left - 3, bounds.bottom))
                        break move;
                }

                tileX = (int) (getBounds().left - 3) / Tile.TILEWIDTH;
                tileY = (int) getBounds().bottom / Tile.TILEWIDTH;

                first = true;
                second = true;

                if(handler.getTileManager().getTiles()[handler.getWorld().getWorld()[tileX][tileY]].isSolid()){
                    if(handler.getTileManager().getTiles()[handler.getWorld().getWorld()[tileX][tileY]].getBounds(tileX * Tile.TILEWIDTH, tileY * Tile.TILEWIDTH).contains(getBounds().left - 3, getBounds().bottom)){
                        if(!handler.getTileManager().getTiles()[handler.getWorld().getWorld()[tileX][tileY]].isAntyBounds()){
                            first = false;
                        }
                    }else {
                        if(handler.getTileManager().getTiles()[handler.getWorld().getWorld()[tileX][tileY]].isAntyBounds())
                            first = false;
                    }
                }

                if(handler.getWorld().getSecondLayer()[tileX][tileY] != -1 && handler.getTileManager().getSecondLayer()[handler.getWorld().getSecondLayer()[tileX][tileY]].isSolid()){
                    if(handler.getTileManager().getSecondLayer()[handler.getWorld().getSecondLayer()[tileX][tileY]].getBounds(tileX * Tile.TILEWIDTH, tileY * Tile.TILEWIDTH).contains(getBounds().left - 3, getBounds().bottom)){
                        second = false;
                    }
                }


                if(!first && !second)
                    break;

                if(!second && first)
                    break;

                if(!first && handler.getWorld().getSecondLayer()[tileX][tileY] == -1)
                    break;

                x -= 2;
            }
        }
    }

    protected void MoveY(float yMove){
        float newY = y + yMove;
        this.moveY = yMove;

        if(yMove > 0){ //Down
            move:
            while(y < newY){
                //Right

                //check for collision with mobs
                for(Entity e : handler.getEntityManager().getEntities()){
                    if(e == this || !e.haveCollisions())
                        continue;

                    if(e.getBounds().contains(bounds.right, bounds.bottom + 3))
                        break move;
                }

                int tileX = (int) getBounds().right / Tile.TILEWIDTH;
                int tileY = (int) (getBounds().bottom + 3) / Tile.TILEWIDTH;

                if(tileY >= handler.getWorld().getHeight())
                    break;

                boolean first = true, second = true; //true = can walk on layer

                if(handler.getTileManager().getTiles()[handler.getWorld().getWorld()[tileX][tileY]].isSolid()){
                    if(handler.getTileManager().getTiles()[handler.getWorld().getWorld()[tileX][tileY]].getBounds(tileX * Tile.TILEWIDTH, tileY * Tile.TILEWIDTH).contains(getBounds().right, getBounds().bottom + 3)){
                        if(!handler.getTileManager().getTiles()[handler.getWorld().getWorld()[tileX][tileY]].isAntyBounds()){
                            first = false;
                        }
                    }else {
                        if(handler.getTileManager().getTiles()[handler.getWorld().getWorld()[tileX][tileY]].isAntyBounds())
                            first = false;
                    }
                }

                if(handler.getWorld().getSecondLayer()[tileX][tileY] != -1 && handler.getTileManager().getSecondLayer()[handler.getWorld().getSecondLayer()[tileX][tileY]].isSolid()){
                    if(handler.getTileManager().getSecondLayer()[handler.getWorld().getSecondLayer()[tileX][tileY]].getBounds(tileX * Tile.TILEWIDTH, tileY * Tile.TILEWIDTH).contains(getBounds().right, getBounds().bottom + 3)){
                        second = false;
                    }
                }

                if(!first && !second)
                    break;

                if(!second && first)
                    break;

                if(!first && handler.getWorld().getSecondLayer()[tileX][tileY] == -1)
                    break;

                //Left

                //check for collision with mobs
                for(Entity e : handler.getEntityManager().getEntities()){
                    if(e == this || !e.haveCollisions())
                        continue;

                    if(e.getBounds().contains(bounds.left, bounds.bottom + 3))
                        break move;
                }

                tileX = (int) getBounds().left / Tile.TILEWIDTH;
                tileY = (int) (getBounds().bottom + 3) / Tile.TILEWIDTH;

                first = true;
                second = true;

                if(handler.getTileManager().getTiles()[handler.getWorld().getWorld()[tileX][tileY]].isSolid()){
                    if(handler.getTileManager().getTiles()[handler.getWorld().getWorld()[tileX][tileY]].getBounds(tileX * Tile.TILEWIDTH, tileY * Tile.TILEWIDTH).contains(getBounds().left, getBounds().bottom + 3)){
                        if(!handler.getTileManager().getTiles()[handler.getWorld().getWorld()[tileX][tileY]].isAntyBounds()){
                            first = false;
                        }
                    }else {
                        if(handler.getTileManager().getTiles()[handler.getWorld().getWorld()[tileX][tileY]].isAntyBounds())
                            first = false;
                    }
                }

                if(handler.getWorld().getSecondLayer()[tileX][tileY] != -1 && handler.getTileManager().getSecondLayer()[handler.getWorld().getSecondLayer()[tileX][tileY]].isSolid()){
                    if(handler.getTileManager().getSecondLayer()[handler.getWorld().getSecondLayer()[tileX][tileY]].getBounds(tileX * Tile.TILEWIDTH, tileY * Tile.TILEWIDTH).contains(getBounds().left, getBounds().bottom + 3)){
                        second = false;
                    }
                }

                if(!first && !second)
                    break;

                if(!second && first)
                    break;

                if(!first && handler.getWorld().getSecondLayer()[tileX][tileY] == -1)
                    break;

                y += 2;
            }
        }

        if(yMove < 0){ //Up
            move:
            while(y > newY){
                //Right

                //check for collision with mobs
                for(Entity e : handler.getEntityManager().getEntities()){
                    if(e == this || !e.haveCollisions())
                        continue;

                    if(e.getBounds().contains(bounds.right, bounds.top - 3))
                        break move;
                }

                int tileX = (int) getBounds().right / Tile.TILEWIDTH;
                int tileY = (int) (getBounds().top - 3) / Tile.TILEWIDTH;

                if(tileY <= 0)
                    break;

                boolean first = true, second = true; //true = can walk on layer

                if(handler.getTileManager().getTiles()[handler.getWorld().getWorld()[tileX][tileY]].isSolid()){
                    if(handler.getTileManager().getTiles()[handler.getWorld().getWorld()[tileX][tileY]].getBounds(tileX * Tile.TILEWIDTH, tileY * Tile.TILEWIDTH).contains(getBounds().right, getBounds().top - 3)){
                        if(!handler.getTileManager().getTiles()[handler.getWorld().getWorld()[tileX][tileY]].isAntyBounds()){
                            first = false;
                        }
                    }else {
                        if(handler.getTileManager().getTiles()[handler.getWorld().getWorld()[tileX][tileY]].isAntyBounds())
                            first = false;
                    }
                }

                if(handler.getWorld().getSecondLayer()[tileX][tileY] != -1 && handler.getTileManager().getSecondLayer()[handler.getWorld().getSecondLayer()[tileX][tileY]].isSolid()){
                    if(handler.getTileManager().getSecondLayer()[handler.getWorld().getSecondLayer()[tileX][tileY]].getBounds(tileX * Tile.TILEWIDTH, tileY * Tile.TILEWIDTH).contains(getBounds().right, getBounds().top - 3)){
                        second = false;
                    }
                }

                if(!first && !second)
                    break;

                if(!second && first)
                    break;

                if(!first && handler.getWorld().getSecondLayer()[tileX][tileY] == -1)
                    break;

                //Left

                //check for collision with mobs
                for(Entity e : handler.getEntityManager().getEntities()){
                    if(e == this || !e.haveCollisions())
                        continue;

                    if(e.getBounds().contains(bounds.left, bounds.top - 3))
                        break move;
                }

                tileX = (int) getBounds().left / Tile.TILEWIDTH;
                tileY = (int) (getBounds().top - 3) / Tile.TILEWIDTH;

                first = true;
                second = true;

                if(handler.getTileManager().getTiles()[handler.getWorld().getWorld()[tileX][tileY]].isSolid()){
                    if(handler.getTileManager().getTiles()[handler.getWorld().getWorld()[tileX][tileY]].getBounds(tileX * Tile.TILEWIDTH, tileY * Tile.TILEWIDTH).contains(getBounds().left, getBounds().top - 3)){
                        if(!handler.getTileManager().getTiles()[handler.getWorld().getWorld()[tileX][tileY]].isAntyBounds()){
                            first = false;
                        }
                    }else {
                        if(handler.getTileManager().getTiles()[handler.getWorld().getWorld()[tileX][tileY]].isAntyBounds())
                            first = false;
                    }
                }

                if(handler.getWorld().getSecondLayer()[tileX][tileY] != -1 && handler.getTileManager().getSecondLayer()[handler.getWorld().getSecondLayer()[tileX][tileY]].isSolid()){
                    if(handler.getTileManager().getSecondLayer()[handler.getWorld().getSecondLayer()[tileX][tileY]].getBounds(tileX * Tile.TILEWIDTH, tileY * Tile.TILEWIDTH).contains(getBounds().left, getBounds().top - 3)){
                        second = false;
                    }
                }

                if(!first && !second)
                    break;

                if(!second && first)
                    break;

                if(!first && handler.getWorld().getSecondLayer()[tileX][tileY] == -1)
                    break;

                y -= 2;
            }
        }
    }

    @Override
    public void animationTick(){
        left.tick();
        down.tick();
        right.tick();
        up.tick();
    }

    protected Bitmap getCurrentTexture(){
        if(moveX > 0){
            if(moveX > Math.abs(moveY))
                return right.GetCurrentFrame();
            else if(moveY > 0)
                return down.GetCurrentFrame();
            else return up.GetCurrentFrame();
        }

        if(moveX < 0){
            if(moveX < -Math.abs(moveY))
                return left.GetCurrentFrame();
            else if(moveY > 0)
                return down.GetCurrentFrame();
            else return up.GetCurrentFrame();
        }

        if(moveX == 0 && moveY != 0){
            if(moveY > 0)
                return down.GetCurrentFrame();
            else
                return up.GetCurrentFrame();
        }

        return textures[1];
    }

    public Bitmap getDefaultTexture(){
        return textures[1];
    }

    public HealthBar getHealthBar() {
        return healthBar;
    }
}
