package minesweeper.ui;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.Timer;
import minesweeper.game.GameService;
import minesweeper.game.Score;
import minesweeper.game.Square;

public class MinesweeperUi extends Application {

//    private Game game = new Game(0, 0, 0, 0);
    private GameService gameService;
    private final static int SQUARE_SIZE = 20;
    private Stage stage;
    private Scene scene;
    private BorderPane mainMenu;
    private BorderPane winScreen;
    private BorderPane scoreScreen;
    private Pane gridPane;
    private int squaresX;
    private int squaresY;
    int remainingTime;

    @Override
    public void start(Stage st) throws Exception {

        gameService = new GameService();
        gameService.initializeDatabase();

        stage = st;

        // Haetaan taustakuva.
        Image bg = new Image("file:resources/images/minefield.jpg");

        // Luodaan päävalikon ruutu mainMenu.
        mainMenu = new BorderPane();
        mainMenu.setPrefSize(800, 600);
        mainMenu.setCenter(createMainMenuVBox());
        mainMenu = setBackground(mainMenu, bg);

        // Luodaan voittoruutu.
        winScreen = new BorderPane();
        winScreen.setPrefSize(800, 600);
        winScreen.setCenter(createWinScreenVBox());
        winScreen = setBackground(winScreen, bg);

        // Luodaan tulosruutu.
        scoreScreen = new BorderPane();
        scoreScreen.setPrefSize(800, 600);
        scoreScreen.setCenter(createScoreScreenVBox());
        scoreScreen = setBackground(scoreScreen, bg);

        // Luodaan scene. Asetetaan nimeksi Minesweeper ja laitetaan se stageen.
        scene = new Scene(mainMenu);
        stage.setTitle("Minesweeper");
        stage.setScene(scene);
        stage.show();
    }

