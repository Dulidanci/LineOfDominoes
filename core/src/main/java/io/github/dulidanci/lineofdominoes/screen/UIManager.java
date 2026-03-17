package io.github.dulidanci.lineofdominoes.screen;

import io.github.dulidanci.lineofdominoes.assets.AssetsLoader;
import io.github.dulidanci.lineofdominoes.input.InputSystem;
import io.github.dulidanci.lineofdominoes.level.movement.Direction;
import io.github.dulidanci.lineofdominoes.render.DrawContext;
import io.github.dulidanci.lineofdominoes.screen.widget.ClickableWidget;
import io.github.dulidanci.lineofdominoes.screen.widget.DominoWidget;
import io.github.dulidanci.lineofdominoes.screen.widget.Widget;

import java.util.*;

public class UIManager {
    private final List<DominoWidget> dominoWidgets = new ArrayList<>();
    private OptionalInt focusedDomino = OptionalInt.empty();
    private final List<Widget> hoveredWidgets = new ArrayList<>();
    private ClickableWidget capturedWidget;

    public void addDomino(DominoWidget widget) {
        dominoWidgets.add(widget);
    }

    public void addDomino(int index, DominoWidget widget) {
        if (index < 0 || index > dominoWidgets.size()) return;
        if (index == dominoWidgets.size()) {
            addDomino(widget);
            return;
        }
        dominoWidgets.add(index, widget);
    }

    public void removeDomino(int index) {
        if (index < 0 || index >= dominoWidgets.size()) {
            throw new ArrayIndexOutOfBoundsException("Tried to remove domino from invalid index: " + index);
        }
        dominoWidgets.remove(index);
    }

    public void clear() {
        dominoWidgets.clear();
    }

    public void update(float delta, InputSystem inputSystem) {
        // update every widget
        for (Widget widget : dominoWidgets) {
            widget.update(delta);
        }

        // handle input on already captured widget
        if (capturedWidget != null) {
            capturedWidget.handleInput(delta, inputSystem);

            if (inputSystem.getMouse().leftJustReleased) {
                capturedWidget = null;
            }

            return;
        }

        // handle input when no widget were selected
        hoveredWidgets.clear();

        dominoWidgets.forEach(widget -> {
            if (widget.contains(inputSystem.getMouse().worldX, inputSystem.getMouse().worldY) && !widget.isDisabled()) {
                hoveredWidgets.add(widget);
            }
        });

        hoveredWidgets.sort(Comparator.comparing(widget -> widget.getLayer().ordinal()));

        if (!hoveredWidgets.isEmpty() && hoveredWidgets.get(0) instanceof ClickableWidget clickableWidget) {
            clickableWidget.handleInput(delta, inputSystem);

            if (inputSystem.getMouse().leftJustPressed) {
                capturedWidget = clickableWidget;
            }
        }
    }

    public void render(float delta, DrawContext drawContext) {
        if (focusedDomino.isPresent()) {
            DominoWidget dominoWidget = dominoWidgets.get(focusedDomino.getAsInt());

            Direction rotation = dominoWidget.getRotation();
            float centerX = dominoWidget.getAnchorX() + dominoWidget.getWidth() / 2;
            float centerY = dominoWidget.getAnchorY() + dominoWidget.getHeight() / 2;

            drawContext.draw(AssetsLoader.getAtlas().findRegion("path_marker"),
                centerX - 12 + rotation.getOpposite().getVector().x() * 12, centerY - 12 + rotation.getOpposite().getVector().y() * 12,
                12, 12, 24, 24, 1.25f, 1.25f,rotation.getTurnDegrees());
            drawContext.draw(AssetsLoader.getAtlas().findRegion("path_marker"),
                centerX - 12 + rotation.getVector().x() * 12, centerY - 12 + rotation.getVector().y() * 12,
                12, 12, 24, 24, 1.25f, 1.25f, rotation.getOpposite().getTurnDegrees());
        }

        dominoWidgets.forEach(widget -> widget.render(delta, drawContext));

        if (capturedWidget != null) {
            capturedWidget.render(delta, drawContext);
        }
    }

    public void focusDomino(int index) {
        if (index < 0 || index >= dominoWidgets.size()) {
            throw new ArrayIndexOutOfBoundsException("Tried to focus on slot outside of a valid interval: " + index);
        }
        if (focusedDomino.isPresent() && focusedDomino.getAsInt() != index) {
            dominoWidgets.get(focusedDomino.getAsInt()).rotate(Direction.UP);
        }
        focusedDomino = OptionalInt.of(index);
    }

    public void unfocusDomino() {
        if (focusedDomino.isPresent() && focusedDomino.getAsInt() >= 0 && focusedDomino.getAsInt() < dominoWidgets.size()) {
            dominoWidgets.get(focusedDomino.getAsInt()).rotate(Direction.UP);
        }
        focusedDomino = OptionalInt.empty();
    }

    public DominoWidget getFocusedDomino() {
        if (focusedDomino.isPresent()) {
            return dominoWidgets.get(focusedDomino.getAsInt());
        }
        return null;
    }

    public void dispose() {
        this.clear();
    }
}
