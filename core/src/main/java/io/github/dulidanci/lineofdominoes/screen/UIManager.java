package io.github.dulidanci.lineofdominoes.screen;

import io.github.dulidanci.lineofdominoes.input.InputSystem;
import io.github.dulidanci.lineofdominoes.screen.widget.DominoWidget;
import io.github.dulidanci.lineofdominoes.screen.widget.Widget;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class UIManager {
    private final List<Widget> dominoWidgets = new ArrayList<>();
    private final List<Widget> hoveredWidgets = new ArrayList<>();
    private Widget capturedWidget;

    public void addDomino(DominoWidget widget) {
        dominoWidgets.add(widget);
    }

    public void removeDomino(DominoWidget widget) {
        dominoWidgets.remove(widget);
    }

    public void clear() {
        dominoWidgets.clear();
    }

    public void update(float delta, InputSystem inputSystem) {
        for (Widget widget : dominoWidgets) {
            widget.update(delta);
        }

        if (capturedWidget != null) {
            capturedWidget.handleInput(delta, inputSystem);

            if (inputSystem.getMouse().leftJustReleased) {
                capturedWidget = null;
            }

            return;
        }

        hoveredWidgets.clear();

        dominoWidgets.forEach(widget -> {
            if (widget.contains(inputSystem.getMouse().worldX, inputSystem.getMouse().worldY) && !widget.isDisabled()) {
                hoveredWidgets.add(widget);
            }
        });

        hoveredWidgets.sort(Comparator.comparing(widget -> widget.getLayer().ordinal()));

        if (!hoveredWidgets.isEmpty()) {
            hoveredWidgets.get(0).handleInput(delta, inputSystem);

            if (inputSystem.getMouse().leftJustPressed) {
                capturedWidget = hoveredWidgets.get(0);
            }
        }
    }

    public ArrayList<Widget> getWidgetList() {
        return new ArrayList<>(dominoWidgets);
    }

    public void dispose() {
        this.clear();
    }
}
