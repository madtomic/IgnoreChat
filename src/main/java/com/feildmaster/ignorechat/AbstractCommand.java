package com.feildmaster.ignorechat;

import java.util.logging.Level;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

abstract class AbstractCommand implements CommandExecutor {
    final Ignore plugin;

    AbstractCommand(Ignore p, String commandName, String permission) {
        plugin = p;
        PluginCommand command = plugin.getCommand(commandName);
        if (command != null) { // Don't do anything if the command has been disabled somehow
            command.setExecutor(this);
            command.setPermission(permission);
        } else {
            plugin.getLogger().log(Level.WARNING, "Failed to register command: {0}", commandName);
        }
    }

    public abstract boolean onCommand(CommandSender sender, Command command, String label, String[] args);

    void broadcastMessage(CommandSender sender, String message) {
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            if (//!player.canSee(playerSender) || // The player can't see the sender?
                    //plugin.isIgnoring(sender, player) || // The sender isIgnoring the player?
                    plugin.isIgnoring(player, sender)) { // Player is ignoring sender?
                continue;
            }
            player.sendMessage(message);
        }

        plugin.getServer().getConsoleSender().sendMessage(message); // Send to the console
    }
}