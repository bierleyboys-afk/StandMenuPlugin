package com.chaosmc.standmenu;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;

public class StandListener implements Listener {
    private final StandMenuPlugin plugin;

    public StandListener(StandMenuPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        Block from = e.getFrom().getBlock();
        Block to = e.getTo().getBlock();
        if (from.equals(to)) return;
        if (to.getType() == plugin.triggerBlock) {
            // open DeluxeMenus GUI via console command
            String cmd = plugin.getOpenCommand().replace("%player%", p.getName());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
            plugin.markOpened(p);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) return;
        Player p = (Player) e.getWhoClicked();
        if (plugin.wasOpened(p)) {
            plugin.markClicked(p);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if (!(e.getPlayer() instanceof Player)) return;
        Player p = (Player) e.getPlayer();
        if (!plugin.wasOpened(p)) return;
        boolean clicked = plugin.wasClicked(p);
        plugin.clearTracking(p);
        if (!clicked) {
            Location tp = plugin.getTeleportLocation();
            if (tp != null && tp.getWorld() != null) {
                p.teleport(tp);
                p.sendMessage("&aTeleported to configured location.");
            } else {
                p.sendMessage("&cTeleport location is not set or world is missing.");
            }
        }
    }
}
