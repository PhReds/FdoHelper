package me.phredss.fdohelper.sqlite;

import java.util.UUID;

public class Identikit {

    private int id;
    private UUID playerUUID;
    private String motivo;
    private double cauzione;
    private String dataEntrata;
    private String dataScadenza;

    public Identikit(int id, UUID playerUUID, String motivo, double cauzione, String dataEntrata, String dataScadenza) {
        this.id = id;
        this.playerUUID = playerUUID;
        this.motivo = motivo;
        this.cauzione = cauzione;
        this.dataEntrata = dataEntrata;
        this.dataScadenza = dataScadenza;
    }

    public int getId() {
        return id;
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public String getMotivo() {
        return motivo;
    }

    public double getImporto() {
        return cauzione;
    }

    public String getDataCreazione() {
        return dataEntrata;
    }

    public String getDataScadenza() {
        return dataScadenza;
    }
}
