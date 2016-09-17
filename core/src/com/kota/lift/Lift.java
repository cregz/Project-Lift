package com.kota.lift;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kota.lift.states.BaseState;
import com.kota.lift.states.GameStateManager;
import com.kota.lift.states.StateFactory;

public class Lift extends ApplicationAdapter {
	public static final int WIDTH = 1024;
	public static final int HEIGHT = 512;
	public static final int STATE_WIDTH = 288;
	public static final int PADDING = 80;
	public static final String TITLE = "Lift things up and put them down";

	private GameStateManager manager;
	private SpriteBatch batch;

	private OrthographicCamera camera;
	Viewport viewport;

	AssetManager assetManager = new AssetManager();

	@Override
	public void create () {
		batch = new SpriteBatch();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		camera = new OrthographicCamera();
		camera.setToOrtho(false);
		camera.position.set(STATE_WIDTH/2f, HEIGHT/2, 0);
		camera.update();
		viewport = new FitViewport(STATE_WIDTH, HEIGHT, camera);
		viewport.apply();
		manager = new GameStateManager(assetManager, new StateFactory(), camera, viewport);
		assetManager.load("gymbg_hd.png", Texture.class);
		assetManager.finishLoading();
		manager.push(manager.getState("BaseState"));
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		manager.update(Gdx.graphics.getDeltaTime());
		manager.render(batch);
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
	}


}
