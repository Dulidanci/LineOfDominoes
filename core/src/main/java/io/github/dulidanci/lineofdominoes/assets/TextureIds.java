package io.github.dulidanci.lineofdominoes.assets;

import com.badlogic.gdx.graphics.Texture;

public enum TextureIds implements AssetId<Texture> {
    BACKGROUND("lineofdominoes/textures/backgrounds/background.png"),
    SUNSET_SMALL("lineofdominoes/textures/backgrounds/sunset_small.png");

    private final String path;

    TextureIds(String path) {
        this.path = path;
    }

    @Override
    public String path() {
        return this.path;
    }

    @Override
    public Class<Texture> type() {
        return Texture.class;
    }
}
