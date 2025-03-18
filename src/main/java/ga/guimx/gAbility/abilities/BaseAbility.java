package ga.guimx.gAbility.abilities;

import ga.guimx.gAbility.GAbility;
import ga.guimx.gAbility.config.PluginConfig;
import ga.guimx.gAbility.utils.Ability;
import ga.guimx.gAbility.utils.Chat;
import ga.guimx.gAbility.utils.PlayerCooldown;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

abstract class BaseAbility {
    public final void handle(Player player, Ability ability, ItemStack item, Object... extraArgs){
        if (isOnGlobalAbilityCooldown(player)){
            long cooldown = (PlayerCooldown.getGlobalAbilityCooldowns().get(player.getUniqueId())-System.currentTimeMillis())/1000;
            long minutes = cooldown / 60;
            long seconds = cooldown % 60;
            player.sendMessage(Chat.translate(GAbility.getPrefix()+ PluginConfig.getMessages().get("global_item_cooldown")
                    .replace("%minutes%",String.format("%02d",minutes)).replace("%seconds%",String.format("%02d",seconds))));
            return;
        }
        if (isOnAbilityCooldown(player,ability)){
            long cooldown = (PlayerCooldown.getAbilityCooldowns().get(player.getUniqueId()).get(ability)-System.currentTimeMillis())/1000;
            long minutes = cooldown / 60;
            long seconds = cooldown % 60;
            player.sendMessage(Chat.translate(GAbility.getPrefix()+ PluginConfig.getMessages().get("item_cooldown")
                    .replace("%ability%", ability.getName())
                    .replace("%minutes%",String.format("%02d",minutes)).replace("%seconds%",String.format("%02d",seconds))));
            return;
        }
        abilityLogic(player,item,extraArgs);
        putOnCooldown(player,ability);
    }
    protected boolean isOnGlobalAbilityCooldown(Player player){
        return System.currentTimeMillis() < PlayerCooldown.getGlobalAbilityCooldowns().getOrDefault(player.getUniqueId(),0L);
    }
    protected boolean isOnAbilityCooldown(Player player, Ability ability){
        return System.currentTimeMillis() < PlayerCooldown.getAbilityCooldowns().getOrDefault(player.getUniqueId(),new HashMap<>()).getOrDefault(ability,0L);
    }
    protected abstract void abilityLogic(Player player,ItemStack item,Object... extraArgs);
    protected void putOnCooldown(Player player, Ability ability){
        if (!PlayerCooldown.getAbilityCooldowns().containsKey(player.getUniqueId())){
            PlayerCooldown.getAbilityCooldowns().put(player.getUniqueId(),new HashMap<>(){{put(ability,System.currentTimeMillis() + ability.getCooldown()*1000);}});
        }else{
            PlayerCooldown.getAbilityCooldowns().get(player.getUniqueId()).put(ability, System.currentTimeMillis() + ability.getCooldown()*1000);
        }
        PlayerCooldown.getGlobalAbilityCooldowns().put(player.getUniqueId(),System.currentTimeMillis()+PluginConfig.getGlobalCooldown()*1000);
    }
}