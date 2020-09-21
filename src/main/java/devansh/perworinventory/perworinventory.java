package devansh.perworinventory;


import devansh.perworinventory.Listeners.INventorycloseEvent;
import devansh.perworinventory.Listeners.WorldChange;
import devansh.perworinventory.Utils.SaveInventory;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public final class perworinventory extends JavaPlugin {
    public HashMap<String, ArrayList<String>> playerWorldHashMap = new HashMap<>();
    public ArrayList<String> worldList= new ArrayList<>();


    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new INventorycloseEvent(this), this);
        getServer().getPluginManager().registerEvents(new WorldChange(this), this);
        for(World world : Bukkit.getWorlds()){
            worldList.add(world.getName());
        }
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
                    playerWorldHashMap.put(player.getUniqueId().toString(), (ArrayList<String>) playerinventories.getStringList(player.getUniqueId().toString()));
                    player.getInventory().setContents(SaveInventory.decodeItems(playerWorldHashMap.get(player.getUniqueId().toString()).get(worldList.indexOf(player.getWorld().getName()))));
            }
        }



    }

    @Override
    public void onDisable() {
        for(Player player:this.getServer().getOnlinePlayers()){
            String encodedItems = SaveInventory.storeItems(Arrays.asList(player.getInventory().getContents()));
            SaveInventory.addToWorldList(player,this);
            player.getInventory().clear();
        }
        File file = new File(getDataFolder(), "Playerinventories.yml");
        FileConfiguration playerinventories = new YamlConfiguration();
        try{
            playerinventories.load(file);
        }catch (IOException | InvalidConfigurationException e){
            e.printStackTrace();
        }
        for (Map.Entry<String, ArrayList<String>> entry: playerWorldHashMap.entrySet()) {
            playerinventories.set(entry.getKey(), entry.getValue());
        }
        try {
            playerinventories.save(file);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    /*for(Player player : this.getServer().getOnlinePlayers()){
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



        }*/
}
