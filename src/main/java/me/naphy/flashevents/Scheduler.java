package me.naphy.flashevents;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class Scheduler {

    public static void start() {
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                if (FlashEvents.plugin.pluginSwitch) {
                    for (EventLoader.EventInfo event : EventLoader.events) {
                        if (event.eventTimer <= 0) {
                            event.eventRedeemable = true;
                            event.eventTimer = FlashEvents.plugin.getConfig().getInt("Events." + event.eventName + ".Timer");
                            event.eventRedeemableTimer = FlashEvents.plugin.getConfig().getInt("Events." + event.eventName + ".Redeemable for");
                            event.eventPlayersRedeemed = new ArrayList<>();
                            String secret = GenerateSecret(32);
                            event.eventSecret = secret;
                            for (Player i : Bukkit.getOnlinePlayers()) {
                                TextComponent text = new TextComponent(ChatColor.translateAlternateColorCodes('&', event.eventMessage
                                        .replaceAll("%player%", i.getName())
                                        .replaceAll("%event_time%", String.valueOf(FlashEvents.plugin.getConfig().getInt("Events." + event.eventName + ".Timer")))
                                ));
                                text.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&aClick to obtain the rewards!")).create()));
                                text.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/flasheventssecret " + event.eventName + " " + secret));
                                i.spigot().sendMessage(text);
                            }
                        }
                        if (event.eventRedeemableTimer <= 0) {
                            event.eventRedeemableTimer = FlashEvents.plugin.getConfig().getInt("Events." + event.eventName + ".Redeemable for");
                            event.eventRedeemable = false;
                        }
                        if (event.eventTimer > 0 && event.eventRedeemable) {
                            event.eventRedeemableTimer--;
                        }
                        if (event.eventTimer > 0 && !event.eventRedeemable) {
                            event.eventTimer--;
                        }
                    }
                }
            }
        };
        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
        service.scheduleAtFixedRate(runnable, 0, 1, TimeUnit.SECONDS);
    }

    public static String GenerateSecret(int n) {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            int index = (int)(AlphaNumericString.length() * Math.random());

            sb.append(AlphaNumericString.charAt(index));
        }

        return sb.toString();
    }
}
