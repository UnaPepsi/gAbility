package ga.guimx.gAbility.listeners;

import ga.guimx.gAbility.GAbility;
import ga.guimx.gAbility.abilities.AntitrapBone;
import ga.guimx.gAbility.utils.Ability;
import ga.guimx.gAbility.utils.AbilityType;
import ga.guimx.gAbility.utils.Chat;
import ga.guimx.gAbility.utils.PlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
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

public class AntitrapBoneListener implements Listener {
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
        String itemType = item.getPersistentDataContainer().get(new NamespacedKey(GAbility.getInstance(),"ability"), PersistentDataType.STRING);
        if (itemType == null || !itemType.equals("antitrap_bone")) return;
        AntitrapBone antitrapBone = new AntitrapBone();
        if (!antitrapBone.checks(attacker)) return;
        if (PlayerInfo.getPlayersWithAntitrapBone().contains(victim.getUniqueId())){
            attacker.sendMessage(Chat.translate(GAbility.getPrefix()+ Ability.fromAbilityType(AbilityType.ANTITRAP_BONE).getErrorMessage()
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
            antitrapBone.handle(attacker, item, victim);
            hitsRemaining.remove(attacker.getUniqueId());
            playerCurrentTarget.remove(attacker.getUniqueId());
        }
    }

    @EventHandler
    void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (PlayerInfo.getPlayersWithAntitrapBone().contains(player.getUniqueId())){
            player.sendMessage(Chat.translate(GAbility.getPrefix() + Ability.fromAbilityType(AbilityType.ANTITRAP_BONE).getMessageTargets()
                    .replace("%player%",player.getName())));
        }
    }
    @EventHandler
    void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (PlayerInfo.getPlayersWithAntitrapBone().contains(player.getUniqueId())){
            player.sendMessage(Chat.translate(GAbility.getPrefix() + Ability.fromAbilityType(AbilityType.ANTITRAP_BONE).getMessageTargets()
                    .replace("%player%",player.getName())));
        }
    }
    @EventHandler
    void onBlockInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if (event.getClickedBlock() != null && PlayerInfo.getPlayersWithAntitrapBone().contains(player.getUniqueId())){
            event.setUseInteractedBlock(Event.Result.DENY);
            player.sendMessage(Chat.translate(GAbility.getPrefix() + Ability.fromAbilityType(AbilityType.ANTITRAP_BONE).getMessageTargets()
                    .replace("%player%",player.getName())));
        }
    }
}
