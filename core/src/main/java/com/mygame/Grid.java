package com.mygame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Grid {
    private int width;
    private int height;
    private Texture texture;

    public Grid(int width, int height, Texture texture) {
        this.width = width;
        this.height = height;
        this.texture = texture;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, 0, 0, width, height);
    }
}
