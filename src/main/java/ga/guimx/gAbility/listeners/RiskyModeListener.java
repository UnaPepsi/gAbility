package ga.guimx.gAbility.listeners;

import ga.guimx.gAbility.utils.PlayerInfo;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class RiskyModeListener implements Listener {
    @EventHandler
    void onHit(EntityDamageByEntityEvent event){
        if (!(event.getDamager() instanceof Player attacker) ||
            !(event.getEntity() instanceof Player victim)) return;
        if (PlayerInfo.getPlayersWithRiskyMode().containsKey(attacker.getUniqueId())){
            event.setDamage(event.getDamage()*(1+PlayerInfo.getPlayersWithRiskyMode().get(attacker.getUniqueId())/100));
            Particle.BLOCK.builder()
                    .allPlayers()
                    .location(victim.getEyeLocation())
                    .data(Material.REDSTONE_BLOCK.createBlockData())
                    .count(50)
                    .spawn();
        }
    }
}
