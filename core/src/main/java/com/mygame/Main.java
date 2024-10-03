package com.mygame;

import com.badlogic.gdx.Game;

public class Main extends Game {
    @Override
    public void create() {
        setScreen(new StarterScreen(this)); // Set the GameScreen as the current screen
    }
}
