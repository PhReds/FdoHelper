package me.phredss.fdohelper.sqlite;


import me.phredss.fdohelper.FdoHelper;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.naming.Name;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

import static me.phredss.fdohelper.utils.Utils.*;


public class IdentikitManager {

    private SQLiteManager sqLiteManager;

    public IdentikitManager(SQLiteManager dbManager) {
        this.sqLiteManager = dbManager;
    }


    public void addIdentikit(UUID playerUUID, String playerName, String motivo, double cauzione, String dataEntrata, String dataScadenza) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

        Connection connection = sqLiteManager.getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO identikit (player_uuid, player_name, motivo, cauzione, data_entrata, data_scadenza) VALUES (?, ?, ?, ?, ?, ?)");
            statement.setString(1, playerUUID.toString());
            statement.setString(2, playerName);
            statement.setString(3, motivo);
            statement.setDouble(4, cauzione);
            statement.setString(5, getDataEntrata());  // Imposta la Data Multa
            statement.setString(6, dataScadenza.replaceAll(
                    "_", "/"
            ));
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkIdentikitExists(String playerUUID) {
        Connection connection = sqLiteManager.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT COUNT(*) FROM identikit WHERE player_uuid = ?")) {
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

    public void removeIdentikit(UUID playerUUID) {
        Connection connection = sqLiteManager.getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM identikit WHERE player_uuid = ?");
            statement.setString(1, playerUUID.toString());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Identikit> getIdentikitByPlayerUUID(UUID playerUUID) {
        List<Identikit> identikitList = new ArrayList<>();

        Connection connection = sqLiteManager.getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM identikit WHERE player_uuid = ?");
            statement.setString(1, playerUUID.toString());

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String motivo = resultSet.getString("motivo");
                double cauzione = resultSet.getDouble("cauzione");

                String dataEntrata = resultSet.getString("data_entrata");
                String dataScadenza = resultSet.getString("data_scadenza");

                Identikit identikit = new Identikit(id, playerUUID, motivo, cauzione, dataEntrata, dataScadenza);
                identikitList.add(identikit);

            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return identikitList;
    }

    public List<String> getPlayerHeads() {
        List<String> playerUUIDs = new ArrayList<>();
        Connection connection = sqLiteManager.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT player_uuid FROM identikit"
        );
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String playerUUID = resultSet.getString("player_uuid");
                playerUUIDs.add(playerUUID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Inverti l'ordine della lista
        Collections.reverse(playerUUIDs);

        return playerUUIDs;
    }




}