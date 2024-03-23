package me.portmapping.airdrops.builder;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import me.filoghost.holographicdisplays.api.HolographicDisplaysAPI;
import me.filoghost.holographicdisplays.api.hologram.Hologram;
import me.filoghost.holographicdisplays.api.hologram.line.TextHologramLine;
import me.portmapping.airdrops.Airdrops;
import me.portmapping.airdrops.listeners.AirdropListeners;
import me.portmapping.airdrops.util.chat.Bridge;
import me.portmapping.airdrops.util.file.ConfigFile;
import me.portmapping.airdrops.util.item.ItemBuilder;
import me.portmapping.airdrops.util.sound.CompatibleSound;
import org.bukkit.*;
import org.bukkit.block.Dropper;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicReference;

@Getter @Setter
public class Airdrop {


    @Getter
    public static Map<String,Airdrop> airdropMap = new HashMap<>();

    public String name;
    public String displayname;
    public Material material;
    public List<Reward> rewards;
    public List<String> lore;

    public int destroytime;

    public int fireworkpower;
    public Color fireworkcolor;
    public FireworkEffect.Type fireworktype;
    public boolean fireworkenabled;

    public boolean hologramEnabled;
    public String mainHoloLine;
    public String secondHoloLine;


    private static Airdrops plugin = Airdrops.getInstance();


    public Airdrop(String name,String displayname, Material material, List<Reward> rewards, List<String>lore, int destroytime, int fireworkpower,Color fireworkcolor,FireworkEffect.Type fireworktype, boolean fireworkenabled,boolean hologramEnabled,String mainHoloLine, String secondHoloLine){
        this.name = name;
        this.displayname = displayname;
        this.material = material;
        this.lore = lore;
        this.rewards = rewards;
        this.destroytime = destroytime;
        this.fireworkpower = fireworkpower;
        this.fireworkcolor = fireworkcolor;
        this.fireworktype = fireworktype;
        this.fireworkenabled = fireworkenabled;
        this.hologramEnabled = hologramEnabled;
        this.mainHoloLine = mainHoloLine;
        this.secondHoloLine = secondHoloLine;
        this.airdropMap.put(name,this);

    }



    public static void loadAirdrops(){
        ConfigFile config = plugin.getAirdropsFile();

        for(String string : config.getConfigurationSection("AIRDROPS").getKeys(false)){
            String displayname = config.getString("AIRDROPS."+string+".DISPLAYNAME");
            Material material = Material.getMaterial(config.getString("AIRDROPS."+string+".MATERIAL"));
            List<Reward> rewardList = Lists.newArrayList();
            List<String> airdroplore = config.getStringList("AIRDROPS."+string+".LORE");
            int destroytime = config.getInt("AIRDROPS."+string+".DESTROY-TIME");

            int fireworkpower = config.getInt("AIRDROPS."+string+".FIREWORK.POWER");
            Color fireworkcolor = colorByName(config.getString("AIDROPS."+string+".FIREWORK.COLOR"));
            FireworkEffect.Type fireworktype = FireworkEffect.Type.valueOf(config.getString("AIRDROPS."+string+".FIREWORK.TYPE"));
            boolean fireworkenabled = config.getBoolean("AIRDROPS."+string+".FIREWORK.ENABLED");

            boolean hologramEnabled = config.getBoolean("AIRDROPS."+string+".HOLOGRAM.ENABLED");
            String mainHoloLine = config.getString("AIRDROPS."+string+".HOLOGRAM.MAIN-LINE");
            String secondHoloLine = config.getString("AIRDROPS."+string+".HOLOGRAM.SECOND-LINE");



            for (String path : config.getConfigurationSection("AIRDROPS."+string+".REWARDS").getKeys(false)){
                String rewardname = config.getString("AIRDROPS."+string+".REWARDS."+path+".NAME");
                Material rewardmaterial = Material.getMaterial(config.getString("AIRDROPS."+string+".REWARDS."+path+".MATERIAL"));
                int rewardamount = config.getInt("AIRDROPS."+string+".REWARDS."+path+".AMOUNT");
                short rewarddata = (short) config.getInt("AIRDROPS."+string+".REWARDS."+path+".DATA");
                int rewardchance =  config.getInt("AIRDROPS."+string+".REWARDS."+path+".CHANCE");

                boolean loreEnabled = config.getBoolean("AIRDROPS."+string+".REWARDS."+path+".LORE.ENABLED");
                List<String> lore = config.getStringList("AIRDROPS."+string+".REWARDS."+path+".LORE.LIST");

                boolean enchantmentsEnabled = config.getBoolean("AIRDROPS."+string+".REWARDS."+path+".ENCHANTMENTS.ENABLED");
                List<String> enchantments = config.getStringList("AIRDROPS."+string+".REWARDS."+path+".ENCHANTMENTS.LIST");

                Reward reward = new Reward(rewardname,rewardmaterial,rewardamount,rewarddata,rewardchance,loreEnabled,lore,enchantmentsEnabled,Reward.parseEnchantmentsStatic(enchantments));
                rewardList.add(reward);


            }

            Airdrop airdrop = new Airdrop(string,displayname,material,rewardList,airdroplore,destroytime,fireworkpower,fireworkcolor,fireworktype,fireworkenabled,hologramEnabled,mainHoloLine,secondHoloLine);
            airdropMap.put(string,airdrop);
        }
    }

