package com.mygame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.Random;

public class Food {
    private int x, y;
    private int tileSize;
    private int boardWidth, boardHeight;
    private Random random;

    public Food(int tileSize, int boardWidth, int boardHeight) {
        this.tileSize = tileSize;
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
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

    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(x * tileSize, y * tileSize, tileSize, tileSize);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
