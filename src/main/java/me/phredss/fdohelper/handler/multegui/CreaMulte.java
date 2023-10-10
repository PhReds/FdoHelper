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
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;


import javax.naming.Name;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static me.phredss.fdohelper.utils.Utils.*;

public class CreaMulte implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();

        if (!e.getView().getTitle().equalsIgnoreCase("Database Multe")) {
            return;
        }
        ItemStack clicked = e.getCurrentItem();
        if (clicked == null) return;
        int slot = e.getRawSlot();
        if (slot == 36 && clicked.equals(getCreaMulte(player))) {
            player.closeInventory();

            new AnvilGUI.Builder().onComplete((player1, cittadino) -> {
                        UUID player1UUID = player1.getUniqueId();
                        Player target = Bukkit.getPlayer(cittadino.toLowerCase());
                        if (target == null) {
                            player1.sendMessage(translate(getPrefix() + "&cIl cittadino non è online"));
                            return AnvilGUI.Response.close();
                        }
                        UUID targetUUID = target.getUniqueId();

                        if (targetUUID.equals(player1UUID)) {
                            player1.sendMessage(translate(getPrefix() + "&cNon puoi multarti da solo!"));
                            return AnvilGUI.Response.close();
                        }
                        if (!target.isOnline()) {
                            player1.sendMessage(translate(getPrefix() + "&cIl Cittadino non è online"));
                            return AnvilGUI.Response.close();
                        }
                        if (player1.getLocation().distance(target.getLocation()) >= 10) {
                            player1.sendMessage(translate(getPrefix() + "&cIl cittadino da multare deve essere entro 10 blocchi da te."));
                            return AnvilGUI.Response.close();
                        }

                        new AnvilGUI.Builder().onComplete((player2, motivo) -> {
                                    String inputMotivo = motivo.toLowerCase();

                                    new AnvilGUI.Builder().onComplete((player3, costo) -> {
                                                String inputCosto = costo.toLowerCase();
                                                if (!isDouble(inputCosto)) {
                                                    player2.sendMessage(translate(getPrefix() + "&cIl costo deve essere un numero!"));
                                                    return AnvilGUI.Response.close();
                                                }

                                                new AnvilGUI.Builder().onComplete((player4, scadenza) -> {
                                                            String inputScadenza = scadenza.toLowerCase();
                                                            String dataScadenza = inputScadenza + " " + getOreEntrata();
                                                            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(targetUUID);

                                                            Inventory confermaMulta = Bukkit.createInventory(null, 45, "Conferma Stampa");


                                                            //Items
                                                            ItemStack conferma = getMulteConfirm(player4);
                                                            ItemMeta metacon = conferma.getItemMeta();
                                                            PersistentDataContainer dataCon = metacon.getPersistentDataContainer();
                                                            dataCon.set(new NamespacedKey(FdoHelper.getPlugin(FdoHelper.class), "cittadinoUUID"), PersistentDataType.STRING, targetUUID.toString());
                                                            dataCon.set(new NamespacedKey(FdoHelper.getPlugin(FdoHelper.class), "motivo"), PersistentDataType.STRING, inputMotivo);
                                                            dataCon.set(new NamespacedKey(FdoHelper.getPlugin(FdoHelper.class), "costo"), PersistentDataType.DOUBLE, Double.parseDouble(inputCosto));
                                                            dataCon.set(new NamespacedKey(FdoHelper.getPlugin(FdoHelper.class), "scadenza"), PersistentDataType.STRING, dataScadenza);
                                                            conferma.setItemMeta(metacon);


                                                            ItemStack annulla = getMulteCancel(player4);

                                                            //Item Multa
                                                            ItemStack confermaHead = new ItemStack(Material.PLAYER_HEAD, 1);
                                                            SkullMeta skullMeta = (SkullMeta) confermaHead.getItemMeta();
                                                            skullMeta.setOwningPlayer(offlinePlayer);
                                                            skullMeta.setDisplayName(translate("&r&f" + offlinePlayer.getName()));
                                                            List<String> lores = new ArrayList<String>();
                                                            lores.add(translate("&7Cittadino: &r&f" + offlinePlayer.getName()));
                                                            lores.add(translate("&7Motivo: &r&f" + inputMotivo));
                                                            lores.add(translate("&7Costo: &r&f" + inputCosto));
                                                            lores.add(translate("&7Data Stampa: &r&f" + getDataEntrata()));
                                                            lores.add(translate("&7Data Scadenza: &r&f" + dataScadenza));
                                                            skullMeta.setLore(lores);
                                                            confermaHead.setItemMeta(skullMeta);

                                                            player4.closeInventory();

                                                            int[] slotIndices = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44};
                                                            for (int i : slotIndices) {
                                                                confermaMulta.setItem(i, getFilter(player));
                                                            }

                                                            confermaMulta.setItem(22, confermaHead);
                                                            confermaMulta.setItem(39, conferma);
                                                            confermaMulta.setItem(41, annulla);
                                                            player4.openInventory(confermaMulta);



                                                            return AnvilGUI.Response.close();
                                                        })
                                                        .plugin(FdoHelper.getInstance())
                                                        .text("§r")
                                                        .title("Data Scadenza")
                                                        .itemLeft(getItemLeft())
                                                        .open(player);


                                                return AnvilGUI.Response.close();
                                            })
                                            .plugin(FdoHelper.getInstance())
                                            .text("§r")
                                            .title("Costo")
                                            .itemLeft(getItemLeft())
                                            .open(player);

                                    return AnvilGUI.Response.close();
                                })
                                .plugin(FdoHelper.getInstance())
                                .text("§r")
                                .title("Motivo")
                                .itemLeft(getItemLeft())
                                .open(player);

                        return AnvilGUI.Response.close();
                    })
                    .plugin(FdoHelper.getInstance())
                    .text("§r")
                    .title("Cittadino")
                    .itemLeft(getItemLeft())
                    .open(player);
        }


    }

}
