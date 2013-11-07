package me.noraaron1.Greport;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class LangFile
{
  static GreportCommands plugin;
  public static String Language;
  public static String ln1;
  public static String ln2;
  public static String ln3;
  public static String ln4;
  public static String ln5;
  public static String ln6;
  public static String ln7;
  public static String ln8;

  public LangFile(GreportCommands instance)
  {
    plugin = instance;
  }

  public static void unpackLangFile()
  {
    File langFile = new File(plugin.getDataFolder(), "LanguageFile.yml");
    if (!langFile.exists()) {
      System.out.println("Unpacking Language File...");
      plugin.saveResource("LanguageFile.yml", false);
      System.out.println("Unpacking Language File... DONE!");
      FileConfiguration fc = YamlConfiguration.loadConfiguration(langFile);
      String lang = fc.getString("Language");
      System.out.println("Loading Language: " + lang);
      Language = lang;
      ln1 = fc.getString("Languages." + lang + ".1");
      ln2 = fc.getString("Languages." + lang + ".2");
      ln3 = fc.getString("Languages." + lang + ".3");
      ln4 = fc.getString("Languages." + lang + ".4");
      ln5 = fc.getString("Languages." + lang + ".5");
      ln6 = fc.getString("Languages." + lang + ".6");
      ln7 = fc.getString("Languages." + lang + ".7");
      ln8 = fc.getString("Languages." + lang + ".8");
      System.out.println("Loaded Language: " + lang);
    } else {
      FileConfiguration fc = YamlConfiguration.loadConfiguration(langFile);
      String lang = fc.getString("Language");
      System.out.println("Loading Language: " + lang);
      Language = lang;
      ln1 = fc.getString("Languages." + lang + ".1");
      ln2 = fc.getString("Languages." + lang + ".2");
      ln3 = fc.getString("Languages." + lang + ".3");
      ln4 = fc.getString("Languages." + lang + ".4");
      ln5 = fc.getString("Languages." + lang + ".5");
      ln6 = fc.getString("Languages." + lang + ".6");
      ln7 = fc.getString("Languages." + lang + ".7");
      ln8 = fc.getString("Languages." + lang + ".8");
      System.out.println("Loaded Language: " + lang);
    }
  }
}