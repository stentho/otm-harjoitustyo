package minesweeper.game;

import java.sql.SQLException;
import java.util.List;
import minesweeper.database.Database;
import minesweeper.database.ScoreDao;

/**
 * Pelilogiikkaa kuvaava luokka. 
 * Tämän luokan kautta luodaan pelejä ja kirjataan/haetaan tuloksia.
 */
public class GameService {

    private Game game;
    private ScoreDao scoreDao;

    public GameService() {
        game = new Game(0, 0, 0, 0);
        game.setGameState(0);
    }

    public Game getGame() {
        return game;
    }

    public int getGameTime() {
        return game.getTime() - game.getRemainingTime();
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void newGame(int x, int y, double mF, int time) {
        game = new Game(x, y, mF, time);
        if (time > 0) {
            game.startCountdown();
        }
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
                game.getTime() - game.getRemainingTime());
        scoreDao.insert(score);
    }

    public boolean isGameWon() {
        return game.isGameWon();
    }

    public void gameEnd() {
        game.gameEnd();
    }
}
