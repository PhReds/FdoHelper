package me.phredss.fdohelper.handler.settinggui;

import me.phredss.fdohelper.FdoHelper;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import static me.phredss.fdohelper.utils.Utils.*;

public class AggiornaTema implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (e.getView().getTitle().equalsIgnoreCase("Personalizza Dispositivo")) {
            e.setCancelled(true);
            ItemStack clicked = e.getCurrentItem();
            if (clicked == null) return;
            int slot = e.getRawSlot();

            if (slot == 12 && clicked.getType() == Material.LIGHT_BLUE_STAINED_GLASS_PANE) {
                player.sendMessage(translate(getPrefix() + "&bE' stato impostato il tema BLUE per il tablet."));
                FdoHelper.getAccountManager().changeTema(player.getUniqueId().toString(), player, "azzurro");
            } else if (slot == 14 && clicked.getType() == Material.BLUE_STAINED_GLASS) {
                player.sendMessage(translate(getPrefix() + "&9E' stato impostato il tema BLUE per il tablet."));
                FdoHelper.getAccountManager().changeTema(player.getUniqueId().toString(), player, "blue");
            } else if (slot == 30 && clicked.getType() == Material.RED_STAINED_GLASS_PANE) {
                player.sendMessage(translate(getPrefix() + "&cE' stato impostato il tema ROSSO per il tablet."));
                FdoHelper.getAccountManager().changeTema(player.getUniqueId().toString(), player, "red");
            } else if (slot == 32 && clicked.getType() == Material.LIME_STAINED_GLASS_PANE) {
                player.sendMessage(translate(getPrefix() + "&aE' stato impostato il tema VERDE per il tablet."));
                FdoHelper.getAccountManager().changeTema(player.getUniqueId().toString(), player, "lime");
            }


        }

    }

}
