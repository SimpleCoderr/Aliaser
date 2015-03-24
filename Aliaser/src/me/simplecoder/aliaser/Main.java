package me.simplecoder.aliaser;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main
  extends JavaPlugin
{
  public void onEnable()
  {
    saveDefaultConfig();
    getServer().getPluginManager().registerEvents(new Events(this), this);
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    if (cmd.getName().equalsIgnoreCase("aliaser"))
    {
      sender.sendMessage(ChatColor.BLUE + "==== Aliaser ====");
      if (args.length == 0) {
        sender.sendMessage(ChatColor.RED + " - Subcommand not found, try /aliaser help!");
      } else if (args.length == 1)
      {
        if ((args[0].equalsIgnoreCase("help")) || (args[0].equalsIgnoreCase("?")))
        {
          sender.sendMessage(ChatColor.GREEN + " - add <alias> <command> | Adds the alias <alias>, for the command <command>!");
          sender.sendMessage(ChatColor.GREEN + " - edit <alias> <command> | Edits the alias <alias>, setting it to the command <command>!");
          sender.sendMessage(ChatColor.GREEN + " - remove <alias> | Removes the alias <alias>");
          sender.sendMessage(ChatColor.GREEN + " - list | Lists all found aliases!");
          sender.sendMessage(ChatColor.GREEN + " - reload | Reloads the configuration!");
        }
        else if (args[0].equalsIgnoreCase("list"))
        {
          sender.sendMessage(ChatColor.GREEN + " Loaded aliases:");
          ConfigurationSection l = getConfig().getConfigurationSection("aliases");
          Map<String, Object> a = new Hashtable();
          ArrayList<String> b = new ArrayList();
          for (String s : l.getKeys(false)) {
            b.add(s);
          }
          a = l.getValues(false);
          for (int i = 0; i < a.size(); i++)
          {
            String cmd2 = (String)a.get(b.get(i));
            sender.sendMessage(ChatColor.GOLD + "  /" + (String)b.get(i) + ChatColor.LIGHT_PURPLE + " points to " + ChatColor.YELLOW + "/" + cmd2.replace("_", " "));
          }
        }
        else if (args[0].equalsIgnoreCase("reload"))
        {
          reloadConfig();
          sender.sendMessage(ChatColor.YELLOW + "  Configuration reloaded!");
        }
        else
        {
          sender.sendMessage(ChatColor.RED + " - Subcommand not found, try /aliaser help!");
        }
      }
      else if (args.length == 2)
      {
        if (args[0].equalsIgnoreCase("remove"))
        {
          getConfig().set("aliases." + args[1], null);
          saveConfig();
          sender.sendMessage(ChatColor.GREEN + "  Alias " + args[1] + " removed!");
        }
        else
        {
          sender.sendMessage(ChatColor.RED + " - Subcommand not found, try /aliaser help!");
        }
      }
      else if (args.length == 3)
      {
        if (args[0].equalsIgnoreCase("add"))
        {
          getConfig().set("aliases." + args[1], args[2]);
          saveConfig();
          sender.sendMessage(ChatColor.GREEN + "  Alias " + args[1] + " added, and pointing to command " + args[2] + "!");
        }
        else if (args[0].equalsIgnoreCase("edit"))
        {
          if (getConfig().getString("aliases." + args[1]) != null)
          {
            getConfig().set("aliases." + args[1], args[2]);
            saveConfig();
            sender.sendMessage(ChatColor.GOLD + "  /" + args[1] + ChatColor.LIGHT_PURPLE + "now points to " + ChatColor.YELLOW + "/" + args[2]);
          }
          else
          {
            sender.sendMessage(ChatColor.RED + " Alias not found!");
          }
        }
        else
        {
          sender.sendMessage(ChatColor.RED + " - Subcommand not found, try /aliaser help!");
        }
      }
      else {
        sender.sendMessage(ChatColor.RED + " - Subcommand not found, try /aliaser help!");
      }
      return true;
    }
    return false;
  }
}

