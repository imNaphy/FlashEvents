package me.naphy.flashevents;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class EventLoader {

    public static class EventInfo {
        public String eventName;
        public boolean eventRedeemable;
        public int eventTimer;
        public int eventRedeemableTimer;
        public String eventMessage;
        public List<String> eventCommands;
        public String eventSecret;
        public List<Player> eventPlayersRedeemed;
    }
    public static List<EventInfo> events = new ArrayList<>();

    public static void init() {
        events = new ArrayList<>();
        for (String i : FlashEvents.plugin.getConfig().getConfigurationSection("Events").getKeys(false)) {
            EventInfo temp = new EventInfo();
            temp.eventName = i;
            temp.eventRedeemable = false;
            temp.eventTimer = FlashEvents.plugin.getConfig().getInt("Events." + i + ".Timer");
            temp.eventRedeemableTimer = FlashEvents.plugin.getConfig().getInt("Events." + i + ".Redeemable for");
            temp.eventMessage = FlashEvents.plugin.getConfig().getString("Events." + i + ".Message");
            temp.eventCommands = FlashEvents.plugin.getConfig().getStringList("Events." + i + ".Commands");
            temp.eventSecret = Scheduler.GenerateSecret(32);
            temp.eventPlayersRedeemed = new ArrayList<>();
            events.add(temp);
        }
    }
}
