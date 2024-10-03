package com.mygame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input; // Import for Input handling
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont; // Import for font rendering
import com.badlogic.gdx.graphics.g2d.GlyphLayout; // For text layout
import com.badlogic.gdx.graphics.GL20; // Import for OpenGL constants
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture gridTexture;
    private Texture foodTexture;
    private Texture snakeTexture; // Texture for the snake
    private Texture menuBackgroundTexture; // Texture for main menu background
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
    private boolean gameOver = false; // Track game over state
    private boolean inMainMenu = true; // Track if in main menu

    @Override
    public void create() {
        batch = new SpriteBatch();
        // Load textures
        gridTexture = new Texture(Gdx.files.internal("board.png")); // Update this path
        foodTexture = new Texture(Gdx.files.internal("food.png")); // Update this path
        snakeTexture = new Texture(Gdx.files.internal("snake.png")); // Update this path
        menuBackgroundTexture = new Texture(Gdx.files.internal("menu_background.png")); // Background for main menu

        // Load font
        font = new BitmapFont(); // Using the default system font

        // Setup camera and viewport
        camera = new OrthographicCamera();
        viewport = new FitViewport(boardWidth, boardHeight, camera); // Using FitViewport
        viewport.apply(); // Apply the viewport to the camera
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

        if (inMainMenu) {
            renderMainMenu(); // Render the main menu
        } else {
            // Handle input and movement
            handleInput();
            
            // Update snake movement
            if (!gameOver) {
                moveTimer += Gdx.graphics.getDeltaTime();
                if (moveTimer >= moveDelay) {
                    snake.move(); // Move snake only when the delay is exceeded
                    moveTimer = 0; // Reset the timer
                }

                // Check if snake eats food
                if (snake.checkCollision(food.getX(), food.getY())) {
                    snake.grow();
                    food.placeFood(snake.getSnakeBody());
                }

                // Check for game over
                if (snake.isGameOver(boardWidth, boardHeight)) {
                    gameOver = true; // Set game over state
                }
            }

            // Draw everything
            batch.begin();
            grid.draw(batch); // Draw grid
            food.draw(batch); // Draw food
            snake.draw(batch); // Draw snake

            if (gameOver) {
                renderGameOver(); // Draw game over message
            }
            batch.end();
        }
    }

    private void renderMainMenu() {
        batch.begin();
        batch.draw(menuBackgroundTexture, 0, 0, boardWidth, boardHeight); // Draw background
        renderMenuText(); // Draw menu text
        batch.end();

        handleMenuInput(); // Handle input for the menu
    }

    private void renderMenuText() {
        // Set the font color (optional)
        font.setColor(1, 1, 1, 1); // White color for text
        
        // Render "Snake Game" title
        String title = "Snake Game";
        GlyphLayout layout = new GlyphLayout(font, title);
        float x = (boardWidth - layout.width) / 2; // Center the title
        float y = boardHeight - 50; // Position the title near the top
        
        font.draw(batch, layout, x, y); // Draw the title

        // Render "Press Space to Start" instruction
        String instruction = "Press SPACE to Start";
        GlyphLayout instructionLayout = new GlyphLayout(font, instruction);
        x = (boardWidth - instructionLayout.width) / 2; // Center the instruction
        y = boardHeight / 2; // Position vertically
        
        font.draw(batch, instructionLayout, x, y); // Draw the instruction
    }

    private void handleMenuInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            startGame(); // Start the game on SPACE key press
        }
    }

    private void startGame() {
        inMainMenu = false; // Exit the main menu
        initializeGame(); // Initialize game elements
    }

    private void initializeGame() {
        grid = new Grid(boardWidth, boardHeight, tileSize, gridTexture);
        food = new Food(tileSize, boardWidth, boardHeight, foodTexture);
        snake = new Snake(tileSize, snakeTexture);
        food.placeFood(snake.getSnakeBody()); // Place food at a valid location
        gameOver = false; // Reset game over state
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
        // Set the font color (optional)
        font.setColor(1, 0, 0, 1); // Red color for text
        
        // Render "Game Over" message
        String gameOverMessage = "Game Over! Press R to Restart";
        GlyphLayout layout = new GlyphLayout(font, gameOverMessage);
        float x = (boardWidth - layout.width) / 2; // Center the text
        float y = boardHeight / 2; // Position vertically
        
        font.draw(batch, layout, x, y); // Draw the text

        // Handle restart input
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            restartGame(); // Restart the game
        }
    }

    private void restartGame() {
        initializeGame(); // Reinitialize the game state
        inMainMenu = false; // Exit main menu if it was active
    }

    @Override
    public void dispose() {
        batch.dispose();
        gridTexture.dispose(); // Dispose grid texture
        foodTexture.dispose(); // Dispose food texture
        snakeTexture.dispose(); // Dispose snake texture
        menuBackgroundTexture.dispose(); // Dispose menu background texture
        font.dispose(); // Dispose font
    }
}
