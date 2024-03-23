package me.portmapping.airdrops.util.menu.pagination;

import lombok.NonNull;
import me.portmapping.airdrops.util.chat.CC;
import me.portmapping.airdrops.util.menu.Button;
import me.portmapping.airdrops.util.menu.Menu;
import me.portmapping.airdrops.util.menu.buttons.BackButton;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class ViewAllPagesMenu extends Menu {
    @NonNull
    PaginatedMenu menu;

    @Override
    public String getTitle(final Player player) {
        return CC.YELLOW + "Jump to page";
    }

    @Override
    public Map<Integer, Button> getButtons(final Player player) {
        final HashMap<Integer, Button> buttons = new HashMap<>();
        buttons.put(0, new BackButton(this.menu));
        int index = 10;
        for (int i = 1; i <= this.menu.getPages(player); ++i) {
            buttons.put(index++, new JumpToPageButton(i, this.menu));
            if ((index - 8) % 9 == 0) {
                index += 2;
            }
        }
        return buttons;
    }

    @Override
    public boolean isAutoUpdate() {
        return true;
    }

    public ViewAllPagesMenu(@NonNull final PaginatedMenu menu) {
        if (menu == null) {
            throw new NullPointerException("menu");
        }
        this.menu = menu;
    }

    @NonNull
    public PaginatedMenu getMenu() {
        return this.menu;
    }
}