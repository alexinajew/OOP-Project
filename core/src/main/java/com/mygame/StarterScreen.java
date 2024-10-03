package com.mygame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Screen;

public class StarterScreen implements Screen {
    private Main game; // Reference to the main game
    private SpriteBatch batch;
    private Texture startTexture; // Texture for the starter screen

    public StarterScreen(Main game) {
        this.game = game;
        batch = new SpriteBatch();
        startTexture = new Texture(Gdx.files.internal("Starter Screen.png")); // Load your starter PNG
    }

    @Override
    public void show() {
        // Any initialization can be done here
    }

    @Override
    public void render(float delta) {
        // Clear the screen with a black color
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Draw the starter texture
        batch.begin();
        batch.draw(startTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        // Check for any key press to start the game
        if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
            game.setScreen(new GameScreen()); // Switch to GameScreen when any key is pressed
        }
    }

    @Override
    public void resize(int width, int height) {}

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
        startTexture.dispose(); // Dispose of the starter texture
    }
}
