package com.chaosmc.standmenu;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public final class StandMenuPlugin extends JavaPlugin {
    final Set<UUID> openedByPlugin = new HashSet<>();
    final Set<UUID> clickedDuringOpen = new HashSet<>();

    Material triggerBlock = Material.EMERALD_BLOCK;
    String openCommandTemplate = "dm open %menu% %player%";
    String menuName = "main";
    Location teleportLocation = null;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        loadConfig();
        getServer().getPluginManager().registerEvents(new StandListener(this), this);
        getLogger().info("StandMenuPlugin enabled");
    }

    @Override
    public void onDisable() {
        getLogger().info("StandMenuPlugin disabled");
    }

    void loadConfig() {
        FileConfiguration cfg = getConfig();
        String mat = cfg.getString("trigger-block", "EMERALD_BLOCK");
        Material m = Material.matchMaterial(mat);
        if (m != null) triggerBlock = m;
        openCommandTemplate = cfg.getString("open-command", openCommandTemplate);
        menuName = cfg.getString("menu-name", menuName);
        String world = cfg.getString("teleport.world", "world");
        double x = cfg.getDouble("teleport.x", 0);
        double y = cfg.getDouble("teleport.y", 64);
        double z = cfg.getDouble("teleport.z", 0);
        teleportLocation = new Location(Bukkit.getWorld(world), x, y, z);
    }

    public void markOpened(Player p) {
        openedByPlugin.add(p.getUniqueId());
        clickedDuringOpen.remove(p.getUniqueId());
    }

    public void markClicked(Player p) {
        clickedDuringOpen.add(p.getUniqueId());
    }

    public boolean wasOpened(Player p) {
        return openedByPlugin.contains(p.getUniqueId());
    }

    public boolean wasClicked(Player p) {
        return clickedDuringOpen.contains(p.getUniqueId());
    }

    public void clearTracking(Player p) {
        openedByPlugin.remove(p.getUniqueId());
        clickedDuringOpen.remove(p.getUniqueId());
    }

    public String getOpenCommand() {
        return openCommandTemplate.replace("%menu%", menuName);
    }

    public Location getTeleportLocation() {
        return teleportLocation;
    }
}
