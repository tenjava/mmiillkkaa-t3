package com.mmiillkkaa.randoms;

import com.mmiillkkaa.randoms.events.EventManager;
import com.mmiillkkaa.randoms.events.RandomEvent;
import com.mmiillkkaa.randoms.listener.EntityListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class RandomsPlugin extends JavaPlugin {
    private static RandomsPlugin instance;
    private EventManager eventManager;

    @Override
    public void onEnable() {
        int eventsPerHour = getConfig().getInt("EventsPerHour", 1);
        int delay = (20 * 60 * 60)/eventsPerHour; // Ticks Per Second * Seconds per Minute * Minutes per Hour all
                                                  // Divided by the number of events in an hour gives us
                                                  // The delay between each event.
        eventManager = new EventManager();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, eventManager, 0, delay);

        getServer().getPluginManager().registerEvents(new EntityListener(), this);
        instance = this;
    }

    @Override
    public void onDisable() {
        saveConfig();
    }

    public static RandomsPlugin getInstance() {
        return instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String args[]) {
        if(command.getName().equalsIgnoreCase("triggerrandom")) {
            if(!(sender instanceof Player)) { // Unless Console is a floating thing in the sky, this isn't happening.
                sender.sendMessage("This command can only be used by in-game players.");
                return true;
            }

            if(!sender.hasPermission("mmrandoms.triggerevent.self")) {
                sender.sendMessage(ChatColor.RED + "You do not have permission to trigger events on yourself.");
            }

            if(args.length == 0) {
                String[] messages = new String[] {"Events:",
                                                  "  0 = Undefined",
                                                  "  1 = Stalker Derpy Pig",
                                                  "  2 = Chicken Attack"};
                sender.sendMessage(messages);
                return true;
            }

            Player p = (Player) sender;
            int eventNumber;
            try {
                eventNumber = Integer.parseInt(args[0]);
                if(eventNumber > 2 || eventNumber == 0) {
                    sender.sendMessage(ChatColor.RED + "" + eventNumber + " is not a valid event. Use /triggerrandom to get a list of" +
                            " events.");
                    return true;
                }
            } catch (NumberFormatException e) {
                sender.sendMessage(ChatColor.RED + "That is not a number, and example of an acceptable number" +
                        " would be 5 or 12345");
                return true;
            }

            if(!eventManager.triggerEvent(eventNumber, p)) {
                p.sendMessage(ChatColor.RED + "Could not run the event in your environment.");
            }
            return true;
        } else if(command.getName().equalsIgnoreCase("setup")) {

            return true;
        }
        return false;
    }
}
