package Controller;

import Model.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.Random;

/**
 * @author : Matthieu Le Boucher
 * The intent of this class is to provide easy CRUD operations onto squares.
 */
public class SquareController {
    public Label bisetsLabel;
    public Label colombinsLabel;
    public Label ramiersLabel;

    public TextField bisetsAmount;
    public TextField colombinsAmount;
    public TextField ramiersAmount;

    public Button launchButton;

    public Canvas squareCanvas;

    private Square square;

    public void createSquare() {

    }

    /**
     * Create a new square and populates it with the specified amounts of each respective species.
     * @param bisetAmount       The amount of bisets to be populated within the square.
     * @param colombinAmount    The amount of colombins to be populated within the square.
     * @param ramierAmount      The amount of ramiers to be populated within the square.
     */
    private void createSquare(int bisetAmount, int colombinAmount, int ramierAmount) {
        square = new Square(600, 400);
        squareCanvas.setOnMouseClicked(event -> {
            square.addFood(new Food(((int) event.getX()), ((int) event.getY())));
        });

        placeSpeciesRandomly(square, Biset.class, bisetAmount);
        placeSpeciesRandomly(square, Colombin.class, colombinAmount);
        placeSpeciesRandomly(square, Ramier.class, ramierAmount);

        square.animatePigeons();
    }

    /**
     * Places a given amount of a specified pigeon species on the given square.
     * @param square        The square where the new pigeons should be created.
     * @param species       The species to be created.
     * @param speciesAmount The amount of pigeons of that species to create.
     */
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
        for(Food food : square.getFoods()) {
            // Update the fresh state of all existing foods.
            food.rotten(System.currentTimeMillis() / 1_000);
        }
    }

    /**
     * Renders all the drawable objects contained within the square.
     */
    private void renderSquare() {
        GraphicsContext graphicsContext = squareCanvas.getGraphicsContext2D();

        for(Pigeon pigeon : square.getPigeons()) {
            pigeon.draw(graphicsContext);
        }

        for(Food food : square.getFoods()) {
            food.draw(graphicsContext);
        }
    }
}
