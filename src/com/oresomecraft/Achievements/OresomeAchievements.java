package com.oresomecraft.Achievements;

import com.oresomecraft.Achievements.achievements.*;
import com.oresomecraft.Achievements.db.MySQL;
import com.oresomecraft.Achievements.event.ReadyAchievementsEvent;

import com.sk89q.bukkit.util.CommandsManagerRegistration;
import com.sk89q.minecraft.util.commands.*;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

/**
 * OresomeAchievements, a plugin with custom milestones that award points (known as BattlePoints) on fulfillment.
 *
 * @author OresomeCraft, R3creat3
 */
public class OresomeAchievements extends JavaPlugin {
    public static final Logger logger = Logger.getLogger("Minecraft");
    protected static OresomeAchievements plugin;
    public ArrayList<String> achs = new ArrayList<String>();
    public HashMap<String, String> criteria = new HashMap<String, String>();

    public String storageType = null;
    public int storagePort = 0;
    public String storageHostname = null;
    public String storageUsername = null;
    public String storagePassword = null;
    public String storageDatabase = null;
    public String storagePrefix = null;
    public MySQL mysql;

    public void onEnable() {
        //Config stuff
        if (!(new File("plugin/OresomeAchievements/config.yml").isFile())) {
            saveDefaultConfig();
        }
        storageType = getConfig().getString("database.type");
        storagePort = getConfig().getInt("database.port");
        storageHostname = getConfig().getString("database.hostname");
        storageUsername = getConfig().getString("database.username");
        storagePassword = getConfig().getString("database.password");
        storageDatabase = "OresomeAchievements";
        storagePrefix = "achievements";


        //SQL stuff
        mysql = new MySQL(logger,
                "[AchievementsDB] ",
                storageHostname,
                storagePort,
                storageDatabase,
                storageUsername,
                storagePassword);
        if (mysql.open()) {
            System.out.println("MySQL connected successfully!");
        } else {
            System.out.println("We couldn't connect to the SQL, throwing error and disabling!");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
            //We couldn't connect, bye bye!
        }

        //Register commands
        registerCommands();

        //Achievement instances
        loadAchs();

        //Listener instances
        new SQLListener(this);
        new AchievementListener(this);
        Bukkit.getPluginManager().callEvent(new ReadyAchievementsEvent());
    }

    protected void loadAchs() {
        //Make a protected method that loads the maps
        new FistsOfFury();
        new Addicted();
        new BigStreak();
        new Experienced();
        new AnvilFalling();
        new Nimble();
        new Builder();
        new Crafter();
        new Demolition();
        new OverPowered();
        new MillionVolts();
        new BlastProof();
        new Torture();
        new N00k();
        new Manipulator();
        new AdminYet();
        new Cursed();
        new Naughty();
        new GlitchingI();
        new GlitchingII();
        new GlitchingIII();
        new Ranked();
        new FreeTransport();
        new Reckless();
        new GoneFishin();
        new Sturdy();
        new SniperPractice();
        new EpicDive();
    }

    public OresomeAchievements() {
        plugin = this;
    }

    public void onDisable() {
        //SQL Stuff

        //Unregister handlers
        HandlerList.unregisterAll(this);

        System.gc();
        //Unsafe GC, remove if you want @Zachoz.
    }

    public static OresomeAchievements getInstance() {
        return plugin;
    }

    public static void addMap(String name) {
        plugin.achs.add(name);
    }

    public static void addCriteria(String name, String criteria) {
        plugin.criteria.put(name, criteria);
    }

    /**
     * *******************************************************************
     * Code to use for sk89q's command framework goes below this comment! *
     * ********************************************************************
     */

    private CommandsManager<CommandSender> commands;
    private boolean opPermissions;

    private void registerCommands() {
        final OresomeAchievements plugin = this;
        // Register the commands that we want to use
        commands = new CommandsManager<CommandSender>() {
            @Override
            public boolean hasPermission(CommandSender player, String perm) {
                return plugin.hasPermission(player, perm);
            }
        };
        commands.setInjector(new SimpleInjector(this));
        final CommandsManagerRegistration cmdRegister = new CommandsManagerRegistration(this, commands);

        cmdRegister.register(Commands.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        try {
            commands.execute(cmd.getName(), args, sender, sender);
        } catch (CommandPermissionsException e) {
            sender.sendMessage(ChatColor.RED + "You don't have permission.");
        } catch (MissingNestedCommandException e) {
            sender.sendMessage(ChatColor.RED + e.getUsage());
        } catch (CommandUsageException e) {
            sender.sendMessage(ChatColor.RED + e.getMessage());
            sender.sendMessage(ChatColor.RED + e.getUsage());
        } catch (WrappedCommandException e) {
            if (e.getCause() instanceof NumberFormatException) {
                sender.sendMessage(ChatColor.RED + "Number expected, string received instead.");
            } else {
                sender.sendMessage(ChatColor.RED + "An error has occurred. See console.");
                e.printStackTrace();
            }
        } catch (CommandException e) {
            sender.sendMessage(ChatColor.RED + e.getMessage());
        }

        return true;
    }

    public boolean hasPermission(CommandSender sender, String perm) {
        if (!(sender instanceof Player)) {
            if (sender.hasPermission(perm)) {
                return ((sender.isOp() && (opPermissions || sender instanceof ConsoleCommandSender)));
            }
        }
        return hasPermission(sender, ((Player) sender).getWorld(), perm);
    }

    public boolean hasPermission(CommandSender sender, World world, String perm) {
        if ((sender.isOp() && opPermissions) || sender instanceof ConsoleCommandSender || sender.hasPermission(perm)) {
            return true;
        }

        return false;
    }

    public void checkPermission(CommandSender sender, String perm)
            throws CommandPermissionsException {
        if (!hasPermission(sender, perm)) {
            throw new CommandPermissionsException();
        }
    }

    public void checkPermission(CommandSender sender, World world, String perm)
            throws CommandPermissionsException {
        if (!hasPermission(sender, world, perm)) {
            throw new CommandPermissionsException();
        }
    }
}