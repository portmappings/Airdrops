package me.portmapping.airdrops.util.item;

import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;

@UtilityClass
public class InventoryUtils {

    public int getSlot(int row, int column) {
        return (row * 9) + column;
    }

    public boolean isBorder(int slot) {
        return slot % 9 == 0 || slot % 9 == 8;
    }


}
