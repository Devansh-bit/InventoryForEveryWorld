package devansh.perworinventory;

import devansh.perworinventory.Utils.SaveInventory;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
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

    @Override
    public void onEnable() {
        File file = new File(getDataFolder(), "Playerinventories.yml");
        System.out.println("hi4");
        saveDefaultConfig();
        //YamlConfiguration playerinventories = YamlConfiguration.loadConfiguration(file);
        SaveInventory InventorysaveHandler = new SaveInventory(this);
        if (this.getDataFolder().exists()){
            System.out.println("hi1");
            if (!file.exists()){
                System.out.println("hi2");
                try {
                    file.createNewFile();
                }catch (IOException e){
                    e.printStackTrace();
                }
                file.getParentFile().mkdirs();
                saveResource("Playerinventories.yml", false);
            }

                System.out.println("hi3");
                FileConfiguration playerinventories = new YamlConfiguration();
                try{
                    playerinventories.load(file);
                }catch (IOException | InvalidConfigurationException e){
                    e.printStackTrace();
                }
                for(Player player: getServer().getOnlinePlayers()){
                    playerinventories.getConfigurationSection(player.getUniqueId().toString());

            }
        }



    }

    @Override
    public void onDisable() {
        for(Player player : this.getServer().getOnlinePlayers()){
            HashMap<String, String> inventoryperworld = new HashMap<>();
            String items = SaveInventory.storeItems(Arrays.asList(player.getInventory().getContents()));
            inventoryperworld.put(player.getWorld().getName(), items);
            this.playerWorldHashMap.put(player.getUniqueId().toString(), inventoryperworld);
            File file = new File(getDataFolder(), "Playerinventories.yml");
            FileConfiguration playerinventories = new YamlConfiguration();
            try{
                playerinventories.load(file);
            }catch (IOException | InvalidConfigurationException e){
                e.printStackTrace();
            }
            for (Map.Entry<String, HashMap> entry: playerWorldHashMap.entrySet()) {
                playerinventories.set(entry.getKey(), entry.getValue());

            }
            try {
                playerinventories.save(file);
            }catch (IOException e){
                e.printStackTrace();
            }



        }

    }
}
