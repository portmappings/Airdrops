package me.portmapping.airdrops.menus;

import com.google.common.collect.Lists;
import me.portmapping.airdrops.Airdrops;
import me.portmapping.airdrops.builder.Airdrop;
import me.portmapping.airdrops.listeners.AirdropListeners;
import me.portmapping.airdrops.util.chat.CC;
import me.portmapping.airdrops.util.item.CompatibleMaterial;
import me.portmapping.airdrops.util.item.ItemBuilder;
import me.portmapping.airdrops.util.menu.Button;
import me.portmapping.airdrops.util.menu.Menu;
import me.portmapping.airdrops.util.menu.buttons.BackButton;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.HashMap;
import java.util.Map;

public class HologramMenu extends Menu {

    private Airdrop airdrop;
    public HologramMenu(Airdrop airdrop){
        this.airdrop = airdrop;
        this.setPlaceholder(true);
    }
    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer,Button> buttons = new HashMap<>();


        buttons.put(12,new HologramEnabledButton(airdrop));
        buttons.put(13,new HologramMainLineButton(airdrop));
        buttons.put(14, new HologramSecondLineButton(airdrop));


        buttons.put(18, new BackButton(new VisualsMenu(airdrop)));



        return buttons;
    }
    @Override
    public int size(final Map<Integer, Button> buttons){
        return 9 * 3;
    }

    @Override
    public String getTitle(final Player player) {
        return "Edit Rewards";
    }

    private class HologramEnabledButton extends Button{
        private Airdrop airdrop;
        public HologramEnabledButton(Airdrop airdrop){
            this.airdrop = airdrop;
        }

        @Override
        public ItemStack getButtonItem(final Player player){
            if(airdrop.hologramEnabled){
                return new ItemBuilder
                        (CompatibleMaterial.INK_SACK.getMaterial())
                        .setName(CC.translate("&aEnabled"))
                        .setDurability(10)
                        .setLore(Lists.newArrayList(
                                CC.translate("&7Click to toggle"),
                                CC.translate("&7the hologram")
                        ))
                        .toItemStack();
            }else {
                return new ItemBuilder
                        (CompatibleMaterial.INK_SACK.getMaterial())
                        .setName(CC.translate("&cDisabled"))
                        .setDurability(8)
                        .setLore(Lists.newArrayList(
                                CC.translate("&7Click to toggle"),
                                CC.translate("&7the hologram")
                                ))
                        .toItemStack();
            }





        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType) {

            airdrop.setHologramEnabled(!airdrop.isHologramEnabled());
            new HologramMenu(airdrop).openMenu(player);



        }


    }

    private class HologramMainLineButton extends Button{
        private Airdrop airdrop;
        public HologramMainLineButton(Airdrop airdrop){
            this.airdrop = airdrop;
        }

        @Override
        public ItemStack getButtonItem(final Player player){

            return new ItemBuilder
                    (CompatibleMaterial.SIGN.getMaterial())
                    .setName(CC.translate("&bMain Line"))
                    .setLore(Lists.newArrayList(airdrop.getMainHoloLine(),"",CC.translate("&7Click to edit the main line")))
                    .toItemStack();
        }




        @Override
        public void clicked(Player player, int slot, ClickType clickType) {

            player.closeInventory();
            AirdropListeners.setAirdropname(airdrop.getName());
            player.setMetadata("editmainline",new FixedMetadataValue(Airdrops.getInstance(),airdrop.getName()));
            player.sendMessage(CC.translate("&aInput the text you want the main line to be or type &ccancel to exit this process"));

        }

    }

    private class HologramSecondLineButton extends Button{
        private Airdrop airdrop;
        public HologramSecondLineButton(Airdrop airdrop){
            this.airdrop = airdrop;
        }

        @Override
        public ItemStack getButtonItem(final Player player){

            return new ItemBuilder
                    (CompatibleMaterial.SIGN.getMaterial())
                    .setName(CC.translate("&bSecond Line"))
                    .setLore(Lists.newArrayList(airdrop.getSecondHoloLine(),"",CC.translate("&7Click to edit the second line")))
                    .toItemStack();
        }




        @Override
        public void clicked(Player player, int slot, ClickType clickType) {

            player.closeInventory();
            AirdropListeners.setAirdropname(airdrop.getName());
            player.setMetadata("editsecondline",new FixedMetadataValue(Airdrops.getInstance(),airdrop.getName()));
            player.sendMessage(CC.translate("&aInput the text you want the main line to be or type &ccancel &ato exit this process"));

        }

    }



}



