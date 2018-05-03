package minesweeper.database;

import java.sql.*;
import java.util.*;

/**
 *  Dao-interface, jota implementoi ScoreDao. Listataan olennaiset metodit.
 */
public interface Dao<T, K> {
    T findOne(K key) throws SQLException;
    List<T> findAll() throws SQLException;
    void insert(T object) throws SQLException;
}
