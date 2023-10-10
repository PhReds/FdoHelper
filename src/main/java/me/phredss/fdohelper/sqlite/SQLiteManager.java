package me.phredss.fdohelper.sqlite;

import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteManager {


    private JavaPlugin plugin;

    public SQLiteManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    private Connection connection;

    public SQLiteManager(String dbPath) {
        connect(dbPath);
    }

    //Connetti Tabella
    public void connect(String dbPath) {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            plugin.getLogger().info("Connessione al database SQLite stabilita.");

            // Aggiungi un messaggio di conferma nell'output della console
            System.out.println("Connessione al database SQLite stabilita.");
            // Creazione delle tabelle se non esistono
            createTables();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    //Crea Tabella
    public void createTables() {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS identikit (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "player_uuid TEXT," +
                    "player_name TEXT," +
                    "motivo TEXT," +
                    "cauzione REAL," +
                    "data_entrata TEXT," +
                    "data_scadenza TEXT)");  // Aggiunto campo Data Scadenza
            statement.close();

            // Aggiungi un messaggio di conferma nell'output della console
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createMulteTable() {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS multe (" +
                    "numero_multa INTEGER PRIMARY KEY," +  // Aggiunto campo numero_multa come chiave primaria
                    "player_uuid TEXT," +
                    "player_name TEXT," +
                    "motivo TEXT," +
                    "costo REAL," +
                    "data_stampa TEXT," +
                    "data_scadenza TEXT)");
            statement.close();

            // Aggiungi un messaggio di conferma nell'output della console
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void createAccountTable() {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS accounts (" +
                    "player_uuid TEXT PRIMARY KEY," +  // Aggiunta virgola qui
                    "password TEXT," +
                    "impronta BOOLEAN," +
                    "tema TEXT)");  // Aggiunto campo Data Scadenza
            statement.close();

            // Aggiungi un messaggio di conferma nell'output della console
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }





    //Connection
    public Connection getConnection() {
        return connection;
    }

    //Disconnection
    public void disconnect() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
