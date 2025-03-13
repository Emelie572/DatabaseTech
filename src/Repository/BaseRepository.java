package Repository;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;


        public class BaseRepository {

            Properties p = new Properties();
            protected Connection connection;

            public BaseRepository() {
                try {
                    p.load(new FileInputStream("src/settings.properties"));
                    connection = DriverManager.getConnection(
                            p.getProperty("connectionString"),
                            p.getProperty("name"),
                            p.getProperty("password"));
                } catch (FileNotFoundException e) {
                    System.out.println("Filen hittades ej " + e.getMessage());
                } catch (IOException e) {
                    System.out.println("Fel vid läsning av fil" + e.getMessage());
                } catch (SQLException e) {
                    System.out.println("Fel vid anslutning till databasen: " + e.getMessage());
                }
            }



            public void closeConnection() {
                try {
                    if (connection != null && !connection.isClosed()) {
                        connection.close();
                    }
                } catch (SQLException e) {
                    System.out.println("Fel vid stängning av anslutning: " + e.getMessage());
                }
            }
        }
