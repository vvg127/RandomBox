package org.plugin.randombox;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;

public class DevTool implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;
            PlayerInventory pi = p.getInventory();

            for (int i = 0; i <= BoxItem.legend.length - 1; i++) {
                pi.addItem(BoxItem.legend[i]);
            }

        }

        return true;
    }
}
