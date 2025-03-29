package ga.guimx.gAbility.listeners;

import ga.guimx.gAbility.GAbility;
import ga.guimx.gAbility.abilities.RageBall;
import ga.guimx.gAbility.abilities.Switcher;
import ga.guimx.gAbility.utils.Ability;
import ga.guimx.gAbility.utils.AbilityType;
import ga.guimx.gAbility.utils.PlayerInfo;
import ga.guimx.gAbility.utils.ThrownEnderPearl;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrowableProjectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class ProjectileAbilityUsageListener implements Listener {
    @EventHandler
    void projectileThrown(ProjectileLaunchEvent event){
        if (!(event.getEntity().getShooter() instanceof Player player)) return;
        ItemStack item;
        if (event.getEntity() instanceof ThrowableProjectile throwableProjectile){
            item = throwableProjectile.getItem();
        }else if (event.getEntity() instanceof AbstractArrow abstractArrow){
            item = abstractArrow.getItemStack();
        }else{
            return;
        }
        switch (item.getPersistentDataContainer().get(GAbility.getKey(), PersistentDataType.STRING)){
            case "switcher":
                Switcher switcher = new Switcher();
                if (!switcher.checks(player)){
                    event.setCancelled(true);
                    return;
                }
                switcher.putOnCooldown(player);
                break;
            case "rage_ball":
                RageBall rageBall = new RageBall();
                if (!rageBall.checks(player)){
                    event.setCancelled(true);
                    return;
                }
                rageBall.putOnCooldown(player);
                break;
            case null, default:
                if (event.getEntity() instanceof EnderPearl){
                    PlayerInfo.getLastThrownEnderPearl().put(player.getUniqueId(),new ThrownEnderPearl(player.getLocation().clone(),System.currentTimeMillis()));
                }
                break;
        }
    }
    @EventHandler
    void projectileHit(ProjectileHitEvent event){
        if (!(event.getEntity().getShooter() instanceof Player player)) return;
        ItemStack item;
        if (event.getEntity() instanceof ThrowableProjectile throwableProjectile){
            item = throwableProjectile.getItem();
        }else if (event.getEntity() instanceof AbstractArrow abstractArrow){
            item = abstractArrow.getItemStack();
        }else{
            return;
        }

        switch (item.getPersistentDataContainer().get(GAbility.getKey(), PersistentDataType.STRING)){
            case "switcher":
                if (event.getHitEntity() != null &&
                    event.getHitEntity() instanceof Player targetPlayer &&
                    !targetPlayer.equals(player))
                {
                    new Switcher().handle(player, null, targetPlayer);
                }
                break;
            case "rage_ball":
                new RageBall().handle(player,null,event.getEntity().getLocation().getNearbyPlayers(Ability.fromAbilityType(AbilityType.RAGE_BALL).getRadius(),
                        p -> !p.getUniqueId().equals(player.getUniqueId())));
                break;
            case null, default:
                break;
        }
    }
}
