package io.github.dulidanci.lineofdominoes.tools;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class AtlasPacker {
    public static void main(String[] args) {
        TexturePacker.process(
            new TexturePacker.Settings(),
            "assets/lineofdominoes/textures/dominoes",
            "assets/lineofdominoes/textures/atlases",
            "domino"
        );
    }
}
