package me.phredss.fdohelper.handler.tabletmenu;

import me.phredss.fdohelper.FdoHelper;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.UUID;

import static me.phredss.fdohelper.utils.Utils.*;

public class ArrestaPlayer implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (!e.getView().getTitle().equalsIgnoreCase("Menu Fdo")) {
            return;
        }
        e.setCancelled(true);
        int slot = e.getRawSlot();
        ItemStack clicked = e.getCurrentItem();
        if (slot == 20 && clicked.equals(getArresta(player))) {
            //Item Sinistro
            ItemStack itemLeft = getItemLeft();
            player.closeInventory();
            new AnvilGUI.Builder().onComplete((player1, cittadino) -> {
                        String fdoId = player1.getUniqueId().toString();
                        String inputCittadino = cittadino;
                        Player cittadinoPlayer = Bukkit.getPlayer(inputCittadino);
                        if (cittadinoPlayer == null) {
                            player1.sendMessage(translate(getPrefix() + "&cIl cittadino non è online!"));
                            player1.closeInventory();
                            return AnvilGUI.Response.close();
                        }
                        String cittadinoId = cittadinoPlayer.getUniqueId().toString();
                        UUID cittadinoUUID = cittadinoPlayer.getUniqueId();

                        if (fdoId.equals(cittadinoId)) {
                            player1.sendMessage(translate(getPrefix() + "&cNon puoi arrestarti da solo!"));
                            return AnvilGUI.Response.close();
                        }
                        if (!cittadinoPlayer.isOnline()) {
                            player1.sendMessage(translate(getPrefix() + "&cIl cittadino non è online!"));
                            return AnvilGUI.Response.close();
                        }
                        if (player1.getLocation().distance(cittadinoPlayer.getLocation()) >= 10) {
                            player1.sendMessage(translate(getPrefix() + "&cIl cittadino da arrestare deve essere entro 10 blocchi da te."));
                            return AnvilGUI.Response.close();
                        }
                new AnvilGUI.Builder().onComplete((jail, password) -> {
                            String inputJail = password.toLowerCase();
                            if (!checkJailExists(inputJail)) {
                                jail.sendMessage(translate(getPrefix() + "&cPrigione Inesistente!"));
                                return AnvilGUI.Response.close();
                            }

                        new AnvilGUI.Builder().onComplete((player2, motivo) -> {
                                    String inputMotivo = motivo.toLowerCase();
                                    new AnvilGUI.Builder().onComplete((player3, cauzione) -> {
                                                String inputCauzione = cauzione.toLowerCase();
                                                if (isDouble(inputCauzione)) {
                                                    Double inputCauzioneDouble = Double.parseDouble(inputCauzione);

                                                    if (!checkPositionExists("utente-foto")) {
                                                        player2.sendMessage(translate(getPrefix() + "&cPosizione utente-foto non impostata!"));
                                                        return AnvilGUI.Response.close();
                                                    }
                                                    if (!checkPositionExists("fdo-foto")) {
                                                        player2.sendMessage(translate(getPrefix() + "&cPosizione fdo-foto non impostata!"));
                                                        return AnvilGUI.Response.close();
                                                    }

                                                    new AnvilGUI.Builder().onComplete((player4, dataScadenza) -> {
                                                                String inputDataScadenza = dataScadenza;
                                                                player4.closeInventory();
                                                                player4.sendMessage(translate(getPrefix() + "&9Creazione identikit in corso..."));
                                                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(FdoHelper.getInstance(), new Runnable(){
                                                            @Override
                                                            public void run(){
                                                                String dataEntrata = getDataEntrata();
                                                                String dataScadenza = inputDataScadenza + " " + getOreEntrata();

                                                                FdoHelper.getIdentikitManager().addIdentikit(cittadinoUUID, inputCittadino, inputMotivo, inputCauzioneDouble, dataEntrata, dataScadenza);
                                                                player4.sendMessage(translate(getPrefix() + "&9Identikit creato con successo."));
                                                                int xU = getFotoCoordX("utente-foto");
                                                                int yU = getFotoCoordY("utente-foto");
                                                                int zU = getFotoCoordZ("utente-foto");
                                                                float yawUtente = getFotoYaw("utente-foto");
                                                                float pitchUtente = getFotoPitch("utente-foto");

                                                                int xF = getFotoCoordX("fdo-foto");
                                                                int yF = getFotoCoordY("fdo-foto");
                                                                int zF = getFotoCoordZ("fdo-foto");
                                                                float yawFdo = getFotoYaw("fdo-foto");
                                                                float pitchFdo = getFotoPitch("fdo-foto");

                                                                Location utenteCoord = new Location(Bukkit.getWorld("world"), xU, yU, zU, yawUtente, pitchUtente);
                                                                Location fdoCoord = new Location(Bukkit.getWorld("world"), xF, yF, zF, yawFdo, pitchFdo);


                                                                cittadinoPlayer.teleport(utenteCoord);
                                                                player4.teleport(fdoCoord);



                                                                //Foto
                                                                player4.sendMessage(translate(getPrefix() + "&9Scatto Foto in corso..."));
                                                                cittadinoPlayer.sendMessage(translate(getPrefix() + "&9Stai fermo, ti stanno scattando una foto!"));
                                                                player4.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 125, 255));
                                                                cittadinoPlayer.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 125, 255));
                                                                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(FdoHelper.getInstance(), new Runnable(){
                                                                    @Override
                                                                    public void run(){
                                                                        player4.sendMessage(translate(getPrefix() + "&aFoto scattata con successo!"));
                                                                        cittadinoPlayer.sendMessage(translate(getPrefix() + "&aFoto scattata con successo!"));

                                                                        int x = getJailCoordX(inputJail);
                                                                        int y = getJailCoordY(inputJail);
                                                                        int z = getJailCoordZ(inputJail);

                                                                        Location prigione = new Location(Bukkit.getWorld("world"), x, y, z);

                                                                        cittadinoPlayer.teleport(prigione);



                                                                        cittadinoPlayer.sendMessage(translate(getPrefix() + "&cSei stato arrestato da " + player4.getName()));
                                                                        player4.sendMessage(translate(getPrefix() + "&aHai arrestato " + cittadinoPlayer.getName() + " con successo."));

                                                                    }
                                                                }, 20 * 6);
                                                            }
                                                        }, 20 * 2);

                                                                return AnvilGUI.Response.close();
                                                            })
                                                            .plugin(FdoHelper.getInstance())
                                                            .text("§r")
                                                            .title("Scadenza Arresto")
                                                            .itemLeft(itemLeft)
                                                            .open(player);
                                                }
                                                else {
                                                    player3.sendMessage(translate(getPrefix() + "&cLa cauzione deve essere un numero!"));
                                                }

                                                return AnvilGUI.Response.close();
                                            })
                                            .plugin(FdoHelper.getInstance())
                                            .text("§r")
                                            .title("Importo Cauzione")
                                            .itemLeft(itemLeft)
                                            .open(player);

                                    return AnvilGUI.Response.close();
                                })
                                .plugin(FdoHelper.getInstance())
                                .text("§r")
                                .title("Motivo Arresto")
                                .itemLeft(itemLeft)
                                .open(player);
                            return AnvilGUI.Response.close();
                        })
                        .plugin(FdoHelper.getInstance())
                        .text("§r")
                        .title("Nome Prigione")
                        .itemLeft(itemLeft)
                        .open(player);
                        return AnvilGUI.Response.close();
                    })
                    .plugin(FdoHelper.getInstance())
                    .text("§r")
                    .title("Cittadino")
                    .itemLeft(itemLeft)
                    .open(player);
        }

    }

}

