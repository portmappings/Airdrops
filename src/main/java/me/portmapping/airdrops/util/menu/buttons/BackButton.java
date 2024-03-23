package me.portmapping.airdrops.util.menu.buttons;

import lombok.AllArgsConstructor;
import me.portmapping.airdrops.util.chat.CC;
import me.portmapping.airdrops.util.item.CompatibleMaterial;
import me.portmapping.airdrops.util.item.ItemBuilder;
import me.portmapping.airdrops.util.menu.Button;
import me.portmapping.airdrops.util.menu.Menu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class BackButton extends Button {

    private Menu back;

    @Override
    public ItemStack getButtonItem(final Player player) {
        return new ItemBuilder(CompatibleMaterial.INK_SACK.getMaterial()).setDurability(1).setName(CC.translate("&cGo Back")).toItemStack();
    }


    @Override
    public void clicked(final Player player, final int i, final ClickType clickType) {
        Button.playNeutral(player);
        this.back.openMenu(player);
    }
}
