package com.example.mmo.MMO.Input;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import com.example.mmo.MMO.Handler;
import com.example.mmo.R;

public class Button {

    private float x, y, radius;

    private RectF bounds;

    private Paint paint;

    private boolean show = true, isHold = false, selected = false;

    protected Bitmap texture, holdTexture;

    public Button(float x, float y, float radius, Bitmap texture, Bitmap holdTexture, Handler handler){
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.texture = texture;
        this.holdTexture = holdTexture;

        bounds = new RectF(x, y, x + radius * 2, y + radius * 2);

        paint = new Paint(handler.getGame().getResources().getColor(R.color.purple));
    }

    public void render(Canvas canvas){
        if(!show)
            return;

        if(!isHold){
            canvas.drawBitmap(texture,
                    null,
                    new RectF(x,
                            y,
                            x + radius * 2,
                            y + radius * 2),
                    null);
        }else{
            canvas.drawBitmap(holdTexture,
                    null,
                    new RectF(x,
                            y,
                            x + radius * 2,
                            y + radius * 2),
                    null);
        }
    }

    public void touch(MotionEvent event){
        if(!show)
            return;

        if(bounds.contains(event.getX(), event.getY())){
            if(event.getAction() == MotionEvent.ACTION_UP){
                isHold = false;
                if(selected)
                    clicked();
            }

            if(event.getAction() == MotionEvent.ACTION_DOWN) {
                isHold = true;
                selected = true;
            }
        }else
            if(event.getAction() == MotionEvent.ACTION_UP)
                selected = false;
    }

    public void clicked(){}

    public void setBounds(RectF bounds) {
        this.bounds = bounds;
    }

    public RectF getBounds() {
        return bounds;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public boolean isShow() {
        return show;
    }
}
