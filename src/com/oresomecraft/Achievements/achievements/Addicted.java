package com.oresomecraft.Achievements.achievements;

import com.oresomecraft.Achievements.IOAchievement;
import com.oresomecraft.Achievements.OAType;
import com.oresomecraft.Achievements.OAchievement;
import com.oresomecraft.Achievements.SQLAccess;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Addicted extends OAchievement implements IOAchievement, Listener {

    public Addicted() {
        super.initiate(this, name, type, criteria, reward);
    }

    //Objective details
    String name = "Addicted";
    OAType type = OAType.INCREMENTAL;
    String criteria = "You've gotta be addicted to come back here more than 100 times!";
    int reward = 20;

    public void readyAchievement() {
        //Don't need anything here yet;
    }

    //Make your own code to set off the achievement.
    @EventHandler(priority = EventPriority.HIGHEST)
    public void checkJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        int increment = SQLAccess.queryGetJoins(event.getPlayer().getName());
        if (increment >= 100) {
            callAchievementGet(name, type, criteria, p, increment, reward);
        }
        if (increment == 50 || increment == 25 || increment == 75 || increment == 90) {
            callAchievementCheckpoint(name, type, criteria, p, increment);
        }
    }
}
