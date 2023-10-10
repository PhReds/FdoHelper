package me.phredss.fdohelper.handler.multegui;

import me.phredss.fdohelper.FdoHelper;
import me.phredss.fdohelper.sqlite.Identikit;
import me.phredss.fdohelper.sqlite.Multe;
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

import static me.phredss.fdohelper.utils.Utils.*;
import static me.phredss.fdohelper.utils.Utils.getDeleteIdentikit;

public class CercaResultInformationPlayer implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();

        if (e.getView().getTitle().startsWith("Multe di")) {
            e.setCancelled(true);
            ItemStack clicked = e.getCurrentItem();
            if (clicked == null) return;
            int slot = e.getRawSlot();
            if (clicked.getType() == Material.PLAYER_HEAD) {
                SkullMeta skullMeta = (SkullMeta)  clicked.getItemMeta();

                // Verifica che il Meta della testa sia valido
                if (skullMeta != null) {
                    PersistentDataContainer data = skullMeta.getPersistentDataContainer();
                    String uuidString = data.get(new NamespacedKey(FdoHelper.getPlugin(FdoHelper.class), "player_uuid"), PersistentDataType.STRING);
                    OfflinePlayer target = Bukkit.getOfflinePlayer(UUID.fromString(uuidString)); // Utilizza UUID.fromString
                    UUID uuid = target.getUniqueId();

                    // Crea un inventario per l'identikit
                    Inventory multe = Bukkit.createInventory(null, 54, "Multa di " + target.getName());

                    // Imposta l'item di filtro
                    ItemStack filterItem = getFilter(player);
                    multe.setItem(0, filterItem);
                    multe.setItem(1, filterItem);
                    multe.setItem(2, filterItem);
                    multe.setItem(3, filterItem);
                    multe.setItem(4, filterItem);
                    multe.setItem(5, filterItem);
                    multe.setItem(6, filterItem);
                    multe.setItem(7, filterItem);
                    multe.setItem(8, filterItem);
                    multe.setItem(9, filterItem);
                    multe.setItem(17, filterItem);
                    multe.setItem(18, filterItem);
                    multe.setItem(26, filterItem);
                    multe.setItem(27, filterItem);
                    multe.setItem(35, filterItem);
                    multe.setItem(36, filterItem);
                    multe.setItem(44, filterItem);
                    multe.setItem(45, filterItem);
                    multe.setItem(46, filterItem);
                    multe.setItem(47, filterItem);
                    multe.setItem(48, filterItem);
                    multe.setItem(49, filterItem);
                    multe.setItem(50, filterItem);
                    multe.setItem(51, filterItem);
                    multe.setItem(52, filterItem);
                    multe.setItem(53, filterItem);

                    // Aggiungi informazioni all'identikit
                    List<Multe> multeList = FdoHelper.getMulteManager().getMultaByPlayerUUID(uuid);
                    for (Multe multeLists : multeList) {
                        ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
                        SkullMeta skullMetas = (SkullMeta) playerHead.getItemMeta();
                        skullMetas.setOwningPlayer(target);
                        skullMetas.setDisplayName(translate("&r&f&l" + target.getName()));
                        List<String> lores = new ArrayList<String>();
                        lores.add(translate("&7Cittadino: &f" + target.getName()));
                        lores.add(translate("&7Motivo: &f" + multeLists.getMotivo()));
                        lores.add(translate("&7Costo: &f" + multeLists.getCosto()));
                        lores.add(translate("&7Data Stampa: &f" + multeLists.getDataCreazione()));
                        lores.add(translate("&7Data Scadenza: &f" + multeLists.getDataScadenza()));
                        lores.add(translate("&7Numero Multa: &f" + multeLists.getNumeroMulta()));
                        skullMetas.setLore(lores);
                        playerHead.setItemMeta(skullMetas);

                        // Aggiungi l'item della testa del giocatore all'inventario identikit
                        multe.setItem(22, playerHead);

                        // Aggiungi l'item per rimuovere l'identikit
                        ItemStack rimuoviMulta = getDeleteMulte(player, target.getName());
                        ItemMeta meta = rimuoviMulta.getItemMeta();
                        PersistentDataContainer dataS = meta.getPersistentDataContainer();
                        dataS.set(new NamespacedKey(FdoHelper.getPlugin(FdoHelper.class), "player"), PersistentDataType.STRING, uuid.toString());
                        dataS.set(new NamespacedKey(FdoHelper.getPlugin(FdoHelper.class), "numero"), PersistentDataType.INTEGER, multeLists.getNumeroMulta());
                        rimuoviMulta.setItemMeta(meta);
                        multe.setItem(39, rimuoviMulta);

                        ItemStack stampa = getMulteRicrea(player);
                        ItemMeta metacon = stampa.getItemMeta();
                        PersistentDataContainer dataCon = metacon.getPersistentDataContainer();
                        dataCon.set(new NamespacedKey(FdoHelper.getPlugin(FdoHelper.class), "cittadinoUUID"), PersistentDataType.STRING, uuid.toString());
                        dataCon.set(new NamespacedKey(FdoHelper.getPlugin(FdoHelper.class), "motivo"), PersistentDataType.STRING, multeLists.getMotivo());
                        dataCon.set(new NamespacedKey(FdoHelper.getPlugin(FdoHelper.class), "costo"), PersistentDataType.DOUBLE, multeLists.getCosto());
                        dataCon.set(new NamespacedKey(FdoHelper.getPlugin(FdoHelper.class), "scadenza"), PersistentDataType.STRING, multeLists.getDataScadenza());
                        stampa.setItemMeta(metacon);
                        multe.setItem(41, stampa);

                        // Chiudi l'inventario del giocatore e apri l'inventario identikit
                        player.closeInventory();
                        player.openInventory(multe);


                    }


                }
            }
        }
    }
}
