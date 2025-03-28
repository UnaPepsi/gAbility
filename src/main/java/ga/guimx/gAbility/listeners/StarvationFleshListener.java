package ga.guimx.gAbility.listeners;

import ga.guimx.gAbility.GAbility;
import ga.guimx.gAbility.abilities.StarvationFlesh;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.UUID;

public class StarvationFleshListener implements Listener {
    private HashMap<UUID,Integer> hitsRemaining = new HashMap<>(); //attacker:hits
    private HashMap<UUID,UUID> playerCurrentTarget = new HashMap<>(); //attacker:victim
    @EventHandler
    void onHit(EntityDamageByEntityEvent event){
        if (!(event.getEntity() instanceof Player victim) ||
                !(event.getDamager() instanceof Player attacker) ||
                event.getDamageSource().isIndirect() //for example, a player shooting with a bow on their off-hand
        ){
            return;
        }
        ItemStack item = attacker.getInventory().getItemInMainHand();
        String itemType = item.getPersistentDataContainer().get(GAbility.getKey(), PersistentDataType.STRING);
        if (itemType == null || !itemType.equals("starvation_flesh")) return;
        StarvationFlesh starvationFlesh = new StarvationFlesh();
        if (!starvationFlesh.checks(attacker)) return;

        if (playerCurrentTarget.getOrDefault(attacker.getUniqueId(),victim.getUniqueId()).equals(victim.getUniqueId())){
            hitsRemaining.put(attacker.getUniqueId(),hitsRemaining.getOrDefault(attacker.getUniqueId(),0)+1);
            //if player switches targets, the counter resets
        }else{
            hitsRemaining.put(attacker.getUniqueId(),1);
        }
        playerCurrentTarget.put(attacker.getUniqueId(),victim.getUniqueId());

        if (hitsRemaining.get(attacker.getUniqueId()) >= 3) {
            starvationFlesh.handle(attacker, item, victim);
            hitsRemaining.remove(attacker.getUniqueId());
            playerCurrentTarget.remove(attacker.getUniqueId());
        }
    }
}
