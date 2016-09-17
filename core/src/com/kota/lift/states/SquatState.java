package com.kota.lift.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.kota.lift.CustomGestureDetector;
import com.kota.lift.Lift;
import com.kota.lift.entities.Bar;
import com.kota.lift.entities.Player;

/**
 * Created by Kota on 2016.06.26..
 */
public class SquatState extends State {
    enum PlayerState{
        Idle(0), Squatting(1);

        private final int value;
        private PlayerState(int value){
            this.value=value;
        }

        public int getValue(){
            return this.value;
        }
    }

    private Bar bar;
    private Texture guard;
    private PlayerState playerState;

    private ShapeRenderer debugRenderer;
    private Vector3 circleOrigin = new Vector3(Lift.STATE_WIDTH*2+Lift.PADDING-25, 25, 25);
    private Vector2 lastTouchPosition = new Vector2(-10,-10);
    private int lastTouchRadius = 10;

    public SquatState(GameStateManager gsm, OrthographicCamera camera) {
        super(gsm, camera);
        bar = new Bar(382, 350);
        guard = new Texture("guard.png");
        Gdx.input.setInputProcessor(new CustomGestureDetector(new CustomGestureDetector.DirectionListener() {
            @Override
            public void onUp() {
                if(playerState==PlayerState.Idle)
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
                if(playerState==PlayerState.Idle)
                    bar.decreaseWeight();
            }

            @Override
            public void onTouch(float x, float y, int pointer) {
                markTouchPosition(x,y);
                if(playerState==PlayerState.Squatting)
                    bar.lift(300);
                if(isInCircle(x,y)){
                    ChangeState();
                }
            }
        }));
        playerState = PlayerState.Idle;
        debugRenderer = new ShapeRenderer();
    }

    private void markTouchPosition(float x, float y) {
        lastTouchPosition=new Vector2(manager.getViewport().unproject(new Vector2(x,y)));
        lastTouchPosition.x-=lastTouchRadius/2;
        lastTouchPosition.y-=lastTouchRadius/2;
    }

    private boolean isInCircle(float x, float y) {
        Vector2 coords = manager.getViewport().unproject(new Vector2(x, y));
        return (Math.abs(coords.x-circleOrigin.x) < circleOrigin.z && Math.abs(coords.y-circleOrigin.y) < circleOrigin.z);
    }

    private void ChangeState() {
        int pStateNum = playerState.getValue();
        pStateNum += 1;
        pStateNum %= PlayerState.values().length;
        playerState = PlayerState.values()[pStateNum];
    }

    @Override
    public void handleInput() {
//        if(Gdx.input.justTouched()){
//            bar.lift(300);
//            ShiftState shiftState = (ShiftState) manager.getState("ShiftState");
//            shiftState.setShift("BenchPressState", 1);
//            manager.set(shiftState);
//        }
    }

    @Override
    public void update(float timeDifference) {
        handleInput();
        if(playerState!=PlayerState.Idle)
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
        font.draw(batch, playerState.toString(), 382, 100);
        batch.end();
        debugRender();
    }

    private void debugRender() {
        debugRenderer.setProjectionMatrix(camera.projection);
        debugRenderer.setTransformMatrix(camera.view);
        debugRenderer.begin(ShapeRenderer.ShapeType.Filled);
        debugRenderer.setColor(Color.CORAL);
        debugRenderer.circle(circleOrigin.x, circleOrigin.y, circleOrigin.z);
        debugRenderer.setColor(Color.FOREST);
        debugRenderer.circle(lastTouchPosition.x+lastTouchRadius/2, lastTouchPosition.y+lastTouchRadius/2, lastTouchRadius);
        debugRenderer.end();
    }

    @Override
    public void dispose() {

    }
}