    public static Airdrop getByName(String str){
        return airdropMap.get(str);
    }

    public static Airdrop getByItem(ItemStack input){
        Airdrop airdrop = null;
        for (Map.Entry<String, Airdrop> entry : Airdrop.getAirdropMap().entrySet()) {
            Airdrop value = entry.getValue();
            ItemStack item = value.toItemStack();
            if(item.isSimilar(input)){
                airdrop = value;
            }
        }
        return airdrop;
    }

    public void save(){
        ConfigFile config = plugin.getAirdropsFile();

        config.set("AIRDROPS."+getName()+".DISPLAYNAME",getDisplayname());
        config.set("AIRDROPS."+getName()+".MATERIAL",material.name());
        config.set("AIRDROPS."+getName()+".LORE",lore);
        config.set("AIRDROPS."+getName()+".DESTROY-TIME",destroytime);

        config.set("AIRDROPS."+getName()+".FIREWORK.POWER",getFireworkpower());
        config.set("AIRDROPS."+getName()+".FIREWORK.COLOR",getFireworkcolor());
        config.set("AIRDROPS."+getName()+".FIREWORK.TYPE",getFireworktype().name());
        config.set("AIRDROPS."+getName()+".FIREWORK.ENABLED",isFireworkenabled());

        config.set("AIRDROPS."+getName()+".HOLOGRAM.ENABLED",isFireworkenabled());
        config.set("AIRDROPS."+getName()+".HOLOGRAM.MAIN-LINE",mainHoloLine);
        config.set("AIRDROPS."+getName()+".HOLOGRAM.SECOND-LINE",secondHoloLine);


        for (int i = 0; i<this.rewards.size();i++){
            Reward reward = this.rewards.get(i);
            config.set("AIRDROPS."+getName()+".REWARDS."+i+".NAME",reward.getName());
            config.set("AIRDROPS."+getName()+".REWARDS."+i+".MATERIAL",reward.getMaterial().name());
            config.set("AIRDROPS."+getName()+".REWARDS."+i+".AMOUNT",reward.getAmount());
            config.set("AIRDROPS."+getName()+".REWARDS."+i+".DATA",reward.getData());
            config.set("AIRDROPS."+getName()+".REWARDS."+i+".CHANCE",reward.getChance());
            config.set("AIRDROPS."+getName()+".REWARDS."+i+".LORE.ENABLED",reward.isLoreEnabled());
            config.set("AIRDROPS."+getName()+".REWARDS."+i+".LORE.LIST",reward.getLore());
            config.set("AIRDROPS."+getName()+".REWARDS."+i+".ENCHANTMENTS.ENABLED",reward.isEnchantmentsEnabled());
            config.set("AIRDROPS."+getName()+".REWARDS."+i+".ENCHANTMENTS.LIST",Reward.parseToList(reward.getEnchantments()));
        }
        config.save();
    }

    public void delete(){
        airdropMap.remove(this.getName());
        ConfigFile config = plugin.getAirdropsFile();
        config.set(("AIRDROPS."+this.getName()),null);
        config.save();
    }

