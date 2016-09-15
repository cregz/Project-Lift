package com.kota.lift;

import com.badlogic.gdx.input.GestureDetector;

/**
 * Created by Kota on 2016.07.14..
 */
public class CustomGestureDetector extends GestureDetector {
    public interface DirectionListener {
        void onLeft();

        void onRight();

        void onUp();

        void onDown();

        void onTouch();
    }

    public CustomGestureDetector(DirectionListener directionListener) {
        super(new DirectionGestureListener(directionListener));
    }

    private static class DirectionGestureListener extends GestureDetector.GestureAdapter {
        DirectionListener directionListener;

        public DirectionGestureListener(DirectionListener directionListener){
            this.directionListener = directionListener;
        }

        @Override
        public boolean fling(float velocityX, float velocityY, int button) {
            if(Math.abs(velocityX)>Math.abs(velocityY)){
                if(velocityX>0){
                    directionListener.onRight();
                }else{
                    directionListener.onLeft();
                }
            }else{
                if(velocityY>0){
                    directionListener.onDown();
                }else{
                    directionListener.onUp();
                }
            }
            return super.fling(velocityX, velocityY, button);
        }

        @Override
        public boolean touchDown(float x, float y, int pointer, int button){
            directionListener.onTouch();
            return super.touchDown(x, y, pointer, button);
        }
    }
}
