package com.kota.lift.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kota.lift.CustomGestureDetector;
import com.kota.lift.Lift;
import com.kota.lift.entities.Bar;

/**
 * Created by Kota on 2016.06.26..
 */
public class SquatState extends State {
    private Bar bar;
    private Texture guard;

    public SquatState(GameStateManager gsm, OrthographicCamera camera) {
        super(gsm, camera);
        bar = new Bar(382, 350);
        guard = new Texture("guard.png");
        Gdx.input.setInputProcessor(new CustomGestureDetector(new CustomGestureDetector.DirectionListener() {
            @Override
            public void onUp() {
                bar.raiseWeight();
            }
            @Override
            public void onRight() {
                ShiftState shiftState = (ShiftState) manager.getState("ShiftState");
                shiftState.setShift("BaseState", -1);
                manager.set(shiftState);
            }
            @Override
            public void onLeft() {
                ShiftState shiftState = (ShiftState) manager.getState("ShiftState");
                shiftState.setShift("BenchPressState", 1);
                manager.set(shiftState);
            }
            @Override
            public void onDown() {
                bar.decreaseWeight();
            }

            @Override
            public void onTouch() {
                bar.lift(20);
            }
        }));
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            bar.lift(300);
            /*ShiftState shiftState = (ShiftState) manager.getState("ShiftState");
            shiftState.setShift("BenchPressState", 1);
            manager.set(shiftState);*/
        }
    }

    @Override
    public void update(float timeDifference) {
        handleInput();
        bar.update(timeDifference);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.setProjectionMatrix(camera.projection);
        batch.setTransformMatrix(camera.view);
        batch.begin();
        batch.draw(background, 0, 0);
        bar.draw(batch);
//        batch.draw(bar.getImage(), bar.getPosition().x, bar.getPosition().y);
//
//        int j = 0;
//        while (!(j >= bar.getWeights().length) && bar.getWeights()[j] != 0) j++;
//
//        for (int i = j - 1; i >= 0; i--){
//            //if(bar.getWeights()[i] != 0){
//                batch.draw(bar.getTextureHolder()[i], bar.getPosition().x + 55 - (3 * i), bar.getPosition().y - 1);
//                batch.draw(bar.getTextureHolder()[i], bar.getPosition().x + 186 + (3 * i), bar.getPosition().y - 1,
//                        bar.getTextureHolder()[i].getWidth(), bar.getTextureHolder()[i].getHeight(),
//                        0, 0, bar.getTextureHolder()[i].getWidth(), bar.getTextureHolder()[i].getHeight(), true, false);
//            //}
//        }
//        batch.draw(guard, bar.getPosition().x + 63, bar.getPosition().y + 12);
//        batch.draw(guard, bar.getPosition().x + 191, bar.getPosition().y + 12);
        font.draw(batch, "test", 382, 100);
        batch.end();
    }

    @Override
    public void dispose() {

    }
}