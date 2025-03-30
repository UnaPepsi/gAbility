package ga.guimx.gAbility.utils;

import lombok.Getter;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public  class PlayerInfo {
    @Getter
    private static final HashMap<UUID,HashMap<Ability,Long>> abilityCooldowns = new HashMap<>();
    @Getter
    private static final HashMap<UUID,Long> globalAbilityCooldowns = new HashMap<>();
    @Getter
    private static final HashMap<UUID,ThrownEnderPearl> lastThrownEnderPearl = new HashMap<>();
    @Getter
    private static final List<UUID> playersWithReach = new ArrayList<>();
    @Getter
    private static final List<UUID> playersShrunk = new ArrayList<>();
    @Getter
    private static final List<UUID> playersWithAntitrapBone = new ArrayList<>();
    @Getter
    private static final HashMap<UUID,LastPlayerHit> lastPlayersHit = new HashMap<>(); //uuid is victim
    @Getter
    private static final HashMap<UUID,Integer> comboHitCounter = new HashMap<>(); //attacker:hits
    @Getter
    private static final List<UUID> playersWithConfuser = new ArrayList<>();
    @Getter
    private static final HashMap<UUID,LastPlayerHit> lastPlayersAttacked = new HashMap<>(); //uuid is attacker, this is basically the opposite of lastPlayersHit
    @Getter
    private static final HashMap<UUID,UUID> playersWithFocusMode = new HashMap<>(); //attacker:victim
    @Getter
    private static final List<UUID> playersWithZeusHammer = new ArrayList<>();
    @Getter
    private static final HashMap<Location,Integer> beaconsPlaced = new HashMap<>();
    @Getter
    private static final HashMap<UUID,Double> playersWithRiskyMode = new HashMap<>(); //player:damage_boost
    @Getter
    private static final List<UUID> playersWithGuardianAngel = new ArrayList<>();
    @Getter
    private static final List<UUID> playersWithBerserk = new ArrayList<>();
}