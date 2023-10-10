package me.phredss.fdohelper.handler.tabletmenu;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static me.phredss.fdohelper.utils.Utils.*;

public class RicercaGui implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (!e.getView().getTitle().equalsIgnoreCase("Menu Fdo")) {
            return;
        }
        e.setCancelled(true);
        int slot = e.getRawSlot();
        ItemStack clicked = e.getCurrentItem();
        if (slot == 29 && clicked.equals(getRicerca(player))) {
            Inventory dbmenu = Bukkit.createInventory(null, 45, "Database Fdo");

            //Filter
            ItemStack filter = getFilter(player);

            //Item Resetta Password
            ItemStack multe = getRicercaMulte(player);
            ItemStack identikit = getRicercaIdentikit(player);


            dbmenu.setItem(0, filter);
            dbmenu.setItem(1, filter);
            dbmenu.setItem(2, filter);
            dbmenu.setItem(3, filter);
            dbmenu.setItem(4, filter);
            dbmenu.setItem(5, filter);
            dbmenu.setItem(6, filter);
            dbmenu.setItem(7, filter);
            dbmenu.setItem(8, filter);
            dbmenu.setItem(9, filter);
            dbmenu.setItem(17, filter);
            dbmenu.setItem(18, filter);
            dbmenu.setItem(26, filter);
            dbmenu.setItem(27, filter);
            dbmenu.setItem(35, filter);
            dbmenu.setItem(36, filter);
            dbmenu.setItem(37, filter);
            dbmenu.setItem(38, filter);
            dbmenu.setItem(39, filter);
            dbmenu.setItem(40, filter);
            dbmenu.setItem(41, filter);
            dbmenu.setItem(42, filter);
            dbmenu.setItem(43, filter);
            dbmenu.setItem(44, filter);


            dbmenu.setItem(21, multe);
            dbmenu.setItem(23, identikit);

            player.closeInventory();
            player.openInventory(dbmenu);
        }

    }
}

