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

import static me.phredss.fdohelper.utils.Utils.*;


public class TemaClick implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (!e.getView().getTitle().equalsIgnoreCase("Impostazioni Dispositivo")) {
            return;
        }
        e.setCancelled(true);
        int slot = e.getRawSlot();
        ItemStack clicked = e.getCurrentItem();
        if (slot == 23 && clicked.equals(getTema(player))) {

            Inventory tema = Bukkit.createInventory(null, 45, "Personalizza Dispositivo");

            ItemStack red = new ItemStack(Material.RED_STAINED_GLASS_PANE, 1);
            ItemMeta mred = red.getItemMeta();
            mred.setDisplayName(translate("&cTema Rosso"));
            List<String> lred = new ArrayList<String>();
            lred.add(translate("&7Clicca qui per impostare il"));
            lred.add(translate("&7Tema &cRosso&7 nel dispositivo."));
            mred.setLore(lred);
            mred.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            red.setItemMeta(mred);


            ItemStack blue = new ItemStack(Material.BLUE_STAINED_GLASS_PANE, 1);
            ItemMeta mblue = blue.getItemMeta();
            mblue.setDisplayName(translate("&9Tema Blue"));
            List<String> lblue = new ArrayList<String>();
            lblue.add(translate("&7Clicca qui per impostare il"));
            lblue.add(translate("&7Tema &9Blue&7 nel dispositivo."));
            mblue.setLore(lblue);
            mblue.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            blue.setItemMeta(mblue);


            ItemStack lime = new ItemStack(Material.LIME_STAINED_GLASS_PANE, 1);
            ItemMeta mlime = lime.getItemMeta();
            mlime.setDisplayName(translate("&aTema Verde"));
            List<String> llime = new ArrayList<String>();
            llime.add(translate("&7Clicca qui per impostare il"));
            llime.add(translate("&7Tema &aVerde&7 nel dispositivo."));
            mlime.setLore(llime);
            mlime.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            lime.setItemMeta(mlime);


            ItemStack light_blue = new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE, 1);
            ItemMeta mlight = light_blue.getItemMeta();
            mlight.setDisplayName(translate("&bTema Azzurro"));
            List<String> llight = new ArrayList<String>();
            llight.add(translate("&7Clicca qui per impostare il"));
            llight.add(translate("&7Tema &bAzzurro&7 nel dispositivo."));
            mlight.setLore(llight);
            mlight.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            light_blue.setItemMeta(mlight);


            ItemStack filterfilterone = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
            ItemMeta meta = filterfilterone.getItemMeta();
            meta.setDisplayName("§r");
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            filterfilterone.setItemMeta(meta);


            tema.setItem(0, filterfilterone);
            tema.setItem(1, filterfilterone);
            tema.setItem(2, filterfilterone);
            tema.setItem(3, filterfilterone);
            tema.setItem(4, filterfilterone);
            tema.setItem(5, filterfilterone);
            tema.setItem(6, filterfilterone);
            tema.setItem(7, filterfilterone);
            tema.setItem(8, filterfilterone);
            tema.setItem(9, filterfilterone);
            tema.setItem(17, filterfilterone);
            tema.setItem(18, filterfilterone);
            tema.setItem(26, filterfilterone);
            tema.setItem(27, filterfilterone);
            tema.setItem(35, filterfilterone);
            tema.setItem(36, filterfilterone);
            tema.setItem(37, filterfilterone);
            tema.setItem(38, filterfilterone);
            tema.setItem(39, filterfilterone);
            tema.setItem(40, filterfilterone);
            tema.setItem(41, filterfilterone);
            tema.setItem(42, filterfilterone);
            tema.setItem(43, filterfilterone);
            tema.setItem(44, filterfilterone);

            tema.setItem(12, light_blue);
            tema.setItem(14, blue);
            tema.setItem(30, red);
            tema.setItem(32, lime);

            player.closeInventory();
            player.openInventory(tema);

        }

    }

}
