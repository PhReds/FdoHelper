package me.phredss.fdohelper.handler.multegui;

import me.phredss.fdohelper.FdoHelper;
import me.phredss.fdohelper.sqlite.Multe;
import net.wesjd.anvilgui.AnvilGUI;
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

public class MulteCerca implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();

        if (!e.getView().getTitle().equalsIgnoreCase("Database Multe")) {
            return;
        }

        e.setCancelled(true);

        int slot = e.getRawSlot();
        ItemStack item = e.getCurrentItem();

        if (item == null) {
            return;
        }

        if (slot == 9 && item.equals(getRicercaMulteSpecifico(player))) {
            player.closeInventory();
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(FdoHelper.getInstance(), () -> {
                new AnvilGUI.Builder()
                        .onComplete((player1, password) -> {
                            String input = password.toLowerCase();
                            List<String> playerUUIDs = FdoHelper.getMulteManager().getPlayerMulte();
                            UUID foundUUID = null;

                            for (String uuid : playerUUIDs) {
                                UUID playerUUID = UUID.fromString(uuid);
                                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerUUID);
                                if (offlinePlayer.getName() != null && offlinePlayer.getName().toLowerCase().contains(input)) {
                                    foundUUID = playerUUID;
                                    break;
                                }
                            }

                            if (foundUUID == null) {
                                player1.sendMessage("§cNessuna Multa trovata per §l" + input);
                                return AnvilGUI.Response.close();
                            }

                            // Apri l'inventario delle multe per il giocatore trovato
                            List<Multe> multeList = FdoHelper.getMulteManager().getMultaByPlayerUUID(foundUUID);
                            Inventory multeInventory = Bukkit.createInventory(null, 54, "Multe di " + Bukkit.getOfflinePlayer(foundUUID).getName());

                            multeInventory.setItem(0, getFilter(player));
                            multeInventory.setItem(1, getFilter(player));
                            multeInventory.setItem(2, getFilter(player));
                            multeInventory.setItem(3, getFilter(player));
                            multeInventory.setItem(4, getFilter(player));
                            multeInventory.setItem(5, getFilter(player));
                            multeInventory.setItem(6, getFilter(player));
                            multeInventory.setItem(7, getFilter(player));
                            multeInventory.setItem(8, getFilter(player));
                            multeInventory.setItem(9, getFilter(player));
                            multeInventory.setItem(17, getFilter(player));
                            multeInventory.setItem(18, getFilter(player));
                            multeInventory.setItem(26, getFilter(player));
                            multeInventory.setItem(27, getFilter(player));
                            multeInventory.setItem(35, getFilter(player));
                            multeInventory.setItem(36, getFilter(player));
                            multeInventory.setItem(44, getFilter(player));
                            multeInventory.setItem(45, getFilter(player));
                            multeInventory.setItem(46, getFilter(player));
                            multeInventory.setItem(47, getFilter(player));
                            multeInventory.setItem(48, getFilter(player));
                            multeInventory.setItem(49, getFilter(player));
                            multeInventory.setItem(50, getFilter(player));
                            multeInventory.setItem(51, getFilter(player));
                            multeInventory.setItem(52, getFilter(player));
                            multeInventory.setItem(53, getFilter(player));

                            // Aggiungi le multe all'inventario
                            OfflinePlayer target = Bukkit.getOfflinePlayer(foundUUID);
                            ItemStack multaItem = createPlayerHead(target);

                            // Crea un set per tenere traccia degli slot utilizzati
                            Set<Integer> usedSlots = new HashSet<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53));

                            int firstUsableSlot = 10;

                            for (Multe multa : multeList) {
                                int slotIndex = findEmptySlot(multeInventory, usedSlots, firstUsableSlot);

                                // Se è stato trovato uno slot libero, inserisci la testa del giocatore
                                if (slotIndex >= 0) {
                                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(foundUUID);

                                    ItemStack playerHead = createPlayerHead(offlinePlayer);
                                    if (playerHead != null) {
                                        multeInventory.setItem(slotIndex, multaItem); // Usa slotIndex invece di aggiungere l'elemento
                                        usedSlots.add(slotIndex); // Aggiungi lo slot utilizzato al set
                                    }
                                } else {
                                    // Se non ci sono slot liberi, esci dal ciclo
                                    break;
                                }
                            }

                            player1.openInventory(multeInventory);
                            return AnvilGUI.Response.close();
                        })
                        .plugin(FdoHelper.getInstance())
                        .text("§r")
                        .title("Cerca Multa")
                        .itemLeft(getItemLeft())
                        .open(player);
            }, 2);
        }
    }

    private ItemStack createPlayerHead(OfflinePlayer player) {
        ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) playerHead.getItemMeta();

        if (skullMeta == null) {
            return null;
        }

        skullMeta.setOwningPlayer(player);
        skullMeta.setDisplayName("§f" + player.getName());
        List<String> lores = new ArrayList<>();
        lores.add("§7Clicca qui per");
        lores.add("§7Aprire la multa.");
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
