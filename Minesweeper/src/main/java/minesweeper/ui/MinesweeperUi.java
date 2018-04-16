package minesweeper.ui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import minesweeper.game.MinesweeperGame;
import minesweeper.game.Square;

public class MinesweeperUi extends Application {

    MinesweeperGame game;
    final static int SQUARE_SIZE = 40;
    Scene scene;
    Pane mainMenu;
    Pane gridPane;
    int squaresX;
    int squaresY;

    // tässä luodaan ruudukko
    public Pane createGrid() {
        gridPane = new Pane();
        gridPane.setPrefSize(SQUARE_SIZE * squaresX, SQUARE_SIZE * squaresY);

        //ensin luodaan kenttä (logiikkatasolla), ja plaseerataan pommit satunnaisesti.
        game.createField();

        //sitten lasketaan ruutujen numeroarvot.
        Square[][] grid = game.calculateNumbersForField();

        //tehdään kentästä visuaalinen ruudukko
        for (int y = 0; y < squaresY; y++) {
            for (int x = 0; x < squaresX; x++) {

                SquarePane squarePane = new SquarePane(grid[x][y], SQUARE_SIZE);

                squarePane.setOnMouseClicked(new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent event) {
                        MouseButton btn = event.getButton();
                        if (btn == MouseButton.PRIMARY) {
                            leftClick(squarePane);
                        } else if (btn == MouseButton.SECONDARY) {
                            rightClick(squarePane);
                        }
                    }
                });

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

    public void leftClick(SquarePane sqPane) {

        // Jos ruutua on jo klikattu, ei tehdä mitään.
        if (sqPane.isOpen()) {
            return;
        }

        // Jos ruutu on pommi, hypätään takaisin päävalikkoon.
        if (sqPane.getSquare().isBomb()) {

            revealAllBombs();
            System.out.println("Hähää, hävisit.");
            Timeline timeline = new Timeline(new KeyFrame(
                    Duration.millis(2000),
                    ae -> scene.setRoot(mainMenu)));
            timeline.play();

            return;
        }

        // Muissa tapauksissa paljastetaan alla oleva teksti (numero) ja
        // värjätään tausta valkoiseksi, niin että numero erottuu selvästi.
        sqPane.setOpen(true);
        sqPane.showEdge(false);

    }

    private void rightClick(SquarePane sqPane) {
        
        // Jos ruutua on jo auki, ei tehdä mitään.
        if (sqPane.isOpen()) {
            return;
        }
        
        if (sqPane.getFlag().isVisible()) {
            sqPane.showFlag(false);
            return;
        }
        
        if (!sqPane.getFlag().isVisible()) {
            sqPane.showFlag(true);
        }
    }

    public void revealAllBombs() {
        for (int i = 0; i < (squaresX * squaresY); i++) {
            Node n = gridPane.getChildren().get(i);
            SquarePane squarePane = (SquarePane) n;

            // Avataan kaikki ruudut siksi, että silloin niitä ei voi enää avata
            // klikkaamalla. Muuten voisi jatkaa pelaamista pelin päätyttyään.
            squarePane.getSquare().setIsOpen(true);

            if (squarePane.getSquare().isBomb()) {
                squarePane.getTile().setFill(Color.RED);
                squarePane.showEdge(false);
            }
        }
    }

    @Override
    public void start(Stage start) throws Exception {

        //aloitusnäyttö
        Button playButton = new Button();
        playButton.setText("Pelaa!");
        playButton.setFont(Font.font(25));
        playButton.setOnAction(e -> {

            //kun klikkaa play-nappulaa, luo pelinäytön
            squaresX = 20;
            squaresY = 20;
            game = new MinesweeperGame(squaresX, squaresY);
//            start.setWidth(SQUARE_SIZE * squaresX);
//            start.setHeight(SQUARE_SIZE * squaresY);
            scene.setRoot(createGrid());
        });

        mainMenu = new StackPane();
        mainMenu.getChildren().add(playButton);

//        BorderPane border = new BorderPane();
//        HBox hbox = new HBox(800);
//        border.setLeft(hbox);
//        border.setCenter(mainMenu);
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
