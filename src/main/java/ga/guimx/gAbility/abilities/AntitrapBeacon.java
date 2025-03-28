package ga.guimx.gAbility.abilities;

import ga.guimx.gAbility.GAbility;
import ga.guimx.gAbility.utils.*;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class AntitrapBeacon extends BaseAbility{
    @Override
    protected Ability getAbility(){return Ability.fromAbilityType(AbilityType.ANTITRAP_BEACON);}
    @Override
    protected void abilityLogic(Player player, ItemStack item, Object... location) {
        Location loc = (Location) location[0];
        player.sendMessage(Chat.translate(GAbility.getPrefix()+ getAbility().getUsedMessage()));
        PlayerInfo.getBeaconsPlaced().put(loc,20);
        Task.runLater(() -> {
            if (PlayerInfo.getBeaconsPlaced().containsKey(loc)){
                loc.getWorld().getBlockAt(loc).setType(Material.AIR);
                PlayerInfo.getBeaconsPlaced().remove(loc);
            }
        }, getAbility().getDuration()*20);
        Task.runTimer(runnable -> {
            if (!PlayerInfo.getBeaconsPlaced().containsKey(loc)){
                runnable.cancel();
                return;
            }
            loc.getWorld().getBlockAt(loc).setType(getAbility().getMaterial()); //if the block gets removed by tnt, staff, etc...
            loc.getNearbyPlayers(30).forEach(p -> {
                float radius = 14.5f;
                for (int angle = 0; angle <= 360; angle+=5){
                    p.spawnParticle(Particle.DUST,
                            loc.getX()+Math.cos(Math.toRadians(angle))*radius,
                            loc.getY(),
                            loc.getZ()+Math.sin(Math.toRadians(angle))*radius,5, new Particle.DustOptions(Color.RED,2));
                }
            });
        },0,30); //1.5s
    }
}