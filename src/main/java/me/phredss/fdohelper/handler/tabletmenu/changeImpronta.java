package me.phredss.fdohelper.handler.tabletmenu;

import me.phredss.fdohelper.FdoHelper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static me.phredss.fdohelper.utils.Utils.*;

public class changeImpronta implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (!e.getView().getTitle().equalsIgnoreCase("Menu Fdo")) {
            return;
        }
        e.setCancelled(true);
        int slot = e.getRawSlot();
        String playerUUID = player.getUniqueId().toString();
        ItemStack clicked = e.getCurrentItem();
        Inventory menu = getTabletMenu(player);
        if (slot == 30 && clicked.equals(getImprontaOff(player))) {
            FdoHelper.getAccountManager().activateImpronta(playerUUID);
            menu.setItem(30, getImprontaOn(player));
            player.openInventory(menu);
            player.sendMessage(translate(getPrefix() + "&aImpronta Digitale attivata"));
        } else if (slot == 30 && clicked.equals(getImprontaOn(player))) {
            FdoHelper.getAccountManager().disactivateImpronta(playerUUID);
            menu.setItem(30, getImprontaOff(player));
            player.openInventory(menu);
            player.sendMessage(translate(getPrefix() + "&cImpronta Digitale disattivata"));
        }
    }
}
