package src;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.util.Duration;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main extends Application {

    private Label fileLabel;
    private GridPane boardView;
    private Board boardToSolve;
    private final Map<Character, Color> colorMap = new HashMap<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        colorMap.put('P', Color.RED);
        primaryStage.setTitle("Rush Hour Solver");

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(15));

        VBox topSection = new VBox(10);
        topSection.setPadding(new Insets(10));
        Button loadButton = new Button("Load Board File");
        fileLabel = new Label("No file loaded.");

        loadButton.setOnAction(e -> loadBoardFile(primaryStage));

        topSection.getChildren().addAll(loadButton, fileLabel);

        boardView = new GridPane();
        boardView.setHgap(2);
        boardView.setVgap(2);
        boardView.setAlignment(Pos.CENTER);

        HBox bottomSection = new HBox(10);
        bottomSection.setPadding(new Insets(10));
        bottomSection.setAlignment(Pos.CENTER_LEFT);


        ComboBox<String> algoSelector = new ComboBox<>();
        algoSelector.getItems().addAll("GBFS", "UCS", "A*");
        algoSelector.setValue("GBFS");


        Button solveButton = new Button("Solve");


        Label algoStatus = new Label("Algorithm: GBFS");


        algoSelector.setOnAction(e -> {
            String selected = algoSelector.getValue();
            algoStatus.setText("Algorithm: " + selected);
        });


        solveButton.setOnAction(e -> {
            String selectedAlgo = algoSelector.getValue();
            algoStatus.setText("Solving with " + selectedAlgo + "...");
            if (this.boardToSolve == null) {
                algoStatus.setText("Please load a board first.");
                return;
            }
            Solver solver = new Solver();
            Board goalBoard = solver.GameSolver(this.boardToSolve, selectedAlgo);

            if (goalBoard == null) {
                algoStatus.setText("No solution found.");
                return;
            }

            List<Board> steps = solver.getResultInOrder(goalBoard);

            // Animate each step
            Timeline timeline = new Timeline();
            int delayMillis = 300;

            for (int i = 0; i < steps.size(); i++) {
                Board boardStep = steps.get(i);
                KeyFrame keyFrame = new KeyFrame(Duration.millis(i * delayMillis), event -> {
                    renderBoard(boardStep);
                });
                timeline.getKeyFrames().add(keyFrame);
            }

            timeline.setOnFinished(ev -> algoStatus.setText("Solved in " + (steps.size() - 1) + " moves using " + selectedAlgo));
            timeline.play();
        });

        bottomSection.getChildren().addAll(algoSelector, solveButton, algoStatus);

        root.setTop(topSection);
        root.setCenter(boardView);
        root.setBottom(bottomSection);

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void loadBoardFile(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Rush Hour Board File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            fileLabel.setText("Loaded: " + selectedFile.getName());

           this.boardToSolve = IO.readInput(selectedFile.getAbsolutePath());
           assert boardToSolve != null;
           this.boardToSolve.updateBoard();
           renderBoard(this.boardToSolve);
        } else {
            fileLabel.setText("File loading cancelled.");
        }
    }

    private void renderBoard(Board board) {
        boardView.getChildren().clear();

        char[][] matrix = board.getMatrix();
        int rows = matrix.length;
        int cols = matrix[0].length;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                char c = matrix[i][j];
                Rectangle cell = new Rectangle(40, 40);
                cell.setStroke(Color.BLACK);

                if (c == '.') {
                    cell.setFill(Color.LIGHTGRAY);
                } else {
                    colorMap.putIfAbsent(c, Color.color(Math.random(), Math.random(), Math.random()));
                    cell.setFill(colorMap.get(c));
                }

                boardView.add(cell, j, i);
            }
        }
    }
}

//Compile
//javac --module-path "C:\javafx-sdk-24.0.1\lib" --add-modules javafx.controls,javafx.fxml -d bin src\*.java
//Run
//java --module-path "C:\javafx-sdk-24.0.1\lib" --add-modules javafx.controls,javafx.fxml -cp bin src.Main
