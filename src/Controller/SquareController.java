package Controller;

import Model.*;
import Model.Bird.Pigeon;
import Model.Bird.PigeonFactory;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;

import java.util.Random;

/**
 * @author : Matthieu Le Boucher
 * The intent of this class is to provide control over the square model and render the simulation in the graphical
 * application.
 */
public class SquareController {
    /**
     * The maximum time during which food remains fresh, in seconds.
     */
    private static final int FOOD_MAX_FRESH_TIME = 5;

    /**
     * The amount of time, in seconds, that a cracker is considered an active threat.
     */
    public static final int CRACKER_THREAT_TIME = 3;

    /**
     * The upper bound of the speed interval of any pigeon in the square.
     */
    private static final int MINIMAL_PIGEON_SPEED = 3;

    /**
     * The upper bound of the speed interval of any pigeon in the square.
     */
    private static final int MAXIMAL_PIGEON_SPEED = 5;

    /**
     * The intended amount of frames per seconds for both simulation and rendering.
     */
    private static final int TARGET_FPS = 30;

    /**
     * Stores the optimal time of a frame in nanoseconds.
     */
    public static final long OPTIMAL_FRAME_TIME = 1_000_000_000 / TARGET_FPS;

    /**
     * View labels handles.
     */
    public Label bisetsLabel;
    public Label colombinsLabel;
    public Label ramiersLabel;

    /**
     * View text fields handles.
     */
    public TextField bisetsAmount;
    public TextField colombinsAmount;
    public TextField ramiersAmount;

    /**
     * View buttons handles.
     */
    public Button launchButton;

    /**
     * Graphical canvas handle.
     */
    public Canvas squareCanvas;

    /**
     * Handle to the main graphical application.
     */
    private PigeonSquare mainApplication;

    /**
     * The square model used for the simulation.
     */
    private Square square;

    /**
     * Random generator used to for pigeons position and speed.
     */
    private Random random = new Random();

    /**
     * Whether or not the simulation is currently running.
     */
    private boolean simulationRunning = false;

    /**
     * The last system time at which the PFS counter was triggered.
     */
    private double lastFpsTime = 0;

    /**
     * The current amount of FPS computed during the last second.
     */
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
                    MAXIMAL_PIGEON_SPEED * random.nextDouble() + MINIMAL_PIGEON_SPEED,
                    square));
        }
    }

    /**
     * Launches the simulation in a separate thread as well as all the separate threads embodied by pigeons.
     */
    void launchSimulation() {
        simulationRunning = true;
        square.animatePigeons();

        (new Thread(new Runnable(){
            public void run(){
                updateSquare(square);
            }
        })).start();
    }

    /**
     * Main pseudo-infinite loop that holds the logic of the simulation update,
     * including model evolution and rendering.
     * @param square The square to update
     */
    private void updateSquare(Square square) {
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

            simulate(updateLength);
            renderSquare(square);

            try{
                Thread.sleep( (lastLoopTime - System.nanoTime() + OPTIMAL_FRAME_TIME) / 1_000_000 );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Simulates the model part of the application.
     * @param delta The amount of time elapsed since last loop.
     */
    private synchronized void simulate(double delta) {
        for (Food food : square.getFoods()) {
            // Update the fresh state of all existing foods.
            //food.rotten(delta);
        }
    }

    /**
     * Renders all the drawable objects contained within the square.
     */
    private synchronized void renderSquare(Square square) {
        if(squareCanvas == null)
            return;

        GraphicsContext graphicsContext = squareCanvas.getGraphicsContext2D();
        graphicsContext.clearRect(0, 0, square.getWidth(), square.getHeight());

        for(Cracker cracker : square.getCrackers()) {
            cracker.draw(graphicsContext);
        }

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

    /**
     * Hook for JavaFX that links all view handles to their graphical instance.
     */
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
                if(event.getButton() == MouseButton.PRIMARY) {
                    // Instantiate new food at clicked position, with random decay time.
                    square.addFood(new Food(((int) event.getX()), ((int) event.getY()),
                            random.nextInt(3) + 1, square));
                } else {
                    square.addCracker(new Cracker(((int) event.getX()), ((int) event.getY()),
                            random.nextInt(50) + 50, 0, random.nextInt(3) + 1, square));
                }
            });
        }
    }

    /**
     * Modifies a text field to make it accept only numerical inputs.
     * @param textField The text field to modify.
     */
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
