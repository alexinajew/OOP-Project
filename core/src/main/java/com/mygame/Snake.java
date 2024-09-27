package com.mygame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class Snake {
    private ArrayList<Tile> snakeBody;
    private int velocityX, velocityY;
    private int tileSize;
    private boolean gameOver;
    private Texture snakeTexture; // Texture for the snake

    public Snake(int tileSize, Texture snakeTexture) {
        this.tileSize = tileSize;
        this.snakeTexture = snakeTexture; // Initialize snake texture
        snakeBody = new ArrayList<>();
        snakeBody.add(new Tile(5, 5));  // Initial snake head position
        velocityX = 1; // Initial velocity
        velocityY = 0;
        gameOver = false;
    }

    public void move() {
        // Move snake body
        for (int i = snakeBody.size() - 1; i > 0; i--) {
            snakeBody.get(i).x = snakeBody.get(i - 1).x;
            snakeBody.get(i).y = snakeBody.get(i - 1).y;
        }

        // Move snake head
        snakeBody.get(0).x += velocityX;
        snakeBody.get(0).y += velocityY;
    }

    public void draw(SpriteBatch spriteBatch) { // Change to accept SpriteBatch
        for (Tile tile : snakeBody) {
            spriteBatch.draw(snakeTexture, tile.x * tileSize, tile.y * tileSize, tileSize, tileSize);
        }
    }

    public boolean checkCollision(int foodX, int foodY) {
        return snakeBody.get(0).x == foodX && snakeBody.get(0).y == foodY;
    }

    public void grow() {
        snakeBody.add(new Tile(snakeBody.get(snakeBody.size() - 1).x, snakeBody.get(snakeBody.size() - 1).y));
    }

    public boolean isGameOver(int boardWidth, int boardHeight) {
        // Check if snake hits the wall or itself
        Tile head = snakeBody.get(0);
        if (head.x < 0 || head.x >= boardWidth / tileSize || head.y < 0 || head.y >= boardHeight / tileSize) {
            gameOver = true;
        }
        for (int i = 1; i < snakeBody.size(); i++) {
            if (head.x == snakeBody.get(i).x && head.y == snakeBody.get(i).y) {
                gameOver = true;
            }
        }
        return gameOver;
    }

    public ArrayList<Tile> getSnakeBody() {
        return snakeBody;
    }

    public int getVelocityX() {
        return velocityX;
    }

    public int getVelocityY() {
        return velocityY;
    }

    public void setVelocity(int velocityX, int velocityY) {
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }

    public int getScore() {
        return snakeBody.size() - 1;
    }
}
