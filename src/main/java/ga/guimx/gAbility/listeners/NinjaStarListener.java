package ga.guimx.gAbility.listeners;

import ga.guimx.gAbility.utils.LastPlayerHit;
import ga.guimx.gAbility.utils.PlayerInfo;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class NinjaStarListener implements Listener {
    @EventHandler
    void onHit(EntityDamageByEntityEvent event){
        if (!(event.getDamager() instanceof Player attacker) ||
            !(event.getEntity() instanceof Player victim)) return;
        PlayerInfo.getLastPlayersHit().put(victim.getUniqueId(),new LastPlayerHit(attacker,System.currentTimeMillis()));
    }
}
