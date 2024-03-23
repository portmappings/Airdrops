package me.portmapping.airdrops;

import lombok.Getter;
import me.portmapping.airdrops.builder.Airdrop;
import me.portmapping.airdrops.commands.AirdropCommand;
import me.portmapping.airdrops.listeners.AirdropListeners;
import me.portmapping.airdrops.manager.AirdropManager;
import me.portmapping.airdrops.manager.impl.*;
import me.portmapping.airdrops.util.chat.CC;
import me.portmapping.airdrops.util.file.ConfigFile;
import me.portmapping.airdrops.util.file.License;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Getter
public final class Airdrops extends JavaPlugin {

    private static Airdrops instance;
    private ConfigFile settingsFile, airdropsFile;
    private AirdropManager airdropManager;

    @Override
    public void onEnable() {
        instance = this;
        if(!getDescription().getAuthors().contains("PortMapping") || !getDescription().getName().equals("PortsAirdrops")){
            Bukkit.getPluginManager().disablePlugin(this);
            Bukkit.shutdown();
            return;
        }


        loadFiles();
        getCommand("airdrop").setExecutor(new AirdropCommand());
        getServer().getPluginManager().registerEvents(new AirdropListeners(),this);
        Airdrop.loadAirdrops();







        if(getServer().getVersion().contains("1.8")){
            this.airdropManager = new AirdropManager_v1_8_8(this);
        }else if(getServer().getVersion().contains("1.9")){
            this.airdropManager = new AirdropManager_v1_9_4(this);
        }else  if(getServer().getVersion().contains("1.10")){
            this.airdropManager = new AirdropManager_v1_10_2(this);
        }else if(getServer().getVersion().contains("1.11")){
            this.airdropManager = new AirdropManager_v1_11_2(this);
        }else  if(getServer().getVersion().contains("1.12")){
            this.airdropManager = new AirdropManager_v1_8_8(this);
        }else  if(getServer().getVersion().contains("1.13")){
            this.airdropManager = new AirdropManager_v1_13_2(this);
        }else if(getServer().getVersion().contains("1.14")){
            this.airdropManager = new AirdropManager_v1_14_4(this);
        }else if(getServer().getVersion().contains("1.15")){
            this.airdropManager = new AirdropManager_v1_15_2(this);
        }else  if(getServer().getVersion().contains("1.16")){
            this.airdropManager = new AirdropManager_v1_16_5(this);
        }else  if(getServer().getVersion().contains("1.17")){
            this.airdropManager = new AirdropManager_v1_17_1(this);
        }else  if(getServer().getVersion().contains("1.18")){
            this.airdropManager = new AirdropManager_v1_18_2(this);
        }else  if(getServer().getVersion().contains("1.19")){
            this.airdropManager = new AirdropManager_v1_19_4(this);
        }else  if(getServer().getVersion().contains("1.20")){
        this.airdropManager = new AirdropManager_v1_20_4(this);
        }

    }









    private void loadFiles(){
        try {
            this.settingsFile = new ConfigFile(this,"settings.yml");
            this.airdropsFile = new ConfigFile(this, "airdrops.yml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Airdrop.saveAll();
        instance = null;
    }

    public static Airdrops getInstance(){
        return instance;
    }
}