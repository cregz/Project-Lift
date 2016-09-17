package com.kota.lift.states;

/**
 * Created by Kota on 2016.06.26..
 */
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Created by Kota on 2016.06.12..
 */
public class GameStateManager {
    private Stack<State> states;
    private AssetManager assetManager;
    private StateFactory factory;
    private OrthographicCamera camera;
    private Viewport viewport;

    public GameStateManager(AssetManager manager, StateFactory factory, OrthographicCamera camera, Viewport viewport){
        states = new Stack<State>();
        this.assetManager=manager;
        this.camera=camera;
        this.factory=factory;
        this.viewport=viewport;
    }

    public void push(State state){
        states.push(state);
    }

    public void pop(){
        states.pop().dispose();
    }

    public void set(State state){
        states.pop().dispose();
        states.push(state);
    }

    public void update(float timeDifference){
        states.peek().update(timeDifference);
    }

    public void render(SpriteBatch batch){
        states.peek().render(batch);
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public State getState(String stateType){
        return factory.makeState(stateType, this, camera);
    }

    public Viewport getViewport() {
        return viewport;
    }
}
