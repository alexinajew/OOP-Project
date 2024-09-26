package com.mygame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.Input;

public class Main extends ApplicationAdapter {
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private BitmapFont font;

    private int boardWidth = 600;
    private int boardHeight = 600;
    private int tileSize = 25;

    private Snake snake;
    private Food food;

    @Override
    public void create() {
        // Setup camera, batch, and font
        camera = new OrthographicCamera();
        camera.setToOrtho(false, boardWidth, boardHeight);

        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont();

        snake = new Snake(tileSize);
        food = new Food(tileSize, boardWidth, boardHeight);
        food.placeFood(snake.getSnakeBody()); // Place food initially
    }

    @Override
    public void render() {
        // Clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        // Draw grid, snake, and food
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        drawGrid();
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        food.draw(shapeRenderer);
        snake.draw(shapeRenderer);
        shapeRenderer.end();

        // Handle input and movement
        handleInput();
        snake.move();

        // Check for game over
        if (snake.isGameOver(boardWidth, boardHeight)) {
            renderGameOver();
        }

        // Check if snake eats food
        if (snake.checkCollision(food.getX(), food.getY())) {
            snake.grow();
            food.placeFood(snake.getSnakeBody());
        }

        // Draw score
        batch.begin();
        font.draw(batch, "Score: " + snake.getScore(), 10, 590);
        batch.end();
    }

    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.UP) && snake.getVelocityY() != -1) {
            snake.setVelocity(0, 1);
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && snake.getVelocityY() != 1) {
            snake.setVelocity(0, -1);
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && snake.getVelocityX() != 1) {
            snake.setVelocity(-1, 0);
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && snake.getVelocityX() != -1) {
            snake.setVelocity(1, 0);
        }
    }

    private void drawGrid() {
        shapeRenderer.setColor(Color.GRAY);
        for (int i = 0; i < boardWidth / tileSize; i++) {
            shapeRenderer.line(i * tileSize, 0, i * tileSize, boardHeight);
            shapeRenderer.line(0, i * tileSize, boardWidth, i * tileSize);
        }
    }

    private void renderGameOver() {
        batch.begin();
        font.setColor(Color.RED);
        font.draw(batch, "Game Over!", boardWidth / 2 - 40, boardHeight / 2);
        font.draw(batch, "Score: " + snake.getScore(), boardWidth / 2 - 40, boardHeight / 2 - 20);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
        font.dispose();
    }
}
