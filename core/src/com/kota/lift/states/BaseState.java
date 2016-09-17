package com.kota.lift.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kota.lift.CustomGestureDetector;
import com.kota.lift.Lift;
import com.kota.lift.entities.Player;

/**
 * Created by Kota on 2016.06.26..
 */
public class BaseState extends State {
    private Player player;

    public BaseState(GameStateManager gsm, OrthographicCamera camera) {
        super(gsm, camera);
        this.player = new Player(30, 30);
        Gdx.input.setInputProcessor(new CustomGestureDetector(new CustomGestureDetector.DirectionListener() {
            @Override
            public void onUp() {

            }
            @Override
            public void onRight() {

            }
            @Override
            public void onLeft() {
                ShiftState shiftState = (ShiftState) manager.getState("ShiftState");
                shiftState.setShift("SquatState", 1);
                manager.set(shiftState);
            }
            @Override
            public void onDown() {

            }

            @Override
            public void onTouch(float x, float y, int pointer) {

            }
        }));
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
//            ShiftState shiftState = (ShiftState) manager.getState("ShiftState");
//            shiftState.setShift("SquatState", 1);
//            manager.set(shiftState);
        }
    }

    @Override
    public void update(float timeDifference) {
        handleInput();
        player.update(timeDifference);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.setProjectionMatrix(camera.projection);
        batch.setTransformMatrix(camera.view);
        batch.begin();
        batch.draw(background, 0, 0);
//      batch.draw(player.getImage(), player.getPosition().x, player.getPosition().y);
        player.draw(batch);
        batch.end();
    }

    @Override
    public void dispose(){

    }
}