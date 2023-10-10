package me.phredss.fdohelper.command.tablcompletion;

import me.phredss.fdohelper.FdoHelper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.*;

public class FdoTabCompletion implements TabCompleter {
    private FdoHelper plugin;
    public FdoTabCompletion(FdoHelper main) {
        plugin = main;
    }
    private static final String[] COMMANDS = {};

        @Override
        public List<String> onTabComplete(CommandSender sender, Command command, String commandLabel, String[] args) {
            List<String> completions = new ArrayList<>();

            if (args.length == 1) {
                StringUtil.copyPartialMatches(args[0], getMainCommands(), completions);
            } else if (args.length == 2) {
                StringUtil.copyPartialMatches(args[1], getSubCommands(args[0]), completions);
            } else if (args.length == 3) {
                if (args[0].equalsIgnoreCase("position")) {
                    StringUtil.copyPartialMatches(args[2], getPositionSubCommands(args[1]), completions);
                }
            }

            return completions;
        }

        private List<String> getMainCommands() {
            List<String> commands = new ArrayList<>();
            commands.add("get");
            commands.add("identikit");
            commands.add("position");
            commands.add("help");
            commands.add("reload");
            return commands;
        }

        private List<String> getSubCommands(String mainCommand) {
            List<String> subCommands = new ArrayList<>();

            if (mainCommand.equalsIgnoreCase("get")) {
                subCommands.add("tablet");
            } else if (mainCommand.equalsIgnoreCase("identikit")) {
                subCommands.add("crea");
                subCommands.add("mostra");
                subCommands.add("rimuovi");
            } else if (mainCommand.equalsIgnoreCase("position")) {
                subCommands.add("crea");
                subCommands.add("rimuovi");
            }

            return subCommands;
        }

        private List<String> getPositionSubCommands(String subCommand) {
            List<String> positionSubCommands = new ArrayList<>();

            if (subCommand.equalsIgnoreCase("crea") || subCommand.equalsIgnoreCase("rimuovi")) {
                positionSubCommands.add("utente-foto");
                positionSubCommands.add("fdo-foto");
            }

            return positionSubCommands;
        }
    }

