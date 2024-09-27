package com.mygame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Random;

public class Food {
    private int x, y;
    private int tileSize;
    private int boardWidth, boardHeight;
    private Random random;
    private Texture foodTexture; // Texture for the food

    public Food(int tileSize, int boardWidth, int boardHeight, Texture foodTexture) {
        this.tileSize = tileSize;
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        this.foodTexture = foodTexture; // Initialize food texture
        random = new Random();
        placeFood(new ArrayList<>());
    }

    public void placeFood(ArrayList<Tile> snakeBody) {
        boolean validPlacement = false;
        while (!validPlacement) {
            x = random.nextInt(boardWidth / tileSize);
            y = random.nextInt(boardHeight / tileSize);

            validPlacement = true;
            for (Tile part : snakeBody) {
                if (part.x == x && part.y == y) {
                    validPlacement = false;
                    break;
                }
            }
        }
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
