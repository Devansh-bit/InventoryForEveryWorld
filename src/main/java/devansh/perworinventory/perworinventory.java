package devansh.perworinventory;

import devansh.perworinventory.Utils.SaveInventory;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public final class perworinventory extends JavaPlugin {
    public HashMap<String, HashMap> playerWorldHashMap = new HashMap<>();
    public HashMap<World, String> inventoryperworld = new HashMap<>();

    @Override
    public void onEnable() {
        File file = new File("PlayerInventories.yml");
        //YamlConfiguration playerinventories = YamlConfiguration.loadConfiguration(file);
        SaveInventory InventorysaveHandler = new SaveInventory(this);
        if (this.getDataFolder().exists()){
            if (!file.exists()){
                try {
                    file.createNewFile();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            else{
                YamlConfiguration playerinventories = YamlConfiguration.loadConfiguration(file);
            }
        }

    }

    @Override
    public void onDisable() {
        for(Player player : this.getServer().getOnlinePlayers()){
            String items = SaveInventory.storeItems(Arrays.asList(player.getInventory().getContents()));
            this.inventoryperworld.put(player.getWorld(), items);
            this.playerWorldHashMap.put(player.getUniqueId().toString(), inventoryperworld);
            File file = new File("PlayerInventories.yml");
            YamlConfiguration playerinventories = YamlConfiguration.loadConfiguration(file);
            for (Map.Entry<String, HashMap> entry: playerWorldHashMap.entrySet()) {
                playerinventories.set(entry.getKey(), entry.getValue());
            }



        }

    }
}
