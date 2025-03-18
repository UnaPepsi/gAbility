package ga.guimx.gAbility.utils;

import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

public class Ability {
    @Getter
    private AbilityType abilityType;
    @Getter
    private String name;
    @Getter
    private String lore;
    @Getter
    private long cooldown;
    public Ability(AbilityType abilityType){
        this.abilityType = abilityType;
        Arrays.stream(abilityType.getValue().split(";")).toList().forEach(c -> {
            if (c.contains("name=")){
                name = c.replace("name=","");
            }else if (c.contains("lore=")){
                lore = c.replace("lore=","");
            }else if (c.contains("cooldown=")){
                cooldown = Long.parseLong(c.replace("cooldown=",""));
            }
        });
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
