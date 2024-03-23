package me.portmapping.airdrops.util.item;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemUtils {

    private static final Map<String, ItemData> NAME_MAP;



    public static String getEnchants(ItemStack i) {
        List<String> e = new ArrayList<String>();
        Map<Enchantment, Integer> en = i.getEnchantments();
        for (Enchantment t : en.keySet()) {
            e.add(t.getName() + ":" + en.get(t));
        }
        return StringUtils.join(e, ",");
    }

    public static String serialize(ItemStack i) {
        String[] parts = new String[6];
        parts[0] = i.getType().name();
        parts[1] = Integer.toString(i.getAmount());
        parts[2] = String.valueOf(i.getDurability());
        parts[3] = i.getItemMeta().getDisplayName();
        parts[4] = String.valueOf(i.getData().getData());
        parts[5] = getEnchants(i);
        return StringUtils.join(parts, ";");
    }

    public static ItemStack deSerialized(String p) {
        String[] a = p.split(";");
        ItemStack i = new ItemStack(Material.getMaterial(a[0]), Integer.parseInt(a[1]));
        i.setDurability((short) Integer.parseInt(a[2]));
        ItemMeta meta = i.getItemMeta();
        meta.setDisplayName(a[3]);
        i.setItemMeta(meta);
        MaterialData data = i.getData();
        data.setData((byte) Integer.parseInt(a[4]));
        i.setData(data);
        if (a.length > 5) {
            String[] parts = a[5].split(",");
            for (String s : parts) {
                String label = s.split(":")[0];
                String amplifier = s.split(":")[1];
                Enchantment type = Enchantment.getByName(label);
                if (type == null)
                    continue;
                int f;
                try {
                    f = Integer.parseInt(amplifier);
                } catch (Exception ex) {
                    continue;
                }
                i.addEnchantment(type, f);
            }
        }
        return i;
    }

    public static void setDisplayName(final ItemStack itemStack, final String name) {
        final ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemStack.setItemMeta(itemMeta);
    }









    static {
        NAME_MAP = new HashMap<String, ItemData>();
    }

    public static class ItemData {
        private final Material material;
        private final short data;



        public boolean matches(final ItemStack item) {
            return item != null && item.getType() == this.material && item.getDurability() == this.data;
        }

        public ItemStack toItemStack() {
            return new ItemStack(this.material, 1, this.data);
        }

        public Material getMaterial() {
            return this.material;
        }

        public short getData() {
            return this.data;
        }

        public ItemData(final Material material, final short data) {
            this.material = material;
            this.data = data;
        }
    }
}