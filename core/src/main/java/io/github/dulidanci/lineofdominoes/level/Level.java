package io.github.dulidanci.lineofdominoes.level;

import com.badlogic.gdx.Gdx;
import io.github.dulidanci.lineofdominoes.assets.AtlasIds;
import io.github.dulidanci.lineofdominoes.domino.DominoSide;
import io.github.dulidanci.lineofdominoes.game.LineOfDominoes;
import io.github.dulidanci.lineofdominoes.level.movement.Direction;
import io.github.dulidanci.lineofdominoes.domino.Domino;
import io.github.dulidanci.lineofdominoes.render.DrawContext;
import io.github.dulidanci.lineofdominoes.util.Pair;
import io.github.dulidanci.lineofdominoes.level.movement.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Level {
    private final ArrayList<Pair<Position, Direction>> path;
    private final ArrayList<Pair<Position, Direction>> emptySpaces;
    private final Map<Position, Domino> dominoes;
    public float anchorX;
    public float anchorY;

    private Level(Builder builder) {
        this.path = builder.path;
        this.emptySpaces = builder.emptySpaces;
        this.dominoes = builder.dominoes;
        this.anchorX = 0.0f;
        this.anchorY = 0.0f;
    }

    public void render(float delta, DrawContext drawContext) {
        emptySpaces.forEach(pair ->
            drawContext.draw(drawContext.getAssetManager().get(AtlasIds.DOMINO.path(), AtlasIds.DOMINO.type()).findRegion("path_marker"),
            anchorX + pair.getFirst().x() * 24, anchorY + (pair.getFirst().y() + 3) * 24,
            12,  12, 24, 24, 1, 1, pair.getSecond().getTurnDegrees()));

        for (Map.Entry<Position, Domino> entry : this.dominoes.entrySet()) {
            drawContext.draw(drawContext.getAssetManager().get(AtlasIds.DOMINO.path(), AtlasIds.DOMINO.type()).findRegion(
                "domino_" + entry.getValue().getFirstSide().ordinal() + "_" + entry.getValue().getDirection().name().toLowerCase()),
                anchorX + entry.getKey().x() * 24, anchorY + (entry.getKey().y() + 3) * 24,
                12, 12, 24, 24, 1, 1, 0);
        }
    }

    public boolean canPlace(Domino domino) {
        return canPlace(domino, false);
    }

    public boolean canPlace(Domino domino, boolean log) {
        if (domino.getFirstSide() == null || domino.getSecondSide() == null) {
            if (log) Gdx.app.error("Level", "Tried to add a domino with missing side values!");
            return false;
        }

        if (!emptySpaces.contains(Pair.of(domino.getPosition(), domino.getDirection()))) {
            if (log) Gdx.app.error("Level", "Tried to add a domino to a position that is not included in emptySpaces");
            return false;
        }

        Domino checkedDomino = path.get(path.indexOf(Pair.of(domino.getPosition(), domino.getDirection())) - 1).equals(
            Pair.of(domino.getPosition().add(domino.getDirection().getVector()), domino.getDirection().getOpposite())) ? domino.flipped() : domino;

        return (!(dominoes.containsKey(path.get(path.indexOf(Pair.of(checkedDomino.getPosition(), checkedDomino.getDirection())) - 1).getFirst())) ||
                dominoes.get(path.get(path.indexOf(Pair.of(checkedDomino.getPosition(), checkedDomino.getDirection())) - 1).getFirst()).getFirstSide().equals(
                    checkedDomino.getFirstSide())) &&
            (!(dominoes.containsKey(path.get(path.indexOf(Pair.of(checkedDomino.getPosition(), checkedDomino.getDirection())) + 2).getFirst())) ||
                dominoes.get(path.get(path.indexOf(Pair.of(checkedDomino.getPosition(), checkedDomino.getDirection())) + 2).getFirst()).getFirstSide().equals(
                    checkedDomino.getSecondSide()));
    }

    public boolean canPlaceAnywhere(Pair<DominoSide, DominoSide> pair) {
        boolean result = false;
        for (Pair<Position, Direction> emptySpace : emptySpaces) {
            if (canPlace(new Domino(emptySpace.getFirst(), emptySpace.getSecond(), pair.getFirst(), pair.getSecond()))) {
                result = true;
                break;
            }
        }
        return result;
    }

    public void placeDomino(Domino domino) {
        if (!canPlace(domino, true)) {
            Gdx.app.error("Level", "Couldn't place domino due to previously mentioned reason. Dismissing domino");
            return;
        }

        Pair<Domino, Domino> halves = domino.split();
        dominoes.put(halves.getFirst().getPosition(), halves.getFirst());
        emptySpaces.remove(Pair.of(halves.getFirst().getPosition(), halves.getFirst().getDirection()));
        dominoes.put(halves.getSecond().getPosition(), halves.getSecond());
        emptySpaces.remove(Pair.of(halves.getSecond().getPosition(), halves.getSecond().getDirection()));
    }

    public int remainingSpaces() {
        return emptySpaces.size();
    }

    public Domino getFinishDomino() {
        Pair<Position, Direction> pair = path.get(path.size() - 1);
        return new Domino(
            pair.getFirst().add(pair.getSecond().getVector()).add(new Position(-LineOfDominoes.WIDTH, 0)),
            pair.getSecond().getOpposite(),
            dominoes.get(pair.getFirst().add(pair.getSecond().getVector())).getFirstSide(),
            dominoes.get(pair.getFirst()).getFirstSide());
    }

    public static class Builder {
        private final ArrayList<Pair<Position, Direction>> path;
        private final ArrayList<Pair<Position, Direction>> emptySpaces;
        private final Map<Position, Domino> dominoes;

        public Builder(ArrayList<Pair<Position, Direction>> path) {
            this.path = new ArrayList<>(path);
            this.emptySpaces = new ArrayList<>(path);
            this.dominoes = new HashMap<>();
        }

        public Builder addDominoList(ArrayList<Domino> dominoes) {
            dominoes.forEach(this::addDomino);
            return this;
        }

        public Builder addDomino(Domino domino) {
            if (domino.getFirstSide() == null || domino.getSecondSide() == null) {
                Gdx.app.error("Level.Builder", "Tried to add a domino with missing side values! Dismissing domino");
                return this;
            }

            Pair<Domino, Domino> halves = domino.split();
            if (emptySpaces.contains(Pair.of(halves.getFirst().getPosition(), halves.getFirst().getDirection()))) {
                dominoes.put(halves.getFirst().getPosition(), halves.getFirst());
                emptySpaces.remove(Pair.of(halves.getFirst().getPosition(), halves.getFirst().getDirection()));
                dominoes.put(halves.getSecond().getPosition(), halves.getSecond());
                emptySpaces.remove(Pair.of(halves.getSecond().getPosition(), halves.getSecond().getDirection()));
            } else {
                throw new IndexOutOfBoundsException("Tried to add a domino, that has incorrect position: " + halves.getFirst() + " " + halves.getSecond());
            }
            return this;
        }

        public Level build() {
            return new Level(this);
        }
    }
}
