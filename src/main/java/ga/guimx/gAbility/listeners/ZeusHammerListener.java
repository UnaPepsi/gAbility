package ga.guimx.gAbility.listeners;

import ga.guimx.gAbility.utils.PlayerInfo;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class ZeusHammerListener implements Listener {
    @EventHandler
    void onHit(EntityDamageByEntityEvent event){
        if (!(event.getDamager() instanceof Player attacker) ||
            !(event.getEntity() instanceof Player victim)) return;
        if (PlayerInfo.getPlayersWithZeusHammer().contains(attacker.getUniqueId()) && Math.random() < 0.2){
            victim.getWorld().strikeLightningEffect(victim.getLocation());
            //victim.damage(16,attacker); to avoid multiple strikes
            victim.setHealth(victim.getHealth()-3);
        }
    }
}
