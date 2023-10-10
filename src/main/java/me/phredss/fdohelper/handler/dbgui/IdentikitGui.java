package me.phredss.fdohelper.handler.dbgui;

import me.phredss.fdohelper.FdoHelper;
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
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;
import static me.phredss.fdohelper.utils.Utils.*;

public class IdentikitGui implements Listener {


    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (!e.getView().getTitle().equalsIgnoreCase("Database Fdo")) {
            return;
        }
        e.setCancelled(true);

        int slot = e.getRawSlot();
        ItemStack clicked = e.getCurrentItem();

        if (slot == 23 && clicked.equals(getRicercaIdentikit(player))) {
            List<String> playerUUIDs = FdoHelper.getIdentikitManager().getPlayerHeads();
            Inventory identikitmenu = Bukkit.createInventory(null, 54, "Database Identikit");

            // Crea un set per tenere traccia degli slot utilizzati
            Set<Integer> usedSlots = new HashSet<>(Arrays.asList(0, 1, 9, 10, 18, 19, 27, 28, 36, 37, 45, 46, 2, 11, 20, 29, 38, 47));

            // Aggiungi gli item di filtro
            ItemStack filter = getFilter(player);

            //Item RicercaIdentikit
            ItemStack cerca = getRicercaIdentikitSpecifico(player);


            for (int i = 1; i <= 46; i += 9) {
                identikitmenu.setItem(i, filter);
            }

            int firstUsableSlot = 12;

            for (String playerUUID : playerUUIDs) {
                UUID uuid = UUID.fromString(playerUUID);
                int slotIndex = findEmptySlot(identikitmenu, usedSlots, firstUsableSlot);

                // Se è stato trovato uno slot libero, inserisci la testa del giocatore
                if (slotIndex >= 0) {
                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);

                    ItemStack playerHead = createPlayerHead(offlinePlayer);
                    if (playerHead != null) {
                        identikitmenu.setItem(slotIndex, playerHead);
                        usedSlots.add(slotIndex); // Aggiungi lo slot utilizzato al set
                    }
                } else {
                    // Se non ci sono slot liberi, esci dal ciclo
                    break;
                }
            }

            identikitmenu.setItem(9, cerca);


            player.closeInventory();
            player.openInventory(identikitmenu);
        }
    }

    private ItemStack createPlayerHead(OfflinePlayer player) {
        ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) playerHead.getItemMeta();

        if (skullMeta == null) {
            return null;
        }

        skullMeta.setOwningPlayer(player);
        skullMeta.setDisplayName(translate("&r&f" + player.getName()));
        List<String> lores = new ArrayList<>();
        lores.add(translate("&7Clicca qui per"));
        lores.add(translate("&7Aprire l'Identikit."));
        PersistentDataContainer data = skullMeta.getPersistentDataContainer();
        data.set(new NamespacedKey(FdoHelper.getPlugin(FdoHelper.class), "player_uuid"), PersistentDataType.STRING, player.getUniqueId().toString());
        skullMeta.setLore(lores);

        playerHead.setItemMeta(skullMeta);
        return playerHead;
    }

    private int findEmptySlot(Inventory inventory, Set<Integer> usedSlots, int startSlot) {
        // Trova uno slot libero che non è nell'elenco degli slot utilizzati, che non è vuoto e che inizia da startSlot
        for (int slotIndex = startSlot; slotIndex < inventory.getSize(); slotIndex++) {
            if (!usedSlots.contains(slotIndex) && inventory.getItem(slotIndex) == null) {
                return slotIndex;
            }
        }
        return -1; // Nessuno slot libero trovato
    }
}
