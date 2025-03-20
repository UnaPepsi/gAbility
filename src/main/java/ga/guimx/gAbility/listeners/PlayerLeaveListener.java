package ga.guimx.gAbility.listeners;

import ga.guimx.gAbility.utils.PlayerInfo;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeaveListener implements Listener {
    @EventHandler
    void onDisconnect(PlayerQuitEvent event){
        Player player = event.getPlayer();
        //if a player disconnects before their size is set back to normal, without this the attribute would stay
        if (PlayerInfo.getPlayersShrunk().contains(player.getUniqueId())){
            player.getAttribute(Attribute.SCALE).setBaseValue(
                    player.getAttribute(Attribute.SCALE).getBaseValue()*2
            );
        }
        //same with reach
        if (PlayerInfo.getPlayersWithReach().contains(player.getUniqueId())){
            player.getAttribute(Attribute.ENTITY_INTERACTION_RANGE).setBaseValue(
                    player.getAttribute(Attribute.ENTITY_INTERACTION_RANGE).getBaseValue()/2
            );
            player.getAttribute(Attribute.BLOCK_INTERACTION_RANGE).setBaseValue(
                    player.getAttribute(Attribute.BLOCK_INTERACTION_RANGE).getBaseValue()/2
            );
        }
    }
}
