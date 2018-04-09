package minesweeper.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import minesweeper.game.MinesweeperGame;
import minesweeper.game.Square;

public class MinesweeperUi extends Application {
    
    MinesweeperGame game;
    
    public static Node createSquare() {
        Rectangle square = new Rectangle();
        square.setWidth(20);
        square.setHeight(20);
        return square;
    }
    
    // tässä luodaan ruudukko
    
    public Parent createPane() {
        Pane pane = new Pane();
        pane.setPrefSize(800, 600);
        
        Square[][] grid = game.createField(pane);
        
        return pane;
    }

    @Override
    public void start(Stage aloitus) throws Exception {
        
        //pelinäyttö
        game = new MinesweeperGame(10,10);
        
        Scene gameScene = new Scene(createPane());
        
        //aloitusnäyttö

        Button playButton = new Button();
        playButton.setText("Pelaa!");
        playButton.setOnAction(e -> {
            aloitus.setScene(gameScene);
        });

        StackPane layout = new StackPane();
        layout.getChildren().add(playButton);
        Scene mainScene = new Scene(layout, 800, 600);
        
        //aloitusnäytön setup

        aloitus.setTitle("Minesweeper");
        aloitus.setScene(mainScene);
        aloitus.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
