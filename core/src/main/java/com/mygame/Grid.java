package com.mygame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Grid {
    // Width and height of the grid
    private int width;
    private int height;
    
    // Texture for the grid background or appearance
    private Texture texture;

    // Constructor to initialize grid dimensions and texture
    public Grid(int width, int height, Texture texture) {
        this.width = width; // Width of the grid in pixels
        this.height = height; // Height of the grid in pixels
        this.texture = texture; // Texture to represent the grid's appearance
    }

    // Method to draw the grid on the screen
    public void draw(SpriteBatch batch) {
        // Draw the grid texture covering the entire game area (from 0,0 to width, height)
        batch.draw(texture, 0, 0, width, height);
    }
}