    // Asetetaan taustakuva BorderPane-oliolle.
    private static BorderPane setBackground(BorderPane mainMenu, Image image) {
        mainMenu.setBackground(new Background(new BackgroundImage(
                image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false))));
        return mainMenu;
    }

    // tässä luodaan ruudukko
    public Pane createGrid() {
//        System.out.println("Starting game: " + game);

        gridPane = new Pane();
        gridPane.setPrefSize(SQUARE_SIZE * squaresX, SQUARE_SIZE * squaresY);

        // ensin luodaan kenttä (logiikkatasolla), ja sijoitetaan pommit 
        // satunnaisesti ja lasketaan ruutujen numeroarvot.
        Square[][] grid = gameService.getGame().createField();

        // tehdään kentästä visuaalinen ruudukko
        for (int y = 0; y < squaresY; y++) {
            for (int x = 0; x < squaresX; x++) {

                SquarePane squarePane = new SquarePane(grid[x][y], SQUARE_SIZE);
                addClickability(squarePane);

                //mikäli vierekkäisten pommien määrä on positiivinen, ja
                //ei ole itse pommi, niin piirretään numero ruutuun.
                int b = squarePane.getAdjacentBombs();
                if (b > 0 && !squarePane.getSquare().isBomb()) {
                    squarePane.setText(Integer.toString(b));
                    setSquareTextColor(squarePane, b);
                }
                gridPane.getChildren().add(squarePane);
            }
        }
        return gridPane;
    }

    // Lisätään parametriruudulle klikattavuustoiminnot.
    private void addClickability(SquarePane squarePane) {
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
    }

    // Tässä asetetaan numeroille omat värit, niin että ne erottuvat toisistaan.
    // Värit ovat valittu alkuperäisen Microsoftin Minesweeper-pelin mukaan.
    private static void setSquareTextColor(SquarePane squarePane, int b) {
        if (b == 1) {
            squarePane.getText().setFill(Color.web("0x0000ff"));
        }
        if (b == 2) {
            squarePane.getText().setFill(Color.web("0x007b00"));
        }
        if (b == 3) {
            squarePane.getText().setFill(Color.web("0xff0000"));
        }
        if (b == 4) {
            squarePane.getText().setFill(Color.web("0x010080"));
        }
        if (b == 5) {
            squarePane.getText().setFill(Color.web("0x810102"));
        }
        if (b == 6) {
            squarePane.getText().setFill(Color.web("0x008081"));
        }
        if (b == 7) {
            squarePane.getText().setFill(Color.web("0x000000"));
        }
        if (b == 8) {
            squarePane.getText().setFill(Color.web("0x808080"));
        }
    }

    public void leftClick(SquarePane sqPane) {

        // Jos ruutua on jo klikattu, ei tehdä mitään.
        if (sqPane.isOpen()) {
            return;
        }

        // Jos ruudussa on lippu, se poistetaan kun ruutu aukaistaan.
        if (sqPane.getFlag().isVisible()) {
            sqPane.showFlag(false);
        }

        // Jos ruutu on pommi, pelaaja häviää pelin.
        if (sqPane.isBomb()) {
            loseGame();
            return;
        }

        // Jos ruutu on tyhjä (0 miinaa ympärillä), avataan myös viereiset
        // ruudut. Mikäli ne on myös tyhjiä, avataan niiden ympärillä olevat
        // ruudut jne kunnes numeroidut ruudut ympäröivät tyhjiöalueen kokonaan.
        if (sqPane.getAdjacentBombs() == 0) {
            gameService.getGame().openAdjacentSquaresIfZero(sqPane.getSquare());
            revealOpenedSquares();
        }

        // Muissa tapauksissa paljastetaan alla oleva teksti (numero) piiloittamalla
        // "kannen" (edge). Asetetaan ruutu "auki"-tilaan myös logiikassa.
        sqPane.setOpen(true);
        sqPane.showEdge(false);

        // Peli laskee joka klikkauksen jälkeen, montako avaamatonta ruutua on
        // vielä jäljellä. Mikäli määrä on sama kuin pommien määrä, peli on 
        // voitettu.
        if (gameService.isGameWon()) {
            winGame();
        }
    }

    // paljastetaan kaikki avatut ruudut, eli käydään läpi jokainen SquarePane
    // ja haetaan sen Square-olion tila (avattu vai ei). Mikäli on avattu,
    // paljastetaan se ruutu pelaajalle.
    private void revealOpenedSquares() {
        for (int i = 0; i < (squaresX * squaresY); i++) {
            Node n = gridPane.getChildren().get(i);
            SquarePane squarePane = (SquarePane) n;

            // Avataan kaikki ruudut siksi, että silloin niitä ei voi enää avata
            // klikkaamalla. Muuten voisi jatkaa pelaamista pelin päätyttyään.
            if (squarePane.isOpen()) {
                squarePane.showEdge(false);
            }
        }
    }

    // oikean hiiripainikkeen toiminnallisuus.
    private void rightClick(SquarePane sqPane) {

        // Jos ruutua on jo auki, ei tehdä mitään.
        if (sqPane.isOpen()) {
            return;
        }

        // Jos ruudussa on jo lippu, se poistetaan.
        if (sqPane.getFlag().isVisible()) {
            sqPane.showFlag(false);
            return;
        }

        // Jos ruudussa ei ole lippua, siihen lisätään sellainen.
        if (!sqPane.getFlag().isVisible()) {
            sqPane.showFlag(true);
        }
    }

    // Paljastetaan kaikki pommiruudut.
    public void revealAllBombs() {
        for (int i = 0; i < (squaresX * squaresY); i++) {
            Node n = gridPane.getChildren().get(i);
            SquarePane squarePane = (SquarePane) n;

            // Avataan kaikki ruudut siksi, että silloin niitä ei voi enää avata
            // klikkaamalla. Muuten voisi jatkaa pelaamista pelin päätyttyään.
            squarePane.setOpen(true);

            if (squarePane.isBomb() && !squarePane.isFlagged()) {
                squarePane.getTile().setFill(Color.RED);
                squarePane.showEdge(false);
            }
        }
    }

    // Peli hävitään. Siirrytään takaisin päävalikkoon.
    private void loseGame() {
        gameService.gameEnd();
        revealAllBombs();
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(2500),
                ae -> scene.setRoot(mainMenu)),
                new KeyFrame(
                        Duration.millis(2500),
                        ae -> resetScreenSize(800, 600)));
        timeline.play();
    }

    // Peli voitetaan, siirrytään voittoruutuun.
    private void winGame() {
        gameService.gameEnd();
        winScreen.setCenter(createWinScreenVBox());
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(2000),
                ae -> scene.setRoot(winScreen)),
                new KeyFrame(
                        Duration.millis(2000),
                        ae -> resetScreenSize(800, 600)));
        timeline.play();
    }

    // Luodaan "Kentän koko"-elementti päävalikkoon.
    private static HBox createSizeHBox(TextField sizeX, TextField sizeY) {
        sizeX.setMaxWidth(40);
        sizeY.setMaxWidth(40);
        Label labelS = new Label("Kentän koko:");
        Label labelX = new Label(" x ");
        HBox hbS = new HBox();
        hbS.getChildren().addAll(labelS, sizeX, labelX, sizeY);
        hbS.setSpacing(10);
        return hbS;
    }

    // Luodaan "Miinoja"-elementti päävalikkoon.
    private static HBox createMinesHBox(TextField mines) {
        mines.setMaxWidth(50);
        Label labelM = new Label("Miinoja:");
        Label labelP = new Label("%");
        HBox hbM = new HBox();
        hbM.getChildren().addAll(labelM, mines, labelP);
        hbM.setSpacing(10);
        return hbM;
    }

    // Luodaan "Aikaraja"-elementti päävalikkoon.
    private static HBox createTimeHBox(TextField time) {
        time.setMaxWidth(40);
        Label labelT = new Label("Aikaraja:");
        Label labelS = new Label("s");
        HBox hbT = new HBox();
        hbT.getChildren().addAll(labelT, time, labelS);
        hbT.setSpacing(10);
        return hbT;
    }

    // Luodaan nimikenttä voittoruutuun.
    private static HBox createNameHBox(TextField name) {
        Label labelN = new Label("Nimi:");
        HBox hbN = new HBox();
        hbN.getChildren().addAll(labelN, name);
        hbN.setSpacing(10);
        return hbN;
    }

    // Tässä muutetaan ikkunan kokoa kentän koon mukaan. Nuo +16 ja +39
    // viittaavat automaattisesti luoduihin reunoihin sekä yläpalkkiin.
    // Ne lasketaan stagen leveyteen ja korkeuteen.
    public void resetScreenSize(int width, int height) {
        stage.setWidth(width + 16);
        stage.setHeight(height + 39);
    }

    public HBox createHBox(Insets inset) {
        HBox hb = new HBox();
        hb.setPadding(inset);
        return hb;
    }

    public VBox createVBox(Insets inset) {
        VBox vb = new VBox();
        vb.setPadding(inset);
        return vb;
    }

    // Luodaan pelinäkymän ruutu.
    private BorderPane createGameBorder(Pane p) {
        BorderPane border = new BorderPane();
        border.setCenter(p);
        border.setBottom(createHBox(new Insets(20)));

        HBox top = createHBox(new Insets(20));
        Text time = new Text("Time: " + remainingTime);
        Button backToMain = new Button("Anna periksi");
        backToMain.setOnAction(e -> {
            loseGame();
        });

        top.getChildren().addAll(time, backToMain);
        top.setSpacing(50);
        border.setTop(top);

        border.setLeft(createVBox(new Insets(20)));
        border.setRight(createVBox(new Insets(20)));

        return border;
    }
    
    // Tämä listener kuuntelee GameService-olion ajastinta muutosten varalta, ja 
    // päivittää peliruudun aika-kohtaa sen mukaan. Koska java ei tue int-olion
    // muutosten kuuntelemista suoraan, tämä on tehtävä IntegerProperty-olion
    // kautta.
    private void addListener() {
        gameService.getRemainingTimeProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                remainingTime = (int) newValue;
                if (remainingTime <= 0) {
                    loseGame();
                }
                BorderPane gamePane = createGameBorder(gridPane);
                scene.setRoot(gamePane);
            }
        });
    }

    // Luodaan päävalikon elementit, laitetaan ne VBoxiin.
    private VBox createMainMenuVBox() {
        Text title = new Text("Minesweeper");
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 26));

        TextField sizeX = new TextField("20");
        TextField sizeY = new TextField("20");
        TextField mines = new TextField("20");
        TextField time = new TextField("30");
        sizeX.setMinWidth(50);
        sizeY.setMinWidth(50);
        mines.setMinWidth(50);
        time.setMinWidth(50);

        HBox hbS = createSizeHBox(sizeX, sizeY);
        HBox hbM = createMinesHBox(mines);
        HBox hbT = createTimeHBox(time);

        Button playButton = new Button();
        playButton.setText("Pelaa!");
        playButton.setFont(Font.font(25));
        playButton.setOnAction(e -> {

            // Kun klikkaa pelaa-nappulaa, luo pelinäytön antamilla arvoilla.
            squaresX = Integer.parseInt(sizeX.getText());
            squaresY = Integer.parseInt(sizeY.getText());
            double mF = 0.01 * Double.parseDouble(mines.getText());
            remainingTime = Integer.parseInt(time.getText());
            
            gameService.newGame(squaresX, squaresY, mF, remainingTime);

            resetScreenSize(SQUARE_SIZE * squaresX + 80, SQUARE_SIZE * squaresY + 80);
            gridPane = createGrid();
            
            BorderPane gamePane = createGameBorder(gridPane);
            scene.setRoot(gamePane);
            if (remainingTime > 0) {
                addListener();
            }
        });

        Button scoreButton = new Button();
        scoreButton.setText("Tulostaulukko");
        scoreButton.setOnAction(e -> {
            scene.setRoot(scoreScreen);
        });

        Button exitButton = new Button();
        exitButton.setText("Lopeta");
        exitButton.setOnAction(e -> {
            System.exit(0);
        });

        VBox centerVbox = new VBox();
        centerVbox.setPadding(new Insets(100));
        centerVbox.setSpacing(20);

        centerVbox.getChildren().addAll(Arrays.asList(title, hbS, hbM, hbT,
                playButton, scoreButton, exitButton));
        return centerVbox;
    }

    // Luodaan voittoruudun elementit, laitetaan ne VBoxiin.
    private VBox createWinScreenVBox() {
        Text title = new Text("Voitit, onneksi olkoon!");
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 26));

        Text score = new Text("Pisteet");
        score.setFont(Font.font("Verdana", FontWeight.BOLD, 18));

        Text size = new Text("Kentän koko: " + squaresX + " x " + squaresY);
        size.setFont(Font.font("Verdana", 18));

        Text mines = new Text("Miinoja: " + gameService.getGame().getMineFreq() * 100 + "%");
        mines.setFont(Font.font("Verdana", 18));

        Text time = new Text("Aika: " + (gameService.getGameTime()) + "s");
        time.setFont(Font.font("Verdana", 18));

        TextField name = new TextField();
        name.setMaxWidth(200);
        HBox hbN = createNameHBox(name);

        Button submitName = new Button("Lähetä");
        submitName.setOnAction(e -> {

            try {
                gameService.insertScore(name.getText().toString());
            } catch (SQLException ex) {
                Logger.getLogger(MinesweeperUi.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(MinesweeperUi.class.getName()).log(Level.SEVERE, null, ex);
            }

            scoreScreen.setCenter(createScoreScreenVBox());
            scene.setRoot(scoreScreen);
        });

        Button backToMain = new Button("Takaisin päävalikkoon");

        backToMain.setOnAction(e -> {
            scene.setRoot(mainMenu);
        });

        VBox centerVbox = new VBox();
        centerVbox.setPadding(new Insets(100));
        centerVbox.setSpacing(20);

        centerVbox.getChildren().addAll(Arrays.asList(title, score, size, mines,
                time, hbN, submitName, backToMain));
        return centerVbox;
    }

    // Luodaan tulostaulukkoruudun elementit, laitetaan ne VBoxiin.
    private VBox createScoreScreenVBox() {
        Text title = new Text("Tulostaulukko");
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 26));

        TableView table = setUpScoreTable();

        List<Score> scores = new ArrayList<>();
        ObservableList<ScoreItem> scoresT = FXCollections.observableArrayList();

        try {
            scores = gameService.getAllScores();
        } catch (ClassNotFoundException | SQLException ex) {

        }

        DecimalFormat f = new DecimalFormat("0.#");

        for (int i = 0; i < scores.size(); i++) {
            Score s = scores.get(i);
            ScoreItem si = new ScoreItem(
                    s.getName(),
                    String.valueOf(s.getWidth()),
                    String.valueOf(s.getHeight()),
                    String.valueOf(f.format(s.getMines())) + " %",
                    String.valueOf(f.format(s.getTime())) + " s");
            scoresT.add(si);
        }
        table.setItems(scoresT);

        Button backToMain = new Button("Takaisin päävalikkoon");

        backToMain.setOnAction(e -> {
            scene.setRoot(mainMenu);
        });

        VBox centerVbox = new VBox();
        centerVbox.setPadding(new Insets(100));
        centerVbox.setSpacing(20);

        centerVbox.getChildren().add(title);
        centerVbox.getChildren().addAll(table);
        centerVbox.getChildren().add(backToMain);

        return centerVbox;
    }

    private TableView setUpScoreTable() {
        TableView table = new TableView();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn name = new TableColumn("Nimi");
        name.setMinWidth(150);
        TableColumn size = new TableColumn("Koko");
        TableColumn width = new TableColumn("Leveys");
        TableColumn height = new TableColumn("Korkeus");
        TableColumn mines = new TableColumn("Miinoja");
        TableColumn time = new TableColumn("Aika");

        name.setCellValueFactory(
                new PropertyValueFactory<Score, String>("name")
        );
        width.setCellValueFactory(
                new PropertyValueFactory<Score, String>("width")
        );
        height.setCellValueFactory(
                new PropertyValueFactory<Score, String>("height")
        );
        mines.setCellValueFactory(
                new PropertyValueFactory<Score, String>("mines")
        );
        time.setCellValueFactory(
                new PropertyValueFactory<Score, String>("time")
        );

        size.getColumns().addAll(width, height);
        table.getColumns().addAll(name, size, mines, time);
        return table;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
