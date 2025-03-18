package ga.guimx.gAbility.listeners;

import ga.guimx.gAbility.abilities.Strength;
import ga.guimx.gAbility.utils.Ability;
import ga.guimx.gAbility.utils.AbilityType;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class AbilityUsageListener implements Listener {
    public AbilityUsageListener(){}
    @EventHandler
    void playerInteract(PlayerInteractEvent event){
        if (event.getItem() == null){return;}
        if (event.getItem().getType() == Material.BLAZE_POWDER){
            new Strength().handle(event.getPlayer(), new Ability(AbilityType.STRENGTH));
        }
    }
}
