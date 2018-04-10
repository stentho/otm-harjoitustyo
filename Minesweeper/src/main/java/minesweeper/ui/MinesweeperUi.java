package minesweeper.ui;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import minesweeper.game.MinesweeperGame;
import minesweeper.game.Square;

public class MinesweeperUi extends Application {

    MinesweeperGame game;
    final static int SQUARE_SIZE = 40;
    Scene scene;
    Pane mainMenu;

    // tässä luodaan ruudukko
    public Pane createGrid(int squaresX, int squaresY) {
        Pane gridPane = new Pane();
        gridPane.setPrefSize(SQUARE_SIZE * squaresX, SQUARE_SIZE * squaresY);

        //ensin luodaan kenttä (logiikkatasolla), ja plaseerataan pommit satunnaisesti.
        game.createField();

        //sitten lasketaan ruutujen numeroarvot.
        Square[][] grid = game.calculateNumbersForField();

        //tehdään kentästä visuaalinen ruudukko
        for (int y = 0; y < squaresY; y++) {
            for (int x = 0; x < squaresX; x++) {

                SquarePane squarePane = new SquarePane(grid[x][y], SQUARE_SIZE);
                squarePane.setOnMouseClicked(e -> click(squarePane));

                int b = squarePane.getSquare().getAdjacentBombs();

                //mikäli vierekkäisten pommien määrä on positiivinen, ja
                //ei ole itse pommi, niin kirjoitetaan numero ruutuun.
                if (b > 0 && !squarePane.getSquare().isBomb()) {
                    squarePane.setText(Integer.toString(b));
                }
                gridPane.getChildren().add(squarePane);
            }
        }
        return gridPane;
    }

    public void click(SquarePane sqPane) {

        // Jos ruutu on pommi, hypätään takaisin päävalikkoon.
        if (sqPane.getSquare().isBomb()) {

            sqPane.getEdge().setFill(Color.RED);
            sqPane.getText().setVisible(true);
            System.out.println("Hähää, hävisit.");
            scene.setRoot(mainMenu);

            return;
        }

        // Jos ruutua on jo klikattu, ei tehdä mitään.
        if (sqPane.isOpen()) {
            return;
        }

        // Muissa tapauksissa paljastetaan alla oleva teksti (numero) ja
        // värjätään tausta valkoiseksi, niin että numero erottuu selvästi.
        sqPane.setOpen(true);
        sqPane.getEdge().setFill(Color.WHITE);
        sqPane.getText().setVisible(true);

    }

    @Override
    public void start(Stage start) throws Exception {

        //aloitusnäyttö
        Button playButton = new Button();
        playButton.setText("Pelaa!");
        playButton.setFont(Font.font(25));
        playButton.setOnAction(e -> {

            //kun klikkaa play-nappulaa, luo pelinäytön
            game = new MinesweeperGame(20, 20);
            scene.setRoot(createGrid(20, 20));
        });

        mainMenu = new StackPane();
        mainMenu.getChildren().add(playButton);
        scene = new Scene(mainMenu, SQUARE_SIZE * 20, SQUARE_SIZE * 20);

        //aloitusnäytön setup
        start.setTitle("Minesweeper");
        start.setScene(scene);
        start.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
