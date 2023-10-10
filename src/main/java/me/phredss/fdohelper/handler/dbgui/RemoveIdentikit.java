package me.phredss.fdohelper.handler.dbgui;

import me.phredss.fdohelper.FdoHelper;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

import static me.phredss.fdohelper.utils.Utils.*;

public class RemoveIdentikit implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (!e.getView().getTitle().startsWith("Identikit di")) {
            return;
        }
        e.setCancelled(true);
        int slot = e.getRawSlot();
        ItemStack clicked = e.getCurrentItem();
        if (clicked == null) return;
        if (!clicked.hasItemMeta()) return;
        ItemMeta meta = clicked.getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();
        String target = data.get(new NamespacedKey(FdoHelper.getPlugin(FdoHelper.class), "player"), PersistentDataType.STRING);
        System.out.println("Pier: " + target);
        if (slot == 40) {
            if (target == null) return;
            UUID uuid = UUID.fromString(target);
            OfflinePlayer offlinePlayer = (OfflinePlayer) Bukkit.getOfflinePlayer(uuid);
            FdoHelper.getIdentikitManager().removeIdentikit(uuid);
            player.sendMessage(translate(getPrefix() + "&cIdentikit di &l" + offlinePlayer.getName() + "&r&c rimosso con successo"));
            player.closeInventory();
        }

    }
}
