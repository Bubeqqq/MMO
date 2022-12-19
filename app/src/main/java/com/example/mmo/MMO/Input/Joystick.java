package com.example.mmo.MMO.Input;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.example.mmo.MMO.Assets.Assets;
import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.Utils;
import com.example.mmo.R;

public class Joystick {

    private float x, y, circleX, circleY, radius, circleRadius;

    private Paint paint, circlePaint;

    private boolean holding;

    public Joystick(Handler handler){
        paint = new Paint();
        paint.setColor(handler.getGame().getResources().getColor(R.color.blue));

        circlePaint = new Paint();
        circlePaint.setColor(handler.getGame().getResources().getColor(R.color.red));

        radius = handler.getWindowSize().x / 16;
        circleRadius = radius;

        x = radius * 2;
        circleX = x;
        y = handler.getWindowSize().y - radius * 2;
        circleY = y;
    }

    public void restartJoystick(){
        circleX = x;
        circleY = y;
        holding = false;
    }

    public void move(MotionEvent event){
        float touchX = event.getX(), touchY = event.getY();

        if(event.getAction() == MotionEvent.ACTION_UP){
            circleX = x;
            circleY = y;
            holding = false;
            return;
        }

        if(Utils.getDistance((int) touchX, (int) touchY, (int) x, (int) y) > 300 && !holding) {
            return;
        }

        while(circleX < touchX){
            if(circleX + 1 > getBounds().left && circleX + 1 < getBounds().right){
                circleX++;
                holding = true;
            }else
                break;
        }

        while (circleX > touchX){
            if(circleX - 1 > getBounds().left && circleX - 1 < getBounds().right){
                circleX--;
                holding = true;
            }else
                break;
        }

        while(circleY < touchY){
            if(circleY + 1 > getBounds().top && circleY + 1 < getBounds().bottom){
                circleY++;
                holding = true;
            }else
                break;
        }

        while(circleY > touchY){
            if(circleY - 1 > getBounds().top && circleY - 1 < getBounds().bottom){
                circleY--;
                holding = true;
            }else
                break;
        }
    }

    public void render(Canvas canvas){
        /*canvas.drawOval(x - radius, y - radius, x + radius, y + radius, paint);
        canvas.drawOval(circleX - circleRadius, circleY - circleRadius, circleX + circleRadius, circleY + circleRadius, circlePaint);*/

        canvas.drawBitmap(Assets.uiGame[12],
                null,
                new RectF(x - radius,
                        y - radius,
                        x + radius,
                        y + radius),
                null);

        canvas.drawBitmap(Assets.uiGame[13],
                null,
                new RectF(circleX - circleRadius,
                        circleY - circleRadius,
                        circleX + circleRadius,
                        circleY + circleRadius),
                null);
    }

    public RectF getBounds(){
        return new RectF(x - radius, y - radius, x + radius, y + radius);
    }

    public float getXOffset(){
        return circleX - x;
    }

    public float getYOffset(){
        return circleY - y;
    }

    public boolean isHolding() {
        return holding;
    }
}
