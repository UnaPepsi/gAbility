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
    private final AbilityType abilityType;
    private final Material material;
    private final String name;
    private final List<String> lore;
    private final Long cooldown;
    private final Long duration;
    private final String usedMessage;
    private final String messageTargets;
    private final String errorMessage;
    private final boolean enchanted;
    public static Ability fromAbilityType(AbilityType abilityType){
        return PluginConfig.getAbilities().get(abilityType);
    }
    private Ability(Builder builder) {
        this.abilityType = builder.abilityType;
        this.material = builder.material;
        this.name = builder.name;
        this.lore = builder.lore;
        this.cooldown = builder.cooldown;
        this.duration = builder.duration;
        this.usedMessage = builder.usedMessage;
        this.errorMessage = builder.errorMessage;
        this.enchanted = builder.enchanted;
        this.messageTargets = builder.messageTargets;
    }

    public static class Builder {
        private final AbilityType abilityType;
        private final Material material;
        private final String name;
        private final List<String> lore;
        private final long cooldown;
        private final boolean enchanted;
        private final String usedMessage;

        private Long duration;
        private String errorMessage = null;
        private String messageTargets = null;

        public Builder(AbilityType abilityType, Material material, String name, List<String> lore, long cooldown, boolean enchanted, String usedMessage) {
            this.abilityType = abilityType;
            this.material = material;
            this.name = name;
            this.lore = lore;
            this.cooldown = cooldown;
            this.enchanted = enchanted;
            this.usedMessage = usedMessage;
        }

        public Builder duration(long duration) {
            this.duration = duration;
            return this;
        }

        public Builder errorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
            return this;
        }

        public Builder messageTargets(String messageTargets) {
            this.messageTargets = messageTargets;
            return this;
        }

        public Ability build() {
            return new Ability(this);
        }
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
