package me.portmapping.airdrops.menus;

import com.google.common.collect.Lists;
import me.portmapping.airdrops.builder.Airdrop;
import me.portmapping.airdrops.util.chat.CC;
import me.portmapping.airdrops.util.effects.ParticleEffect;
import me.portmapping.airdrops.util.item.CompatibleMaterial;
import me.portmapping.airdrops.util.item.ItemBuilder;
import me.portmapping.airdrops.util.menu.Button;
import me.portmapping.airdrops.util.menu.Menu;
import me.portmapping.airdrops.util.menu.buttons.BackButton;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class FireworkMenu extends Menu {

    private Airdrop airdrop;
    public FireworkMenu(Airdrop airdrop){
        this.airdrop = airdrop;
        this.setPlaceholder(true);
    }
    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer,Button> buttons = new HashMap<>();


        buttons.put(12,new FireworkColorButton(airdrop));
        buttons.put(13,new FireworkPowerButton(airdrop));
        buttons.put(14, new FireworkTypeButton(airdrop));
        buttons.put(22, new FireworkEnabledButton(airdrop));

        buttons.put(18, new BackButton(new VisualsMenu(airdrop)));



        return buttons;
    }
    @Override
    public int size(final Map<Integer, Button> buttons){
        return 9 * 3;
    }

    @Override
    public String getTitle(final Player player) {
        return "Edit Rewards";
    }

    private class FireworkPowerButton extends Button{
        private Airdrop airdrop;
        public FireworkPowerButton(Airdrop airdrop){
            this.airdrop = airdrop;
        }

        @Override
        public ItemStack getButtonItem(final Player player){
            if(airdrop.getFireworkpower()==1){
                return new ItemBuilder
                        (CompatibleMaterial.FIREBALL.getMaterial())
                        .setName(CC.translate("&bPower"))
                        .setLore(Lists.newArrayList(
                                CC.translate("&b> &e1"),
                                CC.translate("&72"),
                                CC.translate("&73")
                        ))
                        .toItemStack();
            }

            if(airdrop.getFireworkpower()==2){
                return new ItemBuilder
                        (CompatibleMaterial.FIREBALL.getMaterial())
                        .setName(CC.translate("&bPower"))
                        .setLore(Lists.newArrayList(
                                CC.translate("&71"),
                                CC.translate("&b> &e2"),
                                CC.translate("&73")
                        ))
                        .toItemStack();
            }

            if(airdrop.getFireworkpower() == 3){
                return new ItemBuilder
                        (CompatibleMaterial.FIREBALL.getMaterial())
                        .setName(CC.translate("&bPower"))
                        .setLore(Lists.newArrayList(
                                CC.translate("&71"),
                                CC.translate("&72"),
                                CC.translate("&b> &e3")
                        ))
                        .toItemStack();
            }
            return new ItemBuilder
                    (CompatibleMaterial.FIREBALL.getMaterial())
                    .setName(CC.translate("&bPower"))
                    .setLore(Lists.newArrayList(
                            CC.translate("&71"),
                            CC.translate("&72"),
                            CC.translate("&b> &e3")
                    ))
                    .toItemStack();


        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType) {

            if(airdrop.getFireworkpower()==1){
                airdrop.setFireworkpower(2);
                new FireworkMenu(airdrop).openMenu(player);
                return;
            }
            if(airdrop.getFireworkpower()==2){
                airdrop.setFireworkpower(3);
                new FireworkMenu(airdrop).openMenu(player);
                return;
            }
            if(airdrop.getFireworkpower()==3){
                airdrop.setFireworkpower(1);
                new FireworkMenu(airdrop).openMenu(player);

            }



        }


    }

    private class FireworkTypeButton extends Button{
        private Airdrop airdrop;
        public FireworkTypeButton(Airdrop airdrop){
            this.airdrop = airdrop;
        }

        @Override
        public ItemStack getButtonItem(final Player player){

            if(airdrop.getFireworktype() == FireworkEffect.Type.STAR){
                return new ItemBuilder(CompatibleMaterial.PAINTING.getMaterial())

                        .setName(CC.translate("&bFirework Type"))
                        .setLore(Lists.newArrayList(
                                CC.translate("&b> &eSTAR"),
                                CC.translate("&7CREEPER"),
                                CC.translate("&7BURST"),
                                CC.translate("&7BALL"),
                                CC.translate("&7BALL_LARGE"))).toItemStack();
            }
            if(airdrop.getFireworktype() == FireworkEffect.Type.CREEPER){
                return new ItemBuilder(CompatibleMaterial.PAINTING.getMaterial())

                        .setName(CC.translate("&bFirework Type"))
                        .setLore(Lists.newArrayList(
                                CC.translate("&7STAR"),
                                CC.translate("&b> &eCREEPER"),
                                CC.translate("&7BURST"),
                                CC.translate("&7BALL"),
                                CC.translate("&7BALL_LARGE"))).toItemStack();
            }
            if(airdrop.getFireworktype()==FireworkEffect.Type.BURST){
                return new ItemBuilder(CompatibleMaterial.PAINTING.getMaterial())
                        .setName(CC.translate("&bFirework Type"))
                        .setLore(Lists.newArrayList(
                                CC.translate("&7STAR"),
                                CC.translate("&7CREEPER"),
                                CC.translate("&b> &eBURST"),
                                CC.translate("&7BALL"),
                                CC.translate("&7BALL_LARGE"))).toItemStack();
            }
            if(airdrop.getFireworktype()==FireworkEffect.Type.BALL){
                return new ItemBuilder(CompatibleMaterial.PAINTING.getMaterial())
                        .setName(CC.translate("&bFirework Type"))
                        .setLore(Lists.newArrayList(
                                CC.translate("&7STAR"),
                                CC.translate("&7CREEPER"),
                                CC.translate("&7BURST"),
                                CC.translate("&b> &eBALL"),
                                CC.translate("&7BALL_LARGE"))).toItemStack();
            }

            return new ItemBuilder(CompatibleMaterial.PAINTING.getMaterial())
                    .setName(CC.translate("&bFirework Type"))
                    .setLore(Lists.newArrayList(
                            CC.translate("&7STAR"),
                            CC.translate("&7CREEPER"),
                            CC.translate("&7BURST"),
                            CC.translate("&7BALL"),
                            CC.translate("&b> &eBALL_LARGE"))).toItemStack();
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType) {
            switch (airdrop.getFireworktype()){
                case STAR:
                    airdrop.setFireworktype(FireworkEffect.Type.CREEPER);
                    break;
                case CREEPER:
                    airdrop.setFireworktype(FireworkEffect.Type.BURST);
                    break;
                case BURST:
                    airdrop.setFireworktype(FireworkEffect.Type.BALL);
                    break;
                case BALL:
                    airdrop.setFireworktype(FireworkEffect.Type.BALL_LARGE);
                    break;
                case BALL_LARGE:
                    airdrop.setFireworktype(FireworkEffect.Type.STAR);
                    break;
            }
        }


    }
    private class FireworkColorButton extends Button{
        private Airdrop airdrop;
        public FireworkColorButton(Airdrop airdrop){
            this.airdrop = airdrop;
        }

        @Override
        public ItemStack getButtonItem(final Player player){

            if(airdrop.getFireworkcolor() == Color.BLACK){
                return new ItemBuilder(CompatibleMaterial.INK_SACK.getMaterial())
                        .setDurability(0)
                        .setName(CC.translate("&bColor")).toItemStack();
            }
            if(airdrop.getFireworkcolor() == Color.RED){
                return new ItemBuilder(CompatibleMaterial.INK_SACK.getMaterial())
                        .setDurability(1)
                        .setName(CC.translate("&bColor"))
                        .toItemStack();
            }
            if(airdrop.getFireworkcolor() == Color.GREEN){
                return new ItemBuilder(CompatibleMaterial.INK_SACK.getMaterial())
                        .setDurability(2)
                        .setName(CC.translate("&bColor"))
                        .toItemStack();
            }
            if(airdrop.getFireworkcolor() == Color.MAROON){
                return new ItemBuilder(CompatibleMaterial.INK_SACK.getMaterial())
                        .setDurability(3)
                        .setName(CC.translate("&bColor"))
                        .toItemStack();
            }
            if(airdrop.getFireworkcolor() == Color.NAVY){
                return new ItemBuilder(CompatibleMaterial.INK_SACK.getMaterial())
                        .setDurability(4)
                        .setName(CC.translate("&bColor"))
                        .toItemStack();
            }
            if(airdrop.getFireworkcolor() == Color.PURPLE){
                return new ItemBuilder(CompatibleMaterial.INK_SACK.getMaterial())
                        .setDurability(5)
                        .setName(CC.translate("&bColor"))
                        .toItemStack();
            }
            if(airdrop.getFireworkcolor() == Color.TEAL){
                return new ItemBuilder(CompatibleMaterial.INK_SACK.getMaterial())
                        .setDurability(6)
                        .setName(CC.translate("&bColor"))
                        .toItemStack();
            }
            if(airdrop.getFireworkcolor() == Color.GRAY){
                return new ItemBuilder(CompatibleMaterial.INK_SACK.getMaterial())
                        .setDurability(7)
                        .setName(CC.translate("&bColor"))
                        .toItemStack();
            }
            if(airdrop.getFireworkcolor() == Color.SILVER){
                return new ItemBuilder(CompatibleMaterial.INK_SACK.getMaterial())
                        .setDurability(8)
                        .setName(CC.translate("&bColor"))
                        .toItemStack();
            }
            if(airdrop.getFireworkcolor() == Color.FUCHSIA){
                return new ItemBuilder(CompatibleMaterial.INK_SACK.getMaterial())
                        .setDurability(9)
                        .setName(CC.translate("&bColor"))
                        .toItemStack();
            }
            if(airdrop.getFireworkcolor() == Color.LIME){
                return new ItemBuilder(CompatibleMaterial.INK_SACK.getMaterial())
                        .setDurability(10)
                        .setName(CC.translate("&bColor"))
                        .toItemStack();
            }
            if(airdrop.getFireworkcolor() == Color.YELLOW){
                return new ItemBuilder(CompatibleMaterial.INK_SACK.getMaterial())
                        .setDurability(11)
                        .setName(CC.translate("&bColor"))
                        .toItemStack();
            }
            if(airdrop.getFireworkcolor() == Color.AQUA){
                return new ItemBuilder(CompatibleMaterial.INK_SACK.getMaterial())
                        .setDurability(12)
                        .setName(CC.translate("&bColor"))
                        .toItemStack();
            }
            if(airdrop.getFireworkcolor() == Color.ORANGE){
                return new ItemBuilder(CompatibleMaterial.INK_SACK.getMaterial())
                        .setDurability(14)
                        .setName(CC.translate("&bColor"))
                        .toItemStack();
            }
            if(airdrop.getFireworkcolor() == Color.WHITE){
                return new ItemBuilder(CompatibleMaterial.INK_SACK.getMaterial())
                        .setDurability(15)
                        .setName(CC.translate("&bColor"))
                        .toItemStack();
            }


                return new ItemBuilder(CompatibleMaterial.INK_SACK.getMaterial())
                        .setDurability(6)
                        .setName(CC.translate("&bColor"))
                        .toItemStack();



        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType) {
            if (Color.BLACK.equals(airdrop.getFireworkcolor())) {
                airdrop.setFireworkcolor(Color.RED);
            } else if (Color.RED.equals(airdrop.getFireworkcolor())) {
                airdrop.setFireworkcolor(Color.GREEN);
            } else if (Color.GREEN.equals(airdrop.getFireworkcolor())) {
                airdrop.setFireworkcolor(Color.MAROON);
            } else if (Color.MAROON.equals(airdrop.getFireworkcolor())) {
                airdrop.setFireworkcolor(Color.NAVY);
            } else if (Color.NAVY.equals(airdrop.getFireworkcolor())) {
                airdrop.setFireworkcolor(Color.PURPLE);
            } else if (Color.PURPLE.equals(airdrop.getFireworkcolor())) {
                airdrop.setFireworkcolor(Color.TEAL);
            } else if (Color.TEAL.equals(airdrop.getFireworkcolor())) {
                airdrop.setFireworkcolor(Color.GRAY);
            } else if (Color.GRAY.equals(airdrop.getFireworkcolor())) {
                airdrop.setFireworkcolor(Color.SILVER);
            } else if (Color.SILVER.equals(airdrop.getFireworkcolor())) {
                airdrop.setFireworkcolor(Color.FUCHSIA);
            } else if (Color.FUCHSIA.equals(airdrop.getFireworkcolor())) {
                airdrop.setFireworkcolor(Color.LIME);
            } else if (Color.LIME.equals(airdrop.getFireworkcolor())) {
                airdrop.setFireworkcolor(Color.YELLOW);
            } else if (Color.YELLOW.equals(airdrop.getFireworkcolor())) {
                airdrop.setFireworkcolor(Color.AQUA);
            } else if (Color.AQUA.equals(airdrop.getFireworkcolor())) {
                airdrop.setFireworkcolor(Color.ORANGE);
            } else if (Color.ORANGE.equals(airdrop.getFireworkcolor())) {
                airdrop.setFireworkcolor(Color.WHITE);
            } else if (Color.WHITE.equals(airdrop.getFireworkcolor())) {
                airdrop.setFireworkcolor(Color.BLACK);
            }
            new FireworkMenu(airdrop).openMenu(player);
        }


    }
    private class FireworkEnabledButton extends Button{
        private Airdrop airdrop;
        public FireworkEnabledButton(Airdrop airdrop){
            this.airdrop = airdrop;
        }

        @Override
        public ItemStack getButtonItem(final Player player){
            if(airdrop.isFireworkenabled()){
                return new ItemBuilder
                        (CompatibleMaterial.INK_SACK.getMaterial())
                        .setName(CC.translate("&aEnabled"))
                        .setDurability(10)
                        .setLore(Lists.newArrayList(
                                CC.translate("&7Click to toggle"),
                                CC.translate("&7the firework")
                        ))
                        .toItemStack();
            }else {
                return new ItemBuilder
                        (CompatibleMaterial.INK_SACK.getMaterial())
                        .setName(CC.translate("&cDisabled"))
                        .setDurability(8)
                        .setLore(Lists.newArrayList(
                                CC.translate("&7Click to toggle"),
                                CC.translate("&7the firework")
                        ))
                        .toItemStack();
            }





        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType) {

            airdrop.setFireworkenabled(!airdrop.isFireworkenabled());
            new FireworkMenu(airdrop).openMenu(player);



        }


    }





}
