package me.phredss.fdohelper.sqlite;

import java.util.UUID;

public class Multe {

    private int numeroMulta;
    private UUID playerUUID;
    private String motivo;
    private double costo;
    private String dataStampa;
    private String dataScadenza;

    public Multe(int numeroMulta, UUID playerUUID, String motivo, double costo, String dataStampa, String dataScadenza) {

        this.numeroMulta = numeroMulta;
        this.playerUUID = playerUUID;
        this.motivo = motivo;
        this.costo = costo;
        this.dataStampa = dataStampa;
        this.dataScadenza = dataScadenza;
    }

    public int getNumeroMulta(){return numeroMulta;}

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public String getMotivo() {
        return motivo;
    }

    public double getCosto() {
        return costo;
    }

    public String getDataCreazione() {
        return dataStampa;
    }

    public String getDataScadenza() {
        return dataScadenza;
    }
}
