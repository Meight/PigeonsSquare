package Model;

import javafx.scene.paint.Color;

/**
 * Created by lefou on 20/02/2018.
 * @author Matthieu Le Boucher
 */
public class Colombin extends Pigeon {

    public Colombin(int x, int y) {
        super(x, y);
    }

    @Override
    public Color getColor() {
        return COLOMBIN_COLOR;
    }

    @Override
    public void run() {
        System.out.println("LOL COLOMBIN");
    }
}
