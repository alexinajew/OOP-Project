package com.mygame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.Screen;

public class GameScreen implements Screen {
    private SpriteBatch batch;
    private Texture gridTexture;
    private Texture foodTexture;
    private Texture snakeTexture;
    private BitmapFont font;
    private Grid grid;
    private Food food;
    private Snake snake;
    private OrthographicCamera camera;
    private Viewport viewport;
    private float moveTimer = 0;
    private float moveDelay = 0.1f;
    private int tileSize = 20;
    private int boardWidth = 400;
    private int boardHeight = 400;
    private boolean isGameOver = false;

    @Override
    public void show() {
        batch = new SpriteBatch();
        gridTexture = new Texture(Gdx.files.internal("board.png"));
        foodTexture = new Texture(Gdx.files.internal("food.png"));
        snakeTexture = new Texture(Gdx.files.internal("snake.png"));
        font = new BitmapFont();

        camera = new OrthographicCamera();
        viewport = new FitViewport(boardWidth, boardHeight, camera);
        viewport.apply();

        grid = new Grid(boardWidth, boardHeight, gridTexture);
        food = new Food(tileSize, boardWidth, boardHeight, foodTexture);
        snake = new Snake(tileSize, snakeTexture);
        food.placeFood(snake.getSnakeBody());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        grid.draw(batch);
        food.draw(batch);
        snake.draw(batch);

        //score
        font.getData().setScale(1);  
        font.draw(batch, "Score: " + snake.getScore(), 10, boardHeight - 10);


        if (isGameOver) {
            renderGameOver(); 
        } else {
            handleInput();
            moveTimer += delta;
            if (moveTimer >= moveDelay) {
                snake.move();
                moveTimer = 0;

                if (snake.isGameOver(boardWidth, boardHeight)) {
                    isGameOver = true; 
                }

                if (snake.checkCollision(food.getX(), food.getY())) {
                    snake.grow();
                    food.placeFood(snake.getSnakeBody());
                }
            }
        }

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
        String gameOverMessage = "Game Over! Press R to Restart";
        GlyphLayout layout = new GlyphLayout(font, gameOverMessage);

        float x = (boardWidth - layout.width) / 2;
        float y = boardHeight / 2;

        font.setColor(1, 0, 0, 1);
        font.draw(batch, layout, x, y);

        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            restartGame();
        }
    }

    private void restartGame() {
        snake = new Snake(tileSize, snakeTexture);
        food.placeFood(snake.getSnakeBody());
        isGameOver = false;
        moveTimer = 0;
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        batch.dispose();
        gridTexture.dispose();
        foodTexture.dispose();
        snakeTexture.dispose();
        font.dispose();
    }
}
