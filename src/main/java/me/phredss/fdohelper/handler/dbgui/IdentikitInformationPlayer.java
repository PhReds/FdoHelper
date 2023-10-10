package me.phredss.fdohelper.handler.dbgui;

import me.phredss.fdohelper.FdoHelper;
import me.phredss.fdohelper.sqlite.Identikit;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static me.phredss.fdohelper.sqlite.IdentikitManager.*;
import static me.phredss.fdohelper.utils.Utils.*;

public class IdentikitInformationPlayer implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        ItemStack item = e.getCurrentItem();

        // Verifica che l'evento provenga dall'inventario corretto
        if (!e.getView().getTitle().equalsIgnoreCase("Database Identikit")) {
            return;
        }
        e.setCancelled(true);
        // Verifica che l'oggetto cliccato sia una testa di giocatore
        if (item != null && item.getType() == Material.PLAYER_HEAD) {
            SkullMeta skullMeta = (SkullMeta) item.getItemMeta();

            // Verifica che il Meta della testa sia valido
            if (skullMeta != null) {
                PersistentDataContainer data = skullMeta.getPersistentDataContainer();
                String uuidString = data.get(new NamespacedKey(FdoHelper.getPlugin(FdoHelper.class), "player_uuid"), PersistentDataType.STRING);
                OfflinePlayer target = Bukkit.getOfflinePlayer(UUID.fromString(uuidString)); // Utilizza UUID.fromString
                UUID uuid = target.getUniqueId();

                // Crea un inventario per l'identikit
                Inventory identikit = Bukkit.createInventory(null, 54, "Identikit di " + target.getName());

                // Imposta l'item di filtro
                ItemStack filterItem = getFilter(player);
                identikit.setItem(0, filterItem);
                identikit.setItem(1, filterItem);
                identikit.setItem(2, filterItem);
                identikit.setItem(3, filterItem);
                identikit.setItem(4, filterItem);
                identikit.setItem(5, filterItem);
                identikit.setItem(6, filterItem);
                identikit.setItem(7, filterItem);
                identikit.setItem(8, filterItem);
                identikit.setItem(9, filterItem);
                identikit.setItem(17, filterItem);
                identikit.setItem(18, filterItem);
                identikit.setItem(26, filterItem);
                identikit.setItem(27, filterItem);
                identikit.setItem(35, filterItem);
                identikit.setItem(36, filterItem);
                identikit.setItem(44, filterItem);
                identikit.setItem(45, filterItem);
                identikit.setItem(46, filterItem);
                identikit.setItem(47, filterItem);
                identikit.setItem(48, filterItem);
                identikit.setItem(49, filterItem);
                identikit.setItem(50, filterItem);
                identikit.setItem(51, filterItem);
                identikit.setItem(52, filterItem);
                identikit.setItem(53, filterItem);

                // Aggiungi informazioni all'identikit
                List<Identikit> identikitList = FdoHelper.getIdentikitManager().getIdentikitByPlayerUUID(uuid);
                for (Identikit identikitLists : identikitList) {
                    ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
                    SkullMeta skullMetas = (SkullMeta) playerHead.getItemMeta();
                    skullMetas.setOwningPlayer(target);
                    skullMetas.setDisplayName(translate("&r&f&l" + target.getName()));
                    List<String> lores = new ArrayList<String>();
                    lores.add(translate("&7Cittadino: &f" + target.getName()));
                    lores.add(translate("&7Motivo: &f" + identikitLists.getMotivo()));
                    lores.add(translate("&7Importo Cauzione: &f" + identikitLists.getImporto()));
                    lores.add(translate("&7Data Arresto: &f" + identikitLists.getDataCreazione()));
                    lores.add(translate("&7Data Scadenza: &f" + identikitLists.getDataScadenza()));
                    lores.add(translate("&7Tempo Rimanente: &f" + calculateTimeDifference(identikitLists.getDataScadenza())));
                    skullMetas.setLore(lores);
                    playerHead.setItemMeta(skullMetas);

                    // Aggiungi l'item della testa del giocatore all'inventario identikit
                    identikit.setItem(22, playerHead);

                    // Aggiungi l'item per rimuovere l'identikit
                    ItemStack rimuoviIdentikit = getDeleteIdentikit(player, target.getName());
                    ItemMeta meta = rimuoviIdentikit.getItemMeta();
                    PersistentDataContainer dataS = meta.getPersistentDataContainer();
                    dataS.set(new NamespacedKey(FdoHelper.getPlugin(FdoHelper.class), "player"), PersistentDataType.STRING, uuid.toString());
                    rimuoviIdentikit.setItemMeta(meta);
                    identikit.setItem(40, rimuoviIdentikit);

                    // Chiudi l'inventario del giocatore e apri l'inventario identikit
                    player.closeInventory();
                    player.openInventory(identikit);


                }


            }
        }
    }
}
