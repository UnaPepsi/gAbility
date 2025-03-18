package ga.guimx.gAbility.listeners;

import ga.guimx.gAbility.GAbility;
import ga.guimx.gAbility.abilities.Resistance;
import ga.guimx.gAbility.abilities.Strength;
import ga.guimx.gAbility.utils.Ability;
import ga.guimx.gAbility.utils.AbilityType;
import io.papermc.paper.persistence.PersistentDataContainerView;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class AbilityUsageListener implements Listener {
    public AbilityUsageListener(){}
    @EventHandler
    void playerInteract(PlayerInteractEvent event){
        if (event.getItem() == null){return;}
        ItemStack item = event.getItem();
        PersistentDataContainerView container = item.getPersistentDataContainer();
        Player player = event.getPlayer();
        if (container.has(new NamespacedKey(GAbility.getInstance(),"strength"))){
            new Strength().handle(player, Ability.fromAbilityType(AbilityType.STRENGTH),item);
        }else if (container.has(new NamespacedKey(GAbility.getInstance(),"resistance"))) {
            new Resistance().handle(player, Ability.fromAbilityType(AbilityType.RESISTANCE), item);
        }
    }
}
