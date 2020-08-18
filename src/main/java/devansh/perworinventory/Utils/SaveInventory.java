package devansh.perworinventory.Utils;

import devansh.perworinventory.perworinventory;
import org.bukkit.entity.Player;

public class SaveInventory {
    private perworinventory plugin;

    public SaveInventory(perworinventory plugin){this.plugin = plugin;}

    public void saveInventory(Player player){
        player.getInventory().getContents();
    }


}
