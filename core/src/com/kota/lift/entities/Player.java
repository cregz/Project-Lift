package com.kota.lift.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Kota on 2016.07.29..
 */
public class Player extends Entity{
    private Vector3 position;
    private Texture image;
    private int frame;
    private int counter;

    private ShapeRenderer debugRenderer;

    public Player(int x, int y) {
        this.position = new Vector3(x, y, 0);
        this.image = new Texture("character_0.png");
        this.frame = 0;
        this.counter = 0;

        this.debugRenderer=new ShapeRenderer();
    }

    public void increment(){
        counter++;
        if(counter == 30){
            switchFrame();
            counter = 0;
        }
    }

    public void switchFrame(){
        if(frame == 5){
            image = new Texture("character_0.png");
            frame = 0;
        }
        else {
            frame++;
            image = new Texture("character_" + frame + ".png");
        }
    }

    public Vector3 getPosition() {
        return position;
    }

    public Texture getImage() {
        return image;
    }

    @Override
    public void update(float deltaTime) {
        increment();
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(image, position.x, position.y);
    }

    private void debugRender() {
        debugRenderer.begin(ShapeRenderer.ShapeType.Filled);
        debugRenderer.setColor(Color.MAROON);
        debugRenderer.rect(50,100,50,100);
        debugRenderer.end();
    }
}
