package me.phredss.fdohelper.handler.settinggui;

import me.phredss.fdohelper.FdoHelper;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static me.phredss.fdohelper.utils.Utils.*;
import static me.phredss.fdohelper.utils.Utils.translate;

public class ResetPasswordClick implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (!e.getView().getTitle().equalsIgnoreCase("Impostazioni Dispositivo")) {
            return;
        }
        e.setCancelled(true);
        int slot = e.getRawSlot();
        ItemStack clicked = e.getCurrentItem();
        if (slot == 21 && clicked.equals(getResetPassword(player))) {

            //Item Sinistro
            ItemStack itemLeft = new ItemStack(Material.RED_STAINED_GLASS_PANE, 1);
            player.closeInventory();
            new AnvilGUI.Builder().onComplete((player1, password) -> {
                        UUID player1UUID = player1.getUniqueId();
                        String input = password.toLowerCase();
                        FdoHelper.getAccountManager().resetPassword(player1UUID.toString(), input);
                        player1.sendMessage(translate("&aPassword Aggiornata: &f" + input));
                        return AnvilGUI.Response.close();
                    })
                    .plugin(FdoHelper.getInstance())
                    .text("Nuova Password")
                    .title("Password Reset")
                    .itemLeft(itemLeft)
                    .open(player);
        }

    }

}
