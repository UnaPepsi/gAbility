package ga.guimx.gAbility.listeners;

import ga.guimx.gAbility.GAbility;
import ga.guimx.gAbility.abilities.*;
import ga.guimx.gAbility.utils.*;
import io.papermc.paper.persistence.PersistentDataContainerView;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class AbilityUsageListener implements Listener {
    public AbilityUsageListener(){}
    @EventHandler
    void playerInteract(PlayerInteractEvent event){
        if (event.getItem() == null){return;}
        ItemStack item = event.getItem();
        PersistentDataContainerView container = item.getPersistentDataContainer();
        Player player = event.getPlayer();
        String itemType = container.get(new NamespacedKey(GAbility.getInstance(),"ability"), PersistentDataType.STRING);
        if (itemType == null) return;
        if (event.getAction().isRightClick()) {
            switch (itemType) {
                case "strength" -> new Strength().handle(player, item);
                case "resistance" -> new Resistance().handle(player, item);
                case "regeneration" -> new Regeneration().handle(player, item);
                case "shrinker" -> new Shrinker().handle(player, item);
                case "time_warp" -> new TimeWarp().handle(player, item);
                case "reach" -> new Reach().handle(player, item);
                case "ninja_star" -> new NinjaStar().handle(player, item);
                case "combo" -> new Combo().handle(player, item);
                case "focus_mode" -> new FocusMode().handle(player, item);
                case "zeus_hammer" -> new ZeusHammer().handle(player, item);
            }
        }else if (event.getAction().isLeftClick()){
            switch (itemType) {
                case "strength" -> new Strength().checks(player);
                case "resistance" -> new Resistance().checks(player);
                case "regeneration" -> new Regeneration().checks(player);
                case "switcher" -> new Switcher().checks(player);
                case "shrinker" -> new Shrinker().checks(player);
                case "time_warp" -> new TimeWarp().checks(player);
                case "reach" -> new Reach().checks(player);
                case "antitrap_bone" -> new AntitrapBone().checks(player);
                case "ninja_star" -> new NinjaStar().checks(player);
                case "combo" -> new Combo().checks(player);
                case "confuser" -> new Confuser().checks(player);
                case "focus_mode" -> new FocusMode().checks(player);
                case "zeus_hammer" -> new ZeusHammer().checks(player);
            }
        }
    }

    @EventHandler
    void blockPlace(BlockPlaceEvent event){
        //to avoid some ability items being placed on the ground
        if (event.getItemInHand().getPersistentDataContainer().has(new NamespacedKey(GAbility.getInstance(),"ability"),PersistentDataType.STRING)) event.setCancelled(true);
    }

    //to try and avoid false staff bans or false reports
    @EventHandler
    void onHit(EntityDamageByEntityEvent event){
        if (!(event.getDamager() instanceof Player attacker) ||
            !(event.getEntity() instanceof Player victim) ||
            Ability.fromAbilityType(AbilityType.REACH).getMessageTargets().isEmpty() ||
            !PlayerInfo.getPlayersWithReach().contains(attacker.getUniqueId())
        ){
            return;
        }
        victim.sendMessage(Chat.translate(GAbility.getPrefix()+ Ability.fromAbilityType(AbilityType.REACH).getMessageTargets()
                .replace("%player%", attacker.getName())));
    }
}
