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
    ANTITRAP_BONE("antitrap_bone"),
    NINJA_STAR("ninja_star"),
    COMBO("combo"),
    CONFUSER("confuser"),
    FOCUS_MODE("focus_mode"),
    ZEUS_HAMMER("zeus_hammer"),
    ANTITRAP_BEACON("antitrap_beacon"),
    RISKY_MODE("risky_mode"),
    GUARDIAN_ANGEL("guardian_angel"),
    RAGE_BALL("rage_ball"),
    PORTABLE_BARD("portable_bard"),
    STARVATION_FLESH("starvation_flesh"),
    BERSERK("berserk"),
    MEDKIT("medkit"),
    CLOSE_CALL("close_call"),
    COPYCAT("copy_cat");
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
