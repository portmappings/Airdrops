package me.portmapping.airdrops.commands;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import me.portmapping.airdrops.builder.Airdrop;
import me.portmapping.airdrops.builder.Reward;
import me.portmapping.airdrops.listeners.AirdropListeners;
import me.portmapping.airdrops.menus.MainEditMenu;
import me.portmapping.airdrops.util.chat.CC;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class AirdropCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if(!command.getName().equalsIgnoreCase("airdrop")){
            return false;
        }

        if(!(commandSender instanceof Player)){
            commandSender.sendMessage(CC.RED+"This is a only player command");
            return false;
        }


        if(args.length==0){
            sendUsage(commandSender);
            return false;
        }



        if(args.length>0){
            Player player = (Player) commandSender;
            if(args[0].equalsIgnoreCase("create")){
                if(!(args.length>=2)){
                    sendUsage(player);
                    return false;
                }
                if(Airdrop.getAirdropMap().get(args[1])!=null){
                    player.sendMessage(CC.RED+"This airdrop already exists");
                    return false;
                }
                List<Reward> rewards = Lists.newArrayList();
                rewards.add(Reward.exampleReward());
                Airdrop airdrop = new Airdrop(args[1],args[1], Material.DISPENSER,rewards, Lists.newArrayList(CC.translate("&7Right click to place and get rewards")),30,1, Color.AQUA, FireworkEffect.Type.STAR,true,true,"<name>","Destroying in <countdown> seconds");
                player.sendMessage(CC.translate("&aCreated a new airdrop named "+airdrop.getName()));
                airdrop.save();
                return false;

            }

            if(args[0].equalsIgnoreCase("edit")){

                new MainEditMenu().openMenu(player);
                player.sendMessage(CC.GREEN+"Opening airdrop edit menu...");
            }
            if(args[0].equalsIgnoreCase("delete")){
                if(!(args.length>=2)){
                    sendUsage(player);
                    return false;
                }
                if(Airdrop.getByName(args[1])==null){
                    player.sendMessage(CC.RED+"This airdrop does not exists");
                    return false;
                }
                Airdrop.getByName(args[1]).delete();
                player.sendMessage(CC.translate("&aSuccessfully deleted airdrop"));
                return false;
            }
            if(args[0].equalsIgnoreCase("give")){
                if(!(args.length>=4)){
                    sendUsage(player);
                    return false;
                }
                if(Airdrop.getAirdropMap().get(args[1])==null){
                player.sendMessage(CC.RED+"This airdrop does not exists");
                return false;
                }
                Player target = Bukkit.getPlayer(args[2]);
                if(target==null){
                    player.sendMessage(CC.RED+"This player is not online");
                    return false;
                }
                if(!AirdropListeners.isNumeric(args[3])){
                    player.sendMessage(CC.RED+"Please input an integer for the amount");
                    return false;
                }
                Airdrop airdrop = Airdrop.getByName(args[1]);
                ItemStack item = airdrop.toItemStack();
                item.setAmount(Integer.parseInt(args[3]));
                target.getInventory().addItem(item);
                player.sendMessage(CC.GREEN+"Gave "+player.getName()+" x"+Integer.parseInt(args[3])+" "+airdrop.getName()+" Airdrops");
            }
            if(args[0].equalsIgnoreCase("addreward")){
                if(!(args.length>=3)){
                    sendUsage(player);
                    return false;
                }
                Airdrop airdrop = Airdrop.getByName(args[1]);
                if(airdrop==null){
                    player.sendMessage(CC.RED+"This airdrop does not exists");
                    return false;
                }
                if(!AirdropListeners.isNumeric(args[2])){
                    player.sendMessage(CC.RED+"Please input an integer for the chances");
                    return false;
                }

                if(Integer.parseInt(args[2])>100) {
                    player.sendMessage(CC.RED + "Please input a number below 100");
                    return false;
                }
                if(airdrop.getRewards().size()==45){
                    player.sendMessage(CC.RED+airdrop.getName()+" already has the maximum possible rewards");
                    return false;
                }
                ItemStack item = player.getItemInHand();
                boolean loreEnabled =false ;
                boolean enchantmentEnabled = false;
                List<String> lore = Lists.newArrayList();
                Map<Enchantment, Integer> enchantments = Maps.newHashMap();

                if(item.getItemMeta().hasLore()){
                    loreEnabled = true;
                    lore = item.getItemMeta().getLore();
                }
                if(item.getEnchantments()!=null || !item.getEnchantments().isEmpty()){
                    enchantmentEnabled = true;
                    enchantments = item.getEnchantments();
                }
                List<Reward> rewardList = airdrop.getRewards();
                String displayname = "test";
                if(item.getItemMeta().getDisplayName()!=null){
                    displayname = item.getItemMeta().getDisplayName();
                }else{
                    displayname = null;
                }

                Reward reward = new Reward(displayname,item.getType(),item.getAmount(),(short) item.getDurability(),Integer.parseInt(args[2]),loreEnabled,lore,enchantmentEnabled,enchantments);
                rewardList.add(reward);
                airdrop.setRewards(rewardList);
                player.sendMessage(CC.translate("&aSuccessfully added a reward to "+ airdrop.getName()));

            }
            if(args[0].equalsIgnoreCase("list")){
                sendAirdropList(player);
                return false;
            }

        }



        return false;
    }

    private void sendUsage(CommandSender player){

        player.sendMessage(CC.GRAY+CC.STRIKE_THROUGH+"---------------------------");
        player.sendMessage(CC.translate("* &b&lAirdrop Helper *"));
        player.sendMessage(" ");
        player.sendMessage(CC.translate("&f* /airdrop create <airdrop>"));
        player.sendMessage(CC.translate("&f* /airdrop delete <airdrop>"));
        player.sendMessage(CC.translate("&f* /airdrop give <airdrop> <player> <amount>"));
        player.sendMessage(CC.translate("&f* /airdrop addreward <airdrop> <chance>"));
        player.sendMessage(CC.translate("&f* /airdrop edit"));
        player.sendMessage(CC.translate("&f* /airdrop list"));
        player.sendMessage(CC.GRAY+CC.STRIKE_THROUGH+"---------------------------");

    }
    private void sendAirdropList(CommandSender player){

        player.sendMessage(CC.GRAY+CC.STRIKE_THROUGH+"---------------------------");
        player.sendMessage(CC.translate("* &b&lAirdrop List *"));
        player.sendMessage(" ");
        for(Map.Entry<String, Airdrop> entry : Airdrop.getAirdropMap().entrySet()){
            Airdrop airdrop = entry.getValue();

            player.sendMessage(CC.translate(airdrop.getName()+" &e- &r"+airdrop.getDisplayname()));
        }
        player.sendMessage(" ");
        player.sendMessage(CC.GRAY+CC.STRIKE_THROUGH+"---------------------------");

    }
}
