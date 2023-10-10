package me.phredss.fdohelper.handler.tabletmenu;

import me.phredss.fdohelper.FdoHelper;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

import static me.phredss.fdohelper.utils.Utils.*;

public class ScarceraPlayer implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (e.getView().getTitle().equalsIgnoreCase("Menu Fdo")) {
            ItemStack clicked = e.getCurrentItem();
            int slot = e.getRawSlot();
            if (slot == 21 && clicked.equals(getScarcera(player))) {
                player.closeInventory();
                new AnvilGUI.Builder().onComplete((player1, cittadino) -> {
                            UUID player1UUID = player1.getUniqueId();
                            String input = cittadino.toLowerCase();
                            Player target = Bukkit.getPlayer(cittadino);
                            if (target == null) {
                                player1.sendMessage(translate(getPrefix() + "&cIl cittadino non è online."));
                                return AnvilGUI.Response.close();
                            }
                            if (!target.isOnline()) {
                                player1.sendMessage(translate(getPrefix() + "&cIl cittadino non è online."));
                                return AnvilGUI.Response.close();
                            }
                            if (!FdoHelper.getIdentikitManager().checkIdentikitExists(target.getUniqueId().toString())) {
                                player1.sendMessage(translate(getPrefix() + "&cIl cittadino non è in stato di arresto."));
                                return AnvilGUI.Response.close();
                            }


                            player1.sendMessage(translate(getPrefix() + "&9Stai scarcerando &l" + target.getName() + "&r&9..."));
                            target.sendMessage(translate(getPrefix() + "&9Ti stanno scarcercando..."));
                            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(FdoHelper.getInstance(), new Runnable() {
                                @Override
                                public void run() {
                                    target.sendMessage(translate(getPrefix() + "&9Sei stato scarcerato da &l" + player1.getName() + "&r&9."));
                                    player1.sendMessage(translate(getPrefix() + "&9Hai scarcerato &l" + target.getName() + "&r&9."));
                                    FdoHelper.getIdentikitManager().removeIdentikit(target.getUniqueId());


                                }
                            }, 20 * 3);

                            return AnvilGUI.Response.close();
                        })
                        .plugin(FdoHelper.getInstance())
                        .text("§r")
                        .title("Scarcera Cittadino")
                        .itemLeft(getItemLeft())
                        .open(player);
            }


        }
    }
}
