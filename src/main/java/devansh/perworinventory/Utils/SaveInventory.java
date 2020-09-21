package devansh.perworinventory.Utils;

import devansh.perworinventory.perworinventory;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

public class SaveInventory {
    private perworinventory plugin;

    public SaveInventory(perworinventory plugin){this.plugin = plugin;}

    public static String storeItems(List<ItemStack> items){
        String encodedItems = "";
        try{
            ByteArrayOutputStream io = new ByteArrayOutputStream();
            BukkitObjectOutputStream os = new BukkitObjectOutputStream(io);
            if (items.size() == 0){
                encodedItems = "";
            }
            else {
                os.writeInt(items.size());
                for(int i = 0; i < items.size(); i++){
                    os.writeObject(items.get(i));
                }
                os.flush();
                byte[] rawData = io.toByteArray();
                encodedItems = Base64.getEncoder().encodeToString(rawData);
            }
        }catch (IOException e){
            System.out.println("Error Saving Player Inventory, Retrying!");
        }
        return encodedItems;
    }
    
    public static ItemStack[] decodeItems(String encodedItems){
        ArrayList<ItemStack> Items = new ArrayList<>();


        if(!encodedItems.equals("")){
            byte[] rawData = Base64.getDecoder().decode(encodedItems);

            try{
                ByteArrayInputStream in = new ByteArrayInputStream(rawData);
                BukkitObjectInputStream oi = new BukkitObjectInputStream(in);

                int ItemsCount = oi.readInt();

                for (int i = 0; i < ItemsCount; i++){
                    Items.add((ItemStack) oi.readObject());

                }
                oi.close();


            }catch (IOException | ClassNotFoundException e){
                e.printStackTrace();
            }
        }
        ItemStack[] stack = Items.toArray(new ItemStack[Items.size()]);
        return stack;
    }

    public static void addToWorldList(Player player, perworinventory plugin){
      String currentWorldInv =  SaveInventory.storeItems(Arrays.asList(player.getInventory().getContents()));
      if(plugin.playerWorldHashMap.containsKey(player.getUniqueId().toString())){
          ArrayList prevWorldList = plugin.playerWorldHashMap.get(player.getUniqueId().toString());
          prevWorldList.set(plugin.worldList.indexOf(player.getWorld().getName()),currentWorldInv);
          plugin.playerWorldHashMap.replace(player.getUniqueId().toString(),prevWorldList);
          player.sendMessage("h169");
      }
      else{
          ArrayList<String> prevWorldList = new ArrayList<>();
          for(int i = 0; i<plugin.worldList.size();i++) {
              prevWorldList.add("");
          }
          prevWorldList.set(plugin.worldList.indexOf(player.getWorld().getName()),currentWorldInv);
          plugin.playerWorldHashMap.put(player.getUniqueId().toString(),prevWorldList);
          player.sendMessage("hi70");
      }
    }
}
