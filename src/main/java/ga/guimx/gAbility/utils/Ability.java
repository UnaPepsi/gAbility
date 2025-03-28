package ga.guimx.gAbility.utils;

import ga.guimx.gAbility.config.PluginConfig;
import lombok.Builder;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import java.util.List;
import java.util.Objects;

@Getter
@Builder
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
    private final EntityType entityType;
    public static Ability fromAbilityType(AbilityType abilityType){
        return PluginConfig.getAbilities().get(abilityType);
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
