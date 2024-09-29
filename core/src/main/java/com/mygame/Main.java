package com.mygame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input; // Import for Input handling
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont; // Import for font rendering
import com.badlogic.gdx.graphics.g2d.GlyphLayout; // For text layout
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.GL20; // Import for OpenGL constants
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private Texture gridTexture;
    private Texture foodTexture;
    private Texture snakeTexture; // Texture for the snake
    private BitmapFont font; // Font for rendering text
    private Grid grid;
    private Food food;
    private Snake snake;
    private OrthographicCamera camera;
    private Viewport viewport; // Viewport to manage camera settings
    private float moveTimer = 0;
    private float moveDelay = 0.1f; // Adjust to change the speed of the snake

    private int tileSize = 20; // Adjust tile size if needed
    private int boardWidth = 400; // Adjust board width
    private int boardHeight = 400; // Adjust board height

    @Override
    public void create() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        // Load textures
        gridTexture = new Texture(Gdx.files.internal("board.png")); // Update this path
        foodTexture = new Texture(Gdx.files.internal("food.png")); // Update this path
        snakeTexture = new Texture(Gdx.files.internal("snake.png")); // Update this path

        // Load font
        font = new BitmapFont(); // Using the default system font

        grid = new Grid(boardWidth, boardHeight, tileSize, gridTexture);
        food = new Food(tileSize, boardWidth, boardHeight, foodTexture);
        snake = new Snake(tileSize, snakeTexture);

        camera = new OrthographicCamera();
        viewport = new FitViewport(boardWidth, boardHeight, camera); // Using FitViewport
        viewport.apply(); // Apply the viewport to the camera

        food.placeFood(snake.getSnakeBody()); // Place food
    }

    @Override
    public void render() {
        // Clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update and apply the camera
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        camera.update();
        batch.setProjectionMatrix(camera.combined); // Set projection matrix for batch

        // Handle input and movement
        handleInput();

        // Update this and in gameover//snake.java
        moveTimer += Gdx.graphics.getDeltaTime();
        if (moveTimer >= moveDelay) {
            snake.move();
            moveTimer = 0;


            if (snake.isGameOver(boardWidth, boardHeight)) {
                renderGameOver();
                return; // Stop further game logic on game over
            }

            // Check if snake eats food
            if (snake.checkCollision(food.getX(), food.getY())) {
                snake.grow();
                food.placeFood(snake.getSnakeBody());
            }
        }

        // Draw everything
        batch.begin();
        grid.draw(batch); // Draw grid
        food.draw(batch); // Draw food
        snake.draw(batch); // Draw snake
        batch.end();
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && snake.getVelocityY() != -1) {
            snake.setVelocity(0, 1);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) && snake.getVelocityY() != 1) {
            snake.setVelocity(0, -1);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT) && snake.getVelocityX() != 1) {
            snake.setVelocity(-1, 0);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) && snake.getVelocityX() != -1) {
            snake.setVelocity(1, 0);
        }
    }

    private void renderGameOver() {
        batch.begin();
        
        // Set the font color (optional)
        font.setColor(1, 0, 0, 1); // Red color for text
        
        // Render "Game Over" message
        String gameOverMessage = "Game Over! Press R to Restart";
        GlyphLayout layout = new GlyphLayout(font, gameOverMessage);
        float x = (boardWidth - layout.width) / 2; // Center the text
        float y = boardHeight / 2; // Position vertically
        
        font.draw(batch, layout, x, y); // Draw the text

        batch.end();
        
        // Handle restart input
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            restartGame(); // Implement this method to restart the game
        }
    }

    private void restartGame() {
        snake = new Snake(tileSize, snakeTexture); // Reinitialize snake
        food.placeFood(snake.getSnakeBody()); // Reinitialize food
        // Any additional reset logic here
    }

    @Override
    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
        gridTexture.dispose(); // Dispose grid texture
        foodTexture.dispose(); // Dispose food texture
        snakeTexture.dispose(); // Dispose snake texture
        font.dispose(); // Dispose font
    }
}
