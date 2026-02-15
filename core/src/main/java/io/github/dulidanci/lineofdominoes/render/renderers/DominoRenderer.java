package io.github.dulidanci.lineofdominoes.render.renderers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import io.github.dulidanci.lineofdominoes.assets.AssetsLoader;
import io.github.dulidanci.lineofdominoes.domino.Domino;
import io.github.dulidanci.lineofdominoes.level.movement.Direction;
import io.github.dulidanci.lineofdominoes.level.movement.Position;
import io.github.dulidanci.lineofdominoes.render.RenderContext;
import io.github.dulidanci.lineofdominoes.render.RenderSystem;
import io.github.dulidanci.lineofdominoes.screen.widget.DominoWidget;
import io.github.dulidanci.lineofdominoes.screen.widget.Widget;
import io.github.dulidanci.lineofdominoes.util.Pair;

import java.util.ArrayList;
import java.util.Comparator;

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

        ArrayList<Widget> widgets = renderContext.uiManager.getWidgetList();
        widgets.sort(Comparator.comparing(widget -> widget.layer.ordinal()));
        for (Widget widget : widgets) {
            if (widget instanceof DominoWidget dominoWidget) {
                renderSystem.batch().draw(atlas.findRegion("domino", dominoWidget.sides.getFirst().ordinal()),
                    dominoWidget.x, dominoWidget.y, 0.5f, 0.5f,
                    1, 1, 1, 1, Direction.UP.getTurnDegrees());
                renderSystem.batch().draw(atlas.findRegion("domino", dominoWidget.sides.getSecond().ordinal()),
                    dominoWidget.x, dominoWidget.y + 1, 0.5f, 0.5f,
                    1, 1, 1, 1, Direction.DOWN.getTurnDegrees());
            }

            // todo: non-Domino widgets are not being rendered!!!!!!
        }
    }
}
