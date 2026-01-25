package io.github.dulidanci.lineofdominoes;

import com.badlogic.gdx.Game;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class LineOfDominoes extends Game {

    @Override
    public void create() {
        setScreen(new FirstScreen());
    }
}
