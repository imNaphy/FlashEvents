package me.naphy.flashevents;

import me.naphy.flashevents.Commands.FlashEventsSecret;
import me.naphy.flashevents.Commands.Main;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class FlashEvents extends JavaPlugin {

    public static FlashEvents plugin;
    public boolean pluginSwitch;

    @Override
    public void onEnable() {
        plugin = this;
        this.saveDefaultConfig();
        pluginSwitch = this.getConfig().getBoolean("Enabled");
        getCommand("flashevents").setExecutor(new Main());
        getCommand("flasheventssecret").setExecutor(new FlashEventsSecret());
        EventLoader.init();
        Scheduler.start();
        getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&a[FlashEvents] The plugin has been loaded!"));

    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&c[FlashEvents] The plugin has been unloaded!"));
    }
}
