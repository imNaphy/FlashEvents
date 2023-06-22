package me.naphy.flashevents.Commands;

import me.naphy.flashevents.EventLoader;
import me.naphy.flashevents.FlashEvents;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Main implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("flashevents")) {
            if (!sender.hasPermission("flashevents.admin")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b[FlashEvents] &7You don't have access to this command!"));
                return true;
            }
            if (args.length == 0) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b[FlashEvents] &7List of available commands: \n\n &b/flashevents help &7- Displays the available commands provided by this plugin. \n &b/flashevents toggle <start/stop> &7- Turns the plugin on/off. \n &b/flashevents reload &7- Reloads the plugin, events and configuration."));
                return true;
            }
            if (args[0].equalsIgnoreCase("help")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b[FlashEvents] &7List of available commands: \n\n &b/flashevents help &7- Displays the available commands provided by this plugin. \n &b/flashevents toggle <start/stop> &7- Turns the plugin on/off. \n &b/flashevents reload &7- Reloads the plugin, events and configuration."));
                return true;
            }
            else if (args[0].equalsIgnoreCase("toggle")) {
                if (args.length == 1) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b[FlashEvents] &7Please provide a valid argument (&con&7/&coff&7)!"));
                    return true;
                }
                if (args[1].equalsIgnoreCase("on")) {
                    if (FlashEvents.plugin.pluginSwitch) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b[FlashEvents] &7The plugin is already &aenabled&7!"));
                        return true;
                    }
                    else {
                        FlashEvents.plugin.pluginSwitch = true;
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b[FlashEvents] &7The plugin has been &aenabled &7successfully!"));
                        return true;
                    }
                }
                else if (args[1].equalsIgnoreCase("off")) {
                    if (!FlashEvents.plugin.pluginSwitch) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b[FlashEvents] &7The plugin is already &cdisabled&7!"));
                        return true;
                    }
                    else {
                        FlashEvents.plugin.pluginSwitch = false;
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b[FlashEvents] &7The plugin has been &cdisabled &7successfully!"));
                        return true;
                    }
                }
                else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b[FlashEvents] &7Please provide a valid argument (&con&7/&coff&7)!"));
                    return true;
                }
            }
            else if (args[0].equalsIgnoreCase("reload")) {
                FlashEvents.plugin.saveDefaultConfig();
                FlashEvents.plugin.reloadConfig();
                FlashEvents.plugin.pluginSwitch = FlashEvents.plugin.getConfig().getBoolean("Enabled");
                EventLoader.init();
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b[FlashEvents] &7The plugin has been reloaded &asuccessfully&7!"));
                return true;
            }
            else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b[FlashEvents] &7List of available commands: \n\n &b/flashevents help &7- Displays the available commands provided by this plugin. \n &b/flashevents toggle <start/stop> &7- Turns the plugin on/off. \n &b/flashevents reload &7- Reloads the plugin, events and configuration."));
                return true;
            }
        }
        return true;
    }
}
