package com.kota.lift.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kota.lift.CustomGestureDetector;
import com.kota.lift.Lift;

/**
 * Created by Kota on 2016.06.26..
 */
public class BenchPressState extends State{
    public BenchPressState(GameStateManager gsm, OrthographicCamera camera) {
        super(gsm, camera);
        Gdx.input.setInputProcessor(new CustomGestureDetector(new CustomGestureDetector.DirectionListener() {
            @Override
            public void onUp() {

            }
            @Override
            public void onRight() {
                ShiftState shiftState = (ShiftState) manager.getState("ShiftState");
                shiftState.setShift("SquatState", -1);
                manager.set(shiftState);
            }
            @Override
            public void onLeft() {

            }
            @Override
            public void onDown() {

            }

            @Override
            public void onTouch() {

            }
        }));
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            /*ShiftState shiftState = (ShiftState) manager.getState("ShiftState");
            shiftState.setShift("BaseState", -2);
            manager.set(shiftState);*/
        }
    }

    @Override
    public void update(float timeDifference) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.setProjectionMatrix(camera.projection);
        batch.setTransformMatrix(camera.view);
        batch.begin();
        batch.draw(background, 0, 0);
        batch.end();
    }

    @Override
    public void dispose() {

    }
}
