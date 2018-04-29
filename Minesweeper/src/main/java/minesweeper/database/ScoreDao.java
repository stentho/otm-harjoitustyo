
package minesweeper.database;

import java.sql.*;
import java.util.*;
import minesweeper.game.Score;

public class ScoreDao implements Dao<Score, Integer> {
    
    private Database db;

    public ScoreDao(Database db) {
        this.db = db;
    }

    @Override
    public Score findOne(Integer key) throws SQLException {
        // ei toteutettu
        return null;
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
    public Score saveOrUpdate(Score object) throws SQLException {
        // ei toteutettu
        return null;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Score WHERE id = ?");

        stmt.setInt(1, key);
        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }
}
