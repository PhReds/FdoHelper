package me.phredss.fdohelper.command;


import me.phredss.fdohelper.FdoHelper;
import me.phredss.fdohelper.sqlite.Identikit;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

import static me.phredss.fdohelper.utils.Utils.*;


public class FdoCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (!player.hasPermission(getPermissions("staff"))) {
            player.sendMessage(getPrefix() + getMessagesNoPermission("generali"));
            return true;
        }

        if (args.length == 0) {
            //Lunghezza 0
            player.sendMessage(getPrefix() + getMessagesSintassiErrataFdo("generali"));
            return false;
        }

        //Sottocomando "get"
        if (args[0].equalsIgnoreCase("get")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("Questo comando può essere eseguito solo da un giocatore.");
                return true;
            }

            if (args.length == 2 && args[1].equalsIgnoreCase("tablet")) {
                //Givva Tablet
                ItemStack tablet = getItemIA(player, getItemsAdderConfig("tablet-fdo"));
                player.getInventory().addItem(tablet);
                return false;
            }
            player.sendMessage(translate("&cSintassi Errata: /fdo get <item>"));

        }

        if (args[0].equalsIgnoreCase("identikit")) {
            if (args.length == 3 && args[1].equalsIgnoreCase("mostra")) {
                Player target = Bukkit.getPlayer(args[2]);
                UUID targetUUID = target.getUniqueId();
                List<Identikit> identikitList = FdoHelper.getIdentikitManager().getIdentikitByPlayerUUID(targetUUID);

                for (int i = 0; i < identikitList.size(); i++) {
                    Identikit identikit = identikitList.get(i);

                    //Calcola Tempo
                        String playerMessage = "Nome: " + target.getName();
                        player.sendMessage(playerMessage);
                        String importoMessage = "Cauzione: " + ChatColor.GREEN + identikit.getImporto();
                        player.sendMessage(importoMessage);
                        String dataCreazioneMessage = "Data Entrata: " + identikit.getDataCreazione();
                        player.sendMessage(dataCreazioneMessage);
                        String dataScadenzaMessage = "Data Scadenza: " + identikit.getDataScadenza();
                        player.sendMessage(dataScadenzaMessage);
                        String tempoRimanente = "Tempo rimanente: " + calculateTimeDifference(identikit.getDataScadenza());
                        player.sendMessage(tempoRimanente);
                        String motivoMessage = "Motivo: " + ChatColor.RED + identikit.getMotivo();
                        player.sendMessage(motivoMessage);
                        String numeroidentikit = "Numero Identikit: " + identikit.getId();
                        player.sendMessage(numeroidentikit);
                        if (i < identikitList.size() - 1) {
                            player.sendMessage(ChatColor.DARK_GRAY + "-------------------");
                        }


                }
                return false;

            }

            if (args.length >= 6 && args[1].equalsIgnoreCase("crea")) {
                Player target = Bukkit.getPlayer(args[2]);

                UUID playerUUID = target.getUniqueId();
                String targetstring = player.getName();
                //String motivo = args[5];
                double cauzione = Double.parseDouble(args[3]);
                String dataEntrata = getDataEntrata();
                String dataEntrataFinale = getDataEntrata().replaceAll(
                        "/", "_"
                );

                String dataScadenzaString = args[4];
                String dataUscitaForm = dataScadenzaString.replaceAll(
                        "/", "_"
                );
                String dataUscitaFinale = dataUscitaForm + " " + getOreEntrata();

                System.out.println(dataEntrata);
                System.out.println(dataScadenzaString);
                StringBuilder argsT = new StringBuilder();
                for (int i = 5; i < args.length; i++){
                    argsT.append(args[i]).append(" ");
                }

                String causale = argsT.toString().trim();

                FdoHelper.getIdentikitManager().addIdentikit(playerUUID, targetstring, causale, cauzione, dataEntrataFinale, dataUscitaFinale);
                player.sendMessage(getMessagesIdentikitCrea("identikit"));
                return false;
            }
            if (args.length == 3 && args[1].equalsIgnoreCase("rimuovi")) {
                Player target = Bukkit.getPlayer(args[2]);
                UUID playerUUID = target.getUniqueId();
                int identikitId = Integer.valueOf(args[3]);

                FdoHelper.getIdentikitManager().removeIdentikit(playerUUID);
                player.sendMessage(getMessagesIdentikitRimuovi("identikit"));

            } else {
                player.sendMessage(getPrefix() + getMessagesSintassiErrataFdo("generali"));
                return false;
            }

        }
        if (args[0].equalsIgnoreCase("reload")) {
            FdoHelper.getInstance().getConfigFile().reload();
            FdoHelper.getInstance().getGuiFile().reload();
            FdoHelper.getInstance().getItemFile().reload();
            FdoHelper.getInstance().getJailFile().reload();
            FdoHelper.getInstance().getMessagesFile().reload();
            FdoHelper.getInstance().getPermissionsFile().reload();
            player.sendMessage(translate(getPrefix() + "Reload completato con successo!"));
        }
        if (args[0].equalsIgnoreCase("rimuoviaccount")) {
            Player target = Bukkit.getPlayer(args[1]);
            if (!FdoHelper.getAccountManager().checkAccountExists(target.getUniqueId().toString())) {
                player.sendMessage(translate("&cL'Utente non ha registrato un account."));
                return false;
            }
            FdoHelper.getAccountManager().removeAccount(target.getUniqueId().toString());
            player.sendMessage(translate("&cAccount di &4&l" + target.getName() + "&r&c eliminato."));
        }
        if (args[0].equalsIgnoreCase("position")) {
            if (args.length == 3 && args[1].equalsIgnoreCase("crea")) {
                if (args[2].equalsIgnoreCase("utente-foto")) {
                    //Crea Position
                    int x = player.getLocation().getBlockX();
                    int y = player.getLocation().getBlockY();
                    int z = player.getLocation().getBlockZ();
                    float pitch = player.getLocation().getPitch();
                    float yaw = player.getLocation().getYaw();

                    createPosition("utente-foto", player, x, y, z, pitch, yaw);
                    return false;
                } else if (args[2].equalsIgnoreCase("fdo-foto")) {
                    //Crea Position
                    int x = player.getLocation().getBlockX();
                    int y = player.getLocation().getBlockY();
                    int z = player.getLocation().getBlockZ();
                    float pitch = player.getLocation().getPitch();
                    float yaw = player.getLocation().getYaw();


                    createPosition("fdo-foto", player, x, y, z, pitch, yaw);
                    return false;
                }
                player.sendMessage(translate(getPrefix() + "&cSintassi Errata: /fdo position crea <utente-foto/fdo-foto>"));
            } else if (args.length == 3 && args[1].equalsIgnoreCase("rimuovi")) {
                if (args[2].equalsIgnoreCase("utente-foto")) {

                    removePosition("utente-foto", player);
                    return false;
                } else if (args[2].equalsIgnoreCase("fdo-foto")) {

                    removePosition("fdo-foto", player);
                    return false;
                }
            }


        }
        return false;
    }
}
