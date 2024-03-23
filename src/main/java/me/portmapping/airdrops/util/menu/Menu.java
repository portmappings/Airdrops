package me.portmapping.airdrops.util.menu;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.portmapping.airdrops.Airdrops;
import me.portmapping.airdrops.util.item.CompatibleMaterial;
import org.apache.commons.math3.util.FastMath;
import me.portmapping.airdrops.util.chat.CC;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Setter
@NoArgsConstructor
public abstract class Menu {
    private Method openInventoryMethod;
    private final Map<Integer, Button> buttons = Maps.newConcurrentMap();
    private boolean autoUpdate = false;
    private boolean closedByMenu = false;
    private boolean updateAfterClick = true;
    private boolean placeholder = false;
    private boolean nonCancellingInventory = false;
    private String staticTitle = null;
    public static Map<String, Menu> currentlyOpenedMenus = new ConcurrentHashMap<>();
    private Inventory inventory;

    private Inventory createInventory(Player player) {
        Map<Integer, Button> invButtons = this.getButtons(player);

        String title = CC.translate(this.getTitle(player));

        if (title.length() > 32) {
            title = title.substring(0, 32);
        }

        Inventory inv;

        if(inventory == null){
            inv = Bukkit.createInventory(player, this.size(invButtons), title);
            inventory = inv;
        }else{
            inv = inventory;
        }

        inventory.clear();

        for (Map.Entry<Integer, Button> buttonEntry : invButtons.entrySet()) {
            this.buttons.put(buttonEntry.getKey(), buttonEntry.getValue());
            inv.setItem(buttonEntry.getKey(), buttonEntry.getValue().getButtonItem(player));
        }

        if (this.isPlaceholder()) {
            Button placeholder = Button.placeholder(CompatibleMaterial.STAINED_GLASS_PANE.getMaterial(),(byte) 15);

            for (int index = 0; index < this.size(invButtons); ++index) {
                if (invButtons.get(index) == null) {
                    this.buttons.put(index, placeholder);
                    inv.setItem(index, placeholder.getButtonItem(player));
                }
            }
        }
        return inv;
    }


    public Menu(String staticTitle) {
        this.staticTitle = Preconditions.checkNotNull(staticTitle);
    }

    public void open(Player player) {
        this.openMenu(player);
    }

    public void openMenu(final Player player) {
        Menu previousMenu = Menu.currentlyOpenedMenus.get(player.getName());

        if (previousMenu != null) {
            previousMenu.onClose(player);
        }

        Inventory inv = this.createInventory(player);

        try {
            player.openInventory(inv);
            Menu.currentlyOpenedMenus.put(player.getName(), this);
            this.onOpen(player);

            try {
                Inventory newInv = this.createInventory(player);

                for (int i = 0; i < player.getOpenInventory().getTopInventory().getSize(); i++) {
                    player.getOpenInventory().getTopInventory().setItem(i, newInv.getItem(i));
                }
            } catch (Exception e) {
                throw new IllegalArgumentException("Menu '" + player.getOpenInventory().getTitle() + "' cannot be created cuz size is below 9, and equals " + player.getOpenInventory().getTopInventory().getSize());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void update(Player player) {
        inventory.clear();

        Map<Integer, Button> invButtons = this.getButtons(player);

        for (Map.Entry<Integer, Button> buttonEntry : invButtons.entrySet()) {
            //this.buttons.put(buttonEntry.getKey(), buttonEntry.getValue());
            inventory.setItem(buttonEntry.getKey(), buttonEntry.getValue().getButtonItem(player));
        }

        if (this.isPlaceholder()) {
            Button placeholder = Button.placeholder(CompatibleMaterial.STAINED_GLASS_PANE.getMaterial(), (byte) 15);

            for (int index = 0; index < this.size(invButtons); ++index) {
                if (invButtons.get(index) == null) {
                    // this.buttons.put(index, placeholder);
                    inventory.setItem(index, placeholder.getButtonItem(player));
                }
            }
        }

        player.updateInventory();
        /*final BukkitRunnable runnable = new BukkitRunnable() {
            public void run() {
                if (!player.isOnline()) {
                    Menu.cancelCheck(player);
                    Menu.currentlyOpenedMenus.remove(player.getName());
                }
                if (Menu.this.isAutoUpdate()) {

                }
            }
        };
        runnable.runTaskTimerAsynchronously(KioCore.INSTANCE, 0L, 1L);
        Menu.checkTasks.put(player.getName(), runnable);*/
    }

    public int size(final Map<Integer, Button> buttons) {
        int highest = 0;

        for (final int buttonValue : buttons.keySet()) {
            if (buttonValue > highest) {
                highest = buttonValue;
            }
        }


        return (int) (FastMath.ceil((highest + 1) / 9.0) * 9.0);
    }

    public int getSlot(final int x, final int y) {
        return 9 * y + x;
    }

    public String getTitle(final Player player) {
        return this.staticTitle;
    }

    public abstract Map<Integer, Button> getButtons(Player player);

    public void onOpen(Player player) {
    }

    public void onClose(Player player) {
    }

    public void fillBorders(Player player, Button button) {
        int rows = inventory.getSize() / 9;
        int columns = inventory.getSize() % 9;

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                inventory.setItem(getSlot(row, column), button.getButtonItem(player));
            }
        }
    }

    public void fillEmpty(Player player, Button button) {
        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, button.getButtonItem(player));
            }
        }
    }

    public Map<Integer, Button> getButtons() {
        return this.buttons;
    }

    static {
        Airdrops.getInstance().getServer().getPluginManager().registerEvents(new ButtonListener(), Airdrops.getInstance());
    }
}