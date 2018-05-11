
package minesweeper.database;

import java.sql.*;
import java.util.*;
import minesweeper.game.Score;

/**
 *  Tulostiedoille luotu rajapinta pelilogiikan ja tietokannan välille.
 */

public class ScoreDao implements Dao<Score, Integer> {
    
    private Database db;

    public ScoreDao(Database db) {
        this.db = db;
    }

    @Override
    public List<Score> findAll() throws SQLException {
        ArrayList<Score> scores;
        try (Connection conn = db.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Score");
            ResultSet rs = stmt.executeQuery();
            scores = new ArrayList<>();
            while (rs.next()) {
                Score s = new Score(rs.getInt("id"), rs.getString("name"), rs.getInt("width"),
                        rs.getInt("height"), rs.getFloat("mines"), rs.getFloat("time"));
                scores.add(s);
            }   rs.close();
            stmt.close();
        }
        
        return scores;
    }

    @Override
    public void insert(Score score) throws SQLException {
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Score (name, width, height, mines, time) VALUES (?, ?, ?, ?, ?)");
        stmt.setString(1, score.getName());
        stmt.setInt(2, score.getWidth());
        stmt.setInt(3, score.getHeight());
        stmt.setFloat(4, (float) score.getMines());
        stmt.setFloat(5, (float) score.getTime());
        stmt.executeUpdate();
        
        stmt.close();
        conn.close();
    }
}
