package ga.guimx.gAbility.listeners;

import ga.guimx.gAbility.GAbility;
import ga.guimx.gAbility.abilities.AntitrapBeacon;
import ga.guimx.gAbility.utils.Ability;
import ga.guimx.gAbility.utils.AbilityType;
import ga.guimx.gAbility.utils.Chat;
import ga.guimx.gAbility.utils.PlayerInfo;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageAbortEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class AntitrapBeaconListener implements Listener {
    @EventHandler
    void onBlockPlace(BlockPlaceEvent event){
        if ("antitrap_beacon".equals(event.getItemInHand().getPersistentDataContainer().get(new NamespacedKey(GAbility.getInstance(),"ability"), PersistentDataType.STRING))) {
            AntitrapBeacon beacon = new AntitrapBeacon();
            boolean check = beacon.checks(event.getPlayer());
            //I remember making this beacon item on Skript long ago,
            //and because there wasn't any event.setCancelled(false),
            //plugins like WorldGuard or Faction Protections made it impossible to use the block place event,
            //so I had to listen for right clicks instead, then check where the player was facing
            //and finally if the block where it would be placed was air.
            //here I just set the event to not cancel lol
            event.setCancelled(!check);
            if (check) {
                beacon.handle(event.getPlayer(), null,event.getBlockPlaced().getLocation());
            }
            return;
        }
        //cancel if beacon nearby
        //checking worlds is necessary as if they're in different worlds Location#distance would throw an exception
        if (PlayerInfo.getBeaconsPlaced().keySet().stream().anyMatch(loc -> loc.getWorld().equals(event.getBlockPlaced().getWorld()) && loc.distance(event.getBlockPlaced().getLocation()) <= 15)){
            event.setCancelled(true);
            event.getPlayer().sendMessage(Chat.translate(GAbility.getPrefix()+ Ability.fromAbilityType(AbilityType.ANTITRAP_BEACON).getMessageTargets()));
        }
    }

    @EventHandler
    void onBlockHit(BlockDamageAbortEvent event){
        Location loc = event.getBlock().getLocation();
        if (!PlayerInfo.getBeaconsPlaced().containsKey(loc)){
            return;
        }

        //event.setCancelled(true);
        float damageRemaining = (float) PlayerInfo.getBeaconsPlaced().get(loc);
        PlayerInfo.getBeaconsPlaced().put(
                loc,PlayerInfo.getBeaconsPlaced().get(loc)-1
        );
        event.getPlayer().sendBlockDamage(loc,1-(damageRemaining/20));

        if (PlayerInfo.getBeaconsPlaced().get(loc) <= 0){
            PlayerInfo.getBeaconsPlaced().remove(loc);
            event.getBlock().setType(Material.AIR);
        }
    }

    @EventHandler
    void onBlockBreak(BlockBreakEvent event){
        Location loc = event.getBlock().getLocation();
        if (PlayerInfo.getBeaconsPlaced().containsKey(loc)){
            event.setCancelled(true);
        }
        //to let other plugins manage the event
        //event.setCancelled(PlayerInfo.getBeaconsPlaced().containsKey(loc));
    }
    @EventHandler
    void onMovement(PlayerItemHeldEvent event){
        Player player = event.getPlayer();
        NamespacedKey key = new NamespacedKey(GAbility.getInstance(),"ability");
        ItemStack item = player.getInventory().getItem(event.getNewSlot());
        if(item == null || !"antitrap_beacon".equals(item.getPersistentDataContainer().get(key,PersistentDataType.STRING))){
            return;
        }
        Bukkit.getScheduler().runTaskTimer(GAbility.getInstance(),runnable -> {
            if (player.getEquipment() == null ||
                    (!"antitrap_beacon".equals(player.getEquipment().getItemInMainHand().getPersistentDataContainer().get(key,PersistentDataType.STRING))
                    && !"antitrap_beacon".equals(player.getEquipment().getItemInMainHand().getPersistentDataContainer().get(key,PersistentDataType.STRING))
            )){
                runnable.cancel();
                return;
            }
            double playerX = player.getX();
            double playerZ = player.getZ();
            float radius = 14.5f;
            for (int angle = 0; angle <= 360; angle+=5){
                player.spawnParticle(Particle.DUST,
                        playerX+Math.cos(Math.toRadians(angle))*radius,
                        player.getY(),
                        playerZ+Math.sin(Math.toRadians(angle))*radius,5, new Particle.DustOptions(Color.RED,2));
            }
        },0,30); //1.5s
    }

    @EventHandler
    void onPistonActivate(BlockPistonExtendEvent event){
        //to stop pistons from moving the beacon
        event.setCancelled(event.getBlocks().stream().anyMatch(block -> PlayerInfo.getBeaconsPlaced().containsKey(block.getLocation())));
    }
}
