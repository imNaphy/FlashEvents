package me.naphy.flashevents.Commands;

import me.naphy.flashevents.EventLoader;
import me.naphy.flashevents.FlashEvents;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlashEventsSecret implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equals("flasheventssecret")) {
            if (!sender.hasPermission("flashevents.use")) return true;
            if (args.length == 2) {
                for (EventLoader.EventInfo event : EventLoader.events) {
                    if (event.eventName.equals(args[0])) {
                        if (!event.eventSecret.equals(args[1])) {
                            return true;
                        }
                        if (!event.eventRedeemable) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b[FlashEvents] &7The time for redeeming the rewards has expired!"));
                            return true;
                        }
                        if (event.eventPlayersRedeemed.contains(sender)) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b[FlashEvents] &7You have already redeemed your rewards!"));
                            return true;
                        }
                        for (String command : event.eventCommands) {
                            try {
                                Bukkit.getScheduler().runTask(FlashEvents.plugin, () -> {
                                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command
                                            .replaceAll("%player%", sender.getName())
                                            .replaceAll("%event_time%", String.valueOf(FlashEvents.plugin.getConfig().getInt("Events." + event.eventName + ".Timer")))
                                    );
                                });
                            } catch (Exception e) {
                                System.err.println(e);
                            }
                        }
                        event.eventPlayersRedeemed.add((Player) sender);
                        return true;
                    }
                }
            }
        }
        return true;
    }
}
