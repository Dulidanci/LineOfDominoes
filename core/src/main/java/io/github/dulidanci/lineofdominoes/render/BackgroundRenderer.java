package io.github.dulidanci.lineofdominoes.render;

import com.badlogic.gdx.graphics.Texture;
import io.github.dulidanci.lineofdominoes.assets.AssetsLoader;

public class BackgroundRenderer {
    private final RenderSystem renderSystem;

    public BackgroundRenderer(RenderSystem renderSystem) {
        this.renderSystem = renderSystem;
    }

    public void render(RenderContext renderContext) {
        Texture texture = AssetsLoader.getBackground();
        // todo: render actual background based on theme
//        renderSystem.batch().draw(texture, -1.5f, 0, 21.78f, 14);
    }
}
