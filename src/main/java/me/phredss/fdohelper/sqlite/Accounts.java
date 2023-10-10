package me.phredss.fdohelper.sqlite;

import java.util.UUID;

public class Accounts {
    private UUID playerUUID;
    private String password;
    private Boolean impronta;
    private String tema;

    public Accounts(UUID playerUUID, String password, Boolean impronta, String tema) {
        this.playerUUID = playerUUID;
        this.password = password;
        this.impronta = impronta;
        this.tema = tema;
    }


    public UUID getPlayerUUID() {
        return playerUUID;
    }
    public String getPassword() {
        return password;
    }
    public Boolean getImpronta() {
        return impronta;
    }
    public String getTema() {return tema;}



}
