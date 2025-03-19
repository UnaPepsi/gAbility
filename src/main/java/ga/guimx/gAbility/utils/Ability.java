package ga.guimx.gAbility.utils;

import ga.guimx.gAbility.config.PluginConfig;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Getter
public class Ability {
    private AbilityType abilityType;
    private Material material;
    private String name;
    private List<String> lore;
    private long cooldown;
    private long duration;
    private String usedMessage;
    private String messageTargets;
    private boolean enchanted;
    public static Ability fromAbilityType(AbilityType abilityType){
        return PluginConfig.getAbilities().get(abilityType);
    }
    public Ability(AbilityType abilityType, Material material, String name, List<String> lore, long cooldown, long duration, String usedMessage, boolean enchanted){
        this.abilityType = abilityType;
        this.material = material;
        this.name = name;
        this.lore = lore;
        this.cooldown = cooldown;
        this.duration = duration;
        this.usedMessage = usedMessage;
        this.enchanted = enchanted;
    }
    public Ability(AbilityType abilityType, Material material, String name, List<String> lore, long cooldown, long duration, String usedMessage, boolean enchanted, String messageTargets){
        this.abilityType = abilityType;
        this.material = material;
        this.name = name;
        this.lore = lore;
        this.cooldown = cooldown;
        this.duration = duration;
        this.usedMessage = usedMessage;
        this.enchanted = enchanted;
        this.messageTargets = messageTargets;
    }

    public Ability(AbilityType abilityType, Material material, String name, List<String> lore, long cooldown, String usedMessage, boolean enchanted){
        this.abilityType = abilityType;
        this.material = material;
        this.name = name;
        this.lore = lore;
        this.cooldown = cooldown;
        this.usedMessage = usedMessage;
        this.enchanted = enchanted;
    }
    public Ability(AbilityType abilityType, Material material, String name, List<String> lore, long cooldown, String usedMessage, boolean enchanted, String messageTargets){
        this.abilityType = abilityType;
        this.material = material;
        this.name = name;
        this.lore = lore;
        this.cooldown = cooldown;
        this.usedMessage = usedMessage;
        this.enchanted = enchanted;
        this.messageTargets = messageTargets;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Ability ability){
            return ability.getName().equalsIgnoreCase(getName());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
