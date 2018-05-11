package minesweeper.game;

import java.sql.SQLException;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class GameServiceTest {
    private GameService gs;
    
    public GameServiceTest() {
    }
    
    @Before
    public void setUp() throws ClassNotFoundException {
        gs = new GameService();
        gs.initializeDatabase();
    }

    @Test
    public void insertCorrectly() throws SQLException, ClassNotFoundException {
        gs.newGame(10, 10, 0.2, 30);
        gs.insertScore("Jeejee");
        
        List<Score> scores = gs.getAllScores();
        Score sc = scores.get(scores.size() - 2);
        System.out.println(sc);
        assertEquals(sc.toString(), "Nimi: Jeejee, Koko: 10 x 10, Miinoja: 20.0, Aika: 0.0");
    }
}
