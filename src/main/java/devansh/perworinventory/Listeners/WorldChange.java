package devansh.perworinventory.Listeners;

import devansh.perworinventory.Utils.SaveInventory;
import devansh.perworinventory.perworinventory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class WorldChange implements Listener {
    private perworinventory plugin;
    public WorldChange(perworinventory plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onchangeWorld(PlayerChangedWorldEvent event){
        Player player = event.getPlayer();
        System.out.println("hi");
        player.getInventory().setContents(SaveInventory.decodeItems(plugin.playerWorldHashMap.get(player.getUniqueId().toString()).get(plugin.worldList.indexOf(player.getWorld().getName()))));
        SaveInventory.addToWorldList(player, plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        player.getInventory().setContents(SaveInventory.decodeItems(plugin.playerWorldHashMap.get(player.getUniqueId().toString()).get(plugin.worldList.indexOf(player.getWorld().getName()))));
        SaveInventory.addToWorldList(player, plugin);
    }
}
