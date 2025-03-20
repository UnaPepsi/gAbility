package ga.guimx.gAbility.utils;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PlayerInfo {
    @Getter
    private static HashMap<UUID,HashMap<Ability,Long>> abilityCooldowns = new HashMap<>();
    @Getter
    private static HashMap<UUID,Long> globalAbilityCooldowns = new HashMap<>();
    @Getter
    private static HashMap<UUID,ThrownEnderPearl> lastThrownEnderPearl = new HashMap<>();
    @Getter
    private static List<UUID> playersWithReach = new ArrayList<>();
    @Getter
    private static List<UUID> playersShrunk = new ArrayList<>();
    @Getter
    private static List<UUID> playersWithAntitrapBone = new ArrayList<>();
}