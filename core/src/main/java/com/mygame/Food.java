package com.mygame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;
import java.util.ArrayList;

public class Food {
    private int x;
    private int y;
    private int tileSize;
    private int boardWidth;
    private int boardHeight;
    private Texture foodTexture;

    public Food(int tileSize, int boardWidth, int boardHeight, Texture foodTexture) {
        this.tileSize = tileSize;
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        this.foodTexture = foodTexture;
        placeFood(null); // Initially place food
    }

    public void placeFood(ArrayList<Tile> snakeBody) {
        Random rand = new Random();
        boolean foodOnSnake;

        do {
            foodOnSnake = false;
            x = rand.nextInt(boardWidth / tileSize);
            y = rand.nextInt(boardHeight / tileSize);

            // Check if food is placed on the snake
            if (snakeBody != null) {
                for (Tile tile : snakeBody) {
                    if (tile.x == x && tile.y == y) {
                        foodOnSnake = true;
                        break;
                    }
                }
            }
        } while (foodOnSnake);
    }

    public void draw(SpriteBatch spriteBatch) {
        spriteBatch.draw(foodTexture, x * tileSize, y * tileSize, tileSize, tileSize);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
