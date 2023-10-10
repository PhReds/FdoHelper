package me.phredss.fdohelper.sqlite;

import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;


public class AccountManager {

    private SQLiteManager sqLiteManager;

    public AccountManager(SQLiteManager dbManager) {
        this.sqLiteManager = dbManager;
    }


    public void addAccount(UUID playerUUID, String password, Boolean impronta, String tema) {
        Connection connection = sqLiteManager.getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO accounts (player_uuid, password, impronta, tema) VALUES (?, ?, ?, ?)");
            statement.setString(1, playerUUID.toString());
            statement.setString(2, password);
            statement.setBoolean(3, impronta);
            statement.setString(4, tema);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public boolean checkAccountExists(String playerUUID) {
        Connection connection = sqLiteManager.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT COUNT(*) FROM accounts WHERE player_uuid = ?")) {
            preparedStatement.setString(1, playerUUID);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Estrai il valore dalla query (sarà 0 o 1)
            int count = resultSet.getInt(1);

            resultSet.close();
            preparedStatement.close();

            // Restituisci true se la riga esiste, altrimenti false
            if (count > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Restituisci false in caso di errore
        }
    }

    public String getAccountPassword(String playerUUUID) {
        Connection connection = sqLiteManager.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT password FROM accounts WHERE player_uuid = ?"
        )) {
            preparedStatement.setString(1, playerUUUID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("password");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Player UUID not found or password not set
    }

    public void activateImpronta(String playerUUID) {
        Connection connection = sqLiteManager.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE accounts SET impronta = ? WHERE player_uuid = ?")) {
            preparedStatement.setBoolean(1, true);
            preparedStatement.setString(2, playerUUID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void changeTema(String playerUUID, Player player, String tema) {
        Connection connection = sqLiteManager.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE accounts SET tema = ? WHERE player_uuid = ?")) {
            preparedStatement.setString(1, tema);
            preparedStatement.setString(2, playerUUID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void disactivateImpronta(String playerUUID) {
        Connection connection = sqLiteManager.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE accounts SET impronta = ? WHERE player_uuid = ?")) {
            preparedStatement.setBoolean(1, false);
            preparedStatement.setString(2, playerUUID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkImpronta(String playerUUID) {
        Connection connection = sqLiteManager.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT impronta FROM accounts WHERE player_uuid = ?")) {
            preparedStatement.setString(1, playerUUID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getBoolean("impronta");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Se il playerUUID non esiste o "impronta" non è impostata, restituisci false
    }

    public void resetPassword(String playerUUID, String password) {
        Connection connection = sqLiteManager.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE accounts SET password = ? WHERE player_uuid = ?")) {
            preparedStatement.setString(1, password);
            preparedStatement.setString(2, playerUUID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeAccount(String playerUUID) {
        Connection connection = sqLiteManager.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM accounts WHERE player_uuid = ?"
        )) {
            preparedStatement.setString(1, playerUUID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }
    public String getTemaFromDatabase(Player player) {
        String playerUuid = player.getUniqueId().toString();
        String tema = null;
        Connection connection = sqLiteManager.getConnection();

            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT tema FROM accounts WHERE player_uuid = ?"
            )) {
                statement.setString(1, playerUuid);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        tema = resultSet.getString("tema");
                    }
                }
            }
         catch (SQLException e) {
            e.printStackTrace();
        }

        return tema;
    }
}


