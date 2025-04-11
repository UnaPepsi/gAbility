package ga.guimx.gAbility.listeners;

import ga.guimx.gAbility.GAbility;
import ga.guimx.gAbility.abilities.Copycat;
import ga.guimx.gAbility.utils.Ability;
import ga.guimx.gAbility.utils.AbilityType;
import ga.guimx.gAbility.utils.Chat;
import ga.guimx.gAbility.utils.PlayerInfo;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public class CopycatListener implements Listener {
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
        if (itemType == null || !itemType.equals("copycat")) return;
        Copycat copycat = new Copycat();
        if (!copycat.checks(attacker)) return;
        if (PlayerInfo.getPlayersWithAntitrapBone().contains(victim.getUniqueId())){
            attacker.sendMessage(Chat.translate(GAbility.getPrefix()+ Ability.fromAbilityType(AbilityType.COPYCAT).getErrorMessage()
                    .replace("%player%",victim.getName())));
            return;
        }
        if (playerCurrentTarget.getOrDefault(attacker.getUniqueId(),victim.getUniqueId()).equals(victim.getUniqueId())){
            hitsRemaining.put(attacker.getUniqueId(),hitsRemaining.getOrDefault(attacker.getUniqueId(),0)+1);
        //if player switches targets, the counter resets
        }else{
            hitsRemaining.put(attacker.getUniqueId(),1);
        }
        playerCurrentTarget.put(attacker.getUniqueId(),victim.getUniqueId());

        if (hitsRemaining.get(attacker.getUniqueId()) >= 3) {
            copycat.handle(attacker, item, victim);
            hitsRemaining.remove(attacker.getUniqueId());
            playerCurrentTarget.remove(attacker.getUniqueId());
        }
    }
    @EventHandler(priority = EventPriority.HIGH)
    void onBlockInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if (event.getClickedBlock() == null) return;
        //Adding blocks to the list
        Optional<ga.guimx.gAbility.utils.Copycat> copyCatVictim = PlayerInfo.getPlayersWithCopycat().stream().filter(copycat -> copycat.victim().getUniqueId().equals(player.getUniqueId())).findFirst();
        copyCatVictim.ifPresent(copycat -> copycat.blocksToInteract().add(event.getClickedBlock().getLocation()));
        //Letting the attacker interact with those blocks
        Optional<ga.guimx.gAbility.utils.Copycat> copyCatAttacker = PlayerInfo.getPlayersWithCopycat().stream().filter(copycat -> copycat.attacker().getUniqueId().equals(player.getUniqueId())).findFirst();
        copyCatAttacker.ifPresent(copycat -> {
            if (copycat.blocksToInteract().contains(event.getClickedBlock().getLocation())){
                event.setUseInteractedBlock(Event.Result.ALLOW);
            }
        });
    }

    @EventHandler
    void onBlockPlace(BlockPlaceEvent event){
        Player player = event.getPlayer();
        Optional<ga.guimx.gAbility.utils.Copycat> copycatVictim = PlayerInfo.getPlayersWithCopycat().stream().filter(copycat -> copycat.victim().getUniqueId().equals(player.getUniqueId())).findFirst();
        copycatVictim.ifPresent(copycat -> copycat.blocksToBreak().add(event.getBlockPlaced().getLocation()));
    }
    @EventHandler(priority = EventPriority.HIGH)
    void onBlockBreak(BlockBreakEvent event){
        Player player = event.getPlayer();
        Optional<ga.guimx.gAbility.utils.Copycat> copyCatAttacker = PlayerInfo.getPlayersWithCopycat().stream().filter(copycat -> copycat.attacker().getUniqueId().equals(player.getUniqueId())).findFirst();
        copyCatAttacker.ifPresent(copycat -> {
            //not using event.setCancelled(!copycat...contains...) to let other plugins handle that if nothing is found
            if (copycat.blocksToBreak().contains(event.getBlock().getLocation())){
                event.setCancelled(false);
            }
        });
    }
}
