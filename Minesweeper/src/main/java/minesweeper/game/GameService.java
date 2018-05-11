package minesweeper.game;

import java.sql.SQLException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import minesweeper.database.Database;
import minesweeper.database.ScoreDao;

/**
 * Pelilogiikkaa kuvaava luokka. 
 * Tämän luokan kautta luodaan pelejä ja kirjataan/haetaan tuloksia taulukosta.
 * Tässä luokassa käsitellään myös ajastinta.
 */
public class GameService {

    private Game game;
    private ScoreDao scoreDao;
    private boolean gameRunning;
    private int gameState;
    private int remainingTime;
    private IntegerProperty remainingTimeProperty;
    private Timer timer;

    public GameService() {
        game = new Game(0, 0, 0, 0);
        gameState = 0;
        timer = new Timer();
    }

    public Game getGame() {
        return game;
    }

    public int getGameTime() {
        return game.getTime() - remainingTime;
    }

    public int getGameState() {
        return gameState;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public IntegerProperty getRemainingTimeProperty() {
        return remainingTimeProperty;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void setGameRunning(boolean gameRunning) {
        this.gameRunning = gameRunning;
    }

    public void setGameState(int gameState) {
        this.gameState = gameState;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    public void newGame(int x, int y, double mF, int time) {
        game = new Game(x, y, mF, time);
        if (time > 0) {
            startCountdown();
        }
    }

    public boolean isGameWon() {
        return game.numberOfUnopenedSquares() == game.numberOfBombs();
    }

    public void gameEnd() {
        timer.cancel();
        setGameRunning(false);
        if (isGameWon()) {
            gameState = 1;
        } else {
            gameState = -1;
        }
    }

    public void startCountdown() {
        remainingTime = game.getTime();
        remainingTimeProperty = new SimpleIntegerProperty(remainingTime);

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        remainingTime = updateTime();
                        remainingTimeProperty.setValue(remainingTime);
                    }
                });
            }

        }, 1000, 1000);
    }

    private int updateTime() {
        if (remainingTime == 1) {
            timer.cancel();
            gameEnd();
        }
        return --remainingTime;
    }

    // alustetaan tietokanta.
    public void initializeDatabase() throws ClassNotFoundException {
        Database database = new Database("jdbc:sqlite:resources/database/scores.db");
        database.init();
        scoreDao = new ScoreDao(database);
    }

    // haetaan tietokannasta kaikki tulokset.
    public List<Score> getAllScores() throws ClassNotFoundException, SQLException {
        return scoreDao.findAll();
    }

    // laitetaan tietokantaa tulos.
    public void insertScore(String name) throws SQLException, ClassNotFoundException {
        Score score = new Score(
                name,
                game.getSquaresX(),
                game.getSquaresY(),
                game.getMineFreq() * 100,
                game.getTime() - remainingTime);
        scoreDao.insert(score);
    }
}
