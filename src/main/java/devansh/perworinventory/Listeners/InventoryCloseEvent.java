package devansh.perworinventory.Listeners;

import devansh.perworinventory.Utils.SaveInventory;
import devansh.perworinventory.perworinventory;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class InventoryCloseEvent implements Listener {
    private perworinventory plugin;
    public InventoryCloseEvent(perworinventory plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClose(org.bukkit.event.inventory.InventoryCloseEvent event){
        Player player = (Player) event.getPlayer();
        SaveInventory.addInventoryToHashMap(player, plugin);

    }
}
