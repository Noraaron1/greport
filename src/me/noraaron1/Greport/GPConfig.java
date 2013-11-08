package me.noraaron1.Greport;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class GPConfig
{
  static GreportCommands plugin;

  public GPConfig(GreportCommands instance)
  {
    plugin = instance;
  }

  public static void createBugReportFile() throws IOException {
    String report = "GriefReports";
    File reportDir = new File(plugin.getDataFolder() + File.separator + "Greports");
    if (!reportDir.exists()) {
      reportDir.mkdir();
    }
    File reportFile = new File(plugin.getDataFolder() + File.separator + "Greports" + File.separator + report + ".yml");
    if (!reportFile.exists()) {
      reportFile.createNewFile();
      FileConfiguration fc = YamlConfiguration.loadConfiguration(reportFile);
      fc.set("Greports", "Greports");
      fc.save(reportFile);
    }
  }

  public static void fileReport(Player player, String bug) throws IOException { String report = "GriefReports";
    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    Date date = new Date();
    File reportFile = new File(plugin.getDataFolder() + File.separator + "Greports" + File.separator + report + ".yml");
    if (reportFile.exists()) {
      FileConfiguration fc = YamlConfiguration.loadConfiguration(reportFile);
      for (int i = 0; i >= 0; i++)
        if (!fc.contains("Greport." + i)) {
          fc.set("Greport." + i + ".Date", dateFormat.format(date));
          fc.set("Greport." + i + ".Player", player.getName());
          fc.set("Greport." + i + ".Report", bug);
          fc.set("Greport." + i + ".Viewed", Boolean.valueOf(false));
          fc.save(reportFile);
          player.sendMessage(ChatColor.GOLD + LangFile.ln5);
          return;
        }
    } }

  public static void listUnreadReports(Player player)
  {
    String report = "GriefReports";
    File reportFile = new File(plugin.getDataFolder() + File.separator + "Greports" + File.separator + report + ".yml");
    boolean rep = false;
    if (reportFile.exists()) {
      FileConfiguration fc = YamlConfiguration.loadConfiguration(reportFile);
      player.sendMessage(ChatColor.GOLD + "========== [Report List] ==========");
      for (int i = 0; i < 500; i++)
        if (fc.contains("Greport." + i)) {
          if (!fc.getBoolean("Greport." + i + ".Viewed")) {
            player.sendMessage(ChatColor.GRAY + "[" + ChatColor.RED + i + ChatColor.GRAY + "]" + ChatColor.DARK_RED + LangFile.ln8 + ".");
            rep = true;
          } else {
            player.sendMessage(ChatColor.GRAY + "[" + ChatColor.RED + i + ChatColor.GRAY + "]" + ChatColor.DARK_GREEN + LangFile.ln7 + ".");
            rep = true;
          }
        } else if ((!fc.contains("Greport." + i)) && (!rep)) {
          i = 0;
          player.sendMessage(ChatColor.BLUE + "[Greport]" + ChatColor.AQUA + LangFile.ln3);
          break;
        }
    }
  }

  public static void readReport(Player player, int i) throws IOException
  {
    String report = "GriefReports";
    File reportFile = new File(plugin.getDataFolder() + File.separator + "Greports" + File.separator + report + ".yml");
    if (reportFile.exists()) {
      FileConfiguration fc = YamlConfiguration.loadConfiguration(reportFile);
      if (fc.contains("Greport." + i)) {
        player.sendMessage(ChatColor.GOLD + "========== [Grief Report: " + i + "] ==========");
        player.sendMessage(ChatColor.GREEN + "Date: " + ChatColor.AQUA + fc.getString(new StringBuilder("Greport.").append(i).append(".Date").toString()));
        player.sendMessage(ChatColor.GREEN + "Player: " + ChatColor.AQUA + fc.getString(new StringBuilder("Greport.").append(i).append(".Player").toString()));
        player.sendMessage(ChatColor.GREEN + "Report: " + ChatColor.AQUA + fc.getString(new StringBuilder("Greport.").append(i).append(".Report").toString()));
        player.sendMessage(ChatColor.GOLD + "========== [End Report: " + i + "] ==========");
        fc.set("Greport." + i + ".Viewed", Boolean.valueOf(true));
        fc.save(reportFile);
      }
    }
  }

  public static boolean checkForUnread() { String report = "GriefReports";
    File reportFile = new File(plugin.getDataFolder() + File.separator + "Greports" + File.separator + report + ".yml");
    if (reportFile.exists()) {
      FileConfiguration fc = YamlConfiguration.loadConfiguration(reportFile);
      for (int i = 0; i < 500; i++) {
        if ((fc.contains("Greport." + i)) && 
          (!fc.getBoolean("Greport." + i + ".Viewed"))) {
          i = 0;
          return true;
        }
      }
    }

    return false;
  }
}