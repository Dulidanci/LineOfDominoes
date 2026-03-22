package io.github.dulidanci.lineofdominoes.assets;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public enum AtlasIds implements AssetId<TextureAtlas> {
     DOMINO("lineofdominoes/textures/atlases/domino.atlas");

    private final String path;

    AtlasIds(String path) {
        this.path = path;
    }

    @Override
    public String path() {
        return this.path;
    }

    @Override
    public Class<TextureAtlas> type() {
        return TextureAtlas.class;
    }
}
