package Controller;

import Model.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
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
    /**
     * The maximum time during which food remains fresh, in seconds.
     */
    private static final int FOOD_MAX_FRESH_TIME = 5;

    private static final int TARGET_FPS = 60;

    private static final long OPTIMAL_FRAME_TIME = 1_000_000_000 / TARGET_FPS;

    public Label bisetsLabel;
    public Label colombinsLabel;
    public Label ramiersLabel;

    public TextField bisetsAmount;
    public TextField colombinsAmount;
    public TextField ramiersAmount;

    public Button launchButton;

    public Canvas squareCanvas;

    private PigeonSquare mainApplication;

    private Square square;

    /**
     * Random generator used to for pigeons position and speed.
     */
    private Random random = new Random();

    private boolean simulationRunning = false;

    private double lastFpsTime = 0;

    private int fps = 0;

    /**
     * Create a new square and populates it with the specified amounts of each respective species.
     * @param bisetAmount       The amount of bisets to be populated within the square.
     * @param colombinAmount    The amount of colombins to be populated within the square.
     * @param ramierAmount      The amount of ramiers to be populated within the square.
     */
    private void createSquare(int bisetAmount, int colombinAmount, int ramierAmount) {
        square = new Square(600, 400);

        placeSpeciesRandomly(square, PigeonFactory.Species.BISET, bisetAmount);
        placeSpeciesRandomly(square, PigeonFactory.Species.COLOMBIN, colombinAmount);
        placeSpeciesRandomly(square, PigeonFactory.Species.RAMIER, ramierAmount);

        System.out.println("Square " + square + " created.");

        mainApplication.setSquare(square);
        mainApplication.showSquareScene();
    }

    /**
     * Places a given amount of a specified pigeon species on the given square.
     * @param square        The square where the new pigeons should be created.
     * @param species       The species to be created.
     * @param speciesAmount The amount of pigeons of that species to create.
     */
    private void placeSpeciesRandomly(Square square, PigeonFactory.Species species, int speciesAmount) {
        for (int i = 0; i < speciesAmount; i++) {
            // Positionate the pigeon randomly within the square limits, with a random speed.
            square.addPigeon(PigeonFactory.createPigeon(species,
                    random.nextInt(square.getWidth()),
                    random.nextInt(square.getHeight()),
                    random.nextDouble(),
                    square));
        }
    }

    public void launchSimulation() {
        simulationRunning = true;
        square.animatePigeons();

        (new Thread(new Runnable(){
            public void run(){
                updateSquare(square);
            }
        })).start();
    }

    public void updateSquare(Square square) {
        long lastLoopTime = System.nanoTime();

        while(simulationRunning) {
            long now = System.nanoTime();
            long updateLength = now - lastLoopTime;
            lastLoopTime = now;
            double delta = updateLength / ((double) OPTIMAL_FRAME_TIME);

            // Update the frame counter.
            lastFpsTime += updateLength;
            fps++;

            if (lastFpsTime >= 1_000_000_000)
            {
                System.out.println("(FPS: "+fps+")");
                lastFpsTime = 0;
                fps = 0;
            }

            simulate(delta);
            renderSquare(square);

            try{
                Thread.sleep( (lastLoopTime - System.nanoTime() + OPTIMAL_FRAME_TIME) / 1_000_000 );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void simulate(double delta) {
        for (Food food : square.getFoods()) {
            // Update the fresh state of all existing foods.
            food.rotten(delta);
        }
    }

    /**
     * Renders all the drawable objects contained within the square.
     */
    private void renderSquare(Square square) {
        if(squareCanvas == null)
            return;

        GraphicsContext graphicsContext = squareCanvas.getGraphicsContext2D();

        for(Pigeon pigeon : square.getPigeons()) {
            pigeon.draw(graphicsContext);
        }

        for(Food food : square.getFoods()) {
            food.draw(graphicsContext);
        }
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApplication
     */
    public void setMainApplication(PigeonSquare mainApplication) {
        this.mainApplication = mainApplication;
    }

    @FXML
    public void initialize() {
        if(bisetsAmount != null) {
            setNumericField(bisetsAmount);
            setNumericField(colombinsAmount);
            setNumericField(ramiersAmount);
        }

        if(launchButton != null) {
            launchButton.setOnMouseClicked(event -> {
                this.createSquare(Integer.parseInt(bisetsAmount.getText()),
                        Integer.parseInt(colombinsAmount.getText()),
                        Integer.parseInt(ramiersAmount.getText()));
            });
        }

        if(squareCanvas != null) {
            squareCanvas.setOnMouseClicked(event -> {
                // Instantiate new food at clicked position, with random decay time.
                square.addFood(new Food(((int) event.getX()), ((int) event.getY()),
                        random.nextInt(FOOD_MAX_FRESH_TIME) + ((int) System.currentTimeMillis() / 1_000)
                ));
            });
        }
    }

    private void setNumericField(TextField textField) {
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    bisetsAmount.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    public void setSquare(Square square) {
        this.square = square;
    }
}
