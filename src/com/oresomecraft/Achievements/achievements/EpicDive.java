package com.oresomecraft.Achievements.achievements;

import com.oresomecraft.Achievements.IOAchievement;
import com.oresomecraft.Achievements.OAType;
import com.oresomecraft.Achievements.OAchievement;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class EpicDive extends OAchievement implements IOAchievement, Listener {

    public EpicDive() {
        super.initiate(this, name, type, criteria, reward);
    }

    //Objective details
    String name = "Epic Dive";
    OAType type = OAType.OBJECTIVE;
    String criteria = "Survive a fall of a certain distance in water!";
    int reward = 15;

    public void readyAchievement() {
        //Don't need anything here yet;
    }

    //Make your own code to set off the achievement.
    @EventHandler
    public void checkDeath(PlayerMoveEvent event) {
        if (event.getPlayer().getWorld().getBlockAt(event.getPlayer().getLocation()).getRelative(BlockFace.DOWN).getType() == Material.WATER ||
                event.getPlayer().getWorld().getBlockAt(event.getPlayer().getLocation()).getRelative(BlockFace.DOWN).getType() == Material.STATIONARY_WATER) {
            if ((int) event.getPlayer().getFallDistance() >= 50 && event.getPlayer().getGameMode() == GameMode.SURVIVAL) {
                callAchievementGet(name, type, criteria, event.getPlayer(), 0, reward);
            }
        }
    }
}
