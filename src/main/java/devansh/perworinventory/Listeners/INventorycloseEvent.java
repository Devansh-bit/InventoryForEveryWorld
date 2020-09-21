package devansh.perworinventory.Listeners;

import devansh.perworinventory.Utils.SaveInventory;
import devansh.perworinventory.perworinventory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class INventorycloseEvent implements Listener {
    private perworinventory plugin;
    public INventorycloseEvent(perworinventory plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event){
        Player player = (Player) event.getPlayer();
        player.sendMessage("hi");
        SaveInventory.addToWorldList(player, plugin);
    }
    @EventHandler
    public void onMove(PlayerMoveEvent event){
        Player player = (Player) event.getPlayer();
        player.sendMessage("hi");
        SaveInventory.addToWorldList(player, plugin);
    }
}
