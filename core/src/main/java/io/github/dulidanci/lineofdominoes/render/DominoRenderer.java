package io.github.dulidanci.lineofdominoes.render;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import io.github.dulidanci.lineofdominoes.assets.AssetsLoader;
import io.github.dulidanci.lineofdominoes.domino.Domino;
import io.github.dulidanci.lineofdominoes.level.movement.Direction;
import io.github.dulidanci.lineofdominoes.level.movement.Position;
import io.github.dulidanci.lineofdominoes.util.Pair;

import java.util.ArrayList;

public class DominoRenderer {
    private final RenderSystem renderSystem;

    public DominoRenderer(RenderSystem renderSystem) {
        this.renderSystem = renderSystem;
    }

    public void render(RenderContext renderContext) {
        TextureAtlas atlas = AssetsLoader.getAtlas();

        ArrayList<Pair<Position, Direction>> path = renderContext.level.getPath();
        for (Pair<Position, Direction> pair : path) {
            renderSystem.batch().draw(atlas.findRegion("path_marker"),
                pair.getFirst().x(), pair.getFirst().y() + 3,
                0.5f,  0.5f,
                1, 1, 1, 1, pair.getSecond().getTurnDegrees());
        }

        ArrayList<Domino> dominoes = renderContext.level.getDominoes();
        for (Domino domino : dominoes) {
            renderSystem.batch().draw(atlas.findRegion("domino", domino.getSide().ordinal()),
                domino.getPosition().x(),  domino.getPosition().y() + 3,
               0.5f,  0.5f,
                1, 1, 1, 1, domino.getDirection().getTurnDegrees());

        }

        ArrayList<Pair<Domino, Domino>> inventory = renderContext.inventory.getInventory();
        for (int i = 0; i < inventory.size(); i++) {
            renderSystem.batch().draw(atlas.findRegion("domino", inventory.get(i).getFirst().getSide().ordinal()),
                i * 2 + 0.5f, 0.5f, 0.5f, 0.5f,
                1, 1, 1, 1, inventory.get(i).getFirst().getDirection().getTurnDegrees());
            renderSystem.batch().draw(atlas.findRegion("domino", inventory.get(i).getSecond().getSide().ordinal()),
                i * 2 + 0.5f, 1.5f, 0.5f, 0.5f,
                1, 1, 1, 1, inventory.get(i).getSecond().getDirection().getTurnDegrees());
        }
    }
}
