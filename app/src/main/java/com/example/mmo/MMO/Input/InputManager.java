package com.example.mmo.MMO.Input;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;

import com.example.mmo.MMO.Assets.Assets;
import com.example.mmo.MMO.Cheats.Cheats;
import com.example.mmo.MMO.Containers.Container;
import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.Items.Swords.Sword;
import com.example.mmo.MMO.World.Tiles.Tile;
import com.example.mmo.R;
import com.google.android.material.progressindicator.BaseProgressIndicator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class InputManager {

    private Joystick joystick;

    private Handler handler;

    private Button pickUpButton, eqButton, closeButton, attackButton;

    private ArrayList<Button> buttons;

    private boolean inGUI = false;

    //delete

    private final Button reset;

    public InputManager(Handler handler){
        handler.setInputManager(this);
        this.handler = handler;

        buttons = new ArrayList<>();

        joystick = new Joystick(handler);
        int radius = handler.getWindowSize().x / 25;
        eqButton = new Button(radius, 3 * radius, radius, Assets.uiGame[6], Assets.uiGame[7], handler){
            @Override
            public void clicked(){
                /*if(handler.getWorld().getID() == 0) {
                    Tile.debug = !Tile.debug;
                    return;
                }*/

                handler.getEq().setActive(!handler.getEq().isActive());
                handler.getSkillManager().setInEq(handler.getEq().isActive());

                if(handler.getEq().isActive())
                    joystick.restartJoystick();


            }
        };

        pickUpButton = new Button(radius, 6 * radius, radius, Assets.uiGame[8], Assets.uiGame[9], handler){
            @Override
            public void clicked(){
                handler.getEntityManager().getPlayer().pickUp();
            }
        };

        attackButton = new Button(handler.getWindowSize().x - radius * 4, handler.getWindowSize().y - radius * 4.5f, radius * 1.5f, Assets.uiGame[0], Assets.uiGame[1], handler){
            @Override
            public void clicked(){
                handler.getEntityManager().getPlayer().attack();
            }
        };

        Point size = new Point();
        handler.getGame().getMainActivity().getWindowManager().getDefaultDisplay().getSize(size);

        closeButton = new Button(size.x - radius * 2, radius, radius, Assets.uiGame[2], Assets.uiGame[3], handler){
            @Override
            public void clicked(){
                handler.getEq().setActive(false);
                handler.getSkillManager().setDefaultPosition();
                handler.getSkillManager().setInteractive(false);
                inGUI = false;
            }
        };

        //delete

        reset = new Button(5, 85, radius / 2, Assets.uiGame[2], Assets.uiGame[3], handler){
            @Override
            public void clicked(){
                handler.getWorld().setPlayerSpawn();
            }
        };
    }

    public void Touch(MotionEvent event){
        closeButton.touch(event);
        reset.touch(event);

        try{
            for (Button b : buttons){
                b.touch(event);
            }
        }catch (ConcurrentModificationException e){
            return;
        }

        if(inGUI)
            return;

        eqButton.touch(event);
        pickUpButton.touch(event);

        if(handler.getEq().isActive())
            return;

        attackButton.touch(event);
        joystick.move(event);
    }

    public void render(Canvas canvas){
        joystick.render(canvas);
        eqButton.render(canvas);
        pickUpButton.render(canvas);
        closeButton.setShow(handler.getEq().isActive() || inGUI);
        closeButton.render(canvas);
        attackButton.render(canvas);
        reset.render(canvas);
    }

    public Joystick getJoystick() {
        return joystick;
    }

    public Button getPickUpButton() {
        return pickUpButton;
    }

    public boolean isInGUI() {
        return inGUI;
    }

    public void setInGUI(boolean inGUI) {
        this.inGUI = inGUI;
    }

    public void addButton(Button b){
        buttons.add(b);
    }
}
