package com.mygame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Grid {
    private int width;
    private int height;
    private int tileSize;
    private Texture gridTexture;

    public Grid(int width, int height, int tileSize, Texture gridTexture) {
        this.width = width;
        this.height = height;
        this.tileSize = tileSize;
        this.gridTexture = gridTexture;
    }

    public void draw(SpriteBatch spriteBatch) {
        for (int x = 0; x < width; x += tileSize) {
            for (int y = 0; y < height; y += tileSize) {
                spriteBatch.draw(gridTexture, x, y, tileSize, tileSize);
            }
        }
    }
}
