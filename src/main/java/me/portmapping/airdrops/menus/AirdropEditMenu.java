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
import me.portmapping.airdrops.util.menu.menus.ConfirmMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.HashMap;
import java.util.Map;

public class AirdropEditMenu extends Menu {
    private Airdrop airdrop;

    public AirdropEditMenu(Airdrop airdrop){
        this.airdrop = airdrop;
    }


    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();

        buttons.put(13,new AirdropDisplayButton(airdrop));
        buttons.put(31,new IconButton(airdrop));
        buttons.put(30,new AirdropDisplayNameButton(airdrop));
        buttons.put(32,new AirdropLoreButton(airdrop));
        buttons.put(29, new AirdropRewardsButton(airdrop));
        buttons.put(45,new BackButton(new MainEditMenu()));
        buttons.put(53, new AirdropDeleteButton(airdrop));
        buttons.put(40,new AirdropDestroyTimeButton(airdrop));
        buttons.put(33,new AirdropVisualsButton(airdrop));


        return buttons;
    }


    @Override
    public String getTitle(final Player player) {
        return airdrop.getName();
    }


    @Override
    public int size(final Map<Integer, Button> buttons){
        return 9*6;
    }


    private class AirdropDisplayButton extends Button {

        private Airdrop airdrop;
        public AirdropDisplayButton(Airdrop airdrop){
            this.airdrop = airdrop;
        }

        @Override
        public ItemStack getButtonItem(final Player player){
            return new ItemBuilder(airdrop.getMaterial()).setName(airdrop.getDisplayname()).setLore(airdrop.getLore()).toItemStack();
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType) {

        }

    }
    private class IconButton extends Button{
        private Airdrop airdrop;
        public IconButton(Airdrop airdrop){
            this.airdrop = airdrop;
        }

        @Override
        public ItemStack getButtonItem(final Player player){
            if(airdrop.getMaterial().equals(Material.DISPENSER)){
                return new ItemBuilder(Material.ITEM_FRAME).setName(CC.translate("&bBlock")).setLore(
                        Lists.newArrayList("&b> &eDISPENSER",
                                                    "&7DROPPER"))
                        .toItemStack();
            }
                return new ItemBuilder(Material.ITEM_FRAME).setName(CC.translate("&bBlock")).setLore(
                                Lists.newArrayList("&7DISPENSER",
                                                            "&b> &eDROPPER"))
                        .toItemStack();


        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType) {


            if(airdrop.getMaterial().equals(Material.DISPENSER)){
                airdrop.setMaterial(Material.DROPPER);
                player.sendMessage(CC.translate("&aChanged the item to "+Material.DROPPER.name()));
                update(player);
                return;
            }
            airdrop.setMaterial(Material.DISPENSER);
            Airdrop.getAirdropMap().remove(airdrop.getName());
            Airdrop.getAirdropMap().put(airdrop.getName(),airdrop);
            update(player);
            player.sendMessage(CC.translate("&aChanged the item to "+Material.DISPENSER.name()));




        }

    }

    private class AirdropDisplayNameButton extends Button{
        private Airdrop airdrop;
        public AirdropDisplayNameButton(Airdrop airdrop){
            this.airdrop = airdrop;
        }

        @Override
        public ItemStack getButtonItem(final Player player){
            return new ItemBuilder(CompatibleMaterial.BOOK_AND_QUILL.getMaterial()).setName(CC.translate("&bDisplay Name")).setLore(Lists.newArrayList(CC.translate("&7Click here to change the"),CC.translate("&7airdrop's display name"))).toItemStack();
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType) {


            player.setMetadata("editdisplayname",new FixedMetadataValue(Airdrops.getInstance(),airdrop.getName()));
            player.sendMessage(CC.translate("&aInput the display name you want to put"));
            AirdropListeners.setAirdropname(airdrop.getName());
            player.closeInventory();

        }

    }

    private class AirdropLoreButton extends Button{
        private Airdrop airdrop;
        public AirdropLoreButton(Airdrop airdrop){
            this.airdrop = airdrop;
        }

        @Override
        public ItemStack getButtonItem(final Player player){
            return new ItemBuilder(Material.POWERED_RAIL).setName(CC.translate("&bLore")).setLore(Lists.newArrayList(CC.translate("&7Click here to change the"),CC.translate("&7airdrop's display lore/description"))).toItemStack();
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType) {


            player.setMetadata("editlore",new FixedMetadataValue(Airdrops.getInstance(),airdrop.getName()));
            AirdropListeners.setAirdropname(airdrop.getName());
            AirdropListeners.sendLoreUsage(player);
            player.closeInventory();

        }

    }

    private class AirdropRewardsButton extends Button{
        private Airdrop airdrop;
        public AirdropRewardsButton(Airdrop airdrop){
            this.airdrop = airdrop;
        }

        @Override
        public ItemStack getButtonItem(final Player player){
            return new ItemBuilder(Material.DIAMOND).setName(CC.translate("&bRewards")).setLore(Lists.newArrayList(CC.translate("&7Click here to manage the"),CC.translate("&7airdrop's rewards"))).toItemStack();
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType) {

            player.closeInventory();
            new RewardsInventory(airdrop).openMenu(player);

        }


    }
    private class AirdropDeleteButton extends Button{
        private Airdrop airdrop;
        public AirdropDeleteButton(Airdrop airdrop){
            this.airdrop = airdrop;
        }

        @Override
        public ItemStack getButtonItem(final Player player){
            return new ItemBuilder(Material.REDSTONE_BLOCK).setName(CC.translate("&c&LDELETE")).setLore(Lists.newArrayList("&7Click to delete the airdrop")).toItemStack();
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType) {

            new ConfirmMenu("Are you sure?", data -> {
                if (data) {
                    player.closeInventory();
                    airdrop.delete();
                    player.sendMessage(CC.GREEN+"Successfully deleted airdrop");
                }else{
                    player.closeInventory();
                    new AirdropEditMenu(airdrop).openMenu(player);
                }
            }, true, this).openMenu(player);


        }


    }

    private class AirdropVisualsButton extends Button{
        private Airdrop airdrop;
        public AirdropVisualsButton(Airdrop airdrop){
            this.airdrop = airdrop;
        }

        @Override
        public ItemStack getButtonItem(final Player player){
            return new ItemBuilder(CompatibleMaterial.EXP_BOTTLE.getMaterial()).setName(CC.translate("&bVisuals")).setLore(Lists.newArrayList(CC.translate("&7Click here manage the"),CC.translate("&7airdrop's visual stuff"))).toItemStack();
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType) {

            player.closeInventory();
            new VisualsMenu(airdrop).openMenu(player);

        }


    }
    private class AirdropDestroyTimeButton extends Button{
        private Airdrop airdrop;
        public AirdropDestroyTimeButton(Airdrop airdrop){
            this.airdrop = airdrop;
        }

        @Override
        public ItemStack getButtonItem(final Player player){
            return new ItemBuilder(CompatibleMaterial.WATCH.getMaterial()).setName(CC.translate("&bDestroy Time &e= &b"+airdrop.getDestroytime()+" seconds")).setLore(Lists.newArrayList(CC.translate("&7Click here to edit the time that"),CC.translate("&7takes for the airdrop to destroy"))).toItemStack();
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType) {

            player.closeInventory();
            player.setMetadata("editdestroytime",new FixedMetadataValue(Airdrops.getInstance(),airdrop.getName()));
            AirdropListeners.setAirdropname(airdrop.getName());
            player.sendMessage(CC.translate("&aPlease input the time in seconds or type &ccancel &ato exit this process"));

        }


    }
}
