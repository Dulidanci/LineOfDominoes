package io.github.dulidanci.lineofdominoes.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public final class AssetsLoader {
    private static AssetManager manager;

    public static void init() {
        manager = new AssetManager();

        manager.load("lineofdominoes/textures/atlases/domino.atlas", TextureAtlas.class);
        manager.load("lineofdominoes/textures/backgrounds/background.png", Texture.class);

        manager.finishLoading();
    }

    public static TextureAtlas getAtlas() {
        return manager.get("lineofdominoes/textures/atlases/domino.atlas", TextureAtlas.class);
    }

    public static Texture getBackground() {
        return manager.get("lineofdominoes/textures/backgrounds/background.png", Texture.class);
    }

    public static void dispose() {
        manager.dispose();
    }
}
