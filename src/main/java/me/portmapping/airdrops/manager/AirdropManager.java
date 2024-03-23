package me.portmapping.airdrops.manager;

import me.portmapping.airdrops.builder.Airdrop;
import me.portmapping.airdrops.builder.Reward;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public interface AirdropManager {

     void place(final Player player, final Location location,Airdrop airdrop);



}
