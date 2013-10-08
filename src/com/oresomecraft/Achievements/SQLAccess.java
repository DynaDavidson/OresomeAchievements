package com.oresomecraft.Achievements;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLAccess {

    /**
     * Create the SQL tables if they don't already exist.
     */
    public static void queryCreateTables() {
        if (!OresomeAchievements.getInstance().mysql.isTable(OresomeAchievements.getInstance().storagePrefix + "_users")) {
            try {
                OresomeAchievements.getInstance().mysql.query("CREATE TABLE `" + OresomeAchievements.getInstance().storagePrefix + "_users` (" +
                        "`id` INT(10) UNSIGNED NULL AUTO_INCREMENT," +
                        "`name` VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8_general_ci'," +
                        "`kicked` VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8_general_ci'," +
                        "`joins` INT UNSIGNED NULL," +
                        "`breaks` INT UNSIGNED NULL," +
                        "`places` INT UNSIGNED NULL," +
                        "`fishes` INT UNSIGNED NULL," +
                        "`crafts` INT UNSIGNED NULL," +
                        "`visits` INT UNSIGNED NULL," +
                        "PRIMARY KEY (`id`))");
            } catch (SQLException e) {
                e.printStackTrace();  //Meh, this isn't retard proof.
            }
        }


        if (!OresomeAchievements.getInstance().mysql.isTable(OresomeAchievements.getInstance().storagePrefix + "_complete")) {
            try {
                OresomeAchievements.getInstance().mysql.query("CREATE TABLE `" + OresomeAchievements.getInstance().storagePrefix + "_complete` (" +
                        "`id` INT(10) UNSIGNED NULL AUTO_INCREMENT," +
                        "`name` VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8_general_ci'," +
                        "`achievement` VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8_general_ci'," +
                        "PRIMARY KEY (`id`))");
            } catch (SQLException e) {
                e.printStackTrace();  //Meh, this isn't retard proof.
            }
        }
    }

    /**
     * Insert a person into the SQL database if they don't exist.
     */
    public static void queryAddUser(String name) {
        ResultSet rs = null;
        try {
            rs = OresomeAchievements.getInstance().mysql.query("SELECT * FROM achievements_users");
        } catch (SQLException e) {
            e.printStackTrace();  //Meh, this isn't retard proof.
        }
        try {
            boolean noadd = false;
            while (rs.next()) {
                if (rs.getString("name").equals(name)) noadd = true;
            }
            if (noadd == false) {
                OresomeAchievements.getInstance().mysql.query("INSERT INTO achievements_users (`name`, `kicked`, `joins`, `breaks`, `places`, `fishes`, `crafts`, `visits`) " +
                        " VALUES ('" + name + "', 'false', '1', '0', '0', '0', '0', '1')");
            }
        } catch (SQLException e) {
            System.out.println("SQL error occurred whilst trying to insert " + name + " into users DB!");
            e.printStackTrace();
        }
    }

    /**
     * Check if they havent achieved said achievement already, then add the achievement.
     */
    public static void queryInsertComplete(String name, String achievement) {
        ResultSet rs = null;
        try {
            rs = OresomeAchievements.getInstance().mysql.query("SELECT * FROM achievements_complete");
        } catch (SQLException e) {
            e.printStackTrace();  //Meh, this isn't retard proof.
        }
        try {
            boolean noadd = false;
            while (rs.next()) {
                if (rs.getString("name").equals(name) && rs.getString("achievement").equals(achievement)){
                    noadd = true;
                    break;
                }
            }
            if (noadd == false) {
                OresomeAchievements.getInstance().mysql.query("INSERT INTO achievements_complete (`name`, `achievement`) " +
                        " VALUES ('" + name + "', '" + achievement + "')");
            }
        } catch (SQLException e) {
            System.out.println("SQL error occurred whilst trying to insert " + name + " and " + achievement + " into achievements DB!");
            e.printStackTrace();
        }
    }

    public static void queryIncrementJoins(String name) {
        ResultSet rs = null;
        try {
            rs = OresomeAchievements.getInstance().mysql.query("SELECT * FROM achievements_users");
        } catch (SQLException e) {
            e.printStackTrace();  //Meh, this isn't retard proof.
        }
        try {
            boolean noinc = false;
            int joins = 0;
            while (rs.next()) {
                if (rs.getString("name").equals(name)) {
                    noinc = true;
                    joins = rs.getInt("joins") + 1;
                    break;
                }
            }
            if (joins == 0) {
                System.out.println("Could not retrieve the `joins` integer from the SQL db!");
                throw new SQLException();
            }
            if (noinc == true) {
                rs = OresomeAchievements.getInstance().mysql.query("UPDATE achievements_users SET joins=" + joins + " WHERE name='" + name + "'");
            }
        } catch (SQLException e) {
            System.out.println("SQL error occurred whilst trying to increment " + name + "'s joins in users DB!");
            e.printStackTrace();
        }
    }

    public static int queryGetJoins(String name) {
        int joins = 0;
        ResultSet rs = null;
        try {
            rs = OresomeAchievements.getInstance().mysql.query("SELECT * FROM achievements_users");
        } catch (SQLException e) {
            e.printStackTrace();  //Meh, this isn't retard proof.
            return joins;
        }
        try {
            while (rs.next()) {
                if (rs.getString("name").equals(name)) {
                    joins = rs.getInt("joins");
                    break;
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL error occurred whilst trying to get " + name + "'s joins in users DB!");
            e.printStackTrace();
            return joins;
        }
        return joins;
    }

    public static void queryIncrementBreaks(String name) {
        ResultSet rs = null;
        try {
            rs = OresomeAchievements.getInstance().mysql.query("SELECT * FROM achievements_users");
        } catch (SQLException e) {
            e.printStackTrace();  //Meh, this isn't retard proof.
        }
        try {
            boolean noinc = false;
            int breaks = 0;
            while (rs.next()) {
                if (rs.getString("name").equals(name)) {
                    noinc = true;
                    breaks = rs.getInt("breaks") + 1;
                    break;
                }
            }
            if (breaks == 0) {
                System.out.println("Could not retrieve the `breaks` integer from the SQL db!");
                throw new SQLException();
            }
            if (noinc == true) {
                rs = OresomeAchievements.getInstance().mysql.query("UPDATE achievements_users SET breaks=" + breaks + " WHERE name='" + name + "'");
            }
        } catch (SQLException e) {
            System.out.println("SQL error occurred whilst trying to increment " + name + "'s breaks in users DB!");
            e.printStackTrace();
        }
    }

    public static int queryGetBreaks(String name) {
        int breaks = 0;
        ResultSet rs = null;
        try {
            rs = OresomeAchievements.getInstance().mysql.query("SELECT * FROM achievements_users");
        } catch (SQLException e) {
            e.printStackTrace();  //Meh, this isn't retard proof.
            return breaks;
        }
        try {
            while (rs.next()) {
                if (rs.getString("name").equals(name)) {
                    breaks = rs.getInt("breaks");
                    break;
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL error occurred whilst trying to get " + name + "'s block breaks in users DB!");
            e.printStackTrace();
            return breaks;
        }
        return breaks;
    }

    public static boolean queryAlreadyAchieved(String name, String achievementName) {
        boolean result = false;
        ResultSet rs = null;
        try {
            rs = OresomeAchievements.getInstance().mysql.query("SELECT * FROM achievements_complete");
        } catch (SQLException e) {
            e.printStackTrace();  //Meh, this isn't retard proof.
            return true;
        }
        try {
            while (rs.next()) {
                if (rs.getString("name").equals(name)) {
                    if (rs.getString("achievement").equals(achievementName)) {
                        result = true;
                        break;
                    } else {
                        result = false;
                        break;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL error occurred whilst trying to get " + name + "'s block breaks in users DB!");
            e.printStackTrace();
            return true;
        }
        return result;

    }
}
