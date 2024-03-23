package me.portmapping.airdrops.menus;

import me.portmapping.airdrops.Airdrops;
import me.portmapping.airdrops.builder.Airdrop;
import me.portmapping.airdrops.builder.Reward;
import me.portmapping.airdrops.listeners.AirdropListeners;
import me.portmapping.airdrops.menus.AirdropEditMenu;
import me.portmapping.airdrops.util.chat.CC;
import me.portmapping.airdrops.util.item.CompatibleMaterial;
import me.portmapping.airdrops.util.item.ItemBuilder;
import me.portmapping.airdrops.util.menu.Button;
import me.portmapping.airdrops.util.menu.Menu;
import me.portmapping.airdrops.util.menu.buttons.BackButton;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.HashMap;
import java.util.Map;

public class RewardsInventory extends Menu {

    private Airdrop airdrop;
    public RewardsInventory(Airdrop airdrop){
        this.airdrop = airdrop;
    }
    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer,Button> buttons = new HashMap<>();

        for(int i = 0 ; i<airdrop.getRewards().size(); i++){
            Button button = new RewardButton(airdrop, airdrop.getRewards().get(i));
            buttons.put(i,button);
        }

        for (int i = 46; i<54;i++){
            buttons.put(i,Button.placeholder(CompatibleMaterial.STAINED_GLASS_PANE.getMaterial(), (byte) 15));
        }

        buttons.put(45, new BackButton(new AirdropEditMenu(airdrop)));



        return buttons;
    }
    @Override
    public int size(final Map<Integer, Button> buttons){
        return 9 * 6;
    }

    @Override
    public String getTitle(final Player player) {
        return "Edit Rewards";
    }



    private class RewardButton extends Button{
        private Airdrop airdrop;
        private Reward reward;
        public RewardButton(Airdrop airdrop, Reward reward){
            this.airdrop = airdrop;
            this.reward = reward;
        }

        @Override
        public ItemStack getButtonItem(final Player player){
            if(reward.isLoreEnabled() && reward.isEnchantmentsEnabled()) {
                return new ItemBuilder
                        (reward.getMaterial(),reward.getAmount())
                        .setDurability(reward.getData())
                        .setLore(reward.getLore())
                        .setName(reward.getName())
                        .addUnsafeEnchantments(reward.getEnchantments())
                        .addLoreLine(CC.GRAY+CC.STRIKE_THROUGH+"---------------------------")
                        .addLoreLine(CC.translate("&cLeft-Click to edit the chances"))
                        .addLoreLine(CC.translate("&cRight-Click to remove this reward"))
                        .addLoreLine(CC.translate("&c&lChance: &f"+reward.getChance()+"%"))
                        .addLoreLine(CC.GRAY+CC.STRIKE_THROUGH+"---------------------------").toItemStack();

            }
            if(reward.isLoreEnabled() && !reward.isEnchantmentsEnabled()){

                    return new ItemBuilder
                            (reward.getMaterial(),reward.getAmount())
                            .setDurability(reward.getData())
                            .setName(reward.getName().toString())
                            .setLore(reward.getLore())
                            .addLoreLine(CC.GRAY+CC.STRIKE_THROUGH+"---------------------------")
                            .addLoreLine(CC.translate("&cLeft-Click to edit the chances"))
                            .addLoreLine(CC.translate("&cRight-Click to remove this reward"))
                            .addLoreLine(CC.translate("&c&lChance: &f"+reward.getChance()+"%"))
                            .addLoreLine(CC.GRAY+CC.STRIKE_THROUGH+"---------------------------").toItemStack();
            }
            if(!reward.isLoreEnabled() && reward.isEnchantmentsEnabled()){

                    return new ItemBuilder
                            (reward.getMaterial(),reward.getAmount())
                            .setDurability(reward.getData())
                            .setName(reward.getName())
                            .addUnsafeEnchantments(reward.getEnchantments())
                            .addLoreLine(CC.GRAY+CC.STRIKE_THROUGH+"---------------------------")
                            .addLoreLine(CC.translate("&cLeft-Click to edit the chances"))
                            .addLoreLine(CC.translate("&cRight-Click to remove this reward"))
                            .addLoreLine(CC.translate("&c&lChance: &f"+reward.getChance()+"%"))
                            .addLoreLine(CC.GRAY+CC.STRIKE_THROUGH+"---------------------------").toItemStack();

            }

                return new ItemBuilder
                        (reward.getMaterial(),reward.getAmount())
                        .setDurability(reward.getData())
                        .setName(reward.getName())
                        .addLoreLine(CC.GRAY+CC.STRIKE_THROUGH+"---------------------------")
                        .addLoreLine(CC.translate("&cLeft-Click to edit the chances"))
                        .addLoreLine(CC.translate("&cRight-Click to remove this reward"))
                        .addLoreLine(CC.translate("&c&lChance: &f"+reward.getChance()+"%"))
                        .addLoreLine(CC.GRAY+CC.STRIKE_THROUGH+"---------------------------").toItemStack();




        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType) {

            if(clickType.isRightClick()){
                airdrop.getRewards().remove(slot);
                Airdrop.getAirdropMap().remove(airdrop.getName());
                Airdrop.getAirdropMap().put(airdrop.getName(),airdrop);
                new RewardsInventory(airdrop).openMenu(player);
                return;
            }
            if(clickType.isLeftClick()){
                player.closeInventory();
                player.setMetadata("editchance",new FixedMetadataValue(Airdrops.getInstance(),airdrop.getName()));
                player.sendMessage(CC.translate("&aPlease input the chance for the reward [1-100]"));
                AirdropListeners.setSlot(slot);
                AirdropListeners.setAirdropname(airdrop.getName());
            }

        }

    }

}

