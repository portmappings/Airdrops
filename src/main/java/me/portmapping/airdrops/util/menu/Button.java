package me.portmapping.airdrops.util.menu;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import me.portmapping.airdrops.util.chat.CC;
import me.portmapping.airdrops.util.sound.CompatibleSound;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.function.Consumer;

public abstract class Button {


    public static Button placeholder(Material material, byte data, String... title) {
        return placeholder(material, data, (title == null || title.length == 0) ? " " : Joiner.on(" ").join(title));
    }

    public static Button placeholder(final Material material) {
        return placeholder(material, " ");
    }

    public static Button placeholder(final Material material, final String title) {
        return placeholder(material, (byte) 0, title);
    }

    public static Button placeholder(final Material material, final byte data, final String title) {
        return new Button() {
            @Override
            public String getName(final Player player) {
                return title;
            }

            @Override
            public List<String> getDescription(final Player player) {
                return ImmutableList.of();
            }

            @Override
            public Material getMaterial(final Player player) {
                return material;
            }

            @Override
            public byte getDamageValue(final Player player) {
                return data;
            }
        };
    }

    public static Button fromItem(final ItemStack item) {
        return new Button() {
            @Override
            public ItemStack getButtonItem(final Player player) {
                return item;
            }

        };
    }

    public static Button fromItem(final ItemStack item, Consumer<Player> consumer) {
        return new Button() {
            @Override
            public ItemStack getButtonItem(final Player player) {
                return item;
            }

            @Override
            public void clicked(Player player, int slot, ClickType clickType) {
                if (consumer != null) {
                    consumer.accept(player);

                }
            }
        };
    }



    public String getName(Player player) {
        return "";
    }

    public List<String> getDescription(final Player player) {
        return null;
    }

    public Material getMaterial(final Player player) {
        return null;
    }

    public byte getDamageValue(Player player) {
        return 0;
    }

    public void clicked(Player player, int slot, ClickType clickType) {
    }

    public void clicked(InventoryClickEvent event) {

    }

    public boolean shouldCancel(Player player, int slot, ClickType clickType) {
        return true;
    }

    public int getAmount(Player player) {
        return 1;
    }

    public ItemStack getButtonItem(final Player player) {

        if (this.getMaterial(player) == null) {
            return new ItemStack(Material.AIR);
        }

        ItemStack buttonItem = new ItemStack(this.getMaterial(player), this.getAmount(player), this.getDamageValue(player));

        ItemMeta meta = buttonItem.getItemMeta();

        if (this.getName(player) != null) meta.setDisplayName(CC.translate(this.getName(player)));

        List<String> description = this.getDescription(player);

        if (description != null) {
            meta.setLore(CC.translate(description));
        }

        buttonItem.setItemMeta(meta);
        return buttonItem;
    }

    public static void playFail(final Player player) {
        CompatibleSound.DIG_GRASS.play(player);
    }

    public static void playSuccess(final Player player) {
        CompatibleSound.VILLAGER_YES.play(player);
    }

    public static void playNeutral(final Player player) {
        CompatibleSound.CLICK.play(player);
    }
}
