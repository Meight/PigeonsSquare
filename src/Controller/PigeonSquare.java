package Controller;

import Model.Square;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author Matthieu Le Boucher
 */
public class PigeonSquare extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;
    private Square square;

    public Square getSquare() {
        return square;
    }

    public void setSquare(Square square) {
        this.square = square;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("PigeonSquare");

        initRootLayout();

        showCreationScene();
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(PigeonSquare.class.getResource("/views/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }

    public void showCreationScene() {

        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(PigeonSquare.class.getResource("/views/Create.fxml"));
            AnchorPane gameCreator = (AnchorPane) loader.load();

            // Give the controller access to the main app.
            SquareController controller = loader.getController();
            controller.setMainApplication(this);

            // Set person overview into the center of root layout.
            rootLayout.setCenter(gameCreator);
        } catch (IOException e) {
            //.printStackTrace();
        }
    }

    public void showSquareScene() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(PigeonSquare.class.getResource("/views/View.fxml"));
            AnchorPane canvasView = (AnchorPane) loader.load();

            // Give the controller access to the main app.
            SquareController controller = loader.getController();
            controller.setSquare(square);
            controller.setMainApplication(this);

            rootLayout.setCenter(canvasView);

            controller.launchSimulation();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}