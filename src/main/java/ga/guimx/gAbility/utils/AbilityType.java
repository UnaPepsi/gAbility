package ga.guimx.gAbility.utils;

import lombok.Getter;

public enum AbilityType {
    STRENGTH("name=&6Strength II;lore=&7Get Strength II for 5 seconds.;cooldown=30");
    @Getter
    private final String value;
    AbilityType(String value){
        this.value = value;
    }
    public static AbilityType fromString(String value){
        for (AbilityType abilityType : AbilityType.values()){
            if (abilityType.name().equalsIgnoreCase(value)){
                return abilityType;
            }
        }
        throw new IllegalArgumentException("Invalid enum of type: "+value);
    }
}
