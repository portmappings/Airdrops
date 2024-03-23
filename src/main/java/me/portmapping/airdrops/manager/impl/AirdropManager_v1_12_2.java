package me.portmapping.airdrops.manager.impl;

import me.filoghost.holographicdisplays.api.HolographicDisplaysAPI;
import me.filoghost.holographicdisplays.api.hologram.Hologram;
import me.filoghost.holographicdisplays.api.hologram.line.TextHologramLine;
import me.portmapping.airdrops.Airdrops;
import me.portmapping.airdrops.builder.Airdrop;
import me.portmapping.airdrops.listeners.AirdropListeners;
import me.portmapping.airdrops.manager.AirdropManager;
import me.portmapping.airdrops.util.effects.ParticleEffect;
import me.portmapping.airdrops.util.sound.CompatibleSound;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.ChatColor;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Dropper;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.atomic.AtomicReference;


public class AirdropManager_v1_12_2 implements AirdropManager {

    public AirdropManager_v1_12_2(Airdrops plugin){

    }

    @Override
    public void place(final Player player, final Location location, Airdrop airdrop){
        final FallingBlock fallingBlock = location.getWorld().spawnFallingBlock(location.clone().add(0.0D, 20.0D, 0.0D), Material.DROPPER, (byte)0);
        fallingBlock.setHurtEntities(false);
        fallingBlock.setDropItem(false);

        Location loc = location;
        if(airdrop.isFireworkenabled()){
            Firework fw = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
            FireworkMeta fwm = fw.getFireworkMeta();

            fwm.setPower(airdrop.getFireworkpower());
            fwm.addEffect(FireworkEffect.builder().with(airdrop.getFireworktype()).withColor(airdrop.getFireworkcolor()).flicker(true).build());

            fw.setFireworkMeta(fwm);

        }

        (new BukkitRunnable() {
            public void run() {
                if (fallingBlock.isDead())
                    try {
                        final Dropper dropper = (Dropper) location.getBlock().getState();
                        if (!dropper.isPlaced())
                            return;
                        dropper.setMetadata("airdrop", (MetadataValue) new FixedMetadataValue(Airdrops.getInstance(), "airdrop"));



                        Hologram hologram = null;
                        if(airdrop.isHologramEnabled()){
                            HolographicDisplaysAPI api = HolographicDisplaysAPI.get(Airdrops.getInstance());
                            Location hologramlocation = location.add(0.5D, 0.0D, 0.5D);
                            hologramlocation.setY(hologramlocation.getY() + 2.0D);
                            hologram = api.createHologram(hologramlocation);
                        }

                        CompatibleSound.ZOMBIE_WOODBREAK.play(player);
                        CompatibleSound.DIG_SNOW.play(player);
                        CompatibleSound.BURP.play(player);
                        dropper.getInventory().setContents(airdrop.getLoot());


                        /*Packet packetparticles = new PacketPlayOutWorldParticles(EnumParticle.valueOf(ParticleEffect.WITCH_MAGIC.getEnumValue()),
                                true,
                                location.getBlockX(),
                                location.getBlockY(),
                                location.getBlockZ(),
                                0F,
                                0F,
                                0F,
                                10F,
                                500);
                        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packetparticles);*/
                        final AtomicReference<Integer> countdown = new AtomicReference<>();
                        countdown.set(Integer.valueOf(airdrop.getDestroytime()));
                        TextHologramLine UpdateLine = null;
                        if(airdrop.isHologramEnabled()){
                            TextHologramLine MainLine = hologram.getLines().insertText(0, ChatColor.translateAlternateColorCodes('&', airdrop.getMainHoloLine().replace("<name>",airdrop.getDisplayname())));
                            UpdateLine = hologram.getLines().appendText(ChatColor.translateAlternateColorCodes('&',airdrop.getSecondHoloLine().replace("<countdown>",String.valueOf(countdown.get()))));

                        }



                        Hologram finalHologram = hologram;
                        TextHologramLine finalUpdateLine = UpdateLine;
                        (new BukkitRunnable() {
                            public void run() {
                                try {
                                    if (dropper.getBlock().getType() == Material.AIR) {
                                        finalHologram.delete();
                                        cancel();
                                    }
                                    if (((Integer)countdown.get()).intValue() > 1) {
                                        countdown.set(Integer.valueOf(((Integer)countdown.get()).intValue() - 1));
                                        if (dropper.getBlock().getType() == Material.AIR)
                                            return;




                                        if(airdrop.isHologramEnabled()){
                                            finalUpdateLine.setText(ChatColor.translateAlternateColorCodes('&', airdrop.getSecondHoloLine().replace("<countdown>",String.valueOf(countdown.get()))));
                                        }
                                    } else if (((Integer)countdown.get()).intValue() == 1) {
                                        if(airdrop.isHologramEnabled()){
                                            finalHologram.delete();
                                        }
                                        dropper.getBlock().breakNaturally();
                                        CompatibleSound.CHICKEN_EGG_POP.play(player);
                                        cancel();
                                        countdown.set(Integer.valueOf(-1));
                                    }
                                } catch (Throwable $ex) {
                                    throw $ex;
                                }
                            }
                        }).runTaskTimer(Airdrops.getInstance(), 20L, 20L);
                        AirdropListeners.airdropLocation.remove(location);
                        cancel();
                    } catch (Exception e) {
                        cancel();
                        e.printStackTrace();
                    }
            }
        }).runTaskTimer(Airdrops.getInstance(), 5L, 5L);
    }
}
