package devansh.perworinventory;

import devansh.perworinventory.Utils.SaveInventory;
import org.bukkit.plugin.java.JavaPlugin;

public final class perworinventory extends JavaPlugin {

    @Override
    public void onEnable() {
        SaveInventory InventorysaveHandler = new SaveInventory(this);


    }

    @Override
    public void onDisable() {

    }
}
