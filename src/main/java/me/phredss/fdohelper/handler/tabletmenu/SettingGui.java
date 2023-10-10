package me.phredss.fdohelper.handler.tabletmenu;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


import static me.phredss.fdohelper.utils.Utils.*;

public class SettingGui implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (!e.getView().getTitle().equalsIgnoreCase("Menu Fdo")) {
            return;
        }
        e.setCancelled(true);
        int slot = e.getRawSlot();
        ItemStack clicked = e.getCurrentItem();
        if (slot == 33 && clicked.equals(getSettings(player))) {
            Inventory setting = Bukkit.createInventory(null, 45, "Impostazioni Dispositivo");

            //Filter
            ItemStack filter = getFilter(player);

            //Item Resetta Password
            ItemStack passreset = getResetPassword(player);
            ItemStack tema = getTema(player);


            setting.setItem(0, filter);
            setting.setItem(1, filter);
            setting.setItem(2, filter);
            setting.setItem(3, filter);
            setting.setItem(4, filter);
            setting.setItem(5, filter);
            setting.setItem(6, filter);
            setting.setItem(7, filter);
            setting.setItem(8, filter);
            setting.setItem(9, filter);
            setting.setItem(17, filter);
            setting.setItem(18, filter);
            setting.setItem(26, filter);
            setting.setItem(27, filter);
            setting.setItem(35, filter);
            setting.setItem(36, filter);
            setting.setItem(37, filter);
            setting.setItem(38, filter);
            setting.setItem(39, filter);
            setting.setItem(40, filter);
            setting.setItem(41, filter);
            setting.setItem(42, filter);
            setting.setItem(43, filter);
            setting.setItem(44, filter);

            setting.setItem(23, tema);
            setting.setItem(21, passreset);
            player.closeInventory();
            player.openInventory(setting);
        }

    }
}
