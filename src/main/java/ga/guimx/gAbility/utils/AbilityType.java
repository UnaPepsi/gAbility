package ga.guimx.gAbility.utils;

import lombok.Getter;

@Getter
public enum AbilityType {
    STRENGTH("strength"),
    RESISTANCE("resistance"),
    REGENERATION("regeneration"),
    SWITCHER("switcher"),
    SHRINKER("shrinker"),
    TIME_WARP("time_warp"),
    REACH("reach"),
    ANTITRAP_BONE("antitrap_bone");
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
