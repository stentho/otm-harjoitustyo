
package minesweeper.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private String databaseAddress;

    public Database(String databaseAddress) throws ClassNotFoundException {
        this.databaseAddress = databaseAddress;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseAddress);
    }
    
    public void init() {
        List<String> statements = initialStatements();
        
        try (Connection conn = getConnection()) {
            Statement st = conn.createStatement();

            // suoritetaan komennot.
            for (String statement : statements) {
                System.out.println("Running command >> " + statement);
                st.executeUpdate(statement);
            }

        } catch (Throwable t) {
            // mikäli tietokantataulu on jo olemassa, komentoja ei suoriteta.
            System.out.println("Error >> " + t.getMessage());
        }
    }

    private List<String> initialStatements() {
        ArrayList<String> list = new ArrayList<>();
        
        list.add("CREATE TABLE Score (id integer PRIMARY KEY, name varchar(255), integer width, integer height, float mines, float time);");
        list.add("INSERT INTO Score (name, width, height, mines, time) VALUES ('Thomas', 10, 10, 20, 40);");
        
        return list;
    }
}