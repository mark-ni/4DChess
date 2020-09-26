package sample;

import javafx.scene.layout.Background;
import javafx.scene.paint.Color;

public class Player {

    private Color color;
    private Background bg;
    private String name;
    private static int count = 0;
    public int num;

    public Player(String name) {
        count += 1;
        num = count;
        this.name = name;

        if (count == 1) {
            color = Color.BLACK;
            bg = Backgrounds.crimson;
        } else {
            color = Color.DODGERBLUE;
            bg = Backgrounds.navy;
        }
    }

    public Color getColor() {
        return color;
    }

    public Background getBg() {
        return bg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
