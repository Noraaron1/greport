package me.noraaron1.Greport;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("static-access")
public class GreportCommands extends JavaPlugin
  implements Listener
{
  GPConfig ch = new GPConfig(this);
  LangFile l = new LangFile(this);

  public void onEnable() { PluginManager pm = getServer().getPluginManager();
    pm.registerEvents(this, this);
    try { saveConfig(); setupConfig(getConfig()); saveConfig(); } catch (Exception e) { e.printStackTrace(); }
    try {
      ch.createBugReportFile();
      LangFile.unpackLangFile();
    } catch (IOException e) {
      System.out.println("[Greport] ERROR - Creating Base Report File");
    } }

  public boolean onCommand(CommandSender sender, Command cmd, String args, String[] args)
  {
    Player player = (Player)sender;
    if (cmd.getName.equalsIgnoreCase("greport")) {
      if (args.length == 0) {
        sender.sendMessage(ChatColor.RED + "Usage: /greport <msg>");
      }
      if (args.length > 1) {
          for (Player p : Bukkit.getOnlinePlayers()) {
            String message = StringUtils.join(args, ' ', 1, args.length);
            try {
              ch.fileReport(player, ChatColor.RED + "[GREPORT] " + ChatColor.WHITE + message.toString());
            } catch (IOException e) {
              System.out.println("[Greport] ERROR - Creating Bug Report");
            }
            if (p.isOp()) {
              p.sendMessage(ChatColor.BLUE + "[Greport]" + ChatColor.AQUA + LangFile.ln4);
          }
        }
        }
    }
    if (cmd.getName.equalsIgnoreCase("glist")) {
      if (player.hasPermission("glist.command")){
        ch.listUnreadReports(player);
    }else{
        player.sendMessage(ChatColor.DARK_RED + LangFile.ln2);
      }
    }
    if (cmd.getName.equalsIgnoreCase("gread")) {
      if (player.hasPermission("gread.command")){
        try {
          if (!isInt(args[0])) break label847;
          try {
            ch.readReport(player, Integer.parseInt(args[0])); 
            } catch (NumberFormatException localNumberFormatException) {
          } catch (IOException localIOException1) {
          }
        } catch (Exception localException) {
        }
    }else{
    	  player.sendMessage(ChatColor.DARK_RED + LangFile.ln2);
      }
    }
    return false;
  }
  public boolean isInt(String s) {
    try {
      Double.parseDouble(s);
      return true; } catch (NumberFormatException e) {
    }
    return false;
  }

  @EventHandler
  public void onLogin(PlayerLoginEvent event)
  {
    final Player player = event.getPlayer();
    if (player.isOp()) {
	boolean un = ch.checkForUnread();
      if (un)
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
          public void run() {
            player.sendMessage(ChatColor.BLUE + "[Greport]" + ChatColor.AQUA + LangFile.ln3);
          }
        }
        , 20L);
    }
  }

  private void setupConfig(FileConfiguration config) throws IOException
  {
    config.set("Plugin", "Created By Noraaron1");
  }
}
