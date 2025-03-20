package ga.guimx.gAbility.listeners;

import ga.guimx.gAbility.GAbility;
import ga.guimx.gAbility.abilities.AntitrapBone;
import ga.guimx.gAbility.utils.Ability;
import ga.guimx.gAbility.utils.AbilityType;
import ga.guimx.gAbility.utils.Chat;
import ga.guimx.gAbility.utils.PlayerInfo;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.UUID;

public class ComboAbilityListener implements Listener {
    @EventHandler
    void onHit(EntityDamageByEntityEvent event){
        if (!(event.getEntity() instanceof Player) ||
            !(event.getDamager() instanceof Player attacker) ||
            event.getDamageSource().isIndirect() //for example, a player shooting with a bow on their off-hand
        ){
            return;
        }
        if (PlayerInfo.getComboHitCounter().containsKey(attacker.getUniqueId())) {
            PlayerInfo.getComboHitCounter().put(attacker.getUniqueId(),
                    PlayerInfo.getComboHitCounter().get(attacker.getUniqueId())+1
            );
        }
    }
}
