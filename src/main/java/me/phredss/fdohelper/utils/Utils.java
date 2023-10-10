package me.phredss.fdohelper.utils;



import dev.lone.itemsadder.api.CustomStack;
import me.phredss.fdohelper.FdoHelper;
import me.phredss.fdohelper.config.FileManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class Utils {

    public static String translate(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String getPrefix() {
        String prefisso;
        if (getConfigFile().getString("custom-prefix") != null) {
            prefisso = ChatColor.translateAlternateColorCodes('&', getConfigFile().getString("custom-prefix"));
            return prefisso;
        } else {
            prefisso = ChatColor.translateAlternateColorCodes('&', "&3[&9&lFdo&3]&r ");
            return prefisso;
        }
    }

    public static FileConfiguration getConfigFile() {
        return FdoHelper.getInstance().getConfigFile().getConfig();
    }


    public static FileConfiguration getItemFile() {
        return FdoHelper.getInstance().getItemFile().getConfig();
    }

    public static String getItemName(String nomeItem) {
        return translate(getItemFile().getConfigurationSection("item." + nomeItem).getString("nome"));
    }

    public static String getInventoryName(String path) {
        return translate(getItemFile().getConfigurationSection("item.").getString(path));
    }

    public static List<String> getItemLore(String nomeItem) {
        List<String> lore = new ArrayList<>();
        for (String s : getItemFile().getConfigurationSection("item." + nomeItem).getStringList("lore")) {
            lore.add(translate(s));
        }
        if (lore.isEmpty()) {
        }
        return lore;
    }

    public static ItemStack getImprontaOn(Player player) {
        //Item ImprontaOn
        ItemStack improntaon = getItemIA(player, "mcicons:icon_toggle_on");
        List<String> loreon = new ArrayList<String>();
        ItemMeta metaon = improntaon.getItemMeta();
        metaon.setDisplayName("§r§aImpronta Digitale");
        loreon.add(translate("&7Clicca qui per"));
        loreon.add(translate("&cDisattivare &7l'Impronta Digitale"));
        metaon.setLore(loreon);
        metaon.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        improntaon.setItemMeta(metaon);
        return  improntaon;
    }

    public static ItemStack getImprontaOff(Player player) {
        //Item ImprontaOff
        ItemStack improntaoff = getItemIA(player, "mcicons:icon_toggle_off");
        List<String> loreoff = new ArrayList<String>();
        ItemMeta metaoff = improntaoff.getItemMeta();
        metaoff.setDisplayName("§r§cImpronta Digitale");
        loreoff.add(translate("&7Clicca qui per"));
        loreoff.add(translate("&aAttivare &7l'Impronta Digitale"));
        metaoff.setLore(loreoff);
        metaoff.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        improntaoff.setItemMeta(metaoff);
        return improntaoff;
    }

    public static ItemStack getSettings(Player player) {
        //Item Setting
        ItemStack setting = getItemIA(player, "mcicons:icon_color_picker");
        ItemMeta metaset = setting.getItemMeta();
        List<String> loreset = new ArrayList<String>();
        metaset.setDisplayName("§rImpostazioni Dispositivo");
        loreset.add(translate("&7Clicca qui per modificare"));
        loreset.add(translate("&7Le impostazioni del dispositivo"));
        metaset.setLore(loreset);
        setting.setItemMeta(metaset);
        return setting;
    }

    public static ItemStack getRicerca(Player player) {
        //Item Ricerca
        ItemStack ricerca = getItemIA(player, "mcicons:icon_search");
        List<String> loreric = new ArrayList<String>();
        ItemMeta metaric = ricerca.getItemMeta();
        metaric.setDisplayName("§rCerca");
        loreric.add(translate("&7Clicca qui per"));
        loreric.add(translate("&7Accedere ai database"));
        metaric.setLore(loreric);
        metaric.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        ricerca.setItemMeta(metaric);
        return ricerca;
    }

    public static ItemStack getRicercaMulte(Player player) {
        //Item Ricerca
        ItemStack ricerca = getItemIA(player, "mcicons:icon_search");
        List<String> loreric = new ArrayList<String>();
        ItemMeta metaric = ricerca.getItemMeta();
        metaric.setDisplayName("§rDatabase Multe");
        loreric.add(translate("&7Clicca qui per"));
        loreric.add(translate("&7Accedere al database"));
        metaric.setLore(loreric);
        metaric.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        ricerca.setItemMeta(metaric);
        return ricerca;
    }

    public static ItemStack getRicercaRicercati(Player player) {
        //Item Ricerca
        ItemStack ricerca = getItemIA(player, "mcicons:icon_search");
        List<String> loreric = new ArrayList<String>();
        ItemMeta metaric = ricerca.getItemMeta();
        metaric.setDisplayName("§rDatabase Ricercati");
        loreric.add(translate("&7Clicca qui per"));
        loreric.add(translate("&7Accedere al database"));
        metaric.setLore(loreric);
        metaric.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        ricerca.setItemMeta(metaric);
        return ricerca;
    }

    public static ItemStack getRicercaIdentikit(Player player) {
        //Item Ricerca
        ItemStack ricerca = getItemIA(player, "mcicons:icon_search");
        List<String> loreric = new ArrayList<String>();
        ItemMeta metaric = ricerca.getItemMeta();
        metaric.setDisplayName("§rDatabase Identikit");
        loreric.add(translate("&7Clicca qui per"));
        loreric.add(translate("&7Accedere al database"));
        metaric.setLore(loreric);
        metaric.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        ricerca.setItemMeta(metaric);
        return ricerca;
    }

    public static ItemStack getRicercaIdentikitSpecifico(Player player) {
        //Item Ricerca
        ItemStack ricerca = getItemIA(player, "mcicons:icon_search");
        List<String> loreric = new ArrayList<String>();
        ItemMeta metaric = ricerca.getItemMeta();
        metaric.setDisplayName("§rCerca");
        loreric.add(translate("&7Clicca qui per"));
        loreric.add(translate("&7Cercare un identikit."));
        metaric.setLore(loreric);
        metaric.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        ricerca.setItemMeta(metaric);
        return ricerca;
    }

    public static ItemStack getRicercaMulteSpecifico(Player player) {
        //Item Ricerca
        ItemStack ricerca = getItemIA(player, "mcicons:icon_search");
        List<String> loreric = new ArrayList<String>();
        ItemMeta metaric = ricerca.getItemMeta();
        metaric.setDisplayName("§rCerca");
        loreric.add(translate("&7Clicca qui per"));
        loreric.add(translate("&7Cercare una multa."));
        metaric.setLore(loreric);
        metaric.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        ricerca.setItemMeta(metaric);
        return ricerca;
    }
    public static ItemStack getRicercaRicercatoSpecifico(Player player) {
        //Item Ricerca
        ItemStack ricerca = getItemIA(player, "mcicons:icon_search");
        List<String> loreric = new ArrayList<String>();
        ItemMeta metaric = ricerca.getItemMeta();
        metaric.setDisplayName("§rCerca");
        loreric.add(translate("&7Clicca qui per"));
        loreric.add(translate("&7Cercare un ricercato."));
        metaric.setLore(loreric);
        metaric.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        ricerca.setItemMeta(metaric);
        return ricerca;
    }



    public static ItemStack getScarcera(Player player) {
        //Item Scarcera
        ItemStack scarcera = getItemIA(player, "mcicons:icon_unlock");
        List<String> loresca = new ArrayList<String>();
        ItemMeta metasca = scarcera.getItemMeta();
        metasca.setDisplayName("§rScarcera Cittadino");
        loresca.add(translate("&7Clicca qui per"));
        loresca.add(translate("&7Scarcerare un cittadino"));
        metasca.setLore(loresca);
        metasca.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        scarcera.setItemMeta(metasca);
        return scarcera;
    }

    public static ItemStack getArresta(Player player) {
        //Item Arresta
        ItemStack arresta = getItemIA(player, "mcicons:icon_lock");
        List<String> lorearr = new ArrayList<String>();
        ItemMeta metaarr = arresta.getItemMeta();
        metaarr.setDisplayName("§rArresta Cittadino");
        lorearr.add(translate("&7Clicca qui per"));
        lorearr.add(translate("&7Arrestare un cittadino"));
        metaarr.setLore(lorearr);
        metaarr.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        arresta.setItemMeta(metaarr);
        return arresta;
    }

    public static ItemStack getFilter(Player player) {
        //Item Filter
        String tema = FdoHelper.getAccountManager().getTemaFromDatabase(player);

        if (tema == null) {
            ItemStack filter = new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE, 1);
            return filter;
        }
        if (tema.equalsIgnoreCase("red")) {
            ItemStack filter = new ItemStack(Material.RED_STAINED_GLASS_PANE, 1);
            return filter;
        } else if (tema.equalsIgnoreCase("blue")) {
            ItemStack filter = new ItemStack(Material.BLUE_STAINED_GLASS_PANE, 1);
            return filter;
        } else if (tema.equalsIgnoreCase("lime")) {
            ItemStack filter = new ItemStack(Material.LIME_STAINED_GLASS_PANE, 1);
            return filter;
        } else if (tema.equalsIgnoreCase("azzurro")) {
            ItemStack filter = new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE, 1);
            return filter;
        }
        else {
            return null;
        }
    }

    public static ItemStack getResetPassword(Player player) {
        //Item Resetta Password
        ItemStack passreset = getItemIA(player, "mcicons:icon_cancel");
        List<String> loreres = new ArrayList<String>();
        ItemMeta metares = passreset.getItemMeta();
        metares.setDisplayName(translate("&cResetta Password"));
        metares.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        loreres.add(translate("&7Clicca qui per &cResettare"));
        loreres.add(translate("&7La password dell'account"));
        metares.setLore(loreres);
        passreset.setItemMeta(metares);
        return passreset;
    }
    public static ItemStack getDeleteIdentikit(Player player, String playerName) {
        //Item Resetta Password
        ItemStack passreset = getItemIA(player, "mcicons:icon_cancel");
        List<String> loreres = new ArrayList<String>();
        ItemMeta metares = passreset.getItemMeta();
        metares.setDisplayName(translate("&cRimuovi"));
        metares.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        loreres.add(translate("&7Clicca qui per &cRimuovere"));
        loreres.add(translate("&7L'Identikit dal database."));
        metares.setLore(loreres);
        passreset.setItemMeta(metares);
        return passreset;
    }

    public static ItemStack getDeleteMulte(Player player, String playerName) {
        //Item Resetta Password
        ItemStack passreset = getItemIA(player, "mcicons:icon_cancel");
        List<String> loreres = new ArrayList<String>();
        ItemMeta metares = passreset.getItemMeta();
        metares.setDisplayName(translate("&cRimuovi"));
        metares.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        loreres.add(translate("&7Clicca qui per &cRimuovere"));
        loreres.add(translate("&7La multa dal database."));
        metares.setLore(loreres);
        passreset.setItemMeta(metares);
        return passreset;
    }
    public static ItemStack getTema(Player player) {
        //Item Resetta Password
        ItemStack passreset = getItemIA(player, "mcicons:icon_color_picker");
        List<String> loreres = new ArrayList<String>();
        ItemMeta metares = passreset.getItemMeta();
        metares.setDisplayName(translate("§r§eTema"));
        metares.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        loreres.add(translate("&7Clicca qui per scegliere"));
        loreres.add(translate("&7Il tema da usare nel tablet."));
        metares.setLore(loreres);
        passreset.setItemMeta(metares);
        return passreset;
    }


    public static ItemStack getDeleteRicercato(Player player, String playerName) {
        //Item Resetta Password
        ItemStack passreset = getItemIA(player, "mcicons:icon_cancel");
        List<String> loreres = new ArrayList<String>();
        ItemMeta metares = passreset.getItemMeta();
        metares.setDisplayName(translate("&cRimuovi"));
        metares.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        loreres.add(translate("&7Clicca qui per &cRimuovere"));
        loreres.add(translate("&7La scheda del ricercato."));
        metares.setLore(loreres);
        passreset.setItemMeta(metares);
        return passreset;
    }

    public static ItemStack getCreaMulte(Player player) {
        //Item Resetta Password
        ItemStack passreset = getItemIA(player, "mcicons:icon_plus");
        List<String> loreres = new ArrayList<String>();
        ItemMeta metares = passreset.getItemMeta();
        metares.setDisplayName(translate("&aCrea"));
        metares.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        loreres.add(translate("&7Clicca qui per &aCreare"));
        loreres.add(translate("&7La multa dal database."));
        metares.setLore(loreres);
        passreset.setItemMeta(metares);
        return passreset;
    }

    public static ItemStack getAggiungiRicercato(Player player) {
        //Item Resetta Password
        ItemStack passreset = getItemIA(player, "mcicons:icon_plus");
        List<String> loreres = new ArrayList<String>();
        ItemMeta metares = passreset.getItemMeta();
        metares.setDisplayName(translate("&aAggiungi"));
        metares.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        loreres.add(translate("&7Clicca qui per &aAggiungere"));
        loreres.add(translate("&7Un ricercato al database."));
        metares.setLore(loreres);
        passreset.setItemMeta(metares);
        return passreset;
    }

    public static ItemStack getMulteRicrea(Player player) {
        //Item Resetta Password
        ItemStack passreset = getItemIA(player, "mcicons:icon_refresh");
        List<String> loreres = new ArrayList<String>();
        ItemMeta metares = passreset.getItemMeta();
        metares.setDisplayName(translate("&aCopia"));
        metares.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        loreres.add(translate("&7Clicca qui per stampare"));
        loreres.add(translate("&7Una copia della multa."));
        metares.setLore(loreres);
        passreset.setItemMeta(metares);
        return passreset;
    }
    public static ItemStack getMulteConfirm(Player player) {
        //Item Resetta Password
        ItemStack passreset = getItemIA(player, "mcicons:icon_confirm");
        List<String> loreres = new ArrayList<String>();
        ItemMeta metares = passreset.getItemMeta();
        metares.setDisplayName(translate("&aConferma"));
        metares.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        loreres.add(translate("&7Clicca qui per &aConfermare"));
        loreres.add(translate("&7La stampa della multa."));
        metares.setLore(loreres);
        passreset.setItemMeta(metares);
        return passreset;
    }
    public static ItemStack getRicercatoConfirm(Player player) {
        //Item Resetta Password
        ItemStack passreset = getItemIA(player, "mcicons:icon_confirm");
        List<String> loreres = new ArrayList<String>();
        ItemMeta metares = passreset.getItemMeta();
        metares.setDisplayName(translate("&aConferma"));
        metares.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        loreres.add(translate("&7Clicca qui per &aConfermare"));
        loreres.add(translate("&7L'aggiunta del ricercato."));
        metares.setLore(loreres);
        passreset.setItemMeta(metares);
        return passreset;
    }

    public static ItemStack getMulteCancel(Player player) {
        //Item Resetta Password
        ItemStack passreset = getItemIA(player, "mcicons:icon_cancel");
        List<String> loreres = new ArrayList<String>();
        ItemMeta metares = passreset.getItemMeta();
        metares.setDisplayName(translate("&cAnnulla"));
        metares.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        loreres.add(translate("&7Clicca qui per &cAnnullare"));
        loreres.add(translate("&7La stampa della multa."));
        metares.setLore(loreres);
        passreset.setItemMeta(metares);
        return passreset;
    }
    public static ItemStack getRicercatiCancel(Player player) {
        //Item Resetta Password
        ItemStack passreset = getItemIA(player, "mcicons:icon_cancel");
        List<String> loreres = new ArrayList<String>();
        ItemMeta metares = passreset.getItemMeta();
        metares.setDisplayName(translate("&cAnnulla"));
        metares.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        loreres.add(translate("&7Clicca qui per &cAnnullare"));
        loreres.add(translate("&7L'aggiunta del ricercato.'."));
        metares.setLore(loreres);
        passreset.setItemMeta(metares);
        return passreset;
    }



    public static ItemStack getItemLeft() {
        //Item Sinistro
        ItemStack itemLeft = new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE, 1);
        return itemLeft;
    }

    public static Inventory getTabletMenu(Player player) {
        //Creazione Gui Menu
        Inventory menu = Bukkit.createInventory(null, 54, "Menu Fdo");
        String playerUUID = player.getUniqueId().toString();

        //Item Arresta
        ItemStack arresta = getArresta(player);

        //Item Scarcera
        ItemStack scarcera = getScarcera(player);

        //Item Ricerca
        ItemStack ricerca = getRicerca(player);

        //Item ImprontaOff
        ItemStack improntaoff = getImprontaOff(player);

        //Item ImprontaOn
        ItemStack improntaon = getImprontaOn(player);

        //Skull Player
        ItemStack testa = new ItemStack(Material.PLAYER_HEAD, 1);
        List<String> loretes = new ArrayList<String>();
        SkullMeta metates = (SkullMeta) testa.getItemMeta();
        metates.setOwningPlayer(player);
        metates.setDisplayName("§rInformazioni Personali");
        loretes.add(translate("&7Nome: &f" + player.getName()));
        loretes.add(translate("§7Data: &f" + getDataEntrata()));
        loretes.add(translate(""));
        metates.setLore(loretes);
        testa.setItemMeta(metates);

        //Item Setting
        ItemStack setting = getSettings(player);



        //Item Filter
        ItemStack filter = getFilter(player);

        menu.setItem(0, filter);
        menu.setItem(1, filter);
        menu.setItem(2, filter);
        menu.setItem(3, filter);
        menu.setItem(4, filter);
        menu.setItem(5, filter);
        menu.setItem(6, filter);
        menu.setItem(7, filter);
        menu.setItem(8, filter);
        menu.setItem(9, filter);
        menu.setItem(17, filter);
        menu.setItem(18, filter);
        menu.setItem(26, filter);
        menu.setItem(27, filter);
        menu.setItem(35, filter);
        menu.setItem(36, filter);
        menu.setItem(44, filter);
        menu.setItem(45, filter);
        menu.setItem(46, filter);
        menu.setItem(47, filter);
        menu.setItem(48, filter);
        menu.setItem(49, filter);
        menu.setItem(50, filter);
        menu.setItem(51, filter);
        menu.setItem(52, filter);
        menu.setItem(53, filter);

        menu.setItem(20, arresta);
        menu.setItem(21, scarcera);
        menu.setItem(29, ricerca);
        menu.setItem(24, testa);
        menu.setItem(33, setting);
        if (FdoHelper.getAccountManager().checkImpronta(playerUUID)) {
            menu.setItem(30, improntaon);
        }
        else {
            menu.setItem(30, improntaoff);
        }
        return menu;
    }


    public static boolean isDouble(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    //Messages Config

    public static FileConfiguration getMessagesFile() {
        return FdoHelper.getInstance().getMessagesFile().getConfig();
    }
    public static String getMessagesNoPermission(String sezione) {
        return translate(getMessagesFile().getConfigurationSection("messages." + sezione).getString("no-permessi"));
    }

    public static String getMessagesSintassiErrataFdo(String sezione) {
        return translate(getMessagesFile().getConfigurationSection("messages." + sezione).getString("sintassi-errata-fdo"));
    }

    public static String getMessagesSintassiErrataJail(String sezione) {
        return translate(getMessagesFile().getConfigurationSection("messages." + sezione).getString("sintassi-errata-jail"));
    }

    public static String getMessagesJailInesistente(String sezione) {
        return translate(getMessagesFile().getConfigurationSection("messages." + sezione).getString("jail-inesistente"));
    }

    public static String getMessagesJailEsistente(String sezione) {
        return translate(getMessagesFile().getConfigurationSection("messages." + sezione).getString("jail-esistente"));
    }

    public static String getMessagesJailCreata(String sezione) {
        return translate(getMessagesFile().getConfigurationSection("messages." + sezione).getString("creazione"));
    }

    public static String getMessagesJailRemove(String sezione) {
        return translate(getMessagesFile().getConfigurationSection("messages." + sezione).getString("rimozione"));
    }

    public static String getMessagesUserJailed(String sezione) {
        return translate(getMessagesFile().getConfigurationSection("messages." + sezione).getString("utente-jailato"));
    }
    public static String getMessagesUserJailedMess(String sezione) {
        return translate(getMessagesFile().getConfigurationSection("messages." + sezione).getString("utente-jailato-mess"));
    }

    public static String getMessagesIdentikitCrea(String sezione) {
        return translate(getMessagesFile().getConfigurationSection("messages." + sezione).getString("creazione"));
    }

    public static String getMessagesIdentikitRimuovi(String sezione) {
        return translate(getMessagesFile().getConfigurationSection("messages." + sezione).getString("rimozione"));
    }

    public static String getMessagesIdentikitEsistente(String sezione) {
        return translate(getMessagesFile().getConfigurationSection("messages." + sezione).getString("idenikit-esistente"));
    }

    public static String getMessagesPasswordErrata(String sezione) {
        return translate(getMessagesFile().getConfigurationSection("messages." + sezione).getString("fdologin-passworderrata"));
    }

    /*

        Return Data Entrata in Jail sottoforma di stringa seguendo un determinato format

     */

    public static String getDataEntrata() {
        ZoneId zoneId = ZoneId.of("Europe/Rome");
        ZonedDateTime data = ZonedDateTime.now(zoneId);
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return data.format(formato); //Esempio di return 16/07/2023
    }

    public static String getOreEntrata() {
        ZoneId zoneId = ZoneId.of("Europe/Rome");
        ZonedDateTime ore = ZonedDateTime.now(zoneId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String formattedTime = formatter.format(ore);
        System.out.println("Ora, minuti e secondi: " + formattedTime);
        return ore.format(formatter);
    }

    public static String calculateTimeDifference(String dataScadenza) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        LocalDateTime dateTime1 = LocalDateTime.parse(getDataEntrata(), formatter);
        LocalDateTime dateTime2 = LocalDateTime.parse(dataScadenza, formatter);
        Duration duration = Duration.between(dateTime1, dateTime2);

        long days = duration.toDays();
        long hours = duration.toHoursPart();
        long minutes = duration.toMinutesPart();

        StringBuilder sb = new StringBuilder();

        if (dateTime1.isAfter(dateTime2)) {
            sb.append(translate("&eScaduto"));
            return sb.toString().trim();
        }


        if (days > 0) {
                sb.append(days).append(days == 1 ? " giorno " : " giorni ");
            }
        if (hours > 0) {
                sb.append(hours).append(hours == 1 ? " ora " : " ore ");
            }
        if (minutes > 0) {
                sb.append(minutes).append(minutes == 1 ? " minuto " : " minuti");
            }

        return sb.toString().trim();
    }

    public static String checkTimeStatus(String dataScadenza) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        LocalDateTime dateTime1 = LocalDateTime.parse(getDataEntrata(), formatter);
        LocalDateTime dateTime2 = LocalDateTime.parse(dataScadenza, formatter);
        Duration duration = Duration.between(dateTime1, dateTime2);

        if (dateTime1.isAfter(dateTime2)) {
            return translate("&cScaduto");
        } else {
            return translate("&aAttivo");
        }
    }

    //ItemsAdder
    public static ItemStack getItemIA(Player player, String idItem) {
        CustomStack stack = CustomStack.getInstance(idItem);
        if(stack != null) {
            ItemStack itemStack = stack.getItemStack();
            ItemMeta meta = itemStack.getItemMeta();
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            meta.setCustomModelData(10469);
            itemStack.setItemMeta(meta);
            return itemStack;
        }
        else {
            player.sendMessage(translate("&cL'item/blocco scelto non esiste."));
            return null;
        }
    }

    public static String getItemsAdderConfig(String nomeItem) {
        return getItemFile().getConfigurationSection("items." + nomeItem).getString(".itemsadder-item");
    }




    //Permissions Config
    public static FileConfiguration getPermissionsFile() {
        return FdoHelper.getInstance().getPermissionsFile().getConfig();
    }
    public static String getPermissions(String gruppo) {
        return getPermissionsFile().getConfigurationSection("permissions." + gruppo).getString("permesso");
    }


    //Gui Config
    public static FileConfiguration getGuiFile() {
        return FdoHelper.getInstance().getGuiFile().getConfig();
    }

    public static String getGuiName(String gui) {
        return translate(getGuiFile().getConfigurationSection("gui." + gui).getString(".nome-gui"));
    }

    public static String getGuiItemNome(String gui, String nomeItem) {
        return translate(getGuiFile().getConfigurationSection("gui." + gui + ".items." + nomeItem).getString(".nome"));
    }

    public static List<String> getGuiItemLore(String gui, String nomeItem) {
        List<String> lore = new ArrayList<>();
        for (String s : getGuiFile().getConfigurationSection("gui." + gui + ".items." + nomeItem).getStringList(".lore")) {
            lore.add(translate(s));
        }
        if (lore.isEmpty()) {
        }
        return lore;
    }

    public static Material getGuiItemMaterial(String gui, String nomeItem) {
        String mat = getGuiFile().getString("gui." + gui + ".items." + nomeItem + ".materiale");
        if (Material.valueOf(mat) != null) {
            return Material.valueOf(getGuiFile().getString("gui." + gui + ".items." + nomeItem + ".materiale"));
        } else {

            return null;
        }
    }

    //Jail Config
    public static FileManager getJailFile() {
        return FdoHelper.getInstance().getJailFile();
    }
    public static FileConfiguration getJailConfig() {
        return FdoHelper.getInstance().getJailFile().getConfig();
    }
    public static void createJail(String nomeJail, Player player, int X, int Y, int Z) {
        if (getJailConfig().isConfigurationSection("jails." + nomeJail)) {
            player.sendMessage(translate(getPrefix() + getMessagesJailEsistente("jail")));
            return;
        }
        try {
            getJailConfig().createSection("jails." + nomeJail);
            getJailConfig().set("jails." + nomeJail + ".coordinate." + "X", X);
            getJailConfig().set("jails." + nomeJail + ".coordinate." + "Y", Y);
            getJailConfig().set("jails." + nomeJail + ".coordinate." + "Z", Z);
            getJailFile().saveConfig();
            getJailFile().reload();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void removeJail(String nomeJail, Player player) {
        if (!getJailConfig().isConfigurationSection("jails." + nomeJail)) {
            player.sendMessage(translate(getPrefix() + getMessagesJailInesistente("jail")));
        }
        else {
            try {
                getJailConfig().getConfigurationSection("jails.").set(nomeJail, null);
                player.sendMessage(translate(getPrefix() + getMessagesJailRemove("jail")));
                getJailFile().saveConfig();
                getJailFile().reload();
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }


    }

    public static boolean checkJailExists(String nomeJail) {
        return getJailConfig().isConfigurationSection("jails." + nomeJail);
    }
    public static int getJailCoordX(String nomeJail) {
        return getJailConfig().getInt("jails." + nomeJail + ".coordinate.X");
    }

    public static int getJailCoordY(String nomeJail) {
        return getJailConfig().getInt("jails." + nomeJail + ".coordinate.Y");
    }

    public static int getJailCoordZ(String nomeJail) {
        return getJailConfig().getInt("jails." + nomeJail + ".coordinate.Z");
    }


    //Position

    public static FileManager getPositionFile() {
        return FdoHelper.getInstance().getPositionFile();
    }
    public static FileConfiguration getPositionConfig() {
        return FdoHelper.getInstance().getPositionFile().getConfig();
    }


    public static boolean checkPositionExists(String nomePosizione) {
        return getPositionConfig().isConfigurationSection("position." + nomePosizione);
    }

    public static void createPosition(String nomePosition, Player player, int X, int Y, int Z, float pitch, float yaw) {
        if (getPositionConfig().isConfigurationSection("position." + nomePosition)) {
            player.sendMessage(translate(getPrefix() + "&cPosizione già impostata, eliminala e riprova."));
            return;
        }
        try {
            getPositionConfig().createSection("position." + nomePosition);
            getPositionConfig().set("position." + nomePosition + ".coordinate." + "X", X);
            getPositionConfig().set("position." + nomePosition + ".coordinate." + "Y", Y);
            getPositionConfig().set("position." + nomePosition + ".coordinate." + "Z", Z);
            getPositionConfig().set("position." + nomePosition + ".coordinate." + "pitch", pitch);
            getPositionConfig().set("position." + nomePosition + ".coordinate." + "yaw", yaw);
            getPositionFile().saveConfig();
            getPositionFile().reload();
            player.sendMessage(translate(getPrefix() + "&aPosizione Creata con successo!"));
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void removePosition(String nomePosition, Player player) {
        if (!getPositionConfig().isConfigurationSection("position." + nomePosition)) {
            player.sendMessage(translate(getPrefix() + "&cPosizione inesistente, settala."));
        }
        else {
            try {
                getPositionConfig().getConfigurationSection("position.").set(nomePosition, null);
                player.sendMessage(translate(getPrefix() + "&cPosizione rimossa con successo!"));
                getPositionFile().saveConfig();
                getPositionFile().reload();
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }


    }

    public static int getFotoCoordX(String nomePosizione) {
        return getPositionConfig().getInt("position." + nomePosizione + ".coordinate.X");
    }

    public static int getFotoCoordY(String nomePosizione) {
        return getPositionConfig().getInt("position." + nomePosizione + ".coordinate.Y");
    }

    public static int getFotoCoordZ(String nomePosizione) {
        return getPositionConfig().getInt("position." + nomePosizione + ".coordinate.Z");
    }

    public static float getFotoPitch(String nomePosizione) {
        return (float) getPositionConfig().getDouble("position." + nomePosizione + ".coordinate.pitch");
    }

    public static float getFotoYaw(String nomePosizione) {
        return (float) getPositionConfig().getDouble("position." + nomePosizione + ".coordinate.yaw");
    }


}



