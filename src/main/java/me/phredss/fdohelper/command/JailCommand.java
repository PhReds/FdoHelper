package me.phredss.fdohelper.command;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;



import static me.phredss.fdohelper.utils.Utils.*;

public class JailCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (!player.hasPermission(getPermissions("staff"))) {
            player.sendMessage(getPrefix() + getMessagesNoPermission("generali"));
            return true;
        }

        if (args.length == 0) {
            //Lunghezza 0
            player.sendMessage(getPrefix() + getMessagesSintassiErrataJail("generali"));
            return false;
        }

        if (args[0].equalsIgnoreCase("crea")) {

            if (args.length == 2) {

                //Crea Jail
                int x = player.getLocation().getBlockX();
                int y = player.getLocation().getBlockY();
                int z = player.getLocation().getBlockZ();
                String nomeJail = args[1];

                createJail(nomeJail, player, x, y, z);
                player.sendMessage(translate(getPrefix() + getMessagesJailCreata("jail")));
                return false;
            }
            player.sendMessage(getPrefix() + getMessagesSintassiErrataJail("generali"));


            return false;
        }

        if (args[0].equalsIgnoreCase("rimuovi")) {

            if (args.length == 2) {
                String nomeJail = args[1];
                removeJail(nomeJail, player);
                return false;
            }
            player.sendMessage(getPrefix() + getMessagesSintassiErrataJail("generali"));

            return false;
        }

        if (args[0].equalsIgnoreCase("elimina")) {

            //Elimina Jail
            removeJail(args[1], player);
            player.sendMessage(translate(getPrefix() + "&cLa jail " + args[1] + " è stata rimossa con successo"));
            return false;
        }

        if (args[0].equalsIgnoreCase("tp")) {
            //Tippa il giocatore alla jail
            if (args.length == 2) {
                if (!checkJailExists(args[1])) {
                    player.sendMessage(translate(getPrefix() + "&cLa jail inserita non esiste!"));
                    return false;
                }
                //Tippa te
                int x = getJailCoordX(args[1]);
                int y = getJailCoordY(args[1]);
                int z = getJailCoordZ(args[1]);
                Location jailCoord = new Location(Bukkit.getWorld("world"), x, y, z);

                player.teleport(jailCoord);
            }
            return false;
        }

        if (args[0].equalsIgnoreCase("playertp")) {
            if (args.length == 3) {
               if (!checkJailExists(args[2])) {
                   player.sendMessage(translate(getPrefix() + "&cLa jail inserita non esiste!"));
                   return false;
               }

                //Tippa altro giocatore
                Player target = Bukkit.getPlayer(args[1]);
                int x = getJailCoordX(args[2]);
                int y = getJailCoordY(args[2]);
                int z = getJailCoordZ(args[2]);
                Location jailCoord = new Location(Bukkit.getWorld("world"), x, y, z);

                player.sendMessage(getPrefix() + getMessagesUserJailed("jail"));
                target.teleport(jailCoord);
                target.sendMessage(getPrefix() + getMessagesUserJailedMess("jail"));

            }
            return false;
        }

         else {
            player.sendMessage(getPrefix() + getMessagesSintassiErrataJail("generali"));
            return false;
        }
    }

}
