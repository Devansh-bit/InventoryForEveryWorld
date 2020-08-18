package devansh.perworinventory.Utils;

import devansh.perworinventory.perworinventory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

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

        }
        return encodedItems;
    }

    public static ArrayList<ItemStack> decodeItems(String encodedItems){
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
        return Items;
    }
}
