package me.portmapping.airdrops.util.menu;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class MenuRunnable implements Runnable {
    @Override
    public void run() {
        Menu.currentlyOpenedMenus.forEach((name, menu) -> {
            final Player player = Bukkit.getPlayer(name);

            if (player != null) {
                if (menu.isAutoUpdate()) {
                    menu.update(player);
                }
            } else {
                Menu.currentlyOpenedMenus.remove(name);
            }
        });
    }
}
