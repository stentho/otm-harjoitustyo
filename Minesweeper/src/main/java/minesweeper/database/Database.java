package minesweeper.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Tietokantaa kuvaava luokka.
 */
public class Database {

    private final String databaseAddress;

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
            // tulostetaan mikä komento suoritetaan. (testausta varten)
            for (String statement : statements) {
//                System.out.println("Running command >> " + statement);
                st.executeUpdate(statement);
            }

        } catch (Throwable t) {
            // mikäli tietokantataulu on jo olemassa, komentoja ei suoriteta.
//             tulostetaan virhe. (testausta varten)
//            System.out.println("Error >> " + t.getMessage());
        }
    }

    // Määritellään alustavat komennot, laitetaan ne ArrayListiin.
    private List<String> initialStatements() {
        ArrayList<String> list = new ArrayList<>();

        list.add("CREATE TABLE Score (id integer PRIMARY KEY, name varchar(255), width integer, height integer, mines float, time float);");
        list.add("INSERT INTO Score (name, width, height, mines, time) VALUES ('Esimerkki', 10, 10, 20, 40);");

        return list;
    }
}
