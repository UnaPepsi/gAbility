package ga.guimx.gAbility.listeners;

import ga.guimx.gAbility.GAbility;
import ga.guimx.gAbility.abilities.Switcher;
import ga.guimx.gAbility.utils.PlayerInfo;
import ga.guimx.gAbility.utils.ThrownEnderPearl;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;

public class ProjectileAbilityUsageListener implements Listener {
    @EventHandler
    void projectileThrown(ProjectileLaunchEvent event){
        if (!(event.getEntity().getShooter() instanceof Player player)) return;
        Projectile projectile = event.getEntity();
        //I spent so much trying to figure out why projectile.gerPersistentDataContainer().getKeys() was always empty
        //oh my god why do you have to convert it into an item first?!?"!?"#!"?#!
        //making a switcher shouldn't have taken this long omg I was about to crash out holy
        if (projectile instanceof Snowball snowball && snowball.getItem().getPersistentDataContainer().has(new NamespacedKey(GAbility.getInstance(),"ability"))){
            Switcher switcher = new Switcher();
            if (!switcher.checks(player)){
                event.setCancelled(true);
                return;
            }
            switcher.putOnCooldown(player);
        }
        //imma leave this for now lol this one of the multiple desperate attempts at tryna figure out why it was always empty
        //reason cuz im using the scheduler here is because I genuinely thought at some point that it had to take some ticks to process the data lmfao
        //Bukkit.getScheduler().runTaskLater(GAbility.getInstance(), () -> {
        //    Bukkit.dispatchCommand(player,"data get entity "+projectile.getUniqueId());
        //    player.sendMessage(projectile.getUniqueId().toString());
        //    player.sendMessage(Component.text(projectile.getUniqueId().toString()).clickEvent(ClickEvent.copyToClipboard(projectile.getUniqueId().toString())));
        //    Bukkit.dispatchCommand(player,"tick freeze");
        //    projectile.getPersistentDataContainer().getKeys().forEach(a -> {
        //        player.sendMessage(a.getKey()+"asdasdasd"+a.value());
        //    });
        //},5);

        else if (projectile instanceof EnderPearl){
            PlayerInfo.getLastThrownEnderPearl().put(player.getUniqueId(),new ThrownEnderPearl(player.getLocation().clone(),System.currentTimeMillis()));
        }
    }
    @EventHandler
    void projectileHit(ProjectileHitEvent event){
        if (event.getHitEntity() == null ||
                !(event.getHitEntity() instanceof Player targetPlayer) ||
                !(event.getEntity().getShooter() instanceof Player player) ||
                targetPlayer.equals(player)
        ) return;
        EntityType projectile = event.getEntity().getType();
        switch (projectile){
            case SNOWBALL -> new Switcher().handle(player,null,targetPlayer);
        }
    }
}
