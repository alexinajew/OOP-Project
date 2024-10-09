package com.mygame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class Snake {
    // List of tiles representing the snake's body
    private ArrayList<Tile> snakeBody;
    
    // Velocity of the snake (how it moves on the X and Y axes)
    private int velocityX, velocityY;
    
    // Size of each tile in the grid
    private int tileSize;
    
    // Boolean to track if the game is over
    private boolean gameOver;
    
    // Texture for the snake, used to display an image for each part of the snake's body
    private Texture snakeTexture;

    // Constructor initializes the snake's size, texture, and starting position
    public Snake(int tileSize, Texture snakeTexture) {
        this.tileSize = tileSize;
        this.snakeTexture = snakeTexture; // Assigns the texture to the snake
        snakeBody = new ArrayList<>(); // Initializes an empty snake body
        snakeBody.add(new Tile(5, 5));  // Snake starts at position (5, 5)
        velocityX = 1; // Snake initially moves right
        velocityY = 0;
        gameOver = false; // The game starts with no game-over condition
    }

    // Method to move the snake
    public void move() {
        // Moves each part of the snake to follow the part ahead of it
        for (int i = snakeBody.size() - 1; i > 0; i--) {
            snakeBody.get(i).x = snakeBody.get(i - 1).x;
            snakeBody.get(i).y = snakeBody.get(i - 1).y;
        }

        // Updates the head of the snake to move in the direction of the velocity
        snakeBody.get(0).x += velocityX;
        snakeBody.get(0).y += velocityY;
    }

    // Draws the snake on the screen using the SpriteBatch (LibGDX)
    public void draw(SpriteBatch spriteBatch) {
        // Loops through each tile of the snake's body and draws it
        for (Tile tile : snakeBody) {
            spriteBatch.draw(snakeTexture, tile.x * tileSize, tile.y * tileSize, tileSize, tileSize);
        }
    }

    // Checks if the snake has collided with the food at (foodX, foodY)
    public boolean checkCollision(int foodX, int foodY) {
        // Compares the head of the snake to the food's position
        return snakeBody.get(0).x == foodX && snakeBody.get(0).y == foodY;
    }

    // Grows the snake by adding a new tile at the current tail's position
    public void grow() {
        // Adds a new tile at the end of the snake, same position as the last tile
        snakeBody.add(new Tile(snakeBody.get(snakeBody.size() - 1).x, snakeBody.get(snakeBody.size() - 1).y));
    }

    // Checks if the game is over (snake hits the wall or itself)
    public boolean isGameOver(int boardWidth, int boardHeight) {
        // Retrieves the head of the snake
        Tile head = snakeBody.get(0);
        
        // Checks if the head hits the wall
        if (head.x < 0 || head.x >= boardWidth / tileSize || head.y < 0 || head.y >= boardHeight / tileSize) {
            gameOver = true; // Game is over if the head hits the wall
        }

        // Checks if the snake collides with its own body
        for (int i = 1; i < snakeBody.size(); i++) {
            if (head.x == snakeBody.get(i).x && head.y == snakeBody.get(i).y) {
                gameOver = true; // Game is over if the head hits the body
            }
        }
        return gameOver;
    }

    // Getter for the snake's body (used for external access)
    public ArrayList<Tile> getSnakeBody() {
        return snakeBody;
    }

    // Getters and setters for snake velocity (control the movement)
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

    // Method to calculate the score, which is the snake's length minus 1 (starting size)
    public int getScore() {
        return snakeBody.size() - 1;
    }
}
