package minesweeper.database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import minesweeper.game.Score;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class DatabaseTest {
    private Database db;
    private ScoreDao sd;

    public DatabaseTest() {
    }

    @Before
    public void setUp() throws ClassNotFoundException {
        db = new Database("jdbc:sqlite:resources/test.db");
        sd = new ScoreDao(db);
    }

    @Test
    public void initializeCorrect() throws SQLException {
        db.init();
        List<Score> scores = sd.findAll();
        Score esimerkki = new Score("Esimerkki", 10, 10, 20, 40);
        assertEquals(scores.get(0).toString(), esimerkki.toString());
        
    }
}
