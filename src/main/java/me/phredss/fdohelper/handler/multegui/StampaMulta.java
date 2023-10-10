package me.phredss.fdohelper.handler.multegui;

import me.phredss.fdohelper.FdoHelper;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static me.phredss.fdohelper.utils.Utils.*;
public class StampaMulta implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (e.getView().getTitle().equalsIgnoreCase("Conferma Stampa")) {
            e.setCancelled(true);
            ItemStack clicked = e.getCurrentItem();
            int slot = e.getRawSlot();
            if (slot == 41 && clicked.equals(getMulteCancel(player))) {
                player.closeInventory();
                player.sendMessage(translate(getPrefix() + "&cStampa della multa annullata"));
            } else if (slot == 39) {
                ItemMeta meta = clicked.getItemMeta();
                if (meta == null) return;
                PersistentDataContainer data = meta.getPersistentDataContainer();
                String cittadino = data.get(new NamespacedKey(FdoHelper.getPlugin(FdoHelper.class), "cittadinoUUID"), PersistentDataType.STRING);
                String motivo = data.get(new NamespacedKey(FdoHelper.getPlugin(FdoHelper.class), "motivo"), PersistentDataType.STRING);
                Double costo = data.get(new NamespacedKey(FdoHelper.getPlugin(FdoHelper.class), "costo"), PersistentDataType.DOUBLE);
                String dataScadenza = data.get(new NamespacedKey(FdoHelper.getPlugin(FdoHelper.class), "scadenza"), PersistentDataType.STRING);

                ItemStack multa = new ItemStack(Material.PAPER, 1);
                Player target = Bukkit.getPlayer(UUID.fromString(cittadino));
                ItemMeta metamul = multa.getItemMeta();
                if (metamul == null) return;
                metamul.setDisplayName(translate("&cMulta di &l" + target.getName()));
                metamul.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                List<String> lores = new ArrayList<String>();
                lores.add(translate("&7Cittadino: &r&f" + target.getName()));
                lores.add(translate("&7Motivo: &r&f" + motivo));
                lores.add(translate("&7Costo: &r&f" + costo));
                lores.add(translate("&7Data Stampa: &r&f" + getDataEntrata()));
                lores.add(translate("&7Data Scadenza: &r&f" + dataScadenza));
                metamul.setLore(lores);
                multa.setItemMeta(metamul);
                player.closeInventory();

                player.sendMessage(translate(getPrefix() + "&aStai stampando una multa per &l" + target.getName()));
                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(FdoHelper.getInstance(), new Runnable() {
                    @Override
                    public void run() {

                        FdoHelper.getMulteManager().addMulta(UUID.fromString(cittadino), target.getName(), motivo, costo, dataScadenza);
                        player.sendMessage(translate(getPrefix() + "&aMulta per &l" + target.getName() + "&r&a stampata con successo."));
                        player.getInventory().addItem(multa);
                    }
                }, 20 * 3);


            }


        } else if (e.getView().getTitle().startsWith("Multa di")) {
            e.setCancelled(true);
            ItemStack clicked = e.getCurrentItem();
            int slot = e.getRawSlot();
            if (slot == 41) {
                ItemMeta meta = clicked.getItemMeta();
                if (meta == null) return;
                PersistentDataContainer data = meta.getPersistentDataContainer();
                String cittadino = data.get(new NamespacedKey(FdoHelper.getPlugin(FdoHelper.class), "cittadinoUUID"), PersistentDataType.STRING);
                String motivo = data.get(new NamespacedKey(FdoHelper.getPlugin(FdoHelper.class), "motivo"), PersistentDataType.STRING);
                Double costo = data.get(new NamespacedKey(FdoHelper.getPlugin(FdoHelper.class), "costo"), PersistentDataType.DOUBLE);
                String dataScadenza = data.get(new NamespacedKey(FdoHelper.getPlugin(FdoHelper.class), "scadenza"), PersistentDataType.STRING);

                ItemStack multa = new ItemStack(Material.PAPER, 1);
                OfflinePlayer target = Bukkit.getOfflinePlayer(UUID.fromString(cittadino));
                ItemMeta metamul = multa.getItemMeta();
                if (metamul == null) return;
                metamul.setDisplayName(translate("&cMulta di &l" + target.getName()));
                metamul.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                List<String> lores = new ArrayList<String>();
                lores.add(translate("&7Cittadino: &r&f" + target.getName()));
                lores.add(translate("&7Motivo: &r&f" + motivo));
                lores.add(translate("&7Costo: &r&f" + costo));
                lores.add(translate("&7Data Stampa: &r&f" + getDataEntrata()));
                lores.add(translate("&7Data Scadenza: &r&f" + dataScadenza));
                metamul.setLore(lores);
                multa.setItemMeta(metamul);
                player.closeInventory();

                player.sendMessage(translate(getPrefix() + "&aStai stampando una copia della multa per &l" + target.getName()));
                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(FdoHelper.getInstance(), new Runnable() {
                    @Override
                    public void run() {

                        player.sendMessage(translate(getPrefix() + "&aCopia della multa per &l" + target.getName() + "&r&a stampata con successo."));
                        player.getInventory().addItem(multa);
                    }
                }, 20 * 3);
            }


        }
    }
}
