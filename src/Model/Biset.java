package Model;

import javafx.scene.paint.Color;

/**
 * Created by lefou on 20/02/2018.
 * @author Matthieu Le Boucher
 */
public class Biset extends Pigeon{
    public Biset(int x, int y) {
        super(x, y);
    }

    @Override
    public Color getColor() {
        return BISET_COLOR;
    }

    @Override
    public void run() {
        System.out.println("LOL BISET");
    }
}
