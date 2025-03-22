package ga.guimx.gAbility.listeners;

import ga.guimx.gAbility.utils.LastPlayerHit;
import ga.guimx.gAbility.utils.PlayerInfo;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class FocusModeListener implements Listener {
    @EventHandler
    void onHit(EntityDamageByEntityEvent event){
        if (!(event.getDamager() instanceof Player attacker) ||
            !(event.getEntity() instanceof Player victim)) return;
        PlayerInfo.getLastPlayersAttacked().put(attacker.getUniqueId(),new LastPlayerHit(victim,System.currentTimeMillis()));
        if (PlayerInfo.getPlayersWithFocusMode().containsKey(attacker.getUniqueId()) &&
            PlayerInfo.getPlayersWithFocusMode().get(attacker.getUniqueId()).equals(victim.getUniqueId()))
        {
            event.setDamage(event.getFinalDamage()*1.25);
            Particle.BLOCK.builder()
                    .allPlayers()
                    .location(victim.getEyeLocation())
                    .data(Material.REDSTONE_BLOCK.createBlockData())
                    .count(50)
                    .spawn();
        }
    }
}
