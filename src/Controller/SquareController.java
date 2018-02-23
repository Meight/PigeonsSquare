package Controller;

import Model.*;

import java.util.Random;

/**
 * @author : Matthieu Le Boucher
 */
public class SquareController {
    private Square square;

    public void createSquare(int bisetAmount, int colombinAmount, int ramierAmount) {
        square = new Square(600, 400);

        placeSpeciesRandomly(square, Biset.class, bisetAmount);
        placeSpeciesRandomly(square, Colombin.class, colombinAmount);
        placeSpeciesRandomly(square, Ramier.class, ramierAmount);

        square.animatePigeons();
    }

    private void placeSpeciesRandomly(Square square, Class<? extends Pigeon> species, int speciesAmount) {
        Random random = new Random();
        for (int i = 0; i < speciesAmount; i++) {
            try {
                Pigeon newPigeon = species.newInstance();

                // Positionate the pigeon randomly within the square limits.
                newPigeon.x = random.nextInt(square.getWidth());
                newPigeon.y = random.nextInt(square.getHeight());

                square.addPigeon(newPigeon);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateSquare() {

    }

    public void renderSquare() {

    }
}
