package me.simplecoder.aliaser;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import org.bukkit.Server;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class Events
  implements Listener
{
  Main plugin;
  
  public Events(Main pl)
  {
    this.plugin = pl;
  }
  
  @EventHandler(priority=EventPriority.HIGHEST)
  public void onPlayerCommand(PlayerCommandPreprocessEvent e)
  {
    ConfigurationSection l = this.plugin.getConfig().getConfigurationSection("aliases");
    Map<String, Object> a = new Hashtable();
    ArrayList<String> b = new ArrayList();
    for (String s : l.getKeys(false)) {
      b.add(s);
    }
    a = l.getValues(false);
    for (int i = 0; i < a.size(); i++)
    {
      String o = e.getMessage();
      String[] arr = o.split(" ", 2);
      if (arr[0].equalsIgnoreCase("/" + (String)b.get(i)))
      {
        String cmd = (String)a.get(b.get(i));
        cmd = cmd.replaceAll("%p", e.getPlayer().getName());
        String args = "";
        if (arr.length > 1) {
          for (int j = 1; j < arr.length; j++) {
            args = args + arr[j];
          }
        }
        args = args.replaceAll("%p", e.getPlayer().getName());
        System.out.print(cmd.replace("_", " ") + " " + args);
        this.plugin.getServer().dispatchCommand(e.getPlayer(), cmd.replace("_", " ") + " " + args);
        e.setCancelled(true);
      }
    }
  }
}

