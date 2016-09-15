package com.kota.lift.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Kota on 2016.07.18..
 */
public class Bar extends Entity {
    private static final int GRAVITY = -15;
    private static final int WEIGHT_SPACING = 3;

    private Vector3 position;
    private Vector3 velocity;
    private Texture image;

    private int[] weights;
    private Texture[] textureHolder;

    private Texture guard;

    public Bar(int x, int y) {
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, 0, 0);
        this.image = new Texture("bar.png");
        guard = new Texture("guard.png");
        weights = new int[15];
        textureHolder = new Texture[15];
    }

    @Override
    public void update (float dt){
        //csak teszt
        if(position.y>100 || velocity.y>0) {
            velocity.add(0, GRAVITY, 0);
            velocity.scl(dt);
            position.add(0, velocity.y, 0);
            velocity.scl(1 / dt);
        }
    }

    public void lift(float amount){
        velocity.y += amount;
    }

    public void raiseWeight(){
        int i = 0;
        while(!(i >= weights.length) && weights[i] == 20) i++;
        if(i >= weights.length) return;
        weights[i] += 5;
        textureHolder[i] = new Texture(Integer.toString(weights[i]) + ".png");
    }

    public void decreaseWeight(){
        int i = weights.length - 1;
        while(weights[i] == 0) {
            if (i == 0 && weights[i] == 0) return;
            i--;
        }
        weights[i] -= 5;
        if(weights[i] == 0) textureHolder[i] = null;
        else textureHolder[i] = new Texture(Integer.toString(weights[i]) + ".png");
    }

    public Vector3 getPosition() {
        return position;
    }

    public Texture getImage() {
        return image;
    }

    public int[] getWeights() {
        return weights;
    }

    public Texture[] getTextureHolder() {
        return textureHolder;
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(image, position.x, position.y);

        int j = 0;
        while (!(j >= weights.length) && weights[j] != 0) j++;

        for (int i = j - 1; i >= 0; i--){
            //if(bar.getWeights()[i] != 0){
            batch.draw(textureHolder[i], position.x + 55 - (3 * i), position.y - 1);
            batch.draw(textureHolder[i], position.x + 186 + (3 * i), position.y - 1,
                    textureHolder[i].getWidth(), textureHolder[i].getHeight(),
                    0, 0, textureHolder[i].getWidth(), textureHolder[i].getHeight(), true, false);
            //}
        }
        batch.draw(guard, position.x + 63, position.y + 12);
        batch.draw(guard, position.x + 191, position.y + 12);
    }
}
