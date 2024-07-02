package inventory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileIO {
    private static final String FILE_NAME = "inventory.txt";

    public static List<InventoryItem> readInventory() {
        List<InventoryItem> inventory = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                InventoryItem item = new InventoryItem(parts[0], parts[1], Integer.parseInt(parts[2]));
                inventory.add(item);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inventory;
    }

    public static void writeInventory(List<InventoryItem> inventory) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (InventoryItem item : inventory) {
                bw.write(item.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
