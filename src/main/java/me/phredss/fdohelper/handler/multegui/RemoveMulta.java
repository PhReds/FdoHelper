package me.phredss.fdohelper.handler.multegui;

import me.phredss.fdohelper.FdoHelper;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

import static me.phredss.fdohelper.utils.Utils.getPrefix;
import static me.phredss.fdohelper.utils.Utils.translate;

public class RemoveMulta implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (!e.getView().getTitle().startsWith("Multa di")) {
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
        Integer numeroMulta = data.get(new NamespacedKey(FdoHelper.getPlugin(FdoHelper.class), "numero"), PersistentDataType.INTEGER);
        if (slot == 39) {
            if (target == null) return;
            UUID uuid = UUID.fromString(target);
            OfflinePlayer offlinePlayer = (OfflinePlayer) Bukkit.getOfflinePlayer(uuid);
            FdoHelper.getMulteManager().removeMulta(uuid, numeroMulta);
            player.sendMessage(translate(getPrefix() + "&cMulta di &l" + offlinePlayer.getName() + " &r&cnumero &l" + numeroMulta + "&r&c rimossa con successo"));
            player.closeInventory();
        }

    }
}

