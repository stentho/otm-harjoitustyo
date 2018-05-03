package minesweeper.database;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import minesweeper.game.Score;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class ScoreDaoTest {
    private ScoreDao sd;

    public ScoreDaoTest() {
    }

    @Before
    public void setUp() throws ClassNotFoundException {
        Database db = new Database("jdbc:sqlite:scores.db");
        sd = new ScoreDao(db);
    }

    @Test
    public void insertsCorrectly() throws SQLException {
        Score sc = new Score("Thomas", 10, 10, 0, 0);
        sd.insert(sc);
        try {
            assertEquals("Nimi: Thomas, Koko: 10 x 10, Miinoja: 0.0, Aika: 0.0", sd.findAll().get(sd.findAll().size() - 1).toString());
        } catch (SQLException ex) {
            Logger.getLogger(ScoreDaoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
