// MulteManager.java
package me.phredss.fdohelper.sqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static me.phredss.fdohelper.utils.Utils.getDataEntrata;

public class MulteManager {

    private SQLiteManager sqLiteManager;

    public MulteManager(SQLiteManager dbManager) {
        this.sqLiteManager = dbManager;
    }

    public void addMulta(UUID playerUUID, String playerName, String motivo, double costo, String dataScadenza) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

        Connection connection = sqLiteManager.getConnection();

        try {
            // Trova il massimo numero_multa attuale nella tabella
            PreparedStatement getMaxNumeroMulta = connection.prepareStatement(
                    "SELECT MAX(numero_multa) FROM multe"
            );
            ResultSet resultSet = getMaxNumeroMulta.executeQuery();
            int nuovoNumeroMulta = 1;  // Imposta il valore predefinito a 1 se la tabella è vuota

            if (resultSet.next()) {
                nuovoNumeroMulta = resultSet.getInt(1) + 1;
            }
            resultSet.close();
            getMaxNumeroMulta.close();

            // Inserisci una nuova multa con il numero_multa incrementato
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO multe (numero_multa, player_uuid, player_name, motivo, costo, data_stampa, data_scadenza) VALUES (?, ?, ?, ?, ?, ?, ?)");
            statement.setInt(1, nuovoNumeroMulta);
            statement.setString(2, playerUUID.toString());
            statement.setString(3, playerName);
            statement.setString(4, motivo);
            statement.setDouble(5, costo);
            statement.setString(6, getDataEntrata());  // Imposta la Data Multa
            statement.setString(7, dataScadenza.replaceAll("_", "/"));
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeMulta(UUID playerUUID, int numeroMulta) {
        Connection connection = sqLiteManager.getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM multe WHERE player_uuid = ? AND numero_multa = ?");
            statement.setString(1, playerUUID.toString());
            statement.setInt(2, numeroMulta);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Multe> getMultaByPlayerUUID(UUID playerUUID) {
        List<Multe> multeList = new ArrayList<>();

        Connection connection = sqLiteManager.getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT numero_multa, motivo, costo, data_stampa, data_scadenza FROM multe WHERE player_uuid = ?");
            statement.setString(1, playerUUID.toString());

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int numeroMultato = resultSet.getInt("numero_multa");
                String motivo = resultSet.getString("motivo");
                double cauzione = resultSet.getDouble("costo");
                String dataEntrata = resultSet.getString("data_stampa");
                String dataScadenza = resultSet.getString("data_scadenza");

                Multe multa = new Multe(numeroMultato, playerUUID, motivo, cauzione, dataEntrata, dataScadenza);
                multeList.add(multa);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return multeList;
    }

    public List<String> getPlayerMulte() {
        List<String> playerUUIDs = new ArrayList<>();
        Connection connection = sqLiteManager.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT player_uuid FROM multe"
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
