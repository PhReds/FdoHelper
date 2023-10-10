package me.phredss.fdohelper;


import lombok.Getter;
import me.phredss.fdohelper.command.FdoCommand;
import me.phredss.fdohelper.command.JailCommand;
import me.phredss.fdohelper.command.tablcompletion.FdoTabCompletion;
import me.phredss.fdohelper.command.tablcompletion.JailTabCompletion;
import me.phredss.fdohelper.config.FileManager;
import me.phredss.fdohelper.handler.dbgui.IdentikitCerca;
import me.phredss.fdohelper.handler.dbgui.IdentikitGui;
import me.phredss.fdohelper.handler.dbgui.IdentikitInformationPlayer;
import me.phredss.fdohelper.handler.dbgui.RemoveIdentikit;
import me.phredss.fdohelper.handler.multegui.*;
import me.phredss.fdohelper.handler.settinggui.AggiornaTema;
import me.phredss.fdohelper.handler.settinggui.ResetPasswordClick;
import me.phredss.fdohelper.handler.settinggui.TemaClick;
import me.phredss.fdohelper.handler.tabletmenu.*;
import me.phredss.fdohelper.licenza.AdvancedLicense;
import me.phredss.fdohelper.sqlite.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class FdoHelper extends JavaPlugin{

    private static SQLiteManager sqliteManager;
    private static IdentikitManager identikitManager;
    private static AccountManager accountManager;
    private static MulteManager multeManager;


    @Getter
    private static FdoHelper instance;
    @Getter
    private FileManager configFile, itemFile, messagesFile, permissionsFile, guiFile, jailFile, positionFile;

    @Override
    public void onEnable() {
        instance = this;

        configFile = new FileManager("config", instance);
        itemFile = new FileManager("item", instance);
        messagesFile = new FileManager("messages", instance);
        permissionsFile = new FileManager("permissions", instance);
        guiFile = new FileManager("gui", instance);
        jailFile = new FileManager("jail", instance);
        positionFile = new FileManager("position", instance);
        String licenza = getConfig().getString("LICENSE-KEY");
        System.out.println("Licenza Sout: " + licenza);
        if(!new AdvancedLicense(licenza, "https://phredsslicensesystem.000webhostapp.com/verify.php", this).register()) {
            FdoHelper.getPlugin(FdoHelper.class).onDisable();
            return;
        }



        //Sqlite
        sqliteManager = new SQLiteManager(this);

        File pluginFolder = getDataFolder();

        String dbPath = pluginFolder.getAbsolutePath() + File.separator + "database.db";
        sqliteManager.connect(dbPath);
        sqliteManager.createTables();
        sqliteManager.createAccountTable();
        sqliteManager.createMulteTable();
        identikitManager = new IdentikitManager(sqliteManager);
        accountManager = new AccountManager(sqliteManager);
        multeManager = new MulteManager(sqliteManager);

        //Registri Comandi
        getCommand("fdo").setExecutor(new FdoCommand());
        getCommand("fdo").setTabCompleter(new FdoTabCompletion(this));
        getCommand("jail").setExecutor(new JailCommand());
        getCommand("jail").setTabCompleter(new JailTabCompletion(this));

        //Registri Eventi
        getServer().getPluginManager().registerEvents(new PlayerClick(), this);
        getServer().getPluginManager().registerEvents(new changeImpronta(), this);
        getServer().getPluginManager().registerEvents(new SettingGui(), this);
        getServer().getPluginManager().registerEvents(new ResetPasswordClick(), this);
        getServer().getPluginManager().registerEvents(new ArrestaPlayer(), this);
        getServer().getPluginManager().registerEvents(new RicercaGui(), this);
        getServer().getPluginManager().registerEvents(new IdentikitGui(), this);
        getServer().getPluginManager().registerEvents(new IdentikitInformationPlayer(), this);
        getServer().getPluginManager().registerEvents(new RemoveIdentikit(), this);
        getServer().getPluginManager().registerEvents(new IdentikitCerca(), this);
        getServer().getPluginManager().registerEvents(new MulteGui(), this);
        getServer().getPluginManager().registerEvents(new MulteInformationPlayer(), this);
        getServer().getPluginManager().registerEvents(new RemoveMulta(), this);
        getServer().getPluginManager().registerEvents(new MulteCerca(), this);
        getServer().getPluginManager().registerEvents(new CreaMulte(), this);
        getServer().getPluginManager().registerEvents(new StampaMulta(), this);
        getServer().getPluginManager().registerEvents(new CercaResultInformationPlayer(), this);
        getServer().getPluginManager().registerEvents(new TemaClick(), this);
        getServer().getPluginManager().registerEvents(new AggiornaTema(), this);
        getServer().getPluginManager().registerEvents(new ScarceraPlayer(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        if (sqliteManager != null) {
            sqliteManager.disconnect();
            System.out.println("Disconnessione Database");
        }
    }
    public static SQLiteManager getSqlManager(){
        return sqliteManager;
    }
    public static IdentikitManager getIdentikitManager(){
        return identikitManager;
    }
    public static AccountManager getAccountManager() {return accountManager;}
    public static MulteManager getMulteManager() {return  multeManager;}
}
