package io.github.dulidanci.lineofdominoes.screen;

import io.github.dulidanci.lineofdominoes.input.InputSystem;
import io.github.dulidanci.lineofdominoes.screen.widget.DominoWidget;
import io.github.dulidanci.lineofdominoes.screen.widget.Widget;

import java.util.ArrayList;
import java.util.List;

public class UIManager {
    private final List<Widget> screenElements = new ArrayList<>();
    private final List<DominoWidget> dominoWidgets = new ArrayList<>();

    public void addElement(Widget widget) {
        screenElements.add(widget);
    }

    public void addDomino(DominoWidget widget) {
        dominoWidgets.add(widget);
    }

    public void removeElement(Widget widget) {
        screenElements.remove(widget);
    }

    public void removeDomino(DominoWidget widget) {
        dominoWidgets.remove(widget);
    }

    public void clear() {
        screenElements.clear();
        dominoWidgets.clear();
    }

    public void update(float delta, InputSystem inputSystem) {
        for (Widget widget : screenElements) {
            widget.update(delta, inputSystem);
        }
        for (DominoWidget dominoWidget : dominoWidgets) {
            dominoWidget.update(delta, inputSystem);
        }
    }

    public ArrayList<Widget> getWidgetList() {
        ArrayList<Widget> widgetList = new ArrayList<>(screenElements);
        widgetList.addAll(dominoWidgets);
        return widgetList;
    }

    public void dispose() {
        this.clear();
    }
}
