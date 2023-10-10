package me.phredss.fdohelper.handler.dbgui;

import me.phredss.fdohelper.FdoHelper;
import me.phredss.fdohelper.sqlite.Identikit;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
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

public class IdentikitCerca implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();

        if (!e.getView().getTitle().equalsIgnoreCase("Database Identikit")) {
            return;
        }

        e.setCancelled(true);

        int slot = e.getRawSlot();
        ItemStack item = e.getCurrentItem();

        if (item == null) {
            return;
        }

        if (slot == 9 && item.equals(getRicercaIdentikitSpecifico(player))) {
            // Check if it isn't a left-click event
            player.closeInventory();
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(FdoHelper.getInstance(), () -> {
                new AnvilGUI.Builder()
                        .onComplete((player1, password) -> {
                            String input = password.toLowerCase();
                            UUID uuid = null;

                            // Cerca il giocatore offline con il nome inserito
                            for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
                                if (offlinePlayer.getName() != null && offlinePlayer.getName().equalsIgnoreCase(input)) {
                                    uuid = offlinePlayer.getUniqueId();
                                    break;
                                }
                            }

                            if (uuid == null) {
                                player1.sendMessage(translate(getPrefix() + "&cNessun giocatore trovato con il nome &l" + input));
                                return AnvilGUI.Response.close();
                            }

                            List<Identikit> identikitList = FdoHelper.getIdentikitManager().getIdentikitByPlayerUUID(uuid);
                            if (identikitList.isEmpty()) {
                                player1.sendMessage(translate(getPrefix() + "&cNessun identikit trovato per &l" + Bukkit.getOfflinePlayer(uuid).getName()));
                                return AnvilGUI.Response.close();
                            }

                            // Crea un inventario per l'identikit
                            Inventory identikit = Bukkit.createInventory(null, 54, "Identikit di " + Bukkit.getOfflinePlayer(uuid).getName());

                            // Imposta l'item di filtro
                            ItemStack filterItem = getFilter(player);
                            for (int i = 0; i < 54; i++) {
                                identikit.setItem(i, filterItem);
                            }

                            // Aggiungi informazioni all'identikit
                            for (Identikit identikitLists : identikitList) {
                                ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
                                SkullMeta skullMetas = (SkullMeta) playerHead.getItemMeta();
                                skullMetas.setOwningPlayer(Bukkit.getOfflinePlayer(uuid));
                                skullMetas.setDisplayName(translate("&r&f&l" + Bukkit.getOfflinePlayer(uuid).getName()));
                                List<String> lores = new ArrayList<>();
                                lores.add(translate("&7Cittadino: &f" + Bukkit.getOfflinePlayer(uuid).getName()));
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
                                ItemStack rimuoviIdentikit = getDeleteIdentikit(player, Bukkit.getOfflinePlayer(uuid).getName());
                                ItemMeta meta = rimuoviIdentikit.getItemMeta();
                                PersistentDataContainer dataS = meta.getPersistentDataContainer();
                                dataS.set(new NamespacedKey(FdoHelper.getPlugin(FdoHelper.class), "player"), PersistentDataType.STRING, uuid.toString());
                                rimuoviIdentikit.setItemMeta(meta);
                                identikit.setItem(40, rimuoviIdentikit);

                                // Chiudi l'inventario del giocatore e apri l'inventario identikit
                                player.closeInventory();
                                player.openInventory(identikit);
                            }

                            return AnvilGUI.Response.close();
                        })
                        .plugin(FdoHelper.getInstance())
                        .text("§r")
                        .title("Cerca Identikit")
                        .itemLeft(getItemLeft())  // Usa l'oggetto copiato qui
                        .open(player);
            }, 2);
        }
    }
}
