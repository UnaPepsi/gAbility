package ga.guimx.gAbility.utils;

import lombok.Getter;

@Getter
public enum AbilityType {
    STRENGTH("strength"),
    RESISTANCE("resistance"),
    REGENERATION("regeneration"),
    SWITCHER("switcher");
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
