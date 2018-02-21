package Controller;

import Model.Ramier;
import Model.Square;

/**
 * @author : Matthieu Le Boucher
 */
public class SquareController {
    private Square square;

    public void createSquare() {
        square = new Square(600, 400);
        Ramier myBeautifulRamier = new Ramier(30, 30);
    }

    public void updateSquare() {

    }

    public void renderSquare() {

    }
}
