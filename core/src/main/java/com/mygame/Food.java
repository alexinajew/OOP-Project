package com.mygame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;
import java.util.ArrayList;

public class Food {
    // Coordinates of the food on the grid
    private int x;
    private int y;
    
    // Size of each tile, and dimensions of the board
    private int tileSize;
    private int boardWidth;
    private int boardHeight;
    
    // Texture to display the food as an image
    private Texture foodTexture;

    // Constructor initializes the food's size, board dimensions, and texture
    public Food(int tileSize, int boardWidth, int boardHeight, Texture foodTexture) {
        this.tileSize = tileSize; // Tile size (size of each grid square)
        this.boardWidth = boardWidth; // Width of the game board
        this.boardHeight = boardHeight; // Height of the game board
        this.foodTexture = foodTexture; // Texture (image) of the food
        placeFood(null); // Place the food in a random position when game starts
    }

    // Method to place food on a random tile, avoiding the snake's body
    public void placeFood(ArrayList<Tile> snakeBody) {
        Random rand = new Random(); // Random generator to place the food randomly
        boolean foodOnSnake;

        do {
            foodOnSnake = false; // Reset flag for each placement attempt
            
            // Generate random coordinates for the food within the board's boundaries
            x = rand.nextInt(boardWidth / tileSize);
            y = rand.nextInt(boardHeight / tileSize);

            // If a snake is passed, ensure the food isn't placed on the snake's body
            if (snakeBody != null) {
                for (Tile tile : snakeBody) {
                    if (tile.x == x && tile.y == y) {
                        // If the food is placed on a tile occupied by the snake, set flag to true
                        foodOnSnake = true;
                        break;
                    }
                }
            }
        } while (foodOnSnake); // Repeat until food is not placed on the snake
    }

    // Method to draw the food on the screen using the SpriteBatch (LibGDX)
    public void draw(SpriteBatch spriteBatch) {
        // Draws the food texture at the calculated (x, y) position on the grid
        spriteBatch.draw(foodTexture, x * tileSize, y * tileSize, tileSize, tileSize);
    }

    // Getter for the food's X-coordinate
    public int getX() {
        return x;
    }

    // Getter for the food's Y-coordinate
    public int getY() {
        return y;
    }
}