    public void place(final Player player, final Location location){
        final FallingBlock fallingBlock = location.getWorld().spawnFallingBlock(location.clone().add(0.0D, 20.0D, 0.0D), Material.DROPPER, (byte)0);
        fallingBlock.setHurtEntities(false);
        fallingBlock.setDropItem(false);

        Location loc = location;
       if(fireworkenabled){
           Firework fw = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
           FireworkMeta fwm = fw.getFireworkMeta();

           fwm.setPower(getFireworkpower());
           fwm.addEffect(FireworkEffect.builder().with(getFireworktype()).withColor(getFireworkcolor()).flicker(true).build());

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

                        HolographicDisplaysAPI api = HolographicDisplaysAPI.get(Airdrops.getInstance());
                        Location hologramlocation = location.add(0.5D, 0.0D, 0.5D);
                        hologramlocation.setY(hologramlocation.getY() + 2.0D);
                        final Hologram hologram = api.createHologram(hologramlocation);
                        CompatibleSound.ZOMBIE_WOODBREAK.play(player);
                        CompatibleSound.DIG_SNOW.play(player);
                        CompatibleSound.BURP.play(player);
                        dropper.getInventory().setContents(getLoot());
                        /*for (int i = 0; i<80;i++){
                            location.setY(location.getY()-0.028);
                            ParticleEffect.WITCH_MAGIC.sendToPlayers(Bukkit.getServer().getOnlinePlayers(), location, ThreadLocalRandom.current().nextInt(-1, 1), 0.0F, ThreadLocalRandom.current().nextInt(-1, 1), 1.0F, 1);
                        }*/
                        TextHologramLine MainLine = hologram.getLines().insertText(0, ChatColor.translateAlternateColorCodes('&', displayname));
                        final AtomicReference<Integer> countdown = new AtomicReference<>();
                        countdown.set(Integer.valueOf(getDestroytime()));
                        final TextHologramLine UpdateLine = hologram.getLines().appendText(ChatColor.translateAlternateColorCodes('&', "&fDestroying in &b" + countdown + " seconds &f."));
                        (new BukkitRunnable() {
                            public void run() {
                                try {
                                    if (dropper.getBlock().getType() == Material.AIR) {
                                        hologram.delete();
                                        cancel();
                                    }
                                    if (((Integer)countdown.get()).intValue() > 1) {
                                        countdown.set(Integer.valueOf(((Integer)countdown.get()).intValue() - 1));
                                        if (dropper.getBlock().getType() == Material.AIR)
                                            return;
                                        UpdateLine.setText(ChatColor.translateAlternateColorCodes('&', "&fDestroying in &b" + countdown + " seconds&f."));
                                    } else if (((Integer)countdown.get()).intValue() == 1) {
                                        hologram.delete();
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

    public ItemStack getRandomReward() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int randomint = random.nextInt(100);
        int randomintforrewards = random.nextInt(getRewards().size());
        Reward reward = getRewards().get(randomintforrewards);
        if(randomint < reward.getChance()){
            return reward.toItemStack();
        }
        return getRandomReward();
    }


    public ItemStack[] getLoot() {
        List<ItemStack> rewardsToReturn = Lists.newArrayList();
        rewardsToReturn.add(0, getRandomReward());
        rewardsToReturn.add(1, getRandomReward());
        rewardsToReturn.add(2, getRandomReward());
        rewardsToReturn.add(3, getRandomReward());
        rewardsToReturn.add(4, getRandomReward());
        rewardsToReturn.add(5, getRandomReward());
        rewardsToReturn.add(6, getRandomReward());
        rewardsToReturn.add(7, getRandomReward());
        rewardsToReturn.add(8, getRandomReward());
        return rewardsToReturn.<ItemStack>toArray(new ItemStack[0]);
    }



    public ItemStack toItemStack(){
        return new ItemBuilder(material).setName(displayname).setLore(lore).toItemStack();
    }

    public static void saveAll(){
        for (Map.Entry<String, Airdrop> entry : Airdrop.getAirdropMap().entrySet()) {
            String key = entry.getKey();
            Airdrop value = entry.getValue();
            value.save();
        }
    }

    private static Color colorByName(String string){
        string = string.toLowerCase();
        switch (string){
            case "aqua":
                return Color.AQUA;
            case "blue":
                return Color.BLUE;
            case "gray":
                return Color.GRAY;
            case "black":
                return Color.BLACK;
            case "fuchsia":
                return Color.FUCHSIA;
            case "green":
                return Color.GREEN;
            case "lime":
                return Color.LIME;
            case "maroon":
                return Color.MAROON;
            case "navy":
                return Color.NAVY;
            case "olive":
                return Color.OLIVE;
            case "orange":
                return Color.ORANGE;
            case "purple":
                return Color.PURPLE;
            case "red":
                return Color.RED;
            case "silver":
                return Color.SILVER;
            case "teal":
                return Color.TEAL;
            case "white":
                return Color.WHITE;
            case "yellow":
                return Color.YELLOW;

            default:
                return Color.AQUA;
        }

    }
}
