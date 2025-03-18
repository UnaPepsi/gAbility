package ga.guimx.gAbility.abilities;

import ga.guimx.gAbility.utils.Ability;
import ga.guimx.gAbility.utils.PlayerCooldown;
import org.bukkit.entity.Player;

import java.util.HashMap;

abstract class BaseAbility {
    public final void handle(Player player, Ability ability){
        if (isOnGlobalAbilityCooldown(player)){
            player.sendMessage("global cd for: "+(PlayerCooldown.getGlobalAbilityCooldowns().get(player.getUniqueId())-System.currentTimeMillis()));
            return;
        }
        if (isOnAbilityCooldown(player,ability)){
            player.sendMessage("pp item "+ability.getName()+" cd for: "+(PlayerCooldown.getAbilityCooldowns().get(player.getUniqueId()).get(ability)-System.currentTimeMillis()));
            return;
        }
        abilityLogic(player);
        putOnCooldown(player,ability);
    }
    protected boolean isOnGlobalAbilityCooldown(Player player){
        return System.currentTimeMillis() < PlayerCooldown.getGlobalAbilityCooldowns().getOrDefault(player.getUniqueId(),0L);
    }
    protected boolean isOnAbilityCooldown(Player player, Ability ability){
        return System.currentTimeMillis() < PlayerCooldown.getAbilityCooldowns().getOrDefault(player.getUniqueId(),new HashMap<>()).getOrDefault(ability,0L);
    }
    protected abstract void abilityLogic(Player player);
    protected void putOnCooldown(Player player, Ability ability){
        if (!PlayerCooldown.getAbilityCooldowns().containsKey(player.getUniqueId())){
            PlayerCooldown.getAbilityCooldowns().put(player.getUniqueId(),new HashMap<>(){{put(ability,System.currentTimeMillis() + ability.getCooldown()*1000);}});
        }else{
            PlayerCooldown.getAbilityCooldowns().get(player.getUniqueId()).put(ability, System.currentTimeMillis() + ability.getCooldown()*1000);
        }
        PlayerCooldown.getGlobalAbilityCooldowns().put(player.getUniqueId(),System.currentTimeMillis()+10*1000);
    }
}