package ga.guimx.gAbility.utils;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;
//reason why I don't combine both Lists is because blocksToInteract is for doors, fence gates, etc..., while blocksToBreak is for blocks that got placed
public record Copycat(Player attacker, Player victim, List<Location> blocksToInteract, List<Location> blocksToBreak) {}