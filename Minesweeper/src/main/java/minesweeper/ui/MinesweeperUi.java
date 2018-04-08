package minesweeper.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class MinesweeperUi extends Application {
    
    public static Node createSquare() {
        Rectangle square = new Rectangle();
        square.setWidth(20);
        square.setHeight(20);
        return square;
    }

    @Override
    public void start(Stage aloitus) throws Exception {
        
        //pelinäyttö
        
        GridPane grid = new GridPane();
        for (int x = 0; x < 10;x++) {
            for (int y = 0; y < 10; y++) {
                grid.add(createSquare(), x, y);
            }
        }
        Scene gameScene = new Scene(grid, 500, 350);
        
        //aloitusnäyttö

        Button playButton = new Button();
        playButton.setText("Pelaa!");
        playButton.setOnAction(e -> {
            aloitus.setScene(gameScene);
        });

        StackPane layout = new StackPane();
        layout.getChildren().add(playButton);
        Scene mainScene = new Scene(layout, 500, 350);
        
        //aloitusnäytön setup

        aloitus.setTitle("Minesweeper");
        aloitus.setScene(mainScene);
        aloitus.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
