package me.portmapping.airdrops.util.menu.menus;


import me.portmapping.airdrops.util.file.Callback;
import me.portmapping.airdrops.util.menu.Button;
import me.portmapping.airdrops.util.menu.Menu;
import me.portmapping.airdrops.util.menu.buttons.BooleanButton;
import org.bukkit.entity.Player;


import java.util.HashMap;
import java.util.Map;

public class ConfirmMenu extends Menu {

    private final String title;
    private final Callback<Boolean> response;
    private final boolean closeAfterResponse;
    private final Button[] centerButtons;


    public ConfirmMenu(String title, Callback<Boolean> response, boolean closeAfter, Button... centerButtons) {
        this.title = title;
        this.response = response;
        this.closeAfterResponse = closeAfter;
        this.centerButtons = centerButtons;

    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        HashMap<Integer, Button> buttons = new HashMap<>();

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                buttons.put(getSlot(x, y), new BooleanButton(true, response, closeAfterResponse));
                buttons.put(getSlot(8 - x, y), new BooleanButton(false, response, closeAfterResponse));
            }
        }

        if (centerButtons != null) {
            for (int i = 0; i < centerButtons.length; i++) {
                if (centerButtons[i] != null) {
                    buttons.put(getSlot(13, i), centerButtons[i]);
                }
            }
        }

        return buttons;
    }

    @Override
    public String getTitle(Player player) {
        return title;
    }
}