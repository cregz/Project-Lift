package com.kota.lift.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by cregz on 2016. 09. 15..
 */

public abstract class Entity {
    public abstract void update(float deltaTime);
    public abstract void draw(SpriteBatch batch);
}
