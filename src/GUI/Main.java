package GUI;

import Model.Processor;
import Model.QueenGame;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Main extends Application {

    private static final int NB_QUEENS = 8;
    private static final double SCREEN_WIDTH = 500;
    private static Processor processor;
    private final double COL_WIDTH = SCREEN_WIDTH / NB_QUEENS;

    @Override
    public void start(Stage primaryStage) throws Exception{

        GridPane root = new GridPane();
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, SCREEN_WIDTH, SCREEN_WIDTH));

        int columns = NB_QUEENS, rows = NB_QUEENS;

        for(int i = 0; i < columns; i++) {
            ColumnConstraints column = new ColumnConstraints(COL_WIDTH);
            root.getColumnConstraints().add(column);
        }

        for(int i = 0; i < rows; i++) {
            RowConstraints row = new RowConstraints(COL_WIDTH);
            root.getRowConstraints().add(row);
        }

        root.setStyle("-fx-background-color: white; -fx-grid-lines-visible: true");


        for (int i = 0; i < NB_QUEENS; i ++) {
            Circle circle = new Circle(COL_WIDTH /2);
            circle.setFill(Color.BLUE);
            root.add(circle, i, processor.queens[i]-1); // column=2 row=1
        }

        primaryStage.show();
    }


    public static void main(String[] args) {
        QueenGame queenGame = new QueenGame(NB_QUEENS);
        processor = queenGame.getProcessor();
        launch(args);
    }
}
