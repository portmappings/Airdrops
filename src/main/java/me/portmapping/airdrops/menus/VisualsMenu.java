package me.portmapping.airdrops.menus;

import com.google.common.collect.Lists;
import me.portmapping.airdrops.builder.Airdrop;
import me.portmapping.airdrops.util.chat.CC;
import me.portmapping.airdrops.util.item.CompatibleMaterial;
import me.portmapping.airdrops.util.item.ItemBuilder;
import me.portmapping.airdrops.util.menu.Button;
import me.portmapping.airdrops.util.menu.Menu;
import me.portmapping.airdrops.util.menu.buttons.BackButton;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class VisualsMenu extends Menu {
    private Airdrop airdrop;

    public VisualsMenu(Airdrop airdrop){
        this.airdrop = airdrop;
        this.setPlaceholder(true);
    }


    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();
        buttons.put(11,new AirdropFireworkButton(airdrop));
        buttons.put(15 ,new HologramMenuButton(airdrop));
        buttons.put(18, new BackButton(new AirdropEditMenu(airdrop)));




        return buttons;
    }


    @Override
    public String getTitle(final Player player) {
        return airdrop.getName()+" Visuals";
    }


    @Override
    public int size(final Map<Integer, Button> buttons){
        return 9*3;
    }



    private class AirdropFireworkButton extends Button{
        private Airdrop airdrop;
        public AirdropFireworkButton(Airdrop airdrop){
            this.airdrop = airdrop;

        }

        @Override
        public ItemStack getButtonItem(final Player player){
            return new ItemBuilder(CompatibleMaterial.FIREWORK.getMaterial()).setName(CC.translate("&bFirework")).setLore(Lists.newArrayList(CC.translate("&7Click here manage the"),CC.translate("&7airdrop's firework"))).toItemStack();
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType) {

            player.closeInventory();
            new FireworkMenu(airdrop).openMenu(player);

        }


    }
    private class HologramMenuButton extends Button{
        private Airdrop airdrop;
        public HologramMenuButton(Airdrop airdrop){
            this.airdrop = airdrop;
        }

        @Override
        public ItemStack getButtonItem(final Player player){
            return new ItemBuilder(CompatibleMaterial.PAINTING.getMaterial()).setName(CC.translate("&bHologram")).setLore(Lists.newArrayList(CC.translate("&7Click here manage the"),CC.translate("&7airdrop's hologram"))).toItemStack();
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType) {

            player.closeInventory();
            new HologramMenu(airdrop).openMenu(player);

        }


    }


}
