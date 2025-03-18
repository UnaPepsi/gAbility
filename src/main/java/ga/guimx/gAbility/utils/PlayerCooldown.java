package ga.guimx.gAbility.utils;

import lombok.Getter;

import java.util.HashMap;
import java.util.UUID;

public class PlayerCooldown {
    @Getter
    private static HashMap<UUID,HashMap<Ability,Long>> abilityCooldowns = new HashMap<>();
    @Getter
    private static HashMap<UUID,Long> globalAbilityCooldowns = new HashMap<>();
}