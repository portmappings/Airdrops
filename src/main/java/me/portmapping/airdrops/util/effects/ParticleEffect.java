package me.portmapping.airdrops.util.effects;

import java.awt.Color;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public enum ParticleEffect {
  HUGE_EXPLOSION("hugeexplosion", "EXPLOSION_HUGE"),
  LARGE_EXPLODE("largeexplode", "EXPLOSION_LARGE"),
  BUBBLE("bubble", "WATER_BUBBLE"),
  SUSPEND("suspended", "SUSPENDED"),
  DEPTH_SUSPEND("depthsuspend", "SUSPENDED_DEPTH"),
  MAGIC_CRIT("magicCrit", "CRIT_MAGIC"),
  MOB_SPELL("mobSpell", "SPELL_MOB", true),
  MOB_SPELL_AMBIENT("mobSpellAmbient", "SPELL_MOB_AMBIENT"),
  INSTANT_SPELL("instantSpell", "SPELL_INSTANT"),
  WITCH_MAGIC("witchMagic", "SPELL_WITCH"),
  EXPLODE("explode", "EXPLOSION_NORMAL"),
  SPLASH("splash", "WATER_SPLASH"),
  LARGE_SMOKE("largesmoke", "SMOKE_LARGE"),
  RED_DUST("reddust", "REDSTONE", true),
  SNOWBALL_POOF("snowballpoof", "SNOWBALL"),
  ANGRY_VILLAGER("angryVillager", "VILLAGER_ANGRY"),
  HAPPY_VILLAGER("happyVillager", "VILLAGER_HAPPY"),
  PORTAL("portal", "PORTAL");
  private String particleName;

  private final String enumValue;

  private boolean hasColor;




  ParticleEffect(String particleName, String enumValue, boolean hasColor) {
    this.particleName = particleName;
    this.enumValue = enumValue;
    this.hasColor = hasColor;
  }

  ParticleEffect(String particleName, String enumValue) {
    this.particleName = particleName;
    this.enumValue = enumValue;
  }

  public String getName() {
    return this.particleName;
  }

  public boolean hasColor() {
    return this.hasColor;
  }
  public String getEnumValue() {
    return this.enumValue;
  }

}
