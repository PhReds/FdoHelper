package me.phredss.fdohelper.handler.tabletmenu;

import me.phredss.fdohelper.FdoHelper;

import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


import java.util.UUID;

import static me.phredss.fdohelper.utils.Utils.*;


public class PlayerClick implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        ItemStack tablet = getItemIA(player, getItemsAdderConfig("tablet-fdo"));
        if (!player.getInventory().getItemInMainHand().equals(tablet)) {
            return;
        }
        if (e.getAction() != Action.RIGHT_CLICK_AIR || e.getAction() != Action.RIGHT_CLICK_BLOCK) {
            if (player.hasPermission(getPermissions("fdo"))) {

                String playerUUID = player.getUniqueId().toString();
                if (FdoHelper.getAccountManager().checkAccountExists(playerUUID)) {
                    if (FdoHelper.getAccountManager().checkImpronta(playerUUID)) {
                        player.openInventory(getTabletMenu(player));
                        return;
                    }

                    ItemStack itemLeft = new ItemStack(Material.BLUE_STAINED_GLASS_PANE, 1);
                    new AnvilGUI.Builder().onComplete((player1, password) -> {
                                UUID player1UUID = player1.getUniqueId();
                                String player1String = player1UUID.toString();
                                String input = password.toLowerCase();
                                String accountPassword = FdoHelper.getAccountManager().getAccountPassword(player1String);
                                if (accountPassword != null && accountPassword.equals(input)) {
                                    //Creazione Gui Menu
                                    player1.sendMessage(translate("&9Accesso eseguito con successo"));
                                    Inventory menu = getTabletMenu(player1);
                                    player1.openInventory(menu);
                                    return AnvilGUI.Response.close();
                                }
                                player1.sendMessage(translate(getPrefix() + getMessagesPasswordErrata("tablet-fdo")));

                                return AnvilGUI.Response.close();
                            })
                            .plugin(FdoHelper.getInstance())
                            .text("Inserisci Password")
                            .title("Account Login")
                            .itemLeft(itemLeft)
                            .open(player);


                } else {
                    //Item Sinistro
                    ItemStack itemLeft = new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE, 1);

                    new AnvilGUI.Builder().onComplete((player1, password) -> {
                                UUID player1UUID = player1.getUniqueId();
                                String input = password.toLowerCase();
                                FdoHelper.getAccountManager().addAccount(player1UUID, input, false, "azzurro");

                                player1.sendMessage(translate("&3[+-------------------+}"));
                                player1.sendMessage(translate("&9Informazioni Account"));
                                player1.sendMessage(translate(" "));
                                player1.sendMessage(translate("&9Username: &3" + player1.getName()));
                                player1.sendMessage(translate("&9Password: &3" + input));
                                player1.sendMessage(translate(" "));
                                player1.sendMessage(translate("&3[+-------------------+}"));
                                return AnvilGUI.Response.close();
                            })
                            .plugin(FdoHelper.getInstance())
                            .text("Inserisci Password")
                            .title("Account Register")
                            .itemLeft(itemLeft)
                            .open(player);
                }


            } else {
                player.sendMessage(translate(getPrefix() + "&cNon hai il permesso per usare il tablet."));
            }
        }
    }
}