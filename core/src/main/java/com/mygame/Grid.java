package com.mygame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Grid {
    private int width;
    private int height;
    private int tileSize;
    private Texture texture;

    public Grid(int width, int height, int tileSize, Texture texture) {
        this.width = width;
        this.height = height;
        this.tileSize = tileSize;
        this.texture = texture;
    }

    public void draw(SpriteBatch batch) {
        for (int x = 0; x < width; x += tileSize) {
            for (int y = 0; y < height; y += tileSize) {
                batch.draw(texture, x, y, tileSize, tileSize);
            }
        }
    }
}
