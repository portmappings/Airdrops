package me.portmapping.airdrops.builder;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.Setter;
import me.portmapping.airdrops.Airdrops;
import me.portmapping.airdrops.util.chat.CC;
import me.portmapping.airdrops.util.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter@Setter
public class Reward {


    private String name;
    private Material material;
    private int amount;
    private short data;
    private int chance;
    private boolean loreEnabled;
    private List<String> lore;
    private boolean enchantmentsEnabled;
    private Map<Enchantment, Integer> enchantments;

    public Reward(@Nullable String name, Material material, int amount, short data, int chance
            , boolean loreEnabled, List<String> lore, boolean enchantmentsEnabled, Map<Enchantment, Integer> enchantments){

        this.name = name;
        this.material = material;
        this.amount = amount;
        this.data = data;
        this.chance = chance;
        this.loreEnabled = loreEnabled;
        this.lore = lore;
        this.enchantmentsEnabled = enchantmentsEnabled;
        this.enchantments = enchantments;


    }

    public ItemStack toItemStack(){
        if (name == null) {
            if(loreEnabled && enchantmentsEnabled){
                return new ItemBuilder(material,amount).setDurability(data).setLore(lore).addUnsafeEnchantments(enchantments).toItemStack();
            }

            if(loreEnabled){
                return new ItemBuilder(material,amount).setDurability(data).setLore(lore).toItemStack();
            }

            if(enchantmentsEnabled){
                return new ItemBuilder(material,amount).setDurability(data).addUnsafeEnchantments(enchantments).toItemStack();
            }

        }else {

            if(loreEnabled && enchantmentsEnabled){
                return new ItemBuilder(material,amount).setDurability(data).setName(name).setLore(lore).addUnsafeEnchantments(enchantments).toItemStack();
            }

            if(loreEnabled){
                return new ItemBuilder(material,amount).setDurability(data).setName(name).setLore(lore).toItemStack();
            }

            if(enchantmentsEnabled){
                return new ItemBuilder(material,amount).setDurability(data).setName(name).addUnsafeEnchantments(enchantments).toItemStack();
            }


        }
        return new ItemBuilder(material,amount).setDurability(data).toItemStack();

    }

    private Map<Enchantment, Integer> parseEnchantments(List<String> enchants){
        Map<Enchantment, Integer> enchantmentIntegerMap = Maps.newHashMap();
       for(String string : enchants){
           String[] split = string.split("[:]",0);

           enchantmentIntegerMap.put(Enchantment.getByName(split[0]),Integer.parseInt(split[1]));
       }
       return enchantmentIntegerMap;
    }
    public static Map<Enchantment, Integer> parseEnchantmentsStatic(List<String> enchants){
        Map<Enchantment, Integer> enchantmentIntegerMap = Maps.newHashMap();
        for(String string : enchants){
            String[] split = string.split("[:]",0);

            enchantmentIntegerMap.put(Enchantment.getByName(split[0]),Integer.parseInt(split[1]));
        }
        return enchantmentIntegerMap;
    }
    public static List<String> parseToList(Map<Enchantment, Integer> enchants){
        List<String> enchantToList = Lists.newArrayList();
        for(Map.Entry<Enchantment, Integer> entry : enchants.entrySet()) {
            String enchantname = entry.getKey().getName();
            int level = entry.getValue();
            enchantToList.add(enchantname+":"+level);

        }

        return enchantToList;
    }

    public static Reward exampleReward(){
        List<String> lorelist = Lists.newArrayList();
        lorelist.add(CC.GRAY+"This is an example reward!");

        List<String> enchantmentslist = Lists.newArrayList();
        enchantmentslist.add("DURABILITY:5");


        return new Reward("&eExample",Material.DIAMOND,64,(short) 0,50,true, lorelist,true,parseEnchantmentsStatic(enchantmentslist));
    }

}
