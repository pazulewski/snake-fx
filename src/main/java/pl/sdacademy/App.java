package pl.sdacademy;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {
    private static final int CANVAS_WIDTH = 400;
    private static final int CANVAS_HEIGHT = 400;
    private static final int POINT_SIZE = 20;
    private GraphicsContext graphicsContext;
    private SnakeGame snakeGame;

    @Override
    public void start(Stage window) {
        initializeUserInterface(window);
        initializeSnakeGame();
    }

    private void initializeSnakeGame() {
        SnakeGameJavaFxPrinter snakeGamePrinter = new SnakeGameJavaFxPrinter(graphicsContext, POINT_SIZE);
        int xBound = CANVAS_WIDTH / POINT_SIZE;
        int yBound = CANVAS_HEIGHT / POINT_SIZE;
        snakeGame = new SnakeGame(xBound, yBound, snakeGamePrinter);
        Thread thread = new Thread(snakeGame::start);
        thread.setDaemon(true);
        thread.start();
    }

    private void initializeUserInterface(Stage window) {
        HBox hBox = new HBox();
        Button btnUp = new Button("góra");
        btnUp.setFocusTraversable(false);
        btnUp.setOnAction((event -> snakeGame.setSnakeDirection(Direction.UP)));
        Button btnLeft = new Button("lewo");
        btnLeft.setFocusTraversable(false);
        btnLeft.setOnAction((event -> snakeGame.setSnakeDirection(Direction.LEFT)));
        Button btnRight = new Button("prawo");
        btnRight.setFocusTraversable(false);
        btnRight.setOnAction((event -> snakeGame.setSnakeDirection(Direction.RIGHT)));
        Button btnDown = new Button("dół");
        btnDown.setFocusTraversable(false);
        btnDown.setOnAction(event -> snakeGame.setSnakeDirection(Direction.DOWN));
        hBox.getChildren().addAll(
                btnUp,
                btnLeft,
                btnRight,
                btnDown
        );
        hBox.setAlignment(Pos.CENTER);
        Canvas canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        graphicsContext = canvas.getGraphicsContext2D();
        VBox vBox = new VBox(canvas, hBox);
        Scene scene = new Scene((vBox));
        scene.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()) {
                case LEFT:
                    snakeGame.setSnakeDirection(Direction.LEFT);
                    break;

                case RIGHT:
                    snakeGame.setSnakeDirection(Direction.RIGHT);
                    break;

                case UP:
                    snakeGame.setSnakeDirection(Direction.UP);
                    break;

                case DOWN:
                    snakeGame.setSnakeDirection(Direction.DOWN);
                    break;
            }
        });
        window.setScene(scene);
        window.show();
    }

    public static void main(String[] args) {
        launch();
    }

}