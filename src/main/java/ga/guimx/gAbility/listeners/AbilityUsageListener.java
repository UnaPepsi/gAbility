package ga.guimx.gAbility.listeners;

import ga.guimx.gAbility.GAbility;
import ga.guimx.gAbility.abilities.Regeneration;
import ga.guimx.gAbility.abilities.Resistance;
import ga.guimx.gAbility.abilities.Strength;
import ga.guimx.gAbility.abilities.Switcher;
import io.papermc.paper.persistence.PersistentDataContainerView;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
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
        switch (itemType){
            case "strength" -> new Strength().handle(player,item);
            case "resistance" -> new Resistance().handle(player, item);
            case "regeneration" -> new Regeneration().handle(player,item);
        }
    }

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
