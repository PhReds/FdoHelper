package me.phredss.fdohelper.command.tablcompletion;

import me.phredss.fdohelper.FdoHelper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class JailTabCompletion implements TabCompleter {

    private FdoHelper plugin;
    public JailTabCompletion(FdoHelper main) {
        plugin = main;
    }

    private static final String[] COMMANDS = {};

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String commandLable, String[] args) {

        List<String> completions = new ArrayList<>();
        Player p = (Player) sender;
        if (args.length == 1) {
            completions.add("crea");
            completions.add("rimuovi");
            completions.add("tp");
            completions.add("playertp");
            return StringUtil.copyPartialMatches(args[0], completions, new ArrayList<>());
        }
        return null;
    }

}
